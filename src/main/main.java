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
        String role;
        do {
            System.out.print("Enter Role (Admin/Student): ");
            role = sc.nextLine();
        } while (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Student"));

        String first_name;
        do {
            System.out.print("Enter First Name: ");
            first_name = sc.nextLine();
        } while (first_name.isEmpty());

        String last_name;
        do {
            System.out.print("Enter Last Name: ");
            last_name = sc.nextLine();
        } while (last_name.isEmpty());

        String email;
        do {
            System.out.print("Enter Email: ");
            email = sc.nextLine();
        } while (!isValidEmail(email));

        String password;
        do {
            System.out.print("Enter Password (min 6 chars): ");
            password = sc.nextLine();
        } while (password.length() < 6);

        String status;
        do {
            System.out.print("Enter Status: ");
            status = sc.nextLine();
        } while (status.isEmpty());

        String hashedPassword = config.hashPassword(password);

        String sql = "INSERT INTO tbl_user (role, first_name, last_name, email, password, status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, role, first_name, last_name, email, hashedPassword, status);

        System.out.println("User registered successfully!");
    }

    public void loginUser() {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        String hashedPass = config.hashPassword(pass);
        String sql = "SELECT * FROM tbl_user WHERE email = ? AND password = ?";
        var result = conf.fetchRecords(sql, email, hashedPass);

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

        // Check if exists
        String sqlCheck = "SELECT * FROM tbl_user WHERE user_id=?";
        var res = conf.fetchRecords(sqlCheck, id);
        if (res.isEmpty()) {
            System.out.println("User ID not found! Update cancelled.");
            return;
        }

        String role;
        do { System.out.print("Enter new Role (Admin/Student): "); role = sc.nextLine(); } 
        while (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Student"));

        String first_name;
        do { System.out.print("Enter new First Name: "); first_name = sc.nextLine(); } while (first_name.isEmpty());

        String last_name;
        do { System.out.print("Enter new Last Name: "); last_name = sc.nextLine(); } while (last_name.isEmpty());

        String email;
        do { System.out.print("Enter new Email: "); email = sc.nextLine(); } while (!isValidEmail(email));

        String password;
        do { System.out.print("Enter new Password (min 6 chars): "); password = sc.nextLine(); } while (password.length() < 6);

        String status;
        do { System.out.print("Enter new Status: "); status = sc.nextLine(); } while (status.isEmpty());

        String hashedPassword = config.hashPassword(password);
        String sql = "UPDATE tbl_user SET role=?, first_name=?, last_name=?, email=?, password=?, status=? WHERE user_id=?";
        conf.updateRecord(sql, role, first_name, last_name, email, hashedPassword, status, id);

        System.out.println("User updated successfully!");
    }

    public void deleteUser() {
        System.out.print("Enter User ID to delete: "); int id = sc.nextInt(); sc.nextLine();

        // Check if exists
        String sqlCheck = "SELECT * FROM tbl_user WHERE user_id=?";
        var res = conf.fetchRecords(sqlCheck, id);
        if (res.isEmpty()) {
            System.out.println("User ID not found! Delete cancelled.");
            return;
        }

        String sql = "DELETE FROM tbl_user WHERE user_id=?";
        conf.deleteRecord(sql, id);
        System.out.println("User deleted successfully!");
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
        String name, criteria, benefits, status;
        do { System.out.print("Enter Scholarship Name: "); name = sc.nextLine(); } while (name.isEmpty());
        do { System.out.print("Enter Criteria: "); criteria = sc.nextLine(); } while (criteria.isEmpty());
        do { System.out.print("Enter Benefits: "); benefits = sc.nextLine(); } while (benefits.isEmpty());
        do { System.out.print("Enter Status: "); status = sc.nextLine(); } while (status.isEmpty());

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

        String sqlCheck = "SELECT * FROM tbl_Scholarship WHERE scholarship_id=?";
        var res = conf.fetchRecords(sqlCheck, id);
        if (res.isEmpty()) {
            System.out.println("Scholarship ID not found! Update cancelled.");
            return;
        }

        String name, criteria, benefits, status;
        do { System.out.print("Enter new Name: "); name = sc.nextLine(); } while (name.isEmpty());
        do { System.out.print("Enter new Criteria: "); criteria = sc.nextLine(); } while (criteria.isEmpty());
        do { System.out.print("Enter new Benefits: "); benefits = sc.nextLine(); } while (benefits.isEmpty());
        do { System.out.print("Enter new Status: "); status = sc.nextLine(); } while (status.isEmpty());

        String sql = "UPDATE tbl_Scholarship SET scholarship_name=?, criteria=?, benefits=?, status=? WHERE scholarship_id=?";
        conf.updateRecord(sql, name, criteria, benefits, status, id);
        System.out.println("Scholarship updated successfully!");
    }

    public void deleteScholarship() {
        System.out.print("Enter Scholarship ID to delete: "); int id = sc.nextInt(); sc.nextLine();

        String sqlCheck = "SELECT * FROM tbl_Scholarship WHERE scholarship_id=?";
        var res = conf.fetchRecords(sqlCheck, id);
        if (res.isEmpty()) {
            System.out.println("Scholarship ID not found! Delete cancelled.");
            return;
        }

        String sql = "DELETE FROM tbl_Scholarship WHERE scholarship_id=?";
        conf.deleteRecord(sql, id);
        System.out.println("Scholarship deleted successfully!");
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
        int student_id = (int) loggedInUser.get("user_id"); // auto set if student
        System.out.print("Enter Scholarship ID: "); int scholarship_id = sc.nextInt(); sc.nextLine();

        String date_submitted;
        do {
            System.out.print("Enter Date Submitted (YYYY-MM-DD): ");
            date_submitted = sc.nextLine();
        } while (!isValidDate(date_submitted));

        System.out.print("Enter Requirements: "); String requirements = sc.nextLine();
        System.out.print("Enter Status: "); String status = sc.nextLine();
        System.out.print("Enter School Year: "); int school_year = sc.nextInt(); sc.nextLine();

        String sql = "INSERT INTO tbl_Applications (student_id, scholarship_id, date_submitted, requirements, status, school_year) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, student_id, scholarship_id, date_submitted, requirements, status, school_year);
        System.out.println("Application added successfully!");
    }

    public void viewApplications() {
        String qry = "SELECT * FROM tbl_Applications";
        String[] headers = {"ID","Student ID","Scholarship ID","Date Submitted","Requirements","Status","School Year"};
        String[] cols = {"app_id","student_id","scholarship_id","date_submitted","requirements","status","school_year"};
        conf.viewRecords(qry, headers, cols);
    }

    public void updateApplication() {
        System.out.print("Enter Application ID to update: "); int id = sc.nextInt(); sc.nextLine();

        String sqlCheck = "SELECT * FROM tbl_Applications WHERE app_id=?";
        var res = conf.fetchRecords(sqlCheck, id);
        if (res.isEmpty()) {
            System.out.println("Application ID not found! Update cancelled.");
            return;
        }

        System.out.print("Enter new Requirements: "); String req = sc.nextLine();
        System.out.print("Enter new Status: "); String status = sc.nextLine();
        System.out.print("Enter new School Year: "); int sy = sc.nextInt(); sc.nextLine();

        String sql = "UPDATE tbl_Applications SET requirements=?, status=?, school_year=? WHERE app_id=?";
        conf.updateRecord(sql, req, status, sy, id);
        System.out.println("Application updated successfully!");
    }

    public void deleteApplication() {
        System.out.print("Enter Application ID to delete: "); int id = sc.nextInt(); sc.nextLine();

        String sqlCheck = "SELECT * FROM tbl_Applications WHERE app_id=?";
        var res = conf.fetchRecords(sqlCheck, id);
        if (res.isEmpty()) {
            System.out.println("Application ID not found! Delete cancelled.");
            return;
        }

        String sql = "DELETE FROM tbl_Applications WHERE app_id=?";
        conf.deleteRecord(sql, id);
        System.out.println("Application deleted successfully!");
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
        System.out.println("Evaluation added successfully!");
    }

    public void viewEvaluations() {
        String qry = "SELECT * FROM tbl_Evaluation";
        String[] headers = {"ID","Application ID","Grades","Requirements Checked","Qualification Checked","Remarks"};
        String[] cols = {"evaluation_id","application_id","grades","requirements_checked","qualification_checked","remarks"};
        conf.viewRecords(qry, headers, cols);
    }

    public void updateEvaluation() {
        System.out.print("Enter Evaluation ID to update: "); int id = sc.nextInt(); sc.nextLine();

        String sqlCheck = "SELECT * FROM tbl_Evaluation WHERE evaluation_id=?";
        var res = conf.fetchRecords(sqlCheck, id);
        if (res.isEmpty()) {
            System.out.println("Evaluation ID not found! Update cancelled.");
            return;
        }

        System.out.print("Enter new Grades: "); int grades = sc.nextInt(); sc.nextLine();
        System.out.print("Enter new Requirements Checked: "); String req = sc.nextLine();
        System.out.print("Enter new Qualification Checked: "); String qual = sc.nextLine();
        System.out.print("Enter new Remarks: "); String remarks = sc.nextLine();

        String sql = "UPDATE tbl_Evaluation SET grades=?, requirements_checked=?, qualification_checked=?, remarks=? WHERE evaluation_id=?";
        conf.updateRecord(sql, grades, req, qual, remarks, id);
        System.out.println("Evaluation updated successfully!");
    }

    public void deleteEvaluation() {
        System.out.print("Enter Evaluation ID to delete: "); int id = sc.nextInt(); sc.nextLine();

        String sqlCheck = "SELECT * FROM tbl_Evaluation WHERE evaluation_id=?";
        var res = conf.fetchRecords(sqlCheck, id);
        if (res.isEmpty()) {
            System.out.println("Evaluation ID not found! Delete cancelled.");
            return;
        }

        String sql = "DELETE FROM tbl_Evaluation WHERE evaluation_id=?";
        conf.deleteRecord(sql, id);
        System.out.println("Evaluation deleted successfully!");
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


    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
