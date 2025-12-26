import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import ElderlyManage from '../views/ElderlyManage.vue'
import HealthAssessment from '../views/HealthAssessment.vue'
import EnvironmentAssessment from '../views/EnvironmentAssessment.vue'
import CareRecord from '../views/CareRecord.vue'
import HealthAssessmentManage from '../views/HealthAssessmentManage.vue'
import EnvironmentAssessmentManage from '../views/EnvironmentAssessmentManage.vue'
import CareRecordManage from '../views/CareRecordManage.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/',
    component: Layout,
    redirect: '/elderly-manage',
    children: [
      {
        path: 'elderly-manage',
        name: 'ElderlyManage',
        component: ElderlyManage
      },
      {
        path: 'health-assessment-manage',
        name: 'HealthAssessmentManage',
        component: HealthAssessmentManage
      },
      {
        path: 'environment-assessment-manage',
        name: 'EnvironmentAssessmentManage',
        component: EnvironmentAssessmentManage
      },
      {
        path: 'care-record-manage',
        name: 'CareRecordManage',
        component: CareRecordManage
      },
      {
        path: 'health-assessment/:elderlyId',
        name: 'HealthAssessment',
        component: HealthAssessment,
        props: true
      },
      {
        path: 'environment-assessment/:elderlyId',
        name: 'EnvironmentAssessment',
        component: EnvironmentAssessment,
        props: true
      },
      {
        path: 'care-record/:elderlyId',
        name: 'CareRecordDetail',
        component: CareRecord,
        props: true
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  if (to.path !== '/login' && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/')
  } else {
    next()
  }
})

export default router
