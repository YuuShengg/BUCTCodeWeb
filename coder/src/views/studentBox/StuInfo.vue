<template>
  <div class="container">
    <el-card class="StuInfo-card">
      <div slot="header" class="clearfix" style="display: flex;">
        <span>学生基本信息</span>
      </div>
      <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" stripe border
        style="width: 100%" v-loading="loading" :cell-style="rowStyle">
        <el-table-column prop="stuNo" label="学号" align="center"></el-table-column>
        <el-table-column prop="stuName" label="姓名" align="center"></el-table-column>
        <el-table-column prop="stuClass" label="班级" align="center"></el-table-column>
        <el-table-column prop="stuAcId" label="AtCoder ID" align="center"></el-table-column>
        <el-table-column prop="stuCfId" label="Codeforces ID" align="center"></el-table-column>
        <el-table-column label="操作" width="100px" align="center">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination layout="total,prev, pager, next" :total="totalNum" :page-size="pageSize"
          :current-page="currentPage" @current-change="handleCurrentChange" background></el-pagination>
      </div>
    </el-card>

    <el-dialog title="学生信息修改" :visible.sync="dialogFormVisible">
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
  created() {
    this.getInfo()
  },
  methods: {
    rowStyle() {
      return "text-align:center";
    },
    getInfo() {
      axios.get('/stu/info/acmer/student/base/1/100').then(res => {
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
              stuCfId: msgInfo[item].stuCfId
            })
          }
          this.totalNum = this.tableData.length
        }
      })
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleEdit(data) {
      this.dialogFormVisible = true // 打开表单对话框
      this.ruleForm.stuNo = data.stuNo
      this.ruleForm.stuName = data.stuName
      this.ruleForm.stuClass = data.stuClass
      this.ruleForm.stuAcId = data.stuAcId
      this.ruleForm.stuCfId = data.stuCfId
    },
    handleDel(data) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.$axios('/stu/info/acmer/student/remove/' + data.stuNo).then((res) => {
          this.getInfo()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
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
          this.$axios.post('/stu/info/acmer/student/update', data).then(res => {
            if (res.data.code === 200) {
              this.getInfo()
              alert('submit !')
              this.ruleForm = {
                stuNo: '',
                stuName: '',
                stuClass: '',
                stuAcId: '',
                stuCfId: '',
              }
            } else {
              this.getInfo()
              alert(res.data.message);
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
          alert("error submit !");
          this.ruleForm = {
            stuNo: '',
            stuName: '',
            stuClass: '',
            stuAcId: '',
            stuCfId: '',
          }
        }
      });
      this.dialogFormVisible = false; // 关闭表单对话框
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

.StuInfo-card {
  margin-top: 20px;
}
</style>
