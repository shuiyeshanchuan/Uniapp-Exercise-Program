<template>
	<view>
		<!-- 通过插值表达式来进行数据展示 -->
		当前text传递的参数为：{{text}}
		当前ID值为：{{id}}
	</view>
</template>

<script>
	export default {
		data() {
			return {
				//定义接收变量
				text: "",
				id: ""
			}
		},
		onLoad(e) {
			console.log(e)
			this.text = e.text
			this.id = e.id
		},
		methods: {

		}
	}
</script>

<style>

</style>
