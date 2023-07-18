<template>
    <div class="container">
        <el-card class="personal-card">
            <div slot="header" class="clearfix" style="display: flex;">
                <span>个人数据</span>
            </div>
            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="Codeforces 比赛情况" name="first">用户管理</el-tab-pane>
                <el-tab-pane label="AtCoder 比赛情况" name="second">配置管理</el-tab-pane>

                <el-tab-pane label="所有提交记录" name="third">
                    
                </el-tab-pane>
            </el-tabs>

            <div class="block">
                <el-pagination layout="total, prev, pager, next" :total="totalNum" :page-size="pageSize"
                    :current-page="currentPage" @current-change="handleCurrentChange" background></el-pagination>
            </div>
        </el-card>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            input: '',
            pageSize: 11, // 表示一页多少条数据
            totalNum: 0,
            currentPage: 1,
            tableData: [],
            loading: true,

            
            submitselectedRow: null, // 选中的行数据
            submitData: [],
            submitloading: true,
        }
    },
    created() {
        this.getsubmitInfo()
    },
    methods: {
        rowStyle() {
            return "text-align:center";
        },
        getsubmitInfo() {
            this.submitselectedRow = row;
            axios.get('/stu/info/acmer/cfsubmission/' + this.submitselectedRow.stuNo + '/1/100').then(res => {
                if (res.data.code === 200) {
                    this.submitloading = false
                    const msgInfo = res.data.data.records
                    for (const item in msgInfo) {
                        this.submitData.push({
                            cfSubmissionId: msgInfo[item].cfSubmissionId,
                            cfUserId: msgInfo[item].cfUserId,
                            cfProblemIndex: msgInfo[item].cfProblemIndex,
                            cfProblemName: msgInfo[item].cfProblemName,
                            cfSubmissionLanguage: msgInfo[item].cfSubmissionLanguage,
                            cfVerdict: msgInfo[item].cfVerdict,
                            cfSubmissionType: msgInfo[item].cfSubmissionType,
                            cfContestId: parseInt(msgInfo[item].cfContestId),
                        })
                    }
                }
            })
        },
        handleCurrentChange(val) {
            this.currentPage = val
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

.personal-card {
    margin-top: 20px;
    margin-right: 20px;
}
</style>