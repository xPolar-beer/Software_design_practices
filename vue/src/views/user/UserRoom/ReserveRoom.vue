<template>
  <div style="min-height: calc(100vh - 160px); overflow: hidden; position: relative">
    <div style="margin-left: 300px; margin-top: 20px">
      <el-page-header @back="goBack(id)" content="详情页面">
      </el-page-header>
    </div>
    <div style="display: flex; justify-content: center; margin-top: 20px">
      <div style="width: 800px; height: 350px;" class="border-style">
        <div style="display: flex; flex-direction: row;">
          <div style="background-color: #F6F7F9; margin-left: 50px; margin-top: 40px; width: 300px; height: 240px">
            <el-form :model="form" style="background-color: #F6F7F9">
              <el-form-item label-width="100px" label="客房房号">
<!--                <el-select v-model="form.roomname" placeholder="请输入房号">-->
<!--                  <el-option label="101" value="101"></el-option>-->
<!--&lt;!&ndash;                  <el-option label="区域二" value="beijing"></el-option>&ndash;&gt;-->
<!--                </el-select>-->
                <el-select v-model="form.roomname" style="width: 200px; height: 36.75px">
                  <el-option :key="item" v-for="item in roomsnumber" :value="item">{{item}}</el-option>
                </el-select>
              </el-form-item>
              <el-form-item label-width="100px" label-size="40px" label="客房类型">
                <el-input v-model="form.style" style="width: 200px" disabled></el-input>
              </el-form-item>
              <el-form-item label-width="100px" label-size="40px" label="客房价格">
                <el-input v-model="form.price" style="width: 200px" disabled></el-input>
              </el-form-item>
              <el-form-item label-width="100px" label-size="40px" label="预定时间">
                <el-date-picker type="date" placeholder="选择日期" v-model="form.time"
                                value-format="yyyy-MM-dd" :picker-options="pickerOptions"
                                style="width: 200px"></el-date-picker>
              </el-form-item>
            </el-form>
          </div>
          <div style="background-color: #F6F7F9; margin-left: 50px; margin-top: 40px; width: 300px; height: 240px">
            <el-form :model="form">
              <el-form-item label-width="100px" label="用户姓名">
                <el-input v-model="form.name" style="width: 200px" disabled></el-input>
              </el-form-item>
              <el-form-item label-width="100px" label="用户电话">
                <el-input v-model="form.phone" style="width: 200px" disabled></el-input>
              </el-form-item>
              <el-form-item label-width="100px" label="用户余额">
                <el-input v-model="form.money" style="width: 200px" disabled></el-input>
              </el-form-item>
              <el-form-item label-width="100px" label="预定天数">
                <el-input v-model="form.day" style="width: 200px"></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div style="display: flex; justify-content: center;">
          <hr style="border: 0.1mm solid;width:790px; color: #79BBFF"/>
        </div>
        <div style="margin-left: 600px; margin-top: -10px">
          <el-button type="primary" @click="save(form.money, form.price, parseInt(form.day))">预订</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";
import moment from 'moment';

export default {
  name: "ReserveRoom",
  data() {
    return {
      params:{
        userid: '',
        roomnumber: '',
        ordernumber: '',
      },
      ID: '',
      ordernumber: '',
      user: {},
      roomstyle: {},
      rooms: [],
      roomsnumber: [],
      order:{},
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 3600 * 1000 * 24;
        },
      },
      form: {
        id: '',
        roomname: '',
        style: '',
        price: '',
        name: '',
        phone: '',
        money: '',
        day: '',
        time: '',
      },
    }
  },
  methods: {
    save(money, price, day){
      if(money < price * day){
        this.$notify.error("余额不足，预订失败")
      } else {
        var temp = money - price * day;
        this.order.userid = JSON.parse(Cookies.get('admin')).id;
        this.order.roomnumber = this.form.roomname;
        this.order.style = this.form.style;
        this.order.money = price * day;
        // = this.form.time
        this.order.reservetime = this.form.time.toString();
        this.order.day = day;
        request.post('/order/save', this.order).then(res => {
          if (res.code === '200') {
            this.ordernumber = res.data.toString();
            request.put('/room/updateup/' + this.order.userid + '/' + this.order.roomnumber).then(res2 => {
              if(res2.code === '200') {
                request.put('/room/updateordernumber/' + this.ordernumber + '/' + this.order.roomnumber).then(res3 => {
                  if(res3.code === '200'){
                    this.$notify.success('预订成功');
                    request.put('/user/updatemoney/' + temp  + '/' + this.order.userid);
                    request.put('/roomstyle/updatenumber/' + this.order.style);
                    this.$router.push('/user/getroom')
                    // this.$router.go(0)
                  } else {
                    this.$notify.error(res.msg)
                  }
                })
              } else {
                this.$notify.error(res.msg)
              }
            })
          } else{
            this.$notify.error(res.msg)
          }
        });
      }
    },

    goBack(id){
      this.$router.push('/user/getroom')
    },
  },
  created() {
    this.ID = this.$route.query.id;
    request.get("/roomstyle/" + this.ID).then(res => {
      this.roomstyle = res.data;
      this.form.style = this.roomstyle.style;
      this.form.price = this.roomstyle.price;
    })
    request.get("/user/" + (JSON.parse(Cookies.get('admin'))).id).then(res => {
      this.user = res.data;
      this.form.name = this.user.name;
      this.form.phone = this.user.phone;
      this.form.money = this.user.money;
    })
    request.get("/room/" + this.ID).then(res => {
      this.roomsnumber = JSON.parse(JSON.stringify(res.data));
    })

  }
}
</script>

<style scoped>
.border-style {
  border: 2px solid #79BBFF;
  border-radius: 10px
}
</style>