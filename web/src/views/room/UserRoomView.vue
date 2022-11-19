<template>
    <CardViewVue>
        <div style="text-align: center;">
            <Form ref="formInline" inline>
                <FormItem prop="user">
                    <Input type="text" size="large" v-model="search_info.buildingName" placeholder="教学楼">
                    </Input>
                </FormItem>
                <FormItem prop="password">
                    <Input type="text" size="large" v-model="search_info.roomName" placeholder="教室号">
                    </Input>
                </FormItem>
                <FormItem>
                    <Button type="primary" size="large" @click="get_page(1)">查询</Button>
                </FormItem>
                <FormItem>
                    <Button type="primary" size="large" @click="handle_search_all">查询全部</Button>
                </FormItem>
            </Form>
        </div>

        <Row justify="center" align="top" class="code-row-bg">
            <Col span="18">
            <div class="table">
                <Table stripe border :columns="columns" :data="rooms">
                    <!-- <template #action="{ row }">
                    </template> -->
                    <template #action="{ row }">
                        <Button type="primary" size="large" @click="handle_collect(row)"
                            v-if="row.collectionId === -1">收藏</Button>
                        <Button type="primary" size="large" @click="handle_collect(row)" v-else>取消收藏</Button>
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
import { schedule_get_list, add_colletion_api, remove_colletion_api } from '@/request/api';
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
                // width: 120,
            },
            {
                title: '教室号',
                key: 'roomName',
                align: 'center',
                // width: 120,

            },
            {
                title: '容量',
                key: 'capacity',
                align: 'center',
                // width: 120,
            },
            {
                title: '第几节有课',
                key: 'classTime',
                align: 'center',
                // width: 120,
            },
            {
                title: '操作',
                align: 'center',
                slot: 'action',
                // width: 200,
            }
        ],)
        let rooms = reactive([]);
        const current_page = ref(1);
        const total_count = ref(0);
        const search_info = reactive({
            buildingName: '',
            roomName: '',
        })
        const get_page = (page) => {
            current_page.value = page
            rooms.length = 0;
            if (search_info.buildingName !== '' || search_info.roomName !== '') {
                schedule_get_list({
                    page: current_page.value,
                    size: 10,
                    buildingName: search_info.buildingName,
                    roomName: search_info.roomName,
                })
                    .then(function (response) {
                        console.log(response)
                        total_count.value = response.data.total_count

                        rooms.push(...response.data.schedules)
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            } else {
                schedule_get_list({
                    page: current_page.value,
                    size: 10,
                })
                    .then(function (response) {
                        // console.log(response)
                        total_count.value = response.data.total_count
                        if (response.data.schedules.length == 0 && current_page.value > 1) {
                            current_page.value--;
                            get_page(current_page.value)
                        } else {
                            current_page.value = page;
                        }
                        rooms.push(...response.data.schedules)
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        }
        get_page(current_page.value);

        const handle_search_all = () => {
            search_info.buildingName = '';
            search_info.roomName = '';
            get_page(1);
        }

        const handle_collect = (row) => {
            console.log(row);
            if (row.collectionId === -1) {
                add_colletion_api({
                    room_id: row.id
                }).then(function (response) {
                    if (response.code === 200) {
                        Message.info("收藏成功")
                        get_page(current_page.value)
                    } else {
                        Message.info("收藏失败,请重试")
                    }
                })
                    .catch(function (error) {
                        console.log(error);
                    })

            } else {
                remove_colletion_api({
                    collection_id: row.collectionId
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
        }
        return {
            columns,
            rooms,
            current_page,
            total_count,
            get_page,
            search_info,
            handle_search_all,
            handle_collect,
        }
    }
}
</script>

<style lang="scss" scoped>
.table {
    width: 100%;
    text-align: center;
    // padding: 10px 0;
    // margin: 15px 0 10px;
}
</style>