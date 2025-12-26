<template>
  <div class="environment-assessment-manage">
    <el-page-header @back="goBack" :content="pageTitle" />
    
    <el-card class="statistics-card" style="margin-top: 20px">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-statistic title="总评估次数" :value="statistics.total" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="本月评估" :value="statistics.thisMonth" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="环境优秀" :value="statistics.excellent" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="需改善" :value="statistics.needImprovement" />
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
        <el-form-item label="评估日期">
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
        <el-table-column prop="assessmentDate" label="评估日期" width="120">
          <template #default="{ row }">
            {{ formatDate(row.assessmentDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="assessorName" label="评估人" width="120" />
        <el-table-column prop="houseType" label="房屋类型" width="100">
          <template #default="{ row }">
            {{ getHouseTypeText(row.houseType) }}
          </template>
        </el-table-column>
        <el-table-column prop="hasElevator" label="电梯" width="80">
          <template #default="{ row }">
            <el-tag :type="row.hasElevator ? 'success' : 'info'" size="small">
              {{ row.hasElevator ? '有' : '无' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="floorSafety" label="地面防滑" width="100">
          <template #default="{ row }">
            <el-tag :type="getFloorSafetyTagType(row.floorSafety)" size="small">
              {{ getFloorSafetyText(row.floorSafety) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="overallResult" label="评估结果" width="100">
          <template #default="{ row }">
            <el-tag :type="getResultTagType(row.overallResult)">
              {{ getResultText(row.overallResult) }}
            </el-tag>
          </template>
        </el-table-column>
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
            <el-button type="warning" link size="small" @click="editAssessment(row)">
              编辑
            </el-button>
            <el-button type="danger" link size="small" @click="deleteAssessment(row)">
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
      title="家庭环境评估详情"
      width="70%"
      :close-on-click-modal="false"
    >
      <div v-if="currentAssessment" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="老人姓名">
            {{ currentAssessment.elderlyName }}
          </el-descriptions-item>
          <el-descriptions-item label="评估日期">
            {{ formatDate(currentAssessment.assessmentDate) }}
          </el-descriptions-item>
          <el-descriptions-item label="评估人">
            {{ currentAssessment.assessorName }}
          </el-descriptions-item>
          <el-descriptions-item label="房屋类型">
            {{ getHouseTypeText(currentAssessment.houseType) }}
          </el-descriptions-item>
          <el-descriptions-item label="是否有电梯">
            <el-tag :type="currentAssessment.hasElevator ? 'success' : 'info'">
              {{ currentAssessment.hasElevator ? '有' : '无' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="地面防滑">
            <el-tag :type="getFloorSafetyTagType(currentAssessment.floorSafety)">
              {{ getFloorSafetyText(currentAssessment.floorSafety) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="扶手设施" :span="2">
            <el-tag
              v-for="handrail in (currentAssessment.handrails || [])"
              :key="handrail"
              size="small"
              style="margin-right: 5px; margin-bottom: 5px"
            >
              {{ handrail }}
            </el-tag>
            <span v-if="!currentAssessment.handrails || currentAssessment.handrails.length === 0">无</span>
          </el-descriptions-item>
          <el-descriptions-item label="综合评估结果" :span="2">
            <el-tag :type="getResultTagType(currentAssessment.overallResult)">
              {{ getResultText(currentAssessment.overallResult) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="主要问题" :span="2">
            {{ currentAssessment.problems || '无' }}
          </el-descriptions-item>
          <el-descriptions-item label="改善建议" :span="2">
            {{ currentAssessment.suggestions || '无' }}
          </el-descriptions-item>
          <el-descriptions-item label="创建时间" :span="2">
            {{ formatDateTime(currentAssessment.createdAt) }}
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
import { getEnvironmentAssessmentList, getAllEnvironmentAssessments, deleteEnvironmentAssessment } from '../api/environmentAssessment'
import { getElderlyList, searchElderly } from '../api/elderly'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const tableData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const detailDialogVisible = ref(false)
const currentAssessment = ref(null)

// 是否从老人档案管理页面跳转过来
const isFromElderlyManage = computed(() => {
  return !!(route.query.elderlyId && route.query.elderlyName)
})

// 页面标题
const pageTitle = computed(() => {
  if (isFromElderlyManage.value) {
    return `${route.query.elderlyName} - 家庭环境档案`
  }
  return '家庭环境档案管理'
})

const filterForm = reactive({
  elderlyName: '',
  dateRange: []
})

const statistics = reactive({
  total: 0,
  thisMonth: 0,
  excellent: 0,
  needImprovement: 0
})

const elderlyMap = ref(new Map())

// 格式化日期
const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
}

// 格式化日期时间
const formatDateTime = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
}

// 获取房屋类型文本
const getHouseTypeText = (type) => {
  const textMap = {
    owned: '自有住房',
    rented: '租赁住房',
    children: '子女住房',
    institution: '养老机构',
    other: '其他'
  }
  return textMap[type] || type
}

// 获取地面防滑标签类型
const getFloorSafetyTagType = (safety) => {
  const typeMap = {
    '良好': 'success',
    '一般': 'warning',
    '较差': 'danger',
    'good': 'success',
    'average': 'warning',
    'poor': 'danger'
  }
  return typeMap[safety] || 'info'
}

// 获取地面防滑文本
const getFloorSafetyText = (safety) => {
  const textMap = {
    '良好': '良好',
    '一般': '一般',
    '较差': '较差',
    'good': '良好',
    'average': '一般',
    'poor': '较差'
  }
  return textMap[safety] || safety
}

// 获取结果标签类型
const getResultTagType = (result) => {
  const typeMap = {
    '安全': 'success',
    '需改善': 'danger',
    '需要改善': 'danger',
    'safe': 'success',
    'needs improvement': 'danger'
  }
  return typeMap[result] || 'info'
}

// 获取结果文本
const getResultText = (result) => {
  const textMap = {
    '安全': '安全',
    '需改善': '需改善',
    '需要改善': '需要改善',
    'safe': '安全',
    'needs improvement': '需要改善'
  }
  return textMap[result] || result
}

// 加载老人列表
const loadElderlyList = async () => {
  try {
    const response = await getElderlyList({ page: 1, size: 1000 })
    const elderlyList = response.data?.list || response.data || []
    elderlyMap.value = new Map(elderlyList.map(item => [item.id, item.name]))
  } catch (error) {
    console.error('加载老人列表失败:', error)
  }
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    let response
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value
    }

    if (isFromElderlyManage.value && route.query.elderlyId && route.query.elderlyId !== 'undefined') {
      // 从老人档案管理页面跳转过来，只显示该老人的评估记录
      response = await getEnvironmentAssessmentList(route.query.elderlyId, params)
    } else {
      // 从菜单进入，显示所有评估记录，传递过滤参数
      if (filterForm.elderlyName) {
        // 根据老人姓名搜索老人ID
        const elderlyResponse = await searchElderly(filterForm.elderlyName)
        const elderlyList = elderlyResponse.data?.list || elderlyResponse.data || []
        if (elderlyList.length > 0) {
          params.elderlyId = elderlyList[0].id
        }
      }
      if (filterForm.dateRange && filterForm.dateRange.length === 2) {
        params.startDate = filterForm.dateRange[0]
        params.endDate = filterForm.dateRange[1]
      }
      response = await getAllEnvironmentAssessments(params)
    }

    let list = response.data?.list || response.data || []

    tableData.value = list.map(item => ({
      ...item,
      elderlyName: elderlyMap.value.get(item.elderlyId) || '未知'
    }))
    total.value = response.total || list.length

    // 计算统计数据
    calculateStatistics()
  } catch (error) {
    console.error('加载数据失败:', error)
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

// 计算统计数据
const calculateStatistics = () => {
  statistics.total = total.value
  statistics.thisMonth = 0
  statistics.excellent = 0
  statistics.needImprovement = 0

  const now = new Date()
  const currentMonth = now.getMonth()
  const currentYear = now.getFullYear()

  tableData.value.forEach(item => {
    const assessmentDate = new Date(item.assessmentDate)
    if (assessmentDate.getMonth() === currentMonth && assessmentDate.getFullYear() === currentYear) {
      statistics.thisMonth++
    }

    if (item.overallResult === 'safe') {
      statistics.excellent++
    } else if (item.overallResult === 'needs improvement') {
      statistics.needImprovement++
    }
  })
}

// 查询
const handleSearch = async () => {
  currentPage.value = 1
  await loadData()
}

// 重置
const handleReset = () => {
  filterForm.elderlyName = ''
  filterForm.dateRange = []
  handleSearch()
}

// 查看详情
const viewDetail = (row) => {
  currentAssessment.value = row
  detailDialogVisible.value = true
}

// 编辑评估
const editAssessment = (row) => {
  router.push({
    name: 'EnvironmentAssessment',
    params: { elderlyId: row.elderlyId },
    query: { assessmentId: row.id }
  })
}

// 删除评估
const deleteAssessment = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除该评估记录吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    await deleteEnvironmentAssessment(row.id)
    ElMessage.success('删除成功')
    await loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 分页大小改变
const handleSizeChange = (val) => {
  pageSize.value = val
  loadData()
}

// 当前页改变
const handleCurrentChange = (val) => {
  currentPage.value = val
  loadData()
}

// 返回
const goBack = () => {
  router.back()
}

onMounted(() => {
  loadElderlyList()
  loadData()
})
</script>

<style scoped>
.environment-assessment-manage {
  padding: 20px;
}

.statistics-card,
.filter-card,
.table-card {
  background: white;
}

.detail-content {
  max-height: 60vh;
  overflow-y: auto;
}

/* 地面防滑标签颜色 */
:deep(.el-tag--success) {
  background-color: #f0f9ff;
  border-color: #67c23a;
  color: #67c23a;
}

:deep(.el-tag--warning) {
  background-color: #fdf6ec;
  border-color: #e6a23c;
  color: #e6a23c;
}

:deep(.el-tag--danger) {
  background-color: #fef0f0;
  border-color: #f56c6c;
  color: #f56c6c;
}

:deep(.el-tag--info) {
  background-color: #f4f4f5;
  border-color: #909399;
  color: #909399;
}

/* 增强标签视觉效果 */
:deep(.el-tag) {
  font-weight: 500;
  padding: 4px 10px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

:deep(.el-tag:hover) {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}
</style>
