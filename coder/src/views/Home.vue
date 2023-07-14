<template>
    <div>
        <el-row class="carouselblock">
            <el-col :span="20" offset="2">
                <el-carousel :interval="4000" type="card" height="285px" ref="carousel" @click.native="linkTo()">
                    <el-carousel-item v-for="  item   in   imgs  " :key="item.url">
                        <img :src="item.url" style="object-fit: scale-down;height: 100%;width: 100%;">
                    </el-carousel-item>
                </el-carousel>
            </el-col>
        </el-row>
        <el-row>
            <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" stripe border
                height="500" highlight-current-row style="width: 100%" v-loading="loading" :cell-style="rowStyle">
                <el-table-column prop="stuNo" label="学号" width="50px" align="center"></el-table-column>
                <el-table-column prop="stuName" label="姓名" width="50px" align="center"></el-table-column>
                <el-table-column prop="stuClass" label="班级" width="50px" align="center"></el-table-column>
                <el-table-column prop="stuAcId" label="AtCoder ID" width="100px" align="center"></el-table-column>
                <el-table-column prop="stuCfId" label="Codeforces ID" width="120px" align="center"></el-table-column>
                <el-table-column prop="cfNewRating" label="Cf最新积分" width="100px" align="center"></el-table-column>
                <el-table-column prop="acNewRating" label="AC最新积分" align="center"></el-table-column>
                <el-table-column prop="cfSumContest" label="Cf比赛场数" align="center"></el-table-column>
                <el-table-column prop="acCount" label="AC比赛场数" align="center"></el-table-column>
                <el-table-column prop="cfAcNumberSum" label="Cf解题数" align="center" sortable></el-table-column>
                <el-table-column prop="cfScNumberSum" label="Cf补题数" align="center" sortable></el-table-column>
                <el-table-column prop="atAcNumberSum" label="AC解题数" align="center" sortable></el-table-column>
                <el-table-column prop="atScNumberSum" label="AC补题数" align="center" sortable></el-table-column>
            </el-table>
            <div class="block">
                <el-pagination layout="total, prev, pager, next" :total="totalNum" :page-size="pageSize"
                    :current-page="currentPage" @current-change="handleCurrentChange" background></el-pagination>
            </div>
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
        rowStyle() {
            return "text-align:center";
        },
        linkTo() {
            let activeIndex = this.$refs.carousel.activeIndex
            window.open(this.imgs[activeIndex].link, "_blank")
        },
        getInfo() {
            axios.get('/stu/info/acmer/student/all/1/100').then(res => {
                if (res.data.code === 200) {
                    this.loading = false
                    const msgInfo = res.data.data.records
                    this.tableData = []
                    for (const item in msgInfo) {
                        this.tableData.push({
                            stuNo: msgInfo[item].stuNo,
                            stuName: msgInfo[item].stuName,
                            stuClass: msgInfo[item].stuClass,
                            stuAcId: msgInfo[item].stuAcId,
                            stuCfId: msgInfo[item].stuCfId,
                            cfNewRating: msgInfo[item].cfNewRating,
                            acNewRating: msgInfo[item].acNewRating,
                            cfSumContest: msgInfo[item].cfSumContest,
                            acCount: msgInfo[item].acCount,
                            cfAcNumberSum: msgInfo[item].cfAcNumberSum,
                            cfScNumberSum: msgInfo[item].cfScNumberSum,
                            atAcNumberSum: msgInfo[item].atAcNumberSum,
                            atScNumberSum: msgInfo[item].atScNumberSum,
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

<style>
.carouselblock {
    background-color: #243946;
}

.el-carousel {
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
</style>