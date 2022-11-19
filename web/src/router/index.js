import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

import store from "../store/index";

const routes = [
	{
		path: "/home",
		name: "home",
		component: HomeView,
		//额外的信息  可以存放一些授权信息
		meta: {
			//是否为admin
			is_admin: false,
			//是否登录
			requestAuth: false,
		},
	},
	{
		path: "/",
		name: "home1",
		component: HomeView,
		meta: {
			is_admin: false,
			requestAuth: false,
		},
	},
	{
		path: "/user/register/",
		name: "register_index",
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () => import(/* webpackChunkName: "about" */ "../views/user/RegisterView.vue"),
		meta: {
			is_admin: false,
			requestAuth: false,
		},
	},
	{
		path: "/user/login/",
		name: "login_index",
		component: () => import("../views/user/LoginView.vue"),
		meta: {
			is_admin: false,
			requestAuth: false,
		},
	},
	{
		path: "/user/info/",
		name: "info_index",
		component: () => import("../views/user/InfoView.vue"),
		meta: {
			is_admin: false,
			requestAuth: true,
		},
	},
	{
		path: "/collection/",
		name: "collection_index",
		component: () => import("../views/collection/CollectionView.vue"),
		meta: {
			is_admin: false,
			requestAuth: true,
		},
	},
	{
		path: "/record/",
		name: "record_index",
		component: () => import("../views/record/RecordList.vue"),
		meta: {
			is_admin: false,
			requestAuth: true,
		},
	},
	{
		path: "/comment/:record_id/",
		name: "comment_index",
		component: () => import("../views/comment/CommentView.vue"),
		meta: {
			is_admin: false,
			requestAuth: true,
		},
	},
	{
		path: "/like/",
		name: "like_index",
		component: () => import("../views/like/LikeView.vue"),
		meta: {
			is_admin: false,
			requestAuth: false,
		},
	},
	{
		path: "/room/",
		name: "room_index",
		component: () => import("../views/room/RoomView.vue"),
		meta: {
			is_admin: false,
			requestAuth: false,
		},
	},
	{
		path: "/404/",
		name: "404",
		component: () => import("../views/error/ErrorView.vue"),
	},
	{
		path: "/:catchAll(.*)",
		redirect: "/404/",
	},
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

// 前端页面的授权控制 利用每个页面的额外信息进行授权  to:去哪个页面 from:从哪个页面  next:控制去哪个页面
router.beforeEach((to, from, next) => {
	if (to.meta.requestAuth && !store.state.user.is_login) {
		next({ name: "login_index" });
	} else {
		next();
	}
});

export default router;
