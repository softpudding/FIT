import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import ManageUser from '@/components/ManageUser'

Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/ManageUser',
      name: 'ManageUser',
      component: ManageUser
    },
  ]
})
