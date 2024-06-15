<template>
  <div>
    <div style="padding-top: 30px" >
      <el-table row-key="id" default-expand-all border :data = "tableData" stripe v-loading="loading" text :default-sort = "{prop: 'id', order: ''}">
        <el-table-column prop = "style" label = "房型" sortable></el-table-column>
        <el-table-column prop = "describes" label = "文字描述" style="width: 100px"></el-table-column>
        <el-table-column prop = "greatnum" label = "点赞数" sortable></el-table-column>
        <el-table-column label="操作">
          <template  v-slot="scope">
            <el-button
                style = "margin-left: 20px;" type = "primary"
                @click="go(scope.row.roomstyleid)">跳转
            </el-button>
            <el-button
              style = "margin-left: 10px;" type = "danger"
              @click="deletes(scope.row.id)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "UserCollet",
  data(){
    return {
      userid: Cookies.get('admin') ? (JSON.parse(Cookies.get('admin'))).id : '',
      Visiable: false,
      tableData: [],
      loading: 0,
      timer: null,
      total: 0,

    }
  },
  methods: {
    go(id){
      this.$router.push('/user/reserve?id=' + id);
    },
    deletes(id){
      request.delete('/collect/delete/' + id).then( res => {
        if(res.code === '200'){
          this.$notify.success("删除成功");
          this.start_load();
        } else {
          this.$notify.error(res.msg);
        }
      })
    },
    start_load(){
      this.loading = 1;
      this.load();
      this.timer = setTimeout(() => { this.loading = 0; },500);
      // this.loading = false;
    },

    load() {
      request.get('/collect/list/' + this.userid).then(res => {
        this.tableData = res.data
        if(res.code === '200'){

        }
      })
    },
    reset() {
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