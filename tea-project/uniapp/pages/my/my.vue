<template>
	<view class="container">
		<uni-collapse style="background-color: #E6E6FA;">
			<uni-collapse-item>
				<!-- 自定义折叠面板标题 -->
				<template v-slot:title>
					<view class="userinfo-header">
						<image :src="userData.img || '../../static/default-avatar.png'" class="avatar"></image>
						<view class="user-info">
							<view class="username">{{ userData.username }}</view>
							<view class="order-info">已下单 {{ orderData.length }} 单</view>
							<!-- 添加进度条 -->
							<view class="progress-container">
								<view class="progress-text">离“{{ nextTitle }}”需下单{{ remainingOrders }}杯</view>
								<view class="progress-bar">
									<!-- 进度条填充部分，宽度根据订单数量计算 -->
									<view class="progress-inner" :style="{ width: progressWidth }"></view>
								</view>
							</view>
						</view>
						<!-- 新增称号显示 -->
						<view class="title-container">
							<view class="title-text" :class="titleClass">{{ currentTitle }}</view>
						</view>
					</view>
				</template>

				<!-- 折叠面板内容 -->
				<view class="userinfo-content">
					<view class="info-item">
						<text class="label">邮箱：</text>
						<text class="value">{{ userData.email || '未设置邮箱' }}</text>
					</view>

					<!-- 退出登录按钮 -->
					<button class="logout-btn" @click="logout">不玩了退了</button>
				</view>
			</uni-collapse-item>
		</uni-collapse>

		<view class="list">
			<div class="item" v-for="item in orderData" :key="item.id">
				<!-- 收货状态 -->
				<view class="state-text" :class="{ 'received': item.ostate === 1, 'unreceived': item.ostate === 0 }">
					{{ item.ostate === 1 ? '已收货' : '未收货' }}
				</view>

				<view class="item-content">
					<!-- 商品图片 -->
					<image :src="item.product[0].pimageurl" mode="aspectFill"></image>

					<!-- 商品信息 -->
					<view class="item-info">
						<view class="product-name">{{ item.product[0].pname }}</view>
						<view class="order-time">下单时间: {{ item.otime }}</view>
						<view class="price-action">
							<text class="price">￥{{ item.oprice }}</text>
							<view class="action-buttons">
								<button class="view-detail-btn" :class="{ 'with-confirm': item.ostate === 0 }"
									@click="showOrderDetail(item)">
									查看详情
								</button>
								<button v-if="item.ostate === 0" class="confirm-receive-btn"
									@click="confirmReceive(item)">
									确认收货
								</button>
							</view>
						</view>
					</view>
				</view>
			</div>
		</view>
		<uni-popup ref="orderDetailPopup" type="center" background-color="#fff">
			<view class="order-detail-popup">
				<view class="popup-header">
					<text class="popup-title">订单详情</text>
					<text class="close-btn" @click="closePopup">×</text>
				</view>
				<scroll-view class="products-list" scroll-y>
					<view class="product-item" v-for="product in currentOrderProducts" :key="product.id">
						<image :src="product.pimageurl" mode="aspectFill" class="product-image"></image>
						<view class="product-info">
							<text class="product-name">{{ product.pname }}</text>
							<text class="product-price">￥{{ product.tprice }}</text>
						</view>
					</view>
				</scroll-view>
			</view>
		</uni-popup>

	</view>
</template>

<script>
import {
	queryOrder,//获取订单信息
	queryUserInfo,//获取用户信息
	updateOrderState,//更新订单状态
} from "@/api/index";

