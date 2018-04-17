
import java.sql.*;
import java.util.*;

public class Menu {
    MgmtSys dbms;
    InitJDBC jdbc;
    
    Menu(MgmtSys dbms, InitJDBC jdbc) {
        this.dbms = dbms;
        this.jdbc = jdbc;
    }
    
    /* login() - Initially displayed screen to login */
    int login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        
        System.out.println("Login as:\n\n");
        System.out.println("1) CEO\n");
        System.out.println("2) Manager\n");
        System.out.println("3) Front Desk Representative\n");
        int access_type = sc.nextInt();
        return access_type;
    }
    
    /* showOperations() - Displays operations menu based on access type */
    void showOperations(int access_type, Statement stmt, Connection conn) throws Exception {
        
        switch(access_type) {
            case 1:
                ceoOperations(stmt);
                break;
            case 2:
                mgrOperations(stmt);
                break;
            case 3:
                fdrOperations(stmt, conn);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
                jdbc.logout = true;
        }
    }
    
    /* ceoOperations() - Displays CEO's operations */
    void ceoOperations(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n\n---------------CEO View---------------\n");
        System.out.println("1) Manage hotels\n");
        System.out.println("2) Manage staff\n");
        System.out.println("3) View reports\n");
        System.out.println("Any other number to logout\n");
        int option = sc.nextInt();
        System.out.print("\033[H\033[2J");
        
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
                System.out.println("Logging out...\n");
                jdbc.logout = true;
        }
    }
    
    /* mgrOperations() - Displays Manager's operations */
    void mgrOperations(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n---------------Manager View---------------\n\n");
        System.out.println("1) Manage rooms\n");
        System.out.println("2) Manage staff\n");
        System.out.println("3) View Customer list\n");
        System.out.println("4) View Reservations list\n");
        System.out.println("4) View reports\n");
        System.out.println("Any other number to logout\n");
        int option = sc.nextInt();
        System.out.print("\033[H\033[2J");
        
        switch(option) {
            case 1:
                manageRooms(stmt);
                break;
            case 2:
                manageStaff(stmt);
                break;
            case 3:
                dbms.displayCustomers(stmt);
                break;
            case 4:
                viewReports(stmt);
                break;
            default:
                System.out.println("Logging out...\n");
                jdbc.logout = true;
        }
    }
    
    /* fdrOperations() - Displays Front Desk Representative's operations */
    void fdrOperations(Statement stmt, Connection conn) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n---------------Front Desk Representative View---------------\n\n");
        System.out.println("1) Manage customers\n");
        System.out.println("2) Manage room assignment\n");
        System.out.println("3) Manage staff\n");
        System.out.println("4) Manage services\n");
        System.out.println("5) Check-out\n");
        System.out.println("Any other number to logout\n");
        
        int option = sc.nextInt();
        System.out.print("\033[H\033[2J");
        
        switch(option) {
            case 1:
                manageCustomers(stmt);
                break;
            case 2:
                dbms.assignRoom(stmt, conn);
                break;
            case 3:
                manageStaff(stmt);
                break;
            case 4:
                manageServices(stmt, conn);
                break;
            case 5:
                dbms.generateBill(stmt, conn);
                break;
            default:
                System.out.println("Logging out...\n");
                jdbc.logout = true;
        }
    }
    
    /* manageHotels() - Displays operations on 'hotels' table and calls the selected query */
    void manageHotels(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Manage Hotels\n\n");
        System.out.println("1) Add a new hotel\n");
        System.out.println("2) Delete a hotel\n");
        System.out.println("3) Update hotel's info\n");
        System.out.println("4) View Hotel list\n");
        
        int option = sc.nextInt();
        System.out.print("\033[H\033[2J");
        
        switch(option) {
            case 1:
                dbms.addHotel(stmt);
                break;
            case 2:
                dbms.deleteHotel(stmt);
                break;
            case 3:
                dbms.updateHotel(stmt);
                break;
            case 4:
                dbms.displayHotels(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    /* manageRooms() - Displays operations on 'rooms' table and calls the selected query */
    void manageRooms(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Manage Rooms\n\n");
        System.out.println("1) Add a new room\n");
        System.out.println("2) Delete a room\n");
        System.out.println("3) Update room info\n");
        System.out.println("4) View Room list\n");
        
        int option = sc.nextInt();
        System.out.print("\033[H\033[2J");
        
        switch(option) {
            case 1:
                dbms.addRoom(stmt);
                break;
            case 2:
                dbms.deleteRoom(stmt);
                break;
            case 3:
                dbms.updateRoom(stmt);
                break;
            case 4:
                dbms.displayRooms(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    /* manageStaff() - Displays operations on 'staff' table and calls the selected query */
    void manageStaff(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Manage Staff\n\n");
        System.out.println("1) Add new staff\n");
        System.out.println("2) Delete staff\n");
        System.out.println("3) Update staff's info\n");
        System.out.println("4) View Staff list\n");
        
        int option = sc.nextInt();
        System.out.print("\033[H\033[2J");
        
        switch(option) {
            case 1:
                dbms.addStaff(stmt);
                break;
            case 2:
                dbms.deleteStaff(stmt);
                break;
            case 3:
                dbms.updateStaff(stmt);
                break;
            case 4:
                dbms.displayStaff(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    /* manageCustomers() - Displays operations on 'customers' table and calls the selected query */
    void manageCustomers(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Manage Customers\n\n");
        System.out.println("1) Add customer\n");
        System.out.println("2) Delete customer\n");
        System.out.println("3) Update customer's info\n");
        System.out.println("4) View Customer list\n");
        
        int option = sc.nextInt();
        System.out.print("\033[H\033[2J");
        
        switch(option) {
            case 1:
                dbms.addCustomer(stmt);
                break;
            case 2:
                dbms.deleteCustomer(stmt);
                break;
            case 3:
                dbms.updateCustomer(stmt);
                break;
            case 4:
                dbms.displayCustomers(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    /* manageServices() - Displays operations on 'services' table and calls the selected query */
    void manageServices(Statement stmt, Connection conn) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Manage Services\n\n");
        System.out.println("1) Add new service\n");
        System.out.println("2) Update service info\n");
        System.out.println("3) View Service list\n");
        
        int option = sc.nextInt();
        System.out.print("\033[H\033[2J");
        
        switch(option) {
            case 1:
                dbms.addService(stmt);
                break;
            case 2:
                dbms.updateService(stmt, conn);
                break;
            case 3:
                dbms.displayServices(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
    
    /* viewReports() - Displays various report types and calls the selected query */
    void viewReports(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        
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
        System.out.print("\033[H\033[2J");
        
        switch(option) {
            case 1:
                dbms.occHotel(stmt);
                break;
            case 2:
                dbms.occRoomType(stmt);
                break;
            case 3:
                dbms.occDateRange(stmt);
                break;
            case 4:
                dbms.occCity(stmt);
                break;
            case 5:
                dbms.occTotal(stmt);
                break;
            case 6:
                dbms.revenue(stmt);
                break;
            case 7:
                dbms.staffInfoByRole(stmt);
                break;
            case 8:
                dbms.staffInfoByCust(stmt);
                break;
            default:
                System.out.println("Invalid option. Re-enter!\n");
        }
    }
}
