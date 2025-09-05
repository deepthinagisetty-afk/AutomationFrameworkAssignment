# selenium-testng-cucumber

Selenium + TestNG + Cucumber example automation framework used for assignment/interview purposes.  
This README explains project layout, setup instructions, how to run tests in IntelliJ and Maven, parallel execution strategies, common pitfalls, and troubleshooting.

---

## 📂 Project structure

selenium-testng-cucumber/
│
├── src/test/java/ # Runner classes, step definitions, hooks, utils, listeners
├── src/test/resources/
│ ├── features/ # Cucumber feature files
│ └── testng.xml # TestNG suite configuration
├── pom.xml # Maven dependencies and plugins
└── README.md # This file

yaml
Copy code

---

## ⚙️ Prerequisites

- **Java 17+** installed and `JAVA_HOME` configured.
- **Maven 3.6+** installed (for CLI execution).
- **Browsers**: Chrome/Edge/Firefox.
- Recommended: **WebDriverManager** (used in the project) so no manual driver binaries are needed.

---

## ▶️ Quick start

### Run from IntelliJ IDEA
1. Import the project as a Maven project.
2. Set Project SDK to Java 17.
3. Right-click `src/test/resources/testng.xml` → **Run**.

> Note: IntelliJ runs TestNG in a single JVM. For true per-`<test>` parallel isolation, use Maven.

---

### Run from Maven

```bash
mvn test
This executes the TestNG suite defined in src/test/resources/testng.xml.

🚀 Execution strategies
1. Scenario-parallel (single runner, no forks)
Runner scans all feature files.

Scenarios run in parallel threads.

java
Copy code
@CucumberOptions(features = "src/test/resources/features", ...)
@DataProvider(parallel = true)
public Object[][] scenarios() { return super.scenarios(); }
2. Per-feature runners + TestNG parallel="classes"
One runner class per feature.

Parallel execution at class level (same JVM, no forks).

Controlled by TestNG suite.

xml
Copy code
<suite name="ParallelFeatureSuite" parallel="classes" thread-count="4">
  <test name="FeatureRunners">
    <classes>
      <class name="runner.CoreProductRunner"/>
      <class name="runner.DerivedProduct2Runner"/>
    </classes>
  </test>
</suite>
3. Per-<test> feature isolation (requires Maven fork)
Different <test> blocks pass different feature paths.

Must fork JVMs so cucumber.features isn’t overwritten.

xml
Copy code
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.0.0-M9</version>
  <configuration>
    <suiteXmlFiles>
      <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
    </suiteXmlFiles>
    <parallel>tests</parallel>
    <threadCount>2</threadCount>
    <forkCount>2</forkCount>
    <reuseForks>false</reuseForks>
  </configuration>
</plugin>
Run with:

bash
Copy code
mvn test
🧩 Utilities
DriverFactory / DriverManager — manages ThreadLocal<WebDriver> for parallel-safe execution.

FeaturePathListener — sets cucumber.features, browser, and testdata.file from TestNG <parameter>.

JsonDataReader — loads test data JSON (searches filesystem path, then classpath resource).

Hooks — global @Before/@After to init/quit drivers and capture screenshots.

🛠️ Troubleshooting
No features found

Ensure @CucumberOptions(features=...) points to the correct path or set -Dcucumber.features=... before execution.

Avoid setting cucumber.features in @BeforeClass (too late).

Tests running the wrong feature multiple times

Cause: parallel <test> in same JVM overwrote cucumber.features.

Fix: fork JVMs with Surefire or use per-feature runner classes.

Chromedriver missing

Use WebDriverManager:

java
Copy code
WebDriverManager.chromedriver().setup();
driver = new ChromeDriver();
Failed to instantiate step class

Do not perform file I/O in step constructors.

Load test data in a @Before hook instead.

📌 Example commands
Run all tests:

bash
Copy code
mvn test
Run a single feature:

bash
Copy code
mvn test -Dcucumber.features=src/test/resources/features/CoreProduct.feature
Run with a specific test data file:

bash
Copy code
mvn test -Dtestdata.file=src/test/resources/testdata/CPData.json
🤝 Contributing
Add feature files under src/test/resources/features.

Add step definitions in src/test/java/steps.

Add new runner classes for per-feature execution if needed.

Keep driver and test data access thread-safe.

📄 License
Add a LICENSE file if you plan to distribute publicly.

pgsql
Copy code

---

✅ This `README.md` is detailed enough for **new developers** or **reviewers** to clone, build, and run your project without confusion.  

Do you want me to also add a **minimal example TestNG XML (`testng.xml`)** inside the README so users can quickly copy-paste and run without looking into the repo?







Ask ChatGPT
You said:
Prepare ReAD Me


ChatGPT said:
Got it 👍 — I’ll prepare a final polished README.md for your project
AutomationFrameworkAssignment/selenium-testng-cucumber.

Here’s the content (copy-paste into selenium-testng-cucumber/README.md):

markdown
Copy code
# Selenium + TestNG + Cucumber Automation Framework

This is a hybrid automation framework built using **Selenium 4**, **Cucumber 7**, and **TestNG 7**.  
It demonstrates **BDD-style automation**, **parallel execution**, and **data-driven testing** with support for **JSON test data** and **reporting**.

---

## 📂 Project Structure

selenium-testng-cucumber/
│
├── src/test/java/
│ ├── runner/ # Cucumber + TestNG runners
│ ├── steps/ # Step definitions
│ ├── hooks/ # Setup & teardown (WebDriver, screenshots)
│ ├── utils/ # DriverFactory, DriverManager, JsonDataReader, listeners
│
├── src/test/resources/
│ ├── features/ # Gherkin feature files
│ ├── testng.xml # TestNG suite
│
├── pom.xml # Maven dependencies & plugins
└── README.md # Project documentation

yaml
Copy code

---

## ⚙️ Prerequisites

- **Java 17+**
- **Maven 3.6+**
- **Browsers**: Chrome / Edge / Firefox
- **WebDriverManager** handles driver binaries automatically (no need to download drivers manually)

---

## ▶️ Running Tests

### Run from IntelliJ
1. Import project as a **Maven project**.
2. Ensure **Project SDK = Java 17**.
3. Right-click `testng.xml` → **Run**.

> ⚠️ Note: IntelliJ runs all `<test>` blocks in the **same JVM**, so `cucumber.features` can be overwritten. For true parallel `<test>` isolation, use **Maven**.

---

### Run from Maven

Run all tests:
```bash
mvn test
Run a single feature:

