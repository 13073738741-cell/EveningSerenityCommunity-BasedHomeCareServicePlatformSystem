<template>
  <div class="environment-assessment">
    <el-page-header @back="goBack" :content="`家庭环境评估 - ${elderlyName}`" />
    
    <el-card class="assessment-card" style="margin-top: 20px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="150px">
        <el-divider content-position="left">基本信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="评估日期" prop="assessmentDate">
              <el-date-picker
                v-model="form.assessmentDate"
                type="date"
                placeholder="选择评估日期"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="评估人" prop="assessorName">
              <el-input v-model="form.assessorName" placeholder="请输入评估人姓名" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">住房条件评估</el-divider>
        <el-form-item label="房屋类型" prop="houseType">
          <el-select v-model="form.houseType" placeholder="请选择房屋类型" style="width: 100%">
            <el-option label="自有住房" value="owned" />
            <el-option label="租赁住房" value="rented" />
            <el-option label="子女住房" value="children" />
            <el-option label="养老机构" value="institution" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="是否有电梯" prop="hasElevator">
          <el-radio-group v-model="form.hasElevator">
            <el-radio :label="true">有</el-radio>
            <el-radio :label="false">无</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-divider content-position="left">安全设施评估</el-divider>
        <el-form-item label="地面防滑" prop="floorSafety">
          <el-radio-group v-model="form.floorSafety">
            <el-radio label="good">良好</el-radio>
            <el-radio label="average">一般</el-radio>
            <el-radio label="poor">较差</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="扶手设施" prop="handrails">
          <el-checkbox-group v-model="form.handrails">
            <el-checkbox label="卫生间扶手" />
            <el-checkbox label="走廊扶手" />
            <el-checkbox label="楼梯扶手" />
            <el-checkbox label="无" />
          </el-checkbox-group>
        </el-form-item>

        <el-divider content-position="left">评估总结</el-divider>
        <el-form-item label="综合评估结果" prop="overallResult">
          <el-select v-model="form.overallResult" placeholder="请选择综合评估结果" style="width: 100%">
            <el-option label="安全" value="safe" />
            <el-option label="需要改善" value="needs improvement" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="主要问题" prop="problems">
          <el-input
            v-model="form.problems"
            type="textarea"
            :rows="4"
            placeholder="请列出主要问题"
          />
        </el-form-item>
        
        <el-form-item label="改善建议" prop="suggestions">
          <el-input
            v-model="form.suggestions"
            type="textarea"
            :rows="5"
            placeholder="请输入改善建议"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存评估</el-button>
          <el-button @click="goBack">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getElderlyById } from '../api/elderly'
import { createEnvironmentAssessment, updateEnvironmentAssessment, getEnvironmentAssessmentById, getEnvironmentAssessmentList } from '../api/environmentAssessment'
import { getUserInfo } from '../api/auth'

const router = useRouter()
const route = useRoute()
const formRef = ref(null)
const loading = ref(false)
const elderlyName = ref('')
const existingAssessmentId = ref(null) // 存储已存在的评估记录ID

const form = reactive({
  id: null,
  elderlyId: route.params.elderlyId,
  assessmentDate: new Date(),
  assessorName: '',
  houseType: 'owned',
  hasElevator: false,
  floorSafety: 'good',
  handrails: [],
  overallResult: 'safe',
  problems: '',
  suggestions: ''
})

const rules = {
  assessmentDate: [{ required: true, message: '请选择评估日期', trigger: 'change' }],
  assessorName: [{ required: true, message: '请输入评估人姓名', trigger: 'blur' }],
  overallResult: [{ required: true, message: '请选择综合评估结果', trigger: 'change' }]
}

const loadElderlyInfo = async () => {
  if (!route.params.elderlyId || route.params.elderlyId === 'undefined') {
    ElMessage.error('缺少老人ID参数')
    router.back()
    return
  }
  try {
    const response = await getElderlyById(route.params.elderlyId)
    elderlyName.value = response.data?.name || response.name || ''
  } catch (error) {
    ElMessage.error('加载老人信息失败')
  }
}

