<!--用来嵌套主页的内容-->

<template>
  <div>
    <!-- 头部区域 -->
    <div style = "display: flex; height: 80px; line-height: 100px; background-color: white; margin-bottom: 2px">
      <div style="margin-right: 10px; width: 50px;">
        <img src = "@/assets/logo.png" style = "width: 100px; position: relative; top: -10px; left: 20px">
      </div>
      <div style="width: 260px; align-self: center;">
        <span style = "margin-left: 25px; margin-top: 5px; font-size: 22px;">XXXX酒店(管理端)</span>
      </div>
      <div style="width: 1000px; align-self: center;" align="right">
        <el-dropdown size="large">
          <span class="el-dropdown-link" style="cursor: pointer; font-size: 20px; color: #1a1e21">
            {{admin.name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" style="margin-top: -20px">
            <el-dropdown-item><div style="width: 50px; text-align: center;" @click="logout">退出</div></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <el-avatar style="margin-top: 10px; margin-left: 20px" :size="60">
        <span style="align-self: center; font-size: 20px">admin</span>
      </el-avatar>
    </div>
    <!-- 侧边栏和主体 -->
    <div style = "display: flex">
      <!-- 侧边栏 -->
      <div style = "width: 200px; min-height: calc(100vh - 82px); overflow: hidden; margin-right: 2px; background-color: #eeeeee">
        <el-menu default-active="$route.path" router class = "el-menu-demo" background-color = white>
          <!-- 首页 -->
          <el-menu-item index="/home/">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>首页</span>
            </template>
          </el-menu-item>

<!--          用户端展示信息管理-->
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-view"></i>
              <span>展示管理</span>
            </template>
            <el-menu-item index = "/usershowedit">
              <i class="el-icon-copy-document"></i>
              <span>首页轮播图</span>
            </el-menu-item>
            <el-menu-item index = "/usercommentedit">
              <i class="el-icon-chat-dot-square"></i>
              <span>用户评论</span>
            </el-menu-item>
          </el-submenu>

<!--          住房管理-->
          <el-submenu index = "3">
            <template slot = "title">
              <i class = "el-icon-s-home"></i>
              <span>住房管理</span>
            </template>
            <el-menu-item index = "/cleanroom">
              <i class="el-icon-circle-check"></i>
              <span>空干净房</span>
            </el-menu-item>
            <el-menu-item index = "/outroom">
              <i class="el-icon-circle-close"></i>
              <span>已入住房</span>
            </el-menu-item>
            <el-menu-item index = "/notcleanroom">
              <i class="el-icon-remove-outline"></i>
              <span>空脏房</span>
            </el-menu-item>
            <el-menu-item index = "/notuseroom">
              <i class="el-icon-time"></i>
              <span>检修房</span>
            </el-menu-item>
            <el-menu-item index = "/editroom">
              <i class="el-icon-tickets"></i>
              <span>住房信息</span>
            </el-menu-item>
          </el-submenu>

          <!--用户管理-->
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <span>用户管理</span>
            </template>
            <el-menu-item index = "/adduser">
              <i class="el-icon-plus"></i>
              <span>用户添加</span>
            </el-menu-item>
            <el-menu-item index = "/userfind">
              <i class="el-icon-search"></i>
              <span>用户查询</span>
            </el-menu-item>
          </el-submenu>

<!--          //管理员管理-->
          <el-submenu index="5">
            <template slot="title">
              <i class="el-icon-s-custom"></i>
              <span>管理员管理</span>
            </template>
            <el-menu-item index = "/addadmin">
              <i class="el-icon-plus"></i>
              <span>管理员添加</span>
            </el-menu-item>
            <el-menu-item index = "/adminfind">
              <i class="el-icon-search"></i>
              <span>管理员查询</span>
            </el-menu-item>
          </el-submenu>

<!--          订单管理-->
          <el-submenu index="6">
            <template slot="title">
              <i class="el-icon-edit-outline"></i>
              <span>订单管理</span>
            </template>
            <el-menu-item index = "/orderfind">
              <i class="el-icon-search"></i>
              <span>订单查询</span>
            </el-menu-item>
          </el-submenu>

<!--          物资管理-->
          <el-submenu index="7">
            <template slot="title">
              <i class="el-icon-box"></i>
              <span>物资管理</span>
            </template>
            <el-menu-item index = "/goodsfind">
              <i class="el-icon-truck"></i>
              <span>库存物资</span>
            </el-menu-item>
            <el-menu-item index = "/infofind">
              <i class="el-icon-bell"></i>
              <span>用户需求</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

      <!-- 主体数据 -->
      <div style = "flex: 1; background-color: white; padding: 10px">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";

export default {
  name: "AdminLayout",

  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }
  },
  mounted() {
    window.Vue22 = this;
  },
  methods: {
    logout() {
      Cookies.remove('admin')
      window.localStorage.clear()
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

</style>