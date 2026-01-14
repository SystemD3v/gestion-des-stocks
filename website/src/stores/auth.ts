import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import { userAPI, type CaveUser } from '@/services/api';

export const useAuthStore = defineStore('auth', () => {
  const user = ref<CaveUser | null>(null);
  const isAuthenticated = computed(() => user.value !== null);

  // Simple login - check if user exists
  async function login(email: string, password: string) {
    try {
      const response = await userAPI.getAllUsers();
      const foundUser = response.data.find(
        (u) => u.email === email && u.password === password
      );

      if (foundUser) {
        user.value = foundUser;
        localStorage.setItem('userId', foundUser.id!.toString());
        localStorage.setItem('userEmail', foundUser.email);
        return true;
      }
      return false;
    } catch (error) {
      console.error('Login error:', error);
      return false;
    }
  }

  async function register(userData: CaveUser) {
    try {
      // Set default values
      userData.role = 'customer';
      userData.total_bottles_bought = 0;

      const response = await userAPI.createUser(userData);

      // After registration, fetch the user to get the ID
      const allUsers = await userAPI.getAllUsers();
      const newUser = allUsers.data.find((u) => u.email === userData.email);

      if (newUser) {
        user.value = newUser;
        localStorage.setItem('userId', newUser.id!.toString());
        localStorage.setItem('userEmail', newUser.email);
        return true;
      }
      return false;
    } catch (error) {
      console.error('Registration error:', error);
      return false;
    }
  }

  function logout() {
    user.value = null;
    localStorage.removeItem('userId');
    localStorage.removeItem('userEmail');
  }

  async function checkAuth() {
    const userId = localStorage.getItem('userId');
    if (userId) {
      try {
        const response = await userAPI.getUserById(parseInt(userId));
        if (response.data && response.data.length > 0) {
          user.value = response.data[0] || null;
        }
      } catch (error) {
        console.error('Auth check error:', error);
        logout();
      }
    }
  }

  return {
    user,
    isAuthenticated,
    login,
    register,
    logout,
    checkAuth,
  };
});
