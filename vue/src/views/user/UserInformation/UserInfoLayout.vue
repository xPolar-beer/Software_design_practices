<template>
  <div style = "display: flex; ">
    <!-- 侧边栏 -->
    <el-menu default-active="$route.path" router
             style="width: 200px; min-height: calc(100vh - 161px); overflow: hidden;">
      <el-menu-item index="/user/info">
        <i class="el-icon-user"></i>
        <span slot="title">我的信息</span>
      </el-menu-item>
      <el-menu-item index="/user/orders">
        <i class="el-icon-s-order"></i>
        <span slot="title">我的订单</span>
      </el-menu-item>
      <el-menu-item index="/user/needinfo">
        <i class="el-icon-bell"></i>
        <span slot="title">需求管理</span>
      </el-menu-item>
      <el-menu-item index="/user/comment">
        <i class="el-icon-chat-line-square"></i>
        <span slot="title">评论管理</span>
      </el-menu-item>
      <el-menu-item index="/user/collect">
        <i class="el-icon-s-flag"></i>
        <span slot="title">我的收藏</span>
      </el-menu-item>
      <el-menu-item index="/user/changepassword">
        <i class="el-icon-setting"></i>
        <span slot="title">修改密码</span>
      </el-menu-item>
    </el-menu>
    <!-- 主体数据 -->
    <div style = "flex: 1; background-color: white; padding: 10px;">
      <router-view/>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserInfoLayout",
  data() {
    return {
     isCollapse: true
   };
  },
  methods: {
  }
}
</script>

<style scoped>

</style>