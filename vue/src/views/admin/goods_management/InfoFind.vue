<template>
  <div class="home">
    <!-- 搜索表单 -->
    <div>
      <el-input style = "width: 240px; margin-left: 20px; margin-bottom: 10px;" placeholder = "请输入姓名" v-model="params.user"></el-input>
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
              title="您确定要移删除吗？"
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
import dialogComponent from "./AddGoods";
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
        user: '',
        phone: '',
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

    add(){
      this.Visiable=true;
      this.$nextTick(()=>{
        //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
        //init调用的是dialog-component组件里面的init方法
        //data是传递给弹窗页面的值
        this.$refs.dialog.init(this.admin);
      })
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
      request.get('/info/page', {
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
        user: '',
        phone: '',
      },
          this.start_load();
    },

    del(id){
      request.delete("/info/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('删除成功');
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