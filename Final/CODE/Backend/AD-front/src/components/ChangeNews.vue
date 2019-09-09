<template></template>

<script>
import http from "../http-common.js";
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
    http
      .post("/news/getOneNews")
      .then(response => {
          this.tittle = response.data.tittle;
          this.news = response.data.news;
          console.log(response.data);
      })
      .finally(() => {
        this.loading = false;
      })
      .catch(e => {
        console.log(e);
      });
  },
  methods: {}
};
</script>

<style>
</style>