const loadUserInfo = async () => {
  try {
    const response = await getUserInfo()
    if (response.success) {
      // 始终使用当前用户的真实姓名作为评估人
      form.assessorName = response.realName || response.username
    }
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

const loadEnvironmentAssessment = async (elderlyId) => {
  if (!elderlyId || elderlyId === 'undefined') {
    return
  }
  loading.value = true
  try {
    // 获取该老人的所有环境评估记录
    const response = await getEnvironmentAssessmentList(elderlyId, { page: 1, pageSize: 1 })
    console.log('环境评估查询响应:', response)
    console.log('response.data:', response.data)
    console.log('response类型:', typeof response)
    console.log('response是否为数组:', Array.isArray(response))

    const assessments = response.data || response
    console.log('评估记录数组:', assessments)
    console.log('assessments是否为数组:', Array.isArray(assessments))

    if (Array.isArray(assessments) && assessments.length > 0) {
      // 获取最新的评估记录（假设列表按时间倒序排列，取第一个）
      const latestAssessment = assessments[0]
      console.log('找到最新评估记录:', latestAssessment)
      console.log('latestAssessment.problems:', latestAssessment.problems)
      console.log('latestAssessment.suggestions:', latestAssessment.suggestions)

      existingAssessmentId.value = latestAssessment.id
      console.log('找到最新评估记录，ID:', latestAssessment.id)

      // 将评估记录数据填充到表单
      Object.assign(form, {
        id: latestAssessment.id,
        elderlyId: latestAssessment.elderlyId,
        assessmentDate: latestAssessment.assessmentDate ? new Date(latestAssessment.assessmentDate) : new Date(),
        assessorName: '', // 不使用历史记录中的评估人，稍后会重新加载当前用户
        houseType: latestAssessment.houseType || 'owned',
        hasElevator: latestAssessment.hasElevator !== undefined ? latestAssessment.hasElevator : false,
        floorSafety: latestAssessment.floorSafety || 'good',
        handrails: latestAssessment.handrails ? (typeof latestAssessment.handrails === 'string' ? latestAssessment.handrails.split(',') : latestAssessment.handrails) : [],
        overallResult: latestAssessment.overallResult || 'good',
        problems: latestAssessment.problems || '',
        suggestions: latestAssessment.suggestions || ''
      })

      console.log('表单数据已更新:', form)
      console.log('form.problems:', form.problems)
      console.log('form.suggestions:', form.suggestions)
      
      // 重新加载当前用户信息作为评估人，不使用历史记录中的评估人
      await loadUserInfo()
    } else {
      console.log('未找到评估记录，使用默认表单数据')
    }
  } catch (error) {
    console.error('加载环境评估失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // 将handrails数组转换为字符串（用逗号分隔）
        const submitData = {
          ...form,
          handrails: Array.isArray(form.handrails) ? form.handrails.join(',') : form.handrails
        }
        
        // 如果存在之前的评估记录，则更新；否则创建新记录
        if (existingAssessmentId.value) {
          await updateEnvironmentAssessment(existingAssessmentId.value, submitData)
        } else {
          await createEnvironmentAssessment(submitData)
        }
        ElMessage.success('保存成功')
        goBack()
      } catch (error) {
        ElMessage.error('保存失败')
      } finally {
        loading.value = false
      }
    }
  })
}

const goBack = () => {
  router.back()
}

onMounted(() => {
  loadElderlyInfo()
  loadUserInfo() // 加载用户信息以设置默认评估人
  loadEnvironmentAssessment(route.params.elderlyId)
})
</script>

<style scoped>
.environment-assessment {
  padding: 20px;
}

.assessment-card {
  background: white;
}

:deep(.el-rate__text) {
  margin-left: 10px;
}
</style>





