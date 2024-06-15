<template>
  <div class="home">
    <!-- 搜索表单 -->
    <el-table :data = "tableData" stripe border v-loading="loading" text :default-sort = "{prop: 'id'}">
      <el-table-column prop = "id" label = "编号" sortable v-if = "disabled"></el-table-column>
      <el-table-column prop = "roomnumber" label = "房号" sortable></el-table-column>
      <el-table-column prop = "user" label = "住户" sortable></el-table-column>
      <el-table-column prop = "phone" label = "电话" sortable></el-table-column>
      <el-table-column prop="tag" label="状态"
                       :filters="[{ text: '未核验', value: '未核验' }, { text: '已核验', value: '已核验' }]"
                       :filter-method="filterTag"
                       filter-placement="bottom-end">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.tag === '未核验' ? 'primary' : 'warning'"
              disable-transitions>{{scope.row.tag}}</el-tag>
        </template>
      </el-table-column>
      <!--      <el-table-column prop = "state" label = "状态" sortable></el-table-column>-->
      <el-table-column prop = "time" label = "时间" sortable></el-table-column>
      <el-table-column prop = "str" label = "描述" sortable></el-table-column>
      <el-table-column label="操作">
        <template  v-slot="scope">
          <el-popconfirm
              title="您确定要核验吗？"
              @confirm="update(scope.row.id)">
            <el-button
                type="primary"
                class="center"
                style="line-height: 9px; margin-left: 12px"
                slot="reference">核验</el-button>
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
  name: 'GoodsFind',

  data(){
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableData: [],

      loading: 0,
      Visiable:false,
      timer: null,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
      },
    }
  },
  mounted() {
    window.Vue = this;
  },
  methods: {
    filterTag(value, row) {
      return row.tag === value;
    },

    clear() {
      this.dialogFormVisible = false;
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
      request.get('/info/list/' + this.admin.id, {
        params: this.params
      }).then(res => {
        if(res.code === '200'){
          // this.tableData = res.data.list
          this.tableData = JSON.parse(JSON.stringify(res.data.list));
          this.total = res.data.total
          for( var i = 0; i < this.tableData.length; i ++){
            if(this.tableData[i].state === 0) {this.tableData[i].tag = '未核验';}
            else {this.tableData[i].tag = '已核验';}
          }
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
      },
          this.start_load();
    },

    update(id){
      request.put("/info/update/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('核验成功');
          this.start_load();
        } else {
          this.$notify.error(res.msg);
        }
      })
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

<style scoped>

</style>