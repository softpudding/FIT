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
      <div id="addNews">
        <el-button size="medium" type="success" @click="addNews">增加公告</el-button>
      </div>
      <el-table :data="newsData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="280"></el-table-column>
        <el-table-column prop="time_stamp" label="发布时间" width="280"></el-table-column>
        <el-table-column prop="tittle" label="标题" width="280"></el-table-column>
        <el-table-column prop="active" label="是否显示" width="280"></el-table-column>
        <el-table-column align="right">
          <template slot="header" slot-scope="scope"></template>
          <template slot-scope="scope">
            <el-button type="danger" size="medium" @click="setHide(scope.$index, scope.row)">撤下公告</el-button>
            <el-button size="medium" type="primary" @click="setShow(scope.$index, scope.row)">显示公告</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="Empty Tab" name="third">后续功能敬请期待（CN jia you!）</el-tab-pane>
  </el-tabs>
</template>

<script>
//  Bvlgari宝格丽
import http from "../http-common.js";
import { constants } from "fs";
import { resolve } from "url";
import { throws } from "assert";
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
    http
      .post("/news/getAllNews")
      .then(response => {
        this.newsData = response.data;
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
    addNews() {
      this.$router.replace("/Addnews");
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
    },
    setHide(index, row) {
      var id = row.id;
      console.log(id);
      http
        .post("news/hideNews", id)
        .then(response => {
          console.log(response.data);
          if (response.data) {
            console.log("撤下成功");
          } else {
            console.log("撤下失败");
          }
        })
        .catch(e => {
          console.log(e);
        });
    },
    setShow(index, row) {
      var id = row.id;
      http
        .post("news/showNews", id)
        .then(response => {
          console.log(response.data);
          if (response.data) {
            console.log("展示成功");
          } else {
            console.log("展示失败");
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
