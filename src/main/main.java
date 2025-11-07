package main;

import java.util.*;
import config.config;

public class main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        main app = new main();
        String resp;

        do {
            System.out.println("\n===== SCHOLARSHIP APPLICATION SYSTEM =====");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Manage Users");
            System.out.println("4. Manage Scholarships");
            System.out.println("5. Manage Evaluations");
            System.out.println("6. Manage Applications");
            System.out.println("7. Exit");
            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 1: app.addUser(); break;
                case 2: app.loginUser(); break;
                case 3: app.manageUsers(); break;
                case 4: app.manageScholarships(); break;
                case 5: app.manageEvaluations(); break;
                case 6: app.manageApplications(); break;
                case 7:
                    System.out.println("Thank you for using the system!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }

            System.out.print("\nDo you want to continue? (yes/no): ");
            resp = sc.nextLine();
        } while (resp.equalsIgnoreCase("yes"));

        System.out.println("Goodbye!");
    }

    // ------------------- MANAGE USERS -------------------
    public void manageUsers() {
        Scanner sc = new Scanner(System.in);
        main app = new main();
        int choice;

        do {
            System.out.println("\n===== USER MANAGEMENT =====");
            System.out.println("1. View Users");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter Action: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: app.viewUsers(); break;
                case 2: app.viewUsers(); app.updateUser(); break;
                case 3: app.viewUsers(); app.deleteUser(); break;
                case 4: System.out.println("Returning to Main Menu..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    // ------------------- ADD USER -------------------
    public void addUser() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Role (Admin/Student): ");
        String role = sc.nextLine();
        System.out.print("Enter First Name: ");
        String first_name = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String last_name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Status: ");
        String status = sc.nextLine();

        String sql = "INSERT INTO tbl_user (role, first_name, last_name, email, password, status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, role, first_name, last_name, email, password, status);

        System.out.println("User registered successfully!");
    }

    public void viewUsers() {
        String qry = "SELECT * FROM tbl_user";
        String[] headers = {"ID", "Role", "First Name", "Last Name", "Email", "Status"};
        String[] columns = {"user_id", "role", "first_name", "last_name", "email", "status"};

        config conf = new config();
        conf.viewRecords(qry, headers, columns);
    }

    public void updateUser() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Role: ");
        String role = sc.nextLine();
        System.out.print("Enter new First Name: ");
        String first_name = sc.nextLine();
        System.out.print("Enter new Last Name: ");
        String last_name = sc.nextLine();
        System.out.print("Enter new Email: ");
        String email = sc.nextLine();
        System.out.print("Enter new Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Status: ");
        String status = sc.nextLine();

        String qry = "UPDATE tbl_user SET role = ?, first_name = ?, last_name = ?, email = ?, password = ?, status = ? WHERE user_id = ?";
        conf.updateRecord(qry, role, first_name, last_name, email, password, status, id);

        System.out.println("User updated successfully! Status: " + status);
    }

    public void deleteUser() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_user WHERE user_id = ?";
        conf.deleteRecord(qry, id);

        System.out.println("User deleted successfully!");
    }

    public void loginUser() {
        Scanner sc = new Scanner(System.in);
        config con = new config();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        String sql = "SELECT * FROM tbl_user WHERE email = ? AND password = ?";
        var result = con.fetchRecords(sql, email, pass);

        if (!result.isEmpty()) {
            var user = result.get(0);
            String role = (String) user.get("role");

            System.out.println("\nLogin successful!");
            System.out.println("Welcome, " + user.get("first_name") + " " + user.get("last_name"));
            System.out.println("Role: " + role);

            if (role.equalsIgnoreCase("Admin")) {
                System.out.println("Access granted. You can manage users, scholarships, applications, and evaluations.");
            } else if (role.equalsIgnoreCase("Student")) {
                System.out.println("Welcome student! You can now apply for scholarships or view your status.");
            } else {
                System.out.println("Unknown role.");
            }
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    public void manageScholarships() {
        Scanner sc = new Scanner(System.in);
        main app = new main();
        int choice;

        do {
            System.out.println("\n===== SCHOLARSHIP MANAGEMENT =====");
            System.out.println("1. Add Scholarship");
            System.out.println("2. View Scholarships");
            System.out.println("3. Update Scholarship");
            System.out.println("4. Delete Scholarship");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter Action: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: app.addScholarship(); break;
                case 2: app.viewScholarships(); break;
                case 3: app.viewScholarships(); app.updateScholarship(); break;
                case 4: app.viewScholarships(); app.deleteScholarship(); break;
                case 5: System.out.println("Returning to Main Menu..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public void addScholarship() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Scholarship Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Criteria: ");
        String criteria = sc.nextLine();
        System.out.print("Enter Benefits: ");
        String benefits = sc.nextLine();
        System.out.print("Enter Status: ");
        String status = sc.nextLine();

        String sql = "INSERT INTO tbl_Scholarship (scholarship_name, criteria, benefits, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, name, criteria, benefits, status);

        System.out.println("✅ Scholarship added successfully!");
    }

    public void viewScholarships() {
        String qry = "SELECT * FROM tbl_Scholarship";
        String[] headers = {"ID", "Scholarship Name", "Criteria", "Benefits", "Status"};
        String[] columns = {"scholarship_id", "scholarship_name", "criteria", "benefits", "status"};

        config conf = new config();
        conf.viewRecords(qry, headers, columns);
    }

    public void updateScholarship() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Scholarship ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Scholarship Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Criteria: ");
        String criteria = sc.nextLine();
        System.out.print("Enter new Benefits: ");
        String benefits = sc.nextLine();
        System.out.print("Enter new Status: ");
        String status = sc.nextLine();

        String qry = "UPDATE tbl_Scholarship SET scholarship_name = ?, criteria = ?, benefits = ?, status = ? WHERE scholarship_id = ?";
        conf.updateRecord(qry, name, criteria, benefits, status, id);

        System.out.println("Scholarship updated successfully!");
    }

    public void deleteScholarship() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Scholarship ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_Scholarship WHERE scholarship_id = ?";
        conf.deleteRecord(qry, id);

        System.out.println(" Scholarship deleted successfully!");
    }

    public void manageEvaluations() {
        Scanner sc = new Scanner(System.in);
        main app = new main();
        int choice;

        do {
            System.out.println("\n===== EVALUATION MANAGEMENT =====");
            System.out.println("1. Add Evaluation");
            System.out.println("2. View Evaluations");
            System.out.println("3. Update Evaluation");
            System.out.println("4. Delete Evaluation");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter Action: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: app.addEvaluation(); break;
                case 2: app.viewEvaluations(); break;
                case 3: app.viewEvaluations(); app.updateEvaluation(); break;
                case 4: app.viewEvaluations(); app.deleteEvaluation(); break;
                case 5: System.out.println("Returning to Main Menu..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public void addEvaluation() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Application ID: ");
        int app_id = sc.nextInt();
        System.out.print("Enter Grades: ");
        int grades = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Requirements Checked: ");
        String req = sc.nextLine();
        System.out.print("Enter Qualification Checked: ");
        String qual = sc.nextLine();
        System.out.print("Enter Remarks: ");
        String remarks = sc.nextLine();

        String sql = "INSERT INTO tbl_Evaluation (application_id, grades, requirements_checked, qualification_checked, remarks) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, app_id, grades, req, qual, remarks);

        System.out.println("Evaluation added successfully!");
    }

    public void viewEvaluations() {
        String qry = "SELECT * FROM tbl_Evaluation";
        String[] headers = {"ID", "Application ID", "Grades", "Requirements Checked", "Qualification Checked", "Remarks"};
        String[] columns = {"evaluation_id", "application_id", "grades", "requirements_checked", "qualification_checked", "remarks"};

        config conf = new config();
        conf.viewRecords(qry, headers, columns);
    }

    public void updateEvaluation() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Evaluation ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Grades: ");
        int grades = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Requirements Checked: ");
        String req = sc.nextLine();
        System.out.print("Enter new Qualification Checked: ");
        String qual = sc.nextLine();
        System.out.print("Enter new Remarks: ");
        String remarks = sc.nextLine();

        String qry = "UPDATE tbl_Evaluation SET grades = ?, requirements_checked = ?, qualification_checked = ?, remarks = ? WHERE evaluation_id = ?";
        conf.updateRecord(qry, grades, req, qual, remarks, id);

        System.out.println(" Evaluation updated successfully!");
    }

    public void deleteEvaluation() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Evaluation ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_Evaluation WHERE evaluation_id = ?";
        conf.deleteRecord(qry, id);

        System.out.println("Evaluation deleted successfully!");
    }

    public void manageApplications() {
        Scanner sc = new Scanner(System.in);
        main app = new main();
        int choice;

        do {
            System.out.println("\n===== APPLICATION MANAGEMENT =====");
            System.out.println("1. Add Application");
            System.out.println("2. View Applications");
            System.out.println("3. Update Application");
            System.out.println("4. Delete Application");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter Action: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: app.addApplication(); break;
                case 2: app.viewApplications(); break;
                case 3: app.viewApplications(); app.updateApplication(); break;
                case 4: app.viewApplications(); app.deleteApplication(); break;
                case 5: System.out.println("Returning to Main Menu..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public void addApplication() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Student ID: ");
        int student_id = sc.nextInt();
        System.out.print("Enter Scholarship ID: ");
        int scholarship_id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Date Submitted (YYYY-MM-DD): ");
        String date_submitted = sc.nextLine();
        System.out.print("Enter Requirements: ");
        String requirements = sc.nextLine();
        System.out.print("Enter Status (Pending/Approved/Rejected): ");
        String status = sc.nextLine();
        System.out.print("Enter School Year (e.g., 2025): ");
        int school_year = sc.nextInt();

        String sql = "INSERT INTO tbl_Applications (student_id, scholarship_id, date_submitted, reqirments, status, school_year) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, student_id, scholarship_id, date_submitted, requirements, status, school_year);

        System.out.println("Application added successfully!");
    }

    public void viewApplications() {
        String qry = "SELECT * FROM tbl_Applications";
        String[] headers = {"ID", "Student ID", "Scholarship ID", "Date Submitted", "Requirements", "Status", "School Year"};
        String[] columns = {"app_id", "student_id", "scholarship_id", "date_submitted", "reqirments", "status", "school_year"};

        config conf = new config();
        conf.viewRecords(qry, headers, columns);
    }

    public void updateApplication() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Application ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Requirements: ");
        String requirements = sc.nextLine();
        System.out.print("Enter new Status: ");
        String status = sc.nextLine();
        System.out.print("Enter new School Year: ");
        int school_year = sc.nextInt();

        String qry = "UPDATE tbl_Applications SET reqirments = ?, status = ?, school_year = ? WHERE app_id = ?";
        conf.updateRecord(qry, requirements, status, school_year, id);

        System.out.println("Application updated successfully!");
    }

    public void deleteApplication() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Application ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_Applications WHERE app_id = ?";
        conf.deleteRecord(qry, id);

        System.out.println("Application deleted successfully!");
    }
}
