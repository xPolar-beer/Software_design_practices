<!--登录界面-->
<template>
  <!--  整个页面-->
  <div style="height: 100vh; overflow: hidden;" class="login_imgSrc" >
    <!--    登录框-->
    <div style="width: 450px; height: 350px; border-radius: 10px; margin: 90px auto; background-color: #F2F6FC;">
      <!--      顶栏和主体 -->
      <div style="display: flex; padding-top: 8px">
        <el-menu :default-active="$route.path" router class="el-menu-demo" mode="horizontal" background-color="#F2F6FC" >
          <el-menu-item index="/userlogin">用户端</el-menu-item>
          <el-menu-item index="/adminlogin">管理端</el-menu-item>
        </el-menu>
      </div>
      <div style = "flex: 1; background-color: white; padding: 10px; background-color: #F2F6FC;">
        <router-view/>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
    }
  },

  methods: {
    inRegister() {
      this.$router.push('/register');
    }
  },
}
</script>

<style scoped>
</style>
