<template>
  <div class="care-record">
    <el-page-header @back="goBack" :content="`关爱档案 - ${elderlyName}`" />
    
    <el-card class="record-card" style="margin-top: 20px">
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="关爱记录列表" name="list">
          <div class="toolbar">
            <el-button type="primary" @click="showAddDialog">
              <el-icon><Plus /></el-icon>
              新增关爱记录
            </el-button>
            <el-input
              v-model="searchKeyword"
              placeholder="搜索记录"
              style="width: 300px; margin-left: 20px"
              clearable
            >
              <template #prefix>
                <el-icon><Search /></el-icon>
              </template>
            </el-input>
          </div>

          <el-table :data="filteredRecords" stripe style="width: 100%; margin-top: 20px">
            <el-table-column prop="date" label="日期" width="120" />
            <el-table-column prop="type" label="关爱类型" width="120">
              <template #default="{ row }">
                <el-tag :type="getTypeTagType(row.type)">{{ getTypeLabel(row.type) }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="content" label="关爱内容" min-width="200" show-overflow-tooltip />
            <el-table-column prop="caregiver" label="关爱人员" width="120" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="row.status === 'completed' ? 'success' : 'warning'">
                  {{ row.status === 'completed' ? '已完成' : '进行中' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200" fixed="right">
              <template #default="{ row }">
                <el-button link type="primary" @click="viewRecord(row)">查看</el-button>
                <el-button link type="primary" @click="editRecord(row)">编辑</el-button>
                <el-button link type="danger" @click="deleteRecord(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[10, 20, 50, 100]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            style="margin-top: 20px; justify-content: flex-end"
          />
        </el-tab-pane>

        <el-tab-pane label="关爱统计" name="statistics">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-card class="stat-card">
                <el-statistic title="总关爱次数" :value="statistics.total" />
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card class="stat-card">
                <el-statistic title="本月关爱次数" :value="statistics.thisMonth" />
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card class="stat-card">
                <el-statistic title="完成率" :value="statistics.completionRate" suffix="%" />
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card class="stat-card">
                <el-statistic title="待处理" :value="statistics.pending" />
              </el-card>
            </el-col>
          </el-row>

          <el-card style="margin-top: 20px">
            <template #header>
              <div class="card-header">
                <span>关爱类型分布</span>
              </div>
            </template>
            <div class="type-distribution">
              <div v-for="(count, type) in typeDistribution" :key="type" class="type-item">
                <el-tag :type="getTypeTagType(type)">{{ getTypeLabel(type) }}</el-tag>
                <span class="count">{{ count }} 次</span>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑关爱记录' : '新增关爱记录'"
      width="700px"
    >
      <el-form :model="recordForm" :rules="recordRules" ref="recordFormRef" label-width="120px">
        <el-form-item label="日期" prop="date">
          <el-date-picker
            v-model="recordForm.date"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        
        <el-form-item label="关爱类型" prop="type">
          <el-select v-model="recordForm.type" placeholder="请选择关爱类型" style="width: 100%">
            <el-option label="电话慰问" value="phone" />
            <el-option label="上门探访" value="visit" />
            <el-option label="生活照料" value="daily_care" />
            <el-option label="医疗护理" value="medical_care" />
            <el-option label="心理疏导" value="psychological" />
            <el-option label="节日慰问" value="holiday" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="关爱人员" prop="caregiver">
          <el-input v-model="recordForm.caregiver" placeholder="请输入关爱人员姓名" />
        </el-form-item>
        
        <el-form-item label="关爱内容" prop="content">
          <el-input
            v-model="recordForm.content"
            type="textarea"
            :rows="4"
            placeholder="请详细描述关爱内容"
          />
        </el-form-item>
        
        <el-form-item label="关爱时长" prop="duration">
          <el-input v-model="recordForm.duration" placeholder="请输入关爱时长（分钟）">
            <template #append>分钟</template>
          </el-input>
        </el-form-item>
        
        <el-form-item label="老人反馈" prop="feedback">
          <el-input
            v-model="recordForm.feedback"
            type="textarea"
            :rows="3"
            placeholder="请记录老人的反馈"
          />
        </el-form-item>
        
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="recordForm.status">
            <el-radio label="completed">已完成</el-radio>
            <el-radio label="pending">进行中</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="备注" prop="notes">
          <el-input
            v-model="recordForm.notes"
            type="textarea"
            :rows="2"
            placeholder="请输入备注信息"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave" :loading="saving">保存</el-button>
      </template>
    </el-dialog>

    <!-- 查看对话框 -->
    <el-dialog v-model="viewDialogVisible" title="关爱记录详情" width="700px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="日期">{{ viewRecord.date }}</el-descriptions-item>
        <el-descriptions-item label="关爱类型">
          <el-tag :type="getTypeTagType(viewRecord.type)">{{ getTypeLabel(viewRecord.type) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="关爱人员">{{ viewRecord.caregiver }}</el-descriptions-item>
        <el-descriptions-item label="关爱内容">{{ viewRecord.content }}</el-descriptions-item>
        <el-descriptions-item label="关爱时长">{{ viewRecord.duration }} 分钟</el-descriptions-item>
        <el-descriptions-item label="老人反馈">{{ viewRecord.feedback }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="viewRecord.status === 'completed' ? 'success' : 'warning'">
            {{ viewRecord.status === 'completed' ? '已完成' : '进行中' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="备注">{{ viewRecord.notes }}</el-descriptions-item>
      </el-descriptions>
      
      <template #footer>
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search } from '@element-plus/icons-vue'
import { getElderlyById } from '../api/elderly'
import { getCareRecordList, createCareRecord, updateCareRecord, deleteCareRecord } from '../api/careRecord'

const router = useRouter()
const route = useRoute()
const recordFormRef = ref(null)
const activeTab = ref('list')
const dialogVisible = ref(false)
const viewDialogVisible = ref(false)
const isEdit = ref(false)
const saving = ref(false)
const searchKeyword = ref('')
const elderlyName = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const recordForm = reactive({
  id: null,
  elderlyId: route.params.elderlyId,
  date: new Date(),
  type: 'phone',
  caregiver: '',
  content: '',
  duration: '',
  feedback: '',
  status: 'completed',
  notes: ''
})

const recordRules = {
  date: [{ required: true, message: '请选择日期', trigger: 'change' }],
  type: [{ required: true, message: '请选择关爱类型', trigger: 'change' }],
  caregiver: [{ required: true, message: '请输入关爱人员姓名', trigger: 'blur' }],
  content: [{ required: true, message: '请输入关爱内容', trigger: 'blur' }]
}

const viewRecord = ref({})
const records = ref([])

const statistics = reactive({
  total: 0,
  thisMonth: 0,
  completionRate: 0,
  pending: 0
})

const typeDistribution = computed(() => {
  const distribution = {}
  records.value.forEach(record => {
    distribution[record.type] = (distribution[record.type] || 0) + 1
  })
  return distribution
})

const filteredRecords = computed(() => {
  if (!searchKeyword.value) {
    return records.value
  }
  const keyword = searchKeyword.value.toLowerCase()
  return records.value.filter(record =>
    record.content.toLowerCase().includes(keyword) ||
    record.caregiver.toLowerCase().includes(keyword)
  )
})

const getTypeLabel = (type) => {
  const typeMap = {
    phone: '电话慰问',
    visit: '上门探访',
    daily_care: '生活照料',
    medical_care: '医疗护理',
    psychological: '心理疏导',
    holiday: '节日慰问',
    other: '其他'
  }
  return typeMap[type] || type
}

const getTypeTagType = (type) => {
  const typeMap = {
    phone: 'info',
    visit: 'success',
    daily_care: 'warning',
    medical_care: 'danger',
    psychological: 'primary',
    holiday: 'success',
    other: 'info'
  }
  return typeMap[type] || 'info'
}

const loadElderlyInfo = async () => {
  try {
    const response = await getElderlyById(route.params.elderlyId)
    elderlyName.value = response.data?.name || response.name || ''
  } catch (error) {
    ElMessage.error('加载老人信息失败')
  }
}

const loadRecords = async () => {
  try {
    const response = await getCareRecordList(route.params.elderlyId, {
      page: currentPage.value,
      pageSize: pageSize.value
    })
    records.value = response.data || response
    total.value = response.total || records.value.length
    updateStatistics()
  } catch (error) {
    ElMessage.error('加载关爱记录失败')
  }
}

const updateStatistics = () => {
  statistics.total = records.value.length
  const thisMonth = new Date().getMonth()
  statistics.thisMonth = records.value.filter(record => {
    const recordDate = new Date(record.date)
    return recordDate.getMonth() === thisMonth
  }).length
  
  const completedCount = records.value.filter(record => record.status === 'completed').length
  statistics.completionRate = statistics.total > 0 
    ? Math.round((completedCount / statistics.total) * 100) 
    : 0
  statistics.pending = records.value.filter(record => record.status === 'pending').length
}

const showAddDialog = () => {
  isEdit.value = false
  Object.assign(recordForm, {
    id: null,
    elderlyId: route.params.elderlyId,
    date: new Date(),
    type: 'phone',
    caregiver: '',
    content: '',
    duration: '',
    feedback: '',
    status: 'completed',
    notes: ''
  })
  dialogVisible.value = true
}

const editRecord = (row) => {
  isEdit.value = true
  Object.assign(recordForm, row)
  dialogVisible.value = true
}

const viewRecordDetail = (row) => {
  viewRecord.value = row
  viewDialogVisible.value = true
}

const handleSave = async () => {
  if (!recordFormRef.value) return
  
  await recordFormRef.value.validate(async (valid) => {
    if (valid) {
      saving.value = true
      try {
        if (isEdit.value) {
          await updateCareRecord(recordForm.id, recordForm)
        } else {
          await createCareRecord(recordForm)
        }
        ElMessage.success(isEdit.value ? '更新成功' : '新增成功')
        dialogVisible.value = false
        loadRecords()
      } catch (error) {
        ElMessage.error('保存失败')
      } finally {
        saving.value = false
      }
    }
  })
}

const deleteRecord = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这条关爱记录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteCareRecord(row.id)
    ElMessage.success('删除成功')
    loadRecords()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const goBack = () => {
  router.back()
}

onMounted(() => {
  loadElderlyInfo()
  loadRecords()
})
</script>

<style scoped>
.care-record {
  padding: 20px;
}

.record-card {
  background: white;
}

.toolbar {
  display: flex;
  align-items: center;
}

.stat-card {
  text-align: center;
}

.type-distribution {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.type-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.type-item .count {
  font-weight: bold;
  color: #409eff;
}
</style>
