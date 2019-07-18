import axios from "axios";

export default axios.create({
  baseURL: "http://202.120.40.8:30233",
  headers: {
    "Content-type": "application/json",
  }
});
