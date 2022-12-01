<template>
    <CardViewVue>
        <Row>
            <Col span="3">
            </Col>
            <Col span="18">
            <List item-layout="vertical" size="large">
                <ListItem v-for="item in record_list" :key="item.id">
                    <Row>
                        <Col span="2">
                        <div class="user-info">
                            <Avatar :size="50" :src="item.photo" />
                        </div>
                        </Col>
                        <Col span="20" offser="1">
                        <div class="user-name"> {{ item.username }}</div>
                        <ListItemMeta
                            :description="'发表于:' + item.modifyTime + ' ' + (item.roomName == null ? '' : '自习室:' + item.roomName)" />

                        <div class="content" v-line-clamp="2">
                            {{ item.description }}
                        </div>
                        <!-- <Button size="small" :icon="status === 1 ? 'md-heart' : 'md-heart-outline'" type="text"
                            shape="circle"></Button> -->
                        <a @click="like_oprate(item)">
                            <Icon :type="item.isLiked ? 'md-heart' : 'md-heart-outline'" />
                            {{ item.likeCount }}
                        </a>
                        &nbsp;
                        <a @click="turn_to_comment(item)" style="color: black;">
                            评论 {{ item.commentCount }}
                        </a>

                        </Col>
                    </Row>
                    <template #extra>
                        <a style="color: black;" @click="remove(item)"
                            v-if="$store.state.user.id == item.userId || $store.state.user.is_admin == true">
                            删除
                        </a>
                    </template>
                </ListItem>
            </List>
            </Col>
            <Col span="3">

            </Col>
        </Row>
        <div style="text-align: center;">
            <Page :total=page.total_count :page-size="page.size" @on-change="get_list" />
        </div>

    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import api from '@/request/api';
import router from '@/router';
import store from '@/store';
import { Message } from 'view-ui-plus';
import { reactive } from 'vue';

export default {
    components: {
        CardViewVue,
    },
    setup() {
        const page = reactive(
            {
                current: 1,
                size: 10,
                total_count: 1,
            }
        )
        let record_list = reactive([])
        const get_list = (valid) => {
            page.current = valid
            record_list.length = 0;
            api.record_getlist({
                page: page.current,
                size: page.size,
                user_id: store.state.user.id,
            })
                .then((response) => {
                    record_list.push(...response.data.records);
                    page.total_count = response.data.total_count
                }).catch((error) => {
                    console.log(error)
                })
        }
        get_list(1);

        const turn_to_comment = (item) => {
            store.commit('updateRecord', item);
            router.push({
                name: "comment_index",
                params: {
                    record_id: item.id
                },
                record_list: item,
            })
            // console.log(item)
        }

        const like_oprate = (item) => {
            if (item.isLiked) {
                item.isLiked = false;
                item.likeCount--;
                api.like_opreate({
                    record_id: item.id,
                })
                    .then(resp => {
                        if (resp.message == 'success') {
                            Message.info("取消点赞");
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            } else {
                item.isLiked = true;
                item.likeCount++;
                api.like_opreate({
                    record_id: item.id,
                })
                    .then(resp => {
                        if (resp.message == 'success') {
                            Message.info("点赞成功");
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            }
        }
        const remove = (item) => {
            api.record_remove({
                id: item.id,
            })
                .then(resp => {
                    if (resp.code == 200) {
                        Message.info(resp.message);
                        get_list(1);
                    }
                })
                .catch(error => {
                    Message.info(error.message);
                    get_list(1);

                })
        }
        return {
            record_list,
            get_list,
            page,
            status: 1,
            turn_to_comment,
            like_oprate,
            remove
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
