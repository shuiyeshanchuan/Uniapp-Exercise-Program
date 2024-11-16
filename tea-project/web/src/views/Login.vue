<template>
    <div class="container">
        <div class="circle circle-1"></div>
        <div class="circle circle-2"></div>
        <div class="login-box">
            <h2 class="login-title">Login Here</h2>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0" class="login-form">
                <div class="form-label">Username</div>
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="Email or Phone" prefix-icon="el-icon-user"
                        clearable>
                    </el-input>
                </el-form-item>
                <div class="form-label">Password</div>
                <el-form-item prop="password">
                    <el-input type="password" v-model="ruleForm.password" placeholder="Password"
                        prefix-icon="el-icon-lock" clearable>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">Log In</el-button>
                    <!-- <div class="divider">
                        <span>or continue with</span>
                    </div>
                    <div class="social-login">
                        <el-button class="google-btn">
                            <i class="fab fa-google"></i>
                        </el-button>
                        <el-button class="facebook-btn">
                            <i class="fab fa-facebook-f"></i>
                        </el-button>
                    </div> -->
                    <div class="register-link">
                        Don't have an account?
                        <router-link :to="{ path: 'register' }">Register Now</router-link>
                    </div>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import { AddRoutes } from "@/router";

export default {
    data() {
        return {
            ruleForm: {
                username: "",
                password: ""
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名称', trigger: 'blur' },
                    // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
                ],
            }
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log(this.ruleForm)
                    this.request.post("/sysUser/login", this.ruleForm).then(res => {
                        console.log(res)
                        if (res.code === 200) {
                            this.$message({
                                message: "登录成功，正在跳转",
                                type: "success"
                            })
                            let userInfo = res.data
                            localStorage.setItem("systemUser", JSON.stringify(userInfo))
                            localStorage.setItem("systemRoleMenu", JSON.stringify(userInfo.roleMenu))
                            AddRoutes()
                            //解决跳转首页后页面404问题
                            if (res.data.roleType === "USER") {
                                this.$router.push({ path: "/front" })
                            } else {
                                this.$router.push({ path: "/" })
                            }
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
}
</script>
<style scoped>
.container {
    width: 100%;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(120deg, #1a1a1a, #292929);
    position: relative;
    overflow: hidden;
}

.circle {
    position: absolute;
    border-radius: 50%;
    filter: blur(80px);
}

.circle-1 {
    width: 500px;
    height: 500px;
    background: rgba(0, 102, 255, 0.3);
    top: -10%;
    right: -10%;
}

.circle-2 {
    width: 500px;
    height: 500px;
    background: rgba(255, 107, 0, 0.3);
    bottom: -10%;
    left: -10%;
}

.login-box {
    width: 420px;
    padding: 40px;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(20px);
    border-radius: 24px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.1);
    z-index: 1;
}

.login-title {
    text-align: center;
    margin-bottom: 40px;
    font-size: 32px;
    color: #fff;
    font-weight: 600;
    letter-spacing: 1px;
}

.form-label {
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 8px;
    font-size: 14px;
}

.login-form :deep(.el-input__inner) {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    height: 50px;
    color: #fff;
    border-radius: 12px;
    padding-left: 45px;
    transition: all 0.3s ease;
}

.login-form :deep(.el-input__inner):focus {
    background: rgba(255, 255, 255, 0.1);
    border-color: rgba(255, 255, 255, 0.2);
    box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.05);
}

.login-form :deep(.el-input__prefix) {
    left: 15px;
    color: rgba(255, 255, 255, 0.6);
}

.login-form :deep(.el-button) {
    height: 50px;
    border-radius: 12px;
    font-size: 16px;
    font-weight: 500;
    letter-spacing: 0.5px;
}

.login-form :deep(.el-button--primary) {
    background: linear-gradient(45deg, #0066ff, #0044ff);
    border: none;
    width: 100%;
    margin-top: 20px;
}

.divider {
    display: flex;
    align-items: center;
    text-align: center;
    margin: 25px 0;
    color: rgba(255, 255, 255, 0.5);
}

.divider::before,
.divider::after {
    content: '';
    flex: 1;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.divider span {
    padding: 0 15px;
    font-size: 14px;
}

.social-login {
    display: flex;
    gap: 15px;
    justify-content: center;
    margin: 20px 0;
}

.social-login .el-button {
    flex: 1;
    max-width: 120px;
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    color: #fff;
}

.social-login .google-btn:hover {
    background: #db4437;
    border-color: #db4437;
}

.social-login .facebook-btn:hover {
    background: #4267B2;
    border-color: #4267B2;
}

.register-link {
    text-align: center;
    margin-top: 25px;
    color: rgba(255, 255, 255, 0.7);
    font-size: 14px;
}

.register-link a {
    color: #0066ff;
    text-decoration: none;
    font-weight: 500;
    margin-left: 5px;
}

.register-link a:hover {
    text-decoration: underline;
}

/* 添加输入框placeholder样式 */
.login-form :deep(.el-input__inner::placeholder) {
    color: rgba(255, 255, 255, 0.3);
}

/* 添加表单项间距 */
.login-form :deep(.el-form-item) {
    margin-bottom: 25px;
}
</style>
