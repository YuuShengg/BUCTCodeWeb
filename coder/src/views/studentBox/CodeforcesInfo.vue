<template>
    <div class="container">
      <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" stripe border height="600"
        highlight-current-row style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="id" width="130px"></el-table-column>
        <el-table-column prop="contest" label="contest" width="210" ></el-table-column>
        <el-table-column prop="contest_id" label="contest_id" width="180px"></el-table-column>
        <el-table-column prop="rank" label="rank" sortable></el-table-column>
        <el-table-column prop="old_rating" label="old_rating" width="120px"></el-table-column>
        <el-table-column prop="new_rating" label="new_rating" width="120px"></el-table-column>
        <el-table-column prop="sum_contest" label="sum_contest" width="80px"></el-table-column>
        <el-table-column prop="date" label="date" sortable></el-table-column>
        <!--      <el-table-column  label="操作" width="100px">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
        </template>
        </el-table-column>-->
      </el-table>
      <div class="block">
        <el-pagination layout="total, prev, pager, next" :total="totalNum" :page-size="pageSize" :current-page="currentPage"
          @current-change="handleCurrentChange" background></el-pagination>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios'
  export default {
    data() {
      return {
        pageSize: 10, // 表示一页多少条数据
        totalNum: 0,
        currentPage: 1,
        tableData: [],
        loading: true
      }
    },
    created() {
      this.getInfo()
    },
    methods: {
      getInfo() {
        axios.get('/stu/info/acmer/codeforces/all/1/100').then(res => {
          if (res.data.code === 200) {
            this.loading = false
            const msgInfo = res.data.data.records
            for (const item in msgInfo) {
              const rank = parseInt(msgInfo[item].cfRank)
              this.tableData.push({
                id: msgInfo[item].cfId,
                contest: msgInfo[item].cfContest,
                contest_id: msgInfo[item].cfContestId,
                rank: rank,
                old_rating: msgInfo[item].cfOldRating,
                new_rating: msgInfo[item].cfNewRating,
                sum_contest: msgInfo[item].cfSumContest,
                date: msgInfo[item].cfDate.slice(0,19)
              })
            }
            this.totalNum = this.tableData.length
          }
        })
      },
      handleCurrentChange(val) {
        this.currentPage = val
      }
    }
  }
  </script>
  
  <style scoped>
  .container {
    width: 100%;
    background-color: white;
    box-sizing: border-box;
    border-bottom: #eceef0 solid 2px;
    padding: 0px 18px;
  }
  
  .container .block {
    display: flex;
    justify-content: center;
    margin-top: 5px;
  }
  </style>
  