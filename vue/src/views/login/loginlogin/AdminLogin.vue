<template>
  <div style="background-color: #F2F6FC">
    <h1 class="center">登录管理端</h1>
    <el-form :model="admin">
      <el-form-item prop="phone">
        <el-input size="large" placeholder="请输入电话号码" prefix-icon="el-icon-s-custom" style="width: 300px; margin-left: 65px" v-model="admin.phone"></el-input>
        <el-input size="large" placeholder="请输入密码" prefix-icon="el-icon-lock" style="width: 300px; margin-left: 65px; margin-top: 20px" show-password v-model="admin.password"></el-input>
      </el-form-item>
    </el-form>
    <el-button type="primary"style="width: 300px; margin-left: 65px" @click="login">登录</el-button>
  </div>
</template>

<script>

import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "AdminLogin",
  data() {
    return {
      admin: {},
      temp_admin: {},
    }
  },
  methods: {
    login() {
      request.post('/admin/login', this.admin).then(res => {
        if (res.code === '200') {
          this.$notify.success("登录成功")
          if (res.data !== null) {
            //Cookies.set('user', obj)
            this.temp_admin = res.data
            Cookies.set('admin', JSON.stringify(res.data))
          }
          this.$router.push('/')
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>