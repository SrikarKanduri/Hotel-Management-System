import java.sql.*;
import java.util.*;

public class Dbms {
    static int access_type;
    
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
                
                dropTables(stmt);
                createTables(stmt);
                populateDemoData(stmt);
                login();
                showOperations(stmt);
//                rs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES");
//
//                while (rs.next()) {
//                    String s = rs.getString("COF_NAME");
//                    float n = rs.getFloat("PRICE");
//                    System.out.println(s + "  " + n);
//                }
                
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
    
    static void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Login as:\n\n");
        System.out.println("1) CEO\n");
        System.out.println("2) Manager\n");
        System.out.println("3) Front Desk Representative\n");
        access_type = sc.nextInt();
        //check if access_type is correct
        //Validate login details?
    }
    
    static void showOperations(Statement stmt) throws Exception {
        switch(access_type) {
            case 1:
                ceoOperations(stmt);
                break;
            case 2:
                mgrOperations(stmt);
                break;
            case 3:
                fdrOperations(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");        }
    }
    
    static void ceoOperations(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
//        Runtime.getRuntime().exec("clear");
        
        System.out.println("CEO View\n\n");
        System.out.println("1) Manage hotels\n");
        System.out.println("2) Manage staff\n");
        System.out.println("3) View reports\n");
        int option = sc.nextInt();
        switch(option) {
            case 1:
                manageHotels(stmt);
                break;
            case 2:
                manageStaff(stmt);
                break;
            case 3:
                viewReports(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    static void mgrOperations(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
//        Runtime.getRuntime().exec("clear");
        
        System.out.println("Manager View\n\n");
        System.out.println("1) Manage hotels\n");
        System.out.println("2) Manage staff\n");
        System.out.println("3) View reports\n");
        int option = sc.nextInt();
        switch(option) {
            case 1:
                manageHotels(stmt);
                break;
            case 2:
                manageStaff(stmt);
                break;
            case 3:
                viewReports(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    static void fdrOperations(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
//        Runtime.getRuntime().exec("clear");
        
        System.out.println("Front Desk Representative View\n\n");
        System.out.println("1) Manage customers\n");
        System.out.println("2) Manage room assignment\n");
        System.out.println("3) Manage staff\n");
        System.out.println("4) Generate bill\n");
        int option = sc.nextInt();
        switch(option) {
            case 1:
                manageCustomers(stmt);
                break;
            case 2:
                assignRoom(stmt);
                break;
            case 3:
                manageStaff(stmt);
                break;
            case 4:
                generateBill(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    static void manageHotels(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
//        Runtime.getRuntime().exec("clear");

        System.out.println("Manage Hotels\n\n");
        System.out.println("1) Add a new hotel\n");
        System.out.println("2) Delete a hotel\n");
        System.out.println("3) Update hotel's info\n");
        System.out.println("4) Add a new room\n");
        System.out.println("5) Delete a room\n");
        System.out.println("6) Update room info\n");
        
        int option = sc.nextInt();
        switch(option) {
            case 1:
                addHotel(stmt);
                break;
            case 2:
                deleteHotel(stmt);
                break;
            case 3:
                updateHotel(stmt);
                break;
            case 4:
                addRoom(stmt);
                break;
            case 5:
                deleteRoom(stmt);
                break;
            case 6:
                updateRoom(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    static void manageStaff(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);

//        Runtime.getRuntime().exec("clear");
        
        System.out.println("Manage Staff\n\n");
        System.out.println("1) Add new staff\n");
        System.out.println("2) Delete staff\n");
        System.out.println("3) Update staff's info\n");
        int option = sc.nextInt();
        switch(option) {
            case 1:
                addStaff(stmt);
                break;
            case 2:
                deleteStaff(stmt);
                break;
            case 3:
                updateStaff(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    static void manageCustomers(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        //        Runtime.getRuntime().exec("clear");
        
        System.out.println("Manage Customers\n\n");
        System.out.println("1) Add customer\n");
        System.out.println("2) Delete customer\n");
        System.out.println("3) Update customer's info\n");
        int option = sc.nextInt();
        switch(option) {
            case 1:
                addCustomer(stmt);
                break;
            case 2:
                deleteCustomer(stmt);
                break;
            case 3:
                updateCustomer(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    static void viewReports(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
//        Runtime.getRuntime().exec("clear");
        
        System.out.println("View Reports\n\n");
        System.out.println("1) Occupancy by hotel\n");
        System.out.println("2) Occupancy by room type\n");
        System.out.println("3) Occupancy by date range\n");
        System.out.println("4) Occupancy by city\n");
        System.out.println("5) Total Hotel occupancy\n");
        System.out.println("6) Hotel revenue in a date range\n");
        System.out.println("7) Staff info by role\n");
        System.out.println("8) Staff info serving a customer\n");
        int option = sc.nextInt();
        switch(option) {
            case 1:
                occHotel(stmt);
                break;
            case 2:
                occRoomType(stmt);
                break;
            case 3:
                occDateRange(stmt);
                break;
            case 4:
                occCity(stmt);
                break;
            case 5:
                occTotal(stmt);
                break;
            case 6:
                revenue(stmt);
            case 7:
                staffInfoByRole(stmt);
                break;
            case 8:
                staffInfoByCust(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    static void addHotel(Statement stmt) throws Exception {
        String name, address;
        int phone, mid;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Add a Hotel\n\n");
        System.out.println("Enter name: ");
        name = sc.nextLine();
        System.out.println("Enter address: ");
        address = sc.nextLine();
        System.out.println("Enter phone: ");
        phone = sc.nextInt();
        System.out.println("Enter manager ID: ");
        mid = sc.nextInt();
        stmt.executeUpdate("INSERT INTO hotels (name, address, phone, manager_id) VALUES (" + name + "," + address + "," + phone + "," + manager_id + ")");
    }
    
    static void deleteHotel(Statement stmt) throws Exception {
        int id;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Delete a Hotel\n\n");
        System.out.println("Enter ID: ");
        id = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void updateHotel(Statement stmt) throws Exception {
        String name, address;
        int phone, id, mid;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Update a Hotel\n\n");
        System.out.println("Enter hotel ID: ");
        id = sc.nextInt();
        System.out.println("Enter new name: ");
        name = sc.nextLine();
        System.out.println("Enter new address: ");
        address = sc.nextLine();
        System.out.println("Enter new phone: ");
        phone = sc.nextInt();
        System.out.println("Enter new manager ID: ");
        mid = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void addRoom(Statement stmt) throws Exception {
        String name, category;
        int no, hid, occ, availability;
        double price;
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Add a Room\n\n");
        System.out.println("Enter hotel ID: ");
        hid = sc.nextInt();
        System.out.println("Enter room number: ");
        no = sc.nextInt();
        System.out.println("Enter category: ");
        category = sc.nextLine();
        System.out.println("Enter max occupancy: ");
        occ = sc.nextInt();
        System.out.println("Enter price: ");
        price = sc.nextDouble();
        System.out.println("Enter availability (0/1): ");
        availability = sc.nextInt();

    }
    
    static void deleteRoom(Statement stmt) throws Exception {
        int id;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Delete a Room\n\n");
        System.out.println("Enter ID: ");
        id = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void updateRoom(Statement stmt) throws Exception {
        String name, category;
        int no, hid, occ, availability;
        double price;
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Update a Room\n\n");
        System.out.println("Enter hotel ID: ");
        hid = sc.nextInt();
        System.out.println("Enter room number: ");
        no = sc.nextInt();
        System.out.println("Enter new category: ");
        category = sc.nextLine();
        System.out.println("Enter new max occupancy: ");
        occ = sc.nextInt();
        System.out.println("Enter new price: ");
        price = sc.nextDouble();
        System.out.println("Enter new availability (0/1): ");
        availability = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void addStaff(Statement stmt) throws Exception {
        String name, title, dept, address;
        int age, phone, availability;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Add Staff\n\n");
        System.out.println("Enter name: ");
        name = sc.nextLine();
        System.out.println("Enter age: ");
        age = sc.nextInt();
        System.out.println("Enter title: ");
        title = sc.nextLine();
        System.out.println("Enter department: ");
        dept = sc.nextLine();
        System.out.println("Enter phone: ");
        phone = sc.nextInt();
        System.out.println("Enter address: ");
        address = sc.nextLine();
        System.out.println("Enter availability (0/1): ");
        availability = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void deleteStaff(Statement stmt) throws Exception {
        int id;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Delete Staff\n\n");
        System.out.println("Enter ID: ");
        id = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void updateStaff(Statement stmt) throws Exception {
        String name, title, dept, address;
        int id, age, phone, availability;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Update Staff\n\n");
        System.out.println("Enter staff ID: ");
        id = sc.nextInt();
        System.out.println("Enter new name: ");
        name = sc.nextLine();
        System.out.println("Enter new age: ");
        age = sc.nextInt();
        System.out.println("Enter new title: ");
        title = sc.nextLine();
        System.out.println("Enter new department: ");
        dept = sc.nextLine();
        System.out.println("Enter new phone: ");
        phone = sc.nextInt();
        System.out.println("Enter new address: ");
        address = sc.nextLine();
        System.out.println("Enter new availability (0/1): ");
        availability = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void addCustomer(Statement stmt) throws Exception {
        String name, dob, email;
        int phone, ssn;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Add Customer\n\n");
        System.out.println("Enter name: ");
        name = sc.nextLine();
        System.out.println("Enter dob: ");
        dob = sc.nextLine();
        System.out.println("Enter phone: ");
        phone = sc.nextInt();
        System.out.println("Enter email: ");
        email = sc.nextLine();
        System.out.println("Enter ssn: ");
        ssn = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void deleteCustomer(Statement stmt) throws Exception {
        int id;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Delete Customer\n\n");
        System.out.println("Enter ID: ");
        id = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void updateCustomer(Statement stmt) throws Exception {
        String name, dob, email;
        int id, phone, ssn;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Update Customer\n\n");
        System.out.println("Enter customer ID: ");
        id = sc.nextInt();
        System.out.println("Enter name: ");
        name = sc.nextLine();
        System.out.println("Enter dob: ");
        dob = sc.nextLine();
        System.out.println("Enter phone: ");
        phone = sc.nextInt();
        System.out.println("Enter email: ");
        email = sc.nextLine();
        System.out.println("Enter ssn: ");
        ssn = sc.nextInt();
        
        //        stmt.executeUpdate("INSERT INTO hotels)
    }
    
    static void assignRoom(Statement stmt) throws Exception {
        String category, dob, email, sdate, edate, check_in_time, check_out_time, payment_method, card_expiry, billing_address;
        int hid, cid, phone, ssn, no_of_guests, reservation_id;
        long card_no;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Assign Room\n\n");
        System.out.println("Enter hotel ID: ");
        hid = sc.nextInt();
        System.out.println("Enter customer ID: ");
        cid = sc.nextInt();
        System.out.println("Enter room category: ");
        category = sc.nextLine();
        
        String query = "SELECT no FROM rooms WHERE category = " + category + " AND hotel_id = " + hid + " AND is_available = 1";
        rs = stmt.executeQuery(query);
        if(!rs.isBeforeFirst()) {
            System.out.println("No rooms available\n");
        } else {
            rs.next();
            int no = rs.getInt(no);
            System.out.println("Room no: " + no + " is available\n\n");
            System.out.println("Enter no of guests: ");
            no_of_guests = sc.nextInt();
            System.out.println("Enter start date: ");
            sdate = sc.nextLine();
            System.out.println("Enter end date: ");
            edate = sc.nextLine();
            System.out.println("Enter check-in time: ");
            check_in_time = sc.nextLine();
            System.out.println("Enter check-out time: ");
            check_out_time = sc.nextLine();
            System.out.println("Enter payment method: ");
            payment_method = sc.nextLine();
            if(!payment_method.equals("cash")) {
                System.out.println("Enter card no: ");
                card_no = sc.nextLong();
                System.out.println("Enter expiry: ");
                card_expiry = sc.nextLine();
                System.out.println("Enter billing address: ");
                billing_address = sc.nextLine();
                reservation_id = stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid) VALUES (" + no_of_guests + "," + start_date + "," +  end_date + "," + check_in_time + "," + check_out_time + ",0," + payment_method + "," + card_no + "," + expiry + "," + billing_address + ",0)", Statement.RETURN_GENERATED_KEYS);
            } else {
                stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid) VALUES (" + no_of_guests + "," + start_date + "," +  end_date + "," + check_in_time + "," + check_out_time + ",0," + payment_method + ",NULL, NULL, NULL, 0)", Statement.RETURN_GENERATED_KEYS);
            }
        stmt.executeUpdate("INSERT INTO customer_makes(reservation_id, customer_id) VALUES (" + reservation_id + "," + cid + ")");
        stmt.executeUpdate("INSERT INTO reservation_for(reservation_id, hotel_id, room_no) VALUES(" + reservation_id + "," + hid + "," + no + ")");
        stmt.executeUpdate("UPDATE rooms SET is_available = 0 WHERE hotel_id = " + hid + "AND no =" + no + ")");
        System.out.println("Room reservation success!");
        }
    }
    
    static void generateBill(Statement stmt) throws Exception {
        int id, no;
        Scanner sc = new Scanner(System.in);
        ResultSet rs = null;
        
        System.out.println("Generate Bill\n\n");
        System.out.println("Enter hotel ID: ");
        id = sc.nextInt();
        System.out.println("Enter room no: ");
        no = sc.nextInt();
        
        stmt.executeUpdate("UPDATE reservations SET total_amount = (SELECT SUM(price) FROM services WHERE reservation_id IN (SELECT reservation_id FROM reservation_for WHERE room_no = " + no + " AND hotel_id = " + hotel_id + ")) + (SELECT price FROM rooms WHERE no = " + no + " AND hotel_id = " + hotel_id + ") WHERE id IN (SELECT reservation_id FROM reservation_for WHERE room_no = " + no + " AND hotel_id = " + hotel_id + ")");
        stmt.executeUpdate("UPDATE reservations SET total_amount = " + "CASE WHEN payment_method = ‘hotel card’ THEN (total_amount * 0.95) END WHERE has_paid = 0");
        //print services here
        rs = stmt.executeQuery("SELECT total_amount FROM reservations WHERE id IN (SELECT reservation_id FROM reservation_for WHERE room_no =" + no + "AND hotel_id = " + id +")");
        rs.next();
        System.out.println("\nTotal: " + rs.getDouble("total_amount"));
    }

    static void occHotel(Statement stmt) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT hotel_id, 100 – (100*SUM(is_available)/COUNT(is_available)) AS H_OCC FROM rooms GROUP BY hotel_id");
        
        while(rs.next()) {
            int id = rs.getInt("hotel_id");
            double occ = rs.getDouble("H_OCC");
            System.out.println("Occupancy of Hotel #" + id + ": "+ occ + "%\n");
        }
    }
    
    static void occRoomType(Statement stmt) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT category, 100 – (100*SUM(is_available)/COUNT(is_available)) AS R_OCC FROM rooms GROUP BY category");
        
        while(rs.next()) {
            String category = rs.getInt("category");
            double occ = rs.getDouble("R_OCC");
            System.out.println("Occupancy of Room Type '" + category + "': "+ occ + "%\n");
        }
    }

    static void occDateRange(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Start date: ");
        String start = sc.nextLine();
        System.out.println("Enter End date: ");
        String end = sc.nextLine();
        
        ResultSet rs = stmt.executeQuery("SELECT 100*COUNT(*)/(SELECT COUNT(*) FROM rooms) AS D_OCC FROM reservations WHERE (‘" + start + "’ BETWEEN start_date AND end_date) AND (‘" + end + "’ BETWEEN start_date AND end_date)");
        rs.next();
        System.out.println("Room occupancy: " + rs.getDouble("H_OCC") + "%\n");
    }

    static void occCity(Statement stmt) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT address AS city, 100 – (100*SUM(is_available)/COUNT(is_available)) AS C_OCC FROM rooms, hotels WHERE hotel_id = id GROUP BY address");
        
        while(rs.next()) {
            String city = rs.getString("city");
            double occ = rs.getDouble("C_OCC");
            System.out.println("Occupancy in " + city + ": " + occ + "%\n");
        }
    }

    static void occTotal(Statement stmt) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS TOTAL_OCC, 100*COUNT(*)/(SELECT COUNT(*) FROM rooms) AS PERC FROM reservation_for");
        while(rs.next()) {
            int cnt = rs.getInt("TOTAL_OCC");
            double occ = rs.getDouble("PERC");
            System.out.println("No of rooms occupied: " + cnt);
            System.out.println("\n% of rooms occupied: " + occ + "%\n");
        }
    }

    static void revenue(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Hotel ID: ");
        int id = sc.nextInt();
        System.out.println("Enter Start date: ");
        String start = sc.nextLine();
        System.out.println("Enter End date: ");
        String end = sc.nextLine();
        
        ResultSet rs = stmt.executeQuery("SELECT SUM(total_amount) AS Revenue FROM reservations r INNER JOIN reservation_for rf ON r.id = rf.reservation_id WHERE end_date BETWEEN ‘" + start + "’ AND ‘" + end + "’ AND hotel_id =" + id +")");
        rs.next();
        int total = rs.getInt("Revenue");
        System.out.println("Total Revenue: $" + total);
    }

    static void staffInfoByRole(Statement stmt) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT title, department, COUNT(*) AS cnt FROM staff GROUP BY title, department;");
        while(rs.next()) {
            String title = rs.getString("title");
            String dept = rs.getString("department");
            int cnt = rs.getInt("cnt");
            System.out.println(title + " (" + dept + ") count: " + cnt + "\n");
        }
    }

    static void staffInfoByCust(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Customer ID: ");
        int id = sc.nextInt();
        
        ResultSet rs = stmt.executeQuery("SELECT id, name, title, department, address, phone, availability, age FROM staff INNER JOIN staff_provides s ON id = staff_id INNER JOIN customer_makes c ON s.reservation_id = c.reservation_id WHERE customer_id =" + id + ")");
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String title = rs.getString("title");
            String dept = rs.getString("department");
            String address = rs.getString("address");
            int phone = rs.getInt("phone");
            int availability = rs.getInt("availability");
            int age = rs.getInt("age");
            System.out.println("STAFF ID #" + id);
            System.out.println("\n\tName: " + name);
            System.out.println("\n\tAge: " + age);
            System.out.println("\n\tTitle: " + title);
            System.out.println("\n\tDepartment: " + dept);
            System.out.println("\n\tAddress: " + address);
            System.out.println("\n\tPhone: " + phone);
            System.out.println("\n\tAvailability: " + availability);
        }
    }

    static void dropTables(Statement stmt) throws Exception{
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
    
    static void createTables(Statement stmt) throws Exception{
        // Create the CUSTOMERS table
        stmt.executeUpdate("CREATE TABLE customers (" +
                           "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "dob DATE NOT NULL, " +
                           "phone INT NOT NULL, " +
                           "email VARCHAR(128) NOT NULL, " +
                           "ssn INT NOT NULL UNIQUE)");
        
        // Create the STAFF table
        stmt.executeUpdate("CREATE TABLE staff ( " +
                           "id INT  PRIMARY KEY AUTO_INCREMENT, " +
                           "name VARCHAR(128) NOT NULL, " +
                           "age INT, " +
                           "title VARCHAR(128) NOT NULL, " +
                           "department VARCHAR(128) NOT NULL, " +
                           "phone INT NOT NULL, " +
                           "address VARCHAR(128) NOT NULL, " +
                           "availability TINYINT(1) NOT NULL)");
        
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
        
        // Create the ROOMS table
        stmt.executeUpdate("CREATE TABLE rooms (" +
                           "no INT NOT NULL, " +
                           "hotel_id INT NOT NULL, " +
                           "category VARCHAR(128) NOT NULL, " +
                           "max_occupancy INT NOT NULL, " +
                           "price INT NOT NULL, " +
                           "is_available TINYINT(1) NOT NULL, " +
                           "CONSTRAINT hotel_room_fk  " +
                           "FOREIGN KEY(hotel_id) REFERENCES hotels(id)  " +
                           "ON DELETE CASCADE ON UPDATE CASCADE, " +
                           "PRIMARY KEY(no, hotel_id))");
        
        // Create the RESERVATIONS table
        stmt.executeUpdate("CREATE TABLE reservations ( " +
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
    
    static void populateDemoData(Statement stmt) throws Exception{
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
                           "VALUES ('Mary', 40, 'Manager',  'Management', 654, '90 ABC St , Raleigh NC 27', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('John', 45, 'Manager',  'Management', 564, '798 XYZ St , Rochester NY 54', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability) " +
                           "VALUES ('Carol', 55, 'Manager',  'Management', 564, '351 MH St , Greensboro NC 27', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('Emma', 55, 'Front Desk Staff',  'Management', 546, '49 ABC St , Raleigh NC 27', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('Ava', 55, 'Catering Staff',  'Catering', 777, '425 RG St , Raleigh NC 27', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('Peter', 52, 'Manager',  'Management', 724, '475 RG St , Raleigh NC 27', 1)");
        
        stmt.executeUpdate("INSERT INTO staff (name,age,title,department,phone,address,availability)" +
                           "VALUES ('Olivia', 27, 'Front Desk Staff',  'Management', 799, '325 PD St , Raleigh NC 27', 1)");
        
        // Populate the HOTELS table
        stmt.executeUpdate("INSERT INTO hotels (name,address,phone,manager_id)" +
                           "VALUES ('Hotel A', '21 ABC St , Raleigh NC 27', 919, 1)");
        stmt.executeUpdate("INSERT INTO hotels (name,address,phone,manager_id)" +
                           "VALUES ('Hotel B', '25 XYZ St , Rochester NY 54', 718, 2)");
        stmt.executeUpdate("INSERT INTO hotels (name,address,phone,manager_id)" +
                           "VALUES ('Hotel C', '29 PQR St , Greensboro NC 27', 984, 3)");
        stmt.executeUpdate("INSERT INTO hotels (name,address,phone,manager_id)" +
                           "VALUES ('Hotel D', '28 GHW St , Raleigh NC 32', 920, 6)");
        
        //Populate the ROOMS table
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 1, 1, 'Economy', 1, 100, 1)");
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 2, 1, 'Deluxe', 2, 200, 1)");
        stmt.executeUpdate("INSERT INTO rooms (no,hotel_id, category, max_occupancy, price, is_available)" +
                           "VALUES ( 3, 2, 'Economy', 1, 100, 1)");
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
                           "VALUES (1, '2017-05-10', '2017-05-13', '3:17:00', '10:22:00', 0, 'credit', 1052, NULL, '980 TRT St , Raleigh NC', 0)");
        
        stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid)" +
                           "VALUES (2, '2017-05-10', '2017-05-13', '4:11:00', '9:27:00', 0, 'credit', 3020, NULL, '7720 MHT St , Greensboro NC', 0)");
        
        stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid)" +
                           "VALUES (1, '2016-05-10', '2016-05-14', '3:45:00', '11:10:00', 0, 'credit', 2497, NULL, '231 DRY St , Rochester NY 78', 0)");
        
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
