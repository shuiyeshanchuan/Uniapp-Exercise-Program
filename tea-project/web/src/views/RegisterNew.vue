<template>
  <div class="main-context">
    <el-card class="box-card">
      <H2>XXX管理系统</H2>
      <br>
      <el-form :model="fromData" label-width="0px" :rules="rules" ref="ruleForm">
        <el-form-item label="" prop="username">
          <el-input
              prefix-icon="el-icon-user"
              placeholder="请输入账号"
              v-model="fromData.username"
              clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="" prop="password">
          <el-input
              prefix-icon="el-icon-unlock"
              placeholder="请输入密码"
              show-password
              v-model="fromData.password"
              clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-button @click="submitForm('ruleForm')" type="success" style="width: 100%">注 册</el-button>
          <br>
          <router-link tag="span" :to="{path:'login'}">
            <el-button type="text" class="button" style="float: right">已有账号？去登录</el-button>
          </router-link>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>

export default {
  data() {
    return {
      fromData: {
        username: "",
        password: ""
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名称', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.request.post("/sysUser/register", this.fromData).then(res => {
            console.log(res)
            if (res.code === 200) {
              this.$message({
                message: "注册成功，正在跳转",
                type: "success"
              })
              setTimeout(() => {
                this.$router.push({path: "login"})
              }, 1000)
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
};
</script>
<style scoped>
.main-context {
  background: url("../assets/login-new.png") 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%; /* 或者指定一个具体的高度 */
}

.box-card {
  width: 300px;
  margin: 0 auto;
  text-align: center;
}
</style>