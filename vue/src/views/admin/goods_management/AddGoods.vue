  <template>
  <el-dialog
      title="添加物品"
      :visible.sync="detailVisible"
      width="500px"
      center>
    <el-form :model="form" status-icon ref="form" label-width="100px" style="margin-left: 0px" :rules="rules">
      <el-form-item prop="goodsname">
        <el-input clearable v-model="form.goodsname" placeholder="请输入物品名称" style="width: 250px;"></el-input>
      </el-form-item>
      <el-form-item  prop="number">
        <el-input clearable v-model="form.number" placeholder="请输入物品数量" style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item prop="style">
        <el-select v-model="form.style" placeholder="请选择物品类型" style="width: 250px;">
          <el-option label="一次性用品" value="一次性用品"></el-option>
          <el-option label="非一次性用品" value="非一次性用品"></el-option>
        </el-select>
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
  name: "AddGoods",
  data() {
    return {
      timer: null,
      form: {
        goodsname: '',
        number: '',
        style: '',
        username: '',
      },
      detailVisible: false,
      rules: {
        goodsname: [
          {required: true, message: '请输入物品名称', trigger: 'blur'},
        ],
        number: [
          {required: true, message: '请输入物品数量', trigger: 'blur'},
        ],
        style: [
          {required: true, message: '请选择物品类型', trigger: 'change'},
        ],
      }
    }
  },
  mounted() {
    window.Vue2 = this;
  },
  methods: {
    init(name) {
      this.detailVisible=true;
      this.form.username= name.name;
    },
    update() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          alert('submit!');
          request.post('/goods/save', this.form).then(res => {
            if (res.code === '200') {
              this.detailVisible=false;
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