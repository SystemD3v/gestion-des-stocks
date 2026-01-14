# VinStock - Système de Gestion de Stock de Vins

## Description

VinStock est une application de gestion de stock de vins professionnelle composée de deux parties :
- **API REST Backend** (Spring Boot) pour la gestion des données
- **Client Lourd Desktop** (JavaFX) pour l'interface utilisateur

Le système permet de gérer l'inventaire de vins, les fournisseurs, les clients et les commandes avec une architecture robuste et scalable.

---

## Architecture du Projet

```
VinStock/
├── gestion-des-stocks/     # API REST Backend (Spring Boot)
└── JavaFX/                 # Client Desktop (JavaFX)
```

---

## Backend - API REST (`gestion-des-stocks`)

### Technologies Utilisées

- **Langage** : Java 21
- **Framework** : Spring Boot 3.5.5
- **Build Tool** : Gradle 8.14.3
- **Base de données** : PostgreSQL 17.6
- **ORM** : JPA/Hibernate
- **Architecture** : RESTful API avec architecture en couches (Controller → Service → Repository)

### Configuration Base de Données

```properties
Database: PostgreSQL
URL: jdbc:postgresql://localhost:5432/cave
Username: root
Password: tartarus
```

### Entités Principales

#### 1. CaveStock
Représente les bouteilles de vin en stock
- **Champs** : id, label, years, genre, area, available_quantity, price, supplier_id
- **Table** : `cave_stocks`

#### 2. CaveUser
Représente les utilisateurs/clients du système
- **Champs** : id, lastname, firstname, total_bottles_bought, email, password, role, address, phone_number
- **Table** : `cave_users`

#### 3. CaveHandler
Représente les commandes/transactions
- **Champs** : id, supplyGroupId, requestAmount, operation, validated, completed, orderTimestamp, stockId, supplierId, userId
- **Table** : `cave_controller`
- **Opérations** : 1=Approvisionnement, 2=Demande

#### 4. CaveSupplier
Représente les fournisseurs
- **Champs** : id, supplier_name, supplier_address, supplier_phone
- **Table** : `cave_supplier`

#### 5. CaveLogs
Journal d'audit des opérations
- **Champs** : logId, operation, description
- **Table** : `cave_logs`

### API Endpoints

**Base URL** : `http://localhost:8080/api/v1`

#### Stock
```
GET    /get_stock                          → Récupérer tous les stocks
GET    /get_stock/{genre}                  → Filtrer par type de vin
GET    /get_stockById/{id}                 → Récupérer un stock par ID
GET    /get_stockByLabel/{label}           → Rechercher par nom de produit
GET    /get_stockByYear/{year1}/{year2}    → Filtrer par intervalle d'années
GET    /get_stockByPrice/{low}/{high}      → Filtrer par intervalle de prix
POST   /createStock                        → Créer un nouveau stock
DELETE /deleteStockById/{id}               → Supprimer un stock
GET    /updateStock/{id}/{field}/{value}   → Mettre à jour un champ de stock
```

#### Utilisateurs
```
GET    /get_users                          → Récupérer tous les utilisateurs
GET    /get_user_by_id/{id}                → Récupérer un utilisateur par ID
GET    /get_user_by_lastname/{lastname}    → Rechercher par nom de famille
POST   /create_user                        → Créer un nouvel utilisateur
DELETE /delete_users/{id}                  → Supprimer un utilisateur
GET    /updateUser/{id}/{field}/{value}    → Mettre à jour un champ utilisateur
```

#### Commandes
```
GET    /get_handler                        → Récupérer toutes les commandes
GET    /getInstanceById/{id}               → Récupérer une commande par ID
GET    /getInstanceByUserId/{id}           → Récupérer les commandes par utilisateur
GET    /getInstanceByStockId/{id}          → Récupérer les commandes par stock
GET    /getInstanceByValidation/{bool}     → Filtrer par statut de validation
GET    /getInstanceByComplete/{bool}       → Filtrer par statut de complétion
GET    /getInstanceByTimestamp/{t1}/{t2}   → Filtrer par intervalle de temps
POST   /createInstance                     → Créer une nouvelle commande
DELETE /deleteInstance/{id}                → Supprimer une commande
GET    /updateInstance/{id}/{field}/{value} → Mettre à jour une commande
GET    /getLastInstance                    → Récupérer les commandes récentes
```

#### Fournisseurs
```
GET    /get_supplier                       → Récupérer tous les fournisseurs
GET    /get_supplier/{id}                  → Récupérer un fournisseur par ID
POST   /create_supplier                    → Créer un nouveau fournisseur
GET    /edit_supplier/{id}/{name}/{phone}/{address} → Mettre à jour un fournisseur
DELETE /sup_supplier/{id}                  → Supprimer un fournisseur
```

#### Logs
```
GET    /get_logs                           → Récupérer tous les logs
GET    /get_recent_logs/{limit}            → Récupérer les logs récents avec limite
GET    /add_log/{operation}/{description}  → Ajouter une entrée de log
```

### Lancement du Backend

```bash
cd gestion-des-stocks
./gradlew bootRun
```

L'API sera accessible sur `http://localhost:8080`

---

## Frontend - Client Desktop (`JavaFX`)

### Technologies Utilisées

