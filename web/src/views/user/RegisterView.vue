<template>
    <CardViewVue>
        <div class="demo-login">
            <Form>
                <FormItem>
                    <Input v-model.trim="sno" prefix="ios-contact-outline" placeholder="学号" maxlength="9" type="text"
                        size="large" clearable></Input>
                </FormItem>
                <FormItem>
                    <Input v-model="password" prefix="ios-lock-outline" placeholder="密码" maxlength="20" password
                        type="password" size="large"></Input>
                </FormItem>
                <FormItem>
                    <Input v-model="password_check" prefix="ios-lock-outline" placeholder="确认密码" maxlength="20"
                        type="password" password size="large"></Input>
                </FormItem>
                <FormItem>
                    <Input v-model="phone" prefix="ios-call-outline" placeholder="手机号" maxlength="11" type="text"
                        size="large" clearable></Input>
                </FormItem>
                <FormItem>
                    <Row>
                        <Col span="14">
                        <Input maxlength="6" prefix="ios-apps-outline" placeholder="输入验证码" size="large"
                            style="width: 100%;" v-model="enter_captcha" />
                        </Col>
                        <Col span="8" offset="2">
                        <Button @click="get_new_captcha" style=" width: 100%; float: right;" size="large">
                            {{ captcha }}
                        </Button>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>
                    <Alert type="error" v-if="error_message !== ''" show-icon>
                        <template #desc>
                            {{ error_message }}
                        </template>
                    </Alert>
                    <Button @click="handle_register" type="primary" style=" width: 49%;" size="large">激活</Button>
                    <Button @click="reset" style="margin-left: 2%; width: 49%;" size="large">重置</Button>
                </FormItem>
            </Form>
        </div>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import { Col, Message, } from 'view-ui-plus';
import { ref } from 'vue';
import { register_api } from '@/request/api';
import router from '@/router';

export default {
    components: {
        CardViewVue,
        Col
    },
    setup() {
        const sno = ref('');
        const password = ref('');
        const phone = ref('');

        const password_check = ref('');
        const enter_captcha = ref('');
        const captcha = ref('');
        const error_message = ref('');

        const get_new_captcha = () => {
            captcha.value = Math.floor(Math.random() * (999999 - 100000)) + 100000;
            // captcha.value = 111111;
        }
        get_new_captcha()

        const check_phone = (phone) => {
            const phone_rule = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/g;
            return (phone.match(phone_rule) !== null)
        }
        const handle_register = () => {
            // console.log(check_phone(phone.value))
            error_message.value = "";

            if (sno.value.length !== 9) {
                error_message.value = "学号格式不正确";
            } else if (password.value.length === 0) {
                error_message.value = "密码不能为空";
            } else if (password.value !== password_check.value) {
                error_message.value = "两次输入的密码不一致";
            } else if (!check_phone(phone.value)) {
                error_message.value = "手机格式不正确";
            } else if (parseInt(enter_captcha.value) !== parseInt(captcha.value)) {
                error_message.value = "验证码不正确";
            } else {

                register_api({
                    sno: sno.value,
                    password: password.value,
                    confirmPwd: password_check.value,
                    phone: phone.value
                })
                    .then(function (response) {
                        if (response.code == 200) {
                            router.push({ name: "login_index" })
                            Message.info('激活成功');
                        } else {
                            Message.info(response.message);
                            error_message.value = response.message;
                        }
                    })
                    .catch(function (error) {
                        Message.info('出现未知错误,请重试');
                        error_message.value = error.message;
                    })

            }

        }
        const reset = () => {
            sno.value = '';
            password.value = '';
            phone.value = '';
            password_check.value = '';
            error_message.value = '';
        }

        return {
            sno, password, phone, password_check, captcha,
            enter_captcha,
            error_message,
            handle_register,
            get_new_captcha,
            reset,
        }
    }
}
</script>

<style lang="scss" scoped>
.demo-login {
    width: 400px !important;
    margin: 0 auto;
}

div.error-message {
    height: 30;
    line-height: 30px;
    text-align: center;
    color: red;
}
</style>