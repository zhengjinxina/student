<template>
  <div>
    <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
    >
      <el-menu-item index="1" @click="handle">
        <el-icon><icon-menu /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="2" @click="handle2">
        <el-icon><setting /></el-icon>
        <span>成绩管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  methods:{
    handle (){
      this.$router.push('/home');
    },
    handle2 (){
      this.$router.push('/score');
    }
  }
}
</script>

<style scoped>

</style>