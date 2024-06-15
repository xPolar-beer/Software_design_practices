<template>
  <div class="home">
    <!-- 搜索表单 -->
    <div>
      <el-input style = "width: 240px; margin-left: 20px; margin-bottom: 10px;" placeholder = "请输入姓名" v-model="params.name"></el-input>
      <el-input style = "width: 240px; margin-left: 5px;" placeholder = "请输入联系电话" v-model="params.phone"></el-input>
      <el-button style = "margin-left: 10px;" type = "primary" @click="start_load">
        <i class = "el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button style = "margin-left: 5px;" type = "success" @click="reset">
        <i class = "el-icon-refresh"></i>
        <span>重置</span>
      </el-button>
    </div>
    <el-table :data = "tableData" stripe border v-loading="loading" text style="width: 100%">
      <el-table-column prop = "id" label = "编号" width="100"></el-table-column>
      <el-table-column prop = "name" label = "姓名" width="150"></el-table-column>
      <el-table-column prop = "username" label = "用户昵称" width="150"></el-table-column>
      <el-table-column prop = "sex" label = "性别" width="100"></el-table-column>
      <el-table-column prop = "birthday" label = "年龄" width="100">
        <template slot-scope="scope">
          {{scope.row.birthday | showAge}}
        </template>
      </el-table-column>
      <el-table-column prop = "phone" label = "电话"></el-table-column>
      <el-table-column prop = "createtime" label = "注册日期"></el-table-column>
      <el-table-column label="操作">
        <template  v-slot="scope">
          <el-button
              type="primary"
              class="center"
              style="line-height: 9px"
              @click="$router.push('/edituser?id=' + scope.row.id)">查看</el-button>
            <el-popconfirm
                title="您确定要删除此用户吗？"
                @confirm="del(scope.row.id)">
              <el-button
                  type="danger"
                  class="center"
                  style="line-height: 9px; margin-left: 12px"
                  slot="reference">删除</el-button>
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

export default {
  name: 'UserFind',
  data(){
    return {
      tableData: [],
      loading: 0,
      timer: null,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: '',
      },
    }
  },
  methods: {
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
      request.get('/user/page', {
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

    del(id){
      request.delete("/user/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('删除成功');
          this.start_load();
        } else {
          this.$notify.error(res.msg);
        }
      })
    }
  },

  filters: {
    showAge(value){
      //指定日期
      let birthdays = new Date(value.replace(/-/g, "/"))
      //当前系统日期
      let d = new Date();
      let age = d.getFullYear() - birthdays.getFullYear() +   (d.getMonth() < birthdays.getMonth() || (d.getMonth() == birthdays.getMonth() && d.getDate() < birthdays.getDate()) ? 0: 1);
      return age;
    }
  },

  beforeDestroy() {
    clearTimeout(this.timer);
  },

  created() {
    this.load()
  },
}
</script>

