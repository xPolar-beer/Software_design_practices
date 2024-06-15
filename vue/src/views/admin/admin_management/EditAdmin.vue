  <template>
  <div>
    <el-form :model="form" label-width="60%">
      <el-form-item label="姓名" style="width: 70%">
        <el-input v-model="form.name" disabled style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="电话" style="width: 70%">
        <el-input v-model="form.phone" placeholder="请输入电话" style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="性别" style="width: 70%">
        <el-input v-model="form.sex" placeholder="请输入性别" style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="年龄" style="width: 70%" >
        <el-input v-model="form.age" placeholder="请输入年龄" style="width: 70%"></el-input>
      </el-form-item>
      <el-form-item label="密码" style="width: 70%" >
        <el-input v-model="form.password" placeholder="请输入密码" style="width: 70%"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button type="primary" @click="update">修改</el-button>
      <!--      <el-button @click="">取消</el-button>-->
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "EditAdmin",
  data() {
    return {
      form: {

      }
    }
  },

  created(){
    const id = this.$route.query.id
    request.get("/admin/" + id).then(res => {
      this.form = res.data
    })
  },

  methods: {
    update() {
      request.put('/admin/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('修改成功');
          this.$router.push("/adminfind");
        } else{
          this.$notify.error(res.msg)
        }
      });
      //第二个变量为传入后台的数据
    }
  },
}
</script>
