<!--显示一级路由-->
<template>
  <div id = "app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  // computed:{
  //   key() {
  //     return this.$route.name !== undefined? this.$route.name + +new Date(): this.$route + +new Date()
  //   }
  // }
}
</script>