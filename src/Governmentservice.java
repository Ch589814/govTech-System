public abstract class  Governmentservice {
    protected String servicename;
    protected double fee;
    public Governmentservice(String servicename, double fee){
        this.servicename = servicename;
        this.fee = fee;

    }
    public  String getServicename(){
        return  servicename;
    }
    public  double getFee(){
        return  fee;
    }
    //abstract methods
    public  abstract  String gettservicetype();
    public abstract boolean processService();

    public abstract String getServiceDetails();
}
