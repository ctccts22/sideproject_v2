import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore('user', () => {
  // State
  const name = ref<string>('')
  const age = ref<number>(0)
  const isLoggedIn = ref<boolean>(false)

  // Getters
  const userInfo = computed(() => {
    return {
      name: name.value,
      age: age.value,
      isLoggedIn: isLoggedIn.value,
    }
  })

  // Actions
  function setName(newName: string) {
    name.value = newName
  }

  function setAge(newAge: number) {
    age.value = newAge
  }

  function logIn() {
    isLoggedIn.value = true
  }

  function logOut() {
    isLoggedIn.value = false
  }

  return {
    name,
    age,
    isLoggedIn,
    userInfo,
    setName,
    setAge,
    logIn,
    logOut,
  }
})
