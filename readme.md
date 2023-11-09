# SEO Automation Tests with Java, JUnit5, and Jsoup

This repository contains a collection of automated tests designed for simple SEO checks. These tests are an essential part of ensuring that your website is optimized for search engines. They validate critical elements like meta titles and descriptions, sitemap links, and the HTTP status of webpage links.

## Features

- **Meta Title and Description Verification**: Tests to ensure that web pages have the correct meta titles and descriptions as per SEO best practices.
- **Sitemap Link Validation**: Checks to confirm that sitemap files contain the proper URLs, aiding in effective site indexing.
- **Link Status Verification**: Ensures that all webpage links return the correct HTTP status code (200 OK), indicating they are accessible and not broken.

## Built With

- [Java](https://java.com): The core programming language used for writing the test scripts.
- [JUnit5](https://junit.org/junit5/): The new generation testing framework for Java applications.
- [Jsoup](https://jsoup.org/): A Java library for working with real-world HTML, providing a very convenient API for extracting and manipulating data.

## Test Data

The tests are parameterized using JUnit5's `@CsvFileSource` and `@ValueSource`, allowing for easy management and extension of test data.

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

Ensure you have the following installed:
- Java Development Kit (JDK)
- Maven (for managing dependencies and running the tests)

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/seo-automation-tests.git
    ```
    Navigate to the project directory:

    ```sh
    cd seo-automation-tests
    ```
    Install the dependencies:

    ```sh
    mvn install
    ```
2. Running the tests

    To run the tests, execute the following command:
    ```sh
    mvn test
    ```
   
