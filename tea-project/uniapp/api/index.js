import request from './request.js'


const baseUrl = ''

//index.vue
export function queryImageByType(id) {
	return request({
		url: baseUrl + '/image/list/' + id,
		method: 'GET',
		data: {}
	})
}


//product.vue
export function getTypes() {
	return request({
		url: baseUrl + '/type/getTypes',
		method: 'GET',
		data: {}
	})
}

export function queryProductByType(id) {
	return request({
		url: baseUrl + '/product/queryProductByType?id=' + id,
		method: 'GET',
		data: {}
	})
}

// 搜索商品
export function searchProducts(keyword) {
	return request({
		url: baseUrl + '/product/search',
		method: 'GET',
		data: {
			keyword: keyword  // 确保参数名称为 'keyword'
		}
	})
}

export function addCart(data) {
	return request({
		url: baseUrl + '/cart/addCart?number=1&pid=' + data,
		method: 'GET',
		data: {}
	})
}



//myCart.vue


export function myCart() {
	return request({
		url: baseUrl + '/cart/queryCartInfo',
		method: 'GET',
		data: {}
	})
}

export function deleteCart(id) {
	return request({
		url: baseUrl + '/cart',
		method: 'DELETE',
		data: [id]
	})
}

// 清空购物车
export function clearCart() {
	return request({
		url: '/cart/clear',
		method: 'POST'
	})
}

export function changeNumber(id, cmd) {
	return request({
		url: baseUrl + '/cart/changeNumber',
		method: 'GET',
		data: {
			id: id,
			cmd: cmd
		}
	})
}

export function addOrder() {
	return request({
		url: baseUrl + '/userOrder/addOrder',
		method: 'GET',
		data: {

		}
	})
}


//my.vue


export function queryOrder() {
	return request({
		url: baseUrl + '/userOrder/queryOrder',
		method: 'GET',
		data: {
			// ostate: ostate
		}
	})
}

export function queryUserInfo() {
	return request({
		url: baseUrl + "/sysUser/queryUserInfo",
		method: 'GET',
		data: {}
	})
}

// 更新订单状态
export function updateOrderState(data) {
	return request({
		url: baseUrl + '/userOrder/updateState',
		method: 'POST',
		data: data
	})
}

//登录
export function login(data) {
	return request({
		url: '/sysUser/login',
		method: 'POST',
		data: data
	})
}


//注册
export function register(data) {
	return request({
		url: '/sysUser/register',
		method: 'POST',
		data: data
	})
}




