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
    <el-table :data = "tableData" stripe border v-loading="loading" text :default-sort = "{prop: 'id', order: 'descending'}">
      <el-table-column prop = "id" label = "编号" sortable v-if = "disabled"></el-table-column>
      <el-table-column prop = "roomnumber" label = "房号" sortable></el-table-column>
      <el-table-column prop = "style" label = "房型" sortable></el-table-column>
      <el-table-column prop = "user" label = "住户" sortable></el-table-column>
      <el-table-column prop = "intime" label = "入住时间" sortable></el-table-column>
      <el-table-column prop = "userid" label = "住户编号" v-if = "false"></el-table-column>
      <el-table-column label="操作">
        <template  v-slot="scope">
          <el-button
              type="primary"
              style="line-height: 7px"
              class="center"
              @click="handleClick(scope.row.userid, scope.row.intime, scope.row.day)">详情</el-button>
          <dialog-component v-if="Visiable" ref="dialogs"></dialog-component>
          <el-popconfirm
              title="您确定要为该住户办理退房？"
              @confirm="del(scope.row.id, scope.row.userid, scope.row.user)">
            <el-button
                type="danger"
                class="center"
                style="line-height: 9px; margin-left: 20px"
                slot="reference">退房</el-button>
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
import dialogComponent from "./CheckUserInfo";

export default {
  name: 'OutRoom',
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
        pageSize: 10,
        roomnumber: '',
        style: '',
      },
    }
  },
  methods: {
    handleClick(id, intime, day){
      this.Visiable=true;
      this.$nextTick(()=>{
        //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
        //init调用的是dialog-component组件里面的init方法
        //data是传递给弹窗页面的值
        this.$refs.dialogs.initt(id, intime, day);
      })
    },
    del(id, userid, user) {
      request.put('/room/updateout/' + id + '/' + user).then(res => {
        if (res.code === '200') {
          request.put('/user/updatestate/' + userid + '/' + 0).then(res2 => {
            if (res2.code === '200') {
              this.$notify.success('退房成功');
              this.start_load();
            }
            else{
              this.$notify.error(res2.msg)
            }
          })
        } else{
          this.$notify.error(res.msg)
        }
      });
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
      request.get('/room/page2', {
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
        roomnumber: '',
        style: '',
      },
          this.start_load();
    },

    // del(id){
    //   request.delete("/admin/delete/" + id).then(res => {
    //     if (res.code === '200') {
    //       this.$notify.success('删除成功');
    //       this.start_load();
    //     } else {
    //       this.$notify.error(res.msg);
    //     }
    //   })
    // }
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