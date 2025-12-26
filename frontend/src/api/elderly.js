import request from '../utils/request'

// 获取所有老人档案
export const getElderlyList = (params) => {
  return request({
    url: '/elderly',
    method: 'get',
    params
  })
}

// 根据ID获取老人档案
export const getElderlyById = (id) => {
  return request({
    url: `/elderly/${id}`,
    method: 'get'
  })
}

// 创建老人档案
export const createElderly = (data) => {
  return request({
    url: '/elderly',
    method: 'post',
    data
  })
}

// 更新老人档案
export const updateElderly = (id, data) => {
  return request({
    url: `/elderly/${id}`,
    method: 'put',
    data
  })
}

// 删除老人档案
export const deleteElderly = (id) => {
  return request({
    url: `/elderly/${id}`,
    method: 'delete'
  })
}

// 搜索老人档案
export const searchElderly = (keyword) => {
  return request({
    url: '/elderly/search',
    method: 'get',
    params: { keyword }
  })
}
