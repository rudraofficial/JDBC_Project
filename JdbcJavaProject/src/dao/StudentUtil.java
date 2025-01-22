package dao;

import conn.JdbcConnec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentUtil {

    private static Connection connectToDb() throws SQLException {
        try {
            return JdbcConnec.getInstance().getConn();
        } catch (SQLException e) {
            throw new SQLException("Error connecting to database: " + e.getMessage());
        }
    }

    public static boolean createStudentTable() throws SQLException {
        Statement stmt = null;

        stmt = connectToDb().createStatement();
        return !stmt.execute(
                "CREATE TABLE if not exists Student(stu_id int primary key auto_increment, Stud_Name varchar(20) not null, Stud_course varchar(15), Stu_age tinyint, pro_Img mediumblob);");

    }

    public static String insertStudent(String name, String course, byte age) throws SQLException {
        PreparedStatement stmt = null;

        stmt = connectToDb()
                .prepareStatement("insert into Student (Stud_name, Stud_course, Stu_age) values (?, ?, ?);");
        stmt.setString(1, name);
        stmt.setString(2, course);
        stmt.setInt(3, (int) age);

        return stmt.executeUpdate() > 0 ? "Student Added" : "Error Not Added";

    }

    public static String updateStudent(int id, String name, String course, byte age) throws SQLException {
        PreparedStatement stmt = null;

        stmt = connectToDb()
                .prepareStatement("UPDATE Student SET Stud_Name = ?, Stud_course = ?, Stu_age= ? WHERE stu_id = ?;");
        stmt.setString(1, name);
        stmt.setString(2, course);
        stmt.setInt(3, age);
        stmt.setInt(4, id);
        return stmt.executeUpdate() > 0 ? "Updated Student" : "Not Updated";

    }

    public static boolean deleteStudent(int id) throws SQLException {
        Statement stmt = null;

        stmt = connectToDb().createStatement();
        String query = String.format("DELETE FROM Student WHERE stu_id=%d", id);
        return stmt.executeUpdate(query) > 0;

    }

    public static ResultSet searchStudentById(int id) throws SQLException {
        Statement stmt = null;

        stmt = connectToDb().createStatement();
        String query = String.format("SELECT * FROM Student WHERE stu_id=%d", id);
        return stmt.executeQuery(query);
    }

    public static String uploadPhoto(File file, int id) throws SQLException, IOException {
        PreparedStatement stmt = null;
        FileInputStream fis = null;

        stmt = connectToDb().prepareStatement("update student set pro_img = ? where stu_id = ?;");
        stmt.setInt(2, id);
        fis = new FileInputStream(file);
        stmt.setBinaryStream(1, fis, (int) file.length()); // Use file length for better performance
        return stmt.executeUpdate() >= 1 ? "Photo Uploaded\n" : "Error Not Uploaded\n";
    }

}