import { get, post } from "./http.js";

export const register_api = (data) => post("/api/user/register/", data);
export const login_api = (data) => post("/api/user/token/", data);
export const get_info_api = (data) => post("/api/user/info/", data);
export const edit_info_api = (data) => post("/api/user/edit/", data);

export const like_api = (data) => post("/api/like/add/", data);

export const record_getlist_api = (data) => get("/api/record/", data);
export const record_addrecord_api = (data) => post("/api/record/", data);
