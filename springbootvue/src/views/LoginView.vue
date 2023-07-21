<template>
<div style="width: 100%;height: 100vh;overflow: hidden">
  <div style="width: 400px;margin: 150px auto">
    <div style="font-size: 30px;text-align: center;padding: 30px">欢迎登陆</div>
    <el-form :model="form"  size="normal">
      <el-form-item >
        <el-input v-model="form.username" placeholder="用户名"  />
      </el-form-item>
      <el-form-item >
        <el-input v-model="form.password" show-password placeholder="密码" />
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="primary" @click="login">登陆</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
import request from "@/utils/request";
import jsCookie from 'js-cookie'

export default {
  name: "LoginView",
  data(){
   return{
      form:{}
    }
  },
  methods:{
    login(){
      request.post("/user/login",this.form).then(res => {
        if (res.code == "0"){
          this.$message({ //用户名：1 ;密码 ：1
            type:"success",
            message:"登陆成功"
          })
          jsCookie.set("username", this.form.username);
          this.$router.push("/home") //登陆成功跳转主页面
        }else{
          this.$message({
            type:"fail",
            message:"登陆失败"
          })
        }

      })
    }
  }
}
</script>

<style scoped>

</style>