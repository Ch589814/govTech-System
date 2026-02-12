import java.util.*;

public class Servicefactory {

    public static List<String> getAvailableServices() {
        List<String> services = new ArrayList<>();
        services.add("Birth Certificate");
        services.add("Driving Test");
        services.add("Passport");
        services.add("Criminal Record");
        services.add("Death Certificate");
        return services;
    }

    public static Governmentservice createService(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                System.out.print("Enter child's name: ");
                String childName = scanner.nextLine();
                return new BirthCertificateService(childName);

            case 2:
                System.out.print("Enter test score: ");
                int score = Integer.parseInt(scanner.nextLine());
                return new Drivingtestservice(score);

            case 3:
                System.out.print("Has required documents? (yes/no): ");
                boolean docs = scanner.nextLine().equalsIgnoreCase("yes");
                return new PassportService(docs);

            case 4:
                System.out.print("Do you have a criminal record? (yes/no): ");
                boolean hasRecord = scanner.nextLine().equalsIgnoreCase("yes");
                return new Criminalrecordservice(hasRecord);

            case 5:
                System.out.print("Enter deceased name: ");
                String deceasedName = scanner.nextLine();
                return new Deathcertificateservice(deceasedName);

            default:
                System.out.println("Invalid choice!");
                return null;
        }
    }

    // Example main method to test
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> services = getAvailableServices();

        System.out.println("Available Services:");
        for (int i = 0; i < services.size(); i++) {
            System.out.println((i + 1) + ". " + services.get(i));
        }

        System.out.print("Enter your choice (1-5): ");
        int choice = Integer.parseInt(scanner.nextLine());

        Governmentservice service = createService(choice, scanner);
        if (service != null) {
            System.out.println("Service created: " + service.getClass().getSimpleName());
        }

        scanner.close();
    }
}
