<template>
	<view class="container">

		<!-- 店铺信息区域 -->
		<view class="shop-info">
			<view class="shop-header">
				<view>
					<view class="shop-name">北极科考站店</view>
					<view class="location">
						<uni-icons type="location" size="12" color="#999"></uni-icons>
						<text>距离您{{ distance }}</text>
					</view>
				</view>
				<view class="clear-btn" @click="showClearConfirm" v-if="cartData.length > 0">
					<uni-icons type="trash" size="16" color="#999"></uni-icons>
					<text>清空</text>
				</view>
			</view>
		</view>

		<view class="list">
			<!-- 空购物车提示 -->
			<view class="no-cart" v-if="cartData.length <= 0">
				<image src="/static/empty-cart.png" mode="aspectFit"></image>
				<text class="empty-text">购物车还是空的呢</text>
				<text class="go-shopping" @click="toProduct">去选购</text>
			</view>

			<!-- 商品列表 -->
			<view class="cart-item" v-for="item in cartData" :key="item.id">
				<image class="product-image" :src="item.product.pimageurl" mode="aspectFill"></image>
				<view class="product-content">
					<view class="product-info">
						<text class="product-name">{{ item.product.pname }}</text>
						<text class="product-desc">{{ item.product.tintroduce }}</text>
					</view>
					<view class="price-control">
						<text class="price">￥{{ item.product.tprice }}</text>
						<view class="quantity-control">
							<view class="control-btn minus" @click="changeNumber('down', item.id)">
								<uni-icons type="minus" size="16" color="#666"></uni-icons>
							</view>
							<text class="quantity">{{ item.number }}</text>
							<view class="control-btn plus" @click="changeNumber('add', item.id)">
								<uni-icons type="plus" size="16" color="#666"></uni-icons>
							</view>
						</view>
					</view>
				</view>
				<!-- 删除按钮 -->
				<view class="delete-btn" @click="delCart(item.id)">
					<uni-icons type="trash" size="18" color="#fff"></uni-icons>
				</view>
			</view>
		</view>

		<view class="floating-cart" v-if="cartData.length > 0">
			<view class="cart-left">
				<view class="cart-icon">
					<uni-icons type="shop" size="24" color="#fff"></uni-icons>
					<view class="badge" v-if="cartData.length">{{ cartData.length }}</view>
				</view>
				<view class="price">¥{{ getTotalPrice }}</view>
			</view>
			<view class="checkout-btn" @click="handleCheckout()">
				去结算
			</view>
		</view>

		<!-- 结算确认弹窗 -->
		<uni-popup ref="checkoutPopup" type="center">
			<view class="checkout-popup">
				<view class="popup-header">
					<text class="popup-title">确认订单</text>
					<uni-icons type="close" size="20" color="#999" @click="closePopup"></uni-icons>
				</view>

				<scroll-view class="order-items" scroll-y>
					<view class="order-item" v-for="item in cartData" :key="item.id">
						<image :src="item.product.pimageurl" mode="aspectFill"></image>
						<view class="item-info">
							<text class="item-name">{{ item.product.pname }}</text>
							<text class="item-desc">{{ item.product.tintroduce }}</text>
							<view class="item-price-qty">
								<text class="item-price">￥{{ item.product.tprice }}</text>
								<text class="item-qty">x{{ item.number }}</text>
							</view>
						</view>
					</view>
				</scroll-view>

				<view class="popup-footer">
					<view class="total-amount">
						<text>合计：</text>
						<text class="amount">￥{{ getTotalPrice }}</text>
					</view>
					<view class="action-buttons">
						<button class="cancel-btn" @click="closePopup">取消</button>
						<button class="confirm-btn" @click="confirmOrder">确认结算</button>
					</view>
				</view>
			</view>
		</uni-popup>

		<!-- 在结算确认弹窗后添加支付弹窗 -->
		<uni-popup ref="paymentPopup" type="center">
			<view class="payment-popup">
				<view class="popup-header">
					<text class="popup-title">多少给点蟹蟹老板</text>
					<uni-icons type="close" size="20" color="#999" @click="closePaymentPopup"></uni-icons>
				</view>

				<view class="qr-container">
					<image src="/static/ShouKuan.jpg" mode="aspectFit" class="qr-code"></image>
				</view>

				<view class="payment-actions">
					<button class="cancel-pay-btn" @click="closePaymentPopup">取消支付</button>
					<button class="confirm-pay-btn" @click="handlePaymentConfirm">已完成支付</button>
				</view>
			</view>
		</uni-popup>

	</view>
