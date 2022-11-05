<template>
    <CardViewVue>
        edit user
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
export default {
    components: {
        CardViewVue
    },
    setup() {

        // $.ajax({
        //     url: "http://localhost:3030/api/user/info/",
        //     type: 'get',

        //     headers: {
        //         Authorization: "Bearer " + token.value,
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