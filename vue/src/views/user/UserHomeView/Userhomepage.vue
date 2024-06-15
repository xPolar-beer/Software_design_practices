<template>
  <div class = "box">
    <div class = "left">
      <div class="center" style="margin-top: 150px; margin-left: 50px">
        <el-button
            type="info"
            class = "size"
            circle plain
            :disabled = !disabled
            @click="call()">呼叫</el-button>
        <dialog-component v-if="Visiable" ref="dialog"></dialog-component>
      </div>
      <div class="center" style="margin-top: 30px; margin-left: 50px">
        <el-button
            type="warning"
            class = "size"
            circle plain
            :disabled = !disabled
            @click="fix()">报修</el-button>
      </div>
    </div>
    <div class = "right">
      <div style = "min-height: calc(100vh - 82px); overflow: hidden; background-color: #F6F7F9">
        <div class="block" style="padding-top: 20px; display: flex; justify-content: center;">
          <el-carousel height="500px" style="width: 1000px">
            <el-carousel-item v-for="item in image" :key="item">
              <img v-if="item.image" :src="item.image" class="avatar">
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import dialogComponent from "./Call";
import Cookies from "js-cookie";

export default {
  name: "Userhomepage",
  components:{
    dialogComponent,
  },
  data() {
    return {
      tp: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      user: [],
      disabled: 0,
      roomnumber: '',
      Visiable:false,
      image:[],
    }
  },

  methods: {
    //呼叫
    call(){
      this.Visiable=true;
      this.$nextTick(()=>{
        //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
        //init调用的是dialog-component组件里面的init方法
        //data是传递给弹窗页面的值
        this.$refs.dialog.init(this.user, this.tp.id);
      })
    },

    //报修
    fix(){
      request.put('/room/updatenotuse/' + this.tp.id).then(res => {
        if(res.code == '200'){
          this.$message({
            message: '报修信息已发送，请等待处理',
            type: 'success',
            center: true,
            showClose: true,
          });
        }
        else{
          this.$notify.error(res.msg)
        }
      })
    }
  },

  created() {
    request.get('/user/' + this.tp.id).then(res => {
      if(res.code === '200'){
        this.user = res.data
        this.disabled = this.user.state
      }
      else{
        this.$notify.error(res.msg)
      }
    })
    request.get('/homeview/list').then(res => {
      this.image = res.data
      if(res.code === '200'){
      }
    })
  }
}
</script>

<style scoped>
.size{
  font-size: 25px;
  padding: 20px 20px;
}
.box{
  overflow: hidden;
}
.left {
  float: left;
  width: 200px;
  background-color: #F6F7F9;
  height: 563px;
}
.right {
  margin-left: 210px;
  background-color: #F6F7F9;
  height: 563px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 1000px;
  height: 500px;
  display: block;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>