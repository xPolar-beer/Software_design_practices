<template>
  <div style="margin-left: 100px">
    <div style = "display: flex; flex-direction: row; background-color: white; margin-bottom: 20px; margin-left: 20px">
      <div>
        <el-upload
            class="avatar-uploader"
            :action="'http://localhost:9090/api/user/the/file/upload?token=' + this.admin.token"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
          <img v-if="form.avatar" :src="form.avatar" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <div style="margin-left: 20px">
        <div style="padding-top: 30px; font-size: 30px">{{username}}</div>
        <div style="padding-top: 5px; margin-left: 2px">普通用户</div>
      </div>
    </div>
    <div style="background-color: white; font-size: 20px;margin-bottom: 20px; margin-left: 20px">
      <i class="el-icon-wallet"></i>
      <span style="margin-left: 5px">我的余额: {{form.money}}</span>
      <el-button type="primary" style="width: 70px; font-size: 12px; height: 30px; margin-left: 20px" round @click="up">充值</el-button>
    </div>
    <div style="margin-bottom: 30px">
      <span style="font-weight: bold; font-size: 25px;margin-left: 20px">基本信息</span>
    </div>
    <div style = "display: flex; flex-direction: row;">
      <div style="margin-left: 50px">
        <el-form :model="form" label-width="70px">
          <el-form-item label="用户昵称" style="width: 70%">
            <el-input v-model="form.username" placeholder="请输入昵称" style="width: 300px; margin-left: 10px"></el-input>
          </el-form-item>
          <el-form-item label="电话" style="width: 70%">
            <el-input v-model="form.phone" placeholder="请输入电话" disabled style="width: 300px; margin-left: 10px"></el-input>
          </el-form-item>
          <el-form-item label="注册时间" style="width: 70%" >
            <el-input v-model="form.createtime" disabled style="width: 300px; margin-left: 10px"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div style="margin-left: 150px">
        <el-form :model="form" label-width="70px">
          <el-form-item label="姓名" style="width: 70%">
            <el-input v-model="form.name" disabled style="width: 300px; margin-left: 10px"></el-input>
          </el-form-item>
          <el-form-item label="性别" style="width: 70%">
            <el-input v-model="form.sex" disabled placeholder="请输入性别" style="width: 300px; margin-left: 10px"></el-input>
          </el-form-item>
          <el-form-item label="出生日期" style="width: 70%" >
            <el-input v-model="form.birthday" disabled style="width: 300px; margin-left: 10px"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div style="margin-left: 400px; padding-top: 30px;">
      <el-button type="primary" @click="update" style="width: 100px; height: 50px" icon="el-icon-upload2">修改</el-button>
      <el-button @click="clear" style="margin-left: 50px; width: 100px; height: 50px">取消</el-button>
    </div>
  </div>

</template>

<script>
import Cookies from "js-cookie"
import request from "@/utils/request";
export default {
  name: "UserInfo",
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      form: {},
      username: '',
      temp_form: {},
    };
  },
  created() {
    this.form.id = this.admin.id;
    request.get("/user/" + this.form.id).then(res => {
      this.form = res.data
      this.username = this.form.username;
    })
  },

  methods: {

    up(){
      {
        this.$prompt('请输入充值金额', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputErrorMessage: '请输入数值',
          inputValidator: value => {
            const r = /^\+?[1-9][0-9]*$/;
            if(value === '' || !r.test(value)){
              return false;
            } else {
              return true;
            }
          },
        }).then(({ value }) => {
          request.put("/user/updatemoneymoney/"  + this.form.id + '/' + value).then( res => {
            request.get("/user/" + this.form.id).then(res => {
              this.form = res.data
            })
            this.$message({
              type: 'success',
              message: '充值成功，充值金额为: ' + value
            });
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消充值'
          });
        });
      }
    },

    handleAvatarSuccess(res) {
      if (res.code === '200') {
        console.log(res.data)
        this.$set(this.form, 'avatar', res.data);
        // this.form.avatar = res.data;
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 或者 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

    update() {
      request.put('/user/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('修改成功');
          request.get("/user/" + this.form.id).then(res => {
            this.form = res.data
          })
          this.username = this.form.username;
          this.$router.go(0);
        } else{
          this.$notify.error(res.msg)
        }
      });
      //第二个变量为传入后台的数据
    },
    clear() {
      request.get("/user/" + this.form.id).then(res => {
        this.form = res.data
      })
    }
  }
}
</script>

<style>
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
  width: 128px;
  height: 128px;
  line-height: 128px;
  text-align: center;
}
.avatar {
  width: 128px;
  height: 128px;
  display: block;
}
</style>

