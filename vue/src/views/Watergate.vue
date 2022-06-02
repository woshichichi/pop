<template>
  <div>
    <h1 class="b" style="height: 200px;width: 1650px;margin-right: 50px" >

    </h1>
<!--    <div style="line-height: 60px; display: flex">-->
      <div style="display: inline-block;  line-height: 30px ;width: 100%">
        <span style="font-size: 20px ; text-align: left">{{"欢迎回来" + user.nickname }}</span>
        <span style="font-size: 20px ;  cursor: pointer; float: right" @click="go" >退出系统</span>

      </div>
<!--      <div style="display: inline-block; text-align: right ; float:right;">-->
<!--      </div>-->
<!--    </div>-->


    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>

      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="oi">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

    </div>

    <el-table :data="tableData" :cell-style="{'text-align':'center'}"
              :header-cell-style="{'text-align':'center'}" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="name" label="水闸名"></el-table-column>
      <el-table-column prop="bianhao" label="id"></el-table-column>
      <el-table-column prop="date" label="建成时间"></el-table-column>
      <el-table-column prop="fuze" label="负责人"></el-table-column>
<!--      <el-table-column prop="jianjie" label="简介"></el-table-column>-->
<!--      <el-table-column prop="jingdu" label="经度"></el-table-column>-->
<!--      <el-table-column prop="weidu" label="纬度"></el-table-column>-->


      <el-table-column label="操作"  width="180" align="center">
        <template slot-scope="scope">
<!--          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>-->
          <el-button type="success" @click="pp(scope.row)">修改<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

<!--    <el-dialog title="信息"  :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">-->
<!--      <el-form  :model="form" label-width="120px" :rules="rules" ref="waterForm" size="small" style="width: 80%; margin: 0 auto">-->
<!--        <el-form-item label="名字" prop="name">-->
<!--          <el-input v-model="form.name"   autocomplete="off" ></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="日期" prop="date">-->
<!--          <el-date-picker v-model="form.date" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="负责人" prop="fuze">-->
<!--          <el-input v-model="form.fuze" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="简介" prop="jianjie">-->
<!--          <el-input v-model="form.jianjie" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="经度" prop="jingdu">-->
<!--          <el-input v-model="form.jingdu" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="纬度" prop="weidu">-->
<!--          <el-input v-model="form.weidu" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->

<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button @click="dialogFormVisible = false">取 消</el-button>-->
<!--        <el-button type="primary" @click="save">确 定</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
export default {
  name: "Watergate",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      isInput: "true",

      rules: {
        name: [
          {required: true, message: '请输入活动名称', trigger: 'blur'}
        ],
        date: [
          {required: true, message: '请输入活动名称', trigger: 'blur'}
        ],
        fuze: [
          {required: true, message: '请输入活动名称', trigger: 'blur'}
        ],
        jianjie: [
          {required: true, message: '请输入活动名称', trigger: 'blur'}
        ],
        jingdu: [
          {required: true, message: '请输入活动名称', trigger: 'blur'}
        ],
        weidu: [
          {required: true, message: '请输入活动名称', trigger: 'blur'}
        ],
        bianhao: [
          {required: true, message: '请输入活动名称', trigger: 'blur'}
        ],



      },
      dialogFormVisible: false,
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.load()
    // this.form = JSON.parse(localStorage.getItem('forme'))
  },
  methods: {
    load() {
      this.request.get("/watergate/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    oi(){
      this.$router.push('/wateradd')
    },
    go() {
      this.$router.push('/login')
    },
    pp(row){
      var a = JSON.stringify(row)
      localStorage.setItem('forme', a)
      this.$router.push('/wateredit')
    },
    // save() {
    //
    //   this.$refs["waterForm"].validate((valid) => {
    //       if (valid) {
    //         if(this.edit){
    //           this.request.post("/watergate", this.form).then(res => {
    //             if (res.code === '200') {
    //               this.$message.success("保存成功")
    //               this.dialogFormVisible = false
    //               this.load()
    //             } else {
    //               this.$message.error("保存失败")
    //             }
    //           })
    //         }else {
    //           this.request.post("/watergate/register", this.form).then(res => {
    //             if (res.code === '200') {
    //               this.request.post("/watergate", this.form).then(res => {
    //                 if (res.code === '200') {
    //                   this.$message.success("保存成功")
    //                   this.dialogFormVisible = false
    //                   this.load()
    //                 } else {
    //                   this.$message.error("保存失败")
    //                 }
    //               })
    //             } else {
    //               this.$message.error(res.msg)
    //             }
    //           })
    //         }
    //
    //       } else {
    //         return false;
    //       }
    //     });
    //
    //
    // },

    register() {
      this.$refs["waterForm"].validate((valid) => {
        if (valid) {
          this.request.post("/watergate/register", this.form).then(res => {
            if (res.code === '200') {
              this.request.post("/watergate", this.form).then(res => {
                if (res.code === '200') {
                  this.$message.success("保存成功")
                  this.dialogFormVisible = false
                  this.load()
                } else {
                  this.$message.error("保存失败")
                }
              })
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });


    },


    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
      // this.edit = false
      this.$nextTick(() => {
        if(this.$refs.img) {
           this.$refs.img.clearFiles();
         }
         if(this.$refs.file) {
          this.$refs.file.clearFiles();
         }
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
      // this.edit = true
       this.$nextTick(() => {
         if(this.$refs.img) {
           this.$refs.img.clearFiles();
         }
         if(this.$refs.file) {
          this.$refs.file.clearFiles();
         }
       })
    },
    del(id) {
      this.request.delete("/watergate/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      if (!this.multipleSelection.length) {
        this.$message.error("请选择需要删除的数据")
        return
      }
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/watergate/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleFileUploadSuccess(res) {
      this.form.file = res
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
    download(url) {
      window.open(url)
    },
    exp() {
      window.open("http://localhost:9090/watergate/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}

.b {
  background-image: url('~@/assets/anastasia-taioglou-CTivHyiTbFw-unsplash.jpg');
  background-size: cover;
  background-position: center;
}
</style>
