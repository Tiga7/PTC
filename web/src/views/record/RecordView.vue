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
                        <ListItemMeta :description="'发表于:' + item.modifyTime" />
                        <div class="content" v-line-clamp="2">
                            {{ item.description }}
                        </div>
                        <Button size="small" :icon="status === 1 ? 'md-heart' : 'md-heart-outline'" type="text"
                            @click="like" shape="circle"></Button>
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
            <Page :total=page.size :model-value=page.current @on-change="get_page" />
        </div>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import api from '@/request/api';
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

        const get_page = (valid) => {
            page.current = valid
            // console.log(valid);
        }
        return {
            data,
            list,
            get_list,
            page,
            get_page,
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