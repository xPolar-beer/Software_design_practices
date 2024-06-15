<!--用来嵌套主页的内容-->

<template>
  <div style="background-color: #F6F7F9">
    <!-- 头部区域 -->
    <div style = "display: flex; flex-direction: row; height: 80px; line-height: 100px; background-color: #aed6f1 ; margin-bottom: 0px">
        <div style="margin-right: 10px; width: 60px;">
          <img src = "@/assets/logo.png" style = "width: 100px; position: relative; top: -10px; left: 20px">
        </div>
        <div style="width: 260px; align-self: center;">
          <span style = "margin-left: 20px; margin-top: 50px; font-size: 22px;color: #1a1e21">XXXX酒店(用户端)</span>
        </div>
        <div style="width: 1000px; align-self: center;" align="right">
          <el-dropdown size="large">
            <span class="el-dropdown-link" style="cursor: pointer; font-size: 20px;color: #1a1e21">
             {{form.username}}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown" style="margin-top: -20px">
              <el-dropdown-item><div style="width: 50px; text-align: center;" @click="logout">退出</div></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-avatar :src="src" style="margin-top: 10px; margin-left: 20px" :size="60" ></el-avatar>
    </div>
<!--    #F4F4F5-->

<!--    #E4E7E0-->
    <div style="background-color: #d6eaf8 ">
      <!--    顶栏-->
      <div style="display: flex;">
        <el-menu style="height: 50px; margin-left: 570px":default-active="$route.path" router class="el-menu-demo" mode="horizontal"
                 background-color="#d6eaf8 "
                 text-color="#303133"
                 active-text-color="#6699CC"
                 @select="handleSelect">
          <el-menu-item index="/user/homepage" style="width: 100px; font-size: 20px; height: 50px" class="center">首页</el-menu-item>
          <el-menu-item index="/user/getroom" style="width: 100px; font-size: 20px; height: 50px" class="center">客房</el-menu-item>
          <el-menu-item index="/user/layout" style="width: 100px; font-size: 20px; height: 50px" class="center">我的</el-menu-item>
        </el-menu>
      </div>
      <div style = "flex: 1; background-color: #F6F7F9; padding: 20px;">
        <router-view/>
      </div>
    </div>


    <!-- 侧边栏和主体 -->
  </div>
</template>

<script>
import {ref} from "vue";
import request from "@/utils/request";
import Cookies from 'js-cookie'

export default {
  name: "UserLayout",
  data() {
    return {
      user: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      form:{},
      src: ""
    }
  },
  created() {
    const id = this.user.id
    request.get("/user/" + id).then(res => {
      this.form = res.data
      this.src = this.form.avatar
    })
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