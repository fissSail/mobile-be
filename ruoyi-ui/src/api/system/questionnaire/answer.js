import request from '@/utils/request'

// 查询问卷答卷列表
export function listAnswersheet(query) {
    return request({
        url: '/questionnaire/answersheet/list',
        method: 'get',
        params: query
    })
}

// 查询问卷问题答卷详细
export function getAnswersheet(id) {
    return request({
        url: '/questionnaire/answersheet/' + id,
        method: 'get'
    })
}
