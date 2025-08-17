Absolutely—here’s a single, **all-in-one, copy-paste-ready** `README.md` with clear **step-by-step** guidance tailored to your project.

```markdown
# 🚀 Selenium Java Maven Framework Design

A robust **UI Test Automation Framework** using **Selenium WebDriver**, **Java (JDK 17)**, **TestNG**, and **Maven**, designed with the **Page Object Model (POM)**.  
It supports **data-driven testing (JSON + Jackson)**, **automatic WebDriver management**, **Extent Reports**, **screenshots on failure via TestNG listeners**, **retry for flaky tests**, and **daily CI pipelines on Jenkins**.

---

## 🔎 Table of Contents

1. [Project Structure](#-project-structure)  
2. [Tech Stack & Dependencies](#-tech-stack--dependencies)  
3. [Prerequisites](#-prerequisites)  
4. [Quick Start (Step-by-Step)](#-quick-start-step-by-step)  
5. [Running Tests (Suites & Profiles)](#-running-tests-suites--profiles)  
6. [Configuration (GlobalData.properties)](#-configuration-globaldataproperties)  
7. [Data-Driven Testing (JSON → HashMap)](#-data-driven-testing-json--hashmap)  
8. [Listeners, Retry & Screenshots](#-listeners-retry--screenshots)  
9. [Reporting (Extent Reports)](#-reporting-extent-reports)  
10. [Jenkins CI/CD (Daily Pipelines)](#-jenkins-cicd-daily-pipelines)  
11. [Extend the Framework](#-extend-the-framework)  
12. [Troubleshooting](#-troubleshooting)  
13. [Author](#-author)  
14. [License](#-license)

---

## 📂 Project Structure

```

MavenFrameworkDesign
│── pom.xml                                   # Maven dependencies, plugins, and profiles
│── reports/                                   # Extent HTML reports + screenshots (generated)
│── test-output/                               # TestNG default output (generated)
│── testSuites/                                # TestNG suite XMLs (e.g., smokeSuite.xml, orderPlacement.xml, errorValidation.xml)
│
└── src
├── main
│   └── java
│       ├── MavenFrameworkDesign.PageObjects
│       │   ├── cartPage.java
│       │   ├── checkoutPage.java
│       │   ├── ConfirmationPage.java
│       │   ├── loginPage.java
│       │   ├── orderPage.java
│       │   └── pdp.java
│       │
│       ├── MavenFrameworkDesign.resources
│       │   ├── ExtentReporterNG.java
│       │   └── GlobalData.properties
│       │
│       └── MavenFrameworkDesign.Utility
│           └── fileUtil.java
│
└── test
└── java
├── MavenFrameworkDesign.data
│   └── data.json
│
├── MavenFrameworkDesign.TestComponents
│   ├── BaseTest.java
│   ├── Listeners.java
│   └── Retry.java
│
└── MavenFrameworkDesign.tests
├── e2e.java
├── ErrorValidation.java
├── ErrorValidationWrongValidation.java
└── placeOrder.java

````

---

## 🧰 Tech Stack & Dependencies

- **Java**: JDK 17  
- **Selenium Java**: `4.32.0`  
- **TestNG**: `7.11.0`  
- **ExtentReports**: `5.1.2`  
- **WebDriverManager**: `6.1.0` (auto driver downloads)  
- **JSON (org.json)**: `20250517`  
- **Jackson Databind**: `2.19.0`  

Maven profiles (from `pom.xml`) to run specific suites:
- `MyRegression` → `testSuites/smokeSuite.xml`
- `Orders` → `testSuites/orderPlacement.xml`
- `ErrorValidation` → `testSuites/errorValidation.xml`

---

## ✅ Prerequisites

1. **Java 17** installed and on `PATH` (`java -version`).  
2. **Maven 3.8+** installed (`mvn -version`).  
3. (Optional) **Chrome/Firefox** installed—drivers are handled automatically by WebDriverManager.  
4. An IDE like **IntelliJ** or **Eclipse** with **TestNG** plugin.

---

## ⚡ Quick Start (Step-by-Step)

