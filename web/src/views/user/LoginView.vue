<template>
    <CardViewVue>
        <div class="show-login">
            <Login @on-submit="login">
                <UserName name="sno" maxlength="9" placeholder="请输入学号" />
                <Password name="password" maxlength="13" placeholder="请输入密码" />
                <Captcha name="enter_captcha" maxlength="6" @on-get-captcha="get_new_captcha">
                    <template #text>
                        {{ captcha }}
                    </template>
                </Captcha>
                <FormItem>
                    <Alert type="error" v-if="error_message !== ''" show-icon>
                        <template #desc>
                            {{ error_message }}
                        </template>
                    </Alert>
                </FormItem>
                <Submit />
            </Login>
        </div>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import { useStore } from 'vuex'
import { ref } from 'vue';
import router from '@/router/index';
export default {
    components: {
        CardViewVue,
    },
    setup() {
        const store = useStore();
        store;
        const error_message = ref('');
        let captcha = ref("");
        const get_new_captcha = () => {
            captcha.value = 111111;
            // captcha.value = Math.floor(Math.random() * (999999 - 100000)) + 100000;
        }
        get_new_captcha()



        const login = (valid, { sno, password, enter_captcha }) => {

            if (parseInt(enter_captcha) === parseInt(captcha.value)) {
                error_message.value = '';
                store.dispatch("login",
                    {
                        sno,
                        password,
                        success() {
                            router.push({ name: "home" })
                            store.dispatch("getinfo",
                                {
                                    success() {
                                        router.push({ name: "home" })
                                    },
                                });
                        },
                        error(error) {
                            error_message.value = error.message;
                            // console.log(resp)
                        }
                    })
            } else {
                error_message.value = '验证码错误,请重新输入';
            }
        }

        return {
            login,
            get_new_captcha,
            captcha,
            error_message,
        }
    }
}
</script>

<style lang="scss" scoped>
div.error-message {
    color: red;
}

.show-login {
    width: 400px !important;
    margin: 0 auto;
}
</style>
