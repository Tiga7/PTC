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
                    <div class="info-image" @click="showDialog">
                        <Avatar :size="100" :src="$store.state.user.photo" />
                    </div>
                    <div class="info-name">{{ $store.state.user.username }}</div>
                    <div class="info-name">欢迎来打卡!</div>
                </div>
            </Card>
            </Col>

            <Col span="12">
            <Card>
                <template #title>
                    <p>
                        账户编辑
                    </p>
                </template>

                card body
            </Card>
            </Col>
        </Row>
    </CardViewVue>
</template>

<script>
import CardViewVue from '@/components/CardView.vue';
import { useStore } from 'vuex';

export default {
    components: {
        CardViewVue
    },
    setup() {

        const store = useStore();
        store;
        return {

        }
    }
}
</script>

<style lang="scss" scoped>
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
</style>