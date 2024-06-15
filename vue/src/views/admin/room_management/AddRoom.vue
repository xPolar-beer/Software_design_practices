  <template>
  <el-dialog
      title="添加住户"
      :visible.sync="detailVisible"
      width="500px"
      center>
    <el-form :model="form" status-icon ref="form" label-width="100px" style="margin-left: 0px" :rules="rules">
      <el-form-item prop="name">
        <el-input clearable v-model="form.name" placeholder="请输入住户姓名" style="width: 250px;"></el-input>
      </el-form-item>
      <el-form-item  prop="phone">
        <el-input clearable v-model="form.phone" placeholder="请输入住户联系电话" style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item prop="sex">
        <el-select v-model="form.sex" placeholder="请选择性别" style="width: 250px;">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item  prop="day">
        <el-input clearable v-model="form.day" placeholder="请输入入住天数" style="width: 250px"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
        <el-button type="primary" @click="update()">提交</el-button>
      <el-button @click="clear">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AddRoom",
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error('请输入合法的手机号'));
      }
      callback()
    };
    return {
      timer: null,
      style: '',
      sexx: '',
      userid: '',
      form: {
        id:'',
        name: '',
        phone: '',
        intime: '',
        day: '',
        sex: '',
      },
      detailVisible: false,
      rules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
        ],
        phone: [
          {validator: checkPhone, trigger: 'blur'},
        ],
        day: [
          {required: true, message: '请输入天数', trigger: 'blur'},
        ],
        sex: [
          {required: true, message: '请选择性别', trigger: 'change'},
        ],
      }
    }
  },
  mounted() {
    window.Vue2 = this;
  },
  methods: {
    init(id, style, userid) {
      this.detailVisible=true;
      this.form.id = id;
      this.style = style;
      this.userid = userid;
    },
    update() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          alert('submit!');
          request.put('/room/update', this.form).then(res => {
            if (res.code === '200') {
              this.detailVisible=false;
              request.put('/roomstyle/updatenumber/' + this.style);
              request.put('/user/updatestate/' + this.userid + '/' + 1)
              this.$notify.success('添加成功');
              this.timer = setTimeout(() => { this.$router.go(0); },200);
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
    beforeDestroy() {
      clearTimeout(this.timer);
    },

    clear() {
      this.detailVisible= false;
    },
  }
}
</script>

<style scoped>

</style>