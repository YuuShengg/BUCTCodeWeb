<template>
    <div>
        <el-row>
            <el-col :span="16">
                <el-card class="studentbox-card">
                    <div slot="header" class="clearfix">
                        <span>学生数据统计</span>
                        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
                    </div>
                    <el-table
                        :data="studenttableData.slice((studentcurrentPage - 1) * studentpageSize, studentcurrentPage * studentpageSize)"
                        stripe border height="600" highlight-current-row style="width: 100%; height:fit-content;"
                        v-loading="studentloading" :cell-style="rowStyle">
                        <el-table-column prop="stuName" label="姓名" width="100px" align="center" fixed>
                            <template slot-scope="scope">
                                <el-link icon="el-icon-s-data" @click="opensubmitDialog(scope.row)">{{
                                    scope.row.stuName
                                }}</el-link>
                            </template>
                        </el-table-column>
                        <el-table-column prop="stuNo" label="学号" align="center"></el-table-column>
                        <el-table-column prop="stuClass" label="班级" align="center"></el-table-column>
                        <el-table-column prop="cfNewRating" label="Cf最新积分" width="120px" align="center"
                            sortable></el-table-column>
                        <el-table-column prop="acNewRating" label="AC最新积分" width="130px" align="center"
                            sortable></el-table-column>
                        <el-table-column prop="cfSumContest" label="Cf比赛场数" width="120px" align="center"
                            sortable></el-table-column>
                        <el-table-column prop="acCount" label="AC比赛场数" width="130px" align="center"
                            sortable></el-table-column>
                        <el-table-column prop="cfAcNumberSum" label="Cf解题数" width="110px" align="center"
                            sortable></el-table-column>
                        <el-table-column prop="cfScNumberSum" label="Cf补题数" width="110px" align="center"
                            sortable></el-table-column>
                        <el-table-column prop="atAcNumberSum" label="AC解题数" width="110px" align="center"
                            sortable></el-table-column>
                        <el-table-column prop="stuAcId" label="AtCoder ID" :show-overflow-tooltip='true'
                            align="center"></el-table-column>
                        <el-table-column prop="stuCfId" label="Codeforces ID" :show-overflow-tooltip='true'
                            align="center"></el-table-column>
                    </el-table>
                    <div class="block">
                        <el-pagination layout="total, prev, pager, next" :total="studenttotalNum"
                            :page-size="studentpageSize" :current-page="studentcurrentPage"
                            @current-change="handlestudentCurrentChange" background></el-pagination>
                    </div>

                    <!-- 弹出对话框展示个人数据 -->
                    <el-dialog :visible.sync="submitdialogVisible" :title="person" @close="clearTableData"
                        fullscreen="true">
                        <el-tabs v-model="onset">
                            <el-tab-pane label="Codeforces 比赛情况" name="first">
                                <div id="Cfchart"
                                    style="display: flex; justify-content: center; width: 1400px; height: 400px;"></div>

                                <el-table :data="personalCfData" stripe border height="600" highlight-current-row
                                    style="width: 100% height:fit-content;" v-loading="personalCfloading"
                                    :cell-style="rowStyle">
                                    <el-table-column prop="cfUserId" label="用户名" width="200px" :show-overflow-tooltip='true'
                                        align="center">
                                    </el-table-column>
                                    <el-table-column prop="cfContestId" label="比赛编号" width="80px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="cfContestName" label="比赛名称" :show-overflow-tooltip='true'
                                        align="center"></el-table-column>
                                    <el-table-column prop="cfUpdateTime" label="比赛时间" width="200px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="cfRank" label="排名" width="80px" align="center"></el-table-column>
                                    <el-table-column prop="cfAcNumber" label="解题数" width="80px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="cfScNumber" label="补题数" width="80px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="cfNewRating" label="赛后积分" width="80px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="change" label="积分变化" width="80px"
                                        align="center"></el-table-column>
                                </el-table>
                            </el-tab-pane>

                            <el-tab-pane label="AtCoder 比赛情况" name="second">
                                <div id="ACchart"
                                    style="display: flex; justify-content: center; width: 1400px; height: 400px;"></div>
                                <el-table :data="personalACData" stripe border height="600" highlight-current-row
                                    style="width: 100% height:fit-content;" v-loading="personalACloading"
                                    :cell-style="rowStyle">
                                    <el-table-column prop="acUserId" label="用户名" width="200px" :show-overflow-tooltip='true'
                                        align="center">
                                    </el-table-column>
                                    <el-table-column prop="acContestName" label="比赛名称" :show-overflow-tooltip='true'
                                        align="center"></el-table-column>
                                    <el-table-column prop="acContestDate" label="比赛时间" width="200px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="acRank" label="排名" width="80px" align="center"></el-table-column>
                                    <el-table-column prop="acAcNumber" label="解题数" width="80px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="acNewRating" label="赛后积分" width="80px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="acDiff" label="积分变化" width="80px"
                                        align="center"></el-table-column>
                                </el-table>
                            </el-tab-pane>

                            <el-tab-pane label="所有提交记录" name="third">
                                <el-table :data="submitData" stripe border height="600" highlight-current-row
                                    style="width: 100% height:fit-content;" v-loading="submitloading"
                                    :cell-style="rowStyle">
                                    <el-table-column prop="cfSubmissionId" label="提交序号" width="100px" align="center">
                                    </el-table-column>
                                    <el-table-column prop="cfUserId" label="用户" width="200px" :show-overflow-tooltip='true'
                                        align="center"></el-table-column>
                                    <el-table-column prop="cfContestId" label="比赛编号" width="110px" align="center"
                                        sortable></el-table-column>
                                    <el-table-column prop="cfProblemIndex" label="题目索引" align="center"></el-table-column>
                                    <el-table-column prop="cfProblemName" label="题目名称" width="300px"
                                        :show-overflow-tooltip='true' align="center"></el-table-column>
                                    <el-table-column prop="cfSubmissionLanguage" label="编程语言" width="150px"
                                        :show-overflow-tooltip='true' align="center"></el-table-column>
                                    <el-table-column prop="cfSubmissionType" label="提交方式" width="80px"
                                        align="center"></el-table-column>
                                    <el-table-column prop="cfVerdict" label="结果" align="center"
                                        :show-overflow-tooltip='true'></el-table-column>
                                </el-table>
                            </el-tab-pane>
                        </el-tabs>
                    </el-dialog>
                </el-card>

            </el-col>
            <el-col :span="8">
                <el-row>
                    <el-card class="carouselblock">
                        <el-carousel :interval="4000" height="205px" ref="carousel" @click.native="linkTo()">
                            <el-carousel-item v-for="  item   in   imgs  " :key="item.url">
                                <img :src="item.url" style="object-fit: scale-down;height: 100%;width: 100%;">
                            </el-carousel-item>
                        </el-carousel>
                    </el-card>
                </el-row>
                <el-row>
                    <el-card class="contestbox-card">
                        <div slot="header" class="clearfix">
                            <span>近期比赛</span>
                            <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
                        </div>
                        <el-table
                            :data="contesttableData.slice((contestcurrentPage - 1) * contestpageSize, contestcurrentPage * contestpageSize)"
                            stripe border height="333" highlight-current-row style="width: 100%;height: fit-content;"
                            v-loading="contestloading" :cell-style="rowStyle">
                            <el-table-column prop="contestName" label="比赛名称" align="center" :show-overflow-tooltip='true'
                                fixed>
                                <template slot-scope="scope">
                                    <el-link icon="el-icon-s-data" @click="gotoContest(scope.row)">{{
                                        scope.row.contestName
                                    }}</el-link>
                                </template>
                            </el-table-column>
                            <el-table-column prop="contestStartTimeSeconds" label="开始时间" width="100px" align="center"
                                fixed></el-table-column>
                            <el-table-column prop="contestDurationSeconds" label="持续时间" width="90px" align="center"
                                fixed></el-table-column>
                            <el-table-column prop="contestUrl" label="链接" align="center" v-if="nothing"></el-table-column>
                        </el-table>
                        <div class="block">
                            <el-pagination layout="total, prev, pager, next" :total="contesttotalNum"
                                :page-size="contestpageSize" :current-page="contestcurrentPage"
                                @current-change="handlecontestCurrentChange" background></el-pagination>
                        </div>
                    </el-card>
                </el-row>


            </el-col>
        </el-row>
    </div>
