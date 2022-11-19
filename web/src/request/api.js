import { post, put_api, delete_api } from "./http.js";

export const register_api = (data) => post("/api/user/register/", data);
export const login_api = (data) => post("/api/user/token/", data);
export const get_info_api = (data) => post("/api/user/info/", data);
export const edit_info_api = (data) => post("/api/user/edit/", data);

export const like_api = (data) => post("/api/like/add/", data);

export const room_get_list = (data) => post("/api/room/list/", data);
export const room_post_api = (data) => post("/api/room/", data);
export const room_put_api = (data) => put_api("/api/room/", data);
export const room_delete_api = (data) => delete_api("/api/room/", data);

export const schedule_get_list = (data) => post("/api/room/schedule/", data);

export const get_colletion_api = (data) => post("/api/collection/list/", data);
export const add_colletion_api = (data) => post("/api/collection/add/", data);
// export const remove_colletion_api = (data) => delete_api("/api/collection/", data);

// export const record_getlist_api = (data) => get("/api/record/", data);
// export const record_addrecord_api = (data) => post("/api/record/", data);

import request from "./test";
export const remove_colletion_api = (data) => request.delete("/api/collection/", data);

// // 可以加以改造;
export default {
	// login: (data) => {
	// 	return request.post("/api/user/token/", data);
	// },
	//......
	record_getlist: (data) => {
		return request.post("/api/record/list/", data);
	},

	comment_get_list: (data) => {
		return request.post("/api/comment/list/", data);
	},
	comment_add: (data) => {
		return request.post("/api/comment", data);
	},
	comment_remove: (data) => {
		return request.delete("/api/comment", data);
	},
};
