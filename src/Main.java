import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ApplicationManager manager = new ApplicationManager();
        Scanner scanner = new Scanner(System.in);

        // Load previous applications
        try {
            manager.loadFromFile();
        } catch (Exception e) {
            System.out.println("Error loading applications: " + e.getMessage());
        }

        System.out.println("******* WELCOME TO DIGITAL GOVERNMENT SERVICES *******");

        boolean continueUsing = true;

        while (continueUsing) {
            try {
                System.out.print("\nEnter your full name: ");
                String fullName = scanner.nextLine();

                System.out.print("Enter your National ID: ");
                String nationalId = scanner.nextLine();

                Citizen citizen = new Citizen(nationalId, fullName);

                System.out.println("\nAvailable Services:");
                System.out.println("1. Driving License Test");
                System.out.println("2. Birth Certificate");
                System.out.println("3. Criminal Record Check");
                System.out.println("0. Cancel");

                System.out.print("Choose a service: ");
                String choiceInput = scanner.nextLine();

                if (choiceInput.equals("0")) {
                    System.out.println("Exiting service selection.");
                    break;
                }

                int choice = Integer.parseInt(choiceInput);
                Governmentservice service = null;

                switch (choice) {
                    case 1:
                        System.out.print("Enter license type: ");
                        String licenseType = scanner.nextLine();

                        System.out.print("Enter test score (0-100): ");
                        int score = Integer.parseInt(scanner.nextLine());

                        service = new DrivingTestService(fullName, nationalId, licenseType, score);
                        break;

                    case 2:
                        System.out.print("Enter child name: ");
                        String childName = scanner.nextLine();

                        System.out.print("Enter date of birth (YYYY-MM-DD): ");
                        String dob = scanner.nextLine();

                        service = new BirthCertificateService(childName, fullName, dob);
                        break;

                    case 3:
                        System.out.print("Does the applicant have a criminal record? (true/false): ");
                        boolean hasRecord = Boolean.parseBoolean(scanner.nextLine());

                        service = new Criminalrecordservice(fullName, nationalId, hasRecord);
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        continue;
                }

                System.out.print("Do you want to proceed? (Y/N): ");
                String proceed = scanner.nextLine().trim().toUpperCase();
                if (!proceed.equals("Y")) {
                    System.out.println("Application cancelled.");
                    continue;
                }

                // Create application and add to manager
                ServiceApplication app = new ServiceApplication(citizen, service);
                manager.addApplication(app);

                // Process immediately
                manager.processApplication(app.getApplicationId());

                // Show status & revenue summary
                System.out.println("\n--- APPLICATION STATUS & REVENUE SUMMARY ---");
                manager.displayApplicationDetails(app.getApplicationId());
                manager.calculateRevenueByService();

                System.out.print("\nDo you want to apply for another service? (Y/N): ");
                String again = scanner.nextLine().trim().toUpperCase();
                if (!again.equals("Y")) {
                    continueUsing = false;
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Display all applications
        manager.displayAllApplications();

        // Show statistics
        System.out.println("\n ********** APPLICATION STATISTICS *******************");
        System.out.println("Total Applications: " + manager.getTotalApplications());
        System.out.println("Approved Applications: " + manager.getApprovedApplications());
        System.out.println("Rejected Applications: " + manager.getRejectedApplications());
        System.out.println("Pending Applications: " + manager.getPendingApplications());

        // Show revenue summary
        manager.calculateRevenueByService();

        // Save data to files
        try {
            manager.saveToFile();
            manager.generateRevenueReportFile("revenue_report.txt");
            System.out.println("\nData saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }

        System.out.println("\nThank you for using our Digital Government Services.");
        scanner.close();
    }
}
