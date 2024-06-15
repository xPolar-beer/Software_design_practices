<template>
  <div>
    <el-form :model="form" label-width="60%">
      <el-form-item label="用户名" style="width: 70%">
        <el-input v-model="form.username" disabled placeholder="请输入用户名" style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="姓名" style="width: 70%">
        <el-input v-model="form.name" disabled style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="电话" style="width: 70%">
        <el-input v-model="form.phone" disabled placeholder="请输入电话" style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="密码" style="width: 70%">
        <el-input v-model="form.password" disabled placeholder="请输入电话" style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="性别" style="width: 70%">
        <el-input v-model="form.sex" disabled placeholder="请输入性别" style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="注册时间" style="width: 70%" >
        <el-input v-model="form.birthday" disabled disabledplaceholder="请输入日期" style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="注册时间" style="width: 70%" >
        <el-input v-model="form.createtime" disabled disabledplaceholder="请输入日期" style="width: 70%"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button type="primary" @click="update">返回</el-button>
<!--      <el-button @click="">取消</el-button>-->
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "EditUser",
  data() {
    return {
      form: {

      }
    }
  },

  created(){
    const id = this.$route.query.id
    request.get("/user/" + id).then(res => {
      this.form = res.data
    })
  },

  methods: {
    update() {
      this.$router.push('/userfind')
    }
  },
}
</script>
