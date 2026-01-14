<template>
  <div class="auth-container">
    <div class="auth-card">
      <h2>Inscription</h2>
      <p class="subtitle">Créez votre compte NEGOSUD</p>

      <form @submit.prevent="handleRegister" class="auth-form">
        <div v-if="error" class="error-message">
          {{ error }}
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="firstname">Prénom *</label>
            <input
              id="firstname"
              v-model="formData.firstname"
              type="text"
              required
              placeholder="Jean"
              class="form-input"
            />
          </div>

          <div class="form-group">
            <label for="lastname">Nom *</label>
            <input
              id="lastname"
              v-model="formData.lastname"
              type="text"
              required
              placeholder="Dupont"
              class="form-input"
            />
          </div>
        </div>

        <div class="form-group">
          <label for="email">Email *</label>
          <input
            id="email"
            v-model="formData.email"
            type="email"
            required
            placeholder="jean.dupont@email.com"
            class="form-input"
          />
        </div>

        <div class="form-group">
          <label for="password">Mot de passe *</label>
          <input
            id="password"
            v-model="formData.password"
            type="password"
            required
            minlength="6"
            placeholder="••••••••"
            class="form-input"
          />
        </div>

        <div class="form-group">
          <label for="phone">Téléphone</label>
          <input
            id="phone"
            v-model="formData.phone_number"
            type="tel"
            placeholder="06 12 34 56 78"
            class="form-input"
          />
        </div>

        <div class="form-group">
          <label for="address">Adresse</label>
          <textarea
            id="address"
            v-model="formData.address"
            placeholder="123 Rue de la Paix, 75000 Paris"
            class="form-input"
            rows="3"
          ></textarea>
        </div>

        <button type="submit" class="btn-primary" :disabled="loading">
          {{ loading ? 'Inscription...' : "S'inscrire" }}
        </button>
      </form>

      <p class="auth-footer">
        Vous avez déjà un compte ?
        <RouterLink to="/login">Connectez-vous</RouterLink>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { CaveUser } from '@/services/api'

const router = useRouter()
const authStore = useAuthStore()

const formData = ref<CaveUser>({
  firstname: '',
  lastname: '',
  email: '',
  password: '',
  phone_number: '',
  address: '',
})

const error = ref('')
const loading = ref(false)

async function handleRegister() {
  error.value = ''
  loading.value = true

  try {
    const success = await authStore.register(formData.value)

    if (success) {
      router.push('/catalog')
    } else {
      error.value = "Une erreur est survenue lors de l'inscription"
    }
  } catch (err) {
    error.value = 'Une erreur est survenue. Veuillez réessayer.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.auth-card {
  background: white;
  padding: 3rem;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 600px;
}

.auth-card h2 {
  font-size: 2rem;
  margin-bottom: 0.5rem;
  color: #7c1e3f;
}

.subtitle {
  color: #666;
  margin-bottom: 2rem;
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.error-message {
  background-color: #fee;
  border: 1px solid #fcc;
  color: #c33;
  padding: 1rem;
  border-radius: 6px;
  text-align: center;
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
  transition: border-color 0.3s;
  font-family: inherit;
}

.form-input:focus {
  outline: none;
  border-color: #7c1e3f;
}

textarea.form-input {
  resize: vertical;
  min-height: 80px;
}

.btn-primary {
  padding: 1rem;
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s;
}

.btn-primary:hover:not(:disabled) {
  transform: translateY(-2px);
}

.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.auth-footer {
  margin-top: 2rem;
  text-align: center;
  color: #666;
}

.auth-footer a {
  color: #7c1e3f;
  text-decoration: none;
  font-weight: 600;
}

.auth-footer a:hover {
  text-decoration: underline;
}

@media (max-width: 640px) {
  .form-row {
    grid-template-columns: 1fr;
  }
}
</style>
