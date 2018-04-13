import java.sql.*;

public class Dbms {
    static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/skandur";
    
    public static void main(String[] args) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
            String user = "skandur";
            String passwd = "200204421";
            
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            try {
                conn = DriverManager.getConnection(jdbcURL, user, passwd);
                stmt = conn.createStatement();
                
                // stmt.executeUpdate("DROP TABLE COFFEES ");
                
                createTables(stmt);
                populateDemoData(stmt);
                
                // Get data from the COFFEES table
                
                rs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES");
                
                // Now rs contains the rows of coffees and prices from
                // the COFFEES table. To access the data, use the method
                // NEXT to access all rows in rs, one row at a time
                
                while (rs.next()) {
                    String s = rs.getString("COF_NAME");
                    float n = rs.getFloat("PRICE");
                    System.out.println(s + "  " + n);
                }
                
            } finally {
                close(rs);
                close(stmt);
                close(conn);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }
    
    static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }
    
    static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }
    
    static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
    }
    
    static createTables(Statement stmt) {
        // Create the CUSTOMERS table
        stmt.executeUpdate("CREATE TABLE customers (" +
                           "id INT PRIMARY KEY AUTO_INCREMENT, " +
                           "ssn INT NOT NULL UNIQUE, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "dob DATE NOT NULL, " +
                           "phone INT NOT NULL, " +
                           "email VARCHAR(128) NOT NULL)");
        
        // Create the HOTELS table
        stmt.executeUpdate("CREATE TABLE hotels (" +
                           "id INT PRIMARY KEY AUTO_INCREMENT, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "address VARCHAR(128) NOT NULL, " +
                           "phone INT NOT NULL, " +
                           "manager_id INT, " +
                           "CONSTRAINT manager_hotel_fk " +
                           "FOREIGN KEY(manager_id) REFERENCES staff(id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE)");
        
        // Create the STAFF table
        stmt.executeUpdate("CREATE TABLE staff ( " +
                           "id INT PRIMARY KEY AUTO_INCREMENT, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "age INT, " +
                           "title VARCHAR(128) NOT NULL, " +
                           "department VARCHAR(128) NOT NULL, " +
                           "phone INT NOT NULL, " +
                           "address VARCHAR(128) NOT NULL, " +
                           "availability TINYINT(1) NOT NULL)");
        
        // Create the ROOMS table
        stmt.executeUpdate("CREATE TABLE rooms ( " +
                           "no INT NOT NULL, " +
                           "hotel_id INT NOT NULL, " +
                           "category VARCHAR(128) NOT NULL, " +
                           "max_occupancy INT NOT NULL, " +
                           "price INT NOT NULL, " +
                           "is_available TINYINT(1) NOT NULL, " +
                           "CONSTRAINT hotel_room_fk  " +
                           "FOREIGN KEY(hotel_id) REFERENCES hotels(id)  " +
                           "ON DELETE CASCADE ON UPDATE CASCADE, " +
                           "PRIMARY KEY(no, hotel_id)");
        
        // Create the RESERVATIONS table
        stmt.executeUpdate("CREATE TABLE reservations ( " +
                           "id INT PRIMARY KEY AUTO_INCREMENT, " +
                           "start_date DATE NOT NULL, " +
                           "no_of_guests INT, " +
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
        stmt.executeUpdate("CREATE TABLE services ( " +
                           "id INT AUTO_INCREMENT NOT NULL, " +
                           "reservation_id INT NOT NULL, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "price DECIMAL(10,2) NOT NULL, " +
                           "CONSTRAINT reservation_service_fk " +
                           "FOREIGN KEY(reservation_id) REFERENCES reservations(id) " +
                           "ON DELETE CASCADE ON UPDATE CASCADE, " +
                           "PRIMARY KEY(id, reservation_id))");
        
        // Create the STAFF_WORKS_AT table
        stmt.executeUpdate("CREATE TABLE staff_works_at ( " +
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
        stmt.executeUpdate("CREATE TABLE customer_makes ( " +
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
        stmt.executeUpdate("CREATE TABLE reservation_for ( " +
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
        stmt.executeUpdate("CREATE TABLE staff_provides ( " +
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
    
        static populateDemoData(Statement stmt) {
            // Populate the HOTELS table
            stmt.executeUpdate("INSERT INTO hotels " +
                               "VALUES ('Hotel A', '21 ABC St , Raleigh NC 27', 919, 100)");
            stmt.executeUpdate("INSERT INTO hotels " +
                               "VALUES ('Hotel B', '25 XYZ St , Rochester NY 54', 718, 101)");
            stmt.executeUpdate("INSERT INTO hotels " +
                               "VALUES ('Hotel C', '29 PQR St , Greensboro NC 27', 984, 102)");
            stmt.executeUpdate("INSERT INTO hotels " +
                               "VALUES ('Hotel D', '28 GHW St , Raleigh NC 32', 920, 105)");
            
            //Populate the ROOMS table
            stmt.executeUpdate("INSERT INTO rooms " +
                               "VALUES ( 1, 1, 'Economy', 1, 100, 'Yes')");
            stmt.executeUpdate("INSERT INTO rooms " +
                               "VALUES ( 2, 1, 'Deluxe', 2, 200, 'Yes')");
            stmt.executeUpdate("INSERT INTO rooms " +
                               "VALUES ( 3, 2, 'Economy', 1, 100, 'Yes')");
            stmt.executeUpdate("INSERT INTO rooms " +
                               "VALUES ( 2, 3, 'Executive', 3, 1000, 'No')");
            stmt.executeUpdate("INSERT INTO rooms " +
                               "VALUES ( 1, 4, 'Presidential', 4, 5000, 'Yes')");
            stmt.executeUpdate("INSERT INTO rooms " +
                               "VALUES ( 5, 1, 'Deluxe', 2, 200, 'Yes')");
            
            //Populate the CUSTOMERS table
            stmt.executeUpdate("INSERT INTO customers " +
                               "VALUES ('David', '01/30/1980', 123, 'david@gmail.com', 5939846)");
            
            stmt.executeUpdate("INSERT INTO customers " +
                               "VALUES ('Sarah', '01/30/1971', 456, 'sarah@gmail.com', 7778352)");

            stmt.executeUpdate("INSERT INTO customers " +
                               "VALUES ('Joseph', '01/30/1987', 789, 'joseph@gmail.com', 8589430)");

            stmt.executeUpdate("INSERT INTO customers " +
                               "VALUES ('Lucy', '01/30/1985', 213, 'lucy@gmail.com', 4409328)");

            //Populate the STAFF table
            stmt.executeUpdate("INSERT INTO staff " +
                               "VALUES ('Mary', 40, 'Manager',  'Management', 654, '90 ABC St , Raleigh NC 27', 'Yes')");
            
            stmt.executeUpdate("INSERT INTO staff " +
                               "VALUES ('John', 45, 'Manager',  'Management', 564, '798 XYZ St , Rochester NY 54', 'Yes')");
            
            stmt.executeUpdate("INSERT INTO staff " +
                               "VALUES ('Carol', 55, 'Manager',  'Management', 564, '351 MH St , Greensboro NC 27', 'Yes')");

            stmt.executeUpdate("INSERT INTO staff " +
                               "VALUES ('Emma', 55, 'Front Desk Staff',  'Management', 546, '49 ABC St , Raleigh NC 27', 'Yes')");

            stmt.executeUpdate("INSERT INTO staff " +
                               "VALUES ('Ava', 55, 'Catering Staff',  'Catering', 777, '425 RG St , Raleigh NC 27', 'Yes')");

            stmt.executeUpdate("INSERT INTO staff " +
                               "VALUES ('Peter', 52, 'Manager',  'Management', 724, '475 RG St , Raleigh NC 27', 'Yes')");

            stmt.executeUpdate("INSERT INTO staff " +
                               "VALUES ('Olivia', 27, 'Front Desk Staff',  'Management', 799, '325 PD St , Raleigh NC 27', 'Yes')");

            //Populate the STAFF_WORKS_AT table
            stmt.executeUpdate("INSERT INTO staff_works_at " +
                               "VALUES (1, 1)");
            
            stmt.executeUpdate("INSERT INTO staff_works_at " +
                               "VALUES (2, 2)");
            
            stmt.executeUpdate("INSERT INTO staff_works_at " +
                               "VALUES (3, 3)");

            stmt.executeUpdate("INSERT INTO staff_works_at " +
                               "VALUES (4, 1)");

            stmt.executeUpdate("INSERT INTO staff_works_at " +
                               "VALUES (5, 1)");

            stmt.executeUpdate("INSERT INTO staff_works_at " +
                               "VALUES (6, 4)");

            stmt.executeUpdate("INSERT INTO staff_works_at " +
                               "VALUES (7, 4)");

            //Populate the RESERVATIONS table
            // no_of_guests start_date  end_date check_in_time check_out_time total_amount payment_method card_no expiry billing_address has_paid 

            stmt.executeUpdate("INSERT INTO reservations " +
                               "VALUES (1, '10/05/2017', '13/05/2017', '3:17 pm', '10:22 am', 0, 'credit', 1052, NULL, '980 TRT St , Raleigh NC', 'No')");

            stmt.executeUpdate("INSERT INTO reservations " +
                               "VALUES (2, '10/05/2017', '13/05/2017', '4:11 pm', '9:27 am', 0, 'credit', 3020, NULL, '7720 MHT St , Greensboro NC', 'No')");

            stmt.executeUpdate("INSERT INTO reservations " +
                               "VALUES (1, '10/05/2016', '14/05/2016', 3:45 pm', '11:10 am', 0, 'credit', 2497, NULL, '231 DRY St , Rochester NY 78', 'No')");

            stmt.executeUpdate("INSERT INTO reservations " +
                               "VALUES (2, '10/05/2018', '12/05/2018', 2:30 pm', '10:00 am', 0, 'cash', NULL, NULL, '24 BST Dr , Dallas TX 14', 'No')");
            
            //Populate the RESERVATION_FOR table
            //hotel,room.
            stmt.executeUpdate("INSERT INTO reservation_for " +
                               "VALUES (1, 1, 1)");
            stmt.executeUpdate("INSERT INTO reservation_for " +
                               "VALUES (2, 1, 2)");
            stmt.executeUpdate("INSERT INTO reservation_for " +
                               "VALUES (3, 2, 3)");
            stmt.executeUpdate("INSERT INTO reservation_for " +
                               "VALUES (4, 3, 2)");

            //Populate the CUSTOMER_MAKES table
            stmt.executeUpdate("INSERT INTO customer_makes " +
                               "VALUES (1, 1)");
            stmt.executeUpdate("INSERT INTO customer_makes " +
                               "VALUES (2, 2)");
            stmt.executeUpdate("INSERT INTO customer_makes " +
                               "VALUES (3, 3)");
            stmt.executeUpdate("INSERT INTO customer_makes " +
                               "VALUES (4, 4)");
            
            //Populate the SERVICES table
            stmt.executeUpdate("INSERT INTO services " +
                               "VALUES (1, 'gyms', 15)");
            stmt.executeUpdate("INSERT INTO services " +
                               "VALUES (1, 'dry cleaning', 16)");
            stmt.executeUpdate("INSERT INTO services " +
                               "VALUES (2, 'gyms', 15)");
            stmt.executeUpdate("INSERT INTO services " +
                               "VALUES (3, 'room service', 10)");
            stmt.executeUpdate("INSERT INTO services " +
                               "VALUES (4, 'phone bills', 5)");

            //Populate the STAFF_PROVIDES table
            //reservation - staffID - serviceID
            stmt.executeUpdate("INSERT INTO staff_provides " +
                               "VALUES (1, 4, 1)");
            stmt.executeUpdate("INSERT INTO staff_provides " +
                               "VALUES (1, 5, 2)");
            stmt.executeUpdate("INSERT INTO staff_provides " +
                               "VALUES (2, 4, 3)");
            stmt.executeUpdate("INSERT INTO staff_provides " +
                               "VALUES (3, 2, 4)");
            stmt.executeUpdate("INSERT INTO staff_provides " +
                               "VALUES (4, 3, 5)");
        }
}
