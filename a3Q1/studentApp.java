import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Scanner;

public class studentApp {

    // Function to establish a connection to the PostgreSQL database
    private static Connection connect(String url, String user, String password) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else{
                System.out.println("Failed to establish connection");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Function to retrieve all students from the database
    private static void getAllStudents(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            System.out.println("All students:");
            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + " | " +
                        rs.getString("first_name") + " | " +
                        rs.getString("last_name") + " | " +
                        rs.getString("email") + " | " +
                        rs.getDate("enrollment_date"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving students");
            e.printStackTrace();
        }
    }

    // Function to add a new student to the database
    private static void addStudent(Connection conn, String firstName, String lastName, String email, Date enrollmentDate) {
        try {
            String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, enrollmentDate);
            pstmt.executeUpdate();
            System.out.println("Student added successfully");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error adding student");
            e.printStackTrace();
        }
    }

    // Function to update the email of a student
    private static void updateStudentEmail(Connection conn, int studentId, String newEmail) {
        try {
            String sql = "UPDATE students SET email = ? WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
            System.out.println("Email updated successfully");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating email");
            e.printStackTrace();
        }
    }

    // Function to delete a student from the database
    private static void deleteStudent(Connection conn, int studentId) {
        try {
            String sql = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
            System.out.println("Student deleted successfully");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error deleting student");
            e.printStackTrace();
        }
    }

    // Main function to test the implemented functions
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5433/a3";
        String user = "postgres";
        String password = "fener";
        
        Connection conn = connect(url, user, password);
        if (conn != null) {
            Scanner scanner = new Scanner(System.in);

            boolean running = true;
            while (running) {
                System.out.println("Select an option:");
                System.out.println("1. Add a new student");
                System.out.println("2. Update student's email");
                System.out.println("3. Delete a student");
                System.out.println("4. Get all students");
                System.out.println("5. Exit");

                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("Enter first name:");
                        String firstName = scanner.next();
                        System.out.println("Enter last name:");
                        String lastName = scanner.next();
                        System.out.println("Enter email:");
                        String email = scanner.next();
                        System.out.println("Enter enrollment date (YYYY-MM-DD):");
                        String enrollmentDate = scanner.next();
                        addStudent(conn, firstName, lastName, email, Date.valueOf(enrollmentDate));
                        break;
                    case 2:
                        System.out.println("Enter student ID:");
                        int studentId = scanner.nextInt();
                        System.out.println("Enter new email:");
                        String newEmail = scanner.next();
                        updateStudentEmail(conn, studentId, newEmail);
                        break;
                    case 3:
                        System.out.println("Enter student ID:");
                        int idToDelete = scanner.nextInt();
                        deleteStudent(conn, idToDelete);
                        break;
                    case 4:
                        getAllStudents(conn);
                        break;
                    case 5:
                        running = false;
                        System.out.println("Exiting... See you next time!");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            }

            scanner.close();

            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}