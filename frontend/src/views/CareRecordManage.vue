<template>
  <div class="care-record-manage">
    <el-page-header @back="goBack" :content="pageTitle" />
    
    <el-card class="statistics-card" style="margin-top: 20px">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-statistic title="总关爱次数" :value="statistics.total" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="本月关爱" :value="statistics.thisMonth" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="电话关怀" :value="statistics.phoneCall" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="上门探访" :value="statistics.homeVisit" />
        </el-col>
      </el-row>
    </el-card>

    <el-card class="filter-card" style="margin-top: 20px">
      <el-form :inline="true" :model="filterForm">
        <el-form-item label="老人姓名">
          <el-input
            v-model="filterForm.elderlyName"
            placeholder="请输入老人姓名"
            clearable
            :disabled="isFromElderlyManage"
            @clear="handleSearch"
          />
        </el-form-item>
        <el-form-item label="关爱类型">
          <el-select
            v-model="filterForm.careType"
            placeholder="请选择关爱类型"
            clearable
          >
            <el-option label="电话关怀" value="电话关怀" />
            <el-option label="上门探访" value="上门探访" />
            <el-option label="节日慰问" value="节日慰问" />
            <el-option label="就医陪同" value="就医陪同" />
            <el-option label="应急探访" value="应急探访" />
          </el-select>
        </el-form-item>
        <el-form-item label="护理员">
          <el-input
            v-model="filterForm.caregiverName"
            placeholder="请输入护理员姓名"
            clearable
            @clear="handleSearch"
          />
        </el-form-item>
        <el-form-item label="关爱日期">
          <el-date-picker
            v-model="filterForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            查询
          </el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-card" style="margin-top: 20px">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="elderlyName" label="老人姓名" width="120" />
        <el-table-column prop="careDate" label="关爱日期" width="120">
          <template #default="{ row }">
            {{ formatDate(row.careDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="caregiverName" label="护理员" width="100" />
        <el-table-column prop="careType" label="关爱类型" width="120">
          <template #default="{ row }">
            <el-tag :type="getCareTypeTagType(row.careType)">{{ row.careType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="careContent" label="关爱内容" min-width="200" show-overflow-tooltip />
        <el-table-column prop="caregiverPhone" label="护理员电话" width="120" />
        <el-table-column prop="createdAt" label="创建时间" width="160">
          <template #default="{ row }">
            {{ formatDateTime(row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="viewDetail(row)">
              查看详情
            </el-button>
            <el-button type="warning" link size="small" @click="editRecord(row)">
              编辑
            </el-button>
            <el-button type="danger" link size="small" @click="deleteRecord(row)">
              删除
            </el-button>
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
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>

    <!-- 详情对话框 -->
    <el-dialog
      v-model="detailDialogVisible"
      title="关爱档案详情"
      width="80%"
      :close-on-click-modal="false"
    >
      <div v-if="currentRecord" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="老人姓名">
            {{ currentRecord.elderlyName }}
          </el-descriptions-item>
          <el-descriptions-item label="关爱日期">
            {{ formatDate(currentRecord.careDate) }}
          </el-descriptions-item>
          <el-descriptions-item label="护理员">
            {{ currentRecord.caregiverName }}
          </el-descriptions-item>
          <el-descriptions-item label="护理员电话">
            {{ currentRecord.caregiverPhone }}
          </el-descriptions-item>
          <el-descriptions-item label="关爱类型">
            <el-tag :type="getCareTypeTagType(currentRecord.careType)">
              {{ currentRecord.careType }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="关爱内容" :span="2">
            {{ currentRecord.careContent }}
          </el-descriptions-item>
          <el-descriptions-item label="关爱结果" :span="2">
            {{ currentRecord.careResult }}
          </el-descriptions-item>
          <el-descriptions-item label="下次关爱日期">
            {{ formatDate(currentRecord.nextCareDate) }}
          </el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">
            {{ currentRecord.notes }}
          </el-descriptions-item>
          <el-descriptions-item label="创建时间" :span="2">
            {{ formatDateTime(currentRecord.createdAt) }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <template #footer>
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { getCareRecordList, getAllCareRecords, deleteCareRecord } from '../api/careRecord'
import { getElderlyList } from '../api/elderly'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const tableData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const detailDialogVisible = ref(false)
const currentRecord = ref(null)

// 是否从老人档案管理页面跳转过来
const isFromElderlyManage = computed(() => {
  return !!(route.query.elderlyId && route.query.elderlyName)
})

// 页面标题
const pageTitle = computed(() => {
  if (isFromElderlyManage.value) {
    return `${route.query.elderlyName} - 关爱档案`
  }
  return '关爱档案管理'
})

const filterForm = reactive({
  elderlyName: '',
  careType: '',
  caregiverName: '',
  dateRange: []
})

const statistics = ref({
  total: 0,
  thisMonth: 0,
  phoneCall: 0,
  homeVisit: 0
})

const elderlyMap = ref(new Map())

// 格式化日期
const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
}

// 格式化日期时间
const formatDateTime = (dateTime) => {
  if (!dateTime) return ''
  const d = new Date(dateTime)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`
}

// 获取关爱类型标签类型
const getCareTypeTagType = (type) => {
  const typeMap = {
    '电话关怀': 'primary',
    '上门探访': 'success',
    '节日慰问': 'warning',
    '就医陪同': 'danger',
    '应急探访': 'info'
  }
  return typeMap[type] || 'info'
}

// 加载老人列表
const loadElderlyList = async () => {
  try {
    const response = await getElderlyList({ page: 1, pageSize: 1000 })
    const elderlyList = response.data || response
    elderlyMap.value = new Map(elderlyList.map(e => [e.id, e.name]))
  } catch (error) {
    console.error('加载老人列表失败', error)
  }
}

// 加载关爱档案列表
const loadRecords = async () => {
  loading.value = true
  try {
    let response
    
    // 如果是从老人档案管理页面跳转过来，使用指定老人的API
    if (isFromElderlyManage.value && route.query.elderlyId && route.query.elderlyId !== 'undefined') {
      response = await getCareRecordList(route.query.elderlyId, {
        page: currentPage.value,
        pageSize: pageSize.value
      })
    } else {
      // 否则获取所有关爱档案，传递分页和过滤参数
      const params = {
        page: currentPage.value,
        pageSize: pageSize.value
      }
      
      // 添加过滤参数
      if (filterForm.elderlyName) {
        params.elderlyName = filterForm.elderlyName
      }
      if (filterForm.careType) {
        params.careType = filterForm.careType
      }
      if (filterForm.caregiverName) {
        params.caregiverName = filterForm.caregiverName
      }
      if (filterForm.dateRange && filterForm.dateRange.length === 2) {
        params.startDate = filterForm.dateRange[0]
        params.endDate = filterForm.dateRange[1]
      }
      
      response = await getAllCareRecords(params)
    }
    
    let records = response.data || response
    
    // 为每条记录添加老人姓名
    records = records.map(record => ({
      ...record,
      elderlyName: elderlyMap.value.get(record.elderlyId) || '未知'
    }))
    
    // 如果是从老人档案管理页面跳转过来，设置老人姓名
    if (isFromElderlyManage.value && route.query.elderlyName) {
      filterForm.elderlyName = route.query.elderlyName
    }
    
    tableData.value = records
    total.value = response.total || records.length
    
    updateStatistics(records)
  } catch (error) {
    ElMessage.error('加载关爱档案列表失败')
  } finally {
    loading.value = false
  }
}

// 更新统计信息
const updateStatistics = (records) => {
  statistics.total = records.length
  
  const thisMonth = new Date().getMonth()
  statistics.thisMonth = records.filter(r => {
    const date = new Date(r.careDate)
    return date.getMonth() === thisMonth
  }).length
  
  statistics.phoneCall = records.filter(r => r.careType === '电话关怀').length
  statistics.homeVisit = records.filter(r => r.careType === '上门探访').length
}

// 查询
const handleSearch = () => {
  currentPage.value = 1
  loadRecords()
}

// 重置
const handleReset = () => {
  filterForm.elderlyName = ''
  filterForm.careType = ''
  filterForm.caregiverName = ''
  filterForm.dateRange = []
  currentPage.value = 1
  loadRecords()
}

// 查看详情
const viewDetail = (row) => {
  currentRecord.value = row
  detailDialogVisible.value = true
}

// 编辑记录
const editRecord = (row) => {
  router.push(`/care-record/${row.elderlyId}`)
}

// 删除记录
const deleteRecord = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这条关爱档案记录吗？', '提示', {
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

// 分页大小改变
const handleSizeChange = (size) => {
  pageSize.value = size
  loadRecords()
}

// 当前页改变
const handleCurrentChange = (page) => {
  currentPage.value = page
  loadRecords()
}

// 返回
const goBack = () => {
  router.back()
}

onMounted(() => {
  loadElderlyList()
  loadRecords()
})
</script>

<style scoped>
.care-record-manage {
  padding: 20px;
}

.statistics-card,
.filter-card,
.table-card {
  background: white;
}

.detail-content {
  max-height: 600px;
  overflow-y: auto;
}

:deep(.el-descriptions__label) {
  font-weight: bold;
}
</style>
