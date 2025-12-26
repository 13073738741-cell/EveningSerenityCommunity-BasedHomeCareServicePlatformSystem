import request from '../utils/request'

// 获取所有健康评估列表
export const getAllHealthAssessments = (params) => {
  return request({
    url: '/health-assessments',
    method: 'get',
    params
  })
}

// 获取老人的健康评估列表
export const getHealthAssessmentList = (elderlyId, params) => {
  return request({
    url: `/health-assessments/elderly/${elderlyId}`,
    method: 'get',
    params
  })
}

// 根据ID获取健康评估详情
export const getHealthAssessmentById = (id) => {
  return request({
    url: `/health-assessments/${id}`,
    method: 'get'
  })
}

// 创建健康评估
export const createHealthAssessment = (data) => {
  return request({
    url: '/health-assessments',
    method: 'post',
    data
  })
}

// 更新健康评估
export const updateHealthAssessment = (id, data) => {
  return request({
    url: `/health-assessments/${id}`,
    method: 'put',
    data
  })
}

// 删除健康评估
export const deleteHealthAssessment = (id) => {
  return request({
    url: `/health-assessments/${id}`,
    method: 'delete'
  })
}
