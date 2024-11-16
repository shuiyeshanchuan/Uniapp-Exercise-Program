<template>
    <el-container class="admin-container">
        <el-aside width="220px" class="admin-aside">
            <h3 class="title">有奶没牛/有牛没奶</h3>
            <el-menu :default-openeds="['user']" router class="admin-menu" :default-active="$route.path"
                background-color="#000000" text-color="#F0F8FF" active-text-color="#ff2e68">
                <el-menu-item v-for="(item, i) in roleMenu" v-show="!item.isPage" :key="i" :index="item.path"><i
                        :class="item.iconClass"></i><span slot="title">{{ item.title }}</span></el-menu-item>
                <el-submenu index="user" v-for="item in roleMenu" v-show="item.isPage" :key="item.name">
                    <template slot="title">
                        <i class="el-icon-s-home"></i><span>{{ item.title }}</span>
                    </template>
                    <el-menu-item v-for="(current, index) in item.list" :key="index" :index="current.path"> <i
                            :class="current.iconClass"></i><span slot="title">{{ current.title }}</span></el-menu-item>
                </el-submenu>
            </el-menu>
        </el-aside>
        <el-container>
            <el-header class="admin-header">
                <div class="meteor-container">
                    <div class="meteor" v-for="n in 12" :key="n"></div>
                </div>
                <div class="header-right">
                    <el-avatar class="user-avatar" :size="32" :src="userInfo.img"></el-avatar>
                    <el-dropdown class="user-dropdown">
                        <span class="el-dropdown-link">
                            <span class="username">{{ userInfo.username }}</span>
                            <i class="el-icon-arrow-down el-icon--right"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item><span @click="editUserInfo">个人信息</span></el-dropdown-item>
                            <el-dropdown-item><span @click="editPassword">修改密码</span></el-dropdown-item>
                            <el-dropdown-item divided><span @click="logout">退出登录</span></el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-header>
            <el-main class="admin-main">
                <el-card class="main-card">
                    <router-view />
                </el-card>
            </el-main>
        </el-container>
    </el-container>
</template>
<script>
import tools from "@/utils/utils";

export default {
    data() {
        return {
            userInfo: tools.getUserInfo(),
            roleMenu: []
        }
    },
    created() {
    },
    mounted() {
        this.roleMenu = tools.getRoleMenu()
    },
    methods: {
        logout() {
            this.$message({
                message: "退出登录成功，正在跳转",
                type: "success"
            })
            localStorage.removeItem("systemUser")
            localStorage.removeItem("systemRoleMenu")
            setTimeout(() => {
                this.$router.push({ path: "/login" })
            }, 1000)
        },
        editUserInfo() {
            this.$router.push({ path: "/editUserInfo" })
        },
        editPassword() {
            this.$router.push({ path: "/editPassword" })
        },
    }
};
</script>

<style>
/* 容器相关样式 */
.admin-container {
    height: 100%;
}

.admin-aside {
    background-color: #000000;
    overflow-x: hidden;
}

.admin-header {
    position: relative;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    background-color: #0a0a0a;
    height: 60px;
    padding: 0 20px;
    overflow: hidden;
}

.admin-main {
    background-color: #f0f2f5;
    padding: 20px;
}

/* 标题样式 */
.title {
    text-align: center;
    color: #fff;
    height: 60px;
    line-height: 60px;
    font-size: 18px;
    padding: 0 16px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

/* 流星容器 */
.meteor-container {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    pointer-events: none;
}

/* 流星样式 */
.meteor {
    position: absolute;
    height: 3px;
    background: linear-gradient(to right,
            rgba(255, 255, 255, 1) 0%,
            rgba(255, 255, 255, 0.8) 20%,
            rgba(255, 255, 255, 0.2) 100%);
    animation: meteor linear infinite;
    border-radius: 3px;
    box-shadow: 0 0 10px rgba(255, 255, 255, 0.8);
}

/* 调整每个流星的大小、位置和时间 */
.meteor:nth-child(1) {
    top: 10%;
    left: 10%;
    width: 150px;
    animation-duration: 2s;
    animation-delay: 0s;
}

.meteor:nth-child(2) {
    top: 15%;
    left: 20%;
    width: 100px;
    animation-duration: 3s;
    animation-delay: 0.2s;
}

.meteor:nth-child(3) {
    top: 20%;
    left: 30%;
    width: 120px;
    animation-duration: 2.5s;
    animation-delay: 0.4s;
}

.meteor:nth-child(4) {
    top: 25%;
    left: 40%;
    width: 180px;
    animation-duration: 3s;
    animation-delay: 0.6s;
}

.meteor:nth-child(5) {
    top: 30%;
    left: 50%;
    width: 130px;
    animation-duration: 2.8s;
    animation-delay: 0.8s;
}

.meteor:nth-child(6) {
    top: 35%;
    left: 60%;
    width: 140px;
    animation-duration: 2.2s;
    animation-delay: 1s;
}

.meteor:nth-child(7) {
    top: 40%;
    left: 70%;
    width: 160px;
    animation-duration: 2.4s;
    animation-delay: 1.2s;
}

.meteor:nth-child(8) {
    top: 45%;
    left: 80%;
    width: 120px;
    animation-duration: 2.6s;
    animation-delay: 1.4s;
}

.meteor:nth-child(9) {
    top: 50%;
    left: 85%;
    width: 140px;
    animation-duration: 2.8s;
    animation-delay: 1.6s;
}

.meteor:nth-child(10) {
    top: 55%;
    left: 15%;
    width: 160px;
    animation-duration: 3s;
    animation-delay: 1.8s;
}

.meteor:nth-child(11) {
    top: 60%;
    left: 35%;
    width: 170px;
    animation-duration: 2.7s;
    animation-delay: 2s;
}

.meteor:nth-child(12) {
    top: 65%;
    left: 55%;
    width: 180px;
    animation-duration: 2.3s;
    animation-delay: 2.2s;
}

@keyframes meteor {
    0% {
        transform: rotate(45deg) translateX(0);
        opacity: 1;
    }

    50% {
        opacity: 1;
    }

    75% {
        opacity: 0.8;
    }

    100% {
        transform: rotate(45deg) translateX(2000px);
        opacity: 0;
    }
}

/* 用户信息区域样式 */
.header-right {
    position: relative;
    z-index: 2;
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0;
    margin: 0;
}

.user-avatar {
    margin-right: 8px;
    cursor: pointer;
}

.user-dropdown {
    height: 100%;
    display: flex;
    align-items: center;
}

.el-dropdown-link {
    color: #fff;
    cursor: pointer;
    display: flex;
    align-items: center;
    height: 100%;
}

.username {
    margin-right: 4px;
}

.el-icon--right {
    margin-left: 4px;
    color: #fff;
}
</style>
