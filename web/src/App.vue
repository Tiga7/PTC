<template>
  <NavBar></NavBar>
  <router-view />
</template>
<script>
// 引入ui
import NavBar from "../src/components/NavBar.vue";

export default {
  components: {
    NavBar
  },
  setup() {

  }

}
</script>
<style>
/* #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;  
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
} */
body {
  background: url("@/assets/image/3c2.png");
  background-size: cover;
}
</style>