</template>

<script>
import {
	myCart,
	deleteCart,
	changeNumber,
	addOrder,
	clearCart,
} from "@/api/index";

export default {
	data() {
		return {
			//用户购物车数据
			cartData: [],
			distance: '计算中...',
			shopLocation: {
				latitude: 90, // 北极点纬度
				longitude: 0 // 北极点经度
			}
		}
	},
	onLoad() {
		this.calculateDistance()

	},
	onShow() {
		this.queryCartInfo()
	},
	methods: {

		calculateDistance() {
			uni.getLocation({
				type: 'wgs84',
				success: (res) => {
					const userLat = res.latitude
					const userLon = res.longitude
					const distance = this.getDistanceFromLatLonInKm(
						userLat,
						userLon,
						this.shopLocation.latitude,
						this.shopLocation.longitude
					)
					this.distance = distance < 1 ?
						`${(distance * 1000).toFixed(0)}m` :
						`${distance.toFixed(1)}km`
				},
				fail: () => {
					this.distance = '未能获取位置'
				}
			})
		},

		// 使用 Haversine 公式计算两点之间的距离
		getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
			const R = 6371 // 地球半径（公里）
			const dLat = this.deg2rad(lat2 - lat1)
			const dLon = this.deg2rad(lon2 - lon1)
			const a =
				Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(this.deg2rad(lat1)) * Math.cos(this.deg2rad(lat2)) *
				Math.sin(dLon / 2) * Math.sin(dLon / 2)
			const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
			return R * c
		},

		deg2rad(deg) {
			return deg * (Math.PI / 180)
		},


		queryCartInfo() {
			//发起请求
			myCart().then(res => {
				console.log(res.data)
				this.cartData = res.data
			})
		},
		delCart(id) {

			deleteCart(id).then(res => {
				this.cartData = res.data
				this.queryCartInfo()
			})

		},
		changeNumber(cmd, id) {

			changeNumber(id, cmd).then(res => {
				this.queryCartInfo()
			})

		},
		//点击结算 添加订单
		addOrder() {
			addOrder().then(res => {
				uni.showToast({
					title: "购买成功"
				})
				//跳转到my页面 switchtab
				uni.switchTab({
					url: "/pages/my/my"
				})
			})
		},
		toProduct() {
			uni.switchTab({
				url: "/pages/product/product"
			})
		},
		// 打开结算弹窗
		showCheckoutPopup() {
			this.$refs.checkoutPopup.open()
		},

		// 关闭弹窗
		closePopup() {
			this.$refs.checkoutPopup.close()
		},

		// 确认结算
		confirmOrder() {
			// 关闭确认订单弹窗
			this.closePopup()
			// 打开支付弹窗
			this.$refs.paymentPopup.open()
		},

		// 关闭支付弹窗
		closePaymentPopup() {
			this.$refs.paymentPopup.close()
		},

		// 处理支付确认
		handlePaymentConfirm() {
			// 关闭支付弹窗
			this.closePaymentPopup()

			// 调用订单API
			addOrder().then(res => {
				uni.showToast({
					title: '支付成功',
					icon: 'success',
					duration: 2000,
					position: 'top'
				})

				// 延迟跳转
				setTimeout(() => {
					uni.switchTab({
						url: "/pages/my/my"
					})
				}, 1500)
			}).catch(err => {
				uni.showToast({
					title: '支付失败',
					icon: 'error'
				})
			})
		},

		// 修改原有的去结算按钮点击事件
		handleCheckout() {
			this.showCheckoutPopup()
		},

		// 清空确认弹窗
		showClearConfirm() {
			uni.showModal({
				title: '清空购物车',
				content: '确定要清空购物车吗？',
				confirmColor: '#fe4e88',
				success: (res) => {
					if (res.confirm) {
						this.clearCart()
					}
				}
			})
		},

		// 清空购物车
		clearCart() {
			// 调用清空购物车的API
			clearCart().then(res => {
				this.cartData = []
				this.queryCartInfo()
			}).catch(err => {
				uni.showToast({
					title: '清空失败',
					icon: 'error'
				})
			})
		}
	},
	computed: {
		getTotalPrice() {
			var num = 0;
			for (var i = 0; i < this.cartData.length; i++) {
				var cart = this.cartData[i];
				num += cart.number * cart.product.tprice
			}
			return num.toFixed(2) //保留小数点后两位
		}
	}
}
</script>

