# API Automation

A Java and Gradle project containing API integration tests written with TestNG and REST Assured. The tests exercise two APIs:

- **Simple Grocery Store API** (`https://simple-grocery-store-api.click`): product, cart, API-client registration, and order endpoints.
- **JSON Server** (`http://localhost:3000`): CRUD operations for `/posts`.

## Requirements

- JDK 11 or later
- Internet access to run the Simple Grocery Store API tests
- A JSON Server running on port `3000` with a `/posts` resource to run the JSON Server tests

The Gradle wrapper uses Gradle 8.13, so a separate Gradle installation is not needed.

## Run the tests

From the repository root in PowerShell:

```powershell
.\gradlew.bat test
```

This runs both suites. The JSON Server suite requires the local service to be running first; without it, those tests cannot connect.

Run either suite on its own:

```powershell
.\gradlew.bat test --tests "click.SimpleGroceryStore.api.GroceryStoreApiTest"
.\gradlew.bat test --tests "jsonServer.test.JsonServerStructureApiTest"
```

## Start the local JSON Server

Provide a JSON Server database file containing a `posts` collection, then start a compatible JSON Server on port `3000`. For example, a `db.json` file can start with:

```json
{
  "posts": [
    {
      "id": "1",
      "title": "Example post",
      "views": 0
    }
  ]
}
```

With JSON Server installed, start it using the command supported by your installed version (commonly `npx json-server --watch db.json --port 3000`). Keep the server running while executing the JSON Server test suite.

Some tests create or modify posts, and one deletes a post. Use disposable test data and restore the database as needed.
