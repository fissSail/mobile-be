import request from '@/utils/request'

// 查询问卷选项列表
export function listSetMeal(query) {
    return request({
        url: '/setMeal/list',
        method: 'get',
        params: query
    })
}
