<template>
	<view class="container">
		<!-- 搜索区域 -->
		<view class="search-box">
			<view class="search-input">
				<image src="../../static/search.png" class="search-icon"></image>
				<input type="text" v-model="searchKey" placeholder="喜欢喝？" @input="debounceSearch" />
				<view class="search-btn" v-if="searchKey" @click="clearSearch">取消</view>
			</view>
		</view>

		<!-- 搜索结果区域 -->
		<view class="search-result" v-if="isSearching">
			<view v-if="searchResults.length === 0" class="no-result">
				暂无相关商品
			</view>
			<view class="result-item" v-for="item in searchResults" :key="item.id">
				<image :src="item.pimageurl" mode="aspectFill"></image>
				<view class="item-info">
					<view class="item-name">{{ item.pname }}</view>
					<view class="item-desc">{{ item.tintroduce }}</view>
					<view class="item-bottom">
						<view class="item-price">￥{{ item.tprice }}</view>
						<view @click.stop="addCart(item)" class="cart-wrapper">
							<uni-icons type="cart" size="24" color="#9370DB" class="cart-icon"></uni-icons>
						</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 主要内容区域 -->
		<view v-else class="main-content">
			<!-- 店铺信息区域 -->
			<view class="shop-info">
				<view class="shop-name">北极科考站店</view>
				<view class="location">
					<uni-icons type="location" size="12" color="#999"></uni-icons>
					<text>距离您{{ distance }}</text>
				</view>
			</view>

			<!-- 商品分类和列表区域 -->
			<view class="product-container">
				<view class="left-list">
					<view v-for="(item, i) in typeList" :key="item.id" :class="i === index ? 'current' : ''"
						@click="selectTab(i, item.id)">
						<image :src="item.timageurl" mode=""></image>
						<view>{{ item.typename }}</view>
					</view>
				</view>

				<view class="right-list">
					<view v-for="item in productList" :key="item.id" class="product-item">
						<image class="product-image" :src="item.pimageurl" mode="aspectFill"></image>
						<view class="product-info">
							<view class="product-title">{{ item.pname }}</view>
							<view class="product-desc">{{ item.tintroduce }}</view>
							<view class="product-bottom">
								<view class="price-info">
									￥<text class="price">16</text>
								</view>
								<view @click.stop="addCart(item)" class="cart-wrapper">
									<uni-icons type="cart" size="24" color="#9370DB" class="cart-icon"></uni-icons>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
import {
	queryImageByType,
	getTypes,
	queryProductByType,
	addCart,
	searchProducts
} from "@/api/index";

export default {
	data() {
		return {
			//当前索引值
			index: 0,
			//定义图片数组
			swiperList: [],
			typeList: [],
			productList: [],
			searchKey: '',
			searchResults: [],
			isSearching: false,
			timer: null,
			distance: '计算中...',
			shopLocation: {
				latitude: 90, // 北极点纬度
				longitude: 0  // 北极点经度
			}
		}
	},
	onLoad() {
		this.getSwiperImageList()
		//函数调用
		this.getTypeList()

		this.queryProductByType(1)
		this.calculateDistance()
	},
	methods: {
		// 轮播图获取接口
		getSwiperImageList() {

			queryImageByType(3).then(res => {
				console.log(res.data)
				this.swiperList = res.data
			})


		},

		//分类获取接口
		getTypeList() {

			getTypes().then(res => {
				this.typeList = res.data
			})


		},
		//切换不同的分类
		selectTab(currentIndex, id) {
			this.index = currentIndex
			this.queryProductByType(id)
		},
		//切换
		queryProductByType(productId) {

			queryProductByType(productId).then(res => {
				this.productList = res.data
			})

		},
		addCart(product) {
			console.log("购物车")
			console.log(product)
			// http://47.96.80.123:8000/cart/addCart?pid=3&number=1&token=63e3423e4185eeb0d9f19d32479bd785&tprice=20

			addCart(product.id + "&tprice=" + product.tprice).then(res => {
				console.log(res)
				if (res.code === 200) {
					uni.showToast({
						title: "添加成功",
						duration: 2000,
					})
				} else {
					uni.showToast({
						title: res.data.msg,
						duration: 2000,
						icon: "error"
					})
				}
			})
		},
		debounceSearch() {
			if (this.timer) clearTimeout(this.timer);

			if (!this.searchKey.trim()) {
				this.clearSearch();
				return;
			}

			this.timer = setTimeout(() => {
				this.handleSearch();
			}, 300);
		},
		handleSearch() {
			this.isSearching = true;
			searchProducts(this.searchKey).then(res => {
				if (res.code === 200) {
					this.searchResults = res.data;
				} else {
					uni.showToast({
						title: '搜索失败',
						icon: 'none'
					});
				}
			}).catch(err => {
				uni.showToast({
					title: '网络错误',
					icon: 'none'
				});
			});
		},
		clearSearch() {
			this.searchKey = '';
			this.searchResults = [];
			this.isSearching = false;
		},
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
		}
	}
}
</script>

