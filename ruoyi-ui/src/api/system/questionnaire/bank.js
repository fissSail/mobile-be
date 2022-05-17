import request from '@/utils/request'

// 查询问卷问题列表
export function listBank(query) {
  return request({
    url: '/questionnaire/bank/list',
    method: 'get',
    params: query
  })
}

// 查询问卷问题详细
export function getBank(id) {
  return request({
    url: '/questionnaire/bank/' + id,
    method: 'get'
  })
}

// 新增问卷问题
export function addBank(data) {
  return request({
    url: '/questionnaire/bank',
    method: 'post',
    data: data
  })
}

// 修改问卷问题
export function updateBank(data) {
  return request({
    url: '/questionnaire/bank',
    method: 'put',
    data: data
  })
}

// 删除问卷问题
export function delBank(id) {
  return request({
    url: '/questionnaire/bank/' + id,
    method: 'delete'
  })
}
