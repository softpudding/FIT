<template>
  <div class="login-box" align="center">
    <h1>FIT</h1>
    <h2>增加新闻</h2>
    <el-container>
      <el-aside width="560px"></el-aside>
      <el-container>
        <el-header></el-header>
        <el-main>
          <el-row>
            <el-col :span="10">
              <el-input v-model="tittle" placeholder="题目">
                <template slot="prepend">标题</template>
              </el-input>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-input v-model="news" @keyup.enter.native="addNews" placeholder="请输入内容">
                <template slot="prepend">正文</template>
              </el-input>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-button id="login" v-on:click="addNews" style="width:100%" type="primary">发布</el-button>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<script>
import http from "../http-common.js";
import { constants } from "fs";
import { resolve } from "path";
export default {
  data() {
    return {
      tittle: "",
      news: ""
    };
  },
  mounted() {
    const data = JSON.parse(sessionStorage.getItem("login"));
    if (data == null) {
      alert("尚未登录");
      this.$router.replace("/");
    }
  },
  methods: {
    addNews() {
      var data = {
        tittle: this.tittle,
        news: this.news
      };
      http.post("news/AddNews", data).then(response => {
        console.log(response.data);
        alert("公告添加成功");
        this.$router.replace("/main");
      });
    }
  }
};
</script>

<style>
</style>