export default {
	data() {
		return {
			id: "",
			navData: ["全部订单", "已收货", "未收货",], // 导航栏数据
			//点击切换
			currentIndex: 0,
			teaToken: "", //用户认证信息
			userData: {}, //用户信息
			orderData: [], // 确保这是数组
			inputValue: '', // 输入框的值
			newState: '', // 新的收货状态
			currentItem: null, // 当前操作的订单
			currentOrderProducts: [], // 当前查看的订单商品列表
		}
	},
	computed: {
		// 计算下一个称号和还需的订单数
		nextTitleInfo() {
			const orderCount = this.orderData.length;
			if (orderCount >= 30) return { title: '已达最高称号', remaining: 0 };
			if (orderCount >= 20) return { title: '本项目股东', remaining: 30 - orderCount };
			if (orderCount >= 10) return { title: '品茶大师', remaining: 20 - orderCount };
			if (orderCount >= 5) return { title: '品茶中手', remaining: 10 - orderCount };
			if (orderCount >= 1) return { title: '品茶入门', remaining: 5 - orderCount };
			return { title: '品茶萌新', remaining: 1 - orderCount };
		},

		// 下一个称号
		nextTitle() {
			return this.nextTitleInfo.title;
		},

		// 距离下一个称号还需订单数
		remainingOrders() {
			return this.nextTitleInfo.remaining;
		},

		// 进度条宽度计算
		progressWidth() {
			const orderCount = this.orderData.length;
			let currentLevelOrders = 0;
			let nextLevelOrders = 0;

			if (orderCount >= 30) {
				return '100%';
			} else if (orderCount >= 20) {
				currentLevelOrders = 20;
				nextLevelOrders = 30;
			} else if (orderCount >= 10) {
				currentLevelOrders = 10;
				nextLevelOrders = 20;
			} else if (orderCount >= 5) {
				currentLevelOrders = 5;
				nextLevelOrders = 10;
			} else if (orderCount >= 1) {
				currentLevelOrders = 1;
				nextLevelOrders = 5;
			} else {
				currentLevelOrders = 0;
				nextLevelOrders = 1;
			}

			// 计算当前等级内的进度百分比
			const levelProgress = (orderCount - currentLevelOrders) / (nextLevelOrders - currentLevelOrders) * 100;
			return `${levelProgress}%`;
		},

		// 计算当前称号
		currentTitle() {
			const orderCount = this.orderData.length
			if (orderCount >= 30) return '本项目股东'
			if (orderCount >= 20) return '品茶大师'
			if (orderCount >= 10) return '品茶中手'
			if (orderCount >= 5) return '品茶入门'
			if (orderCount >= 1) return '品茶萌新'
			return '游客'
		},

		// 称号样式
		titleClass() {
			const orderCount = this.orderData.length
			if (orderCount >= 30) return 'title-shareholder'
			if (orderCount >= 20) return 'title-master'
			if (orderCount >= 10) return 'title-advanced'
			if (orderCount >= 5) return 'title-beginner'
			if (orderCount >= 1) return 'title-novice'
			return 'title-visitor'
		}
	},
	onShow() {
		this.queryUserInfo()
		this.queryOrder()
		// this.updateOstate()

	},
	methods: {

		confirmReceive(order) {
			uni.showModal({
				// title: '确认收货',
				content: '确定要确认收货吗？',
				success: (res) => {
					if (res.confirm) {
						updateOrderState({
							id: order.id,
							ostate: 1  // 1 表示已收货
						}).then(res => {
							if (res.code === 200) {
								uni.showToast({
									title: '收货成功',
									icon: 'success'
								});
								// 刷新订单列表
								this.queryOrder();
							} else {
								uni.showToast({
									title: res.msg || '收货失败',
									icon: 'error'
								});
							}
						}).catch(err => {
							uni.showToast({
								title: '操作失败',
								icon: 'error'
							});
						});
					}
				}
			});
		},

		// 切换导航项
		changeIndex(index) {
			this.currentIndex = index // 更新当前选中项

			var ostate = "" // 订单状态参数

			// 根据导航索引设置不同的订单状态
			if (index === 0) {
				ostate = ""
			} else if (index === 1) {
				ostate = "1"
			} else if (index === 3) {
				ostate = "2"
			} else if (index == 2) {
				ostate = "0"
			}
			// 请求对应状态的订单数据
			queryOrder(ostate).then(res => {
				this.orderData = res.data
			})

		},

		queryUserInfo() {
			queryUserInfo().then(res => {
				this.userData = res.data
			})
		},

		// 查询单数据的方法
		queryOrder() {
			// 调用 api 中定义的 queryOrder 接口函数
			// 参数 "" 表示查询所有订单（不区分状态）
			queryOrder("").then(res => {
				// 请求成功后的回调
				// 将后台返回的订单数据赋值给 orderData
				this.orderData = res.data
				console.log('===== 订单列表数据 =====');
				console.log('订单数据：', res.data) // 添加这行来查看数据

			})
		},




		logout() {
			uni.showModal({
				title: '提示',
				content: '你确定退出登录吗',
				success: function (res) {
					if (res.confirm) {
						uni.removeStorage({
							key: 'token',
							success: function () {
								console.log('用户 token 已删除');
								// 跳转到登录页面或其他逻辑
							},
							fail: function (err) {
								console.error('删除用户 token 失败', err);
							}
						});
						uni.showToast({
							title: '用户已退出',
							duration: 2000
						});
						setTimeout(() => {

							uni.navigateTo({
								url: "/pages/login/login"
							})
						}, 2000)

						console.log('用户点击确定');
					} else if (res.cancel) {
						uni.showToast({
							title: '用户取消退出',
							duration: 2000
						});
						console.log('用户点击取消');
					}
				}
			});
		},

		// 添加状态文本转换方法
		getStateText(state) {
			const stateMap = {
				0: '未收货',
				1: '已收货',
				2: '退订',
				3: '禁用'
			}
			return stateMap[state] || '未知'
		},

		// 显示订单详情
		showOrderDetail(order) {
			console.log('显示订单详情:', order); // 添加调试日志
			this.currentOrderProducts = order.product;
			// 确保通过 ref 正确引用弹窗组件
			this.$nextTick(() => {
				this.$refs.orderDetailPopup.open('center');
			});
		},

		// 关闭弹窗
		closePopup() {
			this.$refs.orderDetailPopup.close();
		},








	}
}
</script>

