import request from '../utils/request'

// 登录
export const login = (data) => {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

// 获取当前用户信息
export const getCurrentUser = () => {
  return request({
    url: '/auth/current',
    method: 'get'
  })
}