<style>
/* 整体容器样式 */
.container {
	height: 100vh;
	display: flex;
	flex-direction: column;
	background-color: #f8f8f8;
}

/* 主要内容区域 */
.main-content {
	flex: 1;
	display: flex;
	flex-direction: column;
	overflow: hidden;
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

/* 商品分类和列表容器 */
.product-container {
	flex: 1;
	display: flex;
	overflow: hidden;
	margin-top: 20rpx;
}

/* 左侧分类列表 */
.left-list {
	width: 180rpx;
	height: 100%;
	background: #f5f5f5;
	overflow-y: auto;
}

.left-list>view {
	padding: 30rpx 20rpx;
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 10rpx;
}

.left-list image {
	width: 60rpx;
	height: 60rpx;
}

.left-list view view {
	font-size: 24rpx;
	color: #666;
}

/* 右侧商品列表 */
.right-list {
	flex: 1;
	padding: 20rpx;
	overflow-y: auto;
	padding-bottom: calc(100rpx + env(safe-area-inset-bottom));
}

.product-item {
	background: #fff;
	border-radius: 15rpx;
	margin-bottom: 20rpx;
	padding: 20rpx;
	display: flex;
	gap: 20rpx;
	box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.product-image {
	width: 180rpx;
	height: 180rpx;
	border-radius: 10rpx;
	object-fit: cover;
}

.product-info {
	flex: 1;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.product-title {
	font-size: 28rpx;
	font-weight: bold;
	color: #333;
}

.product-desc {
	font-size: 24rpx;
	color: #999;
	margin: 10rpx 0;
}

.product-bottom {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.price-info {
	color: #ff2c79;
	font-size: 26rpx;
}

.price {
	font-size: 32rpx;
	font-weight: bold;
}

.cart-wrapper {
	padding: 10rpx;
}

.cart-wrapper:active {
	transform: scale(0.95);
}

/* 当前选中分类样式 */
.current {
	background: #fff;
	position: relative;
}

.current::before {
	content: '';
	position: absolute;
	left: 0;
	top: 50%;
	transform: translateY(-50%);
	width: 6rpx;
	height: 36rpx;
	background: #9370DB;
}

.current view {
	color: #9370DB !important;
}

/* 添加搜索相关样式 */
.search-box {
	padding: 20rpx 30rpx;
	background: #fff;
}

.search-input {
	display: flex;
	align-items: center;
	background: #f5f5f5;
	padding: 15rpx 20rpx;
	border-radius: 35rpx;
}

.search-icon {
	width: 40rpx;
	height: 40rpx;
	margin-right: 10rpx;
}

.search-input input {
	flex: 1;
	font-size: 28rpx;
}

.search-btn {
	font-size: 28rpx;
	color: #666;
	margin-left: 20rpx;
}

.search-result {
	position: absolute;
	top: 180rpx;
	left: 0;
	right: 0;
	bottom: 0;
	background: #fff;
	z-index: 999;
	padding: 20rpx;
	padding-bottom: calc(100rpx + env(safe-area-inset-bottom));
	overflow-y: auto;
}

.no-result {
	text-align: center;
	color: #999;
	padding: 40rpx 0;
}

.result-item {
	display: flex;
	padding: 20rpx;
	border-bottom: 1rpx solid #f5f5f5;
}

.result-item image {
	width: 160rpx;
	height: 160rpx;
	border-radius: 8rpx;
	margin-right: 20rpx;
}

.item-info {
	flex: 1;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.item-name {
	font-size: 30rpx;
	font-weight: bold;
}

.item-desc {
	font-size: 24rpx;
	color: #999;
	margin: 10rpx 0;
}

.item-bottom {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.item-price {
	color: orange;
	font-size: 32rpx;
	font-weight: bold;
}

.item-bottom .cart {
	width: 50rpx;
	height: 50rpx;
}

.shop-info {
	padding: 20rpx 0;
}

/* .shop-name {
	font-size: 36rpx;
	font-weight: bold;
	color: #333;
	margin-bottom: 12rpx;
	display: flex;
	align-items: center;
} */

.location {
	display: flex;
	align-items: center;
	font-size: 24rpx;
	color: #666;
}

.location text {
	margin-left: 6rpx;
	color: #999;
}

/* 确保整个容器可以正常滚动 */
.container {
	height: 100vh;
	box-sizing: border-box;
	padding-bottom: env(safe-area-inset-bottom);
	display: flex;
	flex-direction: column;
}

.list {
	flex: 1;
	overflow: hidden;
	margin-top: 25rpx;
	display: flex;
}
</style>
