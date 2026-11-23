# 🚀 QA Automation Framework – Selenium + BDD (OrangeHRM)

[![CI](https://github.com/SebasAre96/qa-automation-selenium-orangehrm/actions/workflows/ci.yml/badge.svg)](https://github.com/SebasAre96/qa-automation-selenium-orangehrm/actions/workflows/ci.yml)
[![Allure Report](https://img.shields.io/badge/Allure-Reporte%20en%20vivo-orange)](https://sebasare96.github.io/qa-automation-selenium-orangehrm/)

## 🧪 Descripción general

Framework de automatización de pruebas funcionales sobre **OrangeHRM Demo**, con **Selenium WebDriver** y un enfoque
**BDD con Cucumber**. Valida flujos críticos de negocio mediante pruebas **End-to-End (E2E)** y se ejecuta de forma
automática en **GitHub Actions** y **Jenkins**, publicando un reporte **Allure**.

📊 **Último reporte:** https://sebasare96.github.io/qa-automation-selenium-orangehrm/

---

## 💼 ¿Qué demuestra este proyecto?

- Framework de automatización UI escalable con **Page Object Model (POM)**
- **BDD** con Cucumber y Gherkin, con escenarios organizados por **tags** (`@smoke`, `@regression`, `@e2e`)
- **Data-Driven Testing** con `Scenario Outline`
- **CI/CD** con GitHub Actions y Jenkins (pipeline como código)
- **Reportes Allure** publicados en GitHub Pages
- **Capturas automáticas** de pantalla cuando un escenario falla
- **Configuración por entorno** y parámetros de ejecución (navegador, headless, tags)
- Driver **thread-safe** (`ThreadLocal`), listo para ejecución en paralelo

---

## 🌐 Aplicación bajo prueba

👉 https://opensource-demo.orangehrmlive.com/

---

## 🧰 Stack tecnológico

- ☕ Java 17
- 🌐 Selenium WebDriver 4
- 🥒 Cucumber 7 (BDD – Gherkin)
- 🧪 TestNG
- 📦 Maven
- 📊 Allure Report
- ⚙️ Selenium Manager (gestión automática de drivers)
- 🔁 GitHub Actions · Jenkins

---

## 📁 Estructura del proyecto

```
├── .github/
│   ├── workflows/ci.yml          # Pipeline de GitHub Actions
│   └── dependabot.yml            # Actualización de dependencias
├── Jenkinsfile                   # Pipeline de Jenkins
├── pom.xml
├── testng.xml
│
└── src/test/
    ├── java/ar/org/proyectoFinal/
    │   ├── config/               # ConfigReader (entornos y propiedades)
    │   ├── pages/                # Page Objects
    │   ├── runner/               # TestRunner + Hooks
    │   ├── steps/                # Step Definitions
    │   └── utils/                # DriverManager (ThreadLocal)
    └── resources/
        ├── config/qa.properties  # Configuración del entorno
        ├── allure.properties
        └── features/             # Escenarios Gherkin
            ├── flujo_completo.feature
            └── flujo_completo_data-driven.feature
```

---

## 🧪 Cobertura de pruebas

| Tag            | Escenarios                                                         |
|----------------|--------------------------------------------------------------------|
| `@smoke`       | Login exitoso                                                      |
| `@regression`  | Login inválido, búsqueda de empleado (PIM), flujo E2E, data-driven |
| `@e2e`         | Login → PIM → búsqueda → logout                                    |
| `@data-driven` | Login y búsqueda con múltiples combinaciones de datos              |

---

## ▶️ Ejecución

```bash
# Todo, con navegador visible
mvn clean test

# Solo smoke, sin interfaz gráfica (como en CI)
mvn clean test -Dheadless=true -Dcucumber.filter.tags="@smoke"

# Combinación de tags y otro navegador
mvn clean test -Dbrowser=firefox -Dcucumber.filter.tags="@smoke or @e2e"
```

| Parámetro                | Valores                         | Default              |
|--------------------------|---------------------------------|----------------------|
| `-Dbrowser`              | `chrome`, `firefox`             | `chrome`             |
| `-Dheadless`             | `true`, `false`                 | `false`              |
| `-Dcucumber.filter.tags` | expresión de tags               | todos                |
| `-Denv`                  | nombre del archivo en `config/` | `qa`                 |
| `-DbaseUrl`              | URL de la aplicación            | la del `.properties` |

> **Windows (PowerShell):** poné comillas alrededor de cada parámetro `-D`:
> `mvn clean test "-Dheadless=true" "-Dcucumber.filter.tags=@smoke"`

**Paralelo:** en `testng.xml`, subir `data-provider-thread-count` (por defecto `1`).

---

## 📊 Reportes

**Allure en local** (requiere [Allure CLI](https://allurereport.org/docs/install/)):

```bash
allure serve target/allure-results
```

**Reporte HTML de Cucumber:** `target/cucumber-reports/cucumber.html`

Cuando un escenario falla, se adjunta una **captura de pantalla** en ambos reportes.

---

## 🔁 CI/CD

### GitHub Actions (`.github/workflows/ci.yml`)

- Se ejecuta en cada **push**, **pull request**, de forma **programada** (días hábiles) y **manual**
  (`workflow_dispatch`, eligiendo tags y navegador).
- Corre `@smoke` en modo headless con Java 17 y caché de Maven.
- Sube los resultados como artefactos y, desde la rama principal, **publica el reporte Allure en GitHub Pages**.

> Configuración única: en el repo, **Settings → Pages → Source: GitHub Actions**.

### Jenkins (`Jenkinsfile`)

- Pipeline declarativo con parámetros: `BROWSER`, `TAGS` y `HEADLESS`.
- Publica resultados JUnit, archiva los reportes y genera Allure si el plugin está instalado.
- Requiere en *Manage Jenkins → Tools* un Maven (`Maven3`) y un JDK (`JDK17`), o ajustar los nombres en el `tools {}`
  del archivo.

---

## 🧱 Arquitectura y buenas prácticas

- ✔️ Page Object Model con `BasePage` y esperas explícitas
- ✔️ BDD con separación Features / Steps / Pages / Runner
- ✔️ Configuración externalizada, sin URLs ni credenciales en el código de las pages
- ✔️ Hooks para setup, teardown y evidencias
- ✔️ Gestión automática de drivers con Selenium Manager
- ✔️ Driver por hilo (`ThreadLocal`)
- ✔️ Pipeline como código (GitHub Actions + Jenkinsfile)

---

## 👨‍💻 Autor

**Sebastian Arevalo** · [GitHub](https://github.com/SebasAre96)