<style scoped>
/* 列表容器 */
.list {
	padding: 20rpx;
	padding-bottom: 180rpx;
	/* 为底部浮动栏留出空间 */
}

/* 空购物车样式 */
.no-cart {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 60rpx 0;
}

.no-cart image {
	width: 240rpx;
	height: 240rpx;
	margin-bottom: 30rpx;
}

.empty-text {
	font-size: 28rpx;
	color: #999;
	margin-bottom: 20rpx;
}

.go-shopping {
	font-size: 28rpx;
	color: #fe4e88;
	padding: 16rpx 40rpx;
	border-radius: 30rpx;
	background: rgba(254, 78, 136, 0.1);
}

/* 商品卡片样式 */
.cart-item {
	position: relative;
	display: flex;
	padding: 30rpx;
	background: #E6E6FA;
	border-radius: 20rpx;
	margin-bottom: 20rpx;
	box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
}

.product-image {
	width: 180rpx;
	height: 180rpx;
	border-radius: 16rpx;
	object-fit: cover;
	box-shadow: 0 2rpx 6rpx rgba(0, 0, 0, 0.1);
}

.product-content {
	flex: 1;
	margin-left: 24rpx;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	/* background-color: #6c62f7; */
}

.product-info {
	flex: 1;
	display: flex;
	flex-direction: column;
	gap: 8rpx;
}

.product-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #333;
}

.product-desc {
	font-size: 24rpx;
	color: #999;
	line-height: 1.4;
}

.price-control {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-top: 20rpx;
}

.price {
	font-size: 36rpx;
	font-weight: 600;
	color: #ff0000;
}

/* 数量控制器样式 */
.quantity-control {
	display: flex;
	align-items: center;
	gap: 20rpx;
	background: #f8f8f8;
	padding: 8rpx 16rpx;
	border-radius: 30rpx;
}

.control-btn {
	width: 44rpx;
	height: 44rpx;
	border-radius: 22rpx;
	background: #fff;
	display: flex;
	align-items: center;
	justify-content: center;
	transition: all 0.2s;
	box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.1);
}

.control-btn:active {
	transform: scale(0.9);
}

.quantity {
	min-width: 40rpx;
	text-align: center;
	font-size: 28rpx;
	color: #333;
	font-weight: 500;
}

/* 删除按钮样式 */
.delete-btn {
	position: absolute;
	top: 20rpx;
	right: 20rpx;
	width: 56rpx;
	height: 56rpx;
	border-radius: 28rpx;
	background: #ff6b6b;
	display: flex;
	align-items: center;
	justify-content: center;
	transition: all 0.2s;
	box-shadow: 0 2rpx 6rpx rgba(255, 107, 107, 0.3);
}

.delete-btn:active {
	transform: scale(0.9);
	background: #ff5252;
}

/* 顶部区域 start */
.header {
	padding: 30rpx;
	background: #fff;
	display: flex;
	justify-content: space-between;
	align-items: center;
	box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.header-left {
	flex: 1;
	margin-right: 20rpx;
}

.shop-title {
	display: flex;
	align-items: center;
	gap: 8rpx;
	margin-bottom: 12rpx;
}

.title-text {
	font-size: 36rpx;
	font-weight: 600;
	color: #5c495f;
}

.location-info {
	display: flex;
	align-items: center;
	gap: 6rpx;
	font-size: 24rpx;
	color: #999;
}

.shop-logo {
	width: 140rpx;
	height: 90rpx;
	border-radius: 8rpx;
}

/* 顶部区域 end */

.floating-cart {
	position: fixed;
	bottom: 120rpx;
	left: 50%;
	transform: translateX(-50%);
	width: 90%;
	height: 96rpx;
	background: rgba(0, 0, 0, 0.85);
	border-radius: 48rpx;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0 20rpx 0 30rpx;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.2);
	z-index: 99;
}

.cart-left {
	display: flex;
	align-items: center;
	gap: 20rpx;
}

.cart-icon {
	position: relative;
	width: 48rpx;
	height: 48rpx;
	display: flex;
	align-items: center;
	justify-content: center;
}

.badge {
	position: absolute;
	top: -8rpx;
	right: -8rpx;
	min-width: 32rpx;
	height: 32rpx;
	border-radius: 16rpx;
	background: #f3a1e7;
	color: #fff;
	font-size: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 0 6rpx;
}

.price {
	color: #fff;
	font-size: 32rpx;
	font-weight: 500;
}

