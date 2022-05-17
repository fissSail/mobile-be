<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
            <el-form-item label="问题标题" prop="questionTitle">
                <el-input
                    v-model="queryParams.questionTitle"
                    placeholder="请输入问题标题"
                    clearable
                    @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button
                    type="primary"
                    plain
                    icon="el-icon-plus"
                    size="mini"
                    @click="handleAdd">新增
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                    type="danger"
                    plain
                    icon="el-icon-delete"
                    size="mini"
                    :disabled="multiple"
                    @click="handleDelete">删除
                </el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="bankList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column type="index" header-align="center" align="center" label="序号"/>
            <el-table-column label="问题标题" align="center" prop="questionTitle"/>
            <el-table-column label="题目类型" align="center" prop="questionType">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.questionType == 0">单选</el-tag>
                    <el-tag v-else>多选</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="是否必填" align="center" prop="requiredFlag">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.requiredFlag == 0">必填</el-tag>
                    <el-tag v-else>选填</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit"
                        @click="handleUpdate(scope.row)"
                        v-hasPermi="['system:bank:edit']"
                    >修改
                    </el-button>
                    <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-delete"
                        @click="handleDelete(scope.row)"
                        v-hasPermi="['system:bank:remove']"
                    >删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
        />

        <!-- 添加或修改问卷问题对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="问题标题" prop="questionTitle">
                    <el-input v-model="form.questionTitle" placeholder="请输入问题标题" maxlength="100" show-word-limit/>
                </el-form-item>
                <el-form-item label="题目类型" prop="questionType">
                    <el-select v-model="form.questionType" placeholder="请选择题目类型" clearable>
                        <el-option v-for="item in questionTypeList" :label="item.label"
                                   :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否必填" prop="requiredFlag">
                    <el-switch v-model="form.requiredFlag" active-value="0" inactive-value="1"></el-switch>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model="form.sort" placeholder="请输入排序"></el-input-number>
                </el-form-item>

                <template>
                    <el-divider>选项</el-divider>
                    <draggable :list="form.questionnaireAnswersVOList" :animation="340" @sort="sort">
                        <div v-for="(vo, index) in form.questionnaireAnswersVOList" :key="index" class="select-item">
                            <div class="select-line-icon option-drag">
                                <i class="el-icon-s-operation"/>
                            </div>
                            <el-input v-model="vo.questionnaireOption" placeholder="选项名" size="small" maxlength="100"
                                      show-word-limit>
                                <el-select slot="append" v-model="vo.setMealId" placeholder="请选择套餐" filterable
                                           clearable style="width: 120px;">
                                    <el-option v-for="options in vo.associatedPackagesOptions" :label="options.label"
                                               :value="options.value"></el-option>
                                </el-select>
                            </el-input>
                            <div class="close-btn select-line-icon"
                                 @click="form.questionnaireAnswersVOList.splice(index, 1)">
                                <i class="el-icon-remove-outline"/>
                            </div>
                        </div>
                    </draggable>
                    <div style="margin-left: 20px;">
                        <el-button
                            style="padding-bottom: 0"
                            icon="el-icon-circle-plus-outline"
                            type="text"
                            @click="addSelectItem">
                            添加选项
                        </el-button>
                    </div>
                    <el-divider/>
                </template>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {listBank, getBank, delBank, addBank, updateBank} from "@/api/system/questionnaire/bank";
    import draggable from 'vuedraggable';
    import {listSetMeal} from "@/api/system/questionnaire/setMeal";

    export default {
        components: {draggable},
        name: "Bank",
        data() {
            return {
                // 遮罩层
                loading: true,
                // 选中数组
                ids: [],
                // 非单个禁用
                single: true,
                // 非多个禁用
                multiple: true,
                // 显示搜索条件
                showSearch: true,
                // 总条数
                total: 0,
                // 问卷问题表格数据
                bankList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    questionTitle: null
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                    questionTitle: [{
                        required: true,
                        message: '请输入问题标题',
                        trigger: 'blur'
                    },],
                },
                questionTypeList: [{
                    "label": "单选",
                    "value": "0"
                }, {
                    "label": "多选",
                    "value": "1"
                }],
                associatedPackages: [],
                updateOptionsFlagListJson: ""
            };
        },
        created() {
            this.getList();
            this.getSetMealList();
        },
        methods: {
            /** 查询问卷问题列表 */
            getList() {
                this.loading = true;
                listBank(this.queryParams).then(response => {
                    this.bankList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            // 取消按钮
            cancel() {
                this.open = false;
                this.reset();
            },
            // 表单重置
            reset() {
                this.form = {
                    id: null,
                    questionTitle: null,
                    questionType: "0",
                    requiredFlag: "1",
                    sort: null,
                    questionnaireAnswersVOList: [
                        {
                            id: "",
                            questionnaireOption: '是',
                            associatedPackagesOptions: this.associatedPackages,
                            setMealId: "",
                            sort: 0
                        },
                        {
                            id: "",
                            questionnaireOption: '否',
                            associatedPackagesOptions: this.associatedPackages,
                            setMealId: "",
                            sort: 1
                        }
                    ],
                };
                this.resetForm("form");
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.pageNum = 1;
                this.getList();
            },
            /** 重置按钮操作 */
            resetQuery() {
                this.resetForm("queryForm");
                this.handleQuery();
            },
            // 多选框选中数据
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.single = selection.length !== 1
                this.multiple = !selection.length
            },
            /** 新增按钮操作 */
            handleAdd() {
                this.reset();
                this.open = true;
                this.title = "添加问卷问题";
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
                this.reset();
                const id = row.id;
                getBank(id).then(({data}) => {
                    //重设关联套餐id与关联套餐选项
                    data.questionnaireAnswersVOList = data.questionnaireAnswersVOList.map(vo => {
                        //设置每个项目关联套餐选项
                        vo.associatedPackagesOptions = this.associatedPackages;
                        return vo;
                    })

                    this.form = data;
                    //修改选项标记，如果选项未改动则不修改选项
                    this.updateOptionsFlagListJson = JSON.stringify(data.questionnaireAnswersVOList)
                    this.open = true;
                    this.title = "修改问卷问题";
                });
            },
            /** 提交按钮 */
            submitForm() {
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        if (this.form.id != null) {
                            //判断选项是否改动
                            if (JSON.stringify(this.form.questionnaireAnswersVOList) == this.updateOptionsFlagListJson) {
                                //未改动则传个标识符给后台
                                this.form.updateOptionsFlag = false;
                            }

                            updateBank(this.form).then(response => {
                                this.$modal.msgSuccess("修改成功");
                                this.open = false;
                                this.getList();
                            });
                        } else {
                            addBank(this.form).then(response => {
                                this.$modal.msgSuccess("新增成功");
                                this.open = false;
                                this.getList();
                            });
                        }
                    }
                });
            },
            /** 删除按钮操作 */
            handleDelete(row) {
                const ids = row.id || this.ids;
                this.$modal.confirm('是否确认删除此问卷问题？').then(function () {
                    return delBank(ids);
                }).then(() => {
                    this.getList();
                    this.$modal.msgSuccess("删除成功");
                }).catch(() => {
                });
            },
            /** 添加选项 */
            addSelectItem() {
                this.form.questionnaireAnswersVOList.push({
                    questionnaireOption: '',
                    associatedPackagesOptions: this.associatedPackages,
                    setMealId: "",
                    sort: this.form.questionnaireAnswersVOList.length
                })
            },
            /** 查询套餐 */
            getSetMealList() {
                const pageSize = 100000;
                listSetMeal({pageSize}).then(({data}) => {
                    let records = data.records.map(setMeal => {
                        return {
                            label: setMeal.title,
                            value: setMeal.id
                        }
                    });
                    this.associatedPackages = records;
                });
            },
            /** 选项拖拽重排序 */
            sort(to) {
                let list = this.form.questionnaireAnswersVOList;
                //选项重排序
                for (let i = 0; i < list.length; i++) {
                    list[i].sort = i;
                }
                this.form.questionnaireAnswersVOList = list;
            }
        }
    };
</script>
<style lang="scss" scoped>
    .select-item {
        display: flex;
        border: 1px dashed #fff;
        box-sizing: border-box;

        & .close-btn {
            cursor: pointer;
            color: #f56c6c;
        }

        & .el-input + .el-input {
            margin-left: 4px;
        }
    }

    .select-line-icon {
        line-height: 32px;
        font-size: 22px;
        padding: 0 4px;
        color: #777;
    }

    .option-drag {
        cursor: move;
    }

    .select-item + .select-item {
        margin-top: 4px;
    }
</style>
