public interface ApplicationManagerInterface {

    void addApplication(ServiceApplication application);

    ServiceApplication findApplication(String applicationId)
            throws ApplicationNotFoundException;

    void processApplication(String applicationId)
            throws ApplicationNotFoundException, InvalidStatusException;

    void displayAllApplications();

    double calculateTotalRevenue();
}
