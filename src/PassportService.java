public class PassportService extends Governmentservice {

    private boolean hasRequiredDocuments;

    public PassportService(boolean hasRequiredDocuments) {
        super("Passport Service", 20000);
        this.hasRequiredDocuments = hasRequiredDocuments;
    }

    @Override
    public String gettservicetype() {
        return " passport service";
    }

    @Override
    public boolean processService() {
        return hasRequiredDocuments;
    }

    @Override
    public String getServiceDetails() {
        return "Documents Verified: " + hasRequiredDocuments;
    }
}
