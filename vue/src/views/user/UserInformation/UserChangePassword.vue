<template>
  <div>
    <div style="display: flex; justify-content: center">
      <div style="width: 400px; height: 300px; margin-top: 100px" class="border-style">
        <div style="display: flex">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="80px" style="padding-top: 60px; margin-left: 55px">
            <el-form-item label="旧密码" prop="oldpass">
              <el-input clearable style="width: 180px" v-model="ruleForm.oldpass"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="password">
              <el-input show-password clearable style="width: 180px" type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码确认" prop="checkpass">
              <el-input show-password clearable style="width: 180px" type="password" v-model="ruleForm.checkpass" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div style="text-align: center">
          <el-button type="danger" @click="save('ruleForm')">修改</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "UserChangePassword",
  data(){
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkpass !== '') {
          this.$refs.ruleForm.validateField('checkpass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      user: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      ruleForm: {
        id:'',
        oldpass: '',
        password: '',
        checkpass: '',
      },
      rules: {
        oldpass: [
          { required: true, message: '请输入原来的密码',trigger: 'blur' },
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' },
          { min: 6, max: 16, message: '密码应为6~16位', trigger: 'blur' }
        ],
        checkpass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    save(ruleform){
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          alert('submit!');
          this.ruleForm.id=this.user.id
          request.post('/user/passwordrequest', this.ruleForm).then(res => {
            if (res.code === '200') {
              request.put('/user/updatepassword/' + this.user.id + '/' + this.ruleForm.password).then(res => {
                if (res.code === '200') {
                  this.$notify.success('修改成功,请重新登录');
                  this.$router.push('/login');
                } else{
                  this.$notify.error(res.msg)
                }
              })
            } else{
              this.$notify.error(res.msg)
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
.border-style {
  border: 2px solid #79BBFF;
  border-radius: 10px
}
</style>