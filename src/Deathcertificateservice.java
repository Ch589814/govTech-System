public class Deathcertificateservice extends Governmentservice {

    private String deceasedName;

    public Deathcertificateservice(String deceasedName) {
        super("Death Certificate Service", 0); // fee example
        this.deceasedName = deceasedName;
    }

    @Override
    public String gettservicetype() {
        return "death certificate service";
    }

    @Override
    public boolean processService() {
        // Must have deceased name
        return deceasedName != null && !deceasedName.isEmpty();
    }

    @Override
    public String getServiceDetails() {
        return "Deceased Name: " + deceasedName;
    }
}
