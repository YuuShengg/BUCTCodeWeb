<template>
  <div class="container clearfix" ref="page">
    <!-- 左侧导航栏开始 -->
    <el-row class="tac">
      <el-col :span="12" class="navBox">
        <el-menu :default-active="defaultActive" class="el-menu-vertical-demo" background-color="#243946"
          text-color="#96afbe" active-text-color="#ffffff">
          <div id="main" style="width: 200px; height: 50px; margin: 10px;"></div>
          <el-menu-item index="1" @click="goHome()">
            <i class="el-icon-s-home"></i>
            <span slot="title">Home</span>
          </el-menu-item>
          <el-menu-item index="5" @click="borrow()">
            <i class="iconfont icon-sousuo2"></i>
            <span slot="title">AtCoder 信息查询</span>
          </el-menu-item>
          <el-menu-item index="4" @click="borrowCoderforces()">
            <i class="iconfont icon-yiyuqi"></i>
            <span slot="title">Codeforces 信息查询</span>
          </el-menu-item>
          <el-menu-item index="2" @click="stuInfo()">
            <i class="iconfont icon-geren3"></i>
            <span slot="title">学生基本信息</span>
          </el-menu-item>
          <el-menu-item index="3" @click="register()">
            <i class="iconfont icon-xuesheng"></i>
            <span slot="title">学生信息注册</span>
          </el-menu-item>
        </el-menu>

      </el-col>
    </el-row>
    <!-- 左侧导航栏结束 -->
    <el-dialog title="学生信息注册" :visible.sync="dialogFormVisible">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px">
        <el-form-item label="学号" prop="stuNo">
          <el-input v-model="ruleForm.stuNo" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="stuName">
          <el-input v-model="ruleForm.stuName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="班级" prop="stuClass">
          <el-input v-model="ruleForm.stuClass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="AtCoder ID" prop="stuAcId">
          <el-input v-model="ruleForm.stuAcId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Codeforces ID" prop="stuCfId">
          <el-input v-model="ruleForm.stuCfId" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitRegistration('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>
    <child ref="StuInfo" />
    <router-view class="right_bottom"></router-view>
  </div>
</template>

<script>
import StuInfo from "./studentBox/StuInfo"
import * as echarts from 'echarts'

export default {
  data() {
    return {
      clientHeight: '',
      defaultActive: '1',

      deadline4: Date.now() + (new Date().setHours(23, 59, 59) - Date.now()),
      stop: true,

      dialogFormVisible: false,
      ruleForm: {
        stuNo: '',
        stuName: '',
        stuClass: '',
        stuAcId: '',
        stuCfId: '',
      },
      rules: {
        stuNo: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          { pattern: /^\d+$/, message: '学号必须为数字', trigger: 'blur' }
        ],
        stuName: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 4, message: '长度在 2 到 4 个字符', trigger: 'blur' }
        ],
        stuClass: [
          { pattern: /^\d+$/, message: '班级必须为数字', trigger: 'blur' }
        ],
        stuAcId: [
          { required: true, message: '请输入您的AtCoder ID', trigger: 'blur' },
        ],
        stuCfId: [
          { required: true, message: '请输入您的Coderforces ID', trigger: 'blur' },
        ]
      }
    }
  },
  async created() {
    if (this.$route.name === 'SelectAll') {
      this.defaultActive = '1'
    }
  },
  mounted() {
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
      graphic: {
        elements: [
          {
            type: 'text',
            left: 'center',
            top: 'center',
            style: {
              text: 'BUCTCODER',
              fontSize: 30,
              fontWeight: 'bold',
              lineDash: [0, 100],
              lineDashOffset: 0,
              fill: 'transparent',
              stroke: '#fff',
              lineWidth: 1
            },
            keyframeAnimation: {
              duration: 3000,
              loop: true,
              keyframes: [
                {
                  percent: 0.7,
                  style: {
                    fill: 'transparent',
                    lineDashOffset: 200,
                    lineDash: [200, 0]
                  }
                },
                {
                  // Stop for a while.
                  percent: 0.8,
                  style: {
                    fill: 'white'
                  }
                },
                {
                  percent: 1,
                  style: {
                    fill: 'white'
                  }
                }
              ]
            }
          }
        ]
      }
    };

    option && myChart.setOption(option);

    // 获取浏览器可视区域高度
    this.clientHeight = `${document.documentElement.clientHeight}`
    const that = this
    window.onresize = function () {
      this.clientHeight = `${document.documentElement.clientHeight}`
      if (that.$refs.page) {
        that.$refs.page.style.height = this.clientHeight + 'px'
      }
    }
  },
  watch: {
    // 如果clientHeight 发生改变，这个函数就会运行
    clientHeight() {
      this.changeFixed(this.clientHeight)
    }
  },
  methods: {
    // 控制浏览器高度
    changeFixed(clientHeight) {
      if (this.$refs.page) {
        this.$refs.page.style.height = clientHeight + 'px'
      }
    },
    goHome() {
      if (this.$route.name !== 'Home') {
        this.$router.replace({ path: '/Home' })
      }
    },
    borrow() {
      if (this.$route.name !== 'SelectAll') {
        this.$router.replace({ path: '/SelectAll' })
      }
    },
    borrowCoderforces() {
      if (this.$route.name !== 'CodeforcesInfo') {
        this.$router.replace({ path: '/CodeforcesInfo' })
      }
    },
    stuInfo() {
      if (this.$route.name !== 'StuInfo') {
        this.$router.replace({ path: '/StuInfo' })
      }
    },
    register() {
      this.dialogFormVisible = true; // 打开表单对话框
    },
    submitRegistration(ruleForm) {
      // 处理表单提交逻辑
      // 可以在这里使用 this.form 对象中的值进行进一步处理或提交到后端
      // 例如：发送 API 请求进行注册

      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          const data = {
            stuNo: this.ruleForm.stuNo,
            stuName: this.ruleForm.stuName,
            stuClass: this.ruleForm.stuClass,
            stuAcId: this.ruleForm.stuAcId,
            stuCfId: this.ruleForm.stuCfId,
          }
          this.$axios.post('/stu/info/acmer/student/insert', data).then(res => {
            if (res.data.code === 200) {
              alert('submit !')
              StuInfo.methods.getInfo()
              this.ruleForm = {
                stuNo: '',
                stuName: '',
                stuClass: '',
                stuAcId: '',
                stuCfId: '',
              }
            } else {
              alert(res.data.message)
              this.ruleForm = {
                stuNo: '',
                stuName: '',
                stuClass: '',
                stuAcId: '',
                stuCfId: '',
              }
              return false;
            }
          })
        }
        else {
          alert("error submit !")
          this.ruleForm = {
            stuNo: '',
            stuName: '',
            stuClass: '',
            stuAcId: '',
            stuCfId: '',
          }
        }
      });
      this.dialogFormVisible = false // 关闭表单对话框
      this.$refs.StuInfo.getInfo()
    },
  }
}
</script>

