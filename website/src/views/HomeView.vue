<template>
  <div class="home">
    <section class="hero">
      <div class="hero-content">
        <h1 class="hero-title">🍷 Découvrez les vins de Gascogne</h1>
        <p class="hero-subtitle">
          Négociant en vins fins, NEGOSUD vous propose une sélection exceptionnelle des meilleurs
          domaines viticoles de Gascogne
        </p>
        <div class="hero-buttons">
          <RouterLink to="/catalog" class="btn-hero-primary">Découvrir nos vins</RouterLink>
          <RouterLink to="/contact" class="btn-hero-secondary">Nous contacter</RouterLink>
        </div>
      </div>
    </section>

    <section class="domains">
      <h2>Nos domaines partenaires</h2>
      <div class="domains-grid">
        <div class="domain-card">
          <h3>🏰 Tariquet</h3>
          <p>Domaine familial réputé pour ses Armagnacs et vins blancs d'exception</p>
        </div>
        <div class="domain-card">
          <h3>🍇 Pelleheaut</h3>
          <p>Vignoble artisanal aux méthodes traditionnelles</p>
        </div>
        <div class="domain-card">
          <h3>✨ Joy</h3>
          <p>Vins élégants et raffinés du terroir gascon</p>
        </div>
        <div class="domain-card">
          <h3>🌿 Vignoble Fontan</h3>
          <p>Agriculture biologique et respect de l'environnement</p>
        </div>
        <div class="domain-card">
          <h3>🎯 Uby</h3>
          <p>Innovation et qualité au service du goût</p>
        </div>
      </div>
    </section>

    <section class="features">
      <h2>Pourquoi choisir NEGOSUD ?</h2>
      <div class="features-grid">
        <div class="feature">
          <div class="feature-icon">🎯</div>
          <h3>Sélection rigoureuse</h3>
          <p>Chaque vin est soigneusement sélectionné par notre œnologue</p>
        </div>
        <div class="feature">
          <div class="feature-icon">🚚</div>
          <h3>Livraison rapide</h3>
          <p>Vos commandes livrées dans les meilleurs délais</p>
        </div>
        <div class="feature">
          <div class="feature-icon">💳</div>
          <h3>Paiement sécurisé</h3>
          <p>Transactions sécurisées via Stripe</p>
        </div>
        <div class="feature">
          <div class="feature-icon">📞</div>
          <h3>Service client</h3>
          <p>Une équipe à votre écoute pour vous conseiller</p>
        </div>
      </div>
    </section>

    <section class="cta">
      <h2>Prêt à découvrir nos vins ?</h2>
      <p>Explorez notre catalogue et trouvez le vin parfait pour chaque occasion</p>
      <RouterLink to="/catalog" class="btn-cta">Voir le catalogue</RouterLink>
    </section>
  </div>
</template>

<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<style scoped>
.home {
  max-width: 100%;
}

.hero {
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
  padding: 6rem 2rem;
  text-align: center;
  border-radius: 12px;
  margin-bottom: 4rem;
}

.hero-content {
  max-width: 800px;
  margin: 0 auto;
}

.hero-title {
  font-size: 3rem;
  margin-bottom: 1.5rem;
  line-height: 1.2;
}

.hero-subtitle {
  font-size: 1.25rem;
  margin-bottom: 2.5rem;
  opacity: 0.95;
}

.hero-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

.btn-hero-primary,
.btn-hero-secondary {
  padding: 1rem 2.5rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 600;
  font-size: 1.1rem;
  transition: transform 0.2s;
}

.btn-hero-primary {
  background-color: white;
  color: #7c1e3f;
}

.btn-hero-primary:hover {
  transform: translateY(-2px);
}

.btn-hero-secondary {
  background-color: transparent;
  color: white;
  border: 2px solid white;
}

.btn-hero-secondary:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.domains,
.features,
.cta {
  margin-bottom: 4rem;
}

.domains h2,
.features h2 {
  text-align: center;
  font-size: 2.5rem;
  margin-bottom: 3rem;
  color: #7c1e3f;
}

.domains-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
}

.domain-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}

.domain-card:hover {
  transform: translateY(-5px);
}

.domain-card h3 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  color: #7c1e3f;
}

.domain-card p {
  color: #666;
  line-height: 1.6;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
}

.feature {
  text-align: center;
  padding: 2rem;
}

.feature-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.feature h3 {
  font-size: 1.25rem;
  margin-bottom: 0.5rem;
  color: #333;
}

.feature p {
  color: #666;
}

.cta {
  background: linear-gradient(135deg, #7c1e3f 0%, #5a1428 100%);
  color: white;
  padding: 4rem 2rem;
  text-align: center;
  border-radius: 12px;
}

.cta h2 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: white;
}

.cta p {
  font-size: 1.25rem;
  margin-bottom: 2rem;
  opacity: 0.95;
}

.btn-cta {
  display: inline-block;
  padding: 1rem 2.5rem;
  background-color: white;
  color: #7c1e3f;
  text-decoration: none;
  border-radius: 8px;
  font-weight: 600;
  font-size: 1.1rem;
  transition: transform 0.2s;
}

.btn-cta:hover {
  transform: translateY(-2px);
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 2rem;
  }

  .hero-subtitle {
    font-size: 1rem;
  }

  .domains h2,
  .features h2,
  .cta h2 {
    font-size: 1.75rem;
  }
}
</style>
