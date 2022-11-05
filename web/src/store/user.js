import $ from "jquery";

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
	getters: {},
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
	//调用 actions 里的函数用store.dispatch() 异步操作 一般写访问后台的操作 到最后也会调用mutations的函数
	actions: {
		//第一个参数可以调用mutations里的方法   第二个参数是传入的参数
		login(context, data) {
			$.ajax({
				url: "http://127.0.0.1:3030/api/user/token/",
				type: "post",
				data: {
					sno: data.sno,
					password: data.password,
				},
				success(resp) {
					if (resp.code == 1000) {
						if (resp.data.result == "success") {
							localStorage.setItem("jwt_token", resp.data.token);
							context.commit("updateToken", resp.data.token);
							//回调data里的函数
							data.success(resp.data);
						} else {
							data.error(resp.data);
						}
					} else {
						data.error(resp);
					}
				},
				error(resp) {
					data.error(resp);
				},
			});
		},
		getinfo(context, data) {
			$.ajax({
				url: "http://localhost:3030/api/user/info/",
				type: "get",
				headers: {
					Authorization: "Bearer " + context.state.token,
				},
				success(resp) {
					if (resp.code == 1000) {
						context.commit("updateUser", {
							...resp.data,
							is_login: true,
							is_admin: resp.data.isAdmin == 1 ? true : false,
						});
						data.success(resp);
					} else {
						data.error(resp);
					}
				},
				error(resp) {
					data.error(resp);
				},
			});
		},
		logout(context) {
			localStorage.removeItem("jwt_token");
			context.commit("logout");
		},
	},
	modules: {},
};
