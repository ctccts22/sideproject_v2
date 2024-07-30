import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import HomePage from '../pages/HomePage.vue';
import AppHeader from '../layouts/AppHeader.vue';
import AppFooter from '../layouts/AppFooter.vue';
import AppLogin from '../pages/AppLogin.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'HomePage',
    components: {
      default: HomePage,
      appHeader: AppHeader,
      appFooter: AppFooter
    },
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/login',
    components: {
      default: AppLogin
    },
    meta: {
      requiresAuth: false
    }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, _, next) => {
  if (to.meta.requiresAuth && !isAuthenticated()) {
    next('/login');
  } else {
    next();
  }
});

function isAuthenticated() {
  return false;
}

export default router;
