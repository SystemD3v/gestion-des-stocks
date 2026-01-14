<template>
  <div class="catalog">
    <h1>Notre Catalogue</h1>

    <div class="filters-section">
      <div class="filters">
        <div class="filter-group">
          <label>Genre</label>
          <select v-model="filters.genre" class="filter-select">
            <option value="">Tous</option>
            <option value="Rouge">Rouge</option>
            <option value="Rosé">Rosé</option>
            <option value="Blanc">Blanc</option>
            <option value="Pétillant">Pétillant</option>
            <option value="Digestif">Digestif</option>
          </select>
        </div>

        <div class="filter-group">
          <label>Domaine</label>
          <select v-model="filters.area" class="filter-select">
            <option value="">Tous</option>
            <option value="Tariquet">Tariquet</option>
            <option value="Pelleheaut">Pelleheaut</option>
            <option value="Joy">Joy</option>
            <option value="Vignoble Fontan">Vignoble Fontan</option>
            <option value="Uby">Uby</option>
          </select>
        </div>

        <div class="filter-group">
          <label>Prix max</label>
          <input
            v-model.number="filters.maxPrice"
            type="number"
            placeholder="€"
            class="filter-input"
            min="0"
          />
        </div>

        <div class="filter-group">
          <label>Trier par</label>
          <select v-model="sortBy" class="filter-select">
            <option value="name">Nom</option>
            <option value="price-asc">Prix (croissant)</option>
            <option value="price-desc">Prix (décroissant)</option>
            <option value="year">Année</option>
          </select>
        </div>

        <button @click="resetFilters" class="btn-reset">Réinitialiser</button>
      </div>
    </div>

    <div v-if="loading" class="loading">
      <p>Chargement des produits...</p>
    </div>

    <div v-else-if="error" class="error">
      <p>{{ error }}</p>
    </div>

    <div v-else-if="filteredProducts.length === 0" class="no-products">
      <p>Aucun produit ne correspond à vos critères</p>
    </div>

    <div v-else class="products-grid">
      <div v-for="product in filteredProducts" :key="product.id" class="product-card">
        <div class="product-info">
          <h3 class="product-label">{{ product.label }}</h3>
          <p class="product-genre">{{ product.genre }}</p>
          <p class="product-details">{{ product.area }} - {{ product.years }}</p>
          <p class="product-price">{{ product.price.toFixed(2) }} €</p>
          <p class="product-stock" :class="{ 'low-stock': product.available_quantity < 10 }">
            {{ product.available_quantity > 0 ? `${product.available_quantity} en stock` : 'Rupture de stock' }}
          </p>
        </div>
        <div class="product-actions">
          <RouterLink :to="`/product/${product.id}`" class="btn-details">
            Voir détails
          </RouterLink>
          <button
            v-if="product.available_quantity > 0"
            @click="addToCart(product)"
            class="btn-add-cart"
          >
            Ajouter au panier
          </button>
          <button v-else class="btn-disabled" disabled>
            Indisponible
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { stockAPI, type CaveStock } from '@/services/api'
import { useCartStore } from '@/stores/cart'

const cartStore = useCartStore()

const products = ref<CaveStock[]>([])
const loading = ref(true)
const error = ref('')

const filters = ref({
  genre: '',
  area: '',
  maxPrice: null as number | null,
})

const sortBy = ref('name')

const filteredProducts = computed(() => {
  let result = [...products.value]

  if (filters.value.genre) {
    result = result.filter((p) => p.genre === filters.value.genre)
  }

  if (filters.value.area) {
    result = result.filter((p) => p.area === filters.value.area)
  }

  if (filters.value.maxPrice !== null && filters.value.maxPrice > 0) {
    result = result.filter((p) => p.price <= filters.value.maxPrice!)
  }

  switch (sortBy.value) {
    case 'price-asc':
      result.sort((a, b) => a.price - b.price)
      break
    case 'price-desc':
      result.sort((a, b) => b.price - a.price)
      break
    case 'year':
      result.sort((a, b) => b.years - a.years)
      break
    case 'name':
    default:
      result.sort((a, b) => a.label.localeCompare(b.label))
      break
  }

  return result
})

function resetFilters() {
  filters.value = {
    genre: '',
    area: '',
    maxPrice: null,
  }
  sortBy.value = 'name'
}

function addToCart(product: CaveStock) {
  cartStore.addToCart(product, 1)
}

async function loadProducts() {
  loading.value = true
  error.value = ''

  try {
    const response = await stockAPI.getAllStock()
    products.value = response.data
  } catch (err) {
    error.value = 'Erreur lors du chargement des produits'
    console.error(err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadProducts()
})
</script>

<style scoped>
.catalog h1 {
  font-size: 2.5rem;
  color: #7c1e3f;
  margin-bottom: 2rem;
}

.filters-section {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  margin-bottom: 2rem;
}

.filters {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  align-items: end;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.filter-group label {
  font-weight: 600;
  color: #333;
  font-size: 0.9rem;
}

.filter-select,
.filter-input {
  padding: 0.75rem;
  border: 2px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.filter-select:focus,
.filter-input:focus {
  outline: none;
  border-color: #7c1e3f;
}

.btn-reset {
  padding: 0.75rem 1.5rem;
  background-color: #f0f0f0;
  border: none;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn-reset:hover {
  background-color: #e0e0e0;
}

.loading,
.error,
.no-products {
  text-align: center;
  padding: 4rem 2rem;
  font-size: 1.2rem;
  color: #666;
}

.error {
  color: #c33;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
}

.product-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.product-card:hover {
  transform: translateY(-5px);
}

.product-info {
  flex: 1;
}

.product-label {
  font-size: 1.5rem;
  color: #7c1e3f;
  margin-bottom: 0.5rem;
}

.product-genre {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  background-color: #7c1e3f;
  color: white;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.product-details {
  color: #666;
  margin: 0.5rem 0;
}

.product-price {
  font-size: 1.75rem;
  font-weight: 700;
  color: #7c1e3f;
  margin: 1rem 0 0.5rem 0;
}

.product-stock {
  font-size: 0.9rem;
  color: #666;
}

.product-stock.low-stock {
  color: #ff6b00;
  font-weight: 600;
}

.product-actions {
  display: flex;
  gap: 0.75rem;
}

.btn-details,
.btn-add-cart,
.btn-disabled {
  flex: 1;
  padding: 0.875rem;
  border-radius: 6px;
  font-weight: 600;
  text-decoration: none;
  text-align: center;
  border: none;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-details {
  background-color: #f0f0f0;
  color: #333;
}

.btn-details:hover {
  background-color: #e0e0e0;
}

.btn-add-cart {
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
}

.btn-add-cart:hover {
  transform: translateY(-2px);
}

.btn-disabled {
  background-color: #ccc;
  color: #666;
  cursor: not-allowed;
}

@media (max-width: 768px) {
  .filters {
    grid-template-columns: 1fr;
  }

  .products-grid {
    grid-template-columns: 1fr;
  }
}
</style>
