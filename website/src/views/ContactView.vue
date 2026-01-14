<template>
  <div class="contact">
    <h1>Contactez-nous</h1>

    <div class="contact-container">
      <div class="contact-info">
        <h2>NEGOSUD</h2>
        <p class="tagline">Négociant en vins de Gascogne</p>

        <div class="info-section">
          <h3>📍 Adresse</h3>
          <p>Entrepôt NEGOSUD<br>Route des Vignobles<br>32000 Gascogne, France</p>
        </div>

        <div class="info-section">
          <h3>📞 Téléphone</h3>
          <p>+33 (0)5 XX XX XX XX</p>
        </div>

        <div class="info-section">
          <h3>📧 Email</h3>
          <p>contact@negosud.fr</p>
        </div>

        <div class="info-section">
          <h3>🕒 Horaires d'ouverture</h3>
          <p>
            Lundi - Vendredi : 9h00 - 18h00<br>
            Samedi : 10h00 - 17h00<br>
            Dimanche : Fermé
          </p>
        </div>
      </div>

      <div class="contact-form-container">
        <form @submit.prevent="handleSubmit" class="contact-form">
          <div v-if="successMessage" class="success-message">
            {{ successMessage }}
          </div>

          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>

          <div class="form-group">
            <label for="name">Nom complet *</label>
            <input
              id="name"
              v-model="formData.name"
              type="text"
              required
              placeholder="Jean Dupont"
              class="form-input"
            />
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
            <label for="phone">Téléphone</label>
            <input
              id="phone"
              v-model="formData.phone"
              type="tel"
              placeholder="06 12 34 56 78"
              class="form-input"
            />
          </div>

          <div class="form-group">
            <label for="subject">Sujet *</label>
            <select id="subject" v-model="formData.subject" required class="form-input">
              <option value="">Sélectionnez un sujet</option>
              <option value="info">Demande d'information</option>
              <option value="order">Question sur une commande</option>
              <option value="product">Question sur un produit</option>
              <option value="other">Autre</option>
            </select>
          </div>

          <div class="form-group">
            <label for="message">Message *</label>
            <textarea
              id="message"
              v-model="formData.message"
              required
              placeholder="Votre message..."
              class="form-input"
              rows="6"
            ></textarea>
          </div>

          <button type="submit" class="btn-submit" :disabled="submitting">
            {{ submitting ? 'Envoi...' : 'Envoyer le message' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const formData = ref({
  name: '',
  email: '',
  phone: '',
  subject: '',
  message: '',
})

const submitting = ref(false)
const successMessage = ref('')
const errorMessage = ref('')

async function handleSubmit() {
  submitting.value = true
  successMessage.value = ''
  errorMessage.value = ''

  try {
    await new Promise((resolve) => setTimeout(resolve, 1000))

    successMessage.value = 'Votre message a été envoyé avec succès ! Nous vous répondrons dans les plus brefs délais.'

    formData.value = {
      name: '',
      email: '',
      phone: '',
      subject: '',
      message: '',
    }
  } catch (err) {
    errorMessage.value = 'Une erreur est survenue. Veuillez réessayer.'
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.contact h1 {
  font-size: 2.5rem;
  color: #7c1e3f;
  margin-bottom: 2rem;
}

.contact-container {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 3rem;
}

.contact-info {
  background: white;
  padding: 2.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  height: fit-content;
}

.contact-info h2 {
  font-size: 2rem;
  color: #7c1e3f;
  margin-bottom: 0.5rem;
}

.tagline {
  color: #666;
  font-size: 1.1rem;
  margin-bottom: 2rem;
}

.info-section {
  margin-bottom: 2rem;
}

.info-section h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 0.75rem;
}

.info-section p {
  color: #666;
  line-height: 1.6;
}

.contact-form-container {
  background: white;
  padding: 2.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.contact-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.success-message {
  background-color: #d4edda;
  border: 1px solid #c3e6cb;
  color: #155724;
  padding: 1rem;
  border-radius: 6px;
}

.error-message {
  background-color: #fee;
  border: 1px solid #fcc;
  color: #c33;
  padding: 1rem;
  border-radius: 6px;
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
  transition: border-color 0.3s;
}

.form-input:focus {
  outline: none;
  border-color: #7c1e3f;
}

textarea.form-input {
  resize: vertical;
  min-height: 120px;
}

.btn-submit {
  padding: 1.25rem;
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s;
}

.btn-submit:hover:not(:disabled) {
  transform: translateY(-2px);
}

.btn-submit:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

@media (max-width: 968px) {
  .contact-container {
    grid-template-columns: 1fr;
  }
}
</style>
