import java.util.*;

public class Main {

    public static void main(String[] args) {

        ApplicationManager manager = new ApplicationManager();

        // Load previous applications from file
        manager.loadFromFile("applications.txt");

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("******* WELCOME TO DIGITAL GOVERNMENT SERVICES *******");

            System.out.print("Enter your full name: ");
            String fullName = scanner.nextLine();

            System.out.print("Enter your National ID: ");
            String nationalId = scanner.nextLine();

            Citizen citizen = new Citizen(nationalId, fullName);

            // Show dynamic list of available services
            List<String> availableServices = Servicefactory.getAvailableServices();
            System.out.println("\nAvailable Services:");
            for (int i = 0; i < availableServices.size(); i++) {
                System.out.println((i + 1) + ". " + availableServices.get(i));
            }

            System.out.print("Choose a service by typing its number: ");
            String chosenServiceInput = scanner.nextLine();
            int chosenService;
            try {
                chosenService = Integer.parseInt(chosenServiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Exiting.");
                return;
            }

            // Create service based on user input
            Governmentservice service = Servicefactory.createService(chosenService, scanner);

            if (service == null) {
                System.out.println("Invalid service choice. Exiting.");
                return;
            }

            // Create application and add to manager
            ServiceApplication application = new ServiceApplication(citizen, service);
            manager.addApplication(application);

            // Process application automatically
            manager.processApplication(application.getApplicationId());

            // Display **all applications**, including previously loaded ones
            System.out.println("\n............. APPLICATION DETAILS .............");
            manager.displayAllApplications();

            // Display summary including all applications
            System.out.println();
            manager.generateSummaryReport();

            // Optionally display revenue by service type
            manager.calculateRevenueByService();

            // Save all applications back to file
            manager.saveToFile("applications.txt");

            // Generate revenue report
            manager.generateRevenueReportFile("revenue_report.txt");

            System.out.println("\nAll data saved successfully.");
            System.out.println("You can check 'applications.txt' and 'revenue_report.txt' for records.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