.checkout-btn {
	background: #fe4e88;
	height: 72rpx;
	padding: 0 40rpx;
	border-radius: 36rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	color: #fff;
	font-size: 28rpx;
	font-weight: 500;
	transition: all 0.2s;
}

.checkout-btn:active {
	transform: scale(0.95);
	background: #fe4e88;
}

.checkout-popup {
	width: 650rpx;
	background: #fff;
	border-radius: 20rpx;
	overflow: hidden;
}

.popup-header {
	padding: 30rpx;
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-bottom: 1rpx solid #f5f5f5;
}

.popup-title {
	font-size: 32rpx;
	font-weight: 600;
	color: #333;
}

.order-items {
	max-height: 60vh;
	padding: 20rpx 30rpx;
}

.order-item {
	display: flex;
	padding: 20rpx 0;
	border-bottom: 1rpx solid #f5f5f5;
}

.order-item image {
	width: 120rpx;
	height: 120rpx;
	border-radius: 10rpx;
	margin-right: 20rpx;
}

.item-info {
	flex: 1;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.item-name {
	font-size: 28rpx;
	font-weight: 500;
	color: #333;
}

.item-desc {
	font-size: 24rpx;
	color: #999;
	margin: 8rpx 0;
}

.item-price-qty {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.item-price {
	font-size: 28rpx;
	color: #5c495f;
	font-weight: 600;
}

.item-qty {
	font-size: 28rpx;
	color: #999;
	font-weight: 500;
}

.popup-footer {
	padding: 20rpx 30rpx;
	border-top: 1rpx solid #f5f5f5;
	background: #fff;
}

.total-amount {
	margin-bottom: 20rpx;
	text-align: right;
	font-size: 28rpx;
}

.amount {
	font-size: 36rpx;
	font-weight: 600;
	color: #5c495f;
	margin-left: 10rpx;
}

.action-buttons {
	display: flex;
	justify-content: space-between;
	gap: 20rpx;
	padding: 10rpx 0;
}

.cancel-btn,
.confirm-btn {
	flex: 1;
	height: 80rpx;
	line-height: 80rpx;
	border-radius: 40rpx;
	font-size: 28rpx;
	border: none;
}

.cancel-btn {
	background: #f5f5f5;
	color: #666;
}

.confirm-btn {
	background: #fe4e88;
	color: #fff;
}

.cancel-btn:active,
.confirm-btn:active {
	transform: scale(0.98);
	opacity: 0.9;
}

/* 移除按钮的默认样式 */
button {
	margin: 0;
	padding: 0;
}

button::after {
	border: none;
}

/* 店铺信息样式 */
.shop-info {
	padding: 30rpx;
	background: #fff;
	border-radius: 20rpx;
	margin: 20rpx;
	box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.shop-name {
	font-size: 36rpx;
	font-weight: bold;
	color: #333;
	margin-bottom: 12rpx;
	margin-left: 10px;
}

.location {
	display: flex;
	align-items: center;
	font-size: 24rpx;
	color: #999;
}

.shop-header {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
}

.clear-btn {
	display: flex;
	align-items: center;
	gap: 4rpx;
	padding: 12rpx 20rpx;
	border-radius: 26rpx;
	background: #f5f5f5;
	transition: all 0.2s;
}

.clear-btn text {
	font-size: 24rpx;
	color: #999;
}

.clear-btn:active {
	opacity: 0.8;
	transform: scale(0.95);
}

/* 在 style 中添加支付弹窗样式 */
.payment-popup {
	width: 600rpx;
	background: #fff;
	border-radius: 20rpx;
	overflow: hidden;
}

.qr-container {
	padding: 40rpx;
	display: flex;
	justify-content: center;
	align-items: center;
}

.qr-code {
	width: 400rpx;
	height: 400rpx;
}

.payment-actions {
	display: flex;
	padding: 30rpx;
	gap: 20rpx;
}

.cancel-pay-btn,
.confirm-pay-btn {
	flex: 1;
	height: 80rpx;
	line-height: 80rpx;
	border-radius: 40rpx;
	font-size: 28rpx;
	border: none;
}

.cancel-pay-btn {
	background: #f5f5f5;
	color: #666;
}

.confirm-pay-btn {
	background: #fe4e88;
	color: #fff;
}

.cancel-pay-btn:active,
.confirm-pay-btn:active {
	transform: scale(0.98);
	opacity: 0.9;
}
</style>