<style scoped>
.userinfo-header {
	display: flex;
	align-items: center;
	gap: 30rpx;
	padding: 30rpx 20rpx;
	background-color: #E6E6FA;
	height: 180rpx;
}

.avatar {
	width: 100rpx;
	height: 100rpx;
	margin-left: 20rpx;
	border-radius: 50%;
}

.username {
	font-size: 40rpx;
	color: #333;
	font-weight: 600;
}

.userinfo-content {
	padding: 20rpx;
	display: flex;
	flex-direction: column;
	gap: 30rpx;
	background-color: #E6E6FA;

}

.info-item {
	display: flex;
	align-items: center;
}

.label {
	color: #4F4F4F;
	margin-right: 20rpx;
}

.value {
	color: #4F4F4F;
}

.list {
	padding: 30rpx;
	box-sizing: border-box;
	padding-bottom: 60px;
	border-radius: 22rpx;

}

.item {
	position: relative;
	padding: 30rpx;
	background-color: #E6E6FA;
	margin-bottom: 20rpx;
	border-radius: 15rpx;
	box-shadow: 0 4rpx 12rpx rgba(92, 73, 95, 0.08);
}

.item-content {
	display: flex;
	gap: 30rpx;
}

.item image {
	width: 180rpx;
	height: 180rpx;
	border-radius: 12rpx;
	flex-shrink: 0;
	box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
}

.item-info {
	flex: 1;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.product-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #5c495f;
	margin-bottom: 12rpx;
	/* 文本超出省略 */
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}

.order-time {
	color: #8a7a8c;
	font-size: 26rpx;
	margin-bottom: 12rpx;
}

.price-action {
	position: relative;
	width: 100%;
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-top: 10px;
}

.price {
	color: #ff4d4f;
	font-weight: 600;
	font-size: 28rpx;
}

.action-buttons {
	display: flex;
	gap: 20rpx;
}

.view-detail-btn {
	position: absolute;
	bottom: -10rpx;
	right: -15rpx;
	background: #8a7a8c;
	color: #ffffff;
	border: none;
	border-radius: 16rpx;
	padding: 4rpx 16rpx;
	font-size: 22rpx;
	min-height: 0;
	line-height: 1.6;
	margin: 0;
	width: auto;
	z-index: 1;
	box-shadow: 0 2rpx 6rpx rgba(92, 73, 95, 0.2);
}

.view-detail-btn:active {
	background-color: #766579;
	transform: scale(0.95);
}

.confirm-receive-btn {
	position: absolute;
	bottom: -10rpx;
	right: -15rpx;
	background: #5c495f;
	color: #ddcaad;
	border: none;
	border-radius: 16rpx;
	padding: 4rpx 16rpx;
	font-size: 22rpx;
	min-height: 0;
	line-height: 1.6;
	margin: 0;
	width: auto;
	z-index: 1;
	box-shadow: 0 2rpx 6rpx rgba(92, 73, 95, 0.2);
}

.confirm-receive-btn:active {
	background-color: #4a3a4d;
	transform: scale(0.95);
}

