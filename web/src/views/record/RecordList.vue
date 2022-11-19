<template>
    <CardViewVue>
        <Row>
            <Col span="3">
            </Col>
            <Col span="18">
            <List item-layout="vertical" size="large">
                <ListItem v-for="item in data" :key="item.id">
                    <Row>
                        <Col span="2">
                        <div class="user-info">
                            <Avatar :size="50" :src="item.photo" />
                        </div>
                        </Col>
                        <Col span="20" offser="1">
                        <div class="user-name"> {{ item.username }}</div>
                        <ListItemMeta
                            :description="'发表于:' + item.modifyTime + ' ' + (item.roomName == null ? '' : item.roomName)" />

                        <div class="content" v-line-clamp="2">
                            {{ item.description }}
                        </div>
                        <!-- <Button size="small" :icon="status === 1 ? 'md-heart' : 'md-heart-outline'" type="text"
                            shape="circle"></Button> -->
                        <a>
                            <Icon :type="'md-heart-outline'" />{{ status }}
                        </a>
                        &nbsp;
                        <a @click="turn_to_comment(item)">
                            评论
                        </a>

                        </Col>
                    </Row>
                </ListItem>
            </List>
            </Col>
            <Col span="3">

            </Col>
        </Row>

        <div>
            <Button type="primary" @click="get_list">Signin</Button>
        </div>
        <div>
            <!-- <Table border :columns="columns" :data="list"></Table> -->
        </div>
        <div style="text-align: center;">
            <!-- <Page :total=page.size :model-value=page.current @on-change="get_page" /> -->
        </div>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import api from '@/request/api';
import router from '@/router';
import store from '@/store';
import { reactive } from 'vue';
export default {
    components: {
        CardViewVue,

    },
    setup() {
        const data = reactive([]);

        const page = reactive(
            {
                size: 1,
                current: 1,
            }
        )
        let list = reactive([])
        const get_list = () => {
            list.length = 0;
            api.record_getlist({
                page: 1,
                size: 20,
            })
                .then((response) => {
                    console.log(response)
                    data.push(...response.data.records);
                    // page.size = response.data.total_count
                    // list.push(...response.data.records)
                }).catch((error) => {
                    console.log(error)
                })
        }
        get_list();
        const get_page = (valid) => {
            page.current = valid
            // console.log(valid);
        }
        const turn_to_comment = (item) => {
            store.commit('updateRecord', item);
            router.push({
                name: "comment_index",
                params: {
                    record_id: item.id
                },
                data: item,
            })
            console.log(item)
        }
        return {
            data,
            list,
            get_list,
            page,
            get_page,
            status: 1,
            turn_to_comment
        }
    }
}
</script>

<style lang="scss" scoped>
.user-info {
    text-align: center;
    padding: 0px 0;
}

.user-name {
    // margin: 10px 0 5px;
    // text-align: center;
    font-size: 15px;
    font-weight: 550;
    color: #262626;
}

.content {
    margin: 5px 0 5px;
    font-size: 15px;
    font-weight: 500;
    color: #262626;
}
</style>