public class Citizen {
    private String nationalID;
    private String fullName;
    //constructor

    public Citizen(String nationalID, String  fullName){
        if(nationalID.length()==16){
            this.nationalID = nationalID;
        }
        else {
            System.out.println("invalid National ID");
        }



        this.fullName = fullName;


    }
    public String getNationalID(){
        return nationalID;
    }
    public String getfullName(){
        return  fullName;
    }
    public  void setcitizenInfo(String nationalID, String fullName){
        this.nationalID = nationalID;
        this.fullName = fullName;
    }
    public  String toString(){
        return "your natinal ID :" + nationalID + " your full name:" + fullName;
    }


}