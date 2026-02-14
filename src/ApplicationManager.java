import java.util.*;
import java.io.*;

public class ApplicationManager implements ApplicationManagerInterface {

    private final List<ServiceApplication> applications;
    private final String filename = "applications.txt";

    public ApplicationManager() {
        applications = new ArrayList<>();
    }

    @Override
    public void addApplication(ServiceApplication app) {
        if (app == null) throw new IllegalArgumentException("Application cannot be null");
        applications.add(app);
        System.out.println("Application added successfully!");
    }

    @Override
    public ServiceApplication findApplication(String applicationId) throws ApplicationNotFoundException {
        for (ServiceApplication app : applications) {
            if (app.getApplicationId().equals(applicationId)) return app;
        }
        throw new ApplicationNotFoundException("Application with ID " + applicationId + " not found!");
    }

    @Override
    public void processApplication(String applicationId) throws ApplicationNotFoundException, InvalidStatusException {
        ServiceApplication app = findApplication(applicationId);

        if (!app.getStatus().equals("PENDING")) {
            throw new InvalidStatusException("Application status must be PENDING to process!");
        }

        System.out.println("Processing application: " + applicationId);

        // Actually process the service
        boolean result = app.getService().processService();

        // Set status based on service result
        if (result) {
            app.setStatus("APPROVED");
            System.out.println("Application APPROVED!");
        } else {
            app.setStatus("REJECTED");
            System.out.println("Application REJECTED!");
        }
    }


    @Override
    public void removeApplication(String applicationId) {
        boolean removed = applications.removeIf(app -> app.getApplicationId().equals(applicationId));
        System.out.println(removed ? "Application removed successfully!" : "Application not found!");
    }

    @Override
    public ServiceApplication searchApplication(String applicationId) {
        for (ServiceApplication app : applications) {
            if (app.getApplicationId().equals(applicationId)) return app;
        }
        return null;
    }

    @Override
    public List<ServiceApplication> getAllApplications() {
        return new ArrayList<>(applications);
    }

    @Override
    public void approveApplication(String applicationId) {
        ServiceApplication app = searchApplication(applicationId);
        if (app != null) {
            app.setStatus("APPROVED");
            System.out.println("Application " + applicationId + " has been APPROVED!");
        } else System.out.println("Application not found!");
    }

    @Override
    public void rejectApplication(String applicationId) {
        ServiceApplication app = searchApplication(applicationId);
        if (app != null) {
            app.setStatus("REJECTED");
            System.out.println("Application " + applicationId + " has been REJECTED!");
        } else System.out.println("Application not found!");
    }

    @Override
    public void displayApplicationDetails(String applicationId) {
        ServiceApplication app = searchApplication(applicationId);
        if (app != null) System.out.println("\n" + app);
        else System.out.println("Application not found!");
    }

    @Override
    public void displayAllApplications() {
        if (applications.isEmpty()) {
            System.out.println("\nNo applications found!");
            return;
        }
        System.out.println("\n========== ALL APPLICATIONS ==========");
        for (ServiceApplication app : applications) {
            System.out.println(app);
            System.out.println("*****************************************");
        }
    }

    @Override
    public double calculateTotalRevenue() {
        return applications.stream()
                .filter(a -> a.getStatus().equals("APPROVED"))
                .mapToDouble(a -> a.getService().getFee())
                .sum();
    }

    // Revenue per service
    public void calculateRevenueByService() {
        Map<String, Double> revenueMap = new HashMap<>();
        for (ServiceApplication app : applications) {
            if (app.getStatus().equals("APPROVED")) {
                String serviceName = app.getService().getServicename();
                revenueMap.put(serviceName, revenueMap.getOrDefault(serviceName, 0.0) + app.getService().getFee());
            }
        }

        System.out.println("\n**************** REVENUE SUMMARY ****************");
        double totalRevenue = 0.0;
        for (Map.Entry<String, Double> entry : revenueMap.entrySet()) {
            System.out.println(entry.getKey() + " Revenue: " + entry.getValue() + " RWF");
            totalRevenue += entry.getValue();
        }
        System.out.println("Total Revenue (Approved Only): " + totalRevenue + " RWF");
    }

    // Save revenue to file
    public void generateRevenueReportFile(String reportFilename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilename))) {
            Map<String, Double> revenueMap = new HashMap<>();
            double totalRevenue = 0.0;
            for (ServiceApplication app : applications) {
                if (app.getStatus().equals("APPROVED")) {
                    String serviceName = app.getService().getServicename();
                    revenueMap.put(serviceName, revenueMap.getOrDefault(serviceName, 0.0) + app.getService().getFee());
                }
            }

            writer.write("GOVERNMENT SERVICE REVENUE REPORT:\n");
            for (Map.Entry<String, Double> entry : revenueMap.entrySet()) {
                writer.write(entry.getKey() + " Revenue: " + entry.getValue() + "\n");
                totalRevenue += entry.getValue();
            }
            writer.write("-----------------------------------------------\n");
            writer.write("Total Revenue (Approved Only): " + totalRevenue + "\n");

            System.out.println("Revenue report saved to " + reportFilename);

        } catch (IOException e) {
            System.out.println("Error saving revenue report: " + e.getMessage());
        }
    }

    @Override
    public void saveToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (ServiceApplication app : applications) {
                writer.write(app.toFileString());
                writer.newLine();
            }
            System.out.println("All applications saved to file successfully!");
        }
    }

    @Override
    public void loadFromFile() throws IOException {
        applications.clear();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found. Starting with empty application list.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                // Use correct static method in ServiceApplication
                ServiceApplication app = ServiceApplication.fromFileString(line);
                applications.add(app);
            }
        }
        System.out.println("All applications loaded from file successfully!");
    }

    // Statistics
    public int getTotalApplications() {
        return applications.size();
    }

    public int getApprovedApplications() {
        return (int) applications.stream().filter(a -> a.getStatus().equals("APPROVED")).count();
    }

    public int getRejectedApplications() {
        return (int) applications.stream().filter(a -> a.getStatus().equals("REJECTED")).count();
    }

    public int getPendingApplications() {
        return (int) applications.stream().filter(a -> a.getStatus().equals("PENDING")).count();
    }
}
