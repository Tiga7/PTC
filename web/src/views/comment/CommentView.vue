<template>
    <CardViewVue>
        <Row>
            <Col span="3">
            </Col>
            <Col span="18">
            <List item-layout="vertical" size="large">
                <ListItem :key="data.id">
                    <Row>
                        <Col span="2">
                        <div class="user-info">
                            <Avatar :size="50" :src="data.photo" />
                        </div>
                        </Col>
                        <Col span="20" offser="1">
                        <div class="user-name"> {{ data.username }}</div>
                        <ListItemMeta :description="'发表于:' + data.modifyTime" />
                        <div class="content" v-line-clamp="2">
                            {{ data.description }}
                        </div>
                        <!-- <Button size="small" :icon="status === 1 ? 'md-heart' : 'md-heart-outline'" type="text"
                            @click="like" shape="circle"></Button> -->
                        </Col>
                    </Row>
                </ListItem>
            </List>
            <Divider plain></Divider>
            <div class="comment-title">评论区
            </div>
            <div class="comment-area">
                <Row>
                    <Col span="2">
                    <div class="user-info">
                        <Avatar :size="50" :src="$store.state.user.photo" />
                    </div>
                    </Col>
                    <Col span="20" offser="1">

                    <Input v-model="comment" type="textarea" :autosize="{ minRows: 3, maxRows: 3 }"
                        placeholder="鼓励一下每天都坚持打卡的同学吧~" />
                    <div class="Button" style="text-align: right">
                        <Button size="large" type="primary" @click="add_comment">发表评论</Button>
                    </div>

                    </Col>
                </Row>
            </div>
            <Divider plain></Divider>
            <!-- comment list -->
            <div>
                <List item-layout="vertical" size="large">
                    <ListItem v-for="item in comment_list" :key="item.id">
                        <Row>
                            <Col span="2">
                            <div class="user-info">
                                <Avatar :size="50" :src="item.photo" />
                            </div>
                            </Col>
                            <Col span="20" offser="1">
                            <div class="user-name">
                                {{ item.username }}

                            </div>
                            <ListItemMeta :description="'发表于:' + item.modifyTime">
                            </ListItemMeta>

                            <div class="content" v-line-clamp="2">
                                {{ item.comment }}
                            </div>


                            </Col>
                        </Row>
                        <template #extra>
                            <a @click="remove_comment(item)" style="color: black;"
                                v-if="$store.state.user.id == item.userId || $store.state.user.is_admin == true">
                                删除
                            </a>
                        </template>
                    </ListItem>
                </List>

                <Page :total=total_count :page-size="10" @on-change="get_comment_list" style="text-align: center;" />
            </div>
            </Col>
            <Col span="3">
            </Col>
        </Row>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import { reactive, ref } from 'vue';
import { useStore } from 'vuex';
import api from '@/request/api';
import { Message } from 'view-ui-plus';

export default {
    components: {
        CardViewVue
    },
    setup() {
        const store = useStore();
        const data = store.state.comment.record;
        const comment = ref("")
        const comment_list = reactive([]);

        const total_count = ref(0);
        const get_comment_list = (page) => {
            comment_list.length = 0;
            if (data.id != null && data.id.length != 0) {
                api.comment_get_list({
                    record_id: data.id,
                    page: page,
                    size: 10
                })
                    .then(response => {
                        if (response.code == 200) {
                            comment_list.push(...response.data.comments);
                            total_count.value = response.data.total_count;
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            } else {
                Message.info("未获取到该条记录");
            }

        }
        get_comment_list(1);

        const add_comment = () => {
            if (data.id == null || data.id.length == 0) {
                Message.info("未获取到该条记录,不能评论");
                return;
            }
            if (store.state.comment.comment_time >= 10) {
                Message.info("不建议评论太多次哦");
                return;
            }
            store.commit("updateCommentTime");
            if (comment.value == null || comment.value.length == 0) {
                Message.info("评论不能为空");
            } else {
                api.comment_add({
                    user_id: store.state.user.id,
                    record_id: data.id,
                    comment: comment.value,
                })
                    .then(response => {
                        if (response.code == 200) {
                            Message.info("评论成功");
                            comment.value = ""
                            get_comment_list(1);
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            }
        }

        const remove_comment = (item) => {
            api.comment_remove({
                id: item.id
            }).then(response => {
                if (response.code == 200) {
                    Message.info("删除评论成功");
                    get_comment_list(1);

                }
            })
                .catch(error => {
                    console.log(error)
                })
        }

        return {
            data, comment, comment_list, total_count,
            get_comment_list, add_comment, remove_comment
        }
    }
}
</script>

<style lang="scss" scoped>
.user-name {
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

.user-info {
    text-align: center;
    padding: 0px 0;
}

.comment-title {
    margin: 5px 20px 5px;
    font-size: 20px;
    font-weight: 600;
    color: #262626;
}

.Button {
    margin: 10px;
}
</style>