public class Criminalrecordservice extends Governmentservice {

    private boolean hasRecord;

    public Criminalrecordservice(boolean hasRecord) {
        super("Criminal Record Service", 1200.0);
        this.hasRecord = hasRecord;
    }



    @Override
    public String gettservicetype() {
        return "Criminal Record Service";
    }

    @Override
    public boolean processService() {
        // Approve only if no criminal record
        return !hasRecord;
    }

    @Override
    public String getServiceDetails() {
        return "Has Criminal Record: " + (hasRecord ? "Yes" : "No");
    }
}
