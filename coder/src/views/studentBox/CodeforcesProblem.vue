<template>
    <div class="container">
        <el-card class="Codeforces-card">
            <div slot="header" class="clearfix" style="display: flex;">
                <span>Codeforces 所有题目</span>
            </div>

            <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" stripe border
                height="600" highlight-current-row style="width: 100% height:fit-content;" v-loading="loading"
                :cell-style="rowStyle">
                <el-table-column prop="cfContestId" label="比赛编号" width="80px" align="center">
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

            gotoProblemRow: null,
        }
    },
    created() {
        this.getInfo()
    },
    methods: {
        rowStyle() {
            return "text-align:center";
        },
        getInfo() {
            axios.get('/stu/info/acmer/cfproblem/all/1/1000').then(res => {
                if (res.data.code === 200) {
                    this.loading = false
                    const msgInfo = res.data.data.records
                    for (const item in msgInfo) {
                        this.tableData.push({
                            cfContestId: msgInfo[item].cfContestId,
                            cfName: msgInfo[item].cfName,
                            cfIndex: msgInfo[item].cfIndex,
                            cfRating: msgInfo[item].cfRating,
                            cfTags: msgInfo[item].cfTags.replace(/[\[\]"]/g, ''),
                        })
                    }
                    this.totalNum = this.tableData.length
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

.Codeforces-card {
    margin-top: 20px;
    margin-right: 20px;
}
</style>