.state-text {
	position: absolute;
	top: 20rpx;
	right: 20rpx;
	font-size: 24rpx;
	padding: 4rpx 12rpx;
	border-radius: 8rpx;
}

.received {
	color: #5c495f;
}

.unreceived {
	color: #ddcaad;
}

.container {
	background: #ffffff;
}

.logout-btn {
	margin-top: 30rpx;
	width: 90%;
	height: 80rpx;
	line-height: 80rpx;
	text-align: center;
	background-color: #ff426e;
	color: #fff;
	border-radius: 40rpx;
	font-size: 28rpx;
}

.logout-btn:active {
	opacity: 0.8;
}

/* 修改折叠面板的样式 */
:deep(.uni-collapse-item__title) {
	background-color: #57310D !important;
}

:deep(.uni-collapse-item__wrap) {
	background-color: #57310D !important;
}

.current-state {
	text-align: center;
	margin-bottom: 15px;
	color: #666;
}

.popup-input {
	border: 1px solid #eee;
	padding: 8px;
	border-radius: 4px;
	width: 100%;
	text-align: center;
}

.glass-effect {
	background: rgba(255, 255, 255, 0.7);
}

.popup-content {
	width: 80%;
	padding: 40rpx 30rpx;
	border-radius: 30rpx;
	background: rgba(255, 255, 255, 0.95);
	backdrop-filter: blur(10px);
	box-shadow: 0 10rpx 30rpx rgba(92, 73, 95, 0.15);
}

.popup-title {
	font-size: 34rpx;
	font-weight: 600;
	text-align: center;
	color: #5c495f;
	margin-bottom: 40rpx;
}

.popup-body {
	padding: 0 20rpx;
	text-align: center;
	color: #8a7a8c;
	font-size: 28rpx;
	margin-bottom: 50rpx;
}

.popup-footer {
	display: flex;
	justify-content: space-between;
	gap: 20rpx;
	padding: 0 20rpx;
}

.popup-btn {
	flex: 1;
	height: 80rpx;
	border-radius: 40rpx;
	font-size: 28rpx;
	font-weight: 500;
	border: none;
	transition: all 0.3s ease;
}

.confirm-btn {
	background: #5c495f;
	color: #ddcaad;
}

.confirm-btn:active {
	background: #4a3a4d;
	transform: scale(0.98);
}

.cancel-btn {
	background: rgba(92, 73, 95, 0.1);
	color: #5c495f;
}

.cancel-btn:active {
	background: rgba(92, 73, 95, 0.15);
	transform: scale(0.98);
}

/* 毛玻璃效果 */
.glass-effect {
	position: relative;
	overflow: hidden;
}

.glass-effect::before {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	backdrop-filter: blur(10px);
	-webkit-backdrop-filter: blur(10px);
	background: rgba(255, 255, 255, 0.7);
	z-index: -1;
}

.state-text {
	position: absolute;
	top: 20rpx;
	right: 20rpx;
	font-size: 24rpx;
	color: #666;
}

.received {
	color: #5c495f;
}

.unreceived {
	color: #ddcaad;
}

/* 新增用户信息容器样式 */
.user-info {
	display: flex;
	flex-direction: column;
	gap: 12rpx;
	flex: 1;
}

/* 增订单信息样式 */
.order-info {
	font-size: 26rpx;
	color: #666;
}

/* 进度条容器 */
.progress-container {
	margin-top: 8rpx;
}

/* 进度条文字 */
.progress-text {
	font-size: 24rpx;
	color: #666;
	margin-bottom: 6rpx;
}

/* 进度条背景 */
.progress-bar {
	width: 100%;
	height: 6rpx;
	background-color: #ffffff;
	/* 白色背景 */
	border-radius: 3rpx;
	overflow: hidden;
}

/* 进度条填充部分 */
.progress-inner {
	height: 100%;
	background-color: #0b100b;
	/* 紫色填充 */
	border-radius: 3rpx;
	transition: width 0.3s ease;
	/* 平滑过渡动画 */
}

/* 称号容器样式 */
.title-container {
	margin-left: auto;
	/* 将称号推到右侧 */
	padding: 0 20rpx;
}

.title-text {
	font-size: 24rpx;
	padding: 8rpx 16rpx;
	border-radius: 20rpx;
	font-weight: 600;
}

