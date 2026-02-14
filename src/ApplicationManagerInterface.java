import java.io.IOException;
import java.util.List;

public interface ApplicationManagerInterface {

    // Add and Remove Applications
    void addApplication(ServiceApplication application);

    ServiceApplication findApplication(String applicationId) throws ApplicationNotFoundException;

    void processApplication(String applicationId) throws ApplicationNotFoundException, InvalidStatusException;

    void removeApplication(String applicationId);

    // Search and Retrieve Applications
    ServiceApplication searchApplication(String applicationId);

    List<ServiceApplication> getAllApplications();

    // Display Applications
    void displayAllApplications();

    void displayApplicationDetails(String applicationId);

    // Approve and Reject Applications
    void approveApplication(String applicationId);

    void rejectApplication(String applicationId);

    // Get Application Statistics
    int getTotalApplications();

    int getApprovedApplications();

    int getRejectedApplications();

    int getPendingApplications();

    // Calculate Revenue
    double calculateTotalRevenue();

    // File Operations
    void saveToFile() throws IOException;

    void loadFromFile() throws IOException;
}