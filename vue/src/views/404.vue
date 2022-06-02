<template>
  <div style="overflow: hidden; height: 100vh" class="con">
    <div  class="my">
      <el-button type="primary" round style="width:300px; height: 80px;font-size:30px;" @click="d">返回</el-button>
    </div>
    <div>
      <img src="../assets/v2-7338d8b81ad7faaa238e2ba2781625e7_720w.png" alt="" style="width: 100%; height: 100%">
    </div>
  </div>


</template>

<script>

export default {
  name: "NotFound",

  methods :{
    d (){
      this.$router.push("/login")
    }
  }
}
</script>

<style>


.con {
  position: relative;
}

.my {
  position: absolute;
  right: 680px;
  bottom: 240px;
}
</style>
