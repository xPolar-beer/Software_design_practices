<template>
  <div style="min-height: calc(100vh - 161px); overflow: hidden; position: relative">
    <div style="display: flex; justify-content: center;">
      <div style="display: flex; width:1000px; align-items: center;height: 40px; background-color: #7DA6CE;">
        <span style="font-size: 20px; margin-left: 20px; color: white">客房列表</span></div>
    </div>
    <div style="display: flex;  justify-content: center;">
      <div style="margin-top: 30px">
        <el-input style = "width: 240px; margin-left: 5px;" placeholder = "请输入房型" v-model="params.style"></el-input>
        <el-button style = "margin-left: 10px;" type = "primary" @click="start_load">
          <i class = "el-icon-search"></i>
          <span>搜索</span>
        </el-button>
        <el-button style = "margin-left: 5px;" type = "success" @click="reset">
          <i class = "el-icon-refresh"></i>
          <span>重置</span>
        </el-button>
      </div>
    </div>
    <div style="padding-top: 20px">
      <el-table style="margin-left: 210px; width: 1000px;" row-key="id" default-expand-all :data = "tableData" stripe>
        <el-table-column prop = "id" label = "编号" width="150px"></el-table-column>
        <el-table-column prop = "avatar" label = "图片描述" width="250px">
          <template v-slot="scope">
            <el-image :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop = "style" label = "房型" width="150px"></el-table-column>
        <el-table-column prop = "describes" label = "文字描述" width="200px"></el-table-column>
        <el-table-column prop = "number" label = "数量" width="50px"></el-table-column>
        <el-table-column label="操作" width="200px">
          <template  v-slot="scope">
            <el-button style = "margin-left: 5px;" type = "primary" @click="reserve(scope.row.number, scope.row.id)">
              <span>查看详情</span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style = "position: relative; left: 50%; float: left;">
        <div style = "margin-top:10px; position: relative; left: -50%;">
          <el-pagination
              background
              @current-change = "handleCurrentChange"
              :current-page = "params.pageNum"
              :page-size = "params.pageSize"
              layout = "total, prev, pager, next, jumper"
              :total = "total">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UserGetRoom",
  data () {
    return {
      tableData: [],
      loading: 0,
      timer: null,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 2,
        style: '',
      },
    }
  },
  methods: {
    reserve(num, id) {
      // if (num !== 0) {
        this.$router.push('/user/reserve?id=' + id);
      // } else {
      //   this.$notify.error("没有房间可以预定");
      // }
    },
    handleCurrentChange(pageNum) {
      //点击分页按钮分页
      this.params.pageNum = pageNum;
      this.start_load();
    },

    start_load(){
      this.loading = 1;
      this.load();
      this.timer = setTimeout(() => { this.loading = 0; },500);
      // this.loading = false;
    },

    load() {
      request.get('/roomstyle/page', {
        params: this.params
      }).then(res => {
        if(res.code === '200'){
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: '',
      },
          this.start_load();
    },
  },

  beforeDestroy() {
    clearTimeout(this.timer);
  },

  created() {
    this.load()
  },
}
</script>

<style scoped>

</style>