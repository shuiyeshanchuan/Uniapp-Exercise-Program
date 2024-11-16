<template>
	<view class="container">
		<image class="top-image" src="/static/damn3.jpg"></image>
		<view class="title">
			茶里有奶
		</view>
		<view class="title2">
			"注册个号，整点薯条"
		</view>
		<input type="text" v-model="userInfo.username" placeholder="name:自己整个能记住的" class="text-align:center">
		<input type="password" v-model="userInfo.password" placeholder="密码忘了别求爷爷告奶奶">

		<!-- 按钮容器 -->
		<view class="button-container">
			<!-- 注册按钮 -->
			<button class="btn register-btn" @click="register">注册一下</button>
			<!-- 返回登录按钮 -->
			<button class="btn login-btn" @click="toLoginPage">返回登录</button>
		</view>
		<h4 class="centered-text">The final right to interpret belongs to Sandlot Garden Kindergarten.</h4>
	</view>
</template>

<script>
import {
	register
} from "@/api/index";

export default {
	data() {
		return {
			userInfo: {
				username: "",
				password: ""
			}

		}
	},
	methods: {
		toLoginPage() {
			uni.navigateTo({
				url: "/pages/login/login",
				animationType: "slide-in-right",
				animationDuration: 300
			})
		},
		register() {


			register(this.userInfo).then(res => {
				uni.showToast({
					title: res.msg,
					duration: 2000,
				});

				//登录成功需要做的操作 存储数据（本地存储） 跳转页面
				//setTimeout 只执行一次 2秒后进行跳转
				setTimeout(() => {
					uni.navigateTo({
						url: "/pages/login/login"
					})
				}, 2000)


				// uni.showToast({
				// 	title: res.data.msg,
				// 	duration: 2000,
				// 	icon: "error"
				// });
			})

			// uni.request({
			// 	//请求路径
			// 	url: "http://47.96.80.123:8000/user/registerUtell",
			// 	//请求方式
			// 	method: 'POST',
			// 	//请求携带的参数
			// 	data: this.userInfo,
			// 	//请求头 这里自定义了请求头
			// 	header: {
			// 		"Content-Type": "application/x-www-form-urlencoded"
			// 	},
			// 	//回调函数 后端返回的数据
			// 	success: (res) => {
			// 		console.log(res)
			//
			// 		if (res.code === "0") {
			// 			uni.showToast({
			// 				title: res.data.msg,
			// 				duration: 2000,
			// 			});
			//
			// 			//登录成功需要做的操作 存储数据（本地存储） 跳转页面
			// 			//setTimeout 只执行一次 2秒后进行跳转
			// 			setTimeout(() => {
			// 				//跳转普通页面 navigateTo
			// 				uni.navigateTo({
			// 					url: "/pages/login/login"
			// 				})
			//
			// 			}, 2000)
			//
			// 		} else {
			// 			uni.showToast({
			// 				title: res.data.msg,
			// 				duration: 2000,
			// 				icon: "error"
			// 			});
			// 		}
			// 	}
			// })


		},
		toBack() {
			uni.navigateBack()
		}
	}
}
</script>

<style scoped>
.container {
	display: flex;
	flex-direction: column;
	justify-content: flex-end;
	background: linear-gradient(to bottom, #fff6d2, #ffffff);
	height: 100%;
}

.top-image {
	width: 100%;
	height: 70%;
	object-fit: cover;
	margin: 0 auto;
	-webkit-mask-image: linear-gradient(to bottom, rgba(170, 255, 255, 1.0) 0%, rgba(0, 0, 0, 0) 100%);
	mask-image: linear-gradient(to bottom, rgba(0, 0, 0, 1) 0%, rgba(0, 0, 0, 0) 100%);
}

uni-input {
	height: 45px;
	width: 80%;
	font-weight: bold;
	border-radius: 10px;
	padding: 3px 10px;
	margin: 0 auto;
	border: none;
	box-sizing: border-box;
	margin-top: 20px;
	background-color: #fff6d5;
	text-align: center;
}

uni-input::placeholder {
	color: #999;
	transition: color 0.1s ease;
}

uni-input:hover::placeholder {
	color: transparent;
}

/* 添加按钮容器样式 */
.button-container {
	display: flex;
	justify-content: space-between;
	width: 80%;
	margin: 0 auto;
	margin-top: 20px;
}

/* 修改按钮样式 */
button {
	width: 48%;
	/* 调整为略小于50%以留出间距 */
	font-weight: bold;
	border-radius: 30px;
	margin-bottom: 20px;
	/* margin: 0; */
	/* 移除原有的margin */
}

/* 移除原有的.btn类中的margin-top */
.btn {
	/* margin-top: 15px; 删除这行 */
}

.login-btn {
	background: #aec3ce;
	color: #fff6d2;
}

.register-btn {
	background: #cb2749;
	color: #fff6d2;
}

.title {
	font-size: 25px;
	text-align: center;
	margin-top: 25px;
	font-weight: bold;
	color: #b59c81;
}

.title2 {
	font-size: 10px;
	text-align: right;
	margin-right: 5px;
	margin-top: 5px;
	font-weight: bold;
	color: #b59c81;
}

.centered-text {
	display: flex;
	justify-content: center;
	align-items: center;
	text-align: center;
	width: 100%;
	font-size: 12px;
	line-height: 1.4;
	color: #aec3ce;
	margin-bottom: 10px;
}
</style>