1. **Clone the repository**
   ```bash
   git clone https://github.com/raghavendran-n/MavenFrameworkDesign.git
   cd MavenFrameworkDesign
````

2. **Build & run all tests (default)**

   ```bash
   mvn clean test
   ```

3. **Run a specific TestNG suite**

   ```bash
   mvn test -DsuiteXmlFile=testSuites/orderPlacement.xml
   ```

4. **Run via Maven profiles**

   ```bash
   mvn test -P MyRegression
   mvn test -P Orders
   mvn test -P ErrorValidation
   ```

5. **Open the HTML report**

   ```
   reports/index.html
   ```

---

## ⚙️ Configuration (GlobalData.properties)

Common keys (edit in `src/main/java/MavenFrameworkDesign/resources/GlobalData.properties`):

```
baseUrl=https://your-app-under-test.com
browser=chrome          # chrome | firefox | edge (as supported in BaseTest)
implicitWait=10
explicitWait=15
username=test-user@example.com
password=secret
```

> Tip: If you support command-line overrides in `BaseTest`, you can run:
>
> ```bash
> mvn test -Dbrowser=firefox -DbaseUrl=https://staging.your-app.com
> ```

---

## 📑 Data-Driven Testing (JSON → HashMap)

* **Test data** lives in `src/test/java/MavenFrameworkDesign/data/data.json`.
* Data is read and converted to `List<HashMap<String, String>>` using **Jackson Databind**.

**Sample JSON (`data.json`):**

```json
[
  { "email": "user1@example.com", "password": "pwd1", "product": "ZARA COAT 3" },
  { "email": "user2@example.com", "password": "pwd2", "product": "ADIDAS ORIGINAL" }
]
```

**Utility idea used (already in your code):**

```java
public List<HashMap<String, String>> jsonDataToHashMap(String filePath) throws IOException {
    String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
}
```

---

## 🧩 Listeners, Retry & Screenshots

### 1) **Screenshots on Failure (TestNG Listener)**

* Implemented in `MavenFrameworkDesign.TestComponents.Listeners`.
* On failure, it:

  * Grabs the `WebDriver` from the test instance.
  * Calls `getScreenshot(testName, driver)` from `BaseTest`.
  * Attaches the screenshot to **Extent Reports**.

**Screenshot path format:**

```
reports/<TestCaseName>.png
```

### 2) **Retry Mechanism (IRetryAnalyzer)**

* `MavenFrameworkDesign.TestComponents.Retry` retries failed tests once by default:

  ```java
  int count = 0;
  int maxtry = 1; // adjust as needed
  ```
* Apply via:

  * `@Test(retryAnalyzer = Retry.class)` on tests, or
  * Globally through a listener/annotation strategy.

### 3) **Enable Listeners**

* If not already enabled project-wide, add **either**:

  * `@Listeners({ Listeners.class })` on your base test/test classes, **or**
  * Include the listener in your `testng.xml`:

    ```xml
    <listeners>
      <listener class-name="MavenFrameworkDesign.TestComponents.Listeners"/>
    </listeners>
    ```

---

## 📊 Reporting (Extent Reports)

* Configured via `ExtentReporterNG.java`:

  * Report path: `reports/index.html`
  * Custom report & document titles (e.g., *Raghav's Execution Report*)

**Open after execution:**

```
reports/index.html
```

**What you get:**

* Pass/Fail/Skip summary
* Error stacktraces on failure
* Attached screenshots for failures

---

## 🧱 Jenkins CI/CD (Daily Pipelines)

This project is integrated with **Jenkins** for:

* **Daily scheduled runs** (CRON)
* **On-push**/PR triggers
* **Archiving reports** (Extent HTML + screenshots)

**Example Declarative Pipeline (Jenkinsfile):**

```groovy
pipeline {
  agent any

  triggers {
    cron('H H * * *')   // once daily at a hashed hour
  }

  tools {
    jdk 'jdk-17'
    maven 'maven-3.9'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build & Test (Regression)') {
      steps {
        sh 'mvn -B clean test -P MyRegression'
      }
    }

    stage('Archive Reports') {
      steps {
        archiveArtifacts artifacts: 'reports/**', fingerprint: true
        publishHTML(target: [
          reportDir: 'reports',
          reportFiles: 'index.html',
          reportName: 'Extent Report'
        ])
      }
    }
  }

  post {
    always {
      junit testResults: 'test-output/testng-results.xml', allowEmptyResults: true
    }
    failure {
      mail to: 'team@example.com',
           subject: "[Jenkins] UI Tests Failed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
           body: "Check Extent report and console output for details."
    }
  }
}
```

> Tip: On Windows nodes, replace `sh 'mvn ...'` with `bat 'mvn ...'`.

---

## ➕ Extend the Framework

* **Add a Page**
  Create a class under `MavenFrameworkDesign.PageObjects` and encapsulate locators + actions.
  Reuse `BaseTest` setup to get the driver.

* **Add a Test**
  Create under `MavenFrameworkDesign.tests`, consume page objects, and assert expected behavior.
  Optionally add `@Test(retryAnalyzer = Retry.class)`.

* **Add Data**
  Append to `data.json` and read via your JSON→HashMap utility.

* **Add a Suite**
  Create a new TestNG XML in `testSuites/`, then wire a new Maven profile in `pom.xml` to run it easily (mirroring the existing `MyRegression`, `Orders`, `ErrorValidation` profiles).

---

## 🛠️ Troubleshooting

* **`mvn` not recognized**
  Ensure Maven is installed and added to `PATH` (`mvn -version` must work).

* **Driver/Browser issues**
  Thanks to **WebDriverManager**, drivers auto-download. Ensure your browser version is compatible or try another browser (`browser=chrome|firefox|edge`).

* **Reports not generated**
  Make sure `Listeners` are active and `ExtentReporterNG` creates `reports/index.html`.
  After a run, delete old `reports/` if permissions lock files, then re-run.

* **Parallel or flaky test failures**
  Use `Retry` (increase `maxtry`) and validate explicit waits in POM methods.

## 👤 Author

**Raghavendran N**

* LinkedIn: https://www.linkedin.com/in/nraghavendran2k/
* Email: raghavendran.stage@gmail.com

---

## 📜 License

MIT — free to use and adapt with attribution.



