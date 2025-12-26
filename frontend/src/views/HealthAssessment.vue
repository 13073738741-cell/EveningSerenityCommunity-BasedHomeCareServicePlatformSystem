<template>
  <div class="health-assessment">
    <el-page-header @back="goBack" :content="`健康能力评估 - ${elderlyName}`" />
    
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

        <el-divider content-position="left">日常生活能力评估（ADL）</el-divider>
        <el-form-item label="进食" prop="eating">
          <el-radio-group v-model="form.eating">
            <el-radio :label="10">独立完成（10分）</el-radio>
            <el-radio :label="5">需部分帮助（5分）</el-radio>
            <el-radio :label="0">完全依赖他人（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="洗澡" prop="bathing">
          <el-radio-group v-model="form.bathing">
            <el-radio :label="5">独立完成（5分）</el-radio>
            <el-radio :label="0">完全依赖他人（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="修饰" prop="grooming">
          <el-radio-group v-model="form.grooming">
            <el-radio :label="5">独立完成（5分）</el-radio>
            <el-radio :label="0">完全依赖他人（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="穿衣" prop="dressing">
          <el-radio-group v-model="form.dressing">
            <el-radio :label="10">独立完成（10分）</el-radio>
            <el-radio :label="5">需部分帮助（5分）</el-radio>
            <el-radio :label="0">完全依赖他人（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="控制大便" prop="bowelControl">
          <el-radio-group v-model="form.bowelControl">
            <el-radio :label="10">完全控制（10分）</el-radio>
            <el-radio :label="5">偶尔失控（5分）</el-radio>
            <el-radio :label="0">完全失控（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="控制小便" prop="bladderControl">
          <el-radio-group v-model="form.bladderControl">
            <el-radio :label="10">完全控制（10分）</el-radio>
            <el-radio :label="5">偶尔失控（5分）</el-radio>
            <el-radio :label="0">完全失控（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="如厕" prop="toilet">
          <el-radio-group v-model="form.toilet">
            <el-radio :label="10">独立完成（10分）</el-radio>
            <el-radio :label="5">需部分帮助（5分）</el-radio>
            <el-radio :label="0">完全依赖他人（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="床椅转移" prop="transfer">
          <el-radio-group v-model="form.transfer">
            <el-radio :label="15">独立完成（15分）</el-radio>
            <el-radio :label="10">需部分帮助（10分）</el-radio>
            <el-radio :label="5">需极大帮助（5分）</el-radio>
            <el-radio :label="0">完全依赖他人（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="平地行走" prop="walking">
          <el-radio-group v-model="form.walking">
            <el-radio :label="15">独立行走45米以上（15分）</el-radio>
            <el-radio :label="10">需部分帮助（10分）</el-radio>
            <el-radio :label="5">需极大帮助（5分）</el-radio>
            <el-radio :label="0">完全依赖他人（0分）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="上下楼梯" prop="stairs">
          <el-radio-group v-model="form.stairs">
            <el-radio :label="10">独立完成（10分）</el-radio>
            <el-radio :label="5">需部分帮助（5分）</el-radio>
            <el-radio :label="0">完全依赖他人（0分）</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-alert
          :title="`日常生活能力总分：${adlTotal} 分（满分100分）`"
          :type="getAdlLevel(adlTotal).type"
          :description="getAdlLevel(adlTotal).description"
          show-icon
          style="margin-bottom: 20px"
        />

        <el-divider content-position="left">认知能力评估</el-divider>
        <el-form-item label="记忆力" prop="memory">
          <el-rate v-model="form.memory" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="注意力" prop="attention">
          <el-rate v-model="form.attention" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="语言能力" prop="language">
          <el-rate v-model="form.language" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="判断力" prop="judgment">
          <el-rate v-model="form.judgment" :max="5" show-text />
        </el-form-item>

        <el-divider content-position="left">心理健康评估</el-divider>
        <el-form-item label="情绪状态" prop="mood">
          <el-select v-model="form.mood" placeholder="请选择情绪状态" style="width: 100%">
            <el-option label="情绪稳定" value="stable" />
            <el-option label="偶尔焦虑" value="anxious" />
            <el-option label="经常焦虑" value="frequent_anxious" />
            <el-option label="抑郁倾向" value="depressed" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="睡眠质量" prop="sleep">
          <el-select v-model="form.sleep" placeholder="请选择睡眠质量" style="width: 100%">
            <el-option label="良好" value="good" />
            <el-option label="一般" value="average" />
            <el-option label="较差" value="poor" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="社交情况" prop="social">
          <el-select v-model="form.social" placeholder="请选择社交情况" style="width: 100%">
            <el-option label="社交活跃" value="active" />
            <el-option label="社交一般" value="average" />
            <el-option label="社交较少" value="less" />
            <el-option label="社交缺乏" value="lack" />
          </el-select>
        </el-form-item>

        <el-divider content-position="left">慢性疾病情况</el-divider>
        <el-form-item label="主要疾病" prop="diseases">
          <el-checkbox-group v-model="form.diseases">
            <el-checkbox label="高血压" />
            <el-checkbox label="糖尿病" />
            <el-checkbox label="心脏病" />
            <el-checkbox label="脑血管疾病" />
            <el-checkbox label="慢性阻塞性肺病" />
            <el-checkbox label="骨质疏松" />
            <el-checkbox label="关节炎" />
            <el-checkbox label="其他" />
          </el-checkbox-group>
        </el-form-item>
        
        <el-form-item label="用药情况" prop="medication">
          <el-input
            v-model="form.medication"
            type="textarea"
            :rows="3"
            placeholder="请输入用药情况"
          />
        </el-form-item>

        <el-divider content-position="left">评估总结</el-divider>
        <el-form-item label="综合评估结果" prop="overallResult">
          <el-select v-model="form.overallResult" placeholder="请选择综合评估结果" style="width: 100%">
            <el-option label="健康" value="healthy" />
            <el-option label="基本健康" value="basically_healthy" />
            <el-option label="亚健康" value="sub_healthy" />
            <el-option label="需重点关注" value="attention_needed" />
            <el-option label="需医疗干预" value="medical_intervention" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="评估建议" prop="suggestions">
          <el-input
            v-model="form.suggestions"
            type="textarea"
            :rows="5"
            placeholder="请输入评估建议"
          />
        </el-form-item>
        
        <el-form-item label="备注" prop="notes">
          <el-input
            v-model="form.notes"
            type="textarea"
            :rows="3"
            placeholder="请输入备注信息"
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
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getElderlyById } from '../api/elderly'
import { createHealthAssessment, updateHealthAssessment, getHealthAssessmentList } from '../api/healthAssessment'
import { getUserInfo } from '../api/auth'

