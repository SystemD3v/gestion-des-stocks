<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useCartStore } from '@/stores/cart'

const authStore = useAuthStore()
const cartStore = useCartStore()

onMounted(() => {
  authStore.checkAuth()
  cartStore.loadCart()
})
</script>

<template>
  <div class="app-container">
    <header class="header">
      <div class="header-content">
        <RouterLink to="/" class="logo">
          <h1>🍷 NEGOSUD</h1>
        </RouterLink>

        <nav class="nav">
          <RouterLink to="/" class="nav-link">Accueil</RouterLink>
          <RouterLink to="/catalog" class="nav-link">Catalogue</RouterLink>
          <RouterLink to="/contact" class="nav-link">Contact</RouterLink>
        </nav>

        <div class="header-actions">
          <RouterLink to="/cart" class="cart-link">
            🛒 Panier
            <span v-if="cartStore.totalItems > 0" class="cart-badge">
              {{ cartStore.totalItems }}
            </span>
          </RouterLink>

          <div v-if="authStore.isAuthenticated" class="user-menu">
            <span class="user-name">{{ authStore.user?.firstname }}</span>
            <RouterLink to="/orders" class="nav-link">Mes commandes</RouterLink>
            <button @click="authStore.logout()" class="btn-logout">Déconnexion</button>
          </div>
          <div v-else class="auth-links">
            <RouterLink to="/login" class="btn-login">Connexion</RouterLink>
            <RouterLink to="/register" class="btn-register">Inscription</RouterLink>
          </div>
        </div>
      </div>
    </header>

    <main class="main-content">
      <RouterView />
    </main>

    <footer class="footer">
      <div class="footer-content">
        <p>&copy; 2025 NEGOSUD - Négociant en vins de Gascogne</p>
        <p>Tariquet, Pelleheaut, Joy, Vignoble Fontan, Uby</p>
      </div>
    </footer>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
  background-color: #f8f9fa;
}

.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 2rem;
}

.logo {
  text-decoration: none;
  color: white;
}

.logo h1 {
  font-size: 1.8rem;
  font-weight: 700;
  letter-spacing: 1px;
}

.nav {
  display: flex;
  gap: 2rem;
  flex: 1;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: 500;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-link:hover,
.nav-link.router-link-active {
  background-color: rgba(255, 255, 255, 0.1);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.cart-link {
  color: white;
  text-decoration: none;
  font-size: 1.1rem;
  position: relative;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.cart-link:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.cart-badge {
  position: absolute;
  top: -5px;
  right: -5px;
  background-color: #ff4444;
  color: white;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.75rem;
  font-weight: bold;
}

.user-menu {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-name {
  font-weight: 600;
}

.auth-links {
  display: flex;
  gap: 1rem;
}

.btn-login,
.btn-register,
.btn-logout {
  padding: 0.5rem 1.5rem;
  border-radius: 4px;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.3s;
  border: none;
  cursor: pointer;
  font-size: 1rem;
}

.btn-login {
  background-color: transparent;
  color: white;
  border: 2px solid white;
}

.btn-login:hover {
  background-color: white;
  color: #7c1e3f;
}

.btn-register {
  background-color: white;
  color: #7c1e3f;
}

.btn-register:hover {
  background-color: #f0f0f0;
}

.btn-logout {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
}

.btn-logout:hover {
  background-color: rgba(255, 255, 255, 0.3);
}

.main-content {
  flex: 1;
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
  width: 100%;
}

.footer {
  background-color: #2c2c2c;
  color: white;
  padding: 2rem;
  text-align: center;
  margin-top: auto;
}

.footer-content p {
  margin: 0.5rem 0;
}

@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    align-items: stretch;
  }

  .nav {
    flex-direction: column;
    gap: 0.5rem;
  }

  .header-actions {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>
