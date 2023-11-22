<template>
   <LoginButton></LoginButton>

   <p><a @click="getMedata" href="#">Get Me Data</a></p>
   {{ userData }}
</template>
<script setup>
import { useUserStore} from "~/stores/auth";
const userData=ref({})
const userStore = useUserStore()

   function getMedata() {
      console.log(userStore.accessToken)
      $fetch("http://localhost:8082/user", {
         method: "GET",
         headers: {
            Authorization: "Bearer " + userStore.accessToken,
         },
      })
         .then((response) => response.json())
         .then((data) => {
            console.log(data);
            userData.value=data
         })
     
   }
</script>