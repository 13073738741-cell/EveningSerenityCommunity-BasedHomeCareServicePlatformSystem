import request from '../utils/request'

// 获取老人的环境评估列表
export const getEnvironmentAssessmentList = (elderlyId, params) => {
  return request({
    url: `/environment-assessments/elderly/${elderlyId}`,
    method: 'get',
    params
  })
}

// 根据ID获取环境评估详情
export const getEnvironmentAssessmentById = (id) => {
  return request({
    url: `/environment-assessments/${id}`,
    method: 'get'
  })
}

// 创建环境评估
export const createEnvironmentAssessment = (data) => {
  return request({
    url: '/environment-assessments',
    method: 'post',
    data
  })
}

// 更新环境评估
export const updateEnvironmentAssessment = (id, data) => {
  return request({
    url: `/environment-assessments/${id}`,
    method: 'put',
    data
  })
}

// 删除环境评估
export const deleteEnvironmentAssessment = (id) => {
  return request({
    url: `/environment-assessments/${id}`,
    method: 'delete'
  })
}
