<template>
  <div>
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
    <div style="padding-top: 30px" >
      <el-table row-key="id" default-expand-all border :data = "tableData" stripe v-loading="loading" text :default-sort = "{prop: 'id', order: ''}">
        <el-table-column prop = "id" label = "编号" width="100px"></el-table-column>
        <el-table-column prop = "avatar" label = "图片描述" width="250px">
          <template v-slot="scope">
            <el-image :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop = "style" label = "房型" width="100px"></el-table-column>
        <el-table-column prop = "price" label = "价格" width="100px"></el-table-column>
        <el-table-column prop = "describes" label = "文字描述" width="300px"></el-table-column>
        <el-table-column prop = "number" label = "数量" width="100px"></el-table-column>
        <el-table-column prop = "greatnum" label = "点赞数" width="100px"></el-table-column>
        <el-table-column label="操作" width="170px">
          <template  v-slot="scope">
            <el-button
                style = "margin-left: 5px;" type = "primary"
                @click="editroom(scope.row.id)">编辑
            </el-button>
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
  </div>
</template>

<script>
import request from "@/utils/request";
import dialogComponent from "./EditRoomInfo"

export default {
  name: "EditRoom",
  components: {
    dialogComponent
  },
  data(){
    return {
      Visiable: false,
      tableData: [],
      loading: 0,
      timer: null,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 3,
        style: '',
      },
    }
  },
  methods: {
    editroom(id){
      this.Visiable=true;
      this.$nextTick(()=>{
        //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
        //init调用的是dialog-component组件里面的init方法
        //data是传递给弹窗页面的值
        this.$refs.dialog.init(id);
      })
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
        pageSize: 3,
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