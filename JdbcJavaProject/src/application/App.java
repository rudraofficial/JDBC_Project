package application;

//getting two errors as i entered two words name then two statemet printing automatically
//another is in databse autoincrement
//in case search by student it always showing exception

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.StudentUtil;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Create Student Table");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Add Student");
            System.out.println("5. Search Student by ID");
            System.out.println("6. Upload Image");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        try {
                            boolean tableCreated = StudentUtil.createStudentTable();
                            System.out.println("Student table created: " + tableCreated);
                        } catch (SQLException e) {
                            System.out.println("Error creating student table: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.print("Enter student ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student course: ");
                        String course = scanner.nextLine();
                        System.out.print("Enter student age: ");
                        byte age = scanner.nextByte();
                        try {
                            String studentUpdated = StudentUtil.updateStudent(id, name, course, age);
                            System.out.println("Student updated: " + studentUpdated);
                        } catch (SQLException e) {
                            System.out.println("Error updating student: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.print("Enter student ID: ");
                        int deleteId = scanner.nextInt();
                        try {
                            boolean studentDeleted = StudentUtil.deleteStudent(deleteId);
                            System.out.println("Student deleted: " + studentDeleted);
                        } catch (SQLException e) {
                            System.out.println("Error deleting student: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("Enter student name: ");
                        String insName = scanner.nextLine();
                        System.out.println("Enter student course: ");
                        String insCourse = scanner.nextLine();
                        System.out.println("Enter student age: ");
                        byte insAge = scanner.nextByte();
                        try {
                            String studentAdded = StudentUtil.insertStudent(insName, insCourse, insAge);
                            System.out.println(studentAdded);
                        } catch (SQLException e) {
                            System.out.println("Error adding student: " + e.getMessage());
                        }
                        break;

                    case 5:
                        System.out.print("Enter student ID: ");
                        int searchId = scanner.nextInt();
                        try {
                            ResultSet rs = StudentUtil.searchStudentById(searchId);
                            if (rs.next()) {
                                System.out.println("Student ID: " + rs.getInt("stu_id"));
                                System.out.println("Student Name: " + rs.getString("Stud_Name"));
                                System.out.println("Student Course: " + rs.getString("Stud_course"));
                                System.out.println("Student Age: " + rs.getByte("Stu_age"));
                            } else {
                                System.out.println("Student not found.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error searching student: " + e.getMessage());
                        }
                        break;

                    case 6:
                        System.out.print("Enter file location: ");
                        String loc = scanner.next();
                        System.out.print("Enter student ID: ");
                        int imgId = scanner.nextInt();
                        try {
                            String sc = StudentUtil.uploadPhoto(new File(loc), imgId);
                            System.out.print(sc);
                        } catch (IOException e) {
                            System.out.println("Error uploading photo: " + e.getMessage());
                        } catch (SQLException e) {
                            System.out.println("Database error during photo upload: " + e.getMessage());
                        }
                        break;

                    case 7:
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
    }
}