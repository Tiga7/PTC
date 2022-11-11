<template>
    <Button shape="circle" :icon="status === 1 ? 'md-heart' : 'md-heart-outline'" @click="like">
    </Button>
</template>

<script>
// import { like_api } from '@/request/api';
import { ref } from 'vue';
export default {
    components: {
    },

    setup() {
        // const icon_name = ["md-heart-outline", "md-heart"]
        let status = ref(0);

        // const record_id = 1;
        const like = () => {

            // like_api({
            //     record_id: record_id,
            // }).then((response) => {

            //     console.log(response)
            // }).catch((error) => {

            //     console.log(error)
            // })

            status.value = 1 ^ status.value;
        }

        return {
            like,
            status,
        }
    }


}
</script>

<style lang="scss" scoped>

</style>