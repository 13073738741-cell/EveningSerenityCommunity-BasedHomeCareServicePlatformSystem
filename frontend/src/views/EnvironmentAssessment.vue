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
            <el-form-item label="评估人" prop="assessor">
              <el-input v-model="form.assessor" placeholder="请输入评估人姓名" />
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
        
        <el-form-item label="居住面积" prop="area">
          <el-input v-model="form.area" placeholder="请输入居住面积（平方米）">
            <template #append>㎡</template>
          </el-input>
        </el-form-item>
        
        <el-form-item label="楼层情况" prop="floor">
          <el-input v-model="form.floor" placeholder="请输入楼层（如：3/6）" />
        </el-form-item>
        
        <el-form-item label="是否有电梯" prop="hasElevator">
          <el-radio-group v-model="form.hasElevator">
            <el-radio :label="true">有</el-radio>
            <el-radio :label="false">无</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="采光通风" prop="lighting">
          <el-rate v-model="form.lighting" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="室内温度" prop="temperature">
          <el-select v-model="form.temperature" placeholder="请选择室内温度情况" style="width: 100%">
            <el-option label="适宜" value="suitable" />
            <el-option label="偏冷" value="cold" />
            <el-option label="偏热" value="hot" />
            <el-option label="不稳定" value="unstable" />
          </el-select>
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
        
        <el-form-item label="照明设施" prop="lightingFacilities">
          <el-radio-group v-model="form.lightingFacilities">
            <el-radio label="good">良好</el-radio>
            <el-radio label="average">一般</el-radio>
            <el-radio label="poor">较差</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="消防安全" prop="fireSafety">
          <el-checkbox-group v-model="form.fireSafety">
            <el-checkbox label="灭火器" />
            <el-checkbox label="烟雾报警器" />
            <el-checkbox label="燃气报警器" />
            <el-checkbox label="应急照明" />
          </el-checkbox-group>
        </el-form-item>
        
        <el-form-item label="电路安全" prop="electricSafety">
          <el-radio-group v-model="form.electricSafety">
            <el-radio label="good">良好</el-radio>
            <el-radio label="average">一般</el-radio>
            <el-radio label="poor">较差</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-divider content-position="left">生活设施评估</el-divider>
        <el-form-item label="卫生间设施" prop="bathroom">
          <el-rate v-model="form.bathroom" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="厨房设施" prop="kitchen">
          <el-rate v-model="form.kitchen" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="供水情况" prop="waterSupply">
          <el-radio-group v-model="form.waterSupply">
            <el-radio label="good">稳定充足</el-radio>
            <el-radio label="average">基本满足</el-radio>
            <el-radio label="poor">不足或不稳定</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="供电情况" prop="powerSupply">
          <el-radio-group v-model="form.powerSupply">
            <el-radio label="good">稳定</el-radio>
            <el-radio label="average">偶尔中断</el-radio>
            <el-radio label="poor">经常中断</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="供暖/制冷" prop="hvac">
          <el-checkbox-group v-model="form.hvac">
            <el-checkbox label="空调" />
            <el-checkbox label="暖气" />
            <el-checkbox label="电暖器" />
            <el-checkbox label="无" />
          </el-checkbox-group>
        </el-form-item>

        <el-divider content-position="left">无障碍设施评估</el-divider>
        <el-form-item label="门宽是否适合轮椅" prop="doorWidth">
          <el-radio-group v-model="form.doorWidth">
            <el-radio :label="true">适合</el-radio>
            <el-radio :label="false">不适合</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="是否有坡道" prop="ramp">
          <el-radio-group v-model="form.ramp">
            <el-radio :label="true">有</el-radio>
            <el-radio :label="false">无</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="床铺高度" prop="bedHeight">
          <el-select v-model="form.bedHeight" placeholder="请选择床铺高度" style="width: 100%">
            <el-option label="适宜" value="suitable" />
            <el-option label="偏高" value="high" />
            <el-option label="偏低" value="low" />
          </el-select>
        </el-form-item>

        <el-divider content-position="left">卫生环境评估</el-divider>
        <el-form-item label="室内卫生" prop="indoorHygiene">
          <el-rate v-model="form.indoorHygiene" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="垃圾处理" prop="garbageDisposal">
          <el-radio-group v-model="form.garbageDisposal">
            <el-radio label="good">及时处理</el-radio>
            <el-radio label="average">偶尔堆积</el-radio>
            <el-radio label="poor">经常堆积</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="虫害防治" prop="pestControl">
          <el-radio-group v-model="form.pestControl">
            <el-radio label="good">良好</el-radio>
            <el-radio label="average">一般</el-radio>
            <el-radio label="poor">有虫害</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-divider content-position="left">周边环境评估</el-divider>
        <el-form-item label="交通便利" prop="transportation">
          <el-rate v-model="form.transportation" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="医疗资源" prop="medicalResources">
          <el-rate v-model="form.medicalResources" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="购物便利" prop="shopping">
          <el-rate v-model="form.shopping" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="社区服务" prop="communityService">
          <el-rate v-model="form.communityService" :max="5" show-text />
        </el-form-item>
        
        <el-form-item label="噪音水平" prop="noise">
          <el-select v-model="form.noise" placeholder="请选择噪音水平" style="width: 100%">
            <el-option label="安静" value="quiet" />
            <el-option label="一般" value="average" />
            <el-option label="嘈杂" value="noisy" />
          </el-select>
        </el-form-item>

        <el-divider content-position="left">评估总结</el-divider>
        <el-form-item label="综合评估结果" prop="overallResult">
          <el-select v-model="form.overallResult" placeholder="请选择综合评估结果" style="width: 100%">
            <el-option label="优秀" value="excellent" />
            <el-option label="良好" value="good" />
            <el-option label="一般" value="average" />
            <el-option label="较差" value="poor" />
            <el-option label="需要改善" value="need_improvement" />
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
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getElderlyById } from '../api/elderly'
import { createEnvironmentAssessment, updateEnvironmentAssessment } from '../api/environmentAssessment'

