<template>
  <div style="min-height: calc(100vh - 160px); overflow: hidden; position: relative">
    <div style="margin-left: 200px">
      <el-page-header @back="goBack" content="详情页面">
      </el-page-header>
    </div>
    <div style="display: flex; justify-content: center; margin-top: 10px">
      <hr style="border: 0.1mm solid;width:1000px; color: #909399"/>
    </div>
    <div style="display: flex; justify-content: center; margin-top: 10px">
      <div style="display: flex; flex-direction: row; background-color: white; height: 450px; width: 900px">
        <div style="padding-top: 25px; margin-left: 50px">
          <el-image
              style="width: 400px; height: 400px;border-radius: 10px"
              :src="form.avatar"
              :preview-src-list="form.avatar">
          </el-image>
        </div>
        <div style="display: flex; flex-direction: column; padding-top: 50px; margin-left: 20px">
          <div class="room">
            <span>客房编号: {{form.id}}</span>
          </div>
          <div class="room">
            <span>客房类型: {{form.style}}</span>
          </div>
          <div class="room">
            <span>客房数量: {{form.number}}</span>
          </div>
          <div class="room">
            <span>客房价格: {{form.price}}</span>
          </div>
          <div class="room">
            <span>客房描述: {{form.describes}}</span>
          </div>
        </div>
      </div>
    </div>
    <div style="display: flex; margin-left: 1000px;">
      <span style="font-size: 20px">点赞数:
        <span style="color: #0dcaf0; font-size: 30px">{{form.greatnum}}</span>
      </span>
    </div>
    <div style="display: flex; justify-content: center; ">
      <hr style="border: 0.1mm solid;width:1000px; color: #909399"/>
    </div>
    <div style="padding-top: 20px">
      <el-button type="primary"style="font-size:15px;width: 100px; margin-left: 800px" @click="goreserve">预订</el-button>
      <el-button type="primary"style="font-size:15px;width: 100px; margin-left: 15px" @click="great(form.id)" :disabled="Visiable">点赞</el-button>
      <el-button type="primary"style="font-size:15px;width: 100px; margin-left: 15px" @click="collect(userID ,form.id)">收藏</el-button>
    </div>
    <div style="display: flex; justify-content: center; padding-top: 20px; ">
      <hr style="border: 0.1mm dashed; width:1000px; color: #909399;"/>
    </div>
<!--    评论列表-->
    <div style="display: flex; justify-content: center; margin-top: 10px">
      <template>
        <div style="width: 1000px">
          <h2>评论</h2>
          <el-form ref="commentForm" :model="comment" label-width="80px">
            <el-form-item label="">
              <el-input type="textarea" v-model="comment.content" :autosize="{ minRows: 4, maxRows: 4 }"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="upcomment()">发表</el-button>
            </el-form-item>
          </el-form>
          <div class="comment-list">
            <div class="content">
              <div class="first" v-for="(item, index) in commentList" :key="index" style="margin-left: 50px">
                  <el-avatar v-if="item.avatar" :size="80" :src="item.avatar"></el-avatar>
                  <el-avatar v-else :size="80" icon="el-icon-user-solid"></el-avatar>
                  <div class="first-content" style="margin-left: 15px">
                    <h3 class="first-username" style="margin-top: 10px">{{ item.username }}</h3>
                    <span class="first-time">{{ item.createtime }}</span>
                    <p class="first-comment">{{ item.content }}</p>
                    <div class="first-right">
                      <el-button  type="text" icon="el-icon-thumb" @click="upgreatnum(item.id, index)">
                        <span v-if="item.greatnum">点赞数：{{ item.greatnum }}</span>
                        <span v-else>点赞数：0</span>
                      </el-button>
                    </div>
                  </div>
              </div>
            </div>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "UserReserveRoom",
  data () {
    return {

      ID: '',
      userID: Cookies.get('admin') ? (JSON.parse(Cookies.get('admin'))).id : '',
      Visiable: false,
      form: {},
      user: {},
      param: {
        userid: Cookies.get('admin') ? (JSON.parse(Cookies.get('admin'))).id : '',
        roomstyleid: '',
        style: '',
        describes: '',
        greatnum: '',
      },
      comment: {
        roomid: '',
        content: '',
        userid: '',
        avatar: '',
        username: '',
      },
      reply: [],
      commentList: []
    }
  },
  mounted() {
    window.Comment = this;
  },
  methods: {
    refresh() {
      this.$router.replace(`/blank?redirect=${this.$route.fullPath}`);
    },
    upgreatnum(id, index){
      request.put("/comment/updategreatnum/" + id);
      this.commentList[index].greatnum += 1;
    },
    upcomment(){
      this.comment.userid = this.user.id;
      this.comment.avatar = this.user.avatar;
      this.comment.username = this.user.username;
      this.comment.roomid = this.ID;
      request.post('/comment/savecomment', this.comment).then(res => {
        if (res.code === '200') {
          this.$notify.success('发表成功');
          this.comment.content = '';
          this.refresh()
        } else{
          this.$notify.error(res.msg)
        }
      });
      request.post
    },
    load(){
      request.get("/roomstyle/" + this.ID).then(res => {
        this.form = res.data
      })
      request.get("/comment/listbyroomid/" + this.ID).then(res2 => {
        this.commentList = res2.data;
      })

    },
    goreserve(){
      if (this.form.number !== 0) {
        this.$router.push('/user/reserveroom?id=' + this.form.id);
      } else {
        this.$notify.error("没有房间可以预定");
      }
    },
    collect(userid, roomstyleid){
      request.get('/collect/' + userid + '/' + roomstyleid).then( res => {
        if(res.data !== null) {
          this.$notify.error("您已收藏");
        } else {
          this.param.roomstyleid = this.form.id;
          this.param.style = this.form.style;
          this.param.describes = this.form.describes;
          this.param.greatnum = this.form.greatnum;
          request.post('/collect/save', this.param).then( res2 => {
            if(res2.code === '200') {
              this.$notify.success("收藏成功");
            } else{
              this.$notify.error(res2.msg)
            }
          })
        }
      })
    },
    goBack(){
      this.$router.push('/user/getroom')
    },
    // 点赞
    great(id){
      request.put('/roomstyle/great/' + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('点赞成功');
          this.form.greatnum += 1;
          // this.$forceUpdate();
        } else{
          this.$notify.error(res.msg)
        }
      });
    }
  },
  created(){
    this.ID = this.$route.query.id;
    request.get("/user/" + this.userID).then( res => {
      this.user = res.data;
    })
    this.load()
  },
}
</script>

