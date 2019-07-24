<template>
  <div class="login-box" align="center">
    <h1>FIT</h1>
    <h2>丑不拉几的管理员页面</h2>
    <el-container>
      <el-aside width="560px"></el-aside>
      <el-container>
        <el-header></el-header>
        <el-main>
          <el-row>
            <el-col :span="10">
              <el-input v-model="id" placeholder="请输入帐号">
                <template slot="prepend">帐号</template>
              </el-input>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-input
                @keyup.enter.native="login"
                v-model="password"
                type="password"
                placeholder="请输入密码"
              >
                <template slot="prepend">密码</template>
              </el-input>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-button id="login" v-on:click="login" style="width:100%" type="primary">登录</el-button>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { constants } from "fs";
import http from "../http-common.js";
export default {
  data() {
    return {
      id: "",
      password: ""
    };
  },
  methods: {
    login() {
      // check
      if (this.id == "" || this.password == "") {
        alert("ID或密码为空");
      }
      var data = {
        id: this.id,
        pwd: this.password
      };
      http
        .post("/admin/login", data)
        .then(response => {
          console.log(response.data);
          sessionStorage.removeItem("login");
          if (response.data == "102") {
            alert("登录失败，ID或密码错误");
          } else if (response.data == "101") {
            alert("登录失败，ID不存在");
          } else if (response.data == "100") {
            alert("登录成功");
            sessionStorage.setItem("login", response.data);
            this.$router.replace("/ManageUser");
          }
        })
        .finally(() => {
          this.loading = false;
        })
        .catch(e => {
          console.log(e);
        });
    }
  }
};
</script>

<style>
</style>
