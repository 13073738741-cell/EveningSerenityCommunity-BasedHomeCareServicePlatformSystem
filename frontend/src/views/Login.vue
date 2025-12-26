<template>
  <div class="login-container">
    <div class="login-box">
      <div class="logo">🏥</div>
      <h1>桑榆晚情社区居家养老服务平台</h1>
      <p class="subtitle">Evening Serenity Community Home Care Service Platform</p>
      
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" @submit.prevent="handleLogin">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
          />
        </el-form-item>
        
        <el-form-item>
          <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            :loading="loading"
            @click="handleLogin"
            style="width: 100%"
          >
            登 录
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="footer">
        <p>© 2025 桑榆晚情社区居家养老服务平台</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api/auth'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: '',
  remember: false
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      
      try {
        const response = await login({
          username: loginForm.username,
          password: loginForm.password
        })
        
        if (response.success) {
          // 保存token和用户信息
          localStorage.setItem('token', response.token)
          localStorage.setItem('username', response.username)
          localStorage.setItem('role', response.role)
          
          ElMessage.success('登录成功')
          router.push('/')
        } else {
          ElMessage.error(response.message || '登录失败')
        }
      } catch (error) {
        ElMessage.error('登录失败，请检查网络连接')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.login-box {
  background: white;
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  padding: 50px 40px;
  max-width: 450px;
  width: 100%;
  text-align: center;
}

.logo {
  font-size: 60px;
  margin-bottom: 20px;
}

h1 {
  color: #333;
  font-size: 28px;
  margin-bottom: 5px;
}

.subtitle {
  color: #999;
  font-size: 14px;
  margin-bottom: 40px;
}

.footer {
  margin-top: 30px;
  color: #999;
  font-size: 12px;
}

:deep(.el-form-item) {
  margin-bottom: 25px;
}

:deep(.el-input__wrapper) {
  border-radius: 10px;
  padding: 14px 16px;
}

:deep(.el-button--large) {
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 10px;
}
</style>
