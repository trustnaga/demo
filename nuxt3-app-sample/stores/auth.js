import { defineStore } from 'pinia'

export const useUserStore   = defineStore('userStore', {
    state: () => ({ 
        accessToken: null 
    }),
  })