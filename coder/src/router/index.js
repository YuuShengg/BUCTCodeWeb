import Vue from 'vue'
import VueRouter from 'vue-router'
import StudentIndex from '../views/StudentIndex.vue'
import SelectAll from '../views/studentBox/SelectAll.vue'
import StuInfo from '../views/studentBox/StuInfo'
import Home from '../views/Home.vue'
import CodeforcesInfo from '../views/studentBox/CodeforcesInfo.vue'

Vue.use(VueRouter)

// 解决在使用ElementUI中的导航时，默认情况下如果重复点击某选项，会报错。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
const originalReplace = VueRouter.prototype.replace
VueRouter.prototype.replace = function replace(location) {
  return originalReplace.call(this, location).catch(err => err)
}

const routes = [{
  path: '/',
  component: StudentIndex,
  redirect: '/Home',
  children: [{
    path: '/SelectAll',
    name: 'SelectAll',
    component: SelectAll
  }, {
    path: '/Home',
    name: 'Home',
    component: Home
  }, {
    path: '/StuInfo',
    name: 'StuInfo',
    component: StuInfo
  }, {
    path: '/CodeforcesInfo',
    name: 'CodeforcesInfo',
    component: CodeforcesInfo
  }
  ]
}, 
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
