
import java.sql.*;
import java.util.*;

public class MgmtSys {
    
    public static void main(String[] args) {
        MgmtSys dbms = new MgmtSys();
        InitJDBC jdbc = new InitJDBC(dbms);
    }
    
    void addHotel(Statement stmt) throws Exception {
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
        sc.nextLine();
        System.out.println("Enter manager ID: ");
        mid = sc.nextInt();
        sc.nextLine();
        stmt.executeUpdate("INSERT INTO hotels (name, address, phone, manager_id) VALUES ("+ "\"" + name + "\",\"" + address + "\"," + phone + "," + mid + ")");
    }
    
    void deleteHotel(Statement stmt) throws Exception {
        int id;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Delete a Hotel\n\n");
        System.out.println("Enter ID: ");
        id = sc.nextInt();
        
        stmt.executeUpdate("DELETE FROM hotels where id ="+ id);
    }
    
    void updateHotel(Statement stmt) throws Exception {
        String name = "", address = "", temp, query;
        int phone = 0, id, mid = 0;
        Scanner sc = new Scanner(System.in);
        ResultSet rs = null;
        query ="UPDATE hotels SET ";
        
        System.out.println("Update a Hotel\nNOTE: Press Enter to skip a field\n");
        System.out.println("Enter hotel ID: ");
        id = sc.nextInt();
        sc.nextLine();
        rs = stmt.executeQuery("SELECT name, address, phone, manager_id FROM hotels WHERE id = " + id);
        
        while(rs.next()) {
            name = rs.getString("name");
            address = rs.getString("address");
            phone = rs.getInt("phone");
            mid = rs.getInt("manager_id");
        }
        
        System.out.println("Enter new name: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            name = temp;
        }
        query = query+" name = \"" + name + "\",";
        
        System.out.println("Enter new address: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            address = temp;
        }
        query = query + " address = \"" + address + "\",";
        
        System.out.println("Enter new phone: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            phone = Integer.valueOf(temp);
        }
        query = query + " phone = " + phone + ",";
        
        System.out.println("Enter new manager ID: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            mid = Integer.valueOf(temp);
        }
        query = query + " manager_id = " + mid;
        query = query + " WHERE id =" + id;
        stmt.executeUpdate(query);
    }
    
    void displayHotels(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hotel List\n");
        ResultSet rs = stmt.executeQuery("SELECT * FROM hotels");
        
        System.out.println("ID|Name|Address|Phone|Manager ID");
        while(rs.next()) {
            System.out.print(rs.getInt("id"));
            System.out.print("|" + rs.getString("name"));
            System.out.print("|" + rs.getString("address"));
            System.out.print("|" + rs.getInt("phone"));
            System.out.println("|" + rs.getInt("manager_id"));
        }
    }
    
    void addRoom(Statement stmt) throws Exception {
        String name, category;
        int no, hid, occ, availability;
        double price;
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Add a Room\n\n");
        System.out.println("Enter hotel ID: ");
        hid = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter room number: ");
        no = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter category (Economy, Deluxe, Executive, Presidential): ");
        category = sc.nextLine();
        System.out.println("Enter max occupancy: ");
        occ = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter price: ");
        price = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter availability (0/1): ");
        availability = sc.nextInt();
        
        stmt.executeUpdate("INSERT INTO rooms (no, hotel_id, price, is_available, max_occupancy, category) VALUES ("+ no + "," + hid + "," +
                           price + "," + availability + "," + occ + ",\"" + category + "\")");
    }
    
    void deleteRoom(Statement stmt) throws Exception {
        int no,hid;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Delete a Room\n\n");
        System.out.println("Enter hotel ID: ");
        hid = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Room no: ");
        no = sc.nextInt();
        stmt.executeUpdate("DELETE from rooms WHERE no = "+ no +" and hotel_id="+hid);
    }
    
