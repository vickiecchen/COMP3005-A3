import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class assignment3 {

    // login credentials
    // the %20 is to represent the space in the DB name (Assignment 3)
    static String url = "jdbc:postgresql://localhost:5432/Assignment%203";
    static String user = "vickiechen";
    static String password = "comp3005VC";

    // retrieves and displays all records from the students table
    public static void getAllStudents() {
        String sql = "SELECT student_id, first_name, last_name, email, enrollment_date FROM students";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Students:");
            while (rs.next()) {
                System.out.println(
                    "Student ID: " + rs.getInt("student_id") + " | Name: "
                    + rs.getString("first_name") + " "
                    + rs.getString("last_name") + " | Email: "
                    + rs.getString("email") + " | Enrolled: "
                    + rs.getDate("enrollment_date")
                );
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // inserts a new student record into the students table
    // uses PreparedStatement (from lecture) so values are passed safely with ?
    public static void addStudent(String first_name, String last_name, String email, String enrollment_date) {
        String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);

            // yyyy-mm-dd format
            pstmt.setDate(4, java.sql.Date.valueOf(enrollment_date));

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student added successfully!");
            }

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // updates the email address for the student with the given student_id
    public static void updateStudentEmail(int student_id, String new_email) {
        String sql = "UPDATE students SET email = ? WHERE student_id = ?";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, new_email);
            pstmt.setInt(2, student_id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Email updated successfully!");
            } else {
                System.out.println("No student found with ID " + student_id);
            }

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // deletes the student with the given student_id
    public static void deleteStudent(int student_id) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, student_id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with ID " + student_id);
            }

            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to establish connection.");
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        getAllStudents();

        addStudent("Vickie", "Chen", "vick.chen@example.com", "2023-09-03");
        getAllStudents();

        updateStudentEmail(1, "jake.new@example.com");
        getAllStudents();

        deleteStudent(5);
        getAllStudents();

        deleteStudent(3);
        getAllStudents();

        updateStudentEmail(3, "joshua.new@example.com");
        getAllStudents();
    }
}
