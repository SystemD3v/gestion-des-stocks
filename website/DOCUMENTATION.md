# NEGOSUD - Site Web E-Commerce

Site web de vente en ligne pour NEGOSUD, négociant en vins de Gascogne.

## 📋 Description

Ce projet constitue le **Jalon 2** du projet STIVE (Solution complète sur la gestion de STock, Inventaire et VEnte en ligne). Il s'agit d'un site web moderne permettant aux clients de consulter le catalogue de produits et de passer des commandes en ligne.

## 🚀 Technologies utilisées

- **Vue.js 3** - Framework JavaScript progressif
- **TypeScript** - Typage statique
- **Vite** - Build tool rapide
- **Pinia** - State management
- **Vue Router** - Routing
- **Axios** - Client HTTP
- **Stripe** - Paiement en ligne (mode test)

## ✨ Fonctionnalités

### Authentification
- Inscription utilisateur
- Connexion / Déconnexion
- Gestion de session

### Catalogue
- Affichage de tous les produits
- Filtrage par genre (Rouge, Rosé, Blanc, Pétillant, Digestif)
- Filtrage par domaine (Tariquet, Pelleheaut, Joy, Vignoble Fontan, Uby)
- Filtrage par prix
- Tri par nom, prix, année
- Affichage des stocks disponibles

### Produits
- Détails complets de chaque produit
- Prix unitaire et prix par carton
- Quantité disponible en temps réel
- Ajout au panier avec sélection de quantité

### Panier
- Visualisation des articles
- Modification des quantités
- Suppression d'articles
- Calcul automatique du total

### Commande
- Formulaire d'informations de livraison
- Intégration Stripe pour le paiement sécurisé
- Création automatique des commandes dans la base de données
- Historique des commandes

### Contact
- Formulaire de contact
- Informations de l'entreprise
- Horaires d'ouverture

## 📦 Installation

### Prérequis
- Node.js (v20.19.0 ou supérieur recommandé)
- npm ou yarn
- Backend Spring Boot en cours d'exécution sur `http://localhost:8080`

### Étapes d'installation

1. **Naviguer dans le dossier du projet**
```bash
cd website
```

2. **Installer les dépendances**
```bash
npm install
```

3. **Lancer le serveur de développement**
```bash
npm run dev
```

4. **Accéder à l'application**
Ouvrir le navigateur à l'adresse : `http://localhost:5173`

## 🔧 Configuration

### API Backend
L'URL de l'API est configurée dans `src/services/api.ts` :
```typescript
const API_BASE_URL = 'http://localhost:8080/api/v1';
```

### Stripe
La clé publique Stripe (mode test) est configurée dans `src/views/CheckoutView.vue`.
Pour utiliser votre propre compte Stripe :
1. Créer un compte sur [stripe.com](https://stripe.com)
2. Récupérer la clé publique de test
3. Remplacer la clé dans le fichier `CheckoutView.vue`

## 🏗️ Structure du projet

```
website/
├── src/
│   ├── assets/          # Ressources statiques
│   ├── components/      # Composants réutilisables
│   ├── router/          # Configuration des routes
│   ├── services/        # Services API
│   │   └── api.ts       # Client API et endpoints
│   ├── stores/          # Stores Pinia
│   │   ├── auth.ts      # Gestion authentification
│   │   └── cart.ts      # Gestion panier
│   ├── views/           # Pages de l'application
│   │   ├── HomeView.vue
│   │   ├── CatalogView.vue
│   │   ├── ProductView.vue
│   │   ├── CartView.vue
│   │   ├── CheckoutView.vue
│   │   ├── OrdersView.vue
│   │   ├── LoginView.vue
│   │   ├── RegisterView.vue
│   │   └── ContactView.vue
│   ├── App.vue          # Composant racine
│   └── main.ts          # Point d'entrée
├── package.json
└── README.md
```

## 🎨 Design

Le site utilise un design moderne avec :
- Palette de couleurs bordeaux (#7c1e3f) rappelant le vin
- Interface responsive (mobile-friendly)
- Animations et transitions fluides
- Navigation sticky
- Cards avec effets hover
- Formulaires stylisés

## 📱 Responsive

Le site est entièrement responsive et s'adapte aux différents formats d'écran :
- Desktop (> 1024px)
- Tablet (768px - 1024px)
- Mobile (< 768px)

## 🔐 Sécurité

- Authentification requise pour la commande
- Guards de navigation pour les routes protégées
- Paiement sécurisé via Stripe
- Validation des formulaires côté client

## 🧪 Mode Test Stripe

Le site utilise Stripe en mode test. Cartes de test :
- **Succès** : 4242 4242 4242 4242
- **Échec** : 4000 0000 0000 0002
- Date d'expiration : N'importe quelle date future
- CVC : N'importe quel 3 chiffres

## 📄 Scripts disponibles

```bash
# Développement
npm run dev

# Build production
npm run build

# Preview du build
npm run preview

# Linting
npm run lint

# Type checking
npm run type-check
```

## 🔗 Connexion avec le Backend

Le site communique avec l'API REST Spring Boot via les endpoints suivants :

### Produits (Stock)
- `GET /api/v1/get_stock` - Tous les produits
- `GET /api/v1/get_stock/{genre}` - Produits par genre
- `GET /api/v1/get_stockById/{id}` - Produit par ID

### Utilisateurs
- `GET /api/v1/get_users` - Tous les utilisateurs
- `POST /api/v1/create_user` - Créer un utilisateur
- `GET /api/v1/get_user_by_id/{id}` - Utilisateur par ID

### Commandes (Handler)
- `GET /api/v1/get_handler` - Toutes les commandes
- `POST /api/v1/createInstance` - Créer une commande
- `GET /api/v1/getInstanceByUserId/{userId}` - Commandes d'un utilisateur

## 👥 Domaines Partenaires

Le site met en avant les 5 domaines viticoles partenaires :
- 🏰 Tariquet
- 🍇 Pelleheaut
- ✨ Joy
- 🌿 Vignoble Fontan
- 🎯 Uby

## 📧 Support

Pour toute question ou problème :
- Email : contact@negosud.fr
- Formulaire de contact sur le site

## 🎯 Fonctionnalités futures possibles

- Système de wishlist
- Avis et notes clients
- Programme de fidélité
- Recommandations personnalisées
- Newsletter
- Suivi de livraison en temps réel

---

**Développé avec ❤️ pour NEGOSUD**
*Solution STIVE - Jalon 2 - 2025*