const router = useRouter()
const route = useRoute()
const formRef = ref(null)
const loading = ref(false)
const elderlyName = ref('')

const form = reactive({
  elderlyId: route.params.elderlyId,
  assessmentDate: new Date(),
  assessor: '',
  houseType: 'owned',
  area: '',
  floor: '',
  hasElevator: false,
  lighting: 4,
  temperature: 'suitable',
  floorSafety: 'good',
  handrails: [],
  lightingFacilities: 'good',
  fireSafety: [],
  electricSafety: 'good',
  bathroom: 4,
  kitchen: 4,
  waterSupply: 'good',
  powerSupply: 'good',
  hvac: [],
  doorWidth: false,
  ramp: false,
  bedHeight: 'suitable',
  indoorHygiene: 4,
  garbageDisposal: 'good',
  pestControl: 'good',
  transportation: 4,
  medicalResources: 4,
  shopping: 4,
  communityService: 4,
  noise: 'quiet',
  overallResult: 'good',
  problems: '',
  suggestions: '',
  notes: ''
})

const rules = {
  assessmentDate: [{ required: true, message: '请选择评估日期', trigger: 'change' }],
  assessor: [{ required: true, message: '请输入评估人姓名', trigger: 'blur' }],
  overallResult: [{ required: true, message: '请选择综合评估结果', trigger: 'change' }]
}

const loadElderlyInfo = async () => {
  try {
    const response = await getElderlyById(route.params.elderlyId)
    elderlyName.value = response.data?.name || response.name || ''
  } catch (error) {
    ElMessage.error('加载老人信息失败')
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        if (form.id) {
          await updateEnvironmentAssessment(form.id, form)
        } else {
          await createEnvironmentAssessment(form)
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
