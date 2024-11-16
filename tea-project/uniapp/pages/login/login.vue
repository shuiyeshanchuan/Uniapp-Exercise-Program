<template>
	<view class="container">
		<image class="top-image" src="/static/damn.jpg"></image>
		<view class="title">
			茶里有奶
		</view>
		<view class="title2">
			"爱点啥点啥，不会真的给你做的"
		</view>
		<input type="text" v-model="userInfo.username" placeholder="HOW TO 称呼" class="text-align:center">
		<input type="password" v-model="userInfo.password" placeholder="愣着呀.输密码干森莫">




		<!-- 按钮容器，用于将登录和注册按钮放在同一行并居中对称 -->
		<view class="button-container">
			<!-- 登录按钮 -->
			<button class="btn login-btn" @click="userLogin">登录一下</button>
			<!-- 注册按钮 -->
			<button class="btn register-btn" @click="toRegisterPage">没号注册</button>
		</view>
		<h4 class="centered-text">The final right to interpret belongs to Sandlot Garden Kindergarten.</h4>


	</view>
</template>

<script>
import {
	login
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
		toRegisterPage() {
			uni.navigateTo({
				url: "/pages/register/register",
				animationType: "slide-in-left",
				animationDuration: 300
			})
		},
		userLogin() {
			login(this.userInfo).then(res => {
				console.log(res.data)
				uni.showToast({
					title: res.msg,
					duration: 2000,
				});

				//登录成功需要做的操作 存储数据（本地存储） 跳转页面
				//setTimeout 只执行一次 2秒后进行跳转
				setTimeout(() => {
					uni.setStorage({
						key: "token",
						data: res.data.token,
						success: function () {
							console.log('success');
							//跳转到首页 switchTab
							uni.switchTab({
								url: "/pages/index/index"
							})
						}
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
			// 	url: "http://47.96.80.123:8000/user/userLogin",
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
/* 使用 flex 布局将内容推向底部 */
.container {
	display: flex;
	flex-direction: column;
	justify-content: flex-end;
	background: linear-gradient(to bottom, #fff6d2, #ffffff);
	/* 从上至下的渐变背景 */
	/* 将所有内容推向底部 */
	height: 100%;
	/* 设置容器高度为视口高度 */
	/* padding-bottom: 40px; */
	/* 给底部添加一些间距 */
}

/* 图片样式 */
.top-image {
	/* margin-top: 20px; */
	/* 设置图片距离顶部 20px */
	width: 100%;
	/* 设置图片宽度为父容器的 70% */
	height: 70%;
	/* 设置图片高度为父容器的 70% */
	object-fit: cover;
	/* 保持图片比例并填充容器 */
	margin: 0 auto;
	/* 居中图片 */
	/* border-radius: 25px; */
	/* 设置四个角为圆角 */
	/* 创建底部透明渐变效果 */
	-webkit-mask-image: linear-gradient(to bottom, rgba(0, 0, 0, 1) 0%, rgba(0, 0, 0, 0) 100%);
	mask-image: linear-gradient(to bottom, rgba(0, 0, 0, 1) 0%, rgba(0, 0, 0, 0) 100%);
}



uni-input {
	height: 45px;
	width: 80%;
	/* 宽度设置为80% */
	font-weight: bold;
	border-radius: 10px;
	padding: 3px 10px;
	margin: 0 auto;
	/* 居中 */
	border: none;
	box-sizing: border-box;
	margin-top: 20px;
	/* 上间距 */
	background-color: #fff6d5;
	text-align: center;
	/* 输入内容居中 */
}

/* 默认占位符样式 */
uni-input::placeholder {
	color: #999;
	/* 默认占位符颜色 */
	transition: color 0.1s ease;
	/* 颜色渐变过渡 */
}

/* 鼠标悬停时隐藏占位符文字 */
uni-input:hover::placeholder {
	color: transparent;
}



button {
	width: 46%;
	/* 设置按钮宽度为父容器的40% */
	font-weight: bold;
	border-radius: 30px;
	margin-bottom: 20px;
}

button:active {
	transform: scale(0.99);
	/* 按钮点击时缩小 */
}

/* 登录按钮样式 */
.login-btn {
	background: #aec3ce;
	/* 红色背景 */
	color: #fff6d2;
}

/* 注册按钮样式 */
.register-btn {
	background: #cb2749;
	/* 绿色背景 */
	color: #fff6d2;
}

/* 返回按钮样式 */
.back-btn {
	background: #9b8c79;
	/* 蓝色背景 */
	color: #fff6d2;
	width: 80%;
	margin-bottom: 40px;
}

/* 按钮的颜色和间距 */
.btn {
	margin-top: 15px;
}

/* button:nth-child(2) {
		margin-top: 20rpx;
		background: #ffe8e8;
		color: #fe8787;
	} */

/* 按钮容器的样式：使用 flex 布局来排列按钮 */
.button-container {
	display: flex;
	/* 使用 flex 布局 */
	justify-content: space-between;
	/* 在容器内均匀分配按钮位置 */
	width: 80%;
	/* 容器宽度为父容器的 80% */
	margin: 0 auto;
	/* 居中显示 */
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
	/* 使用 flex 布局 */
	justify-content: center;
	/* 水平居中对齐 */
	align-items: center;
	/* 垂直居中对齐 */
	text-align: center;
	/* 文本居中 */
	width: 100%;
	/* 宽度占满整个行 */
	font-size: 12px;
	/* 调整字体大小 */
	line-height: 1.4;
	/* 增加行高以增强可读性，可根据需要调整 */
	color: #aec3ce;
	margin-bottom: 10px;
}
</style>