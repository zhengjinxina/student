<template>
  <div style="padding: 10px">
    <div style="margin: 10px 0" >
      <el-select v-model="search" placeholder="请选择科目" style="width: 20%" >
        <el-option label="语文" value="语文" />
        <el-option label="数学" value="数学" />
        <el-option label="英语" value="英语" />
        <el-option label="物理" value="物理" />
        <el-option label="化学" value="化学" />
        <el-option label="生物" value="生物" />
        <el-option label="地理" value="地理" />
        <el-option label="政治" value="政治" />
        <el-option label="历史" value="历史" />
        <el-option label="思想品德" value="思想品德" />
        <el-option label="音乐" value="音乐" />
        <el-option label="美术" value="美术" />
        <el-option label="体育" value="体育" />
        <el-option label="信息技术" value="信息技术" />
      </el-select>
      <el-select v-model="studentName" placeholder="请选择学生" style="width: 20%" >
        <el-option v-for="item in namelist" :key="item.value" :value="item.value" :label="item.label"></el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      <el-button type="primary" style="float: right " @click="add">添加</el-button>
    </div>



    <el-table :data="tableData" border style="width: 100%">
      <!--      <el-table-column prop="id" label="id" width="180"/>-->
      <el-table-column prop="studentName" label="姓名" width="180"/>
      <el-table-column prop="courseName" label="科目"/>
      <el-table-column prop="score" label="成绩"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column prop="updateTime" label="修改时间"/>
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>

          <el-popconfirm title="确认删除吗?" @confirm="removeCurrent(scope.row.id)">
            <template #reference>
              <el-button type="text" >删除</el-button>
            </template>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
    <div style="margin:10px 0">
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

      <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="科目">
            <el-select v-model="form.courseName" placeholder="请选择科目">
              <el-option label="语文" value="语文" />
              <el-option label="数学" value="数学" />
              <el-option label="英语" value="英语" />
              <el-option label="物理" value="物理" />
              <el-option label="化学" value="化学" />
              <el-option label="生物" value="生物" />
              <el-option label="地理" value="地理" />
              <el-option label="政治" value="政治" />
              <el-option label="历史" value="历史" />
              <el-option label="思想品德" value="思想品德" />
              <el-option label="音乐" value="音乐" />
              <el-option label="美术" value="美术" />
              <el-option label="体育" value="体育" />
              <el-option label="信息技术" value="信息技术" />
            </el-select>
          </el-form-item>
          <el-form-item label="姓名">
            <el-select v-model="form.studentName" placeholder="请选择学生">
              <el-option v-for="item in namelist" :key="item.value" :value="item.value" :label="item.label"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="成绩">
            <el-input type="number" placeholder="请输入成绩" v-model="form.score" style="width: 80%" />
          </el-form-item>
        </el-form>

        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确定
        </el-button>
      </span>
        </template>
      </el-dialog>

      <el-dialog v-model="editdialogVisible" title="Tips" width="30%">
        <el-form :model="form" label-width="120px" >
          <el-form-item label="科目">
            <el-select v-model="form.courseName" placeholder="请选择科目">
              <el-option label="语文" value="语文" />
              <el-option label="数学" value="数学" />
              <el-option label="英语" value="英语" />
              <el-option label="物理" value="物理" />
              <el-option label="化学" value="化学" />
              <el-option label="生物" value="生物" />
              <el-option label="地理" value="地理" />
              <el-option label="政治" value="政治" />
              <el-option label="历史" value="历史" />
              <el-option label="思想品德" value="思想品德" />
              <el-option label="音乐" value="音乐" />
              <el-option label="美术" value="美术" />
              <el-option label="体育" value="体育" />
              <el-option label="信息技术" value="信息技术" />
            </el-select>
          </el-form-item>
          <el-form-item label="姓名">
            <el-select v-model="form.studentName" placeholder="请选择学生">
              <el-option v-for="item in namelist" :key="item.value" :value="item.value" :label="item.label"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="成绩">
            <el-input type="number" placeholder="请输入成绩" v-model="form.score" style="width: 80%" />
          </el-form-item>
        </el-form>

        <template #footer>
      <span class="dialog-footer">
        <el-button @click="editdialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确定
        </el-button>
      </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>


<script>
import request from "@/utils/request";


export default {
  name: 'ScoreView',
  components: {},
  data() {
    return {
      form:{},
      dialogVisible:false,
      editdialogVisible:false,
      search:'',
      studentName:'',
      currentPage:1,
      pageSize:10,
      total:0,
      tableData: [

      ],
      namelist:[
        {
          value: '', label: '',
        }
      ]
    }
  },
  created() {
    this.load();
    this.getStudentNameList();
  }
  ,
  methods:{
    load(){
      request.get("/score",{
        params:{
          pageNum:this.currentPage,
          pageSize: this.pageSize,
          search:this.search,
          studentName:this.studentName
        }
      }).then(res =>{
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    getStudentNameList(){
      request.get("/student/studentList").then(res => {
        console.log(res.data.length)
        for (let a=0;a<res.data.length;a++){
          this.namelist.push({
            text: res.data[a].name,
            value: res.data[a].name,
          })
        }
        this.namelist.shift();
      });
    },
    removeCurrent(id){
      request.delete("/score/"+ id).then(res => {
        if (res.code == "0"){
          this.dialogVisible=false
          this.$message({
            type:"success",
            message:"删除成功"
          })
        }else{
          this.$message({
            type:"fail",
            message:"删除失败"
          })
        }
        this.load() //刷新表格数据
      })
    },
    add(){
      this.dialogVisible=true
      this.form = {}
    },
    save(){
      if (this.form.id){ //修改
        request.put("/score",this.form).then(res => {
          if (res.code == "0"){
            this.editdialogVisible=false
            this.$message({
              type:"success",
              message:"修改成功"
            })
          }else{
            this.$message({
              type:"fail",
              message:"修改失败"
            })
          }
          this.load() //刷新表格数据
        })
      }else { //新增
        request.post("/score",this.form).then(res => {
          if (res.code == "0"){
            this.dialogVisible=false
            this.$message({
              type:"success",
              message:"新增成功"
            })
          }else{
            this.dialogVisible=false
            this.$message({
              type:"fail",
              message:"新增失败"
            })
          }
          this.load() //刷新表格数据
        })
      }

    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.editdialogVisible = true
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage = pageNum;
      this.load()
    },
  }
}
</script>
