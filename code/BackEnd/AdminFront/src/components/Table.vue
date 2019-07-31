<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="用户管理" name="first">
      <div>
        <el-container>
          <el-container>
            <el-main>
              <el-table
                class="booktable"
                :data="tableData.filter(data => !search || data.email.toLowerCase().includes(search.toLowerCase()))"
                style="width: 100%"
              >
                <el-table-column prop="id" label="ID" width="180"></el-table-column>
                <el-table-column prop="tel" label="Telephone number" width="180"></el-table-column>
                <el-table-column prop="nickName" label="nick name" width="180"></el-table-column>
                <el-table-column prop="isactive" label="if active" width="180"></el-table-column>
                <el-table-column align="right">
                  <template slot="header" slot-scope="scope"></template>
                  <template slot-scope="scope">
                    <el-button
                      type="danger"
                      size="medium"
                      @click="setLock(scope.$index, scope.row)"
                    >封禁用户</el-button>
                    <el-button
                      size="medium"
                      type="primary"
                      @click="setUnlock(scope.$index, scope.row)"
                    >解禁用户</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-main>
          </el-container>
        </el-container>
      </div>
    </el-tab-pane>
    <el-tab-pane label="公告管理" name="second">
      <h1>公告管理</h1>

      <el-table :data="newsData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="180"></el-table-column>
        <el-table-column prop="time_stamp" label="发布时间" width="180"></el-table-column>
        <el-table-column prop="tittle" label="标题"></el-table-column>
        <el-table-column prop="if_active" label="是否显示"></el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="Empty Tab" name="third">角色管理</el-tab-pane>
  </el-tabs>
</template>

<script>
import http from "../http-common.js";
import { constants } from "fs";
export default {
  data() {
    return {
      activeName: "first",
      tableData: [],
      newsData: [],
      search: ""
    };
  },
  mounted() {
    const data = JSON.parse(sessionStorage.getItem("login"));
    if (data == null) {
      alert("尚未登录");
      this.$router.replace("/");
    }
    http
      .post("/admin/getAll")
      .then(response => {
        this.tableData = response.data;
        console.log(response.data);
      })
      .finally(() => {
        this.loading = false;
      })
      .catch(e => {
        console.log(e);
      });
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    setLock(index, row) {
      var tel = row.tel;
      http
        .post("/admin/ban", tel)
        .then(response => {
          console.log(response.data);
          if (response.data) {
            console.log("封禁成功");
          } else {
            console.log("封禁失败");
          }
          location.reload();
        })
        .catch(e => {
          console.log(e);
        });
    },
    setUnlock(index, row) {
      var tel = row.tel;
      http
        .post("/admin/relieve", tel)
        .then(response => {
          console.log(response.data);
          if (response.data) {
            console.log("解禁成功");
          } else {
            console.log("解禁失败");
          }
          location.reload();
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
