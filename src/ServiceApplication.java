import java.util.UUID;
import java.time.LocalDate;

public class ServiceApplication {

    private String applicationId;
    private Citizen citizen;
    private Governmentservice service;
    private String status;
    private String submissionDate;

    // Constructor
    public ServiceApplication(Citizen citizen, Governmentservice service) {
        this.applicationId = UUID.randomUUID().toString();
        this.citizen = citizen;
        this.service = service;
        this.status = "PENDING";
        this.submissionDate = LocalDate.now().toString();
    }

    // Getters
    public String getApplicationId() { return applicationId; }
    public String getStatus() { return status; }
    public Governmentservice getService() { return service; }
    public Citizen getCitizen() { return citizen; }
    public String getSubmissionDate() { return submissionDate; }
    public void setStatus(String status) { this.status = status; }

    // Process application
    public void processApplication() throws InvalidStatusException {
        if (!status.equals("PENDING")) throw new InvalidStatusException("Application must be PENDING to process!");
        if (service.processService()) status = "APPROVED";
        else status = "REJECTED";
    }

    // Convert to file string
    public String toFileString() {
        return applicationId + "," +
                citizen.getfullName() + "," +
                citizen.getNationalID() + "," +
                service.getServicename() + "," +
                status + "," +
                submissionDate;
    }

    // STATIC: recreate application from file line
    public static ServiceApplication fromFileString(String line) {
        String[] parts = line.split(",");

        if (parts.length < 6) throw new IllegalArgumentException("Invalid file format");

        String id = parts[0];
        String fullName = parts[1];
        String nationalId = parts[2];
        String serviceName = parts[3];
        String status = parts[4];
        String submissionDate = parts[5];

        Citizen citizen = new Citizen(nationalId, fullName);

        // Recreate the proper service based on serviceName
        Governmentservice service;
        switch (serviceName) {
            case "Driving License Test Service":
                service = new DrivingTestService(fullName, nationalId, "A", 60); // dummy licenseType & score
                break;
            case "Birth Certificate Service":
                service = new BirthCertificateService("ChildName", fullName, "2000-01-01"); // dummy childName & DOB
                break;
            case "Criminal Record Service":
                service = new Criminalrecordservice(fullName, nationalId, false); // dummy record
                break;
            default:
                // fallback
                service = new BirthCertificateService("ChildName", fullName, "2000-01-01");
        }

        ServiceApplication app = new ServiceApplication(citizen, service);
        app.applicationId = id;
        app.status = status;
        app.submissionDate = submissionDate;

        return app;
    }

    @Override
    public String toString() {
        return "Application ID: " + applicationId + "\n" +
                "Citizen Name: " + citizen.getfullName() + "\n" +
                "Citizen ID: " + citizen.getNationalID() + "\n" +
                "Service: " + service.getServicename() + "\n" +
                "Fee: " + service.getFee() + " RWF\n" +
                "Status: " + status + "\n" +
                "Submission Date: " + submissionDate;
    }
}
