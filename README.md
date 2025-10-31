# Gestion Production Restaurant

A comprehensive Spring Boot application for managing restaurant production operations, including product management, recipe tracking, order processing, inventory control, and supplier management.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Database Configuration](#database-configuration)
- [Default Users](#default-users)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)

## Overview

Gestion Production Restaurant is a full-stack web application designed to streamline restaurant production management. It provides comprehensive tools for tracking products, managing recipes, processing orders, controlling inventory, and managing supplier relationships.

## Features

### Core Functionalities

- **Product Management** (`Produit`)
  - Track products with barcode support
  - Manage stock levels (minimum, maximum, current stock)
  - Expiration date tracking and alerts
  - Lot management and tracking
  - Product categorization
  - Price management with TVA calculation

- **Production Management** (`Production`)
  - Track production operations
  - Monitor input/output quantities
  - Calculate losses (perte)
  - Time tracking for production processes
  - Link productions to recipes and products

- **Recipe Management** (`Recette`)
  - Create and manage recipes
  - Track recipe costs
  - Set consumption date limits
  - Recipe categorization
  - Link recipes to products via ingredients

- **Order Management** (`BonCommande`)
  - Create purchase orders
  - Track order status
  - Manage order line items

- **Reception Management** (`Bon_Reception`)
  - Process product receptions
  - Track received quantities
  - Link to orders

- **Supplier Management** (`Fournisseur`)
  - Manage supplier information
  - Track supplier-product relationships
  - Supplier availability scheduling

- **Inventory Management**
  - Storage zone management (`Zone_stockage`)
  - Product-zone associations
  - Stock level monitoring

- **Brand & Category Management**
  - Product brands (`Marque`)
  - Product categories (`Categorie`)
  - Recipe categories (`Categorie_recette`)

- **Laboratory Management** (`Laboratoire`)
  - Track laboratory operations
  - Link productions to laboratories

- **Security**
  - User authentication and authorization
  - Role-based access control (Admin, User)
  - Spring Security integration

## Technology Stack

- **Framework**: Spring Boot 3.0.6
- **Java Version**: 17
- **Build Tool**: Maven
- **Database**: MySQL
- **ORM**: Spring Data JPA (Jakarta Persistence)
- **Template Engine**: Thymeleaf
- **Security**: Spring Security 6
- **Validation**: Spring Boot Validation
- **Development Tools**: Lombok, Spring Boot DevTools
- **Connector**: MySQL Connector/J

## Project Structure

```
src/main/java/com/example/gestionproductionrestaurant/
├── Entities/          # JPA Entity classes (26 entities)
│   ├── AppRole.java
│   ├── AppUser.java
│   ├── Produit.java
│   ├── Production.java
│   ├── Recette.java
│   ├── BonCommande.java
│   ├── Bon_Reception.java
│   ├── Fournisseur.java
│   ├── Zone_stockage.java
│   ├── Categorie.java
│   ├── Categorie_recette.java
│   ├── Marque.java
│   ├── Lot.java
│   ├── Laboratoire.java
│   └── ... (additional entities)
│
├── Repository/        # Spring Data JPA repositories (20 repositories)
│   ├── ProduitRepository.java
│   ├── ProductionRepository.java
│   ├── RecetteRepository.java
│   ├── UserRepository.java
│   ├── RoleRepository.java
│   └── ... (additional repositories)
│
├── Metiers/          # Business logic layer (15 service classes)
│   ├── ProduitMetier.java
│   ├── ProductionMetier.java
│   ├── RecetteMetier.java
│   ├── SecuriteService.java
│   └── ... (additional services)
│
├── Web/              # REST Controllers (15 controllers)
│   ├── ProduitController.java
│   ├── ProductionController.java
│   ├── RecetteController.java
│   ├── SecurityController.java
│   ├── DashboardController.java
│   └── ... (additional controllers)
│
└── Security/         # Security configuration

GestionProductionRestaurantApplication.java  # Main application class
AppConfig.java        # Application configuration
```

## Prerequisites

Before running this application, ensure you have the following installed:

- **JDK 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+** or compatible database
- **IDE** (Eclipse, IntelliJ IDEA, or VS Code) - Optional but recommended

## Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd "GestionProductionRestaurant"
```

### 2. Database Setup

Create a MySQL database for the application:

```sql
CREATE DATABASE gestion_production_restaurant;
```

### 3. Configure Database Connection

Update the `application.properties` or `application.yml` file in `src/main/resources/` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_production_restaurant
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

Using Maven:
```bash
mvn spring-boot:run
```

Or using the Maven wrapper:
```bash
./mvnw spring-boot:run    # Linux/Mac
mvnw.cmd spring-boot:run  # Windows
```

The application will start on `http://localhost:8080` (default port).

## Default Users

The application initializes with the following default users on startup:

### Admin User
- **Username**: `medo`
- **Password**: `11121314`
- **Role**: `admin` (Le gerant)

### Regular User
- **Username**: `khallaoui`
- **Password**: `1234`
- **Role**: `user` (chef cuisinier)

> **Note**: Change these default credentials in production environments. The initialization code is located in `GestionProductionRestaurantApplication.java`.

## API Endpoints

The application provides REST endpoints for various operations. Main controllers include:

- **ProduitController** - Product management
- **ProductionController** - Production tracking
- **RecetteController** - Recipe management
- **BonCommandeController** - Order management
- **ReceptionController** - Reception processing
- **FournisseurController** - Supplier management
- **SecurityController** - Authentication & authorization
- **DashboardController** - Dashboard data

## Key Entities

### Produit (Product)
- Product name, brand, barcode
- Stock levels and alerts
- Expiration tracking
- Price and TVA
- Lot management
- Category association

### Production
- Input/output quantities
- Production time tracking
- Loss calculation (perte)
- Product and category relationships
- Laboratory association

### Recette (Recipe)
- Recipe designation
- Total cost calculation
- Consumption date limits
- Recipe category
- Ingredient line items

### BonCommande (Purchase Order)
- Order creation and management
- Line items tracking
- Supplier association

## Security

The application uses Spring Security for authentication and authorization:
- **Authentication**: Username/password based
- **Authorization**: Role-based (admin, user)
- **Integration**: Thymeleaf security extensions
- **Default login page**: `/login`

## Development

### Using DevTools

Spring Boot DevTools is included for enhanced development experience:
- Automatic application restart on code changes
- LiveReload support for browser refresh

### Lombok

The project uses Lombok to reduce boilerplate code:
- `@Data` - Generates getters, setters, toString, equals, hashCode
- `@AllArgsConstructor` / `@NoArgsConstructor` - Constructor generation
- `@ToString` - Customizable toString generation

## Dependencies

Key dependencies in `pom.xml`:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Validation
- MySQL Connector
- Lombok
- Thymeleaf Spring Security 6

