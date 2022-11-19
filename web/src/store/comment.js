export default {
	state: {
		record: {
			id: "",
			userId: "",
			roomId: "",
			modifyTime: "",
			description: "",
			username: "",
			photo: "",
			buildingName: "",
			roomName: "",
		},
		comment_time: 0,
	},
	//获取state的信息
	getters: {},
	//直接对state进行修改
	//调用mutations 里的函数用store.commit()  同步操作 将数据写到本地缓存中
	mutations: {
		updateRecord(state, record) {
			state.record = record;
		},
		updateCommentTime(state) {
			if (state.comment_time < 10) {
				state.comment_time++;
			}
		},
	},
	//不能直接对本地的state进行修改 可以context.commit 调用mutations进行修改
	//调用 actions 里的函数用store.dispatch() 异步操作 一般写访问后台的操作 到最后也会调用mutations的函数
	actions: {
		//第一个参数可以调用mutations里的方法   第二个参数是传入的参数
	},
	modules: {},
};
