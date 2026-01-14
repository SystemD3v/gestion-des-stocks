<template>
  <div class="product-detail">
    <div v-if="loading" class="loading">Chargement...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="product" class="product-container">
      <div class="product-main">
        <div class="product-image-placeholder">
          <span class="wine-icon">🍷</span>
        </div>

        <div class="product-content">
          <h1>{{ product.label }}</h1>
          <div class="product-badges">
            <span class="badge badge-genre">{{ product.genre }}</span>
            <span class="badge badge-domain">{{ product.area }}</span>
            <span class="badge badge-year">{{ product.years }}</span>
          </div>

          <div class="product-price">
            <span class="price-amount">{{ product.price.toFixed(2) }} €</span>
            <span class="price-unit">/ bouteille</span>
          </div>

          <div class="product-stock">
            <span v-if="product.available_quantity > 0" class="stock-available">
              ✓ {{ product.available_quantity }} bouteilles disponibles
            </span>
            <span v-else class="stock-unavailable">✗ Rupture de stock</span>
          </div>

          <div v-if="product.available_quantity > 0" class="product-quantity">
            <label>Quantité :</label>
            <div class="quantity-control">
              <button @click="decrementQuantity" class="qty-btn">-</button>
              <input v-model.number="quantity" type="number" min="1" :max="product.available_quantity" class="qty-input" />
              <button @click="incrementQuantity" class="qty-btn">+</button>
            </div>
          </div>

          <div class="product-actions">
            <button
              v-if="product.available_quantity > 0"
              @click="handleAddToCart"
              class="btn-add-to-cart"
            >
              Ajouter au panier - {{ (product.price * quantity).toFixed(2) }} €
            </button>
            <RouterLink to="/catalog" class="btn-back">Retour au catalogue</RouterLink>
          </div>

          <div v-if="showSuccess" class="success-message">
            ✓ Produit ajouté au panier !
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { stockAPI, type CaveStock } from '@/services/api'
import { useCartStore } from '@/stores/cart'

const route = useRoute()
const cartStore = useCartStore()

const product = ref<CaveStock | null>(null)
const loading = ref(true)
const error = ref('')
const quantity = ref(1)
const showSuccess = ref(false)

function incrementQuantity() {
  if (product.value && quantity.value < product.value.available_quantity) {
    quantity.value++
  }
}

function decrementQuantity() {
  if (quantity.value > 1) {
    quantity.value--
  }
}

function handleAddToCart() {
  if (product.value) {
    cartStore.addToCart(product.value, quantity.value)
    showSuccess.value = true
    setTimeout(() => {
      showSuccess.value = false
    }, 3000)
  }
}

async function loadProduct() {
  loading.value = true
  error.value = ''

  try {
    const productId = parseInt(route.params.id as string)
    const response = await stockAPI.getStockById(productId)
    product.value = response.data as CaveStock
  } catch (err) {
    error.value = 'Produit introuvable'
    console.error(err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadProduct()
})
</script>

<style scoped>
.product-detail {
  max-width: 1200px;
  margin: 0 auto;
}

.loading,
.error {
  text-align: center;
  padding: 4rem 2rem;
  font-size: 1.2rem;
}

.error {
  color: #c33;
}

.product-container {
  background: white;
  border-radius: 12px;
  padding: 3rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.product-main {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 3rem;
}

.product-image-placeholder {
  aspect-ratio: 1;
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wine-icon {
  font-size: 8rem;
}

.product-content {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.product-content h1 {
  font-size: 2.5rem;
  color: #7c1e3f;
  margin: 0;
}

.product-badges {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.badge {
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-weight: 600;
  font-size: 0.9rem;
}

.badge-genre {
  background-color: #7c1e3f;
  color: white;
}

.badge-domain {
  background-color: #f0f0f0;
  color: #333;
}

.badge-year {
  background-color: #e8f4f8;
  color: #0066cc;
}

.product-price {
  display: flex;
  align-items: baseline;
  gap: 0.5rem;
}

.price-amount {
  font-size: 3rem;
  font-weight: 700;
  color: #7c1e3f;
}

.price-unit {
  font-size: 1.2rem;
  color: #666;
}

.product-stock {
  padding: 1rem;
  border-radius: 8px;
  font-weight: 600;
}

.stock-available {
  color: #28a745;
}

.stock-unavailable {
  color: #dc3545;
}

.product-quantity {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.product-quantity label {
  font-weight: 600;
  font-size: 1.1rem;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.qty-btn {
  width: 40px;
  height: 40px;
  border: 2px solid #7c1e3f;
  background: white;
  color: #7c1e3f;
  border-radius: 6px;
  font-size: 1.5rem;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
}

.qty-btn:hover {
  background: #7c1e3f;
  color: white;
}

.qty-input {
  width: 80px;
  height: 40px;
  text-align: center;
  border: 2px solid #ddd;
  border-radius: 6px;
  font-size: 1.1rem;
  font-weight: 600;
}

.product-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
}

.btn-add-to-cart,
.btn-back {
  padding: 1rem 2rem;
  border-radius: 8px;
  font-weight: 600;
  font-size: 1.1rem;
  text-decoration: none;
  text-align: center;
  border: none;
  cursor: pointer;
  transition: transform 0.2s;
}

.btn-add-to-cart {
  flex: 2;
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
}

.btn-add-to-cart:hover {
  transform: translateY(-2px);
}

.btn-back {
  flex: 1;
  background-color: #f0f0f0;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-back:hover {
  background-color: #e0e0e0;
}

.success-message {
  background-color: #d4edda;
  border: 1px solid #c3e6cb;
  color: #155724;
  padding: 1rem;
  border-radius: 6px;
  text-align: center;
  font-weight: 600;
}

@media (max-width: 768px) {
  .product-main {
    grid-template-columns: 1fr;
  }

  .product-content h1 {
    font-size: 1.75rem;
  }

  .price-amount {
    font-size: 2rem;
  }

  .product-actions {
    flex-direction: column;
  }
}
</style>
