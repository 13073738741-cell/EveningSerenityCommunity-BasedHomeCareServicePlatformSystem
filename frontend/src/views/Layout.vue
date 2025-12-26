<template>
  <el-container class="layout-container">
    <el-aside width="250px" class="aside">
      <div class="logo">
        <h2>桑榆晚情</h2>
        <p>社区居家养老服务平台</p>
      </div>
      
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical"
        router
      >
        <el-sub-menu index="elderly">
          <template #title>
            <el-icon><User /></el-icon>
            <span>老人档案管理</span>
          </template>
          <el-menu-item index="/elderly-manage">
            <el-icon><User /></el-icon>
            <span>老人档案列表</span>
          </el-menu-item>
          <el-menu-item index="/health-assessment-manage">
            <el-icon><Document /></el-icon>
            <span>健康能力档案管理</span>
          </el-menu-item>
          <el-menu-item index="/environment-assessment-manage">
            <el-icon><Document /></el-icon>
            <span>家庭环境档案管理</span>
          </el-menu-item>
          <el-menu-item index="/care-record-manage">
            <el-icon><Document /></el-icon>
            <span>关爱档案管理</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <h3>晚霞社区居家养老服务平台</h3>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><Avatar /></el-icon>
              {{ username }}
              <el-icon class="el-icon--right"><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
    
    <!-- 个人信息对话框 -->
    <el-dialog
      v-model="profileDialogVisible"
      title="个人信息"
      width="500px"
    >
      <el-form :model="profileForm" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="profileForm.username" disabled />
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="profileForm.realName" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="角色">
          <el-input v-model="profileForm.role" disabled />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="profileDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSaveProfile" :loading="profileLoading">保存</el-button>
      </template>
    </el-dialog>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserInfo, updateRealName } from '../api/auth'

const router = useRouter()
const route = useRoute()

const username = ref('')
const profileDialogVisible = ref(false)
const profileLoading = ref(false)
const profileForm = ref({
  username: '',
  realName: '',
  role: ''
})

const activeMenu = computed(() => route.path)

onMounted(() => {
  // 优先显示真实姓名，如果没有则显示用户名
  const realName = localStorage.getItem('realName')
  const usernameFromStorage = localStorage.getItem('username')
  username.value = realName || usernameFromStorage || '管理员'
})

const handleCommand = async (command) => {
  if (command === 'profile') {
    await loadUserInfo()
    profileDialogVisible.value = true
  } else if (command === 'logout') {
    try {
      await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      
      localStorage.removeItem('token')
      localStorage.removeItem('username')
      localStorage.removeItem('role')
      
      ElMessage.success('已退出登录')
      router.push('/login')
    } catch (error) {
      // 用户取消操作
    }
  }
}

const loadUserInfo = async () => {
  try {
    const response = await getUserInfo()
    if (response.success) {
      profileForm.value = {
        username: response.username,
        realName: response.realName || '',
        role: response.role
      }
    } else {
      ElMessage.error(response.message || '获取用户信息失败')
    }
  } catch (error) {
    ElMessage.error('获取用户信息失败')
  }
}

const handleSaveProfile = async () => {
  if (!profileForm.value.realName || profileForm.value.realName.trim() === '') {
    ElMessage.warning('请输入真实姓名')
    return
  }
  
  profileLoading.value = true
  try {
    const response = await updateRealName(profileForm.value.realName)
    if (response.success) {
      // 更新localStorage中的realName
      localStorage.setItem('realName', response.realName)
      // 更新显示的用户名
      username.value = response.realName || profileForm.value.username
      
      ElMessage.success('保存成功')
      profileDialogVisible.value = false
    } else {
      ElMessage.error(response.message || '保存失败')
    }
  } catch (error) {
    ElMessage.error('保存失败')
  } finally {
    profileLoading.value = false
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.aside {
  background: linear-gradient(180deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.logo {
  padding: 30px 20px;
  text-align: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.logo h2 {
  font-size: 24px;
  margin-bottom: 5px;
}

.logo p {
  font-size: 12px;
  opacity: 0.8;
}

.el-menu-vertical {
  border: none;
  background: transparent;
}

:deep(.el-menu-item) {
  color: rgba(255, 255, 255, 0.8);
  margin: 5px 10px;
  border-radius: 8px;
}

:deep(.el-menu-item:hover) {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

:deep(.el-menu-item.is-active) {
  background: rgba(255, 255, 255, 0.2);
  color: white;
}

:deep(.el-sub-menu) {
  margin: 5px 10px;
  border-radius: 8px;
}

:deep(.el-sub-menu__title) {
  color: rgba(255, 255, 255, 0.8);
  border-radius: 8px;
}

:deep(.el-sub-menu__title:hover) {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

:deep(.el-sub-menu.is-opened > .el-sub-menu__title) {
  color: white;
  background: rgba(255, 255, 255, 0.15);
}

:deep(.el-menu--inline) {
  background: rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  margin: 5px 10px;
  padding: 5px 0;
}

:deep(.el-menu--inline .el-menu-item) {
  margin: 3px 15px;
  min-height: 40px;
  line-height: 40px;
}

.header {
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
}

.header-left h3 {
  color: #333;
  font-size: 18px;
  font-weight: 500;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #666;
  font-size: 14px;
}

.user-info .el-icon {
  margin: 0 5px;
}

.main {
  background: #f5f5f5;
  padding: 20px;
}
</style>





