public class BirthCertificateService extends Governmentservice {

    private final String childName;
    private final String parentName;
    private final String dateOfBirth;

    public BirthCertificateService(String childName, String parentName, String dateOfBirth) {
        super("Birth Certificate Service", 2000.0);
        this.childName = childName;
        this.parentName = parentName;
        this.dateOfBirth = dateOfBirth;
    }



    @Override
    public String getservicetype() {
        return "BIRTH_CERTIFICATE";
    }

    @Override
    public boolean processService() {
        // Check if child name is valid
        if (childName == null || childName.isEmpty()) {
            System.out.println("Error: Child name is required");
            return false;
        }

        // Check if parent name is valid
        if (parentName == null || parentName.isEmpty()) {
            System.out.println("Error: Parent name is required");
            return false;
        }

        // If all validations pass, process the service
        System.out.println("Processing Birth Certificate for: " + childName);
        System.out.println("Application submitted successfully!");
        return true;
    }

    @Override
    public String getServiceDetails() {
        return "Service: " + servicename + "\n" +
                "Type: " + getservicetype() + "\n" +
                "Fee: " + fee + " RWF\n" +
                "Child Name: " + childName + "\n" +
                "Parent Name: " + parentName + "\n" +
                "Date of Birth: " + dateOfBirth;
    }

    // Getters
    public String getChildName() {
        return childName;
    }

    public String getParentName() {
        return parentName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}