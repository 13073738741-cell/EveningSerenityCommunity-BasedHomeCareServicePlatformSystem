<template>
  <div class="health-assessment-manage">
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
          <el-statistic title="健康老人" :value="statistics.healthy" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="需关注老人" :value="statistics.attention" />
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
        <el-form-item label="评估结果">
          <el-select
            v-model="filterForm.overallResult"
            placeholder="请选择评估结果"
            clearable
          >
            <el-option label="健康" value="healthy" />
            <el-option label="基本健康" value="basically_healthy" />
            <el-option label="亚健康" value="sub_healthy" />
            <el-option label="需重点关注" value="attention_needed" />
            <el-option label="需医疗干预" value="medical_intervention" />
          </el-select>
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
        <el-table-column prop="assessor" label="评估人" width="100" />
        <el-table-column label="ADL总分" width="100">
          <template #default="{ row }">
            <el-tag :type="getAdlTagType(row.adlTotal)">{{ row.adlTotal }}分</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="ADL等级" width="120">
          <template #default="{ row }">
            <el-tag :type="getAdlTagType(row.adlTotal)">{{ getAdlLevelText(row.adlTotal) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="overallResult" label="综合评估结果" width="120">
          <template #default="{ row }">
            <el-tag :type="getResultTagType(row.overallResult)">
              {{ getResultText(row.overallResult) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="diseases" label="慢性疾病" width="200">
          <template #default="{ row }">
            <el-tag
              v-for="disease in (row.diseases || [])"
              :key="disease"
              size="small"
              style="margin-right: 5px; margin-bottom: 5px"
            >
              {{ disease }}
            </el-tag>
            <span v-if="!row.diseases || row.diseases.length === 0">无</span>
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
      title="健康评估详情"
      width="80%"
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
            {{ currentAssessment.assessor }}
          </el-descriptions-item>
          <el-descriptions-item label="ADL总分">
            <el-tag :type="getAdlTagType(currentAssessment.adlTotal)">
              {{ currentAssessment.adlTotal }}分
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="ADL等级" :span="2">
            <el-tag :type="getAdlTagType(currentAssessment.adlTotal)">
              {{ getAdlLevelText(currentAssessment.adlTotal) }}
            </el-tag>
            - {{ getAdlLevelDescription(currentAssessment.adlTotal) }}
          </el-descriptions-item>
          <el-descriptions-item label="进食">
            {{ currentAssessment.eating }}分
          </el-descriptions-item>
          <el-descriptions-item label="洗澡">
            {{ currentAssessment.bathing }}分
          </el-descriptions-item>
          <el-descriptions-item label="修饰">
            {{ currentAssessment.grooming }}分
          </el-descriptions-item>
          <el-descriptions-item label="穿衣">
            {{ currentAssessment.dressing }}分
          </el-descriptions-item>
          <el-descriptions-item label="控制大便">
            {{ currentAssessment.bowelControl }}分
          </el-descriptions-item>
          <el-descriptions-item label="控制小便">
            {{ currentAssessment.bladderControl }}分
          </el-descriptions-item>
          <el-descriptions-item label="如厕">
            {{ currentAssessment.toilet }}分
          </el-descriptions-item>
          <el-descriptions-item label="床椅转移">
            {{ currentAssessment.transfer }}分
          </el-descriptions-item>
          <el-descriptions-item label="平地行走">
            {{ currentAssessment.walking }}分
          </el-descriptions-item>
          <el-descriptions-item label="上下楼梯">
            {{ currentAssessment.stairs }}分
          </el-descriptions-item>
          <el-descriptions-item label="记忆力">
            {{ currentAssessment.memory }}分
          </el-descriptions-item>
          <el-descriptions-item label="注意力">
            {{ currentAssessment.attention }}分
          </el-descriptions-item>
          <el-descriptions-item label="语言能力">
            {{ currentAssessment.language }}分
          </el-descriptions-item>
          <el-descriptions-item label="判断力">
            {{ currentAssessment.judgment }}分
          </el-descriptions-item>
          <el-descriptions-item label="情绪状态">
            {{ getMoodText(currentAssessment.mood) }}
          </el-descriptions-item>
          <el-descriptions-item label="睡眠质量">
            {{ getSleepText(currentAssessment.sleep) }}
          </el-descriptions-item>
          <el-descriptions-item label="社交情况">
            {{ getSocialText(currentAssessment.social) }}
          </el-descriptions-item>
          <el-descriptions-item label="慢性疾病" :span="2">
            <el-tag
              v-for="disease in (currentAssessment.diseases || [])"
              :key="disease"
              size="small"
              style="margin-right: 5px; margin-bottom: 5px"
            >
              {{ disease }}
            </el-tag>
            <span v-if="!currentAssessment.diseases || currentAssessment.diseases.length === 0">无</span>
          </el-descriptions-item>
          <el-descriptions-item label="用药情况" :span="2">
            {{ currentAssessment.medication || '无' }}
          </el-descriptions-item>
          <el-descriptions-item label="综合评估结果" :span="2">
            <el-tag :type="getResultTagType(currentAssessment.overallResult)">
              {{ getResultText(currentAssessment.overallResult) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="评估建议" :span="2">
            {{ currentAssessment.suggestions || '无' }}
          </el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">
            {{ currentAssessment.notes || '无' }}
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
import { getHealthAssessmentList, getAllHealthAssessments, deleteHealthAssessment } from '../api/healthAssessment'
import { getElderlyList } from '../api/elderly'

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
    return `${route.query.elderlyName} - 健康能力档案`
  }
  return '健康能力档案管理'
})

const filterForm = reactive({
  elderlyName: '',
  dateRange: [],
  overallResult: ''
})

const statistics = reactive({
  total: 0,
  thisMonth: 0,
  healthy: 0,
  attention: 0
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

// 计算ADL总分
const calculateAdlTotal = (assessment) => {
  return (assessment.eating || 0) +
         (assessment.bathing || 0) +
         (assessment.grooming || 0) +
         (assessment.dressing || 0) +
         (assessment.bowelControl || 0) +
         (assessment.bladderControl || 0) +
         (assessment.toilet || 0) +
         (assessment.transfer || 0) +
         (assessment.walking || 0) +
         (assessment.stairs || 0)
}

// 获取ADL标签类型
const getAdlTagType = (score) => {
  if (score >= 75) return 'success'
  if (score >= 50) return 'warning'
  return 'danger'
}

// 获取ADL等级文本
const getAdlLevelText = (score) => {
  if (score >= 95) return '良好'
  if (score >= 75) return '较好'
  if (score >= 50) return '一般'
  if (score >= 25) return '较差'
  return '极差'
}

// 获取ADL等级描述
const getAdlLevelDescription = (score) => {
  if (score >= 95) return '日常生活能力良好，基本独立'
  if (score >= 75) return '日常生活能力较好，轻度依赖'
  if (score >= 50) return '日常生活能力一般，中度依赖'
  if (score >= 25) return '日常生活能力较差，重度依赖'
  return '日常生活能力极差，完全依赖'
}

// 获取结果标签类型
const getResultTagType = (result) => {
  const typeMap = {
    healthy: 'success',
    basically_healthy: 'success',
    sub_healthy: 'warning',
    attention_needed: 'danger',
    medical_intervention: 'danger'
  }
  return typeMap[result] || 'info'
}

// 获取结果文本
const getResultText = (result) => {
  const textMap = {
    healthy: '健康',
    basically_healthy: '基本健康',
    sub_healthy: '亚健康',
    attention_needed: '需重点关注',
    medical_intervention: '需医疗干预'
  }
  return textMap[result] || result
}

// 获取情绪文本
const getMoodText = (mood) => {
  const textMap = {
    stable: '情绪稳定',
    anxious: '偶尔焦虑',
    frequent_anxious: '经常焦虑',
    depressed: '抑郁倾向'
  }
  return textMap[mood] || mood
}

// 获取睡眠文本
const getSleepText = (sleep) => {
  const textMap = {
    good: '良好',
    average: '一般',
    poor: '较差'
  }
  return textMap[sleep] || sleep
}

// 获取社交文本
const getSocialText = (social) => {
  const textMap = {
    active: '社交活跃',
    average: '社交一般',
    less: '社交较少',
    lack: '社交缺乏'
  }
  return textMap[social] || social
}

// 加载老人列表
const loadElderlyList = async () => {
  try {
    const response = await getElderlyList({ page: 1, pageSize: 1000 })
    const elderlyList = response.data || response
    elderlyList.forEach(elderly => {
      elderlyMap.value.set(elderly.id, elderly.name)
    })
  } catch (error) {
    console.error('加载老人列表失败', error)
  }
}

// 加载健康评估列表
const loadAssessments = async () => {
  loading.value = true
  try {
    let response
    
    // 如果是从老人档案管理页面跳转过来，使用指定老人的API
    if (isFromElderlyManage.value && route.query.elderlyId) {
      response = await getHealthAssessmentList(route.query.elderlyId, {
        page: currentPage.value,
        pageSize: pageSize.value
      })
    } else {
      // 否则获取所有评估记录
      response = await getAllHealthAssessments({
        page: currentPage.value,
        pageSize: pageSize.value
      })
    }
    
    let assessments = response.data || response
    
    // 为每条记录添加老人姓名、计算ADL总分和转换diseases格式
    assessments = assessments.map(assessment => {
      const elderlyName = elderlyMap.value.get(assessment.elderlyId) || '未知'
      // 将字符串格式的diseases转换为数组
      let diseases = assessment.diseases
      if (diseases && typeof diseases === 'string') {
        diseases = diseases.split(',').map(d => d.trim()).filter(d => d)
      }
      return {
        ...assessment,
        elderlyName,
        adlTotal: calculateAdlTotal(assessment),
        diseases: diseases || []
      }
    })
    
    // 如果是从老人档案管理页面跳转过来，设置老人姓名
    if (isFromElderlyManage.value && route.query.elderlyName) {
      filterForm.elderlyName = route.query.elderlyName
    }
    
    // 过滤
    if (filterForm.elderlyName && !isFromElderlyManage.value) {
      assessments = assessments.filter(a => 
        a.elderlyName.includes(filterForm.elderlyName)
      )
    }
    
    if (filterForm.dateRange && filterForm.dateRange.length === 2) {
      const [start, end] = filterForm.dateRange
      assessments = assessments.filter(a => {
        const date = formatDate(a.assessmentDate)
        return date >= start && date <= end
      })
    }
    
    if (filterForm.overallResult) {
      assessments = assessments.filter(a => 
        a.overallResult === filterForm.overallResult
      )
    }
    
    tableData.value = assessments
    total.value = response.total || assessments.length
    
    updateStatistics(assessments)
  } catch (error) {
    ElMessage.error('加载健康评估列表失败')
  } finally {
    loading.value = false
  }
}

// 更新统计信息
const updateStatistics = (assessments) => {
  statistics.total = assessments.length
  
  const thisMonth = new Date().getMonth()
  statistics.thisMonth = assessments.filter(a => {
    const date = new Date(a.assessmentDate)
    return date.getMonth() === thisMonth
  }).length
  
  statistics.healthy = assessments.filter(a => 
    a.overallResult === 'healthy' || a.overallResult === 'basically_healthy'
  ).length
  
  statistics.attention = assessments.filter(a => 
    a.overallResult === 'attention_needed' || a.overallResult === 'medical_intervention'
  ).length
}

// 查询
const handleSearch = () => {
  currentPage.value = 1
  loadAssessments()
}

// 重置
const handleReset = () => {
  filterForm.elderlyName = ''
  filterForm.dateRange = []
  filterForm.overallResult = ''
  currentPage.value = 1
  loadAssessments()
}

// 查看详情
const viewDetail = (row) => {
  currentAssessment.value = row
  detailDialogVisible.value = true
}

// 编辑评估
const editAssessment = (row) => {
  router.push(`/health-assessment/${row.elderlyId}`)
}

// 删除评估
const deleteAssessment = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这条健康评估记录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteHealthAssessment(row.id)
    ElMessage.success('删除成功')
    loadAssessments()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 分页大小改变
const handleSizeChange = (size) => {
  pageSize.value = size
  loadAssessments()
}

// 当前页改变
const handleCurrentChange = (page) => {
  currentPage.value = page
  loadAssessments()
}

// 返回
const goBack = () => {
  router.back()
}

onMounted(() => {
  loadElderlyList()
  loadAssessments()
})
</script>

<style scoped>
.health-assessment-manage {
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
