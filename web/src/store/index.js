import { createStore } from "vuex";
import ModuleUser from "./user";
import ModuleComment from "./comment";

export default createStore({
	state: {},
	getters: {},
	mutations: {},
	actions: {},
	modules: {
		user: ModuleUser,
		comment: ModuleComment,
	},
});