</template>


<script>
import axios from 'axios'
import * as echarts from 'echarts';
export default {
    data() {
        return {
            imgs: [
                { url: 'https://www.buctcoder.com/ad1.JPG', link: 'https://www.buctcoder.com/loginpage.php' },
                { url: 'https://www.buctcoder.com/ad3.JPG', link: 'https://wj.qq.com/s2/8625740/684f/' },
                { url: 'https://www.buctcoder.com/ad4.JPG', link: 'https://cist.buct.edu.cn/2020/1203/c981a140579/page.htm' },
            ],
            studentpageSize: 12, // 表示一页多少条数据
            studenttotalNum: 0,
            studentcurrentPage: 1,
            studenttableData: [],
            studentloading: true,

            contestpageSize: 5, // 表示一页多少条数据
            contesttotalNum: 0,
            contestcurrentPage: 1,
            contesttableData: [],
            contestloading: true,

            submitdialogVisible: false, // 对话框可见性
            submitselectedRow: null, // 选中的行数据
            submitData: [],
            submitloading: true,

            gotoContestRow: null,

            personalCfData: [],
            personalCfloading: true,
            personalACData: [],
            personalACloading: true,
            person: null,
            onset: 'first',
            CfchartData: [],
            ACchartData: [],
        }
    },
    created() {
        this.getstudentInfo()
        this.getcontestInfo()
    },
    methods: {
        clearTableData() {
            this.submitData = []; // 清空表格数据
            this.personalCfData = []; // 清空表格数据
            this.personalACData = []; // 清空表格数据
        },
        rowStyle() {
            return "text-align:center";
        },
        linkTo() {
            let activeIndex = this.$refs.carousel.activeIndex
            window.open(this.imgs[activeIndex].link, "_blank")
        },
        getstudentInfo() {
            axios.get('/stu/info/acmer/student/all/1/100').then(res => {
                if (res.data.code === 200) {
                    this.studentloading = false
                    const msgInfo = res.data.data.records
                    this.studenttableData = []
                    for (const item in msgInfo) {
                        const acNewRating = 0
                        if (msgInfo[item].acNewRating !== '-') {
                            acNewRating = parseInt(msgInfo[item].acNewRating)
                        }
                        const cfNewRating = parseInt(msgInfo[item].cfNewRating)
                        this.studenttableData.push({
                            stuNo: msgInfo[item].stuNo,
                            stuName: msgInfo[item].stuName,
                            stuClass: msgInfo[item].stuClass,
                            stuAcId: msgInfo[item].stuAcId,
                            stuCfId: msgInfo[item].stuCfId,
                            cfNewRating: cfNewRating,
                            acNewRating: acNewRating,
                            cfSumContest: msgInfo[item].cfSumContest,
                            acCount: msgInfo[item].acCount,
                            cfAcNumberSum: msgInfo[item].cfAcNumberSum,
                            cfScNumberSum: msgInfo[item].cfScNumberSum,
                            atAcNumberSum: msgInfo[item].atAcNumberSum,
                        })
                    }
                    this.studenttotalNum = this.studenttableData.length
                }
            })

        },
        getcontestInfo() {
            axios.get('/stu/info/acmer/contest/recent').then(res => {
                if (res.data.code === 200) {
                    this.contestloading = false
                    const msgInfo = res.data.data
                    this.contesttableData = []
                    for (const item in msgInfo) {
                        this.contesttableData.push({
                            contestName: msgInfo[item].contestName,
                            contestStartTimeSeconds: msgInfo[item].contestStartTimeSeconds.slice(0, 19),
                            contestDurationSeconds: msgInfo[item].contestDurationSeconds,
                            contestUrl: msgInfo[item].contestUrl,
                        })
                    }
                    this.contesttotalNum = this.contesttableData.length
                }
            })
        },
        handlestudentCurrentChange(val) {
            this.studentcurrentPage = val
        },
        handlecontestCurrentChange(val) {
            this.contestcurrentPage = val
        },
        opensubmitDialog(row) {//提交情况
            this.submitselectedRow = row
            this.submitdialogVisible = true
            this.person = this.submitselectedRow.stuName + " 个人数据"

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
            axios.get('/stu/info/acmer/cfrating/all/select/1/1000/cf_user_id/' + this.submitselectedRow.stuCfId).then(res => {
                if (res.data.code === 200) {
                    this.personalCfloading = false
                    const msgInfo = res.data.data.records
                    for (const item in msgInfo) {
                        this.personalCfData.push({
                            cfContestId: msgInfo[item].cfContestId,
                            cfContestName: msgInfo[item].cfContestName,
                            cfUserId: msgInfo[item].cfUserId,
                            cfRank: parseInt(msgInfo[item].cfRank),
                            cfUpdateTime: msgInfo[item].cfUpdateTime,
                            cfNewRating: parseInt(msgInfo[item].cfNewRating),
                            cfAcNumber: msgInfo[item].cfAcNumber,
                            cfScNumber: msgInfo[item].cfScNumber,
                            change: parseInt(msgInfo[item].cfNewRating) - parseInt(msgInfo[item].cfOldRating),
                        })
                        this.CfchartData.push([msgInfo[item].cfUpdateTime, parseInt(msgInfo[item].cfNewRating)])
                    }
                }
            })
            axios.get('/stu/info/acmer/acrating/all/select/1/100/ac_user_id/' + this.submitselectedRow.stuAcId).then(res => {
                if (res.data.code === 200) {
                    this.personalACloading = false
                    const msgInfo = res.data.data.records
                    for (const item in msgInfo) {
                        this.personalACData.push({
                            acContestName: msgInfo[item].acContestName,
                            acUserId: msgInfo[item].acUserId,
                            acRank: parseInt(msgInfo[item].acRank),
                            acContestDate: msgInfo[item].acContestDate.slice(0, 19),
                            acNewRating: parseInt(msgInfo[item].acNewRating),
                            acAcNumber: msgInfo[item].acAcNumber,
                            acDiff: parseInt(msgInfo[item].acDiff),
                        })
                        this.ACchartData.push([msgInfo[item].acContestDate, parseInt(msgInfo[item].acNewRating)])
                    }
                }
            })

            var CfchartDom = document.getElementById('Cfchart');
            var myCfChart = echarts.init(CfchartDom);
            var Cfoption;

            Cfoption = {
                tooltip: {
                    trigger: 'axis',
                    position: function (pt) {
                        return [pt[0], '10%'];
                    }
                },
                title: {
                    left: 'center',
                    text: '积分变化'
                },
                toolbox: {
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'time',
                    boundaryGap: [0, '100%']
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%']
                },
                dataZoom: [
                    {
                        type: 'inside',
                        start: 0,
                        end: 20
                    },
                    {
                        start: 0,
                        end: 20
                    }
                ],
                series: [
                    {
                        name: 'Fake Data',
                        type: 'line',
                        smooth: false,
                        symbol: 'none',
                        areaStyle: {},
                        data: this.CfchartData
                    }
                ]
            };

            Cfoption && myCfChart.setOption(Cfoption);

            var ACchartDom = document.getElementById('ACchart');
            var myACChart = echarts.init(ACchartDom);
            var ACoption;

            ACoption = {
                tooltip: {
                    trigger: 'axis',
                    position: function (pt) {
                        return [pt[0], '10%'];
                    }
                },
                title: {
                    left: 'center',
                    text: '积分变化'
                },
                toolbox: {
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'time',
                    boundaryGap: false
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%']
                },
                dataZoom: [
                    {
                        type: 'inside',
                        start: 0,
                        end: 20
                    },
                    {
                        start: 0,
                        end: 20
                    }
                ],
                series: [
                    {
                        name: 'Fake Data',
                        type: 'line',
                        smooth: false,
                        symbol: 'none',
                        areaStyle: {},
                        data: this.ACchartData
                    }
                ]
            };

            ACoption && myACChart.setOption(ACoption);

        },
        gotoContest(row) {
            this.gotoContestRow = row;
            //是否纯数字
            if (!isNaN(this.gotoContestRow.contestUrl)) {
                const url = "https://codeforces.com/contests/" + this.gotoContestRow.contestUrl
                window.open(url, '_blank') // 在新窗口打开外链接
            } else {
                const url = "https://atcoder.jp" + this.gotoContestRow.contestUrl
                window.open(url, '_blank') // 在新窗口打开外链接
            }
        }
    }
}
</script>

<style>
.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.studentbox-card {
    margin-top: 20px;
    margin-right: 20px;
}

.contestbox-card {
    margin-top: 20px;
}

.carouselblock {
    background-color: #243946;
    margin-top: 20px;
}

.el-carousel__item {
    line-height: 200px;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

.block {
    display: flex;
    justify-content: center;
    margin-top: 5px;
}
</style>