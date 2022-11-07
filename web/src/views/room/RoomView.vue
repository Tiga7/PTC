<template>
    <CardViewVue>
        自习室管理
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';

// import { register_api } from '@/request/api';

export default {
    components: {
        CardViewVue
    },
    setup() {

        // login_api({
        //     sno: '111111111',
        //     password: 'cc',
        // })
        //     .then(function (response) {
        //         console.log(response);
        //     })
        //     .catch(function (error) {
        //         console.log(error);
        //     })

        // get_info_api()
        //     .then(function (response) {
        //         console.log(response);
        //     })
        //     .catch(function (error) {
        //         console.log(error);
        //     })

        // register_api({
        //     sno: "333333333",
        //     password: "cc",
        //     confirmPwd: "cc",
        //     phone: "18908901111"
        // })
        //     .then(function (response) {
        //         console.log(response);
        //     })
        //     .catch(function (error) {
        //         console.log(error);
        //     })


        // axios({
        //     url: "http://localhost:3030/api/user/token/",
        //     method: 'post',
        //     headers: {
        //         authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyYjQ3Yjk1YmE2OWQ0NTc1OTU4MDgyMDkwMGM2NzVmMCIsInN1YiI6IjQxIiwiaXNzIjoic2ciLCJpYXQiOjE2Njc4MDU4NjMsImV4cCI6MTY2ODQxMDY2M30.Y2gETMD7aarfhkNSJU_gbao1PdkkfwFLsiId7dmQmXU",
        //     },
        // })
        //     .then(function (response) {
        //         console.log(response);
        //     })
        //     .catch(function (error) {
        //         console.log(error);
        //     })

        // $.ajax({
        //     url: "http://localhost:3030/api/user/token/",
        //     // type: 'post',
        //     method: 'post',
        //     data: {
        //         sno: "111111111",
        //         password: 'cc',
        //     },
        //     success(resp) {
        //         console.log(resp)
        //     },
        //     error(resp) {
        //         console.log(resp)
        //     }
        // })

        return {

        }
    }
}
</script>

<style lang="scss" scoped>

</style>