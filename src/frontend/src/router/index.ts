import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import MaterialView from '@/views/materials/MaterialView.vue'
import CandidateView from '@/views/candidates/CandidateView.vue'
import GrantsView from '@/views/grants/GrantsView.vue'
import GrantDetails from '@/views/grants/GrantDetails.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: HomeView
    },
    {
      path: '/contact',
      name: 'contact',
      component: HomeView
    },
    {
      path: '/materials',
      name: 'materials',
      component: MaterialView
    },
    {
      path: '/register',
      name: 'register',
      component: HomeView
    },
    {
      path: '/profile',
      name: 'profile',
      component: HomeView
    },
    {
      path: '/admin',
      name: 'admin',
      component: HomeView
    },
    {
      path: '/candidates',
      name: 'candidates',
      component: CandidateView
    },
    {
      path: '/grants',
      name: 'grants',
      component: GrantsView
    },
    {
      path: '/grant/:id',
      name: 'grant-details',
      component: GrantDetails,
    },
  ]
})

export default router
