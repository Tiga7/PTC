<template>
    <CardViewVue>
        <div v-if="!$store.state.user.is_admin">
            <UserRoomView />
        </div>
        <div v-else>
            <AdminRoomView />
        </div>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import AdminRoomView from './AdminRoomView.vue';
import UserRoomView from './UserRoomView.vue'

export default {
    components: {
        CardViewVue,
        AdminRoomView,
        UserRoomView,
    },
    setup() {

        return {

        }
    }
}
</script>

<style lang="scss" scoped>

</style>