    void updateRoom(Statement stmt) throws Exception {
        String name, category = "", temp, query;
        int no, hid, occ = 0, availability = 0;
        double price = 0.0;
        ResultSet rs = null;
        query ="UPDATE rooms SET ";
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Update a Room\n\n");
        System.out.println("Enter hotel ID: ");
        hid = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter room number: ");
        no = sc.nextInt();
        sc.nextLine();
        
        rs = stmt.executeQuery("SELECT category, price, is_available, max_occupancy FROM rooms WHERE no="+no+" AND hotel_id=" + hid);
        
        while(rs.next()) {
            category = rs.getString("category");
            price = rs.getDouble("price");
            availability = rs.getInt("is_available");
            occ = rs.getInt("max_occupancy");
        }
        System.out.println("Enter category: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            category = temp;
        }
        query = query + " category = \"" + category + "\",";
        
        System.out.println("Enter max occupancy: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            occ = Integer.valueOf(temp);
        }
        query = query + " max_occupancy = " + occ + ",";
        
        System.out.println("Enter price: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            price = Double.valueOf(temp);
        }
        query = query + " price = " + price + ",";
        
        System.out.println("Enter availability (0/1): ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            availability = Integer.valueOf(temp);
        }
        query = query + " is_available = " + availability;
        query = query + " WHERE no =" + no +" and hotel_id="+hid;
        stmt.executeUpdate(query);
    }
    
    void displayRooms(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Room List\n");
        ResultSet rs = stmt.executeQuery("SELECT * FROM rooms");
        
        System.out.println("No|Hotel|ID|Category|Max occpancy|Price|Available (0=No, 1=Yes)");
        while(rs.next()) {
            System.out.print(rs.getInt("no"));
            System.out.print("|" + rs.getInt("hotel_id"));
            System.out.print("|" + rs.getString("category"));
            System.out.print("|" + rs.getInt("max_occupancy"));
            System.out.print("|" + rs.getDouble("price"));
            System.out.println("|" + rs.getInt("is_available"));
        }
    }
    