/* 不同称号的样式 */
.title-shareholder {
	background: linear-gradient(45deg, #FFD700, #FFA500);
	color: #fff;
	box-shadow: 0 2px 4px rgba(255, 215, 0, 0.3);
}

.title-master {
	background: linear-gradient(45deg, #9932CC, #800080);
	color: #fff;
	box-shadow: 0 2px 4px rgba(153, 50, 204, 0.3);
}

.title-advanced {
	background: linear-gradient(45deg, #4169E1, #0000CD);
	color: #fff;
	box-shadow: 0 2px 4px rgba(65, 105, 225, 0.3);
}

.title-beginner {
	background: linear-gradient(45deg, #3CB371, #2E8B57);
	color: #fff;
	box-shadow: 0 2px 4px rgba(60, 179, 113, 0.3);
}

.title-novice {
	background: linear-gradient(45deg, #87CEEB, #4682B4);
	color: #fff;
	box-shadow: 0 2px 4px rgba(135, 206, 235, 0.3);
}

.title-visitor {
	background: #f5f5f5;
	color: #666;
}

.price-action {
	position: relative;
	width: 100%;
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-top: 10px;
}

.confirm-receive-btn {
	position: absolute;
	bottom: -10rpx;
	right: -15rpx;
	background: #5c495f;
	color: #ddcaad;
	border: none;
	border-radius: 16rpx;
	padding: 4rpx 16rpx;
	font-size: 22rpx;
	min-height: 0;
	line-height: 1.6;
	margin: 0;
	width: auto;
	z-index: 1;
	box-shadow: 0 2rpx 6rpx rgba(92, 73, 95, 0.2);
}

.confirm-receive-btn:active {
	background-color: #4a3a4d;
	transform: scale(0.95);
}

/* 查看详情按钮样式 */
.view-detail-btn {
	position: absolute;
	bottom: -10rpx;
	right: -15rpx;
	background: #8a7a8c;
	color: #ffffff;
	border: none;
	border-radius: 16rpx;
	padding: 4rpx 16rpx;
	font-size: 22rpx;
	min-height: 0;
	line-height: 1.6;
	margin: 0;
	width: auto;
	z-index: 1;
	box-shadow: 0 2rpx 6rpx rgba(92, 73, 95, 0.2);
}

.view-detail-btn:active {
	background-color: #766579;
	transform: scale(0.95);
}

/* 弹窗样式 */
.order-detail-popup {
	width: 600rpx;
	background: #ffffff;
	border-radius: 20rpx;
	padding: 30rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.1);
}

.popup-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding-bottom: 20rpx;
	margin-bottom: 20rpx;
	border-bottom: 2rpx solid #E6E6FA;
}

.popup-title {
	font-size: 32rpx;
	font-weight: 600;
	color: #5c495f;
}

.close-btn {
	width: 48rpx;
	height: 48rpx;
	line-height: 48rpx;
	text-align: center;
	font-size: 40rpx;
	color: #8a7a8c;
	border-radius: 24rpx;
	transition: all 0.3s;
}

.close-btn:active {
	background-color: #f0f0f0;
}

/* 商品列表区域 */
.products-list {
	max-height: 700rpx;
	overflow-y: auto;
}

/* 商品项 */
.product-item {
	display: flex;
	align-items: center;
	padding: 20rpx 0;
	border-bottom: 2rpx solid #E6E6FA;
}

.product-item:last-child {
	border-bottom: none;
}

/* 商品图片 */
.product-image {
	width: 140rpx;
	height: 140rpx;
	border-radius: 12rpx;
	margin-right: 24rpx;
	box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
}

/* 商品信息 */
.product-info {
	flex: 1;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	height: 120rpx;
	padding: 10rpx 0;
}

.product-name {
	font-size: 28rpx;
	color: #333;
	font-weight: 500;
	/* 文本超出省略 */
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}

.product-price {
	font-size: 32rpx;
	color: #ff4d4f;
	font-weight: 600;
}

/* 设置滚动条样式 */
.products-list ::-webkit-scrollbar {
	width: 4rpx;
	background-color: #f5f5f5;
}

.products-list ::-webkit-scrollbar-thumb {
	background-color: #E6E6FA;
	border-radius: 2rpx;
}

.view-detail-btn.with-confirm {
	right: 120rpx;
}
</style>