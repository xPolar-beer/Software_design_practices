<template>
  <div>
    <el-dialog
        title="编辑客房信息"
        :visible.sync="detailVisible"
        width="600px"
        center>
      <div style = "display: flex; flex-direction: row; margin-bottom: 20px; margin-left: 30px">
        <div style="background-color: white; ">
          <el-upload
              class="avatar-uploader"
              :action="'http://localhost:9090/api/roomstyle/the/file/upload?token=' + this.admin.token"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="form.avatar" :src="form.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <div style="margin-left: 10px; background-color: white; padding-top: 10px">
          <el-form :model="form" label-width="80px" ref="form" :rules="rules">
            <el-form-item label="房型" style="width: 200px">
              <el-input v-model="form.style" disabled></el-input>
            </el-form-item>
            <el-form-item label="价格" style="width: 200px" prop="price">
              <el-input v-model="form.price" placeholder="请输入价格 "></el-input>
            </el-form-item>
            <el-form-item label="文字描述" style="width: 300px">
              <el-input type="textarea" v-model="form.describes"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div style="text-align: center">
        <el-button type="primary" @click="update()">修改</el-button>
        <el-button @click="clear">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "EditRoomInfo",
  data() {
    var checkPrice = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('价格不能为空'));
      }
      setTimeout(() => {
          callback();
      }, 100);
    };
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      roomstyle: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      roomstyleId: '',
      timer: null,
      form: {},
      detailVisible: false,
      rules: {
        price: [
          {validator: checkPrice, trigger: 'blur'},
        ],
      }
    }
  },

  methods: {
    init(id) {
      this.detailVisible = true;
      request.get("/roomstyle/" + id).then(res => {
        this.form = res.data
      })
    },
    update() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.put('/roomstyle/update', this.form).then(res => {
            if (res.code === '200') {
              this.detailVisible=false;
              this.$notify.success('修改成功');
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
    handleAvatarSuccess(res) {
      if (res.code === '200') {
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

    clear() {
      this.detailVisible= false;
    },
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
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>