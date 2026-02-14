public class Criminalrecordservice extends Governmentservice {

    private final boolean hasRecord;
    private final String applicantName;
    private final String applicantID;
    private final String requestDate;

    public Criminalrecordservice(String applicantName, String applicantID, boolean hasRecord) {
        super("Criminal Record Service", 1200.0);
        this.applicantName = applicantName;
        this.applicantID = applicantID;
        this.hasRecord = hasRecord;
        this.requestDate = java.time.LocalDate.now().toString();
    }

    @Override
    public String getservicetype() {
        return "CRIMINAL_RECORD";
    }

    @Override
    public boolean processService() {
        // Validate applicant name
        if (applicantName == null || applicantName.isEmpty()) {
            System.out.println("Error: Applicant name is required");
            return false;
        }

        // Validate applicant ID
        if (applicantID == null || applicantID.isEmpty()) {
            System.out.println("Error: Applicant ID is required");
            return false;
        }

        // Process the criminal record check
        System.out.println("Processing Criminal Record Check for: " + applicantName);

        if (hasRecord) {
            System.out.println("Warning: Criminal record found for this applicant");
        } else {
            System.out.println("No criminal record found for this applicant");
        }

        System.out.println("Application submitted successfully!");
        return true;
    }

    @Override
    public String getServiceDetails() {
        String recordStatus = hasRecord ? "Yes - Record Found" : "No - Clean Record";

        return "Service: " + servicename+ "\n" +
                "Type: " + getservicetype() + "\n" +
                "Fee: " + fee + " RWF\n" +
                "Applicant Name: " + applicantName + "\n" +
                "Applicant ID: " + applicantID + "\n" +
                "Criminal Record: " + recordStatus + "\n" +
                "Request Date: " + requestDate;
    }

    // Getters
    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public boolean hasRecord() {
        return hasRecord;
    }

    public String getRequestDate() {
        return requestDate;
    }
}