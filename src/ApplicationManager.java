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
        if (app == null) {
            throw new IllegalArgumentException("Application cannot be null");
        }
        applications.add(app);
        System.out.println("Application added successfully!");
    }

    @Override
    public ServiceApplication findApplication(String applicationId) throws ApplicationNotFoundException {
        for (ServiceApplication app : applications) {
            if (app.getApplicationId().equals(applicationId)) {
                return app;
            }
        }
        throw new ApplicationNotFoundException("Application with ID " + applicationId + " not found!");
    }

    @Override
    public void processApplication(String applicationId) throws ApplicationNotFoundException, InvalidStatusException {
        ServiceApplication app = findApplication(applicationId);

        if (app == null) {
            throw new ApplicationNotFoundException("Application not found!");
        }

        String status = app.getStatus();
        if (!status.equals("PENDING")) {
            throw new InvalidStatusException("Application status must be PENDING to process!");
        }

        System.out.println("Processing application: " + applicationId);
        app.setStatus("PROCESSING");
        System.out.println("Application is now being processed!");
    }

    @Override
    public void removeApplication(String applicationId) {
        boolean removed = applications.removeIf(app ->
                app.getApplicationId().equals(applicationId));

        if (removed) {
            System.out.println("Application removed successfully!");
        } else {
            System.out.println("Application not found!");
        }
    }

    @Override
    public ServiceApplication searchApplication(String applicationId) {
        for (ServiceApplication app : applications) {
            if (app.getApplicationId().equals(applicationId)) {
                return app;
            }
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
        } else {
            System.out.println("Application not found!");
        }
    }

    @Override
    public void rejectApplication(String applicationId) {
        ServiceApplication app = searchApplication(applicationId);

        if (app != null) {
            app.setStatus("REJECTED");
            System.out.println("Application " + applicationId + " has been REJECTED!");
        } else {
            System.out.println("Application not found!");
        }
    }

    public void displayApplicationDetails(String applicationId) {
        ServiceApplication app = searchApplication(applicationId);

        if (app != null) {
            System.out.println("\n" + app.toString());
        } else {
            System.out.println("Application not found!");
        }
    }

    @Override
    public void displayAllApplications() {
        if (applications.isEmpty()) {
            System.out.println("\nNo applications found!");
            return;
        }

        System.out.println("\n========== ALL APPLICATIONS ==========");
        for (ServiceApplication app : applications) {
            System.out.println(app.toString());
            System.out.println("*****************************************");
        }
    }

    @Override
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;

        for (ServiceApplication app : applications) {
            if (app.getStatus().equals("APPROVED")) {
                // Get the fee from the service
                Governmentservice service = app.getService();
                totalRevenue += service.getFee();
            }
        }

        return totalRevenue;
    }

    public int getTotalApplications() {
        return applications.size();
    }

    public int getApprovedApplications() {
        int count = 0;
        for (ServiceApplication app : applications) {
            if (app.getStatus().equals("APPROVED")) {
                count++;
            }
        }
        return count;
    }

    public int getRejectedApplications() {
        int count = 0;
        for (ServiceApplication app : applications) {
            if (app.getStatus().equals("REJECTED")) {
                count++;
            }
        }
        return count;
    }

    public int getPendingApplications() {
        int count = 0;
        for (ServiceApplication app : applications) {
            if (app.getStatus().equals("PENDING")) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void saveToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (ServiceApplication app : applications) {
                writer.write(app.toFileString());
                writer.newLine();
            }
            System.out.println("All applications saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void loadFromFile() throws IOException {
        applications.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    ServiceApplication app = ServiceApplication.fromFileString(line);
                    applications.add(app);
                }
            }
            System.out.println("All applications loaded from file successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with empty application list.");
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
            throw e;
        }
    }
}