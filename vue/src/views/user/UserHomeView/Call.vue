<template>
  <el-dialog
      title="需求"
      :visible.sync="detailVisible"
      width="700px"
      center>
    <div style = "display: flex; flex-direction: row;">
      <div style="margin-left: 28px">
        <el-form :model="form" label-width="70px">
          <el-form-item label="房间号" style="width: 70%">
            <el-input v-model="form.roomnumber" disabled style="width: 80px; margin-left: 10px"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div style="margin-left: 10px">
        <el-form :model="form" label-width="70px">
          <el-form-item label="住户" style="width: 70%">
            <el-input v-model="form.user" disabled style="width: 80px; margin-left: 10px"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div style="margin-left: 10px">
        <el-form :model="form" label-width="70px">
          <el-form-item label="电话" style="width: 70%">
            <el-input v-model="form.phone" disabled style="width: 160px; margin-left: 10px"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div style="margin-left: 35px">
      <el-transfer
          style="text-align: left; display: inline-block"
          :titles="['物品列表', '所需物品']"
          v-model="value"
          @change="handleChange"
          :data="names">
      </el-transfer>
    </div>
    <div style="text-align: center; margin-top: 20px">
        <el-button type="primary" @click="update()">呼叫</el-button>
      <el-button @click="clear">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Call",
  data() {
    return {
      name:[],
      names: [],
      tp: '',
      value: [],
      leftvalue: [],
      rightvalue: [],
      timer: null,
      form: {
        userid: '',
        roomnumber: '',
        user: '',
        phone: '',
        str: '',
      },
      detailVisible: false,
    }
  },
  mounted() {
    window.Vue10 = this;
  },
  methods: {
    init(user, id) {
      request.get('/goods/findall').then(res => {
        if(res.code === '200') {
          this.names = [];
          this.name = res.data;
          res.data.forEach((name, index) => {
            this.names.push({
              label: name,
              key: index,
            })
          });
        }
        else{
          this.$notify.error(res.msg);
        }
      });
      request.get('/room/findroomnumber/' + id).then(res => {
        if(res.code === '200'){
          this.form.roomnumber = res.data;
          this.form.userid = id;
        }
        else{
          this.$notify.error(res.msg);
        }
      });
      this.detailVisible = true;
      this.form.user = user.name;
      this.form.phone = user.phone

    },

    handleChange(value, direction, movedKeys) {
      if (direction === "right") {
        movedKeys.forEach((key) => {
          let index = this.leftvalue.findIndex((item) => item === key);
          this.leftvalue.splice(index, 1);
        });
        movedKeys.forEach((key) => {
          this.rightvalue.push(key);
        });
      } else {
        movedKeys.forEach((key) => {
          let index = this.rightvalue.findIndex((item) => item === key);
          this.rightvalue.splice(index, 1);
        });
        movedKeys.forEach((key) => {
          this.leftvalue.push(key);
        });
      }
      console.log(this.leftvalue);
      console.log(this.rightvalue);
    },

    update() {
      if (this.rightvalue == null || this.rightvalue.length == 0) {
        this.$alert("请先选择所需物品", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.value.forEach((key) => {
          this.form.str += this.name[key];
          this.form.str += "  "
        })
        request.post('/info/save', this.form).then(res => {
          if (res.code === '200') {
            this.detailVisible=false;
            this.$notify.success('添加成功');
            // this.timer = setTimeout(() => { this.$router.go(0); },200);
          } else{
            this.$notify.error(res.msg)
          }
        });
      }
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