<style scoped>

.room{
  font-size: 20px;
  padding-top: 10px;
  margin-left: 10px;
}

.article{
  width: 1000px
}

/* 评论框 */
.head {
  background-color: rgb(248, 248, 248);
  position: relative;
  height: 75px;
  border-radius: 5px;
}
.head img {
  width: 55px;
  height: 55px;
  border-radius: 50%;
  position: absolute;
  top: 10px;
  left: 13px;
}
/* 评论框 */
.head input {
  position: absolute;
  top: 13px;
  left: 80px;
  height: 45px;
  border-radius: 5px;
  outline: none;
  width: 65%;
  font-size: 20px;
  padding: 0 20px;
  border: 2px solid #f8f8f8;
}
/* 发布评论按钮 */
.head button {
  position: absolute;
  top: 13px;
  right: 20px;
  width: 120px;
  height: 48px;
  border: 0;
  border-radius: 5px;
  font-size: 20px;
  font-weight: 500;
  color: #fff;
  background-color: rgb(118, 211, 248);
  cursor: pointer;
  letter-spacing: 2px;
}
/* 鼠标经过字体加粗 */
.head button:hover {
  font-weight: 600;
}

/* 评论内容区域 */
.content .first {
  display: flex;
  position: relative;
  padding: 10px 10px 0px 0;
}
.first .first-img {
  flex: 1;
  text-align: center;
}
.first img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
.first-username,
.second-username {
  color: #504f4f;
}
.first-content {
  flex: 9;
}
.first-time,
.second-time {
  color: #767575;
}
.first-comment,
.second-comment {
  margin-top: 20px;
}
/* 右边点赞和评论 */
.first-right,
.second-right {
  position: absolute;
  right: 1%;
  top: 10px;
}
.first-right span,
.second-right span {
  margin-right: 20px;
  cursor: pointer;
}
/* 删除评论 */
.delete:hover {
  color: red;
}
/* 评论字体图标 */
.comments::before {
  /* 想使用的icon的十六制编码，去掉&#x之后的 */
  content: "\e8b9";
  /* 必须加 */
  font-family: "iconfont";
  margin-right: 4px;
  font-size: 16px;
}
/* 点赞字体图标 */
.praise::before {
  /* 想使用的icon的十六制编码，去掉&#x之后的 */
  content: "\ec7f";
  /* 必须加 */
  font-family: "iconfont";
  margin-right: 4px;
  font-size: 19px;
}
.second {
  background-color: #f3f3f3;
  margin-top: 10px;
}
.second li {
  padding: 10px 10px 10px 0;
  border-bottom: 1px solid rgb(237, 237, 237);
}
.second .top {
  display: flex;
  position: relative;
}
.second-img {
  flex: 1;
  text-align: center;
}
.to_reply {
  color: rgb(106, 106, 106);
}
.second-content {
  flex: 9;
}
.second .reply_li {
  margin-left: 70px;
}
/* 评论框 */
.reply-comment {
  margin: 10px 0 0 0;
}
.reply-comment input {
  height: 40px;
  border-radius: 5px;
  outline: none;
  width: 70%;
  font-size: 18px;
  padding: 0 20px;
  /* border: 2px solid #f8f8f8; */
  border: 2px solid skyblue;
}
/* 发布评论按钮 */
.reply-comment button {
  width: 15%;
  height: 43px;
  border: 0;
  border-radius: 5px;
  font-size: 18px;
  font-weight: 500;
  color: #fff;
  background-color: rgb(118, 211, 248);
  cursor: pointer;
  letter-spacing: 2px;
  margin-left: 20px;
}
/* 鼠标经过字体加粗 */
.reply-comment button:hover {
  font-weight: 600;
}
/* 评论点赞颜色 */
.comment-like {
  color: red;
}

</style>