<template>
    <el-dialog
        title="答卷详情"
        :close-on-click-modal="true"
        :visible.sync="visible">
        <h1 class="wj-title">体检问卷</h1>
        <div class="user-answer-info">
            <span>答卷人：{{ userName ? userName : '--' }}</span>
            <span class="info-interval">答卷时间：{{ createTime }}</span>
        </div>

        <div v-for="(item, index) in dataList">
            <div
                :class=" item.requiredFlag == '0' ? 'el-form-item el-form-item--medium item-left is-required dashed' : 'el-form-item el-form-item--medium item-left dashed'">
                <label class="el-form-item__label question-title">{{ (index + 1) + '、' + item.questionTitle }}
                    <span class="qtypetip">&nbsp;{{ labelQuestionType(item) }}</span>
                </label>
                <div class="el-form-item__content">
                    <div class="option_style" v-if="item.questionType == 0"
                         v-for="answersItem in item.questionnaireAnswersVOList">
                        <el-radio v-model="item.questionnaireAnswersIdList[0]"
                                  :disabled="!item.questionnaireAnswersIdList.includes(answersItem.id)"
                                  :label="answersItem.id">{{ answersItem.questionnaireOption }}
                        </el-radio>
                    </div>

                    <div class="option_style" v-if="item.questionType == 1"
                         v-for="answersItem in item.questionnaireAnswersVOList">
                        <el-checkbox v-model="item.questionnaireAnswersIdList"
                                     :disabled="!item.questionnaireAnswersIdList.includes(answersItem.id)"
                                     :label="answersItem.id">{{ answersItem.questionnaireOption }}
                        </el-checkbox>
                    </div>
                </div>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="visible = false">取消</el-button>
          <el-button type="primary" @click="visible = false">确定</el-button>
        </span>
    </el-dialog>
</template>

<script>

import {getAnswersheet} from "@/api/system/questionnaire/answer";

export default {
    name: "AnswerSheetView",
    data() {
        return {
            visible: false,
            dataList: {},
            userName: "",
            createTime: "",
            labelQuestionType: item => {
                let questionType = item.questionType == "0" ? "单选题" : "多选题";
                return "【" + questionType + "】"
            }
        };
    },
    methods: {
        init(row) {
            this.userName = row.userName;
            this.createTime = row.createTime;
            this.visible = true;
            this.$nextTick(() => {
                getAnswersheet(row.id).then(({data}) => {
                    this.dataList = data;
                });
            });
        }
    }
};
</script>
<style>
.option_style {
    padding-left: 25px;
}

.wj-title {
    font-size: 24px !important;
    font-weight: bold;
    color: #0095ff;
    vertical-align: middle;
    margin: 0;
    padding: 15px 0;
    line-height: 30px;
    text-align: center;
}

.user-answer-info {
    text-align: center;
}

.info-interval {
    padding-left: 20px;
}

.item-left {
    padding-left: 60px;
    margin-top: 10px;
}

.qtypetip {
    color: #999;
    font-weight: normal;
    font-size: 14px;
}

.question-title {
    float: none;
    display: inline-block;
    text-align: left;
    padding: 0 0 10px 0;
}

.dashed {
    border: 2px dashed #eee;
}

.el-dialog:not(.is-fullscreen) {
    margin-bottom: 6vh;
}
</style>
