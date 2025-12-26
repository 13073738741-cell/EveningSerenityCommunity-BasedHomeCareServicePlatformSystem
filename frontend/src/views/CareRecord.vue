<template>
  <div class="care-record">
    <el-page-header @back="goBack" :content="`关爱档案记录 - ${elderlyName}`" />
    
    <el-card class="record-card" style="margin-top: 20px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="150px">
        <el-divider content-position="left">基本信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="关爱日期" prop="careDate">
              <el-date-picker
                v-model="form.careDate"
                type="date"
                placeholder="选择关爱日期"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="护理员姓名" prop="caregiverName">
              <el-input v-model="form.caregiverName" placeholder="请输入护理员姓名" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="关爱类型" prop="careType">
          <el-select
            v-model="form.careType"
            placeholder="请选择关爱类型"
            style="width: 100%"
          >
            <el-option label="电话关怀" value="电话关怀" />
            <el-option label="上门探访" value="上门探访" />
            <el-option label="节日慰问" value="节日慰问" />
            <el-option label="就医陪同" value="就医陪同" />
            <el-option label="应急探访" value="应急探访" />
          </el-select>
        </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="护理等级" prop="careLevel">
              <el-select v-model="form.careLevel" placeholder="请选择护理等级" style="width: 100%">
                <el-option label="一级护理" value="一级护理" />
                <el-option label="二级护理" value="二级护理" />
                <el-option label="三级护理" value="三级护理" />
                <el-option label="特级护理" value="特级护理" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="关爱时长" prop="careDuration">
              <el-input-number
                v-model="form.careDuration"
                :min="0"
                :max="24"
                :precision="1"
                :step="0.5"
                placeholder="关爱时长(小时)"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">生活照料</el-divider>
        <el-form-item label="生活照料项目" prop="lifeCareItems">
          <el-checkbox-group v-model="form.lifeCareItems">
            <el-checkbox label="协助进食" />
            <el-checkbox label="协助洗澡" />
            <el-checkbox label="协助穿衣" />
            <el-checkbox label="协助如厕" />
            <el-checkbox label="协助翻身" />
            <el-checkbox label="协助行走" />
            <el-checkbox label="清洁护理" />
            <el-checkbox label="其他" />
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="生活照料记录" prop="lifeCareRecord">
          <el-input
            v-model="form.lifeCareRecord"
            type="textarea"
            :rows="3"
            placeholder="请详细记录生活照料情况"
          />
        </el-form-item>

        <el-divider content-position="left">健康监测</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="血压" prop="bloodPressure">
              <el-input v-model="form.bloodPressure" placeholder="例如: 120/80" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="心率" prop="heartRate">
              <el-input-number
                v-model="form.heartRate"
                :min="0"
                :max="200"
                placeholder="心率(次/分)"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="体温" prop="temperature">
              <el-input-number
                v-model="form.temperature"
                :min="30"
                :max="45"
                :precision="1"
                :step="0.1"
                placeholder="体温(℃)"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="血糖" prop="bloodSugar">
              <el-input-number
                v-model="form.bloodSugar"
                :min="0"
                :max="30"
                :precision="1"
                :step="0.1"
                placeholder="血糖(mmol/L)"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用药情况" prop="medication">
              <el-input v-model="form.medication" placeholder="请记录用药情况" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="健康监测记录" prop="healthMonitorRecord">
          <el-input
            v-model="form.healthMonitorRecord"
            type="textarea"
            :rows="3"
            placeholder="请详细记录健康监测情况"
          />
        </el-form-item>

        <el-divider content-position="left">心理关怀</el-divider>
        <el-form-item label="心理状态" prop="mentalState">
          <el-select v-model="form.mentalState" placeholder="请选择心理状态" style="width: 100%">
            <el-option label="情绪稳定" value="情绪稳定" />
            <el-option label="情绪良好" value="情绪良好" />
            <el-option label="情绪一般" value="情绪一般" />
            <el-option label="情绪低落" value="情绪低落" />
            <el-option label="情绪激动" value="情绪激动" />
          </el-select>
        </el-form-item>

        <el-form-item label="心理关怀内容" prop="mentalCareContent">
          <el-checkbox-group v-model="form.mentalCareContent">
            <el-checkbox label="陪伴聊天" />
            <el-checkbox label="倾听倾诉" />
            <el-checkbox label="心理疏导" />
            <el-checkbox label="情感支持" />
            <el-checkbox label="娱乐活动" />
            <el-checkbox label="其他" />
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="心理关怀记录" prop="mentalCareRecord">
          <el-input
            v-model="form.mentalCareRecord"
            type="textarea"
            :rows="3"
            placeholder="请详细记录心理关怀情况"
          />
        </el-form-item>

        <el-divider content-position="left">关爱总结</el-divider>
        <el-form-item label="关爱效果" prop="careEffect">
          <el-select v-model="form.careEffect" placeholder="请选择关爱效果" style="width: 100%">
            <el-option label="效果良好" value="效果良好" />
            <el-option label="效果较好" value="效果较好" />
            <el-option label="效果一般" value="效果一般" />
            <el-option label="效果较差" value="效果较差" />
          </el-select>
        </el-form-item>

        <el-form-item label="关爱总结" prop="careSummary">
          <el-input
            v-model="form.careSummary"
            type="textarea"
            :rows="5"
            placeholder="请总结本次关爱情况"
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
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存记录</el-button>
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
import { createCareRecord, updateCareRecord, getCareRecordById } from '../api/careRecord'
import { getUserInfo } from '../api/auth'

