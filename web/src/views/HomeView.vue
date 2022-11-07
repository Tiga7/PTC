<template>
    <CardViewVue>
        <Row>
            <Col span="14">col-12</Col>
            <Col span="8" offset="2">
            <Card dis-hover>
                <template #title>

                </template>
                <p>Content of card</p>
                <p>Content of card</p>
                <p>Content of card</p>
            </Card>
            </Col>
        </Row>
    </CardViewVue>
</template>

<script>
// @ is an alias to /src
import CardViewVue from '@/components/CardView.vue';
export default {
    components: {
        CardViewVue
    },

}
</script>
<style scoped>
.box-card {
    text-align: center;
    width: 100%;
    margin-top: 0px;
    /* padding-top: 20px; */
}

Image {
    border-radius: 50%;
}
</style>