<template>
  <div class="container">
    <el-card class="Codeforces-card">
      <div slot="header" class="clearfix" style="display: flex;">
        <span>Codeforces 比赛信息</span>
        <!-- <el-input placeholder="输入比赛名称或编号" v-model="input" style="width: fit-content; margin-left: auto;"
          clearable></el-input> -->
        <el-button type="primary" icon="el-icon-document" style="margin-left: auto;"
          @click="getproblems">所有题目列表</el-button>
      </div>
      <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" stripe border height="600"
        highlight-current-row style="width: 100% height:fit-content;" v-loading="loading" :cell-style="rowStyle">

        <el-table-column prop="cfContestName" label="比赛名称" width="500px" :show-overflow-tooltip='true' align="center">
          <template slot-scope="scope">
            <el-link icon="el-icon-s-data" @click="openparticipateDialog(scope.row)">{{ scope.row.cfContestName
            }}</el-link>
          </template>
        </el-table-column>

        <el-table-column prop="cfContestId" label="比赛编号" width="100px" align="center">
          <template slot-scope="scope">
            <el-link icon="el-icon-view" @click="openproblemDialog(scope.row)">{{ scope.row.cfContestId }}</el-link>
          </template>
        </el-table-column>

        <el-table-column prop="cfContestType" label="比赛类型" width="100px" align="center"></el-table-column>
        <el-table-column prop="cfContestStarttimeseconds" label="开始时间" width="100px" align="center"></el-table-column>
        <el-table-column prop="cfContestPhase" label="当前阶段" align="center"></el-table-column>
        <el-table-column prop="cfContestDurationseconds" label="持续时间" align="center"></el-table-column>
        <el-table-column prop="cfContestParticipantsnumber" label="参与人数" align="center"></el-table-column>
      </el-table>
      <div class="block">
        <el-pagination layout="total, prev, pager, next" :total="totalNum" :page-size="pageSize"
          :current-page="currentPage" @current-change="handleCurrentChange" background></el-pagination>
      </div>

      <el-dialog :visible.sync="problemdialogVisible" @close="clearTableData">
        <el-tabs v-model="onset">
          <el-tab-pane label="题目列表" name="first">
            <el-table :data="problemlistData" stripe border height="400" highlight-current-row
              style="width: 100% height:fit-content;" v-loading="problemlistloading" :cell-style="rowStyle">
              <el-table-column prop="cfContestId" label="比赛编号" width="100px" :show-overflow-tooltip='true' align="center"
                v-if="nothing">
              </el-table-column>
              <el-table-column prop="cfName" label="题目名称" width="300px" :show-overflow-tooltip='true' align="center">
                <template slot-scope="scope">
                  <el-link icon="el-icon-s-data" @click="gotoProblem(scope.row)">{{
                    scope.row.cfName
                  }}</el-link>
                </template>
              </el-table-column>
              <el-table-column prop="cfIndex" label="索引" width="80px" align="center" sortable></el-table-column>
              <el-table-column prop="cfRating" label="评级" width="80px" align="center" sortable></el-table-column>
              <el-table-column prop="cfTags" label="标签" :show-overflow-tooltip='true' align="center"></el-table-column>
            </el-table>
          </el-tab-pane>

          <el-tab-pane label="提交列表" name="second">
            <el-table :data="problemsubmitData.slice((submitcurrentPage - 1) * pageSize, submitcurrentPage * pageSize)"
              stripe border height="400" highlight-current-row style="width: 100% height:fit-content;"
              v-loading="problemsubmitloading" :cell-style="rowStyle">
              <el-table-column prop="cfSubmissionId" label="提交序号" width="100px" :show-overflow-tooltip='true'
                align="center" fixed></el-table-column>
              <el-table-column prop="cfUserId" label="用户" width="120px" :show-overflow-tooltip='true' align="center"
                sortable></el-table-column>
              <el-table-column prop="cfProblemName" label="题目名称" width="200px" :show-overflow-tooltip='true'
                align="center"></el-table-column>
              <el-table-column prop="cfProblemIndex" label="索引" width="80px" align="center" sortable>
              </el-table-column>
              <el-table-column prop="cfSubmissionDate" label="提交时间" width="120px" align="center"
                sortable></el-table-column>
              <el-table-column prop="cfSubmissionLanguage" label="编程语言" width="120px" :show-overflow-tooltip='true'
                align="center"></el-table-column>
              <el-table-column prop="cfSubmissionType" label="提交方式" align="center"></el-table-column>
              <el-table-column prop="cfVerdict" label="结果" width="120px" :show-overflow-tooltip='true'
                align="center"></el-table-column>
            </el-table>
            <div class="block">
              <el-pagination layout="total, prev, pager, next" :total="submittotalNum" :page-size="pageSize"
                :current-page="submitcurrentPage" @current-change="handleCurrentsubmitChange" background></el-pagination>
            </div>
          </el-tab-pane>
        </el-tabs>

      </el-dialog>

      <el-dialog :visible.sync="participatedialogVisible" title="参与情况" @close="clearTableData">
        <el-table :data="participateData" stripe border height="300" highlight-current-row
          style="width: 100% height:fit-content;" v-loading="participateloading" :cell-style="rowStyle">
          <el-table-column prop="cfContestId" label="比赛编号" width="80px" :show-overflow-tooltip='true' align="center">
          </el-table-column>
          <el-table-column prop="cfUserId" label="用户名" width="200px" align="center"></el-table-column>
          <el-table-column prop="cfRank" label="排名" align="center" sortable></el-table-column>
          <el-table-column prop="cfAcNumber" label="解题数" align="center"></el-table-column>
          <el-table-column prop="cfScNumber" label="补题数" align="center"></el-table-column>
          <el-table-column prop="cfNewRating" label="积分" align="center" sortable></el-table-column>
          <el-table-column prop="change" label="积分变化" align="center"></el-table-column>
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
      onset: 'first',
      input: '',
      pageSize: 10, // 表示一页多少条数据
      submittotalNum: 0,
      totalNum: 0,
      submitcurrentPage: 1,
      currentPage: 1,
      tableData: [],
      loading: true,
      problemdialogVisible: false, // 对话框可见性
      problemselectedRow: null, // 选中的行数据
      problemlistData: [],
      problemlistloading: true,
      problemsubmitData: [],
      problemsubmitloading: true,
      participatedialogVisible: false, // 对话框可见性
      participateselectedRow: null, // 选中的行数据
      participateData: [],
      participateloading: true,

      gotoProblemRow: null,
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    clearTableData() {
      this.participateData = []; // 清空表格数据
      this.problemlistData = []; // 清空表格数据
      this.problemsubmitData = []; // 清空表格数据
    },
    rowStyle() {
      return "text-align:center";
    },
    getInfo() {
      axios.get('/stu/info/acmer/cfcontest/all/1/1000').then(res => {
        if (res.data.code === 200) {
          this.loading = false
          const msgInfo = res.data.data.records
          for (const item in msgInfo) {
            this.tableData.push({
              cfContestName: msgInfo[item].cfContestName,
              cfContestId: msgInfo[item].cfContestId,
              cfContestType: msgInfo[item].cfContestType,
              cfContestPhase: msgInfo[item].cfContestPhase,
              cfContestParticipantsnumber: msgInfo[item].cfContestParticipantsnumber,
              cfContestDurationseconds: msgInfo[item].cfContestDurationseconds,
              cfContestStarttimeseconds: msgInfo[item].cfContestStarttimeseconds,
            })
          }
          this.totalNum = this.tableData.length
        }
      })
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleCurrentsubmitChange(val) {
      this.submitcurrentPage = val
    },
    openproblemDialog(row) {//题目列表+提交列表
      this.problemselectedRow = row;
      this.problemdialogVisible = true;
      axios.get('/stu/info/acmer/cfproblem/all/select/1/1000/cf_contest_id/' + this.problemselectedRow.cfContestId).then(res => {
        if (res.data.code === 200) {
          this.problemlistloading = false
          const msgInfo = res.data.data.records
          for (const item in msgInfo) {
            this.problemlistData.push({
              cfContestId: msgInfo[item].cfContestId,
              cfName: msgInfo[item].cfName,
              cfIndex: msgInfo[item].cfIndex,
              cfRating: msgInfo[item].cfRating,
              cfTags: msgInfo[item].cfTags.replace(/[\[\]"]/g, ''),
            })
          }
        }
      })
      axios.get('/stu/info/acmer/cfsubmission/contest/' + this.problemselectedRow.cfContestId + '/1/1000').then(res => {
        if (res.data.code === 200) {
          this.problemsubmitloading = false
          const msgInfo = res.data.data.records
          for (const item in msgInfo) {
            this.problemsubmitData.push({
              cfSubmissionId: msgInfo[item].cfSubmissionId,
              cfProblemName: msgInfo[item].cfProblemName,
              cfProblemIndex: msgInfo[item].cfProblemIndex,
              cfSubmissionDate: msgInfo[item].cfSubmissionDate,
              cfSubmissionLanguage: msgInfo[item].cfSubmissionLanguage,
              cfSubmissionType: msgInfo[item].cfSubmissionType,
              cfVerdict: msgInfo[item].cfVerdict,
              cfUserId: msgInfo[item].cfUserId,
            })
          }
          this.submittotalNum = this.problemsubmitData.length
        }
      })
    },
    openparticipateDialog(row) {//参与情况
      this.participateselectedRow = row;
      this.participatedialogVisible = true;
      axios.get('/stu/info/acmer/cfrating/all/select/1/100/cf_contest_name/' + this.participateselectedRow.cfContestName).then(res => {
        if (res.data.code === 200) {
          this.participateloading = false
          const msgInfo = res.data.data.records
          for (const item in msgInfo) {
            this.participateData.push({
              cfContestId: msgInfo[item].cfContestId,
              cfUserId: msgInfo[item].cfUserId,
              cfRank: parseInt(msgInfo[item].cfRank),
              cfAcNumber: msgInfo[item].cfAcNumber,
              cfScNumber: msgInfo[item].cfScNumber,
              cfNewRating: parseInt(msgInfo[item].cfNewRating),
              change: parseInt(msgInfo[item].cfNewRating) - parseInt(msgInfo[item].cfOldRating),
            })
          }
        }
      })
    },
    getproblems() {
      this.$router.push('/CodeforcesProblem')
    },
    gotoProblem(row) {
      this.gotoProblemRow = row;
      const url = "https://codeforces.com/contest/" + this.gotoProblemRow.cfContestId + "/problem/" + this.gotoProblemRow.cfIndex
      window.open(url, '_blank') // 在新窗口打开外链接
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

.Codeforces-card {
  margin-top: 20px;
  margin-right: 20px;
}
</style>