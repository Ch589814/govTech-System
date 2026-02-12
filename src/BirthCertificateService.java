public class BirthCertificateService extends Governmentservice {

    private String childName;

    public BirthCertificateService(String childName) {
        super("Birth Certificate Service", 2000.0);
        this.childName = childName;
    }

    @Override
    public String gettservicetype() {
        return " birth certificate service";
    }

    @Override
    public boolean processService() {

        if (childName == null || childName.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public String getServiceDetails() {
        return "Child Name: " + childName;
    }
}
