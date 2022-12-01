<template>
    <Row>
        <Col span="2">
        </Col>
        <Col span="20">
        <Menu mode="horizontal" theme="light" :open-names="['1']" accordion>

            <MenuItem name="6" to="/home">
            <Icon type="md-checkmark" />
            HOME
            </MenuItem>
            <MenuItem name="4" to="/record/">
            <Icon type="ios-stats" />
            交流社区
            </MenuItem>
            <MenuItem name="1" to="/collection/">
            <Icon type="ios-paper" />
            收藏
            </MenuItem>
            <MenuItem name="5" to="/room/">
            <Icon type="ios-stats" />
            自习室
            </MenuItem>

            <MenuItem name="3" :to="{ name: 'shechule_index' }">
            <Icon type="ios-stats" />
            课表管理
            </MenuItem>

            <Submenu name="2" style="float: right;" v-if="$store.state.user.username !== ''">
                <template #title>
                    <Icon type="md-person" />
                    {{ $store.state.user.username }}
                </template>
                <MenuItem name="2-3" to="/user/info/">个人信息</MenuItem>
                <MenuItem name="2-4" @click="logout">退出</MenuItem>
            </Submenu>
            <Submenu name="2" style="float: right;" v-else>
                <template #title>
                    <Icon type="md-person" />用户管理
                </template>
                <MenuItem name="2-1" to="/user/login">登录</MenuItem>
                <MenuItem name="2-2" to="/user/register">激活</MenuItem>

            </Submenu>
        </Menu>
        </Col>
        <Col span="2">
        </Col>
    </Row>

</template>
<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import router from '@/router';
export default {
    components: {

    },
    setup() {
        const store = useStore();
        const cc = ref(1)
        const activeIndex = ref('1')
        const handleSelect = () => {
            cc.value++;
        }

        const token = localStorage.getItem("jwt_token");
        if (token) {
            store.commit("updateToken", token);
            store.dispatch("getinfo", {
                success() {
                    router.push({ name: "home" });
                    store.commit("updataLoadingOver", true)
                },
                error() {
                    store.commit("updataLoadingOver", false)
                    // router.push({ name: "login_index" });
                }
            })
        } else {
            store.commit("updataLoadingOver", false)
        }

        const logout = () => {
            store.dispatch("logout");
        }
        return {
            cc,
            activeIndex,
            handleSelect,
            logout
        }

    },
}
</script>

<style lang="scss" scoped>
.flex-grow {
    flex-grow: 1;
}

.demo-tabs>.el-tabs__content {
    padding: 32px;
    color: #6b778c;
    font-size: 32px;
    font-weight: 600;
}
</style>