bash
Copy code
mvn test -Dcucumber.features=src/test/resources/features/CoreProduct.feature
Run with specific data file:

bash
Copy code
mvn test -Dtestdata.file=src/test/resources/testdata/CPData.json
🚀 Parallel Execution Options
🔹 Option A — Scenario Parallel (single runner)
All features scanned under src/test/resources/features.

Scenarios run in parallel threads.

Implemented with @DataProvider(parallel = true).

🔹 Option B — Per Feature Runner + TestNG parallel="classes"
Create one runner class per feature.

Run them in parallel as separate TestNG classes.

Works well inside IntelliJ (no fork required).

xml
Copy code
<suite name="ParallelFeatureSuite" parallel="classes" thread-count="2">
  <test name="Features">
    <classes>
      <class name="runner.CoreProductRunner"/>
      <class name="runner.DerivedProduct2Runner"/>
    </classes>
  </test>
</suite>
🔹 Option C — Per <test> Feature (requires Maven fork)
Each <test> block in testng.xml points to a different feature.

Requires Maven Surefire forkCount to isolate JVMs.

xml
Copy code
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.0.0-M9</version>
  <configuration>
    <suiteXmlFiles>
      <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
    </suiteXmlFiles>
    <parallel>tests</parallel>
    <threadCount>2</threadCount>
    <forkCount>2</forkCount>
    <reuseForks>false</reuseForks>
  </configuration>
</plugin>
Run with:

bash
Copy code
mvn test
🛠️ Key Components
DriverFactory / DriverManager → Provides thread-safe WebDriver instances.

JsonDataReader → Loads test data from src/test/resources/testdata.

Hooks → Global @Before/@After for driver setup/teardown & screenshots.

ExtentReports + Cucumber Adapter → HTML, JSON, Extent report outputs.

FeaturePathListener → Picks feature file paths and test data from testng.xml.

🧩 Example testng.xml
xml
Copy code
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >
<suite name="AutomationSuite" parallel="tests" thread-count="2">

  <test name="CoreProduct">
    <parameter name="browser" value="chrome"/>
    <parameter name="features" value="src/test/resources/features/CoreProduct.feature"/>
    <parameter name="dataFile" value="CPData.json"/>
    <classes>
      <class name="runner.TestRunner"/>
    </classes>
  </test>

  <test name="DerivedProduct2">
    <parameter name="browser" value="edge"/>
    <parameter name="features" value="src/test/resources/features/DerivedProduct2.feature"/>
    <parameter name="dataFile" value="DP2Data.json"/>
    <classes>
      <class name="runner.TestRunner"/>
    </classes>
  </test>

</suite>
🛑 Troubleshooting
No features found

Ensure correct path in @CucumberOptions or use -Dcucumber.features=....

Same feature runs multiple times

Happens when multiple <test>s share the same JVM.

Fix: use Option B (per-feature runners) or Option C (Maven fork).

chromedriver must exist

Use WebDriverManager:

java
Copy code
WebDriverManager.chromedriver().setup();
driver = new ChromeDriver();
Failed to instantiate step class

Caused by I/O in step constructors.

Move file reading to a @Before hook.

📊 Reports
HTML Report → target/cucumber-report/cucumber.html

JSON Report → target/cucumber-report/cucumber.json

Extent Report → target/extent-report/

🤝 Contributing
Add new .feature files in src/test/resources/features.

Implement steps under src/test/java/steps.

Use DriverManager.getDriver() instead of creating WebDriver directly.

Keep all step definitions thread-safe.

📄 License
Add a LICENSE file if you plan to open-source this repository.

yaml
Copy code

