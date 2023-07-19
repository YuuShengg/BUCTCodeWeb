<template>
  <div class="container">
    <el-card class="AtCoder-card">
      <div slot="header" class="clearfix">
        <span>AtCoder 比赛信息</span>
      </div>
      <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" stripe border height="600"
        highlight-current-row style="width: 100% height:fit-content;" v-loading="loading" :cell-style="rowStyle">
        <el-table-column prop="acContestName" label="比赛名称" width="500px" :show-overflow-tooltip='true' align="center">
          <template slot-scope="scope">
            <el-link icon="el-icon-s-data" @click="openparticipateDialog(scope.row)">{{ scope.row.acContestName
            }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="acContestId" label="比赛编号" width="100px" align="center"></el-table-column>
        <el-table-column prop="acContestType" label="比赛类型" width="150px" align="center"></el-table-column>
        <el-table-column prop="acContestStarttimeseconds" label="开始时间" width="100px" align="center"></el-table-column>
        <el-table-column prop="acContestPhase" label="当前阶段" align="center"></el-table-column>
        <el-table-column prop="acContestDurationseconds" label="持续时间" align="center"></el-table-column>
        <el-table-column prop="acContestParticipantsnumber" label="参与人数" align="center"></el-table-column>
      </el-table>
      <div class="block">
        <el-pagination layout="total, prev, pager, next" :total="totalNum" :page-size="pageSize"
          :current-page="currentPage" @current-change="handleCurrentChange" background></el-pagination>
      </div>

      <el-dialog :visible.sync="participatedialogVisible" title="参与情况" @close="clearTableData">
        <el-table :data="participateData" stripe border height="300" highlight-current-row
          style="width: 100% height:fit-content;" v-loading="participateloading" :cell-style="rowStyle">
          <el-table-column prop="stuName" label="姓名" width="80px" :show-overflow-tooltip='true' align="center">
          </el-table-column>
          <el-table-column prop="stuAcId" label="用户名" width="150px" align="center"></el-table-column>
          <el-table-column prop="stuClass" label="班级" width="80px" align="center"></el-table-column>
          <el-table-column prop="ranks" label="排名" align="center" sortable></el-table-column>
          <el-table-column prop="acNumber" label="解题数" align="center"></el-table-column>
          <el-table-column prop="scNumber" label="补题数" align="center"></el-table-column>
          <el-table-column prop="newRating" label="积分" align="center"></el-table-column>
        </el-table>
      </el-dialog>

    </el-card>
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
      loading: true,
      participatedialogVisible: false, // 对话框可见性
      participateselectedRow: null, // 选中的行数据
      participateData: [],
      participateloading: true,
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    clearTableData() {
      this.participateData = []; // 清空表格数据
    },
    rowStyle() {
      return "text-align:center";
    },
    getInfo() {
      axios.get('/stu/info/acmer/accontest/all/1/1000').then(res => {
        if (res.data.code === 200) {
          this.loading = false
          const msgInfo = res.data.data.records
          for (const item in msgInfo) {
            this.tableData.push({
              acContestName: msgInfo[item].acContestName,
              acContestId: msgInfo[item].acContestId,
              acContestType: msgInfo[item].acContestType.slice(10,),
              acContestPhase: msgInfo[item].acContestPhase,
              acContestDurationseconds: msgInfo[item].acContestDurationseconds,
              acContestStarttimeseconds: msgInfo[item].acContestStarttimeseconds.slice(0, 19),
              acContestParticipantsnumber: msgInfo[item].acContestParticipantsnumber,
            })
          }
          this.totalNum = this.tableData.length
        }
      })
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    openparticipateDialog(row) {//参与情况
      this.participateselectedRow = row;
      this.participatedialogVisible = true;
      axios.get('/stu/info/acmer/accontest/' + this.participateselectedRow.acContestName + '/1/100').then(res => {
        if (res.data.code === 200) {
          this.participateloading = false
          const msgInfo = res.data.data.records
          for (const item in msgInfo) {
            this.participateData.push({
              stuName: msgInfo[item].stuName,
              stuAcId: msgInfo[item].stuAcId,
              stuClass: msgInfo[item].stuClass,
              ranks: parseInt(msgInfo[item].ranks),
              acNumber: msgInfo[item].acNumber,
              scNumber: msgInfo[item].scNumber,
              newRating: msgInfo[item].newRating,
            })
          }
        }
      })
    },
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

.AtCoder-card {
  margin-top: 20px;
  margin-right: 20px;
}
</style>
