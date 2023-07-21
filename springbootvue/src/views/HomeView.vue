<template>
  <div style="padding: 10px">
    <div style="margin: 10px 0" hidden>
      <el-input v-model="search" placeholder="请输入" style="width: 20%" />
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <div style="margin: 10px 0;float:right">
      <el-button type="primary" @click="add">添加</el-button>
    </div>

    <el-table :data="tableData" border style="width: 100%">
<!--      <el-table-column prop="id" label="id" width="180"/>-->
      <el-table-column prop="name" label="姓名" width="180"/>
      <el-table-column prop="sex" :formatter="gssex" label="性别"/>
      <el-table-column prop="level" label="班级"/>
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
        <el-form-item label="姓名">
          <el-input placeholder="请输入学生姓名" v-model="form.name" style="width: 80%" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.sex" :formatter="gssex" placeholder="请选择性别">
            <el-option label="男" value="1" />
            <el-option label="女" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="form.level" placeholder="请选择班级">
            <el-option label="1班" value="1班" />
            <el-option label="2班" value="2班" />
          </el-select>
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
      <el-form :model="form" label-width="120px">
        <el-form-item label="姓名">
          <el-input placeholder="请输入学生姓名" v-model="form.name" style="width: 80%" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.sex" :value="value" value-key="sex"  placeholder="请选择性别">
            <el-option v-for="item in sexoption" :key="item.value" :value="item.value" :label="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="form.level" placeholder="请选择班级">
            <el-option label="1班" value="1班" />
            <el-option label="2班" value="2班" />
          </el-select>
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
// @ is an alias to /src

import request from "@/utils/request";


export default {
  name: 'HomeView',
  components: {},
  data() {
    return {
      form:{},
      dialogVisible:false,
      editdialogVisible:false,
      search:'',
      currentPage:1,
      pageSize:10,
      total:0,
      tableData: [

      ],
      sexoption:[
        {
          label: '女',
          value: 0
        },{
          label: '男',
          value: 1
        }
      ]
    }
  },
  created() {
    this.load()
  }
  ,
  methods:{
    load(){
      request.get("/student",{
       params:{
         pageNum:this.currentPage,
         pageSize: this.pageSize,
         search:this.search
       }
      }).then(res =>{
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    gssex(row,index){
      if(row.sex == 1){
        return '男';
      }else{
        return '女';
      }
    },
    removeCurrent(id){
      request.delete("/student/"+ id).then(res => {
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
        request.put("/student",this.form).then(res => {
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
        request.post("/student",this.form).then(res => {
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
