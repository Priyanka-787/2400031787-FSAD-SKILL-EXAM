package com.restaurant.client;

import com.restaurant.entity.Restaurant;
import com.restaurant.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Demonstrates HQL operations using Named Parameters
 * ─────────────────────────────────────────────────
 *  1. Insert sample data
 *  2. SELECT all restaurants
 *  3. SELECT by status  (named param)
 *  4. SELECT by name    (named param)
 *  5. UPDATE status     (named params)
 *  6. UPDATE rating     (named params)
 *  7. DELETE by status  (named param)
 */
public class ClientDemo {

    // ──────────────────────────────────────────────────────────────────────────
    // 1. INSERT – save sample restaurants
    // ──────────────────────────────────────────────────────────────────────────
    public static void insertSampleData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx  = null;
        try {
            tx = session.beginTransaction();

            session.save(new Restaurant("Spice Garden",  "OPEN",   "Indian",    "Hyderabad", 4.5));
            session.save(new Restaurant("Pizza Palace",  "OPEN",   "Italian",   "Hyderabad", 4.2));
            session.save(new Restaurant("Dragon Wok",    "CLOSED", "Chinese",   "Hyderabad", 3.8));
            session.save(new Restaurant("Burger Barn",   "OPEN",   "American",  "Hyderabad", 4.0));
            session.save(new Restaurant("Sushi Spot",    "CLOSED", "Japanese",  "Hyderabad", 4.7));

            tx.commit();
            System.out.println("✔  Sample data inserted successfully.\n");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 2. SELECT ALL
    // ──────────────────────────────────────────────────────────────────────────
    public static void selectAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Restaurant";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            List<Restaurant> list   = query.list();

            System.out.println("── All Restaurants ──────────────────────────");
            list.forEach(System.out::println);
            System.out.println();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 3. SELECT by STATUS  (named parameter :status)
    // ──────────────────────────────────────────────────────────────────────────
    public static void selectByStatus(String status) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Restaurant r WHERE r.status = :status";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            query.setParameter("status", status);           // named param
            List<Restaurant> list = query.list();

            System.out.println("── Restaurants with status = '" + status + "' ──");
            list.forEach(System.out::println);
            System.out.println();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 4. SELECT by NAME (partial match, named parameter :name)
    // ──────────────────────────────────────────────────────────────────────────
    public static void selectByName(String keyword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Restaurant r WHERE r.name LIKE :name";
            Query<Restaurant> query = session.createQuery(hql, Restaurant.class);
            query.setParameter("name", "%" + keyword + "%"); // named param
            List<Restaurant> list = query.list();

            System.out.println("── Restaurants matching name '" + keyword + "' ──");
            list.forEach(System.out::println);
            System.out.println();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 5. UPDATE status by id  (named parameters :newStatus, :id)
    // ──────────────────────────────────────────────────────────────────────────
    public static void updateStatusById(int id, String newStatus) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx  = null;
        try {
            tx = session.beginTransaction();

            String hql = "UPDATE Restaurant r SET r.status = :newStatus " +
                         "WHERE r.id = :id";
            Query<?> query = session.createQuery(hql);
            query.setParameter("newStatus", newStatus);     // named param
            query.setParameter("id", id);                   // named param
            int rowsAffected = query.executeUpdate();

            tx.commit();
            System.out.println("── updateStatusById: " + rowsAffected +
                               " row(s) updated (id=" + id +
                               ", newStatus=" + newStatus + ")\n");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 6. UPDATE status for ALL restaurants with a given old status
    //    Named params: :oldStatus, :newStatus
    // ──────────────────────────────────────────────────────────────────────────
    public static void updateStatusBulk(String oldStatus, String newStatus) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx  = null;
        try {
            tx = session.beginTransaction();

            String hql = "UPDATE Restaurant r SET r.status = :newStatus " +
                         "WHERE r.status = :oldStatus";
            Query<?> query = session.createQuery(hql);
            query.setParameter("newStatus", newStatus);     // named param
            query.setParameter("oldStatus", oldStatus);     // named param
            int rowsAffected = query.executeUpdate();

            tx.commit();
            System.out.println("── updateStatusBulk: " + rowsAffected +
                               " row(s) updated (" + oldStatus +
                               " → " + newStatus + ")\n");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 7. UPDATE rating by id  (named parameters :rating, :id)
    // ──────────────────────────────────────────────────────────────────────────
    public static void updateRatingById(int id, double newRating) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx  = null;
        try {
            tx = session.beginTransaction();

            String hql = "UPDATE Restaurant r SET r.rating = :rating " +
                         "WHERE r.id = :id";
            Query<?> query = session.createQuery(hql);
            query.setParameter("rating", newRating);        // named param
            query.setParameter("id", id);                   // named param
            int rowsAffected = query.executeUpdate();

            tx.commit();
            System.out.println("── updateRatingById: " + rowsAffected +
                               " row(s) updated (id=" + id +
                               ", newRating=" + newRating + ")\n");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 8. DELETE by STATUS  (named parameter :status)
    // ──────────────────────────────────────────────────────────────────────────
    public static void deleteByStatus(String status) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx  = null;
        try {
            tx = session.beginTransaction();

            String hql = "DELETE FROM Restaurant r WHERE r.status = :status";
            Query<?> query = session.createQuery(hql);
            query.setParameter("status", status);           // named param
            int rowsAffected = query.executeUpdate();

            tx.commit();
            System.out.println("── deleteByStatus: " + rowsAffected +
                               " row(s) deleted (status=" + status + ")\n");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // MAIN – orchestrates all operations
    // ──────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  Restaurant HQL Demo – Named Params    ");
        System.out.println("========================================\n");

        // 1. Insert
        insertSampleData();

        // 2. View all
        selectAll();

        // 3. Filter by status
        selectByStatus("OPEN");

        // 4. Filter by name keyword
        selectByName("Pizza");

        // 5. Update a single restaurant's status by id
        updateStatusById(1, "CLOSED");

        // 6. Verify the change
        selectByStatus("CLOSED");

        // 7. Bulk update: reopen all CLOSED restaurants
        updateStatusBulk("CLOSED", "OPEN");

        // 8. Update rating
        updateRatingById(2, 4.9);

        // 9. View all after updates
        selectAll();

        // 10. Delete all CLOSED restaurants
        deleteByStatus("CLOSED");

        // 11. Final view
        System.out.println("── Final state ──────────────────────────────");
        selectAll();

        HibernateUtil.shutdown();
        System.out.println("Done.");
    }
}
