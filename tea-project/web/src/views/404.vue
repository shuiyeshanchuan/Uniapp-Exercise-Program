<template>
    <div>
        <div class="container">
            <h3>很抱歉,当前页面未被找到</h3>
            <p>请试试以下方法：</p>
            <p><span>1、</span>检查网络是否正确链接</p>
            <p><span>2、</span>检查访问路径是否存在</p>
            <p>
                <span>3、</span>页面将在<span class="autotime">{{ autoTime }}</span>
                <span class="go_back">s</span>后自动跳转到 <span  @click="toUrl">系统 </span>首页
            </p>
        </div>
    </div>
</template>
<script>
import utils from "@/utils/utils";

export default {

    data() {
        return {
            autoTime: 10,
            timer: null,
        }
    },
    created() {
        this.timer = setInterval(() => {
            console.log("调用")
            --this.autoTime
            if (this.autoTime <= 0) {
                clearTimeout(this.timer)
                console.log("定时器停止")
                //判断普通用户
                utils.getUserInfo().roleType==='USER'?this.$router.push("/front/index"):this.$router.push("/")
            }

        }, 1000)
    },
    methods:{
        toUrl(){
            utils.getUserInfo().roleType==='USER'?this.$router.push("/front/index"):this.$router.push("/")
        }
    }

}
</script>


<style type="text/css" scoped>

.container {
    width: 480px;
    height: 500px;
    margin: 40px auto;
}

.container h3 {
    font-size: 24px;
    line-height: 55px;
}

.container p {
    font-size: 13px;
    color: #666;
    line-height: 25px;
}

.container p font {
    color: #FF00CC;
    font-weight: bold;
}

.container span {
    color: red;
    cursor: pointer;
}

.container p span {
    color: red;
    font-weight: bold;
    padding-left: 5px;
}

.go_back {
    color: red;
    padding-right: 5px;
}
</style>
