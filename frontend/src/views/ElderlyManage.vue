<template>
  <div class="elderly-manage">
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="搜索">
          <el-input
            v-model="searchForm.keyword"
            placeholder="请输入姓名或身份证号"
            clearable
            @clear="handleSearch"
          >
            <template #append>
              <el-button :icon="Search" @click="handleSearch">搜索</el-button>
            </template>
          </el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" :icon="Plus" @click="handleAdd">新增老人档案</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="table-card">
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="{ row }">
            {{ row.gender === 'male' ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column label="年龄" width="80">
          <template #default="{ row }">
            {{ calculateAge(row.birthDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="idCard" label="身份证号" width="180" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="address" label="住址" min-width="200" />
        <el-table-column prop="healthStatus" label="健康状况" width="150">
          <template #default="{ row }">
            <div>
              <el-tag :type="getHealthStatusType(row.healthStatus)" style="margin-bottom: 5px">
                {{ row.healthStatus }}
              </el-tag>
              <div v-if="elderlyAdlMap.has(row.id)" style="font-size: 12px; color: #606266">
                ADL: <span style="font-weight: bold; color: #409EFF">{{ elderlyAdlMap.get(row.id) }}分</span>
              </div>
              <div v-else style="font-size: 12px; color: #909399">
                未评估
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="careLevel" label="护理等级" width="100">
          <template #default="{ row }">
            <el-tag :type="getCareLevelType(row.careLevel)">
              {{ row.careLevel }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="350" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button type="warning" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
            <el-dropdown @command="(command) => handleDropdownCommand(command, row)">
              <el-button type="info" size="small">
                更多<el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="health">健康能力评估</el-dropdown-item>
                  <el-dropdown-item command="environment">家庭环境评估</el-dropdown-item>
                  <el-dropdown-item command="care">建立关爱档案</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
    
    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="800px"
      @close="handleDialogClose"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="male" />
                <el-option label="女" value="female" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="form.idCard" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birthDate">
              <el-date-picker
                v-model="form.birthDate"
                type="date"
                placeholder="选择出生日期"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="健康状况" prop="healthStatus">
              <el-select v-model="form.healthStatus" placeholder="请选择健康状况" style="width: 100%">
                <el-option label="健康" value="健康" />
                <el-option label="良好" value="良好" />
                <el-option label="一般" value="一般" />
                <el-option label="较差" value="较差" />
                <el-option label="差" value="差" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="护理等级" prop="careLevel">
              <el-select v-model="form.careLevel" placeholder="请选择护理等级" style="width: 100%">
                <el-option label="一级护理" value="一级护理" />
                <el-option label="二级护理" value="二级护理" />
                <el-option label="三级护理" value="三级护理" />
                <el-option label="四级护理" value="四级护理" />
                <el-option label="五级护理" value="五级护理" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="紧急联系人" prop="emergencyContact">
              <el-input v-model="form.emergencyContact" placeholder="请输入紧急联系人" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="住址" prop="address">
          <el-input v-model="form.address" type="textarea" :rows="3" placeholder="请输入住址" />
        </el-form-item>
        
        <el-form-item label="备注" prop="notes">
          <el-input v-model="form.notes" type="textarea" :rows="3" placeholder="请输入备注信息" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Plus, ArrowDown } from '@element-plus/icons-vue'
import { getElderlyList, createElderly, updateElderly, deleteElderly } from '../api/elderly'
import { getHealthAssessmentList } from '../api/healthAssessment'

const router = useRouter()

const loading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('新增老人档案')
const formRef = ref(null)

const searchForm = reactive({
  keyword: ''
})

const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

const tableData = ref([])
const elderlyAdlMap = ref(new Map()) // 存储每个老人的最新ADL分数
const form = reactive({
  id: null,
  name: '',
  gender: '',
  idCard: '',
  birthDate: '',
  phone: '',
  healthStatus: '',
  careLevel: '',
  emergencyContact: '',
  address: '',
  notes: ''
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  idCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { pattern: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入正确的身份证号', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  healthStatus: [{ required: true, message: '请选择健康状况', trigger: 'change' }],
  careLevel: [{ required: true, message: '请选择护理等级', trigger: 'change' }],
  address: [{ required: true, message: '请输入住址', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      size: pagination.size,
      keyword: searchForm.keyword
    }
    const response = await getElderlyList(params)
    tableData.value = response.data || []
    pagination.total = response.total || 0
    
    // 加载所有老人的最新ADL分数
    await loadAllAdlScores()
  } catch (error) {
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

// 加载所有老人的最新ADL分数
const loadAllAdlScores = async () => {
  try {
    elderlyAdlMap.value.clear()
    
    // 为每个老人获取最新的健康评估记录
    for (const elderly of tableData.value) {
      try {
        const response = await getHealthAssessmentList(elderly.id, { page: 1, pageSize: 1 })
        console.log(`老人 ${elderly.name} (ID: ${elderly.id}) 的评估数据:`, response)
        
        // 处理响应数据
        let assessments = response.data || response
        
        if (Array.isArray(assessments) && assessments.length > 0) {
          // 获取最新的评估记录并计算ADL总分
          const latestAssessment = assessments[0]
          console.log(`最新评估记录:`, latestAssessment)
          
          const adlTotal = (latestAssessment.eating || 0) +
                          (latestAssessment.bathing || 0) +
                          (latestAssessment.grooming || 0) +
                          (latestAssessment.dressing || 0) +
                          (latestAssessment.bowelControl || 0) +
                          (latestAssessment.bladderControl || 0) +
                          (latestAssessment.toilet || 0) +
                          (latestAssessment.transfer || 0) +
                          (latestAssessment.walking || 0) +
                          (latestAssessment.stairs || 0)
          
          console.log(`计算得到的ADL总分:`, adlTotal)
          elderlyAdlMap.value.set(elderly.id, adlTotal)
        } else {
          console.log(`老人 ${elderly.name} 没有评估记录`)
        }
      } catch (error) {
        // 如果某个老人没有评估记录，忽略错误
        console.error(`加载老人 ${elderly.name} 的ADL分数失败`, error)
      }
    }
    
    console.log('所有老人的ADL分数Map:', elderlyAdlMap.value)
  } catch (error) {
    console.error('加载ADL分数失败', error)
  }
}

const handleSearch = () => {
  pagination.page = 1
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '新增老人档案'
  resetForm()
  dialogVisible.value = true
}

const handleView = (row) => {
  ElMessage.info('查看功能待实现')
}

const handleDropdownCommand = (command, row) => {
  switch (command) {
    case 'health':
      router.push({ name: 'HealthAssessment', params: { elderlyId: row.id } })
      break
    case 'environment':
      router.push({ name: 'EnvironmentAssessment', params: { elderlyId: row.id } })
      break
    case 'care':
      router.push({ name: 'CareRecord', params: { elderlyId: row.id } })
      break
  }
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑老人档案'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该老人档案吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteElderly(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) {
          await updateElderly(form.id, form)
          ElMessage.success('更新成功')
        } else {
          await createElderly(form)
          ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        loadData()
      } catch (error) {
        ElMessage.error('操作失败')
      }
    }
  })
}

const handleDialogClose = () => {
  resetForm()
}

const resetForm = () => {
  form.id = null
  form.name = ''
  form.gender = ''
  form.idCard = ''
  form.birthDate = ''
  form.phone = ''
  form.healthStatus = ''
  form.careLevel = ''
  form.emergencyContact = ''
  form.address = ''
  form.notes = ''
  formRef.value?.clearValidate()
}

const handleSizeChange = (val) => {
  pagination.size = val
  loadData()
}

const handleCurrentChange = (val) => {
  pagination.page = val
  loadData()
}

const getHealthStatusType = (status) => {
  const map = {
    '健康': 'success',
    '良好': 'success',
    '一般': 'warning',
    '较差': 'danger',
    '差': 'danger'
  }
  return map[status] || 'info'
}

const getCareLevelType = (level) => {
  const map = {
    '一级护理': 'success',
    '二级护理': 'primary',
    '三级护理': 'warning',
    '四级护理': 'danger',
    '五级护理': 'danger'
  }
  return map[level] || 'info'
}

const calculateAge = (birthDate) => {
  if (!birthDate) return '-'
  
  const birth = new Date(birthDate)
  const today = new Date()
  
  let age = today.getFullYear() - birth.getFullYear()
  const monthDiff = today.getMonth() - birth.getMonth()
  
  if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
    age--
  }
  
  return age
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.elderly-manage {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.table-card {
  background: white;
}

:deep(.el-pagination) {
  display: flex;
}
</style>
