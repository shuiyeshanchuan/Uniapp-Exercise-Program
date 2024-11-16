<template>
	<view class="custom-tab-bar">
		<view v-for="(item, index) in tabList" :key="index" class="tab-item" @click="switchTab(item.pagePath)">
			<text class="tab-text" :class="{ active: current === index }">{{ item.text }}</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				current: 0,
				tabList: [{
						text: "喝",
						pagePath: "/pages/index/index"
					},
					{
						text: "选",
						pagePath: "/pages/product/product"
					},
					{
						text: "买",
						pagePath: "/pages/myCart/myCart"
					},
					{
						text: "看",
						pagePath: "/pages/my/my"
					}
				]
			}
		},
		methods: {
			switchTab(path) {
				uni.switchTab({
					url: path
				})
			}
		}
	}
</script>

<style>
	.custom-tab-bar {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		height: 60px;
		background: rgba(86, 118, 94, 0.9);
		backdrop-filter: blur(10px);
		display: flex;
		justify-content: space-around;
		align-items: center;
		box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.08);
	}

	.tab-item {
		flex: 1;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.tab-text {
		color: #CBDA99;
		font-family: KaiTi, "楷体", "楷体_GB2312";
		font-weight: bold;
		font-size: 14px;
	}

	.tab-text.active {
		color: #ffffff;
	}
</style>