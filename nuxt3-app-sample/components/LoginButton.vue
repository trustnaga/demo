<script setup>
import { useUserStore} from "~/stores/auth";
import { storeToRefs } from 'pinia'
 const { $msalInstance } = useNuxtApp();
 const userStore = useUserStore()

async function login() {
    try {
    const response = await $msalInstance.loginPopup({
        scopes: ["openid profile email api://a5948bd0-10cd-42c7-b066-264ff114ddb9/Api.Read"],
    });
   
    userStore.$patch((state) => {
      console.log(response.accessToken);  
      state.accessToken = response.accessToken;
    });
    console.log(response);
    console.log(userStore.accessToken);
    } catch (error) {
    console.log(error);
    }
}

</script>

<template>
    <v-btn @click="login">Login</v-btn>
</template>