const router = useRouter()
const route = useRoute()
const formRef = ref(null)
const loading = ref(false)
const elderlyName = ref('')
const existingRecordId = ref(null)

const form = reactive({
  elderlyId: route.params.elderlyId,
  careDate: new Date(),
  caregiverName: '',
  careType: '上门探访',
  careLevel: '二级护理',
  careDuration: 2,
  lifeCareItems: [],
  lifeCareRecord: '',
  bloodPressure: '',
  heartRate: null,
  temperature: null,
  bloodSugar: null,
  medication: '',
  healthMonitorRecord: '',
  mentalState: '情绪稳定',
  mentalCareContent: [],
  mentalCareRecord: '',
  careEffect: '效果良好',
  careSummary: '',
  notes: ''
})

const rules = {
  careDate: [{ required: true, message: '请选择关爱日期', trigger: 'change' }],
  caregiverName: [{ required: true, message: '请输入护理员姓名', trigger: 'blur' }],
  careType: [{ required: true, message: '请选择关爱类型', trigger: 'change' }],
  careLevel: [{ required: true, message: '请选择护理等级', trigger: 'change' }],
  careDuration: [{ required: true, message: '请输入关爱时长', trigger: 'blur' }],
  careEffect: [{ required: true, message: '请选择关爱效果', trigger: 'change' }]
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
      form.caregiverName = response.realName || response.username
    }
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

const loadCareRecord = async (recordId) => {
  loading.value = true
  try {
    const response = await getCareRecordById(recordId)
    if (response) {
      existingRecordId.value = response.id
      
      const data = { ...response }
      if (data.lifeCareItems && typeof data.lifeCareItems === 'string') {
        data.lifeCareItems = data.lifeCareItems.split(',').map(d => d.trim()).filter(d => d)
      }
      if (data.mentalCareContent && typeof data.mentalCareContent === 'string') {
        data.mentalCareContent = data.mentalCareContent.split(',').map(d => d.trim()).filter(d => d)
      }
      Object.assign(form, data)
      
      await loadUserInfo()
    }
  } catch (error) {
    console.error('加载关爱记录失败:', error)
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
        const submitData = {
        ...form,
        elderlyName: elderlyName.value,
        careContent: `关爱类型：${form.careType}，护理等级：${form.careLevel}，关爱时长：${form.careDuration}小时，生活照料：${Array.isArray(form.lifeCareItems) ? form.lifeCareItems.join('、') : form.lifeCareItems}`,
        lifeCareItems: Array.isArray(form.lifeCareItems) ? form.lifeCareItems.join(',') : form.lifeCareItems,
        mentalCareContent: Array.isArray(form.mentalCareContent) ? form.mentalCareContent.join(',') : form.mentalCareContent
      }

        if (existingRecordId.value) {
          await updateCareRecord(existingRecordId.value, submitData)
        } else {
          await createCareRecord(submitData)
        }
        ElMessage.success('保存成功')
        goBack()
      } catch (error) {
        console.error('保存失败:', error)
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
  loadUserInfo()
  
  if (route.params.id) {
    loadCareRecord(route.params.id)
  }
})
</script>

<style scoped>
.care-record {
  padding: 20px;
}

.record-card {
  background: white;
}
</style>
