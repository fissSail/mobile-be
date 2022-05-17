<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="100px">
            <el-form-item label="答卷人" prop="userName">
                <el-input
                    v-model="queryParams.userName"
                    placeholder="请输入答卷人"
                    clearable
                    @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="answersheetList">
            <el-table-column type="index" header-align="center" align="center" label="序号"/>
            <el-table-column label="答卷人" align="center" prop="userName"/>
            <el-table-column label="答卷时间" align="center" prop="createTime"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit"
                        @click="handleView(scope.row)"
                    >查看
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

        <!-- 弹窗, 查看 -->
        <answer-sheet-view v-if="answersheetViewVisible" ref="answersheetView" @refreshDataList="getList"></answer-sheet-view>
    </div>
</template>

<script>
    import AnswerSheetView from './answer-sheet-view';
    import {listAnswersheet} from "@/api/system/questionnaire/answer";

    export default {
        name: "Answersheet",
        data() {
            return {
                // 遮罩层
                loading: true,
                // 列表数据
                answersheetList: [],
                // 显示搜索条件
                showSearch: true,
                // 总条数
                total: 0,
                // 是否显示弹出层
                answersheetViewVisible: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    userName: null
                }
            };
        },
        components: {AnswerSheetView},
        created() {
            this.getList()
        },
        methods: {
            /** 查询问卷问题列表 */
            getList() {
                this.loading = true;
                listAnswersheet(this.queryParams).then(response => {
                    this.answersheetList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            // 取消按钮
            cancel() {
                this.answersheetViewVisible = false;
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
            /** 新增按钮操作 */
            handleView(row) {
                this.answersheetViewVisible = true;
                this.$nextTick(() => {
                    this.$refs.answersheetView.init(row)
                })
            }
        }
    };
</script>