- **Langage** : Java 21
- **Framework UI** : JavaFX 21.0.6
- **Modules** : javafx.controls, javafx.fxml, javafx.web, javafx.swing
- **Build Tool** : Gradle avec JavaFX Plugin
- **HTTP Client** : Java 11+ HttpClient API
- **JSON** : Google Gson
- **Bibliothèques additionnelles** : FormsFX, TilesFX, Ikonli (icônes)

### Fonctionnalités Principales

#### 1. Gestion du Stock
- Affichage de l'inventaire dans un tableau
- Recherche et filtrage par catégorie, genre, intervalle d'années
- Calcul de la valeur totale (prix × quantité)
- Ajout de nouveaux produits via dialogue
- Association dynamique avec les fournisseurs

#### 2. Gestion des Commandes
- Affichage des commandes en deux tableaux : incomplètes et complètes
- Validation/complétion des commandes
- Création de nouvelles commandes
- Modification automatique des quantités de stock

#### 3. Gestion des Fournisseurs
- Opérations CRUD complètes
- Création, mise à jour et suppression de fournisseurs
- Affichage en tableau

#### 4. Gestion des Clients
- Opérations CRUD pour les utilisateurs/clients
- Gestion des profils clients

### Écrans Disponibles

- **HOME** : Gestion du stock (Stock.fxml)
- **FOURNISSEUR** : Gestion des fournisseurs (Fournisseur.fxml)
- **COMMANDE** : Gestion des commandes (Commandes.fxml)
- **CLIENTS** : Gestion des clients (Clients.fxml)

### Communication avec l'API

Le fichier `APICall.java` gère toutes les communications HTTP :
- **Base URL** : `http://localhost:8080/api/v1/`
- **Méthodes HTTP** : GET, POST, DELETE
- **Traitement JSON** : Gson pour la sérialisation/désérialisation

### Lancement du Client

```bash
cd JavaFX
./gradlew run
```

L'application s'ouvrira avec une résolution de 1280x720.

---

## Installation et Configuration

### Prérequis

- Java 21 JDK
- PostgreSQL 17.6
- Gradle 8.14.3 (ou wrapper inclus)

### Configuration de la Base de Données

1. Installer PostgreSQL
2. Créer une base de données nommée `cave` :
```sql
CREATE DATABASE cave;
```
3. Mettre à jour les identifiants dans `gestion-des-stocks/src/main/resources/application.properties` si nécessaire

### Installation

1. Cloner le repository
```bash
git clone <repository-url>
cd VinStock
```

2. Configurer la base de données PostgreSQL (voir ci-dessus)

3. Lancer le backend
```bash
cd gestion-des-stocks
./gradlew bootRun
```

4. Dans un nouveau terminal, lancer le client JavaFX
```bash
cd JavaFX
./gradlew run
```

---

## Flux de Communication

```
Client JavaFX (Interface Utilisateur)
    ↓
APICall.java (Couche HTTP)
    ↓
Requêtes/Réponses HTTP
    ↓
API Spring Boot (localhost:8080)
    ↓
CaveController (Endpoints REST)
    ↓
Services (Logique métier)
    ↓
Repositories (Accès aux données JPA/Hibernate)
    ↓
Base de données PostgreSQL
```

---

## Fonctionnalités Métier

### Gestion du Stock de Vins
- Opérations CRUD complètes
- Filtrage avancé (genre, année, prix, label)
- Suivi des quantités disponibles
- Calculs de valeur totale
- Association avec les fournisseurs

### Gestion des Utilisateurs/Clients
- Profils clients complets
- Suivi du nombre total de bouteilles achetées
- Gestion des informations de contact
- Système de rôles

### Gestion des Commandes
- Suivi des approvisionnements et demandes
- Statuts de validation et de complétion
- Groupement des commandes
- Historique avec horodatage

### Gestion des Fournisseurs
- Base de données des fournisseurs
- Liaison avec les produits en stock
- Mise à jour des informations de contact

### Journal d'Activité
- Piste d'audit pour toutes les opérations
- Descriptions des actions effectuées
- Consultation avec pagination

---

## Patterns de Conception Utilisés

- **MVC** : Séparation modèle, vue, contrôleur
- **Service Layer** : Abstraction de la logique métier
- **Repository** : Abstraction de l'accès aux données
- **Singleton** : Gestion de l'instance Launcher
- **Dependency Injection** : Spring et Lombok

---

## Structure du Projet

### Backend (gestion-des-stocks)
```
src/main/java/com/example/stockapplication/
├── controller/          # Endpoints REST
├── entity/              # Entités JPA
├── service/             # Logique métier
├── repository/          # Accès aux données
├── dto/                 # Data Transfer Objects
└── StockApplication.java # Point d'entrée Spring Boot
```

### Frontend (JavaFX)
```
src/main/java/com/example/javafx/
├── Controller/          # Contrôleurs UI
├── model/               # Modèles de données
├── view/                # Classes de vues
├── Launcher.java        # Point d'entrée JavaFX
└── resources/
    └── com/example/javafx/view/
        ├── Stock.fxml           # Interface stock
        ├── Commandes.fxml       # Interface commandes
        ├── Fournisseur.fxml     # Interface fournisseurs
        ├── Clients.fxml         # Interface clients
        └── style.css            # Styles CSS
```

---

## Développement

### Branche Actuelle
- **Branche principale** : `dev-back`

---



## Auteurs

Yoann Meynsan, Lilian Fischer, Emily Dutilh, Lucas Raoul

---

