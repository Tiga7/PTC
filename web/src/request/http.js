import axios from "axios"; // 引入 axios
import qs from "qs"; // 引入 qs 模块，用来序列化 post 类型的数据，后面会提到
import router from "@/router/index";
import { Message } from "view-ui-plus";

axios.defaults.baseURL = "http://localhost:3030";
axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";
axios.defaults.timeout = 5000;

//请求拦截器
axios.interceptors.request.use(
	(config) => {
		// 每次发送请求之前判断 vuex 中是否存在 token
		// 如果存在，则统一在 http 请求的 header 都加上 token，这样后台根据 token 判断你的登录情况
		// 即使本地存在 token，也有可能 token 是过期的，所以在响应拦截器中要对返回状态进行判断

		if (localStorage.getItem("jwt_token") != null) {
			const token = "Bearer " + localStorage.getItem("jwt_token");
			token && (config.headers.Authorization = token);
		}
		return config;
	},
	(error) => {
		return Promise.error(error);
	}
);

// 响应拦截器
axios.interceptors.response.use(
	(response) => {
		// 如果返回的状态码为 200，说明接口请求成功，可以正常拿到数据
		// 否则的话抛出错误
		if (response.status === 200) {
			return Promise.resolve(response);
		} else {
			return Promise.reject(response);
		}
	},
	// 服务器状态码不是 2 开头的的情况
	// 这里可以跟你们的后台开发人员协商好统一的错误状态码
	// 然后根据返回的状态码进行一些操作，例如登录过期提示，错误提示等等
	// 下面列举几个常见的操作，其他需求可自行扩展
	(error) => {
		if (error.response.status) {
			switch (error.response.status) {
				// 401: 未登录
				// 未登录则跳转登录页面，并携带当前页面的路径
				// 在登录成功后返回当前页面，这一步需要在登录页操作。
				case 401:
					Message.info("用户未登录,即将跳到登录页面");
					router.replace({
						path: "/user/login/",
						query: {
							redirect: router.currentRoute.fullPath,
						},
					});
					break;

				// 403 token过期
				// 登录过期对用户进行提示
				// 清除本地token和清空vuex中token对象
				// 跳转登录页面
				case 403:
					//提示信息
					Message.info("账户未激活或没有权限,即将跳到登录页面");

					// 清除token
					localStorage.removeItem("token");

					// 跳转登录页面，并将要浏览的页面fullPath传过去，登录成功后跳转需要访问的页面
					setTimeout(() => {
						router.replace({
							path: "/user/login/",
							query: {
								redirect: router.currentRoute.fullPath,
							},
						});
					}, 1000);
					break;

				// 404请求不存在
				case 404:
					Message.info("资源路径不存在,即将跳到登录页面");
					setTimeout(() => {
						router.replace({
							path: "/user/login/",
							query: {
								redirect: router.currentRoute.fullPath,
							},
						});
					}, 1000);
					break;
				// 其他错误，直接抛出错误提示
				default:
					Message.info(error.response.data.message);
			}
			return Promise.reject(error.response);
		}
	}
);

//get方法的params是一组键值对
export function get(url, params) {
	return new Promise((resolve, reject) => {
		axios
			.get(url, {
				params: params,
			})
			.then((res) => {
				resolve(res.data);
			})
			.catch((err) => {
				reject(err.data);
			});
	});
}

//post方法的data是多组键值对
export function post(url, data) {
	return new Promise((resolve, reject) => {
		axios({
			method: "post",
			url: url,
			data: qs.stringify(data),
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
		})
			.then((res) => {
				resolve(res.data);
			})
			.catch((err) => {
				reject(err.data);
			});
	});
}

export function put_api(url, data) {
	return new Promise((resolve, reject) => {
		axios({
			method: "put",
			url: url,
			data: qs.stringify(data),
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
		})
			.then((res) => {
				resolve(res.data);
			})
			.catch((err) => {
				reject(err.data);
			});
	});
}

export function delete_api(url, data) {
	return new Promise((resolve, reject) => {
		axios({
			method: "delete",
			url: url,
			data: qs.stringify(data),
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
		})
			.then((res) => {
				resolve(res.data);
			})
			.catch((err) => {
				reject(err.data);
			});
	});
}
