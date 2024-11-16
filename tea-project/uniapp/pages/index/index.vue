<template>
	<view class="container">
		<view class="banner-container">
			<image src="../../static/HAND.png" mode="aspectFill" class="banner-image">
			</image>
			<view class="gradient-overlay"></view>
		</view>

		<!-- 用户信息卡片 -->
		<view class="user-card">
			<view class="avatar-container">
				<image :src="userData.img || '../../static/default-avatar.png'" class="avatar"></image>
			</view>
			<view class="user-info">
				<text class="username">能登录上来也是奇迹之"你的名字"：<text
						:style="{ color: '#FFF8DC', fontSize: '20px', fontWeight: 'bold' }">{{ userData.username
						}}</text></text>
			</view>
		</view>

		<view class="box" @click="toProduct">
			<view class="nav-section">
				<image src="../../static/icon3.png"></image>
				<view class="title">请儿自取</view>
				<view class="content">从长城排到圣母院</view>
			</view>

			<!-- 添加分隔线 -->
			<view class="divider"></view>

			<view class="nav-section">
				<image style="border-radius: 30rpx;" src="../../static/memesong.gif"></image>
				<view class="title">喊哥给送</view>
				<view class="content">比那碗胡辣汤还快</view>
			</view>
		</view>



	</view>

</template>

<script>
import {
	queryImageByType,
	queryUserInfo // 添加用户信息接口
} from "@/api/index";

//Vue中核心概念  以数据作为驱动 页面效果展示和隐藏  js、jquery通过DOM节点操作 Vue通过变量操作

export default {
	data() {
		return {
			title: 'Hello',
			userData: {}, //用户信息
			teaToken: "", //用户认证信息
			swiperImageList: [{
				iurl: "http://47.96.80.123:8000/img/i4.png"
			},]
		}
	},
	// 生命周期回调监听页面加载
	// 页面加载时触发。一个页面只会调用一次，可以在 onLoad 的参数中获取打开当前页面路径中的参数。
	onLoad() { },
	// 生命周期回调监听应用显示
	// 应用启动，或从后台进入前台显示时触发

	//离开当前页面再次进入会重新触发该方法
	onShow() {
		this.getBannerList()
		this.queryUserInfo()

	},
	methods: {
		getCarouselList() {
			getCarouselQueryPage({}).then(res => {
				this.carouselList = res.data.list
			})
		},
		toUrl() {
			uni.navigateTo({
				url: "/pages/test/test?text=我是从index页面传递过来的123&id=999"
			})
		},
		//定义获取轮播API接口方法
		getBannerList() {
			queryImageByType(2).then(res => {
				console.log(res.data)
				this.swiperImageList = res.data
			})

		},
		//跳转到产品列表页
		toProduct() {
			uni.switchTab({
				url: "/pages/product/product"
			})
		},
		queryUserInfo() {
			queryUserInfo().then(res => {
				this.userData = res.data
			})
		},

	}
}
</script>
<!-- scoped 表示css样式只在局部生效 当前页面 -->
<style scoped>
.container {
	background-color: #CBDA99;
	min-height: 100vh;
	padding: 0;
	width: 100%;
	overflow-x: hidden;
}

.banner-container {
	position: relative;
	width: 100vw;
	height: 60vh;
	margin: 0;
}

.banner-image {
	width: 100vw;
	height: 100%;
	object-fit: cover;
	display: block;
}

.user-card {
	margin: 30rpx 40rpx;
	height: 40rpx;
	border-radius: 20rpx;
	padding: 20rpx;
	padding-left: 120rpx;
	backdrop-filter: blur(10px);
	background-color: rgba(86, 118, 94, 0.6);
	position: relative;
}

.avatar-container {
	position: absolute;
	/* 绝对定位，相对于最近的定位父元素（user-card）进行定位 */

	/* 控制圆球的位置 */
	left: -5rpx;
	/* 负值会让圆球向左移出卡片，正值会向右移动 */
	top: -15rpx;
	/* 负值会让圆球向上移出卡片，正值会向下移动 */

	/* 控制圆球的大小 */
	width: 100rpx;
	/* 圆球的宽度，增大这个值会让圆球变大 */
	height: 100rpx;
	/* 圆球的高度，需要和宽度保持一致才能是正圆形 */

	/* 圆球的样式 */
	border-radius: 50%;
	/* 使容器呈现为圆形 */
	overflow: hidden;
	/* 确保图片不会超出圆形范围 */
	border: 4rpx solid rgba(86, 118, 94, 0.6);
	/* 边框粗细和颜色 */
}

.avatar {
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.user-info {
	display: flex;
	align-items: center;
	height: 100%;
}

.username {
	color: #CBDA99;
	font-size: 28rpx;
	font-family: "Noto Sans SC", sans-serif;
}

.gradient-overlay {
	position: absolute;
	bottom: 0;
	left: 0;
	width: 100vw;
	height: 200rpx;
	background: linear-gradient(to bottom,
			rgba(203, 218, 153, 0),
			rgba(203, 218, 153, 0.5),
			rgba(203, 218, 153, 1));
	pointer-events: none;
}

.box {
	display: flex;
	justify-content: space-around;
	align-items: center;
	margin: 30rpx 40rpx;
	border-radius: 20rpx;
	padding: 30rpx 20rpx;
	backdrop-filter: blur(10px);
	background-color: rgba(86, 118, 94, 0.6);
	position: relative;
}

.nav-section {
	flex: 1;
	display: flex;
	flex-direction: column;
	border-radius: 20rpx;
	align-items: center;
}

.divider {
	position: absolute;
	left: 50%;
	transform: translateX(-50%);
	height: 60%;
	width: 4rpx;
	background-color: #CBDA99;
	opacity: 0.8;
	top: 20%;
}

.box image {
	width: 180rpx;
	height: 180rpx;
	/* object-fit: contain; */
}

.box text {
	font-size: 28rpx;
	color: #CBDA99;
}

.box>view view {
	text-align: center;
}

.title {
	font-weight: bold;
	font-size: 23px;
	margin-bottom: 5px;
	color: #CBDA99;
	font-family: "Noto Serif SC", "华文仿宋", sans-serif;
}

.content {
	color: #CBDA99;
	font-family: "Noto Sans SC", "华文仿宋", sans-serif;
}

.info {
	display: flex;
	justify-content: space-around;
}

.info view {
	text-align: center;
}

.info image {
	width: 70px;
	height: 70px;
}

.info>view {
	line-height: 35px;
	background: pink;
	padding: 20px;
}

.info>view:nth-child(1) {
	background: #f5f6fa;
}

.info>view:nth-child(2) {
	background: #fdf7eb;
}

.info>view:nth-child(3) {
	background: rgb(242, 246, 229);
}

.info-title {
	font-weight: bold;
	font-size: 19px;
}

.info-text {
	color: #ccc;
	font-size: 13px;
	margin-bottom: 5px;
}
</style>