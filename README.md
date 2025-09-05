# selenium-testng-cucumber

Selenium + TestNG + Cucumber example automation framework used for assignment/interview purposes.  
This README explains project layout, setup instructions, how to run tests in IntelliJ and Maven, parallel execution strategies, common pitfalls, and troubleshooting.

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

