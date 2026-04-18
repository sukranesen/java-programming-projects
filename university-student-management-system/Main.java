import java.io.*;

// Main entry point of the University Student Management System
public class Main {

    public static void main(String[] args) {
        // Check for correct number of input arguments
        if (args.length != 7) {
            System.out.println("Usage: java Main persons.txt departments.txt programs.txt courses.txt assignments.txt grades.txt output.txt");
            return;
        }

        // Get input and output file paths from command-line arguments
        String personFile = args[0];
        String departmentFile = args[1];
        String programFile = args[2];
        String courseFile = args[3];
        String assignmentFile = args[4];
        String gradeFile = args[5];
        String outputFile = args[6];

        // Create the management system instance
        StudentManagementSystem sms = new StudentManagementSystem();

        // Open output file writer and load data + generate report
        try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
            sms.loadPersons(personFile, pw);       // Load person data
            sms.loadDepartments(departmentFile, pw); // Load departments
            sms.loadPrograms(programFile, pw);       // Load programs
            sms.loadCourses(courseFile, pw);         // Load courses
            sms.loadAssignments(assignmentFile, pw); // Load course assignments
            sms.loadGrades(gradeFile, pw);           // Load grades

            sms.generateReports(pw); // Write all reports to output file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
