<template>
  <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #ccc;display: flex">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: black">
      学生管理系统
    </div>
    <div style="flex: 1">
    </div>
    <div style="width: 110px">
      <el-dropdown>
    <span class="el-dropdown-link" >
      {{username}}
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="handle">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { ArrowDown } from '@element-plus/icons-vue';
import Cookies from "js-cookie";

export default {
  name: "Header",
  components: {ArrowDown},
  data(){
    return{
      username: Cookies.get("username")
    }
  },
  methods:{
    handle (){
      this.$router.push('/login');
    },

  }
}
</script>



