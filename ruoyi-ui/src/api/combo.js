import request from '@/utils/request'


//获取pc端套餐
export function setMealList(params) {
  return request({
    url: 'examinePackage/optionList',
    headers: {
      isToken: true
    },
    method: 'get',
    params
  })
}
//获取移动端套餐
export function getMobileList(params) {
  return request({
    url: 'setMeal/list',
    headers: {
      isToken: true
    },
    method: 'get',
    params
  })
}



//删除套餐
export function deleteCombo(setMealIds) {
  return request({
    url: 'setMeal/delete',
    headers: {
      isToken: true
    },
    method: 'post',
    data:{
      setMealIds
    }
  })
}

//更新套餐
export function updateCombo(data) {
  return request({
    url: 'setMeal/updateCombo',
    headers: {
      isToken: true
    },
    method: 'post',
    data
  })
}

//套餐缩略图
export function upload(data) {
  return request({
    url: '/common/upload',
    headers: {
      isToken: true,
      'Content-Type':'multipart/form-data'
    },
    method: 'post',
    data

  })
}

//套餐轮播图
export function uploads(data) {
  return request({
    url: '/common/uploads',
    headers: {
      isToken: true,
      'Content-Type':'multipart/form-data'
    },
    method: 'post',
    data

  })
}


//获取便签集合
export function getTagList(data) {
  return request({
    url: '/tag/list',
    headers: {
      isToken: true
    },
    method: 'post',
    data

  })
}

//
export function updateAndAddTag(data) {
  return request({
    url: '/tag/updateAndAdd',
    headers: {
      isToken: true
    },
    method: 'post',
    data

  })
}



