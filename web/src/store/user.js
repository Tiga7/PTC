import { get_info_api, login_api } from "@/request/api";

export default {
	state: {
		id: "",
		sno: "",
		username: "",
		photo: "",
		is_login: false,
		token: "",

		is_admin: false, //普通用户还是amdin

		loading_info_over: false, //是否正在从服务端获取信息 如果没有获取某些信息 可能会影响样式的显示
	},
	//获取state的信息
	getters: {},
	//直接对state进行修改
	//调用mutations 里的函数用store.commit()  同步操作 将数据写到本地缓存中
	mutations: {
		updateUser(state, user) {
			state.id = user.id;
			state.sno = user.sno;
			state.username = user.username;
			state.photo = user.photo;
			state.is_admin = user.is_admin;
			state.is_login = user.is_login;
		},
		updateToken(state, token) {
			state.token = token;
		},
		logout(state) {
			state.id = "";
			state.sno = "";
			state.username = "";
			state.photo = "";
			state.is_login = false;
			state.token = "";
		},
		updataLoadingOver(state, loading_info_over) {
			state.loading_info_over = loading_info_over;
		},
	},
	//不能直接对本地的state进行修改 可以context.commit 调用mutations进行修改
	//调用 actions 里的函数用store.dispatch() 异步操作 一般写访问后台的操作 到最后也会调用mutations的函数
	actions: {
		//第一个参数可以调用mutations里的方法   第二个参数是传入的参数
		login(context, data) {
			login_api({
				sno: data.sno,
				password: data.password,
			})
				.then(function (response) {
					if (response.code == 200) {
						// console.log(response);
						localStorage.setItem("jwt_token", response.data.token);
						context.commit("updateToken", response.data.token);
						data.success(response);
					} else {
						data.error(response);
					}
				})
				.catch(function (error) {
					data.error(error);
				});
		},
		getinfo(context, data) {
			get_info_api()
				.then(function (response) {
					if (response.code == 200) {
						context.commit("updateUser", {
							...response.data,
							is_login: true,
							is_admin: response.data.isAdmin == 1 ? true : false,
						});
						data.success(response);
					} else {
						data.error(response);
					}
				})
				.catch(function (error) {
					data.error(error);
				});
		},
		logout(context) {
			localStorage.removeItem("jwt_token");
			context.commit("logout");
		},
	},
	modules: {},
};
