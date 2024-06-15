<template>

</template>

<script>
export default {
  name: "blank",
  created() {
    this.$router.replace(this.$route.query.redirect);
  },
}
</script>

<style scoped>

</style>