<template>
  <div class="main">
    <el-container>
      <el-header style="padding: 0">
        <div class="container-fluid" style="box-shadow: 5px 0 5px #ccc">
          <div class="container">
            <el-row class="nav">
              <el-col :span="4" class="logo">
                <div>
                  <router-link to="/front/index" tag="div">
                    <img src="@/assets/logo.jpg" alt="">
                  </router-link>
                </div>
              </el-col>
              <el-col :span="16">
                <div>
                  <el-menu
                      :default-active="$route.path"
                      class="el-menu-demo"
                      mode="horizontal"
                      router
                      @select="handleSelect"
                  >
                    <el-menu-item index="/front/index">首页</el-menu-item>
                    <el-menu-item index="/front/about">关于我们</el-menu-item>
                    <template v-if="!isUserLogin">
                      <el-menu-item index="/login">登录</el-menu-item>
                      <el-menu-item index="/register">去注册</el-menu-item>
                    </template>
                  </el-menu>
                </div>
              </el-col>
              <el-col :span="4" class="control">
                <el-avatar style="width: 30px;height: 30px;position: relative;top: 15px;right: 10px;border-radius: 50%"
                           shape="square" :size="30" :src="userInfo.img"></el-avatar>
                <el-dropdown v-if="isUserLogin" class="el-dropdown-font">
                                    <span class="el-dropdown-link">{{ userInfo.username }}<i
                                        class="el-icon-arrow-down el-icon--right"></i></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item><span @click="editUserInfo">个人信息</span></el-dropdown-item>
                    <el-dropdown-item><span @click="editPassword">修改密码</span></el-dropdown-item>
                    <el-dropdown-item divided><span @click="logout">退出登录</span>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-header>
      <div class="container">
        <el-main>
          <router-view/>
        </el-main>
      </div>
      <div class="container-fluid">
        <el-footer class="footer">
          <p>版权所有，翻版必究</p>
<!--          <p>©2024 B站：程序员瑞哥 </p>-->
        </el-footer>
      </div>
    </el-container>
  </div>
</template>

<script>
import tools from "@/utils/utils";

export default {
  data() {
    return {
      isUserLogin: tools.isUserLogin(),
      userInfo: tools.getUserInfo()
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    logout() {
      this.$message({
        message: "退出登录成功，正在跳转",
        type: "success"
      })
      localStorage.removeItem("systemUser")
      localStorage.removeItem("systemRoleMenu")

      setTimeout(() => {
        this.$router.push({path: "/login"})
      }, 1000)
    },
    editUserInfo() {
      this.$router.push({path: "/front/editUserInfo"})
    },
    editPassword() {
      this.$router.push({path: "/front/editPassword"})
    },
  }
}
</script>


<style scoped>
.container {
  width: 1200px;
  margin: 0 auto;
  /*background: pink;*/

}

.container-fluid {
  width: 100%;
  /*background: #909399;*/
}

.el-menu.el-menu--horizontal {
  border-bottom: none;
}

.el-menu, .el-header {
  background: #fff;
  /*background: #909399;*/
}

.el-footer {
  padding: 10px 0;
  height: auto !important;
}

.el-main {
  /*padding: 20px;*/
  /*box-shadow: 0 0 3px #ccc;*/
  /*margin: 30px 0 110px;*/
}

.main {
  position: relative;
  min-height: 100%;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-icon-arrow-down {
  font-size: 12px;
}


</style>

<style scoped>
.nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.control {
  display: flex;
  justify-content: right;
}

.logo img {
  display: flex;
  align-items: center;
  width: 100px;
  cursor: pointer;
}

.footer {
  text-align: center;
  color: #fff;
  background: #409EFF;
  position: absolute;
  bottom: 0;
  width: 100%;
}

.footer p {
  line-height: 30px;
  font-size: 14px;

}
</style>
