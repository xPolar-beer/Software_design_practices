<template>
  <div>
    <el-dialog
        title="编辑图片"
        :visible.sync="detailVisible"
        width="400px"
        center>
      <div style = "display: flex; flex-direction: row; margin-bottom: 20px; margin-left: 25px">
        <div style="background-color: white; ">
          <el-upload
              class="avatar-uploader"
              :action="'http://localhost:9090/api/roomstyle/the/file/upload?token=' + this.admin.token"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="form.image" :src="form.image" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
      </div>
      <div style="text-align: center">
        <el-button type="primary" @click="update()">修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "Edit",
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      timer: null,
      form: {},
      detailVisible: false,
      rules: {
      }
    }
  },
mounted() {
  window.V = this;
},
  methods: {
    refresh() {
      this.$router.replace(`/blank?redirect=${this.$route.fullPath}`);
    },
    init(id) {
      this.detailVisible = true;
      request.get("/homeview/" + id).then(res => {
        this.form = res.data
      })
    },
    update() {
      request.put('/homeview/update', this.form).then(res => {
        if (res.code === '200') {
          this.detailVisible=false;
          this.$notify.success('修改成功');
          this.timer = setTimeout(() => { this.refresh(); },200);
        } else{
          this.$notify.error(res.msg)
        }
      });
    },

    beforeDestroy() {
      clearTimeout(this.timer);
    },
    handleAvatarSuccess(res) {
      if (res.code === '200') {
        this.$set(this.form, 'image', res.data);
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

<style scoped>
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
  width: 300px;
  height: 300px;
  display: block;
}
</style>