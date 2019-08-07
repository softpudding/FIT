import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Table from '@/components/Table'
import ManageUser from '@/components/ManageUser'
import ChangeNews from '@/components/ChangeNews'
import AddNews from '@/components/AddNews'

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
    {
      path: '/main',
      name: Table,
      component: Table
    },
    {
      path: '/ChangeNews',
      name: ChangeNews,
      component: ChangeNews
    },
    {
      path: '/AddNews',
      name: AddNews,
      component: AddNews
    }
  ]
})
