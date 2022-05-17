import request from '@/utils/request'

// 查询问卷问题列表
export function listTips(query) {
  return request({
    url: '/health/tips/list',
    method: 'get',
    params: query
  })
}

// 查询问卷问题详细
export function getTips(id) {
  return request({
    url: '/health/tips/' + id,
    method: 'get'
  })
}

// 新增问卷问题
export function addTips(data) {
  return request({
    url: '/health/tips',
    method: 'post',
    data: data
  })
}

// 修改问卷问题
export function updateTips(data) {
  return request({
    url: '/health/tips',
    method: 'put',
    data: data
  })
}

// 删除问卷问题
export function delTips(id) {
  return request({
    url: '/health/tips/' + id,
    method: 'delete'
  })
}
