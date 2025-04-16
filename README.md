// stores/userStore.ts
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    user: {
      name: '',
      age: 0
    }
  }),

  getters: {
    getName: (state) => state.user.name,
    getAge: (state) => state.user.age
  },

  actions: {
    setName(newName: string) {
      this.user.name = newName
    },
    setAge(newAge: number) {
      this.user.age = newAge
    }
  }
})
