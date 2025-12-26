import request from '../utils/request'

// 获取所有关爱档案列表
export const getAllCareRecords = (params) => {
  return request({
    url: '/care-records',
    method: 'get',
    params
  })
}

// 获取老人的关爱记录列表
export const getCareRecordList = (elderlyId, params) => {
  return request({
    url: `/care-records/elderly/${elderlyId}`,
    method: 'get',
    params
  })
}

// 根据ID获取关爱记录详情
export const getCareRecordById = (id) => {
  return request({
    url: `/care-records/${id}`,
    method: 'get'
  })
}

// 根据关爱类型获取关爱档案列表
export const getCareRecordsByCareType = (careType, params) => {
  return request({
    url: `/care-records/care-type/${careType}`,
    method: 'get',
    params
  })
}

// 根据护理员姓名获取关爱档案列表
export const getCareRecordsByCaregiverName = (caregiverName, params) => {
  return request({
    url: `/care-records/caregiver/${caregiverName}`,
    method: 'get',
    params
  })
}

// 创建关爱记录
export const createCareRecord = (data) => {
  return request({
    url: '/care-records',
    method: 'post',
    data
  })
}

// 更新关爱记录
export const updateCareRecord = (id, data) => {
  return request({
    url: `/care-records/${id}`,
    method: 'put',
    data
  })
}

// 删除关爱记录
export const deleteCareRecord = (id) => {
  return request({
    url: `/care-records/${id}`,
    method: 'delete'
  })
}
