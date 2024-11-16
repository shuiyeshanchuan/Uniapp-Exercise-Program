<template>
    <div class="container">
        <!-- 背景装饰圆 -->
        <div class="circle circle-1"></div>
        <div class="circle circle-2"></div>

        <div class="register-box">
            <h2 class="register-title">Create Account</h2>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0" class="register-form">
                <div class="form-label">Username</div>
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="Enter your username" prefix-icon="el-icon-user"
                        clearable>
                    </el-input>
                </el-form-item>

                <div class="form-label">Password</div>
                <el-form-item prop="password">
                    <el-input type="password" v-model="ruleForm.password" placeholder="Create a password"
                        prefix-icon="el-icon-lock" clearable>
                    </el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">Sign Up</el-button>


                    <div class="login-link">
                        Already have an account?
                        <router-link :to="{ path: 'login' }">Login Here</router-link>
                    </div>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
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
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
            }
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.request.post("/sysUser/register", this.ruleForm).then(res => {
                        if (res.code === 200) {
                            this.$message({
                                message: "注册成功，正在跳转",
                                type: "success"
                            })
                            setTimeout(() => {
                                this.$router.push({ path: "login" })
                            }, 1000)
                        }
                    })
                } else {
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
    background: rgba(255, 0, 64, 0.3);
    top: -10%;
    left: -10%;
}

.circle-2 {
    width: 500px;
    height: 500px;
    background: rgba(179, 0, 255, 0.3);
    bottom: -10%;
    right: -10%;
}

.register-box {
    width: 420px;
    padding: 40px;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(20px);
    border-radius: 24px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.1);
    z-index: 1;
}

.register-title {
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

.register-form :deep(.el-input__inner) {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    height: 50px;
    color: #fff;
    border-radius: 12px;
    padding-left: 45px;
    transition: all 0.3s ease;
}

.register-form :deep(.el-input__inner):focus {
    background: rgba(255, 255, 255, 0.1);
    border-color: rgba(255, 255, 255, 0.2);
    box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.05);
}

.register-form :deep(.el-input__prefix) {
    left: 15px;
    color: rgba(255, 255, 255, 0.6);
}

.register-form :deep(.el-button) {
    height: 50px;
    border-radius: 12px;
    font-size: 16px;
    font-weight: 500;
    letter-spacing: 0.5px;
}

.register-form :deep(.el-button--primary) {
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

.login-link {
    text-align: center;
    margin-top: 25px;
    color: rgba(255, 255, 255, 0.7);
    font-size: 14px;
}

.login-link a {
    color: #0066ff;
    text-decoration: none;
    font-weight: 500;
    margin-left: 5px;
}

.login-link a:hover {
    text-decoration: underline;
}

/* 添加输入框placeholder样式 */
.register-form :deep(.el-input__inner::placeholder) {
    color: rgba(255, 255, 255, 0.3);
}

/* 添加表单项间距 */
.register-form :deep(.el-form-item) {
    margin-bottom: 25px;
}
</style>
