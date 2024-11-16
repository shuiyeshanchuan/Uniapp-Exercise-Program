<template>
	<!-- 容器视图，监听触摸事件用于滑动检测 -->
	<view class="container" @touchstart="onTouchStart" @touchend="onTouchEnd">
		<!-- 简化后的背景图层 -->
		<image class="bg-image" src="/static/welcome.jpg" mode="cover"></image>

		<!-- 渐变遮罩，增强文字可读性 -->
		<view class="overlay"></view>

		<!-- 中央内容区域：展示欢迎文字 -->
		<view class="content">
			<view class="header">
				<view class="brand-name">茶礼牛奶</view>
				<view class="brand-name-en">Milk In Tea DiaoZhaTian
				</view>
			</view>

			<view class="slogan">
				<view class="column">
					<text>国</text>
					<text>窖</text>
					<text>二</text>
					<text>零</text>
					<text>二</text>
					<text>四</text>
				</view>
				<view class="column">
					<text>百</text>
					<text>年</text>
					<text>传</text>
					<text>承</text>
				</view>
			</view>
		</view>

		<!-- 底部上滑提示：动画箭头 -->
		<view class="scroll-hint">
			<view class="arrow"></view>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			// 记录触摸开始时的纵坐标
			startY: 0
		}
	},
	methods: {
		// 触摸开始事件处理
		onTouchStart(e) {
			this.startY = e.touches[0].clientY;
		},
		// 触摸结束事件处理
		onTouchEnd(e) {
			const endY = e.changedTouches[0].clientY;
			const moveDistance = this.startY - endY;

			// 当上滑距离超过100px时跳转到登录页
			if (moveDistance > 100) {
				uni.navigateTo({
					url: '/pages/login/login'
				});
			}
		}
	},

}
</script>

<style scoped>
.container {
	width: 100%;
	height: 100vh;
	min-height: 100vh;
	position: relative;
	overflow: hidden;
}

.bg-image {
	position: absolute;
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.overlay {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: linear-gradient(to bottom,
			rgba(0, 0, 0, 0.3) 0%,
			rgba(0, 0, 0, 0.1) 50%,
			rgba(0, 0, 0, 0.3) 100%);
}

/* 内容区域样式：半透明背景配合模糊效果，营造磨砂玻璃效果 */
.content {
	position: absolute;
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	padding: 200rpx 40rpx 300rpx;
	/* 上、左右、下的内边距 */
	box-sizing: border-box;
}

/* 头部品牌区域 */
.header {
	text-align: center;
}

.brand-name {
	font-family: "楷体", KaiTi, serif;
	font-size: 60rpx;
	color: rgba(255, 255, 255, 0.95);
	letter-spacing: 4rpx;
	font-weight: 500;
	margin-bottom: 10rpx;
}

.brand-name-en {
	font-family: "Times New Roman", serif;
	font-size: 32rpx;
	color: rgba(255, 255, 255, 0.8);
	letter-spacing: 2rpx;
}

/* 标语区域 */
.slogan {
	display: flex;
	flex-direction: row-reverse;
	justify-content: center;
	gap: 40rpx;
}

.column {
	display: flex;
	flex-direction: column;
	align-items: center;
}

.column text {
	font-family: "楷体", KaiTi, serif;
	font-size: 20px;
	color: #f2eeee;
	/* 深褐色 */
	line-height: 2;
	font-weight: 600;
	/* 加粗以提高深色文字的可读性 */
	text-shadow: 0 0 2rpx rgba(235, 228, 228, 0.3);
	/* 添加轻微文字阴影提高可读性 */
}

/* 上滑提示容器：固定在底部，包含浮动动画 */
.scroll-hint {
	position: fixed;
	bottom: 40rpx;
	left: 50%;
	transform: translateX(-50%);
	width: 60rpx;
	height: 60rpx;
	display: flex;
	justify-content: center;
	align-items: center;
	animation: float 1.5s ease-in-out infinite;
}

/* 箭头样式：使用边框创建箭头形状 */
.arrow {
	width: 30rpx;
	height: 30rpx;
	border-left: 4rpx solid rgba(255, 255, 255, 0.6);
	border-top: 4rpx solid rgba(255, 255, 255, 0.6);
	transform: rotate(45deg);
}

/* 浮动动画：实现箭头上下浮动效果 */
@keyframes float {
	0% {
		transform: translate(-50%, 0);
	}

	50% {
		transform: translate(-50%, -20rpx);
	}

	100% {
		transform: translate(-50%, 0);
	}
}
</style>
