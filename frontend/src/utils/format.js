/**
 * 格式化日期为 YYYY-MM-DD 格式
 * @param {Date|string} date - 日期对象或日期字符串
 * @returns {string} 格式化后的日期字符串
 */
export const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
}

/**
 * 格式化日期时间为 YYYY-MM-DD HH:mm 格式
 * @param {Date|string} date - 日期对象或日期字符串
 * @returns {string} 格式化后的日期时间字符串
 */
export const formatDateTime = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
}

/**
 * 格式化日期时间为 YYYY-MM-DD HH:mm:ss 格式
 * @param {Date|string} date - 日期对象或日期字符串
 * @returns {string} 格式化后的日期时间字符串
 */
export const formatDateTimeWithSeconds = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`
}

/**
 * 获取标签类型
 * @param {string} result - 结果值
 * @param {Object} typeMap - 类型映射表
 * @returns {string} 标签类型
 */
export const getTagType = (result, typeMap = {}) => {
  return typeMap[result] || 'info'
}

/**
 * 获取文本映射
 * @param {string} value - 值
 * @param {Object} textMap - 文本映射表
 * @returns {string} 映射后的文本
 */
export const getTextMapping = (value, textMap = {}) => {
  return textMap[value] || value
}

/**
 * 将字符串转换为数组（按逗号分隔）
 * @param {string} str - 字符串
 * @returns {Array} 数组
 */
export const stringToArray = (str) => {
  if (!str) return []
  if (Array.isArray(str)) return str
  return str.split(',').map(d => d.trim()).filter(d => d)
}

/**
 * 计算年龄
 * @param {Date|string} birthDate - 出生日期
 * @returns {number} 年龄
 */
export const calculateAge = (birthDate) => {
  if (!birthDate) return 0
  const birth = new Date(birthDate)
  const today = new Date()
  let age = today.getFullYear() - birth.getFullYear()
  const monthDiff = today.getMonth() - birth.getMonth()
  if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
    age--
  }
  return age
}
