<template>
  <div class="home">
    <!-- 搜索表单 -->
    <div>
      <el-input style = "width: 240px; margin-left: 20px; margin-bottom: 10px;" placeholder = "请输入房号" v-model="params.roomnumber"></el-input>
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
    <el-table :data = "tableData" stripe border v-loading="loading" text :default-sort = "{prop: 'id', order: ''}">
      <el-table-column prop = "id" label = "编号" sortable></el-table-column>
      <el-table-column prop = "roomnumber" label = "房号" sortable></el-table-column>
      <el-table-column prop = "style" label = "房型" sortable></el-table-column>
      <el-table-column prop = "userid" v-if = "false"></el-table-column>
      <el-table-column prop = "ordernumber" label = "房型" sortable v-if = "false"></el-table-column>
      <el-table-column prop="up" label="状态" width="100px"
          :filters="[{ text: '未预定', value: 0 }, { text: '已预定', value: 1 }]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.up === 0 ? 'primary' : 'warning'"
              disable-transitions>{{scope.row.tag}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template  v-slot="scope">
          <el-button
              type="primary"
              style="line-height: 7px"
              class="center"
              @click="handleClick(scope.row.id, scope.row.style, scope.row.userid)">添加住户</el-button>
          <dialog-component v-if="Visiable" ref="dialog"></dialog-component>
          <el-button
              type="success"
              style="line-height: 7px;margin-left:20px"
              class="center"
              @click="CheckOrder(scope.row.up, scope.row.id, scope.row.ordernumber, scope.row.userid)">核验订单</el-button>
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
import dialogComponent from "./AddRoom";

export default {
  name: 'CleanRoom',
  components:{
    dialogComponent
  },
  data(){
    return {
      form: {
        id: '',
        intime: '',
        userid: '',
        day: '',
      },
      ordernumber: '',
      Visiable:false,
      tableData: [],
      loading: 0,
      timer: null,
      state: 1,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        roomnumber: '',
        style: '',
      },
    }
  },
  mounted() {
    window.Vue = this;
  },
  methods: {
    filterTag(value, row) {
      return row.up === value;
    },
    //添加住户
    handleClick(id, style, userid){
      this.Visiable=true;
      this.$nextTick(()=>{
        //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
        //init调用的是dialog-component组件里面的init方法
        //data是传递给弹窗页面的值
        this.$refs.dialog.init(id, style, userid);
      })
    },
    // 核验订单
    CheckOrder(up, id, number, userid){
      if (up === 0) {
        this.$notify.error("此房间未被预订")
      } else {
        this.ordernumber = number;
        request.put('/order/updatestate/' + number).then(res => {
          if (res.code === '200') {
            // request.
            this.form.id = id;
            this.form.userid = userid
            this.form.day= parseInt(res.data);
            request.put('/room/updatenotup', this.form).then(res2 =>{
              if (res2.code === '200') {
                request.put('/user/updatestate/' + userid + '/' + this.state).then(res3 => {
                  if (res3.code === '200') {
                    this.$notify.success('订单已核验');
                    this.start_load();
                  }
                  else {
                    this.$notify.error(res.msg)
                  }
                })
              } else {
                this.$notify.error(res.msg)
              }
            })
          } else{
            this.$notify.error(res.msg)
          }
        });
      }
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
      request.get('/room/page1', {
        params: this.params
      }).then(res => {
        if(res.code === '200'){
          // this.tableData = res.data.list
          this.tableData = JSON.parse(JSON.stringify(res.data.list));
          this.total = res.data.total
          for( var i = 0; i < this.tableData.length; i ++){
            if(this.tableData[i].up === 0) {this.tableData[i].tag = '未预定';}
            else {this.tableData[i].tag = '已预定';}
          }
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        roomnumber: '',
        style: '',
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