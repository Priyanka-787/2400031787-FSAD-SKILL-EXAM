# Restaurant Hibernate HQL Demo

## Project Structure

```
RestaurantHibernate/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/restaurant/
        │       ├── entity/
        │       │   └── Restaurant.java       ← JPA Entity
        │       ├── util/
        │       │   └── HibernateUtil.java    ← SessionFactory singleton
        │       └── client/
        │           └── ClientDemo.java       ← HQL demo (all operations)
        └── resources/
            └── hibernate.cfg.xml             ← DB config
```

## Entity: Restaurant

| Field       | Type   | Column            | Notes               |
|-------------|--------|-------------------|---------------------|
| id          | int    | id (PK, AUTO INC) | Generated           |
| name        | String | name              | Not null, max 100   |
| status      | String | status            | OPEN / CLOSED / etc |
| cuisineType | String | cuisine_type      | Max 50 chars        |
| location    | String | location          | Max 150 chars       |
| rating      | double | rating            | 0.0 – 5.0           |

## HQL Operations in ClientDemo

| Method                              | HQL Operation                            |
|-------------------------------------|------------------------------------------|
| `insertSampleData()`                | session.save() (not HQL)                |
| `selectAll()`                       | `FROM Restaurant`                        |
| `selectByStatus(status)`            | `WHERE r.status = :status`               |
| `selectByName(keyword)`             | `WHERE r.name LIKE :name`                |
| `updateStatusById(id, newStatus)`   | `SET r.status = :newStatus WHERE id=:id` |
| `updateStatusBulk(old, new)`        | Bulk UPDATE with :oldStatus :newStatus   |
| `updateRatingById(id, rating)`      | `SET r.rating = :rating WHERE id=:id`   |
| `deleteByStatus(status)`            | `DELETE WHERE r.status = :status`        |

## Prerequisites

- Java 11+
- Maven 3.6+
- MySQL 8.x running locally

## Database Setup

```sql
CREATE DATABASE restaurant_db;
-- Hibernate will auto-create the `restaurant` table (hbm2ddl.auto=update)
```

## Configuration

Edit `src/main/resources/hibernate.cfg.xml`:

```xml
<property name="hibernate.connection.url">
    jdbc:mysql://localhost:3306/restaurant_db?useSSL=false&serverTimezone=UTC
</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">root</property>
```

## Build & Run

```bash
# Compile
mvn clean compile

# Run ClientDemo
mvn exec:java

# Or build fat-jar and run
mvn clean package
java -cp target/RestaurantHibernate-1.0-SNAPSHOT.jar com.restaurant.client.ClientDemo
```

## Expected Console Output

```
========================================
  Restaurant HQL Demo – Named Params    
========================================

✔  Sample data inserted successfully.

── All Restaurants ──────────────────────────
Restaurant{id=1, name='Spice Garden', status='OPEN', ...}
...

── Restaurants with status = 'OPEN' ──
...

── updateStatusById: 1 row(s) updated (id=1, newStatus=CLOSED)
...
Done.
```
