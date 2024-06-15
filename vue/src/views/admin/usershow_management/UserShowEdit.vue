<template>
  <div>
    <div style="padding-top: 30px" >
      <el-table row-key="id" default-expand-all border :data = "tableData" stripe v-loading="loading" text :default-sort = "{prop: 'id', order: ''}">
        <el-table-column prop = "id" label = "编号" width="100px"></el-table-column>
        <el-table-column prop = "image" label = "图片描述" style="width: 100px">
          <template v-slot="scope">
            <el-image :src="scope.row.image" :preview-src-list="[scope.row.image]" style="width: 300px"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template  v-slot="scope">
            <el-button
                style = "margin-left: 5px;" type = "primary"
                @click="editroom(scope.row.id)">编辑
            </el-button>
              <dialog-component v-if="Visiable" ref="dialog"></dialog-component>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";
import dialogComponent from "@/views/admin/usershow_management/Edit";

export default {
  name: "UserShowEdit",
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
    start_load(){
      this.loading = 1;
      this.load();
      this.timer = setTimeout(() => { this.loading = 0; },500);
      // this.loading = false;
    },

    load() {
      request.get('/homeview/list').then(res => {
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