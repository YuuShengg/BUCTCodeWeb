<template>
  <div class="container">
    <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" stripe border height="600"
      highlight-current-row style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="id" width="130px"></el-table-column>
      <el-table-column prop="date" label="date" width="210" sortable></el-table-column>
      <el-table-column prop="contest" label="contest" width="180px"></el-table-column>
      <el-table-column prop="rank" label="rank" sortable></el-table-column>
      <el-table-column prop="performance" label="performance" width="120px"></el-table-column>
      <el-table-column prop="newRating" label="newRating" width="120px"></el-table-column>
      <el-table-column prop="diff" label="diff" width="80px"></el-table-column>
      <el-table-column prop="count" label="count"></el-table-column>
      <el-table-column prop="maxrating" label="maxrating" sortable></el-table-column>
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
      axios.get('/stu/info/acmer/atcoder/all/1/100').then(res => {
        if (res.data.code === 200) {
          this.loading = false
          const msgInfo = res.data.data.records
          for (const item in msgInfo) {
            const rank = parseInt(msgInfo[item].acRank)
            const maxrating = parseInt(msgInfo[item].acMaxrating)
            this.tableData.push({
              id: msgInfo[item].acId,
              date: msgInfo[item].acDate.slice(0,19),
              contest: msgInfo[item].acContest,
              rank: rank,
              performance: msgInfo[item].acPerformance,
              newRating: msgInfo[item].acNewrating,
              diff: msgInfo[item].acDiff,
              count: msgInfo[item].acCount,
              maxrating: maxrating,
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
