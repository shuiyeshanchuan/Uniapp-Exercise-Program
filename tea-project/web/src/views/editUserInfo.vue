<template>
  <div class="login-container">
    <el-form :model="formData" :rules="rulesList" status-icon ref="formData" label-position="left" label-width="100px"
      class="login-page">
      <h3 class="title" style="color: #000">用户信息修改</h3>
      <el-upload class="avatar-uploader" :action="$httpUploadFile" :headers="headers" :show-file-list="false"
        :on-success="uploadImgSuccess">
        <img v-if="formData.img" :src="formData.img" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item prop="username" label="用户名">
        <el-input type="text" v-model="formData.username" auto-complete="off" placeholder="用户名"
          :disabled="true"></el-input>
      </el-form-item>

      <el-form-item prop="email" label="邮箱">
        <el-input type="text" v-model="formData.email" auto-complete="off" placeholder="邮箱"></el-input>
      </el-form-item>

      <el-form-item prop="mobile" label="手机号">
        <el-input type="text" v-model="formData.mobile" auto-complete="off" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item prop="address" label="地址">
        <el-input type="text" v-model="formData.address" auto-complete="off" placeholder="地址"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="formData.gender">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="content" label="个人简介">
        <el-input type="textarea" :rows="3" v-model="formData.content" auto-complete="off"
          placeholder="个人简介"></el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100px;" @click="handleSubmit">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "PersonEdit",
  data() {
    return {
      formData: {},
      systemUser: JSON.parse(localStorage.getItem("systemUser") || "{}"),
      rulesList: {
        username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
      },
      checked: false,
      headers: {
        "aaa": "12343"
      }
    }
  },
  created() {
    const systemUser = JSON.parse(localStorage.getItem("systemUser") || "{}")
    this.formData = { ...systemUser }

    this.getUserInfo().then(res => {
      this.formData = res
    })
  },
  methods: {
    async getUserInfo() {
      return (await this.request.get('/sysUser/getUserInfo')).data
    },

    handleSubmit() {
      this.request.put('/sysUser/update', this.formData).then(res => {
        if (res.code == 200) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.$emit('refreshUser');
          this.getUserInfo().then(res => {
            res.token = JSON.parse(localStorage.getItem("systemUser")).token
            localStorage.setItem("systemUser", JSON.stringify(res))
          })
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      });
    },
    uploadImgSuccess(res) {
      this.$set(this.formData, "img", res.data.url)
    }
  }
};
</script>

<style scoped>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
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
  width: 150px;
  height: 150px;
  line-height: 150px;
  text-align: center;
}

.avatar {
  width: 150px;
  height: 150px;
  display: block;
}

.login-page {
  border-radius: 5px;
  width: 600px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

label.el-checkbox.rememberme {
  margin: 0px 0px 15px;
  text-align: left;
}

.title {
  text-align: center;
  margin-bottom: 35px;
}
</style>
