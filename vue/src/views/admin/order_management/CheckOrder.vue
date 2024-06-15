<template>
  <el-dialog
      title="住户信息"
      :visible.sync="detailVisible"
      width="800px"
      center>
    <el-form status-icon ref="form" label-width="100px" style="margin-left: 0px" :rules="rules">
      <el-descriptions class="margin-top" title="住户信息" :column="3" :size="size" border>

<!--        订单号-->
        <el-descriptions-item>
          <template slot="label">
            <i class=""></i>订单号
          </template>
          {{form.number}}
        </el-descriptions-item>
        <!--        姓名-->
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            姓名
          </template>
          {{user.name}}
        </el-descriptions-item>
        <!--        电话-->
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            电话
          </template>
          {{user.phone}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-timer"></i>
            预订时间
          </template>
          {{form.reservetime}}
        </el-descriptions-item>
<!--        订单创建时间-->
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-time"></i>
            订单创建时间
          </template>
          {{form.establishtime}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-timer"></i>
            入住天数
          </template>
          {{form.day}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-timer"></i>
            房型
          </template>
          {{form.style}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-timer"></i>
            房号
          </template>
          {{form.roomnumber}}
        </el-descriptions-item>
        <!--        价格-->
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-timer"></i>
            价格
          </template>
          {{form.money}}
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
  name: "CheckOrder",
  data() {
    return {
      userid: '',
      form: {},
      use:{},
      time: '',
      days: '',
      detailVisible: false,
    }
  },
  mounted() {
    window.Vue3 = this;
  },
  methods: {
    init(id, userid) {
      this.detailVisible=true;
      request.get("/order/" + id).then(res => {
        this.form = res.data;
      })
      request.get("/user/" + userid).then(res => {
        this.user = res.data;
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