const router = useRouter()
const route = useRoute()
const formRef = ref(null)
const loading = ref(false)
const elderlyName = ref('')
const existingAssessmentId = ref(null) // 存储已存在的评估记录ID

const form = reactive({
  elderlyId: route.params.elderlyId,
  assessmentDate: new Date(),
  assessorName: '',
  eating: 10,
  bathing: 5,
  grooming: 5,
  dressing: 10,
  bowelControl: 10,
  bladderControl: 10,
  toilet: 10,
  transfer: 15,
  walking: 15,
  stairs: 10,
  memory: 4,
  attention: 4,
  language: 4,
  judgment: 4,
  mood: 'stable',
  sleep: 'good',
  social: 'active',
  diseases: [],
  medication: '',
  overallResult: 'healthy',
  suggestions: '',
  notes: ''
})

const rules = {
  assessmentDate: [{ required: true, message: '请选择评估日期', trigger: 'change' }],
  assessorName: [{ required: true, message: '请输入评估人姓名', trigger: 'blur' }],
  overallResult: [{ required: true, message: '请选择综合评估结果', trigger: 'change' }]
}

const adlTotal = computed(() => {
  return form.eating + form.bathing + form.grooming + form.dressing +
         form.bowelControl + form.bladderControl + form.toilet +
         form.transfer + form.walking + form.stairs
})

const getAdlLevel = (score) => {
  if (score >= 95) {
    return { type: 'success', description: '日常生活能力良好，基本独立' }
  } else if (score >= 75) {
    return { type: 'success', description: '日常生活能力较好，轻度依赖' }
  } else if (score >= 50) {
    return { type: 'warning', description: '日常生活能力一般，中度依赖' }
  } else if (score >= 25) {
    return { type: 'danger', description: '日常生活能力较差，重度依赖' }
  } else {
    return { type: 'danger', description: '日常生活能力极差，完全依赖' }
  }
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

const loadHealthAssessment = async (elderlyId) => {
  if (!elderlyId || elderlyId === 'undefined') {
    return
  }
  loading.value = true
  try {
    // 获取该老人的所有评估记录
    const response = await getHealthAssessmentList(elderlyId)
    if (response && response.length > 0) {
      // 获取最新的评估记录（假设列表按时间倒序排列，取第一个）
      const latestAssessment = response[0]
      existingAssessmentId.value = latestAssessment.id
      
      // 将字符串格式的diseases转换为数组
      const data = { ...latestAssessment }
      if (data.diseases && typeof data.diseases === 'string') {
        data.diseases = data.diseases.split(',').map(d => d.trim()).filter(d => d)
      }
      Object.assign(form, data)
      
      // 重新加载当前用户信息作为评估人，不使用历史记录中的评估人
      await loadUserInfo()
    }
  } catch (error) {
    console.error('加载健康评估失败:', error)
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
        // 将diseases数组转换为字符串（用逗号分隔）
        const submitData = {
          ...form,
          diseases: Array.isArray(form.diseases) ? form.diseases.join(',') : form.diseases
        }
        
        // 如果存在之前的评估记录，则更新；否则创建新记录
        if (existingAssessmentId.value) {
          await updateHealthAssessment(existingAssessmentId.value, submitData)
        } else {
          await createHealthAssessment(submitData)
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
  loadHealthAssessment(route.params.elderlyId)
})
</script>

<style scoped>
.health-assessment {
  padding: 20px;
}

.assessment-card {
  background: white;
}

:deep(.el-rate__text) {
  margin-left: 10px;
}
</style>