<template>
  <CardView>
    <label for="username">aaaaaaaaaaa</label>
    <input id="username" name="asjdfk" type="number" style="width: 300px" />
  </CardView>
  <div>

    <form id="form">
      <input type="text" placeholder="text input">
      <input type="password" placeholder="password">
    </form>
  </div>
</template>

<script>

import CardView from '@/components/CardView.vue';
import { onMounted } from 'vue';


export default {
  components: {
    CardView
  },

  setup() {
    onMounted(() => {
      const form = document.getElementById('form');
      form.addEventListener('keydown', (event) => {
        console.log(event.key)
        // event.target.style.background = 'pink';
      }, true);
    })
    if (typeof (WebSocket) == "undefined") {
      console.log("您的浏览器不支持WebSocket");
    }
    return {
      value: ""
    }
  }


}
</script>

<style lang="scss" scoped>

</style>