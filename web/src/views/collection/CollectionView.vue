<template>
    <CardViewVue>
        <Row justify="center" align="top" class="code-row-bg">
            <Col span="18">
            <div class="table">
                <Table stripe border :columns="columns" :data="colletions">
                    <!-- <template #action="{ row }">
                    </template> -->
                    <template #action="{ row }">
                        <Button type="primary" size="large" @click="handle_collect(row)">取消收藏</Button>
                    </template>
                </Table>

                <Page :total=total_count :model-value=current_page @on-change="get_page" />
            </div>
            </Col>
        </Row>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import { get_colletion_api, remove_colletion_api } from '@/request/api';
import { Message } from 'view-ui-plus';
import { reactive, ref } from 'vue';
export default {
    components: {
        CardViewVue,
    },
    setup() {
        const columns = reactive([
            {
                title: '序号',
                type: 'index',
                width: 80,
                align: 'center',
            },
            {
                title: '教学楼',
                key: 'buildingName',
                align: 'center',
            },
            {
                title: '教室号',
                key: 'roomName',
                align: 'center',
            },
            {
                title: '容量',
                key: 'capacity',
                align: 'center',
            },
            {
                title: '操作',
                align: 'center',
                slot: 'action',
            }
        ],)
        let colletions = reactive([]);
        const current_page = ref(1);
        const total_count = ref(0);

        const get_page = (page) => {
            current_page.value = page
            colletions.length = 0;
            get_colletion_api({
                page: current_page.value,
                size: 10,
            })
                .then(function (response) {
                    // console.log(response)
                    total_count.value = response.data.total_count
                    if (response.data.collectionVo.length == 0 && current_page.value > 1) {
                        current_page.value--;
                        get_page(current_page.value)
                    } else {
                        current_page.value = page;
                    }
                    colletions.push(...response.data.collectionVo)
                })
                .catch(function (error) {
                    console.log(error);
                })
        }
        get_page(current_page.value);

        const handle_collect = (row) => {
            remove_colletion_api({
                collection_id: row.id
            }).then(function (response) {
                if (response.code === 200) {
                    Message.info("取消收藏成功")
                    get_page(current_page.value)
                } else {
                    Message.info("取消收藏失败,请重试")
                }
            })
                .catch(function (error) {
                    console.log(error);
                })

        }

        return {
            columns, colletions, current_page, total_count,
            handle_collect, get_page
        }

    }
}
</script>

<style lang="scss" scoped>
.table {
    width: 100%;
    text-align: center;
}
</style>