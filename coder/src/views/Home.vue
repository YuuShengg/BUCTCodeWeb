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
                        <el-table-column prop="stuName" label="姓名" align="center" fixed></el-table-column>
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
                                fixed></el-table-column>
                            <el-table-column prop="contestStartTimeSeconds" label="开始时间" width="100px" align="center"
                                fixed></el-table-column>
                            <el-table-column prop="contestDurationSeconds" label="持续时间" width="90px" align="center"
                                fixed></el-table-column>
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
            contestloading: true
        }
    },
    created() {
        this.getstudentInfo()
        this.getcontestInfo()
    },
    methods: {
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