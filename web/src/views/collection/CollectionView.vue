<template>
    <CardViewVue>
        <CommentView></CommentView>





        <CommentView></CommentView>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import CommentView from '../comment/CommentView.vue';
export default {
    components: {
        CardViewVue,
        CommentView
    },
    setup() {

        // const time = Date().format('YYYY-MM-DD HH:mm:ss');

        return {
            // time: new Date().getTime(),
        }

    }
}
</script>

<style lang="scss" scoped>

</style>