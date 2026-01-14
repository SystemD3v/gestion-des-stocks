<template>
  <div class="cart">
    <h1>Mon Panier</h1>

    <div v-if="cartStore.items.length === 0" class="empty-cart">
      <p>Votre panier est vide</p>
      <RouterLink to="/catalog" class="btn-shop">Découvrir nos vins</RouterLink>
    </div>

    <div v-else class="cart-content">
      <div class="cart-items">
        <div v-for="item in cartStore.items" :key="item.product.id" class="cart-item">
          <div class="item-info">
            <h3>{{ item.product.label }}</h3>
            <p>{{ item.product.genre }} - {{ item.product.area }} ({{ item.product.years }})</p>
            <p class="item-price">{{ item.product.price.toFixed(2) }} € / unité</p>
          </div>

          <div class="item-controls">
            <div class="quantity-control">
              <button @click="cartStore.updateQuantity(item.product.id, item.quantity - 1)" class="qty-btn">-</button>
              <span class="qty-display">{{ item.quantity }}</span>
              <button @click="cartStore.updateQuantity(item.product.id, item.quantity + 1)" class="qty-btn">+</button>
            </div>

            <div class="item-total">
              {{ (item.product.price * item.quantity).toFixed(2) }} €
            </div>

            <button @click="cartStore.removeFromCart(item.product.id)" class="btn-remove">
              🗑️
            </button>
          </div>
        </div>
      </div>

      <div class="cart-summary">
        <h2>Récapitulatif</h2>
        <div class="summary-line">
          <span>Articles ({{ cartStore.totalItems }})</span>
          <span>{{ cartStore.totalPrice.toFixed(2) }} €</span>
        </div>
        <div class="summary-line summary-total">
          <span>Total</span>
          <span>{{ cartStore.totalPrice.toFixed(2) }} €</span>
        </div>

        <RouterLink to="/checkout" class="btn-checkout">
          Procéder au paiement
        </RouterLink>
        <RouterLink to="/catalog" class="btn-continue">
          Continuer mes achats
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { RouterLink } from 'vue-router'
import { useCartStore } from '@/stores/cart'

const cartStore = useCartStore()
</script>

<style scoped>
.cart h1 {
  font-size: 2.5rem;
  color: #7c1e3f;
  margin-bottom: 2rem;
}

.empty-cart {
  text-align: center;
  padding: 4rem 2rem;
  background: white;
  border-radius: 12px;
}

.empty-cart p {
  font-size: 1.5rem;
  color: #666;
  margin-bottom: 2rem;
}

.btn-shop {
  display: inline-block;
  padding: 1rem 2rem;
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-weight: 600;
}

.cart-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 2rem;
}

.cart-items {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.cart-item {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 2rem;
}

.item-info h3 {
  color: #7c1e3f;
  margin-bottom: 0.5rem;
}

.item-info p {
  color: #666;
  margin: 0.25rem 0;
}

.item-price {
  font-weight: 600;
  color: #333;
}

.item-controls {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.qty-btn {
  width: 32px;
  height: 32px;
  border: 2px solid #7c1e3f;
  background: white;
  color: #7c1e3f;
  border-radius: 4px;
  font-size: 1.2rem;
  cursor: pointer;
  transition: all 0.2s;
}

.qty-btn:hover {
  background: #7c1e3f;
  color: white;
}

.qty-display {
  width: 40px;
  text-align: center;
  font-weight: 600;
  font-size: 1.1rem;
}

.item-total {
  font-size: 1.3rem;
  font-weight: 700;
  color: #7c1e3f;
  min-width: 100px;
  text-align: right;
}

.btn-remove {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  opacity: 0.6;
  transition: opacity 0.2s;
}

.btn-remove:hover {
  opacity: 1;
}

.cart-summary {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  height: fit-content;
  position: sticky;
  top: 100px;
}

.cart-summary h2 {
  font-size: 1.5rem;
  color: #7c1e3f;
  margin-bottom: 1.5rem;
}

.summary-line {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
  font-size: 1.1rem;
}

.summary-total {
  padding-top: 1rem;
  border-top: 2px solid #ddd;
  font-weight: 700;
  font-size: 1.3rem;
  color: #7c1e3f;
  margin-top: 1rem;
}

.btn-checkout,
.btn-continue {
  display: block;
  width: 100%;
  padding: 1rem;
  border-radius: 8px;
  text-align: center;
  text-decoration: none;
  font-weight: 600;
  margin-top: 1rem;
  transition: transform 0.2s;
}

.btn-checkout {
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
}

.btn-checkout:hover {
  transform: translateY(-2px);
}

.btn-continue {
  background-color: #f0f0f0;
  color: #333;
}

.btn-continue:hover {
  background-color: #e0e0e0;
}

@media (max-width: 968px) {
  .cart-content {
    grid-template-columns: 1fr;
  }

  .cart-summary {
    position: static;
  }

  .cart-item {
    flex-direction: column;
    align-items: stretch;
  }

  .item-controls {
    justify-content: space-between;
  }
}
</style>
