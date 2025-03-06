## 3.3 C

### Advantages

- Realistic testing environment (using a real database ensures that tests closely mimic production conditions)
- Performance benchmarking (helps measure actual query execution time and database load, ensuring scalability)
- Testing SQL queries (verifies that queries work as expected, especially when using complex joins)
- Ensuring Schema Validity (confirms that the schema is correctly implemented and migrations work as intended)
- Identifying integration issues (ensures seamless interaction between the application and database, catching issues early)

### Disadvantages

- Slower test execution (database connections, queries, and transactions introduce latency, slowing down test runs). It's must faster when using mocks
- Data cleanup issues (tests can leave residual data)
- Dependency on external resources (if the database is down or misconfigured, tests may fail unpredictably even when the buisness logic is right)
- Requires setting up
- Not ideal for Unit Tests