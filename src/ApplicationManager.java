import java.util.*;
import java.io.*;

public class ApplicationManager implements ApplicationManagerInterface {

    private final List<ServiceApplication> applications;

    public ApplicationManager() {
        applications = new ArrayList<>();
    }

    @Override
    public void addApplication(ServiceApplication application) {
        applications.add(application);
    }

    @Override
    public ServiceApplication findApplication(String applicationId)
            throws ApplicationNotFoundException {

        for (ServiceApplication app : applications) {
            if (app.getApplicationId().equals(applicationId)) {
                return app;
            }
        }
        throw new ApplicationNotFoundException("Application not found.");
    }

    @Override
    public void processApplication(String applicationId)
            throws ApplicationNotFoundException, InvalidStatusException {

        ServiceApplication app = findApplication(applicationId);
        app.processApplication();
    }

    @Override
    public void displayAllApplications() {
        if (applications.isEmpty()) {
            System.out.println("No applications available.");
            return;
        }
        for (ServiceApplication app : applications) {
            System.out.println(app);
        }
    }

    // ---------------- Revenue Calculations ----------------

    @Override
    public double calculateTotalRevenue() {
        double total = 0;
        for (ServiceApplication app : applications) {
            if (app.getStatus().equals("APPROVED")) {
                total += app.getService().getFee();
            }
        }
        return total;
    }

    public void calculateRevenueByService() {
        Map<String, Double> revenueMap = new HashMap<>();
        for (ServiceApplication app : applications) {
            if (app.getStatus().equals("APPROVED")) {
                String serviceName = app.getService().getServicename();
                double fee = app.getService().getFee();
                revenueMap.put(serviceName, revenueMap.getOrDefault(serviceName, 0.0) + fee);
            }
        }

        System.out.println("\n------ Revenue By Service Type ------");
        for (String service : revenueMap.keySet()) {
            System.out.println(service + " Revenue: " + revenueMap.get(service));
        }
        System.out.println("-------------------------------------");
    }

    public void generateSummaryReport() {
        int approved = 0;
        int rejected = 0;
        int pending = 0;

        for (ServiceApplication app : applications) {
            switch (app.getStatus()) {
                case "APPROVED" -> approved++;
                case "REJECTED" -> rejected++;
                default -> pending++;
            }
        }

        System.out.println("\n********** SUMMARY REPORT *********");
        System.out.println("Approved Applications: " + approved);
        System.out.println("Rejected Applications: " + rejected);
        System.out.println("Pending Applications: " + pending);
        System.out.println("Total Revenue (Approved Only): " + calculateTotalRevenue());
    }



    // Save all applications
    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (ServiceApplication app : applications) {
                writer.write(app.toString());
                writer.newLine();
            }
            System.out.println("Applications saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving applications: " + e.getMessage());
        }
    }

    // Save single application
    public void saveToFileAppend(String fileName, ServiceApplication app) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(app.toString());
            writer.newLine();
            System.out.println("Application appended successfully: " + app.getApplicationId());
        } catch (IOException e) {
            System.out.println("Error saving application: " + e.getMessage());
        }
    }

    // Load applications from file
    public void loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Assuming format: id,citizenName,serviceName,serviceDetail,status
                String[] parts = line.split(",", 5);
                if (parts.length < 5) continue;

                String id = parts[0];
                String citizenName = parts[1];
                String serviceName = parts[2];
                String serviceDetail = parts[3];
                String status = parts[4];

                Citizen citizen = new Citizen(citizenName, "");

                Governmentservice service = null;
                switch (serviceName) {
                    case "Birth Certificate Service" -> service = new BirthCertificateService(serviceDetail);
                    case "Driving Test Service" -> service = new Drivingtestservice(15);
                    // Add other service types here if you have them
                }

                if (service == null) continue;

                ServiceApplication app = new ServiceApplication(citizen, service);
                app.setApplicationId(id);
                app.setStatus(status);

                applications.add(app);

                System.out.println("Loaded Record: " + app);
            }

            System.out.println("Previous applications loaded successfully.");

        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    // Generate revenue report file
    public void generateRevenueReportFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("GOVERNMENT SERVICE REVENUE REPORT:");
            writer.newLine();
            writer.write("Total Revenue (Approved Only): " + calculateTotalRevenue());
            writer.newLine();
            writer.write("-----------------------------------------------");
            writer.newLine();

            Map<String, Double> revenueMap = new HashMap<>();
            for (ServiceApplication app : applications) {
                if (app.getStatus().equals("APPROVED")) {
                    String serviceName = app.getService().getServicename();
                    double fee = app.getService().getFee();
                    revenueMap.put(serviceName, revenueMap.getOrDefault(serviceName, 0.0) + fee);
                }
            }

            for (String service : revenueMap.keySet()) {
                writer.write(service + " Revenue: " + revenueMap.get(service));
                writer.newLine();
            }

            System.out.println("Revenue report saved successfully.");

        } catch (IOException e) {
            System.out.println("Error generating revenue report: " + e.getMessage());
        }
    }
}
