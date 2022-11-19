<template>
    <div style="text-align: center;">
        <Button shape="circle" :icon="status === 1 ? 'md-heart' : 'md-heart-outline'" @click="like" size="small">
        </Button>
    </div>

</template>

<script>
import { ref } from 'vue';

export default {
    components: {
    },

    setup() {

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