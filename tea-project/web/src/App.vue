<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

html, body, #app {
    height: 100%;
}

.container-shadow {
    box-shadow: 0 0 20px #ccc;
    /*margin:20px 20px;*/
    padding: 20px;
    box-sizing: border-box;
    border-radius: 15px;
}

</style>
