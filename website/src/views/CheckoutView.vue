<template>
  <div class="checkout">
    <h1>Finaliser la commande</h1>

    <div v-if="cartStore.items.length === 0" class="empty-message">
      <p>Votre panier est vide</p>
      <RouterLink to="/catalog" class="btn-link">Retour au catalogue</RouterLink>
    </div>

    <div v-else class="checkout-content">
      <div class="checkout-form">
        <div class="section">
          <h2>Informations de livraison</h2>
          <div class="form-grid">
            <div class="form-group">
              <label>Prénom *</label>
              <input v-model="shippingInfo.firstname" type="text" required class="form-input" />
            </div>
            <div class="form-group">
              <label>Nom *</label>
              <input v-model="shippingInfo.lastname" type="text" required class="form-input" />
            </div>
            <div class="form-group full-width">
              <label>Adresse *</label>
              <textarea v-model="shippingInfo.address" required class="form-input" rows="3"></textarea>
            </div>
            <div class="form-group">
              <label>Téléphone *</label>
              <input v-model="shippingInfo.phone" type="tel" required class="form-input" />
            </div>
            <div class="form-group">
              <label>Email *</label>
              <input v-model="shippingInfo.email" type="email" required class="form-input" />
            </div>
          </div>
        </div>

        <div class="section">
          <h2>Paiement</h2>
          <p class="payment-info">💳 Utilisez une carte de test Stripe</p>
          <div id="card-element" class="stripe-card"></div>
          <div v-if="paymentError" class="error-message">{{ paymentError }}</div>
        </div>

        <button @click="handlePayment" class="btn-pay" :disabled="processing">
          {{ processing ? 'Traitement...' : `Payer ${cartStore.totalPrice.toFixed(2)} €` }}
        </button>
      </div>

      <div class="order-summary">
        <h2>Récapitulatif</h2>
        <div class="summary-items">
          <div v-for="item in cartStore.items" :key="item.product.id" class="summary-item">
            <span>{{ item.product.label }} x{{ item.quantity }}</span>
            <span>{{ (item.product.price * item.quantity).toFixed(2) }} €</span>
          </div>
        </div>
        <div class="summary-total">
          <span>Total</span>
          <span>{{ cartStore.totalPrice.toFixed(2) }} €</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import { orderAPI } from '@/services/api'
import { loadStripe, type Stripe, type StripeElements } from '@stripe/stripe-js'

const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()

const shippingInfo = ref({
  firstname: authStore.user?.firstname || '',
  lastname: authStore.user?.lastname || '',
  address: authStore.user?.address || '',
  phone: authStore.user?.phone_number || '',
  email: authStore.user?.email || '',
})

const processing = ref(false)
const paymentError = ref('')

let stripe: Stripe | null = null
let cardElement: any = null

onMounted(async () => {
  stripe = await loadStripe('pk_test_51SaGFU3Vt6UEOJ18WE6maCkCXFDVPlMOE3gk9e5DVCUF1TtFXsYJo1YydXIdw09XmuoI7db5ZDSNoL2K0jC7D9lp00ohLKfDC8')

  if (stripe) {
    const elements = stripe.elements()
    cardElement = elements.create('card', {
      style: {
        base: {
          fontSize: '16px',
          color: '#32325d',
          fontFamily: '"Segoe UI", Tahoma, Geneva, Verdana, sans-serif',
          '::placeholder': {
            color: '#aab7c4',
          },
        },
      },
    })
    cardElement.mount('#card-element')
  }
})

async function handlePayment() {
  if (!stripe || !cardElement) {
    paymentError.value = 'Stripe non initialisé'
    return
  }

  if (!shippingInfo.value.firstname || !shippingInfo.value.lastname || !shippingInfo.value.address) {
    paymentError.value = 'Veuillez remplir tous les champs obligatoires'
    return
  }

  processing.value = true
  paymentError.value = ''

  try {
    // Create payment method
    const { error, paymentMethod } = await stripe.createPaymentMethod({
      type: 'card',
      card: cardElement,
      billing_details: {
        name: `${shippingInfo.value.firstname} ${shippingInfo.value.lastname}`,
        email: shippingInfo.value.email,
        phone: shippingInfo.value.phone,
        address: {
          line1: shippingInfo.value.address,
        },
      },
    })

    if (error) {
      paymentError.value = error.message || 'Erreur de paiement'
      processing.value = false
      return
    }

    // For demo purposes, simulate successful payment
    // In production, you would send paymentMethod.id to your backend
    console.log('Payment Method created:', paymentMethod.id)

    // Create orders in database
    await createOrders()

    // Clear cart and redirect
    cartStore.clearCart()
    router.push('/orders?success=true')

  } catch (err) {
    console.error('Payment error:', err)
    paymentError.value = 'Une erreur est survenue'
    processing.value = false
  }
}

async function createOrders() {
  if (!authStore.user) return

  for (const item of cartStore.items) {
    try {
      await orderAPI.createHandler({
        stockId: item.product.id,
        userId: authStore.user.id!,
        requestAmount: item.quantity,
        operation: 1,
        validated: true,
        completed: false,
        orderTimestamp: new Date().toISOString(),
      })
    } catch (err) {
      console.error('Erreur création commande:', err)
    }
  }
}
</script>

<style scoped>
.checkout h1 {
  font-size: 2.5rem;
  color: #7c1e3f;
  margin-bottom: 2rem;
}

.empty-message {
  text-align: center;
  padding: 4rem;
  background: white;
  border-radius: 12px;
}

.checkout-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 2rem;
}

.checkout-form {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.section {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.section h2 {
  font-size: 1.5rem;
  color: #7c1e3f;
  margin-bottom: 1.5rem;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.full-width {
  grid-column: 1 / -1;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-weight: 600;
  color: #333;
}

.form-input {
  padding: 0.875rem;
  border: 2px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
  font-family: inherit;
}

.form-input:focus {
  outline: none;
  border-color: #7c1e3f;
}

.payment-info {
  color: #666;
  margin-bottom: 1rem;
  font-size: 0.95rem;
}

.stripe-card {
  padding: 1rem;
  border: 2px solid #ddd;
  border-radius: 6px;
  background: white;
  margin-top: 1rem;
}

#card-element {
  padding: 0.5rem 0;
}

.error-message {
  background-color: #fee;
  border: 1px solid #fcc;
  color: #c33;
  padding: 1rem;
  border-radius: 6px;
  margin-top: 1rem;
}

.btn-pay {
  padding: 1.25rem;
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.2rem;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s;
}

.btn-pay:hover:not(:disabled) {
  transform: translateY(-2px);
}

.btn-pay:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.order-summary {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  height: fit-content;
  position: sticky;
  top: 100px;
}

.order-summary h2 {
  font-size: 1.5rem;
  color: #7c1e3f;
  margin-bottom: 1.5rem;
}

.summary-items {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  margin-bottom: 1.5rem;
}

.summary-item {
  display: flex;
  justify-content: space-between;
  font-size: 0.95rem;
}

.summary-total {
  display: flex;
  justify-content: space-between;
  padding-top: 1rem;
  border-top: 2px solid #ddd;
  font-weight: 700;
  font-size: 1.3rem;
  color: #7c1e3f;
}

@media (max-width: 968px) {
  .checkout-content {
    grid-template-columns: 1fr;
  }

  .form-grid {
    grid-template-columns: 1fr;
  }

  .order-summary {
    position: static;
  }
}
</style>
