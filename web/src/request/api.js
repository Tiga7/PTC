import { post } from "./http.js";

export const register_api = (data) => post("/api/user/register/", data);
export const login_api = (data) => post("/api/user/token/", data);
export const get_info_api = (data) => post("/api/user/info/", data);
