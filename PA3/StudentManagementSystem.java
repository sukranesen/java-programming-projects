import java.io.*;
import java.util.*;

// This class manages all entities in the university system,
// including students, faculty, departments, programs, and courses.
public class StudentManagementSystem {

    // Maps for storing academic data with key as ID or code
    private Map<String, Student> students = new LinkedHashMap<>();
    private Map<String, AcademicMember> faculty = new LinkedHashMap<>();
    private Map<String, Department> departments = new LinkedHashMap<>();
    private Map<String, Program> programs = new LinkedHashMap<>();
    private Map<String, Course> courses = new LinkedHashMap<>();

    // Reads and loads student and academic member data from file
    public void loadPersons(String filename, PrintWriter pw) {
        pw.println("Reading Person Information ");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    // Split each line by comma (expecting 5 parts)
                    String[] parts = line.split(",", 5);
                    if (parts.length < 5) throw new IllegalArgumentException();

                    String type = parts[0]; // "S" or "F"
                    String id = parts[1];
                    String name = parts[2];
                    String email = parts[3];
                    String department = parts[4];

                    // Create and store Student or AcademicMember
                    if (type.equals("S")) {
                        Student s = new Student(id, name, email, department);
                        students.put(id, s);
                    } else if (type.equals("F")) {
                        AcademicMember f = new AcademicMember(id, name, email, department);
                        faculty.put(id, f);
                    } else {
                        // If person type is not recognized
                        pw.println("Invalid Person Type");
                    }
                } catch (Exception e) {
                    // Catch malformed line or conversion errors
                    pw.println("Invalid Person Type");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads department info and assigns head if valid faculty found
    public void loadDepartments(String filename, PrintWriter pw) {
        pw.println("Reading Departments ");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into 4 expected parts
                String[] parts = line.split(",", 4);
                if (parts.length < 4) continue;

                String code = parts[0];
                String name = parts[1];
                String desc = parts[2];
                String headId = parts[3];

                // Create department object
                Department d = new Department(code, name, desc);

                // Assign department head if exists in faculty
                if (faculty.containsKey(headId)) {
                    d.setHead(faculty.get(headId));
                } else {
                    pw.println("Academic Member Not Found with ID " + headId);
                }

                departments.put(code, d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads program information and stores it
    public void loadPrograms(String filename, PrintWriter pw) {
        pw.println("Reading Programs ");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Each line must have 6 parts
                String[]parts = line.split(",", 6);
                if (parts.length < 6) continue;

                String code = parts[0];
                String name = parts[1];
                String desc = parts[2];
                String department = parts[3];
                String degree = parts[4];
                int credits = Integer.parseInt(parts[5]);

                // Create and save program
                Program p = new Program(code, name, desc, department, degree, credits);
                programs.put(code, p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads course information and assigns each course to its program
    public void loadCourses(String filename, PrintWriter pw) {
        pw.println("Reading Courses ");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split course line into 6 fields
                String[] parts = line.split(",", 6);
                if (parts.length < 6) continue;

                String code = parts[0];
                String name = parts[1];
                String dept = parts[2];
                int credits = Integer.parseInt(parts[3]);
                String semester = parts[4];
                String programCode = parts[5];

                // Create and store course
                Course c = new Course(code, name, dept, credits, semester, programCode);
                courses.put(code, c);

                // Add course to related program if it exists
                if (programs.containsKey(programCode)) {
                    programs.get(programCode).addCourse(code);
                } else {
                    pw.println("Program " + programCode + " Not Found");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Assigns courses to instructors and students
    public void loadAssignments(String filename, PrintWriter pw) {
        pw.println("Reading Course Assignments ");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;

                String type = parts[0];  // "F" for faculty, "S" for student
                String id = parts[1];
                String courseCode = parts[2];

                // Assign course to faculty
                if (type.equals("F")) {
                    if (!faculty.containsKey(id)) {
                        pw.println("Academic Member Not Found with ID " + id);
                        continue;
                    }
                    if (!courses.containsKey(courseCode)) {
                        pw.println("Course " + courseCode + " Not Found");
                        continue;
                    }

                    AcademicMember instructor = faculty.get(id);
                    Course course = courses.get(courseCode);
                    course.setInstructor(instructor);
                    instructor.assignCourse(course);

                    // Assign course to student
                } else if (type.equals("S")) {
                    if (!students.containsKey(id)) {
                        pw.println("Student Not Found with ID " + id);
                        continue;
                    }
                    if (!courses.containsKey(courseCode)) {
                        pw.println("Course " + courseCode + " Not Found");
                        continue;
                    }

                    Student student = students.get(id);
                    Course course = courses.get(courseCode);
                    course.enrollStudent(student);
                    student.enrollInCourse(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads grades for students and records them
    public void loadGrades(String filename, PrintWriter pw) {
        pw.println("Reading Grades ");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;

                String grade = parts[0];
                String studentId = parts[1];
                String courseCode = parts[2];

                // Check if student exists
                if (!students.containsKey(studentId)) {
                    pw.println("Student Not Found with ID " + studentId);
                    continue;
                }

                // Check if course exists
                if (!courses.containsKey(courseCode)) {
                    pw.println("Course " + courseCode + " Not Found");
                    continue;
                }

                // Validate grade format
                if (GradeUtil.getGradePoint(grade) == -1.0) {
                    pw.println("The grade " + grade + " is not valid");
                    continue;
                }

                // Save the valid grade to course and student
                Student student = students.get(studentId);
                Course course = courses.get(courseCode);

                course.addGrade(student, grade);
                student.completeCourse(course, grade);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generates the final reports for all entities and prints to output
    public void generateReports(PrintWriter pw) {

        // Academic Members Section
        pw.println("----------------------------------------");
        pw.println("            Academic Members");
        pw.println("----------------------------------------");
        for (AcademicMember am : faculty.values()) {
            pw.print(am.generateReport());
        }
        pw.println("----------------------------------------");
        pw.println();

        // Student List Section
        pw.println("----------------------------------------");
        pw.println("                STUDENTS");
        pw.println("----------------------------------------");
        for (Student s : students.values()) {
            pw.print(s.studentList());
        }
        pw.println("----------------------------------------");
        pw.println();

        // Department Information Section
        pw.println("---------------------------------------");
        pw.println("              DEPARTMENTS");
        pw.println("---------------------------------------");
        for (Department d : departments.values()) {
            pw.println("Department Code: " + d.getCode());
            pw.println("Name: " + d.getName());
            AcademicMember head = d.getHead();
            pw.println("Head: " + (head != null ? head.getName() : "Not assigned"));
            pw.println();
        }
        pw.println("----------------------------------------");
        pw.println();

        // Program Details Section
        pw.println("--------------------------------------");
        pw.println("                PROGRAMS");
        pw.println("---------------------------------------");
        programs.values().stream()
                .sorted(Comparator.comparing(Program::getCode))
                .forEach(p -> pw.print(p.generateReport()));
        pw.println("----------------------------------------");
        pw.println();

        // Course Basic Info Section
        pw.println("---------------------------------------");
        pw.println("                COURSES");
        pw.println("---------------------------------------");
        courses.values().stream()
                .sorted(Comparator.comparing(Course::getCode))
                .forEach(c -> {
                    pw.println("Course Code: " + c.getCode());
                    pw.println("Name: " + c.getName());
                    pw.println("Department: " + c.getDepartment());
                    pw.println("Credits: " + c.getCredits());
                    pw.println("Semester: " + c.getSemester());
                    pw.println();
                });
        pw.println("----------------------------------------");
        pw.println();

        // Detailed Course Reports Section
        pw.println("----------------------------------------");
        pw.println("             COURSE REPORTS");
        pw.println("----------------------------------------");
        courses.values().stream()
                .sorted(Comparator.comparing(Course::getCode))
                .forEach(c -> pw.print(c.generateReport()));

        // Detailed Student Reports Section
        pw.println("----------------------------------------");
        pw.println("            STUDENT REPORTS");
        pw.println("----------------------------------------");
        for (Student s : students.values()) {
            pw.print(s.generateReport());
            pw.println();
        }
    }
}
