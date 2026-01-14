# NEGOSUD Website - Guide de démarrage rapide

Ce guide vous explique comment lancer le site web NEGOSUD (Jalon 2).

## Prérequis

Avant de commencer, assurez-vous d'avoir :

1. **Node.js** installé (version 20.19+ ou 22.12+ recommandée)
2. **Le backend Spring Boot** en cours d'exécution sur `http://localhost:8080`
3. **npm** (installé automatiquement avec Node.js)

## Démarrage du site web

### 1. Ouvrir un terminal et naviguer vers le dossier website

```bash
cd website
```

### 2. Installer les dépendances (première fois seulement)

```bash
npm install
```

### 3. Lancer le serveur de développement

```bash
npm run dev
```

### 4. Accéder au site

Ouvrez votre navigateur et allez sur :
```
http://localhost:5173
```

## Fonctionnalités disponibles

✅ **Page d'accueil** - Présentation de NEGOSUD et des domaines partenaires
✅ **Catalogue** - Consultation des produits avec filtres et tri
✅ **Détails produit** - Informations complètes sur chaque vin
✅ **Inscription/Connexion** - Système d'authentification
✅ **Panier** - Gestion des articles à commander
✅ **Paiement** - Intégration Stripe (mode test)
✅ **Mes commandes** - Historique des achats
✅ **Contact** - Formulaire de contact

## Tester le paiement (Stripe mode test)

Pour tester le processus de paiement, utilisez ces cartes de test :

**Carte bancaire test (succès):**
- Numéro : `4242 4242 4242 4242`
- Date d'expiration : N'importe quelle date future (ex: 12/25)
- CVC : N'importe quel 3 chiffres (ex: 123)

**Carte bancaire test (échec):**
- Numéro : `4000 0000 0000 0002`

## Tester l'application

### 1. Créer un compte utilisateur
1. Cliquer sur "Inscription"
2. Remplir le formulaire
3. Se connecter avec les identifiants créés

### 2. Explorer le catalogue
1. Aller dans "Catalogue"
2. Tester les filtres (genre, domaine, prix)
3. Tester le tri (nom, prix, année)

### 3. Ajouter au panier
1. Cliquer sur un produit
2. Ajuster la quantité
3. Cliquer sur "Ajouter au panier"

### 4. Passer commande
1. Aller dans le panier
2. Vérifier les articles
3. Cliquer sur "Procéder au paiement"
4. Remplir les informations de livraison
5. Utiliser une carte de test Stripe
6. Valider le paiement

### 5. Consulter les commandes
1. Aller dans "Mes commandes"
2. Voir l'historique des commandes

## Structure du projet

```
website/
├── src/
│   ├── views/          # Pages (Catalog, Cart, Checkout, etc.)
│   ├── stores/         # État global (auth, cart)
│   ├── services/       # API calls
│   ├── router/         # Configuration des routes
│   └── App.vue         # Composant racine
├── package.json
└── DOCUMENTATION.md    # Documentation complète
```

## Commandes disponibles

```bash
npm run dev          # Démarrer en mode développement
npm run build        # Créer la version de production
npm run preview      # Prévisualiser la version de production
npm run type-check   # Vérifier les types TypeScript
npm run lint         # Vérifier le code
```

## Connexion avec le backend

Le site communique avec l'API Spring Boot via `http://localhost:8080/api/v1`.

Assurez-vous que :
- Le backend est démarré
- La base de données PostgreSQL est accessible
- Les endpoints sont fonctionnels

## En cas de problème

### Le site ne se lance pas
- Vérifier que Node.js est installé : `node --version`
- Vérifier que npm est installé : `npm --version`
- Supprimer `node_modules` et réinstaller : `rm -rf node_modules && npm install`

### Erreurs API
- Vérifier que le backend est démarré sur `http://localhost:8080`
- Vérifier les logs du backend
- Ouvrir la console du navigateur (F12) pour voir les erreurs

### Problèmes de paiement Stripe
- Utiliser uniquement les cartes de test fournies
- Vérifier la connexion internet (Stripe nécessite une connexion)
- Consulter la console pour les erreurs

## Support

Pour plus d'informations, consulter :
- [website/DOCUMENTATION.md](website/DOCUMENTATION.md) - Documentation complète
- [CahierDesCharges.pdf](CahierDesCharges.pdf) - Cahier des charges
- [AdditionalNotes.pdf](AdditionalNotes.pdf) - Notes additionnelles

---

**NEGOSUD** - Solution STIVE Jalon 2 - 2025
