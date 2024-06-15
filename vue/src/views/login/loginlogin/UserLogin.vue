<template>
  <div style="background-color: #F2F6FC">
    <h1 class="center">登录您的账户</h1>
    <el-form :model="user" ref="loginForm">
      <el-form-item prop="phone">
        <el-input size="large" placeholder="请输入电话号码" prefix-icon="el-icon-user" style="width: 300px; margin-left: 65px"
                  v-model="user.phone"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input size="large" placeholder="请输入密码" prefix-icon="el-icon-lock" style="width: 300px; margin-left: 65px" show-password
                  v-model="user.password"></el-input>
      </el-form-item>
    </el-form>
    <el-button type="success"style="width: 300px; margin-left: 65px" @click="login">登录</el-button>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'

export default {
  name: "UserLogin",
  data() {
    return {
      user: {},
      admin: {},
    }
  },
  mounted() {
    window.Vue1 = this;
  },
  methods: {
    login() {
      request.post('/user/the/login', this.user).then(res => {
        if (res.code === '200') {
          this.$notify.success("登录成功")
          if (res.data !== null) {
            //Cookies.set('user', obj)
            //   console.log(res.data);
              this.admin = res.data;
              Cookies.set('admin', JSON.stringify(res.data))
          }
          this.$router.push('/user')
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