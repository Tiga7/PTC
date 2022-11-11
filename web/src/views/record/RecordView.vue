<template>
    <CardViewVue>
        <div>
            <Button type="primary" @click="get_list">Signin</Button>
        </div>
        <div>
            <Table border :columns="columns" :data="list"></Table>
        </div>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import { record_getlist_api } from '@/request/api';
import { reactive } from 'vue';
export default {
    components: {
        CardViewVue
    },
    setup() {
        const columns = reactive([
            {
                type: 'index',
                width: 80,
                align: 'center'
            },
            {
                title: 'user_id',
                key: 'userId'
            },
            {
                title: 'room_id',
                key: 'roomId'
            },
            {
                title: '打卡时间 ',
                key: 'modifyTime'
            },
            {
                title: 'description ',
                key: 'description'
            }
        ],)

        let list = reactive([])
        console.log(list)
        const get_list = () => {
            record_getlist_api({

            })
                .then((response) => {
                    // console.log(response.data)
                    list.push(...response.data)
                    console.log(list)
                }).catch((error) => {
                    console.log(error)
                })

        }
        return {
            columns,
            list,
            get_list,
        }
    }
}
</script>

<style lang="scss" scoped>

</style>