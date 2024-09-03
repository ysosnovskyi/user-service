# User Service

## Possible improvements:
- Change 'no-content' response to some, which contains the status of a user update operation
- According to requirements id and balance of the User entity were of type Integer. That's not the best choice, let's use Long or Uuid for User id, and some BigDecimal for the balance
- Population script must be moved from migrations, that was done only in this test task for simplicity. For example, in ITs we can populate data using the @Sql approach
- BATCH_SIZE is fully configurable, set it according to your input data