    void addStaff(Statement stmt) throws Exception {
        String name, title, department, address;
        int age, phone, availability, hid, staff_id;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Add Staff\n\n");
        System.out.println("Enter hotel ID: ");
        hid = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter name: ");
        name = sc.nextLine();
        System.out.println("Enter age: ");
        age = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter title: ");
        title = sc.nextLine();
        System.out.println("Enter department: ");
        department = sc.nextLine();
        System.out.println("Enter phone: ");
        phone = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter address: ");
        address = sc.nextLine();
        System.out.println("Enter availability (0/1): ");
        availability = sc.nextInt();
        stmt.executeUpdate("INSERT INTO staff (name, age, title, department, phone, address, availability) VALUES (\"" + name + "\"," + age + ",\"" + title + "\",\"" + department + "\"," +         phone + ",\"" + address + "\"," + availability + ")", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        staff_id = rs.getInt(1);
        stmt.executeUpdate("INSERT INTO staff_works_at (hotel_id, staff_id) VALUES (" + hid + "," + staff_id + ")");
    }
    
    void deleteStaff(Statement stmt) throws Exception {
        int id;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Delete Staff\n\n");
        System.out.println("Enter ID: ");
        id = sc.nextInt();
        stmt.executeUpdate("delete from staff where id ="+ id);
    }
    
    void updateStaff(Statement stmt) throws Exception {
        String name = "", title = "", dept = "", address = "", query, temp;
        int id, age = 0, phone = 0, availability = 0;
        Scanner sc = new Scanner(System.in);
        ResultSet rs = null;
        System.out.println("Update Staff\n\n");
        System.out.println("Enter staff ID: ");
        id = sc.nextInt();
        sc.nextLine();
        rs = stmt.executeQuery("SELECT name, age, title, department, phone, address, availability FROM staff WHERE id = "+id);
        while(rs.next()) {
            name = rs.getString("name");
            age = rs.getInt("age");
            availability = rs.getInt("availability");
            title = rs.getString("title");
            dept = rs.getString("department");
            phone = rs.getInt("phone");
            address = rs.getString("address");
        }
        query ="UPDATE staff SET ";
        System.out.println("Enter new name: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            name = temp;
        }
        query = query + " name = \"" + name + "\",";
        System.out.println("Enter new age: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            age = Integer.valueOf(temp);
        }
        query = query + " age = " + age + ",";
        System.out.println("Enter new title: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            title = temp;
        }
        query = query + " title = \"" + title + "\",";
        System.out.println("Enter new department: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            dept = temp;
        }
        query = query + " department = \"" + dept + "\",";
        System.out.println("Enter new phone: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            phone = Integer.valueOf(temp);
        }
        query = query + " phone = " + phone + ",";
        System.out.println("Enter new address: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            address = temp;
        }
        query = query + " address = \"" + address + "\",";
        System.out.println("Enter new availability (0/1): ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            availability = Integer.valueOf(temp);
        }
        query = query + " availability = " + availability + " WHERE id = " +  id;
        stmt.executeUpdate(query);
    }
    
    void displayStaff(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Hotel ID: ");
        int hotel_id = sc.nextInt();
        System.out.println("Staff List\n");
        ResultSet rs = stmt.executeQuery("SELECT * FROM staff WHERE id IN (SELECT staff_id FROM staff_works_at WHERE hotel_id = " + hotel_id + ")");
        
        System.out.println("ID|Name|Age|Title|Department|Phone|Address|Available (0=No, 1=Yes)");
        while(rs.next()) {
            System.out.print(rs.getInt("id"));
            System.out.print("|" + rs.getString("name"));
            System.out.print("|" + rs.getInt("age"));
            System.out.print("|" + rs.getString("title"));
            System.out.print("|" + rs.getString("department"));
            System.out.print("|" + rs.getInt("phone"));
            System.out.print("|" + rs.getString("address"));
            System.out.println("|" + rs.getInt("availability"));
        }
    }
    
    void addCustomer(Statement stmt) throws Exception {
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
        sc.nextLine();
        System.out.println("Enter email: ");
        email = sc.nextLine();
        System.out.println("Enter SSN: ");
        ssn = sc.nextInt();
        
        stmt.executeUpdate("INSERT INTO customers (name, dob, phone, email, ssn) VALUES (\"" + name + "\",\"" + dob + "\"," + phone + ",\"" + email + "\"," + ssn + ")");
    }
    
    void deleteCustomer(Statement stmt) throws Exception {
        int id;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Delete Customer\n\n");
        System.out.println("Enter ID: ");
        id = sc.nextInt();
        stmt.executeUpdate("DELETE FROM customers where id ="+ id);
    }
    
    void updateCustomer(Statement stmt) throws Exception {
        String name = "", dob = "0000-00-00", email = "", temp, query;
        int id, phone = 0, ssn = 0;
        Scanner sc = new Scanner(System.in);
        ResultSet rs = null;
        query ="UPDATE customers SET ";
        
        System.out.println("Update Customer\n\n");
        System.out.println("Enter customer ID: ");
        id = sc.nextInt();
        sc.nextLine();
        
        rs = stmt.executeQuery("SELECT name, dob, phone, email, ssn FROM customers WHERE id = "+ id);
        while(rs.next()) {
            name = rs.getString("name");
            dob = rs.getString("dob");
            phone = rs.getInt("phone");
            email = rs.getString("email");
            ssn = rs.getInt("ssn");
        }
        
        System.out.println("Enter name: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            name = temp;
        }
        query = query + " name = \"" + name + "\",";
        
        System.out.println("Enter DOB (yyyy-mm-dd): ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            dob = temp;
        }
        query = query + " dob = \"" + dob + "\",";
        
        System.out.println("Enter phone: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            phone = Integer.valueOf(temp);
        }
        query = query + " phone = " +  phone + ",";
        
        System.out.println("Enter email: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            email = temp;
        }
        query = query + " email = \"" + email + "\",";
        
        System.out.println("Enter ssn: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            ssn = Integer.valueOf(temp);
        }
        query = query + " ssn = " + ssn;
        query = query + " WHERE id = " + id ;
        stmt.executeUpdate(query);
    }
    
    void displayCustomers(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Hotel ID: ");
        int hotel_id = sc.nextInt();
        System.out.println("Customer List\n");
        ResultSet rs = stmt.executeQuery("SELECT * FROM customers WHERE id IN (SELECT customer_id FROM customer_makes WHERE reservation_id IN (SELECT reservation_id FROM reservation_for WHERE hotel_id = " + hotel_id + "))");
        
        System.out.println("ID|Name|DOB|Phone|Email");
        while(rs.next()) {
            System.out.print(rs.getInt("id"));
            System.out.print("|" + rs.getString("name"));
            System.out.print("|" + rs.getString("dob"));
            System.out.print("|" + rs.getInt("phone"));
            System.out.println("|" + rs.getString("email"));
        }
    }
    
    void addService(Statement stmt) throws Exception {
        String name;
        int reservation_id, staff_id, service_id;
        double price;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Add Service\n\n");
        System.out.println("Enter reservation ID: ");
        reservation_id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter name: ");
        name = sc.nextLine();
        System.out.println("Enter price: ");
        price = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter staff ID: ");
        staff_id = sc.nextInt();
        
        stmt.executeUpdate("INSERT INTO services (reservation_id, name, price) VALUES (" + reservation_id + ",\"" + name + "\"," + price + ")", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        service_id = rs.getInt(1);
        stmt.executeUpdate("INSERT INTO staff_provides (reservation_id, staff_id, service_id) VALUES (" + reservation_id + "," + staff_id + "," + service_id + ")");
    }
    
    void updateService(Statement stmt, Connection conn) throws Exception {
        String name = "", temp, query;
        int reservation_id, staff_id = 0, service_id, old_staff_id = 0;
        double price = 0.0;
        Scanner sc = new Scanner(System.in);
        Boolean flag = false;
        ResultSet rs = null;
        System.out.println("Update Service\n\n");
        System.out.println("Enter service ID: ");
        service_id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter reservation ID: ");
        reservation_id = sc.nextInt();
        sc.nextLine();
        query ="UPDATE services SET ";
        rs = stmt.executeQuery("SELECT name,price FROM services WHERE id = " + service_id +" AND reservation_id =" + reservation_id);
        while(rs.next()) {
            name = rs.getString("name");
            price = rs.getDouble("price");
        }
        System.out.println("Enter name: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            name = temp;
        }
        query = query + " name = \"" + name + "\",";
        System.out.println("Enter price: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            price = Double.valueOf(temp);
        }
        query = query + " price = " + price;
        System.out.println("Enter staff ID: ");
        temp = sc.nextLine();
        if(temp.length() != 0){
            flag = true;
            staff_id = Integer.valueOf(temp);
            rs = stmt.executeQuery("SELECT staff_id from staff_provides WHERE service_id = " + service_id +" AND reservation_id =" + reservation_id);
            while(rs.next()) {
                old_staff_id = rs.getInt("staff_id");
            }
        }
        query = query + " WHERE id = " + service_id +" and reservation_id =" + reservation_id;
        conn.setAutoCommit(false);
        try {
            stmt.executeUpdate(query);
            if(flag){
                stmt.executeUpdate("UPDATE staff_provides SET staff_id = " + staff_id + " WHERE service_id = " + service_id + " AND reservation_id =" + reservation_id+" AND staff_id = " + old_staff_id );
            }
            conn.commit();
        }
        catch(Exception e) {
            conn.rollback();
        }
        finally {
            conn.setAutoCommit(true);
        }
    }
    
    void displayServices(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Reservation ID: ");
        int reservation_id = sc.nextInt();
        System.out.println("Services List\n");
        ResultSet rs = stmt.executeQuery("SELECT * FROM services WHERE reservation_id = " + reservation_id);
        
        while(rs.next())
            System.out.println("\n" + rs.getString("name") + "\tPrice: " + rs.getDouble("price"));
    }
    
    void assignRoom(Statement stmt, Connection conn) throws Exception {
        String category, dob, email, start_date, end_date, check_in_time, check_out_time, payment_method, expiry, billing_address;
        int hid, cid, phone, ssn, no_of_guests, reservation_id;
        long card_no;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Assign Room\n\n");
        System.out.println("Enter hotel ID: ");
        hid = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter customer ID: ");
        cid = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter room category: ");
        category = sc.nextLine();
        
        String query = "SELECT no FROM rooms WHERE category = '" + category + "' AND hotel_id = " + hid + " AND is_available = 1";
        rs = stmt.executeQuery(query);
        if(!rs.isBeforeFirst()) {
            System.out.println("No rooms available\n");
        } else {
            conn.setAutoCommit(false);
            try {
                rs.next();
                int no = rs.getInt("no");
                System.out.println("Room no: " + no + " is available\n\n");
                System.out.println("Enter no of guests: ");
                no_of_guests = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter start date: ");
                start_date = sc.nextLine();
                System.out.println("Enter end date: ");
                end_date = sc.nextLine();
                System.out.println("Enter check-in time: ");
                check_in_time = sc.nextLine();
                System.out.println("Enter check-out time: ");
                check_out_time = sc.nextLine();
                System.out.println("Enter payment method: ");
                payment_method = sc.nextLine();
                if(!payment_method.equals("cash")) {
                    System.out.println("Enter card no: ");
                    card_no = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Enter expiry: ");
                    expiry = sc.nextLine();
                    System.out.println("Enter billing address: ");
                    billing_address = sc.nextLine();
                    stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid) VALUES (" + no_of_guests + ",\"" + start_date + "\",\"" +  end_date + "\",\"" + check_in_time + "\",\"" + check_out_time + "\",0,\"" + payment_method + "\"," + card_no + ",\"" + expiry + "\",\"" + billing_address + "\",0)", Statement.RETURN_GENERATED_KEYS);
                } else {
                    stmt.executeUpdate("INSERT INTO reservations (no_of_guests, start_date,  end_date, check_in_time, check_out_time, total_amount, payment_method, card_no, expiry, billing_address, has_paid) VALUES (" + no_of_guests + ",\"" + start_date + "\",\"" +  end_date + "\",\"" + check_in_time + "\",\"" + check_out_time + "\",0,\"" + payment_method + "\""+",NULL, NULL, NULL, 0)", Statement.RETURN_GENERATED_KEYS);
                }
                rs = stmt.getGeneratedKeys();
                rs.next();
                reservation_id = rs.getInt(1);
                stmt.executeUpdate("INSERT INTO customer_makes(reservation_id, customer_id) VALUES (" + reservation_id + "," + cid + ")");
                stmt.executeUpdate("INSERT INTO reservation_for(reservation_id, hotel_id, room_no) VALUES(" + reservation_id + "," + hid + "," + no + ")");
                stmt.executeUpdate("UPDATE rooms SET is_available = 0 WHERE hotel_id =  " + hid + " AND no = " + no);
                System.out.println("Room reservation success!");
                conn.commit();
                //   conn.setAutoCommit(true);
            }
            catch(Exception e) {
                conn.rollback();
            }
            finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
    
    void generateBill(Statement stmt, Connection conn) throws Exception {
        int hotel_id, reservation_id, no;
        double room_price;
        Scanner sc = new Scanner(System.in);
        ResultSet rs = null;
        
        System.out.println("Generate Bill\n\n");
        System.out.println("Enter Hotel ID: ");
        hotel_id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Room no: ");
        no = sc.nextInt();
        sc.nextLine();
        
        conn.setAutoCommit(false);
        try {
            rs = stmt.executeQuery("SELECT reservation_id FROM reservation_for WHERE reservation_id IN (SELECT id FROM reservations WHERE has_paid = 0) AND room_no =" + no + " AND hotel_id = " + hotel_id);
            rs.next();
            reservation_id = rs.getInt("reservation_id");
            
            rs = stmt.executeQuery("SELECT price * (SELECT DATEDIFF(end_date,start_date) FROM reservations WHERE id = " + reservation_id + ") AS price FROM rooms WHERE no = " + no + " AND hotel_id = " + hotel_id);
            rs.next();
            room_price = rs.getDouble("price");
            
            stmt.executeUpdate("UPDATE reservations SET total_amount = (SELECT SUM(price) FROM services WHERE reservation_id = " + reservation_id + ") + " + room_price + " WHERE id = " + reservation_id);
            stmt.executeUpdate("UPDATE reservations SET total_amount = " +
                               "CASE WHEN payment_method = \"hotel credit\" THEN (total_amount * 0.95) ELSE (total_amount) END WHERE has_paid = 0");
            //Itemized receipt
            System.out.println("\n-----------RECEIPT-----------\n");
            System.out.println("Room price (for the whole stay): $" + room_price);
            rs = stmt.executeQuery("SELECT name, price FROM services WHERE reservation_id = " + reservation_id);
            while(rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                System.out.println("\n" + name + ": $"+ price);
            }
            //Total amount
            rs = stmt.executeQuery("SELECT total_amount FROM reservations WHERE id = " + reservation_id);
            rs.next();
            System.out.println("\nTOTAL AMOUNT: $" + rs.getDouble("total_amount"));
            stmt.executeUpdate("UPDATE rooms SET is_available = 1 WHERE hotel_id = " + hotel_id + " AND no = " + no);
            stmt.executeUpdate("UPDATE reservations SET has_paid = 1 WHERE has_paid = 0 AND id = " + reservation_id);
            System.out.println("\nCheck-out success!\n");
            conn.commit();
        }
        catch(Exception e) {
            conn.rollback();
        }
        finally {
            conn.setAutoCommit(true);
        }
    }
    
    void occHotel(Statement stmt) throws Exception {
        System.out.println("Report: Occupancy by Hotel\n\n");
        ResultSet rs = stmt.executeQuery("SELECT hotel_id, 100 - (100*SUM(is_available)/COUNT(is_available)) AS H_OCC FROM rooms GROUP BY hotel_id");
        
        while(rs.next()) {
            int id = rs.getInt("hotel_id");
            double occ = rs.getDouble("H_OCC");
            System.out.println("Occupancy of Hotel #" + id + ": "+ occ + "%\n");
        }
    }
    
    void occRoomType(Statement stmt) throws Exception {
        System.out.println("Report: Occupancy by Room Type\n\n");
        ResultSet rs = stmt.executeQuery("SELECT category, 100 - (100*SUM(is_available)/COUNT(is_available)) AS R_OCC FROM rooms GROUP BY category");
        
        while(rs.next()) {
            String category = rs.getString("category");
            double occ = rs.getDouble("R_OCC");
            System.out.println("Occupancy of Room Type '" + category + "': "+ occ + "%\n");
        }
    }
    
    void occDateRange(Statement stmt) throws Exception {
        System.out.println("Report: Occupancy by Date Range\n\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Start date: ");
        String start = sc.nextLine();
        System.out.println("Enter End date: ");
        String end = sc.nextLine();
        
        ResultSet rs = stmt.executeQuery("SELECT 100*COUNT(*)/(SELECT COUNT(*) FROM rooms) AS D_OCC FROM reservations WHERE ('" + start + "' BETWEEN start_date AND end_date) AND ('" + end + "' BETWEEN start_date AND end_date)");
        rs.next();
        System.out.println("Room occupancy: " + rs.getDouble("D_OCC") + "%\n");
    }
    
    void occCity(Statement stmt) throws Exception {
        System.out.println("Report: Occupancy by City Address\n\n");
        ResultSet rs = stmt.executeQuery("SELECT address AS city, 100 - (100*SUM(is_available)/COUNT(is_available)) AS C_OCC FROM rooms, hotels WHERE hotel_id = id GROUP BY address");
        
        while(rs.next()) {
            String city = rs.getString("city");
            double occ = rs.getDouble("C_OCC");
            System.out.println("Occupancy in " + city + ": " + occ + "%\n");
        }
    }
    
    void occTotal(Statement stmt) throws Exception {
        System.out.println("Report: Total occupancy\n\n");
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS TOTAL_OCC, 100*COUNT(*)/(SELECT COUNT(*) FROM rooms) AS PERC FROM reservation_for WHERE reservation_id IN (SELECT id FROM reservations WHERE has_paid = 0)");
        while(rs.next()) {
            int cnt = rs.getInt("TOTAL_OCC");
            double occ = rs.getDouble("PERC");
            System.out.println("No of rooms occupied: " + cnt);
            System.out.println("\n% of rooms occupied: " + occ + "%\n");
        }
    }
    
    void revenue(Statement stmt) throws Exception {
        System.out.println("Report: Total revenue by hotel for given date range\n\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Hotel ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Start date: ");
        String start = sc.nextLine();
        System.out.println("Enter End date: ");
        String end = sc.nextLine();
        
        ResultSet rs = stmt.executeQuery("SELECT SUM(total_amount) AS Revenue FROM reservations r INNER JOIN reservation_for rf ON r.id = rf.reservation_id WHERE end_date BETWEEN '" + start + "' AND '" + end + "' AND hotel_id =" + id);
        rs.next();
        int total = rs.getInt("Revenue");
        System.out.println("Total Revenue: $" + total);
    }
    
    void staffInfoByRole(Statement stmt) throws Exception {
        System.out.println("Report: Staff information by role\n\n");
        ResultSet rs = stmt.executeQuery("SELECT title, department, COUNT(*) AS cnt FROM staff GROUP BY title, department;");
        while(rs.next()) {
            String title = rs.getString("title");
            String dept = rs.getString("department");
            int cnt = rs.getInt("cnt");
            System.out.println(title + " (" + dept + ") count: " + cnt + "\n");
        }
    }
    
    void staffInfoByCust(Statement stmt) throws Exception {
        System.out.println("Report: Staff information by customer served\n\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Customer ID: ");
        int cid = sc.nextInt();
        
        ResultSet rs = stmt.executeQuery("SELECT id, name, title, department, address, phone, availability, age FROM staff INNER JOIN staff_provides s ON id = staff_id INNER JOIN customer_makes c ON s.reservation_id = c.reservation_id WHERE customer_id =" + cid);
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String title = rs.getString("title");
            String dept = rs.getString("department");
            String address = rs.getString("address");
            int phone = rs.getInt("phone");
            int availability = rs.getInt("availability");
            int age = rs.getInt("age");
            System.out.println("\nSTAFF ID #" + id);
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Title: " + title);
            System.out.println("Department: " + dept);
            System.out.println("Address: " + address);
            System.out.println("Phone: " + phone);
            System.out.println("Availability: " + availability);
        }
    }
}
