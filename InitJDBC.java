
import java.sql.*;
import java.util.*;

public class InitJDBC {
    int access_type;
    boolean logout;
    
    Connection conn;
    Statement stmt;
    
    String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/skandur";
    
    public InitJDBC(MgmtSys dbms) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
            String user = "skandur";
            String passwd = "200204421";
            
            conn = null;
            stmt = null;
            
            try {
                conn = DriverManager.getConnection(jdbcURL, user, passwd);
                stmt = conn.createStatement();
                
                Menu menu = new Menu(dbms, this);
                dropTables(stmt);
                createTables(stmt);
                populateDemoData(stmt);
                do {
                    logout = false;
                    access_type = menu.login();
                    while(!logout)
                        menu.showOperations(access_type, stmt, conn);
                } while(true);
                
            } finally {
                close(stmt);
                close(conn);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }
    
    void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }
    
    void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }
    
    void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
    }
    
    void dropTables(Statement stmt) throws Exception{
        stmt.executeUpdate("DROP TABLE customer_makes");
        stmt.executeUpdate("DROP TABLE reservation_for");
        stmt.executeUpdate("DROP TABLE staff_works_at");
        stmt.executeUpdate("DROP TABLE staff_provides");
        stmt.executeUpdate("DROP TABLE customers");
        stmt.executeUpdate("DROP TABLE services");
        stmt.executeUpdate("DROP TABLE rooms");
        stmt.executeUpdate("DROP TABLE hotels");
        stmt.executeUpdate("DROP TABLE reservations");
        stmt.executeUpdate("DROP TABLE staff");
    }
    
    void createTables(Statement stmt) throws Exception{
        // Create the CUSTOMERS table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customers (" +
                           "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "dob DATE NOT NULL, " +
                           "phone INT NOT NULL, " +
                           "email VARCHAR(128) NOT NULL, " +
                           "ssn INT NOT NULL UNIQUE)");
        
        // Create the STAFF table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS staff ( " +
                           "id INT  PRIMARY KEY AUTO_INCREMENT, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "age INT NOT NULL, " +
                           "title VARCHAR(128) NOT NULL, " +
                           "department VARCHAR(128) NOT NULL, " +
                           "phone INT NOT NULL, " +
                           "address VARCHAR(128) NOT NULL, " +
                           "availability TINYINT(1) NOT NULL)");
        
        // Create the HOTELS table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS hotels (" +
                           "id INT PRIMARY KEY AUTO_INCREMENT, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "address VARCHAR(128) NOT NULL, " +
                           "phone INT NOT NULL, " +
                           "manager_id INT, " +
                           "CONSTRAINT manager_hotel_fk " +
                           "FOREIGN KEY(manager_id) REFERENCES staff(id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE)");
        
        // Create the ROOMS table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rooms (" +
                           "no INT NOT NULL, " +
                           "hotel_id INT NOT NULL, " +
                           "category VARCHAR(128) NOT NULL, " +
                           "max_occupancy INT NOT NULL, " +
                           "price DECIMAL(10,2) NOT NULL, " +
                           "is_available TINYINT(1) NOT NULL, " +
                           "CONSTRAINT hotel_room_fk  " +
                           "FOREIGN KEY(hotel_id) REFERENCES hotels(id)  " +
                           "ON DELETE CASCADE ON UPDATE CASCADE, " +
                           "PRIMARY KEY(no, hotel_id))");
        
        // Create the RESERVATIONS table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS reservations ( " +
                           "id INT PRIMARY KEY AUTO_INCREMENT, " +
                           "no_of_guests INT, " +
                           "start_date DATE NOT NULL, " +
                           "end_date DATE NOT NULL, " +
                           "check_in_time TIME NOT NULL, " +
                           "check_out_time TIME NOT NULL, " +
                           "total_amount DECIMAL(10,2) NOT NULL, " +
                           "payment_method VARCHAR(128) NOT NULL, " +
                           "card_no BIGINT, " +
                           "expiry DATE, " +
                           "billing_address VARCHAR(128), " +
                           "has_paid TINYINT(1) NOT NULL)");
        
        // Create the SERVICES table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS services ( " +
                           "id INT AUTO_INCREMENT NOT NULL, " +
                           "reservation_id INT NOT NULL, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "price DECIMAL(10,2) NOT NULL, " +
                           "CONSTRAINT reservation_service_fk " +
                           "FOREIGN KEY(reservation_id) REFERENCES reservations(id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE, " +
                           "PRIMARY KEY(id, reservation_id))");
        
        // Create the STAFF_WORKS_AT table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS staff_works_at ( " +
                           "hotel_id INT NOT NULL, " +
                           "staff_id INT NOT NULL, " +
                           "CONSTRAINT staff_works_at_pk PRIMARY KEY(hotel_id, staff_id), " +
                           "CONSTRAINT hotel_staff_works_at_fk " +
                           "FOREIGN KEY(hotel_id) REFERENCES hotels(id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE, " +
                           "CONSTRAINT staff_works_at_fk " +
                           "FOREIGN KEY(staff_id) REFERENCES staff(id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE)");
        
        // Create the CUSTOMER_MAKES table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customer_makes ( " +
                           "reservation_id INT NOT NULL," +
                           "customer_id INT NOT NULL," +
                           "CONSTRAINT customer_makes_pk PRIMARY KEY(customer_id, reservation_id)," +
                           "CONSTRAINT reservation_customer_makes_fk " +
                           "FOREIGN KEY(reservation_id) REFERENCES reservations(id)" +
                           "ON DELETE CASCADE ON UPDATE CASCADE," +
                           "CONSTRAINT customer_makes_fk " +
                           "FOREIGN KEY(customer_id) REFERENCES customers(id)" +
                           "ON DELETE CASCADE ON UPDATE CASCADE)");
        
        // Create the RESERVATION_FOR table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS reservation_for ( " +
                           "reservation_id INT NOT NULL, " +
                           "room_no INT NOT NULL, " +
                           "hotel_id INT NOT NULL, " +
                           "CONSTRAINT reservation_for_pk " +
                           "PRIMARY KEY(reservation_id, room_no, hotel_id), " +
                           "CONSTRAINT reservation_for_fk " +
                           "FOREIGN KEY(reservation_id) REFERENCES reservations(id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE, " +
                           "CONSTRAINT room_reservation_for_fk " +
                           "FOREIGN KEY(room_no,hotel_id) REFERENCES rooms(no,hotel_id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE)");
        
        // Create the STAFF_PROVIDES table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS staff_provides ( " +
                           "reservation_id INT NOT NULL, " +
                           "staff_id INT NOT NULL, " +
                           "service_id INT NOT NULL, " +
                           "CONSTRAINT staff_provides_pk " +
                           "PRIMARY KEY(reservation_id, staff_id, service_id), " +
                           "CONSTRAINT staff_provides_fk " +
                           "FOREIGN KEY(staff_id) REFERENCES staff(id)" +
                           "ON DELETE CASCADE ON UPDATE CASCADE, " +
                           "CONSTRAINT service_staff_provides_fk " +
                           "FOREIGN KEY(service_id,reservation_id) REFERENCES services(id,reservation_id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE)");
    }
    
    void populateDemoData(Statement stmt) throws Exception{
        //Populate the CUSTOMERS table
        stmt.executeUpdate("INSERT INTO customers (name,dob,phone,email,ssn) " +
                           "VALUES ('David', '1980-01-30', 123, 'david@gmail.com', 5939846)");
        
        stmt.executeUpdate("INSERT INTO customers (name,dob,phone,email,ssn)" +
                           "VALUES ('Sarah', '1971-01-30', 456, 'sarah@gmail.com', 7778352)");
        
        stmt.executeUpdate("INSERT INTO customers (name,dob,phone,email,ssn) " +
                           "VALUES ('Joseph', '1987-01-30', 789, 'joseph@gmail.com', 8589430)");
        
        stmt.executeUpdate("INSERT INTO customers (name,dob,phone,email,ssn)" +
                           "VALUES ('Lucy', '1985-01-30', 213, 'lucy@gmail.com', 4409328)");
        
        //Populate the STAFF table
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability) " +
                           "VALUES ('Mary', 40, 'Manager',  'Management', 654, 'Raleigh NC', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('John', 45, 'Manager',  'Management', 564, 'Rochester NY', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability) " +
                           "VALUES ('Carol', 55, 'Manager',  'Management', 564, 'Greensboro NC', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('Emma', 55, 'Front Desk Staff',  'Management', 546, 'Raleigh NC', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('Ava', 55, 'Catering Staff',  'Catering', 777, 'Raleigh NC', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('Peter', 52, 'Manager',  'Management', 724, 'Raleigh NC', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('Olivia', 27, 'Front Desk Staff',  'Management', 799, 'Raleigh NC', 1)");
        
        // Populate the HOTELS table
        stmt.executeUpdate("INSERT INTO hotels (name,address,phone,manager_id)" +
                           "VALUES ('Hotel A', 'Raleigh NC', 919, 1)");
        stmt.executeUpdate("INSERT INTO hotels (name,address,phone,manager_id)" +
                           "VALUES ('Hotel B', 'Rochester NY', 718, 2)");
        stmt.executeUpdate("INSERT INTO hotels (name,address,phone,manager_id)" +
                           "VALUES ('Hotel C', 'Greensboro NC', 984, 3)");
        stmt.executeUpdate("INSERT INTO hotels (name,address,phone,manager_id)" +
                           "VALUES ('Hotel D', 'Raleigh NC', 920, 6)");
        
        //Populate the ROOMS table
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 1, 1, 'Economy', 1, 100, 0)");
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 2, 1, 'Deluxe', 2, 200, 0)");
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 3, 2, 'Economy', 1, 100, 0)");
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 2, 3, 'Executive', 3, 1000, 0)");
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 1, 4, 'Presidential', 4, 5000, 1)");
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 5, 1, 'Deluxe', 2, 200, 1)");
        
        //Populate the STAFF_WORKS_AT table
        stmt.executeUpdate("INSERT INTO staff_works_at (hotel_id, staff_id)" +
                           "VALUES (1, 1)");
        
        stmt.executeUpdate("INSERT INTO staff_works_at (hotel_id, staff_id)" +
                           "VALUES (2, 2)");
        
        stmt.executeUpdate("INSERT INTO staff_works_at (hotel_id, staff_id)" +
                           "VALUES (3, 3)");
        
        stmt.executeUpdate("INSERT INTO staff_works_at (hotel_id, staff_id)" +
                           "VALUES (1, 4)");
        
        stmt.executeUpdate("INSERT INTO staff_works_at (hotel_id, staff_id)" +
                           "VALUES (1, 5)");
        
        stmt.executeUpdate("INSERT INTO staff_works_at (hotel_id, staff_id)" +
                           "VALUES (4, 6)");
        
        stmt.executeUpdate("INSERT INTO staff_works_at (hotel_id, staff_id)" +
                           "VALUES (4, 7)");
        
        //Populate the RESERVATIONS table
        // no_of_guests start_date  end_date check_in_time check_out_time total_amount payment_method card_no expiry billing_address has_paid
        
        stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid)" +
                           "VALUES (1, '2017-05-10', '2017-05-13', '3:17:00', '10:22:00', 331.0, 'credit', 1052, NULL, '980 TRT St , Raleigh NC', 1)");
        
        stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid)" +
                           "VALUES (2, '2017-05-10', '2017-05-13', '4:11:00', '9:27:00', 615.0, 'credit', 3020, NULL, '7720 MHT St , Greensboro NC', 1)");
        
        stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid)" +
                           "VALUES (1, '2016-05-10', '2016-05-14', '3:45:00', '11:10:00', 410.0, 'credit', 2497, NULL, '231 DRY St , Rochester NY 78', 1)");
        
        stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid)" +
                           "VALUES (2, '2018-05-10', '2018-05-12', '2:30:00', '10:00:00', 0, 'cash', NULL, NULL, '24 BST Dr , Dallas TX 14', 0)");
        
        //Populate the RESERVATION_FOR table
        //hotel,room.
        stmt.executeUpdate("INSERT INTO reservation_for (reservation_id, room_no, hotel_id)" +
                           "VALUES (1, 1, 1)");
        stmt.executeUpdate("INSERT INTO reservation_for (reservation_id, room_no, hotel_id)" +
                           "VALUES (2, 2, 1)");
        stmt.executeUpdate("INSERT INTO reservation_for (reservation_id, room_no, hotel_id)" +
                           "VALUES (3, 3, 2)");
        stmt.executeUpdate("INSERT INTO reservation_for (reservation_id, room_no, hotel_id)" +
                           "VALUES (4, 2, 3)");
        
        //Populate the CUSTOMER_MAKES table
        stmt.executeUpdate("INSERT INTO customer_makes (reservation_id, customer_id)" +
                           "VALUES (1, 1)");
        stmt.executeUpdate("INSERT INTO customer_makes (reservation_id, customer_id)" +
                           "VALUES (2, 2)");
        stmt.executeUpdate("INSERT INTO customer_makes (reservation_id, customer_id)" +
                           "VALUES (3, 3)");
        stmt.executeUpdate("INSERT INTO customer_makes (reservation_id, customer_id)" +
                           "VALUES (4, 4)");
        
        //Populate the SERVICES table
        stmt.executeUpdate("INSERT INTO services (reservation_id, name, price)" +
                           "VALUES (1, 'gyms', 15)");
        stmt.executeUpdate("INSERT INTO services (reservation_id, name, price)" +
                           "VALUES (1, 'dry cleaning', 16)");
        stmt.executeUpdate("INSERT INTO services (reservation_id, name, price)" +
                           "VALUES (2, 'gyms', 15)");
        stmt.executeUpdate("INSERT INTO services (reservation_id, name, price)" +
                           "VALUES (3, 'room service', 10)");
        stmt.executeUpdate("INSERT INTO services (reservation_id, name, price)" +
                           "VALUES (4, 'phone bills', 5)");
        
        //Populate the STAFF_PROVIDES table
        //reservation - staffID - serviceID
        stmt.executeUpdate("INSERT INTO staff_provides (reservation_id, staff_id, service_id)" +
                           "VALUES (1, 4, 1)");
        stmt.executeUpdate("INSERT INTO staff_provides (reservation_id, staff_id, service_id)" +
                           "VALUES (1, 5, 2)");
        stmt.executeUpdate("INSERT INTO staff_provides (reservation_id, staff_id, service_id)" +
                           "VALUES (2, 4, 3)");
        stmt.executeUpdate("INSERT INTO staff_provides (reservation_id, staff_id, service_id)" +
                           "VALUES (3, 2, 4)");
        stmt.executeUpdate("INSERT INTO staff_provides (reservation_id, staff_id, service_id)" +
                           "VALUES (4, 3, 5)");
    }
}
