import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../pages/HomePage.vue'
import AppHeader from '../layouts/AppHeader.vue'
import AppFooter from "../layouts/AppFooter.vue";

const routes = [
  {
    path: '/',
    components: {
      default: HomePage,
      appHeader: AppHeader,
      appFooter: AppFooter,
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
