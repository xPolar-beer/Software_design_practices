<template>
  <div style="height: 600px; overflow: hidden">
    <div style="width: 450px; height: 600px; background-color: white; border-radius: 10px; margin: 90px auto;">
      <div>
        <el-form :model="form" status-icon ref="form" label-width="100px" style="margin-left: 25px" :rules="rules">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户昵称" style="width: 250px;"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" show-password autocomplete="off" placeholder="请输入密码" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkpass">
            <el-input v-model="form.checkpass" show-password autocomplete="off" placeholder="请再次输入密码" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入姓名" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入电话" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="form.sex" style="margin-left: 20px">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="出生日期" prop="birthday">
            <el-date-picker v-model="form.birthday" type="date" placeholder="选择日期" :picker-options="pickerOptions" style="width: 250px">出生日期</el-date-picker>
          </el-form-item>
        </el-form>
        <div style="text-align: center">
          <el-button type="primary" @click="save('form')">添加</el-button>
          <el-button @click="clear('form')">重置</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AddUser",
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error('请输入合法的手机号'));
      }
      callback()
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.form.checkpass !== '') {
          this.$refs.form.validateField('checkpass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      form: {
        username: '',
        password: '',
        checkpass: '',
        name: '',
        phone: '',
        sex: '',
        birthday: '',
      },
      rules:{
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {validator: validatePass, required: true, trigger: 'blur' },
          { min: 6, max: 16, message: '密码应为6~16位', trigger: 'blur' }
        ],
        checkpass: [
          {validator: validatePass2, required: true, trigger: 'blur' }
        ],
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
        ],
        phone: [
          {validator: checkPhone,required: true,trigger: 'blur'},
        ],
        sex: [
          {required: true, message: '请输入性别', trigger: 'change'},
        ],
        birthday: [
          {required: true, message: '请输入出生日期', trigger: 'blur'},
        ],
      },
    }
  },

  methods: {
    save(formName) {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          alert('submit!');
          request.post('/user/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('添加成功');
              this.form = {};
            } else{
              this.$notify.error(res.msg)
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    clear(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>
