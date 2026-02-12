public class Drivingtestservice extends Governmentservice {

    private int testScore;

    public Drivingtestservice(int testScore) {
        super("Driving Test Service", 10000.0);
        this.testScore = testScore;
    }

    @Override
    public String gettservicetype() {
        return " driving test service";
    }

    @Override
    public boolean processService() {

        if (testScore >= 10) {
            return true;
        }

        return false;
    }

    @Override
    public String getServiceDetails() {
        return "Test Score: " + testScore;
    }
}
