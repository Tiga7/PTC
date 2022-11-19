<template>
    <CardViewVue>
        <Row justify="center" :gutter="16">
            <Col span="12">
            <Card>
                <template #title>
                    <p>
                        基础信息
                    </p>
                </template>
                <div class="info">
                    <div class="info-image">
                        <Avatar :size="100" :src="$store.state.user.photo" />
                    </div>
                    <div class="info-name">Hi,{{ $store.state.user.username }}</div>
                    <div class="info-name">欢迎来打卡!</div>
                </div>

            </Card>
            </Col>
            <Col span="12">
            <Card>
                <template #title>
                    <p>
                        打卡信息
                    </p>
                </template>
                <div class="info-ptc">
                    当前时间 :
                    &nbsp;
                    <Time :time="nowTime" type="datetime" />
                </div>
                <div class="info-ptc">
                    <Space direction="vertical" size="large" type="flex">
                        <Input v-model="description" type="textarea" :autosize="{ minRows: 3, maxRows: 5 }"
                            maxlength="200" style="width: 30vw" placeholder="今日打卡,说点什么鼓励一下自己吧~" />
                    </Space>
                </div>
                <!-- <div class="info-ptc">
                    <Input v-model="value" placeholder="地址(选填)" style="width: 30vw" size="large" />
                </div> -->
                <div class="info-ptc">
                    推荐自习室为:5301
                </div>
                <div class="info-ptc">
                    <TreeSelect v-model="rooms.value" prefix="md-pin" :data="rooms" v-width="300" size="large"
                        placeholder="地址(选填)">
                    </TreeSelect>
                </div>
                <div class="info-ptc">
                    <Button type="info" @click="handle_submit">开启今天的打卡之旅</Button>
                </div>

            </Card>
            </Col>
        </Row>
    </CardViewVue>
</template>

<script>
// @ is an alias to /src
import CardViewVue from '@/components/CardView.vue';
import { reactive, ref } from 'vue';
import api from "@/request/api"

import { useStore } from 'vuex';
import { Message } from 'view-ui-plus';
import router from '@/router';
export default {
    components: {
        CardViewVue
    },

    setup() {
        const store = useStore();

        let nowTime = ref(new Date().getTime());
        const get_time = () => {
            nowTime.value = new Date().getTime();
        };
        setInterval(get_time, 1000);

        let description = ref("");
        let rooms = reactive([
            {
                title: '五号楼',
                expand: false,
                value: '五号楼',
                selected: false,
                checked: false,
                children: [
                    {
                        title: '5101',
                        expand: true,
                        value: '5101',
                        selected: false,
                        checked: false,
                    },
                    {
                        title: '5102',
                        expand: true,
                        value: '5012',
                        selected: false,
                        checked: false,
                    },
                ]
            },
            {
                title: '6号楼',
                expand: false,
                value: '6号楼',
                selected: false,
                checked: false,
                children: [
                    {
                        title: '6101',
                        expand: true,
                        value: '6101',
                        selected: false,
                        checked: false,
                    },
                    {
                        title: '6102',
                        expand: true,
                        value: '6012',
                        selected: false,
                        checked: false,
                    },
                ]
            },
        ])

        const handle_submit = () => {
            // record_getlist_api({
            //     sno: store.state.user.sno
            // })
            //     .then((response) => {
            //         console.log(response);
            //     })
            //     .catch((error) => {
            //         console.log(error)
            //     })
            if (store.state.user.is_login !== true) {
                Message.info("请先登录再进行打卡操作");
                setTimeout(() => {
                    router.push({ name: "login_index" })
                }, 1000);
            } else {
                if (!rooms.value) rooms.value = 0;
                api.record_add({
                    user_id: store.state.user.id,
                    room_id: rooms.value,
                    description: description
                })
                    .then((response) => {
                        if (response.code === 200) {
                            Message.info(response.data)
                        }
                        console.log(response);
                    })
                    .catch((error) => {
                        console.log(error)
                    })
            }



        }
        return {
            nowTime,
            description,
            rooms,
            handle_submit
        }
    }
}
</script>
<style scoped>
.box-card {
    text-align: center;
    width: 100%;
    margin-top: 0px;
    /* padding-top: 20px; */
}

.info {
    text-align: center;
    padding: 35px 0;
}

.info-name {
    margin: 15px 0 10px;
    font-size: 24px;
    font-weight: 500;
    color: #262626;
}

.info-ptc {
    text-align: center;
    padding: 10px 0;
    margin: 15px 0 10px;
    font-size: 24px;
    font-weight: 500;
    color: #262626;
}
</style>