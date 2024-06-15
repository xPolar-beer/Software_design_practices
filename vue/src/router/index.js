import Vue from 'vue'
import VueRouter from 'vue-router'
import UserLayout from "@/views/user/UserLayout";
import AdminLayout from '../views/admin/AdminLayout.vue'
import Login from "@/views/login/Login.vue";
import Cookies from "js-cookie"
import MenuView from "@/views/MenuView";

Vue.use(VueRouter)

const routes = [
    // ====== 注册 ======
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/login/Register')
  },
  {
    path: '/blank',
    name: 'Blank',
    component: () => import('../views/blank')
  },

    // ====== 登录 ======
  {
    path: '/login',
    name: 'Login',
    redirect: '/userlogin',
    component: Login,
    children: [
        // 用户登录界面
      {
        path: '/userlogin',
        name: 'UserLogin',
        component: () => import('../views/login/loginlogin/UserLogin')
      },
      {
        path: '/adminlogin',
        name: 'AdminLogin',
        component: () => import('../views/login/loginlogin/AdminLogin')
      },
    ]
  },
  {
    path: '/menu',
    name: 'menu',
    component: () => import('../views/MenuView')
  },

// 用户端
  {
    path: '/user/',
    name: 'UserLayout',
    redirect: '/user/homepage',
    component: UserLayout,
    children: [
      {
        path: '/user/homepage',
        name: 'UserHomePage',
        component: () => import('../views/user/UserHomeView/Userhomepage'),
      },
      {
        path: '/user/getroom',
        name: 'UserGetRoom',
        component: () => import('../views/user/UserRoom/UserGetRoom'),
      },
      {
        path: '/user/reserve',
        name: 'UserReserve',
        component: () => import('../views/user/UserRoom/UserReserveRoom'),
      },
      {
        path: '/user/reserveroom',
        name: 'UserReserveRoom',
        component: () => import('../views/user/UserRoom/ReserveRoom'),
      },
      {
        path: '/user/layout',
        name: 'UserInfoLayout',
        redirect: '/user/info',
        component: () => import('../views/user/UserInformation/UserInfoLayout'),
        children: [
          {
            path: '/user/comment',
            name: 'UserCommrnt',
            component: () => import('../views/user/UserInformation/UserComment'),
          },
          {
            path: '/user/info',
            name: 'UserInfo',
            component: () => import('../views/user/UserInformation/UserInfo'),
          },
          {
            path: '/user/needinfo',
            name: 'UserNeedInfo',
            component: () => import('../views/user/UserInformation/UserNeedInfo'),
          },
          {
            path: '/user/orders',
            name: 'UserOrders',
            component: () => import('../views/user/UserInformation/UserOrders'),
          },
          {
            path: '/user/collect',
            name: 'UserCollect',
            component: () => import('../views/user/UserInformation/UserCollect'),
          },
          {
            path: '/user/changepassword',
            name: 'UserChangepassword',
            component: () => import('../views/user/UserInformation/UserChangePassword'),
          },
        ]
      },

        // 个人信息编辑
    ]
  },
    // 管理端
  {
    path: '/',
    name: 'AdminLayout',
    redirect: '/home',
    component: AdminLayout,
    children: [
      //首页
      {
        path: '/home',
        name: 'Home',
        component: () => import('../views/admin/HomeView'),
      },
        //用户端展示信息管理
      {
        path: '/usershowedit',
        name: 'UserShowEdit',
        component: () => import('../views/admin/usershow_management/UserShowEdit'),
      },
        //评论管理
      {
        path: '/usercommentedit',
        name: 'UserCommentEdit',
        component: () => import('../views/admin/usershow_management/EditComment'),
      },
        //------住房管理------
      {
        path: '/cleanroom',
        name: 'CleanRoom',
        component: () => import('../views/admin/room_management/CleanRoom'),
      },
      {
        path: '/outroom',
        name: 'OutRoom',
        component: () => import('../views/admin/room_management/OutRoom'),
      },
      {
        path: '/notcleanroom',
        name: 'NotCleanRoom',
        component: () => import('../views/admin/room_management/NotCleanRoom'),
      },
      {
        path: '/notuseroom',
        name: 'NotUseRoom',
        component: () => import('../views/admin/room_management/NotUseRoom'),
      },
      {
        path: '/editroom',
        name: 'EditRoom',
        component: () => import('../views/admin/room_management/EditRoom'),
      },
      {
        path: '/addroom',
        name: 'AddRoom',
        component: () => import('../views/admin/room_management/AddRoom'),
      },
      {
        path: '/checkuserinfo',
        name: 'CheckUserInfo',
        component: () => import('../views/admin/room_management/CheckUserInfo'),
      },
      //用户增加
      {
        path: '/adduser',
        name: 'AddUser',
        component: () => import('../views/admin/user_management/AddUser.vue'),
      },
      //用户查询
      {
        path: '/userfind',
        name: 'UserFind',
        component: () => import('../views/admin/user_management/UserFind.vue'),
      },
      //用户编辑
      {
        path: '/edituser',
        name: 'EditUser',
        component: () => import('../views/admin/user_management/EditUser.vue'),
      },
      //管理员增加
      {
        path: '/addadmin',
        name: 'AddAdmin',
        component: () => import('../views/admin/admin_management/AddAdmin'),
      },
      //管理员查询
      {
        path: '/adminfind',
        name: 'adminfind',
        component: () => import('../views/admin/admin_management/AdminFind'),
      },
      //管理员编辑
      {
        path: '/editadmin',
        name: 'EditAdmin',
        component: () => import('../views/admin/admin_management/EditAdmin'),
      },
      {
        path: '/orderfind',
        name: 'OrderFind',
        component: () => import('../views/admin/order_management/OrderFind'),
      },
      {
        path: '/goodsfind',
        name: 'GoodsFind',
        component: () => import('../views/admin/goods_management/GoodsFind'),
      },
      {
        path: '/infofind',
        name: 'InfoFind',
        component: () => import('../views/admin/goods_management/InfoFind'),
      },
    ]
  },
  //404
  {
    path: "*",
    component: () => import('../views/404')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
   if (to.path === '/login' || to.path === '/userlogin' || to.path === '/adminlogin' || to.path === '/register') next()
  const admin = Cookies.get("admin")
  if (!admin && to.path !== '/login' && to.path !== '/userlogin' && to.path !== '/adminlogin' && to.path !== '/register') return next("/login")
  next()
})

export default router
