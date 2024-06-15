<template>
  <div class="home">
    <!-- 搜索表单 -->
    <div>
      <el-input style = "width: 240px; margin-left: 20px; margin-bottom: 10px;" placeholder = "请输入订单号" v-model="params.number"></el-input>
      <el-date-picker v-model="params.reservetime" align="right" type="date"
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
    <el-table :data = "tableData" stripe border v-loading="loading" text :default-sort = "{prop: 'number', order: 'descending'}">
      <el-table-column prop = "id" label = "订单编号" sortable></el-table-column>
      <el-table-column prop = "number" label = "订单号" sortable></el-table-column>
      <el-table-column prop = "roomnumber" label = "房号" sortable></el-table-column>
      <el-table-column prop = "establishtime" label = "订单创建日期" sortable></el-table-column>
      <el-table-column prop = "reservetime" label = "预订日期" sortable></el-table-column>
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
      <el-table-column label="操作">
        <template  v-slot="scope">
          <el-button
              type="primary"
              style="line-height: 7px"
              class="center"
              @click="handleClick(scope.row.id, scope.row.userid)">查看详情</el-button>
          <dialog-component v-if="Visiable" ref="dialog"></dialog-component>
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
import dialogComponent from "./CheckOrder";

export default {
  name: 'OrderFind',
  components:{
    dialogComponent
  },
  data(){
    return {
      Visiable:false,
      tableData: [],
      loading: 0,
      timer: null,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 9,
        number: '',
        reservetime: '',
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 3600 * 1000 * 24;
        },
      },
    }
  },
  methods: {
    filterTag(value, row) {
      return row.tag === value;
    },

    handleClick(id, userid){
      this.Visiable=true;
      this.$nextTick(()=>{
        //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
        //init调用的是dialog-component组件里面的init方法
        //data是传递给弹窗页面的值
        this.$refs.dialog.init(id, userid);
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

      request.get('/order/page', {
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
        number: '',
        reservetime: '',
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