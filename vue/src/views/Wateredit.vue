<template>
  <div class="a">
    <el-card style="width: 500px; margin: 100px auto">
      <el-form  :model="form" :rules="rules" label-width="120px"  ref="waterForm" size="small" style="width: 80%; margin: 0 auto">
        <el-form-item label="编号" prop="bianhao">
          <el-input v-model="form.bianhao" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="水闸名" prop="name">
          <el-input v-model="form.name"   autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="日期" prop="date">
          <el-date-picker v-model="form.date" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="负责人" prop="fuze">
          <el-input v-model="form.fuze" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="经度" prop="jingdu">
          <el-input v-model="form.jingdu" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="纬度" prop="weidu">
          <el-input v-model="form.weidu" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="jianjie">
          <el-input v-model="form.jianjie" type="textarea" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="save">确 定</el-button>
          <el-button @click="no">取 消</el-button>
        </el-form-item>

      </el-form>

    </el-card>
  </div>

</template>

<script>
export default {
  name: "Wateredit",
  data() {
    return {
      form: {},
      rules: {
        name: [
          {required: true, message: '请输入水闸名', trigger: 'blur'}
        ],
        date: [
          {required: true, message: '请输入建成日期', trigger: 'blur'}
        ],
        fuze: [
          {required: true, message: '请输入负责人', trigger: 'blur'}
        ],
        jianjie: [
          {required: true, message: '请输入简介', trigger: 'blur'}
        ],
        jingdu: [
          {required: true, message: '请输入经度', trigger: 'blur'}
        ],
        weidu: [
          {required: true, message: '请输入纬度', trigger: 'blur'}
        ],
        bianhao: [
          {required: true, message: '请输入编号', trigger: 'blur'}
        ],
      }
    }
  },
  created() {

    this.form = JSON.parse(localStorage.getItem('forme'))
  },
  methods: {

    save() {
      this.$refs["waterForm"].validate((valid) => {
        if (valid) {
          this.request.post("/watergate", this.form).then(res => {
              if (res.code === '200') {
                this.$message.success("保存成功")
                this.$router.push("/")

              } else {
                this.$message.error("保存失败")
              }
            })
          }
          })

        } ,
    no(){
      this.$router.push("/")
    }


  }
}
</script>

<style>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}


/*.a {*/

/*  height: 100vh;*/
/*  !*background-image: linear-gradient(to bottom right, #4169E1 , 	#87CEFA);*!*/
/*  background: url("~@/assets/wes-hicks-lY2ayo1i4pM-unsplash.jpg");*/
/*  background-size: cover;*/
/*  overflow: hidden;*/
/*}*/
</style>
