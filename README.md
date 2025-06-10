# Application JEE Spring MVC - Gestion des Patients

## Table des matières
1. [Introduction](#introduction)
2. [Architecture de l'application](#architecture-de-lapplication)
3. [Technologies utilisées](#technologies-utilisées)
4. [Structure du projet](#structure-du-projet)
5. [Fonctionnalités](#fonctionnalités)
6. [Modèle de données](#modèle-de-données)
7. [Couche de persistance](#couche-de-persistance)
8. [Services](#services)
9. [Contrôleurs](#contrôleurs)
10. [Sécurité](#sécurité)
11. [Interfaces utilisateur](#interfaces-utilisateur)
12. [Ressources et actifs statiques](#ressources-et-actifs-statiques)
13. [Rendu HTML avec Thymeleaf](#rendu-html-avec-thymeleaf)
14. [Installation et déploiement](#installation-et-déploiement)
15. [Configuration](#configuration)
16. [Tests](#tests)
17. [Contributeurs](#contributeurs)
18. [Licence](#licence)

## Introduction

Cette application JEE basée sur Spring MVC permet la gestion des patients dans un établissement médical. Elle offre des fonctionnalités de création, consultation, modification et suppression des dossiers patients, avec un système d'authentification et d'autorisation multi-rôles.

L'application est construite selon une architecture MVC (Modèle-Vue-Contrôleur) et utilise les technologies Spring Boot, Spring Security, Spring Data JPA et Thymeleaf pour les vues.

## Architecture de l'application

L'application suit une architecture en couches typique des applications d'entreprise :

- **Couche présentation** : Contrôleurs Spring MVC et vues Thymeleaf
- **Couche métier** : Services et logique métier
- **Couche d'accès aux données** : Repositories Spring Data JPA
- **Couche de sécurité** : Spring Security pour l'authentification et l'autorisation

## Technologies utilisées

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- Hibernate
- Maven
- Base de données (H2 ou autre configurée dans les propriétés)
- Bootstrap (pour les templates web)
- Lombok

## Structure du projet

```
src/
  ├── main/
  │   ├── java/
  │   │   └── com/
  │   │       └── enset/
  │   │           └── sdia/
  │   │               └── jeespringmvc/
  │   │                   ├── JeeSpringMvcApplication.java
  │   │                   ├── entity/
  │   │                   │   └── Patient.java
  │   │                   ├── repository/
  │   │                   │   └── PatientRepo.java
  │   │                   ├── security/
  │   │                   │   ├── SecurityConfig.java
  │   │                   │   ├── entities/
  │   │                   │   │   ├── AppRole.java
  │   │                   │   │   └── AppUser.java
  │   │                   │   ├── repository/
  │   │                   │   │   ├── AppRoleRepo.java
  │   │                   │   │   └── AppUserRepo.java
  │   │                   │   └── service/
  │   │                   │       ├── AccountService.java
  │   │                   │       ├── AccountServiceImpl.java
  │   │                   │       └── UserDetailsServiceImpl.java
  │   │                   └── web/
  │   │                       ├── PatientController.java
  │   │                       └── SecurityController.java
  │   └── resources/
  │       ├── application.properties
  │       ├── Schema.sql
  │       └── templates/
  │           ├── editPatient.html
  │           ├── formPatient.html
  │           ├── login.html
  │           ├── notAuthorized.html
  │           ├── patient.html
  │           └── template1.html
  └── test/
      └── java/
          └── com/
              └── enset/
                  └── sdia/
                      └── jeespringmvc/
                          └── JeeSpringMvcApplicationTests.java
```

## Fonctionnalités

1. **Gestion des patients**
   - Consultation de la liste des patients avec pagination
   - Recherche de patients par nom
   - Ajout de nouveaux patients
   - Modification des données des patients
   - Suppression de patients

2. **Gestion des utilisateurs et des rôles**
   - Création de nouveaux utilisateurs
   - Attribution et révocation de rôles
   - Authentification des utilisateurs

3. **Sécurité et contrôle d'accès**
   - Accès public aux ressources statiques
   - Accès niveau utilisateur pour consultation
   - Accès niveau administrateur pour modification

## Modèle de données

### Entité Patient
```java
@Entity
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nom;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    private boolean malade;
    private int score;
}
```

### Entités de sécurité

**AppUser**
```java
@Entity
public class AppUser {
    @Id
    private String userId;
    private String userName;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
```

**AppRole**
```java
@Entity
public class AppRole {
    @Id
    private String role;
}
```

## Couche de persistance

L'application utilise Spring Data JPA pour l'accès aux données avec les repositories suivants :

### PatientRepo
```java
public interface PatientRepo extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContains(String nom, Pageable pageable);
}
```

### AppUserRepo
```java
public interface AppUserRepo extends JpaRepository<AppUser, String> {
    AppUser findAppUsersByUserName(String username);
}
```

### AppRoleRepo
```java
public interface AppRoleRepo extends JpaRepository<AppRole, String> {
}
```

## Services

### AccountService
Service responsable de la gestion des utilisateurs et des rôles :
- Ajout d'utilisateurs
- Ajout de rôles
- Attribution de rôles aux utilisateurs
- Révocation de rôles
- Chargement des utilisateurs par nom d'utilisateur

L'implémentation `AccountServiceImpl` fournit la logique métier pour ces opérations.

## Contrôleurs

### PatientController
Gère les opérations liées aux patients :
- Affichage de la liste des patients (`/user/index`)
- Suppression d'un patient (`/admin/delete`)
- Création d'un patient (`/admin/formPatient` et `/admin/save`)
- Modification d'un patient (`/admin/editPatient`)

### SecurityController
Gère les aspects de sécurité de l'interface utilisateur :
- Page de connexion (`/login`)
- Page d'accès non autorisé (`/notAuthorized`)

## Sécurité

La sécurité est gérée par Spring Security et configurée dans la classe `SecurityConfig`. 
Les fonctionnalités de sécurité comprennent :

- Authentification basée sur des utilisateurs stockés en base de données
- Autorisations basées sur les rôles : 
  - Rôle "user" : accès aux opérations de lecture
  - Rôle "admin" : accès aux opérations de création, modification et suppression
- Page de login personnalisée
- Gestion des accès refusés

## Interfaces utilisateur

L'application utilise Thymeleaf comme moteur de template pour les vues HTML :

- `patient.html` : Affichage et recherche des patients avec pagination
- `formPatient.html` : Formulaire d'ajout de patients
- `editPatient.html` : Formulaire de modification de patients
- `login.html` : Page de connexion
- `notAuthorized.html` : Page d'erreur d'accès refusé
- `template1.html` : Template principal avec layout et menu de navigation

## Ressources et actifs statiques

L'application utilise diverses ressources statiques pour offrir une expérience utilisateur riche et réactive.

### Structure des ressources
```
src/main/resources/
  ├── application.properties   # Configuration principale de l'application
  ├── Schema.sql               # Script SQL d'initialisation (si utilisé)
  ├── static/                  # Ressources statiques (si présentes)
  │   ├── css/                 # Feuilles de style personnalisées
  │   ├── js/                  # Scripts JavaScript personnalisés
  │   └── img/                 # Images et icônes
  └── templates/               # Templates Thymeleaf
      ├── editPatient.html
      ├── formPatient.html
      ├── login.html
      ├── notAuthorized.html
      ├── patient.html
      └── template1.html
```

### Ressources externes

L'application utilise également des ressources et bibliothèques externes pour améliorer l'interface utilisateur :

1. **Bootstrap 5.3.3**
   - Framework CSS responsive pour la mise en page et les composants UI
   - Intégré via WebJars : `/webjars/bootstrap/5.3.3/css/bootstrap.min.css`
   - JavaScript associé : `/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js`

2. **WebJars**
   - Permet de gérer les dépendances client-side via Maven
   - Assure une cohérence des versions des bibliothèques front-end

3. **Configuration dans le pom.xml**
   ```xml
   <dependency>
       <groupId>org.webjars</groupId>
       <artifactId>bootstrap</artifactId>
       <version>5.3.3</version>
   </dependency>
   ```

### Accès aux ressources statiques

Dans la configuration de sécurité (`SecurityConfig.java`), les ressources statiques sont configurées pour être accessibles sans authentification :

```java
.requestMatchers("/webjars/**", "h2-console/**").permitAll()
```

## Rendu HTML avec Thymeleaf

Thymeleaf est un moteur de template moderne côté serveur qui permet de créer des vues HTML dynamiques pour les applications web Java. Dans cette application, il est utilisé pour générer toutes les interfaces utilisateur.

### Configuration de Thymeleaf

Thymeleaf est configuré dans `application.properties` avec des paramètres comme :

```properties
spring.thymeleaf.cache=false            # Désactive le cache pendant le développement
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

### Caractéristiques Thymeleaf utilisées

1. **Layouts et fragments**
   - L'application utilise le système de layout Thymeleaf pour définir une structure commune
   - Le template principal `template1.html` sert de layout de base
   - Exemple d'utilisation de layout:
     ```html
     <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
           layout:decorate="template1">
     ```

2. **Expression Language et Objets**
   - Syntaxe `${}` pour évaluer des expressions
   - Syntaxe `@{}` pour générer des URLs
   - Exemple: `th:href="@{/admin/delete(id=${p.id})}"` pour générer une URL avec paramètres

3. **Conditionnels et itérations**
   - `th:if` et `th:each` pour les conditions et boucles
   - Exemple d'utilisation d'une condition basée sur les rôles:
     ```html
     <li th:if="${#authorization.expression('hasRole(''admin'')')}">...</li>
     ```
   - Exemple d'itération sur une collection:
     ```html
     <tr th:each="p:${patients}">...</tr>
     ```

4. **Formulaires**
   - Liaison bidirectionnelle avec les formulaires HTML
   - Validation côté client et serveur
   - Exemple de formulaire:
     ```html
     <form method="post" th:action="@{/admin/save}">
       <input type="text" name="nom" th:value="${patient.nom}">
     </form>
     ```

5. **Gestion des erreurs**
   - Affichage des erreurs de validation 
   - Exemple: `<span th:errors="${patient.nom}"></span>`

6. **Intégration avec Spring Security**
   - Objets spéciaux pour accéder aux informations de sécurité
   - Exemple: `th:text="${#authentication.name}"` pour afficher le nom d'utilisateur

7. **Pagination**
   - Les templates utilisent un système de pagination personnalisé
   - Navigation entre les pages avec conservation du contexte de recherche

### Exemple de code Thymeleaf

Le template `patient.html` illustre l'utilisation avancée de Thymeleaf:

```html
<!-- Table des patients avec itération -->
<tbody>
  <tr th:each="p:${patients}">
    <td th:text="${p.id}"></td>
    <td th:text="${p.nom}"></td>
    <td th:text="${p.dateNaissance}"></td>
    <td th:text="${p.malade}"></td>
    <td th:text="${p.score}"></td>
    <!-- Boutons d'action avec contrôle d'accès par rôle -->
    <td th:if="${#authorization.expression('hasRole(''admin'')')}">
      <a th:href="@{/admin/delete(id=${p.id}, keyword = ${ky}, page = ${currentPage})}" 
         class="btn btn-danger">Delete</a>
    </td>
  </tr>
</tbody>

<!-- Pagination dynamique -->
<ul class="nav nav-pills">
  <li th:each="page,status:${pages}">
    <a th:href="@{/user/index(page=${status.index},keyword=${ky})}" 
       th:class="${currentPage == status.index ? 'btn btn-primary ms-1':'btn btn-outline-primary ms-1'}" 
       th:text="${status.index}"></a>
  </li>
</ul>
```

### Avantages de Thymeleaf dans l'application

1. **Intégration native avec Spring** - Fonctionne parfaitement avec Spring MVC et Security
2. **Prototypes naturels** - Les templates sont valides en HTML et peuvent être visualisés même sans serveur
3. **Sécurité** - Protection contre les attaques XSS avec échappement automatique
4. **Contrôle d'accès basé sur les rôles** - Intégration avec Spring Security pour montrer/cacher des éléments selon les autorisations
5. **Maintenance simplifiée** - Séparation claire entre la logique et la présentation

## Installation et déploiement

### Prérequis
- JDK 17 ou supérieur
- Maven 3.6 ou supérieur
- Base de données (H2 par défaut, configurable)

### Étapes d'installation

1. Cloner le dépôt :
```bash
git clone [url-du-dépôt]
cd jee-spring-mvc
```

2. Compiler l'application :
```bash
mvn clean install
```

3. Exécuter l'application :
```bash
mvn spring-boot:run
```
ou
```bash
java -jar target/jee-spring-mvc-0.0.1-SNAPSHOT.jar
```

4. Accéder à l'application via un navigateur : http://localhost:8080

## Configuration

La configuration principale de l'application se trouve dans le fichier `application.properties`. 
Vous pouvez modifier les paramètres suivants selon vos besoins :

- Configuration de la base de données
- Niveau de journalisation
- Port du serveur
- Configurations spécifiques à Spring Security
