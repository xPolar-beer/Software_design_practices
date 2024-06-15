<template>
  <div>
    <div>
      <el-date-picker v-model="params.createtime" align="right" type="date"
                      style = "width: 240px; margin-left: 5px;"
                      value-format="yyyy-MM-dd"
                      placeholder="选择预订日期" :picker-options="pickerOptions">
      </el-date-picker>
      <el-button style = "margin-left: 10px;" type = "primary" @click="start_load">
        <i class = "el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button style = "margin-left: 5px;" type = "success" @click="reset">
        <i class = "el-icon-refresh"></i>
        <span>重置</span>
      </el-button>
    </div>
    <el-table :data = "tableData" stripe border v-loading="loading" text :default-sort = "{prop: 'number', order: 'descending'}" style="margin-top: 20px">
      <el-table-column prop = "createtime" label = "评论时间" sortable></el-table-column>
      <el-table-column prop = "content" label = "评论内容" sortable></el-table-column>
      <el-table-column prop = "greatnum" label = "点赞数" sortable></el-table-column>
      <el-table-column label="操作">
        <template  v-slot="scope">
          <el-popconfirm
              title="您确定要删除该评论吗？"
              @confirm="del(scope.row.id)">
            <el-button
                type="danger"
                class="center"
                style="line-height: 9px; margin-left: 12px"
                slot="reference">删除评论</el-button>
          </el-popconfirm>
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
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "UserComment",
  data(){
    return {
      ID: Cookies.get('admin') ? (JSON.parse(Cookies.get('admin'))).id : '',
      tableData: [],
      loading: 0,
      timer: null,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        userid: Cookies.get('admin') ? (JSON.parse(Cookies.get('admin'))).id : '',
        createtime: '',
      },
      pickerOptions: {
        disabledDate(time) {
          // return time.getTime() < Date.now() - 3600 * 1000 * 24;
        },
      },
    }
  },
  methods: {
    filterTag(value, row) {
      return row.tag === value;
    },

    del(id){
      request.delete("/comment/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('删除成功');
          this.start_load();
        } else {
          this.$notify.error(res.msg);
        }
      })
    },

    handleCurrentChange(pageNum) {
      //点击分页按钮分页
      this.params.pageNum = pageNum;
      this.start_load();
    },

    start_load(){
      this.loading = 1;
      // this.params.reservetime = this.params.reservetime.toString();
      this.load();
      this.timer = setTimeout(() => { this.loading = 0; },500);
      // this.loading = false;
    },

    load() {

      request.get('/comment/userpage', {
        params: this.params
      }).then(res => {
        if(res.code === '200'){
          // this.tableData = res.data.list
          this.tableData = JSON.parse(JSON.stringify(res.data.list));
          this.total = res.data.total
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        userid: '',
        createtime: '',
      },
          this.params.userid = this.ID;
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