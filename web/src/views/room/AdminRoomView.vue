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
                    <Button type="primary" size="large" @click="handle_add">新增</Button>
                </FormItem>
            </Form>
        </div>
        <Row justify="center" align="top" class="code-row-bg">
            <Col span="18">
            <div class="table">
                <Table stripe border :columns="columns" :data="rooms">
                    <template #action="{ row }">
                        <Button type="primary" size="large" style="margin-right: 5px"
                            @click="handle_edit(row)">修改</Button>
                        <Button type="error" size="large" style="margin-right: 5px"
                            @click="delete_room(row.id)">删除</Button>
                    </template>
                </Table>
                <Modal v-model="edit_modal" title="修改自习室信息" @on-ok="submit_edit" @on-cancel="edit_modal = false">
                    <Form :model="form_data" label-position="right" :label-width="100">
                        <FormItem label="教学楼:">
                            <Input v-model="form_data.buildingName" disabled> </Input>
                        </FormItem>
                        <FormItem label="教室号:">
                            <Input v-model="form_data.roomName" disabled></Input>
                        </FormItem>
                        <FormItem label="容量:">
                            <Input v-model="form_data.capacity" maxlength="3"></Input>
                        </FormItem>
                    </Form>
                </Modal>
                <Modal v-model="add_modal" title="新增自习室" @on-ok="submit_add" @on-cancel="add_modal = false">
                    <Form :model="form_data" label-position="right" :label-width="100">
                        <FormItem label="教学楼:">
                            <Input v-model="form_data.buildingName"> </Input>
                        </FormItem>
                        <FormItem label="教室号:">
                            <Input v-model="form_data.roomName"></Input>
                        </FormItem>
                        <FormItem label="容量:">
                            <Input v-model="form_data.capacity" maxlength="3"></Input>
                        </FormItem>
                    </Form>
                </Modal>
                <Page :total=total_count :model-value=current_page @on-change="get_page" />
            </div>
            </Col>
        </Row>

    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import { room_delete_api, room_get_list, room_post_api, room_put_api } from '@/request/api';
import { Message, Modal } from 'view-ui-plus';
import { reactive, ref } from 'vue';
export default {
    components: {
        CardViewVue,
        Modal
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
                title: '操作',
                align: 'center',
                slot: 'action',
                // width: 200,
            }
        ],)
        let rooms = reactive([]);
        const current_page = ref(1);
        const total_count = ref(0);

        const edit_modal = ref(false)
        const add_modal = ref(false)
        const form_data = reactive({
            id: 0,
            buildingName: '',
            roomName: '',
            capacity: ''
        })

        const search_info = reactive({
            buildingName: '',
            roomName: '',
        })
        const get_page = (page) => {
            rooms.length = 0;
            if (search_info.buildingName !== '' || search_info.roomName === '') {
                room_get_list({
                    page: page,
                    size: 10,
                    buildingName: search_info.buildingName,
                    roomName: search_info.roomName,
                })
                    .then(function (response) {
                        // console.log(response)
                        total_count.value = response.data.total
                        rooms.push(...response.data.records)
                        current_page.value = page;
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            } else {
                room_get_list({
                    page: page,
                    size: 10,
                })
                    .then(function (response) {
                        // console.log(response)
                        total_count.value = response.data.total
                        if (response.data.records.length == 0 && current_page.value > 1) {
                            current_page.value--;
                            get_page(current_page.value)
                        } else {
                            current_page.value = page;
                        }
                        rooms.push(...response.data.records)
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }

        }
        get_page(current_page.value);


        const delete_room = (id) => {
            room_delete_api({
                id: id,
            })
                .then(function (response) {
                    Message.info(response.data)
                    // console.log(response);
                    get_page(current_page.value)
                })
                .catch(function (error) {
                    console.log(error);
                })
        }

        const handle_edit = (row) => {
            edit_modal.value = true;
            form_data.id = row.id;
            form_data.buildingName = row.buildingName;
            form_data.roomName = row.roomName;
            form_data.capacity = row.capacity
        }
        const handle_add = () => {
            add_modal.value = true;
            form_data.buildingName = '';
            form_data.roomName = '';
            form_data.capacity = ''
            // console.log(row)
        }
        const submit_edit = () => {
            if (form_data.capacity < 0) {
                Message.info('输入参数出错, 请重新输入')
            } else {
                room_put_api({
                    id: form_data.id,
                    buildingName: form_data.buildingName,
                    roomName: form_data.roomName,
                    capacity: form_data.capacity,
                })
                    .then(function (response) {
                        // console.log(response);
                        if (response.code == 200) {
                            Message.info(response.data)
                            get_page(current_page.value);
                        } else {
                            Message.info("出现未知错误,请重试.")
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        }
        const submit_add = () => {
            if (form_data.buildingName.length == 0
                || form_data.roomName.length == 0
                || form_data.capacity < 0
            ) {
                console.log(form_data)
                Message.info('输入参数出错, 请重新输入')
            } else {
                console.log(form_data)
                room_post_api({
                    buildingName: form_data.buildingName,
                    roomName: form_data.roomName,
                    capacity: form_data.capacity
                })
                    .then(function (response) {
                        if (response.code == 200) {
                            Message.info(response.data);
                            get_page(current_page.value);
                        } else {
                            Message.info("出现未知错误,请重试.")
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }
        }
        const handle_search = () => {
            if (search_info.buildingName === '' && search_info.roomName === '') {
                Message.info("参数不能都为空");
            } else {
                get_page(current_page.value)
            }
        }
        return {
            columns,
            rooms,
            current_page,
            total_count,
            get_page,
            delete_room,
            handle_edit,
            handle_add,
            edit_modal,
            add_modal,
            form_data,
            submit_edit,
            submit_add,
            search_info,
            handle_search,
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