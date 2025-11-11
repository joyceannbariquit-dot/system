package main;

import java.util.*;
import config.config;

public class main {
    static Scanner sc = new Scanner(System.in);
    static config conf = new config();
    static Map<String, Object> loggedInUser = null;

    public static void main(String[] args) {
        main app = new main();
        String resp;

        do {
            System.out.println("\n===== SCHOLARSHIP APPLICATION SYSTEM =====");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Exit");
            System.out.print("Enter Action: ");
            int action = sc.nextInt(); sc.nextLine();

            switch (action) {
                case 1: app.addUser(); break;
                case 2: app.loginUser(); break;
                case 3: System.out.println("Thank you for using the system!"); System.exit(0);
                default: System.out.println("Invalid choice!");
            }

            System.out.print("\nDo you want to continue? (yes/no): ");
            resp = sc.nextLine();
        } while (resp.equalsIgnoreCase("yes"));

        System.out.println("Goodbye!");
    }

    public void addUser() {
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

    public void loginUser() {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        String sql = "SELECT * FROM tbl_user WHERE email = ? AND password = ?";
        var result = conf.fetchRecords(sql, email, pass);

        if (!result.isEmpty()) {
            loggedInUser = result.get(0);
            String role = (String) loggedInUser.get("role");
            System.out.println("\nLogin successful! Welcome " + loggedInUser.get("first_name"));

            if (role.equalsIgnoreCase("Admin")) {
                manageAdminMenu();
            } else if (role.equalsIgnoreCase("Student")) {
                manageStudentMenu();
            }
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    public void manageAdminMenu() {
        int choice;
        do {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Scholarships");
            System.out.println("3. Manage Applications");
            System.out.println("4. Manage Evaluations");
            System.out.println("5. Logout");
            System.out.print("Enter Action: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: manageUsers(); break;
                case 2: manageScholarships(); break;
                case 3: manageApplications(); break;
                case 4: manageEvaluations(); break;
                case 5: System.out.println("Logging out..."); loggedInUser = null; break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public void manageStudentMenu() {
        int choice;
        do {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View Scholarships");
            System.out.println("2. View My Applications");
            System.out.println("3. View My Evaluations");
            System.out.println("4. Logout");
            System.out.print("Enter Action: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: viewScholarships(); break;
                case 2: viewStudentApplications(); break;
                case 3: viewStudentEvaluations(); break;
                case 4: System.out.println("Logging out..."); loggedInUser = null; break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    public void manageUsers() {
        int choice;
        do {
            System.out.println("\n===== USER MANAGEMENT =====");
            System.out.println("1. View Users");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. Back");
            System.out.print("Enter Action: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: viewUsers(); break;
                case 2: viewUsers(); updateUser(); break;
                case 3: viewUsers(); deleteUser(); break;
                case 4: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    public void viewUsers() {
        String qry = "SELECT * FROM tbl_user";
        String[] headers = {"ID","Role","First Name","Last Name","Email","Status"};
        String[] cols = {"user_id","role","first_name","last_name","email","status"};
        conf.viewRecords(qry, headers, cols);
    }

    public void updateUser() {
        System.out.print("Enter User ID to update: "); int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter new Role: "); String role = sc.nextLine();
        System.out.print("Enter new First Name: "); String first_name = sc.nextLine();
        System.out.print("Enter new Last Name: "); String last_name = sc.nextLine();
        System.out.print("Enter new Email: "); String email = sc.nextLine();
        System.out.print("Enter new Password: "); String password = sc.nextLine();
        System.out.print("Enter new Status: "); String status = sc.nextLine();

        String sql = "UPDATE tbl_user SET role=?, first_name=?, last_name=?, email=?, password=?, status=? WHERE user_id=?";
        conf.updateRecord(sql, role, first_name, last_name, email, password, status, id);
    }

    public void deleteUser() {
        System.out.print("Enter User ID to delete: "); int id = sc.nextInt(); sc.nextLine();
        String sql = "DELETE FROM tbl_user WHERE user_id=?";
        conf.deleteRecord(sql, id);
    }

    public void manageScholarships() {
        int choice;
        do {
            System.out.println("\n===== SCHOLARSHIP MANAGEMENT =====");
            System.out.println("1. Add Scholarship");
            System.out.println("2. View Scholarships");
            System.out.println("3. Update Scholarship");
            System.out.println("4. Delete Scholarship");
            System.out.println("5. Back");
            System.out.print("Enter Action: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: addScholarship(); break;
                case 2: viewScholarships(); break;
                case 3: viewScholarships(); updateScholarship(); break;
                case 4: viewScholarships(); deleteScholarship(); break;
                case 5: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public void addScholarship() {
        System.out.print("Enter Scholarship Name: "); String name = sc.nextLine();
        System.out.print("Enter Criteria: "); String criteria = sc.nextLine();
        System.out.print("Enter Benefits: "); String benefits = sc.nextLine();
        System.out.print("Enter Status: "); String status = sc.nextLine();

        String sql = "INSERT INTO tbl_Scholarship (scholarship_name, criteria, benefits, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, name, criteria, benefits, status);
    }

    public void viewScholarships() {
        String qry = "SELECT * FROM tbl_Scholarship";
        String[] headers = {"ID","Scholarship Name","Criteria","Benefits","Status"};
        String[] cols = {"scholarship_id","scholarship_name","criteria","benefits","status"};
        conf.viewRecords(qry, headers, cols);
    }

    public void updateScholarship() {
        System.out.print("Enter Scholarship ID to update: "); int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter new Name: "); String name = sc.nextLine();
        System.out.print("Enter new Criteria: "); String criteria = sc.nextLine();
        System.out.print("Enter new Benefits: "); String benefits = sc.nextLine();
        System.out.print("Enter new Status: "); String status = sc.nextLine();

        String sql = "UPDATE tbl_Scholarship SET scholarship_name=?, criteria=?, benefits=?, status=? WHERE scholarship_id=?";
        conf.updateRecord(sql, name, criteria, benefits, status, id);
    }

    public void deleteScholarship() {
        System.out.print("Enter Scholarship ID to delete: "); int id = sc.nextInt(); sc.nextLine();
        String sql = "DELETE FROM tbl_Scholarship WHERE scholarship_id=?";
        conf.deleteRecord(sql, id);
    }

    public void manageApplications() {
        int choice;
        do {
            System.out.println("\n===== APPLICATION MANAGEMENT =====");
            System.out.println("1. Add Application");
            System.out.println("2. View Applications");
            System.out.println("3. Update Application");
            System.out.println("4. Delete Application");
            System.out.println("5. Back");
            System.out.print("Enter Action: "); choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1: addApplication(); break;
                case 2: viewApplications(); break;
                case 3: viewApplications(); updateApplication(); break;
                case 4: viewApplications(); deleteApplication(); break;
                case 5: break;
                default: System.out.println("Invalid choice!");
            }
        } while(choice != 5);
    }

    public void addApplication() {
        System.out.print("Enter Student ID: "); int student_id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Scholarship ID: "); int scholarship_id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Date Submitted (YYYY-MM-DD): "); String date_submitted = sc.nextLine();
        System.out.print("Enter Requirements: "); String requirements = sc.nextLine();
        System.out.print("Enter Status: "); String status = sc.nextLine();
        System.out.print("Enter School Year: "); int school_year = sc.nextInt(); sc.nextLine();

        String sql = "INSERT INTO tbl_Applications (student_id, scholarship_id, date_submitted, requirements, status, school_year) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, student_id, scholarship_id, date_submitted, requirements, status, school_year);
    }

    public void viewApplications() {
        String qry = "SELECT * FROM tbl_Applications";
        String[] headers = {"ID","Student ID","Scholarship ID","Date Submitted","Requirements","Status","School Year"};
        String[] cols = {"app_id","student_id","scholarship_id","date_submitted","requirements","status","school_year"};
        conf.viewRecords(qry, headers, cols);
    }

    public void updateApplication() {
        System.out.print("Enter Application ID to update: "); int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter new Requirements: "); String req = sc.nextLine();
        System.out.print("Enter new Status: "); String status = sc.nextLine();
        System.out.print("Enter new School Year: "); int sy = sc.nextInt(); sc.nextLine();

        String sql = "UPDATE tbl_Applications SET requirements=?, status=?, school_year=? WHERE app_id=?";
        conf.updateRecord(sql, req, status, sy, id);
    }

    public void deleteApplication() {
        System.out.print("Enter Application ID to delete: "); int id = sc.nextInt(); sc.nextLine();
        String sql = "DELETE FROM tbl_Applications WHERE app_id=?";
        conf.deleteRecord(sql, id);
    }

    public void manageEvaluations() {
        int choice;
        do {
            System.out.println("\n===== EVALUATION MANAGEMENT =====");
            System.out.println("1. Add Evaluation");
            System.out.println("2. View Evaluations");
            System.out.println("3. Update Evaluation");
            System.out.println("4. Delete Evaluation");
            System.out.println("5. Back");
            System.out.print("Enter Action: "); choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1: addEvaluation(); break;
                case 2: viewEvaluations(); break;
                case 3: viewEvaluations(); updateEvaluation(); break;
                case 4: viewEvaluations(); deleteEvaluation(); break;
                case 5: break;
                default: System.out.println("Invalid choice!");
            }
        } while(choice != 5);
    }

    public void addEvaluation() {
        System.out.print("Enter Application ID: "); int app_id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Grades: "); int grades = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Requirements Checked: "); String req = sc.nextLine();
        System.out.print("Enter Qualification Checked: "); String qual = sc.nextLine();
        System.out.print("Enter Remarks: "); String remarks = sc.nextLine();

        String sql = "INSERT INTO tbl_Evaluation (application_id, grades, requirements_checked, qualification_checked, remarks) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, app_id, grades, req, qual, remarks);
    }

    public void viewEvaluations() {
        String qry = "SELECT * FROM tbl_Evaluation";
        String[] headers = {"ID","Application ID","Grades","Requirements Checked","Qualification Checked","Remarks"};
        String[] cols = {"evaluation_id","application_id","grades","requirements_checked","qualification_checked","remarks"};
        conf.viewRecords(qry, headers, cols);
    }

    public void updateEvaluation() {
        System.out.print("Enter Evaluation ID to update: "); int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter new Grades: "); int grades = sc.nextInt(); sc.nextLine();
        System.out.print("Enter new Requirements Checked: "); String req = sc.nextLine();
        System.out.print("Enter new Qualification Checked: "); String qual = sc.nextLine();
        System.out.print("Enter new Remarks: "); String remarks = sc.nextLine();

        String sql = "UPDATE tbl_Evaluation SET grades=?, requirements_checked=?, qualification_checked=?, remarks=? WHERE evaluation_id=?";
        conf.updateRecord(sql, grades, req, qual, remarks, id);
    }

    public void deleteEvaluation() {
        System.out.print("Enter Evaluation ID to delete: "); int id = sc.nextInt(); sc.nextLine();
        String sql = "DELETE FROM tbl_Evaluation WHERE evaluation_id=?";
        conf.deleteRecord(sql, id);
    }

    public void viewStudentApplications() {
        String qry = "SELECT * FROM tbl_Applications";
        String[] headers = {"ID","Student ID","Scholarship ID","Date Submitted","Requirements","Status","School Year"};
        String[] cols = {"app_id","student_id","scholarship_id","date_submitted","requirements","status","school_year"};
        conf.viewRecords(qry, headers, cols);
    }

    public void viewStudentEvaluations() {
        String qry = "SELECT * FROM tbl_Evaluation";
        String[] headers = {"ID","Application ID","Grades","Requirements Checked","Qualification Checked","Remarks"};
        String[] cols = {"evaluation_id","application_id","grades","requirements_checked","qualification_checked","remarks"};
        conf.viewRecords(qry, headers, cols);
    }
}
