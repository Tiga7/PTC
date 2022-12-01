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
                    推荐自习室为:{{ room }}
                </div>
                <div class="info-ptc">
                    <Select v-model="room" filterable prefix="md-pin" v-width="300" size="large" placeholder="地址(选填)"
                        on-open-change="get_room_list">
                        <Option v-for="item in rooms" :value="item.id" :key="item.id">
                            {{ item.buildingName }} {{ item.roomName }}
                        </Option>
                    </Select>
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

        let room = ref("5301")
        let rooms = reactive([])
        let num = Math.floor(Math.random() * (70));

        const get_room_list = () => {
            if (store.state.user.is_login == true) {
                api.room_simplelist({
                })
                    .then(resp => {
                        rooms.push(...resp.data)
                        room.value = rooms.at(num).roomName
                    }).catch(error => {
                        console.log(error)
                    })
            }
        }
        get_room_list();

        const handle_submit = () => {
            if (store.state.user.is_login !== true) {
                Message.info("请先登录再进行打卡操作");
                // setTimeout(() => {
                //     router.push({ name: "login_index" })
                // }, 1000);
            } else {
                if (!room.value) room.value = 0;
                if (description.value == '' || description.value.length == 0) {
                    Message.info("打卡说点什么鼓励一下自己吧");
                    return;
                }
                api.record_add({
                    user_id: store.state.user.id,
                    room_id: room.value,
                    description: description.value
                })
                    .then((response) => {
                        if (response.code === 200) {
                            Message.info(response.data)
                        }
                    })
                    .catch((error) => {
                        console.log(error)
                    })
            }
        }
        return {
            nowTime,
            description,
            room,
            rooms,
            handle_submit,
            get_room_list,

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