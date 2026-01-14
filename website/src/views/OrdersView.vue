<template>
  <div class="orders">
    <h1>Mes Commandes</h1>

    <div v-if="successParam" class="success-banner">
      ✓ Votre commande a été passée avec succès ! Merci pour votre achat.
    </div>

    <div v-if="loading" class="loading">Chargement de vos commandes...</div>

    <div v-else-if="error" class="error">{{ error }}</div>

    <div v-else-if="orders.length === 0" class="no-orders">
      <p>Vous n'avez pas encore passé de commande</p>
      <RouterLink to="/catalog" class="btn-shop">Découvrir nos vins</RouterLink>
    </div>

    <div v-else class="orders-list">
      <div v-for="order in orders" :key="order.id" class="order-card">
        <div class="order-header">
          <div class="order-info">
            <h3>Commande #{{ order.id }}</h3>
            <p class="order-date">{{ formatDate(order.orderTimestamp) }}</p>
          </div>
          <div class="order-status">
            <span v-if="!order.validated" class="status-badge status-pending">En attente</span>
            <span v-else-if="!order.completed" class="status-badge status-validated">Validée</span>
            <span v-else class="status-badge status-completed">Livrée</span>
          </div>
        </div>

        <div class="order-details">
          <div v-if="order.id && orderProducts[order.id]" class="order-product">
            <div class="product-info">
              <h4>{{ orderProducts[order.id]?.label }}</h4>
              <p>{{ orderProducts[order.id]?.genre }} - {{ orderProducts[order.id]?.area }}</p>
            </div>
            <div class="product-quantity">
              <span>Quantité : {{ order.requestAmount }}</span>
              <span class="product-price">{{ ((orderProducts[order.id]?.price || 0) * order.requestAmount).toFixed(2) }} €</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { orderAPI, stockAPI, type CaveHandler, type CaveStock } from '@/services/api'
import { useAuthStore } from '@/stores/auth'

const route = useRoute()
const authStore = useAuthStore()

const orders = ref<CaveHandler[]>([])
const orderProducts = ref<Record<number, CaveStock>>({})
const loading = ref(true)
const error = ref('')

const successParam = computed(() => route.query.success === 'true')

function formatDate(timestamp: string) {
  const date = new Date(timestamp)
  return date.toLocaleDateString('fr-FR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  })
}

async function loadOrders() {
  if (!authStore.user) {
    error.value = 'Utilisateur non connecté'
    loading.value = false
    return
  }

  loading.value = true
  error.value = ''

  try {
    const response = await orderAPI.getHandlerByUserId(authStore.user.id!)
    orders.value = response.data.sort((a, b) =>
      new Date(b.orderTimestamp).getTime() - new Date(a.orderTimestamp).getTime()
    )

    for (const order of orders.value) {
      try {
        const productResponse = await stockAPI.getStockById(order.stockId)
        orderProducts.value[order.id!] = productResponse.data as CaveStock
      } catch (err) {
        console.error('Erreur chargement produit:', err)
      }
    }
  } catch (err) {
    error.value = 'Erreur lors du chargement des commandes'
    console.error(err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.orders h1 {
  font-size: 2.5rem;
  color: #7c1e3f;
  margin-bottom: 2rem;
}

.success-banner {
  background-color: #d4edda;
  border: 1px solid #c3e6cb;
  color: #155724;
  padding: 1.5rem;
  border-radius: 8px;
  text-align: center;
  font-weight: 600;
  font-size: 1.1rem;
  margin-bottom: 2rem;
}

.loading,
.error,
.no-orders {
  text-align: center;
  padding: 4rem 2rem;
  background: white;
  border-radius: 12px;
}

.error {
  color: #c33;
}

.no-orders p {
  font-size: 1.2rem;
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

.orders-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.order-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 2px solid #f0f0f0;
}

.order-info h3 {
  font-size: 1.5rem;
  color: #7c1e3f;
  margin-bottom: 0.5rem;
}

.order-date {
  color: #666;
  font-size: 0.95rem;
}

.status-badge {
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-weight: 600;
  font-size: 0.9rem;
}

.status-pending {
  background-color: #fff3cd;
  color: #856404;
}

.status-validated {
  background-color: #cce5ff;
  color: #004085;
}

.status-completed {
  background-color: #d4edda;
  color: #155724;
}

.order-product {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.product-info h4 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 0.25rem;
}

.product-info p {
  color: #666;
  font-size: 0.95rem;
}

.product-quantity {
  text-align: right;
}

.product-quantity span {
  display: block;
  margin-bottom: 0.25rem;
}

.product-price {
  font-size: 1.3rem;
  font-weight: 700;
  color: #7c1e3f;
}

@media (max-width: 768px) {
  .order-header {
    flex-direction: column;
    gap: 1rem;
  }

  .order-product {
    flex-direction: column;
    align-items: stretch;
    gap: 1rem;
  }

  .product-quantity {
    text-align: left;
  }
}
</style>