<style scoped>
.container {
  width: 100%;
  margin: 0 auto;
  background-color: #f3f5f6;
}

.container .tac {
  float: left;
  position: fixed;
}

.container .tac,
.container .tac .navBox,
.container .tac .navBox ul {
  width: 220px;
  height: 100%;
}

.container .tac .navBox h2 {
  margin-left: 20px;
  line-height: 56px;
  color: white;
}

.container .tac .navBox p {
  line-height: 56px;
  color: #96afbe;
  margin-left: 10px;
}

.container .tac .navBox .iconfont {
  margin-right: 5px;
  width: 24px;
  text-align: center;
  font-size: 18px;
  vertical-align: middle;
  font-style: normal;
  font-weight: 400;
  font-variant: normal;
  text-transform: none;
  line-height: 1;
  display: inline-block;
}

.container .right_head {
  width: calc(100% - 220px);
  height: 55px;
  background-color: white;
  float: right;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
  padding: 0px 18px;
  margin-bottom: 18px;
  box-shadow: 0px 2px 5px #dbdedf;
}

.container .right_head .logo {
  width: 40px;
  height: 40px;
}

.container .right_head div {
  color: #a9acae;
}

.container .right_head div img {
  width: 30px;
  height: 30px;
  display: inline-block;
  border-radius: 50%;
  vertical-align: middle;
}

.container .right_head span {
  font-weight: bold;
}

.container .right_head .el-button {
  background-color: #fff;
  color: black;
  font-size: 20px;
  border: 0px;
}

.container .right_head .el-button:hover {
  color: #409eff;
}

.container .right_bottom {
  width: calc(100% - 256px);
  float: right;
  margin-right: 18px;
}

.notice-info .el-tag {
  font-size: 20px;
  width: calc(100% - 10px);
  margin: 5px;
}
</style>
