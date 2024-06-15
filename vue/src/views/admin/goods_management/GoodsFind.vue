<template>
  <div class="home">
    <!-- 搜索表单 -->
    <div>
      <el-input style = "width: 240px; margin-left: 20px; margin-bottom: 10px;" placeholder = "请输入物品名称" v-model="params.goodsname"></el-input>
      <el-button style = "margin-left: 10px;" type = "primary" @click="start_load">
        <i class = "el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button style = "margin-left: 5px;" type = "success" @click="reset">
        <i class = "el-icon-refresh"></i>
        <span>重置</span>
      </el-button>
      <el-button
          style = "margin-left: 400px;"
          type = "primary"
          class = "el-icon-plus"
          @click="add()">新添物品</el-button>
      <dialog-component v-if="Visiable" ref="dialog"></dialog-component>
    </div>
    <el-table :data = "tableData" stripe border v-loading="loading" text :default-sort = "{prop: 'id'}">
      <el-table-column prop = "id" label = "编号" sortable v-if = "disabled"></el-table-column>
      <el-table-column prop = "goodsname" label = "名称" sortable></el-table-column>
      <el-table-column prop = "number" label = "数量" sortable></el-table-column>
      <el-table-column prop = "style" label = "类型" sortable></el-table-column>
      <el-table-column prop = "lasttime" label = "上一次修改时间" sortable></el-table-column>
      <el-table-column prop = "username" label = "修改用户" sortable></el-table-column>
      <el-table-column label="操作">
        <template  v-slot="scope">
          <el-button
              type="primary"
              class="center"
              style="line-height: 9px"
              @click="dialogFormVisible = true">修改</el-button>
          <el-dialog title="修改数量" :visible.sync="dialogFormVisible" width="500px"
                     center>
            <el-form :model="form" status-icon ref="form" label-width="100px" style="margin-left: 0px" :rules="rules">
              <el-form-item prop="goodsname">
                <el-input clearable v-model="form.goodsname" placeholder="修改名称" style="width: 250px;"></el-input>
              </el-form-item>
              <el-form-item  prop="number">
                <el-input clearable v-model="form.number" placeholder="修改数量" style="width: 250px"></el-input>
              </el-form-item>
              <el-form-item prop="style">
                <el-select v-model="form.style" placeholder="修改类型类型" style="width: 250px;">
                  <el-option label="一次性用品" value="一次性用品"></el-option>
                  <el-option label="非一次性用品" value="非一次性用品"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <div style="text-align: center">
              <el-button type="primary" @click="update(scope.row.id)">提交</el-button>
              <el-button @click="clear()">取消</el-button>
            </div>
        </el-dialog>
          <el-popconfirm
              title="您确定要移除此物品吗？"
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
  components:{
    dialogComponent,
  },

  data(){
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      form: {
        id: '',
        goodsname: '',
        number: '',
        style: '',
        username: '',
      },
      dialogFormVisible: false,
      rules: {
        goodsname: [
          {required: true, message: '请输入物品名称', trigger: 'blur'},
        ],
        number: [
          {required: true, message: '请输入物品数量', trigger: 'blur'},
        ],
        style: [
          {required: true, message: '请选择物品类型', trigger: 'change'},
        ],
      },
      tableData: [],

      loading: 0,
      Visiable:false,
      timer: null,
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        goodsname: '',
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

    add(){
      this.Visiable=true;
      this.$nextTick(()=>{
        //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
        //init调用的是dialog-component组件里面的init方法
        //data是传递给弹窗页面的值
        this.$refs.dialog.init(this.admin);
      })
    },

    update(id){
      this.form.id = id
      this.form.username = this.admin.name
      this.$refs['form'].validate((valid) => {
        if (valid) {
          alert('submit!');
          request.put('/goods/update', this.form).then(res => {
            if (res.code === '200') {
              this.dialogFormVisible=false;
              this.$notify.success('修改成功');
              this.timer = setTimeout(() => { this.$router.go(0); },200);
            } else{
              this.$notify.error(res.msg)
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
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
      request.get('/goods/page', {
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
        goodsname: '',
      },
          this.start_load();
    },

    del(id){
      request.delete("/goods/delete/" + id).then(res => {
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