const url_all = {
	'DEV': 'http://localhost:1000',
	'BUILD': 'http://localhost:1000'
}
let server_url = '';
// let token = '';
process.env.NODE_ENV === 'development' ? server_url = url_all['DEV'] : server_url = url_all['BUILD'];

function service(options = {}) {

	options.url = `${server_url}${options.url}`;

	// options.params = options.params;

	// console.log("111111111111111111")
	// if (options.params == {}) {
	// 	options.data = options.data;
	// }
	let token = ""
	try {
		//https://m.php.cn/article/465799.html解决手机端缓存失效问题
		const value = uni.getStorageSync('token');

		if (value) {
			console.log(value);
			token = value
		}

	} catch (e) {

		// error
	};

	options.header = {
		'token': token
	};

	return new Promise((resolved, rejected) => {
		options.success = (response) => {
			let res = response.data
			console.log(res.code)
			if (res.code === 200) {
				resolved(res);
			} else if (res.code === 500) {
				uni.showToast({
					title: res.msg,
					duration: 2000,
					icon: 'none'
				});
			} else {
				if (res.msg != null) {
					uni.showToast({
						title: res.msg,
						duration: 2000,
						icon: 'none'
					});

					uni.navigateTo({
						url: "/pages/login/login"
					})

				}
				console.log(res)
				rejected(res);
			}
		}
		options.fail = (err) => {
			//Do something with request error
			rejected(err)
		}
		uni.request(options);

	});
}
export default service;