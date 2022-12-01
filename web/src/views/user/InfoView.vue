<template>
    <CardViewVue>
        <Row justify="center" :gutter="16">
            <Col span="12">
            <Card>
                <template #title>
                    <p>
                        修改基础信息
                    </p>
                </template>
                <div class="edit-info">
                    <Form label-position="right" :label-width="90">
                        <FormItem label="">
                            <div class="info-name2">
                                <p>Hi , {{ username }}</p>
                            </div>
                        </FormItem>
                        <FormItem label="姓名:">
                            <Input v-model="new_info.username" size="large" maxlength="9"></Input>
                        </FormItem>
                        <FormItem label="性别:">
                            <RadioGroup v-model="new_info.gender">
                                <Radio label="男" border></Radio>
                                <Radio label="女" border></Radio>
                            </RadioGroup>
                        </FormItem>
                        <FormItem>
                            <Button type="primary" style="width: 80%; text-align: center;"
                                @click="edit_info">保存</Button>
                        </FormItem>
                    </Form>
                </div>
            </Card>
            </Col>

            <Col span="12">
            <Card>
                <template #title>
                    <p>
                        修改密码
                    </p>
                </template>
                <div class="edit-info">
                    <Form label-position="right" :label-width="90">
                        <FormItem label="">
                            <div class="info-name2">
                                <p>Hi , {{ username }}</p>
                            </div>
                        </FormItem>
                        <FormItem label="旧密码:">
                            <Input v-model="new_info.password" size="large" maxlength="20" type="password"></Input>
                        </FormItem>
                        <FormItem label="新密码:">
                            <Input v-model="new_info.new_password" size="large" maxlength="20" type="password"></Input>
                        </FormItem>
                        <FormItem label="确认新密码:">
                            <Input v-model="new_info.confirm_password" size="large" maxlength="20"
                                type="password"></Input>
                        </FormItem>
                        <FormItem>
                            <Button type="primary" style="width: 80%; text-align: center;"
                                @click="edit_password">确认修改保存</Button>
                        </FormItem>
                    </Form>
                </div>

            </Card>
            </Col>
        </Row>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import { useStore } from 'vuex';
import { reactive, ref } from 'vue'
import { Message } from 'view-ui-plus';
import api from "@/request/api";

import router from '@/router';
export default {
    components: {
        CardViewVue
    },
    setup() {
        const store = useStore();
        const username = ref(store.state.user.username);
        const new_info = reactive({
            username: '',
            gender: '',
            password: '',
            new_password: '',
            confirm_password: '',
        })


        const edit_info = () => {
            if (new_info.username === '') {
                Message.info("姓名不能为空");
                return;
            }
            if (new_info.gender === '') {
                Message.info("请选择性别");
                return;
            }
            api.edit_info({
                id: store.state.user.id,
                username: new_info.username,
                gender: new_info.gender,
            })
                .then(function (response) {
                    if (response.code == 200) {
                        Message.info('修改基础信息成功');
                        username.value = new_info.username;
                        store.dispatch("getinfo",
                            {
                                success() {
                                },
                                error() {
                                }
                            });
                    }
                })
                .catch(function (error) {
                    console.log(error)
                    Message.info('出现未知错误,请重试');
                })

        }
        const edit_password = () => {
            if (new_info.password === '' || new_info.new_password === '' || new_info.confirm_password === '') {
                Message.info("密码不能为空");
                return;
            }
            if (new_info.password === new_info.new_password) {
                Message.info("新密码和旧密码不能一样");
                return;
            }
            if (new_info.confirm_password !== new_info.new_password) {
                Message.info("两个新密码不一致");
                return;
            }

            api.edit_info({
                id: store.state.user.id,
                password: new_info.password,
                new_password: new_info.new_password,
                confirm_password: new_info.confirm_password
            })
                .then(function (response) {
                    if (response.code == 200) {
                        Message.info(response.data + ',请重新登录');
                        localStorage.removeItem("jwt_token")
                        setTimeout(() => {
                            router.push({ name: "login_index" });
                        }, 1000);
                    }
                })
                .catch(function () {
                    Message.info('出现未知错误,请重试');
                })
        }

        return {
            username,
            new_info,
            edit_info,
            edit_password
        }
    }
}
</script>

<style lang="scss" scoped>
.edit-info {
    width: 400px !important;
    margin: 0 auto;
}

.info {
    text-align: center;
    padding: 35px 0;
}

.info-name {
    margin: 15px 0 10px;
    font-size: 24px;
    font-weight: 500;
    color: #262626;
}

.info-name2 {
    margin: 8px 0 0px;
    font-size: 24px;
    font-weight: 500;
    color: #262626;
}
</style>