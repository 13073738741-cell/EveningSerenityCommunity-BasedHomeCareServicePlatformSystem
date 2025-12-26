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

// 获取用户详细信息
export const getUserInfo = () => {
  return request({
    url: '/auth/user',
    method: 'get'
  })
}

// 更新真实姓名
export const updateRealName = (realName) => {
  return request({
    url: '/auth/user/realname',
    method: 'put',
    data: { realName }
  })
}