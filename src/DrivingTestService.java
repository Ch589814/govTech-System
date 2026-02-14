
public class DrivingTestService extends Governmentservice {

    private final int testScore;
    private final String applicantName;
    private final String applicantID;
    private final String licenseType;
    private final String testDate;
    private String testStatus; // PASSED, FAILED, PENDING

    public DrivingTestService(String applicantName, String applicantID, String licenseType, int testScore) {
        super("Driving License Test Service", 10000.0);
        this.applicantName = applicantName;
        this.applicantID = applicantID;
        this.licenseType = licenseType;
        this.testScore = testScore;
        this.testDate = java.time.LocalDate.now().toString();
        this.testStatus = "PENDING";
    }

    @Override
    public String getservicetype() {
        return "DRIVING_LICENSE";
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

        // Validate license type
        if (licenseType == null || licenseType.isEmpty()) {
            System.out.println("Error: License type is required");
            return false;
        }

        // Validate test score (0-100)
        if (testScore < 0 || testScore > 100) {
            System.out.println("Error: Test score must be between 0 and 100");
            return false;
        }

        // Determine if applicant passed
        if (testScore >= 60) {
            testStatus = "PASSED";
            System.out.println("Congratulations! " + applicantName + " PASSED the driving test!");
            System.out.println("Score: " + testScore + "/100");
        } else {
            testStatus = "FAILED";
            System.out.println("Unfortunately, " + applicantName + " FAILED the driving test");
            System.out.println("Score: " + testScore + "/100");
            System.out.println("You can retake the test after 30 days");
        }

        System.out.println("Application processed successfully!");
        return true;
    }

    @Override
    public String getServiceDetails() {
        return "Service: " + servicename + "\n" +
                "Type: " + getservicetype() + "\n" +
                "Fee: " + fee + " RWF\n" +
                "Applicant Name: " + applicantName + "\n" +
                "Applicant ID: " + applicantID + "\n" +
                "License Type: " + licenseType + "\n" +
                "Test Score: " + testScore + "/100\n" +
                "Test Status: " + testStatus + "\n" +
                "Test Date: " + testDate;
    }

    // Getters
    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public int getTestScore() {
        return testScore;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public String getTestDate() {
        return testDate;
    }
}