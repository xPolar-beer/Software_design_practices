<template>
  <el-dialog
      title="住户信息"
      :visible.sync="detailVisible"
      width="800px"
      center>
    <el-form status-icon ref="form" label-width="100px" style="margin-left: 0px" :rules="rules">
      <el-descriptions class="margin-top" title="住户信息" :column="3" :size="size" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>姓名
          </template>
          {{form.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号
          </template>
          {{form.phone}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-female"></i>
            性别
          </template>
          {{form.sex}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            备注
          </template>
          <el-tag size="small">普通用户</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-time"></i>
            入住时间
          </template>
          {{time}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-timer"></i>
            入住天数
          </template>
          {{days}}
        </el-descriptions-item>
      </el-descriptions>
    </el-form>
    <div style="text-align: center; padding-top: 50px">
      <el-button type="primary" @click="clear">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "CheckUserInfo",
  data() {
    return {
      userid: '',
      form: {},
      time: '',
      days: '',
      detailVisible: false,
    }
  },
  mounted() {
    window.Vue3 = this;
  },
  methods: {
    initt(id, time, day) {
      this.detailVisible=true;
      this.userid = id;
      this.time = time;
      this.days = day;
      request.get("/user/" + this.userid).then(res => {
        this.form = res.data;
      })
      // this.form.intime = time;
      // this.form.day = day;
    },
    clear() {
      this.detailVisible=false;
    },
  }
}
</script>

<style scoped>

</style>