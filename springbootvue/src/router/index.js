import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/login',
    children:[
      {
        path:'home',
        name:'Home',
        component: () => import('@/views/HomeView'),
      },
      {
        path: 'score',
        name:'Score',
        component: () => import('@/views/ScoreView'),
        hidden: true
      }
    ]
  },

  {
    path: '/login',
    component: () => import('@/views/LoginView'),
    hidden: true
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
