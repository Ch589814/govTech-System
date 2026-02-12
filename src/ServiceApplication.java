import java.util.*;

    public class ServiceApplication {

        private String applicationId;
        private Citizen citizen;
        private Governmentservice service;
        private String status; // PENDING, APPROVED, REJECTED

        public ServiceApplication(Citizen citizen, Governmentservice service) {
            this.applicationId = UUID.randomUUID().toString();
            this.citizen = citizen;
            this.service = service;
            this.status = "PENDING";
        }

        public String getApplicationId() {
            return applicationId;
        }

        public String getStatus() {
            return status;
        }

        public Governmentservice getService() {
            return service;
        }

        public Citizen getCitizen() {
            return citizen;
        }

        public void processApplication() throws InvalidStatusException {

            if (!status.equals("PENDING")) {
                throw new InvalidStatusException(null);
            }

            if (service.processService()) {
                status = "APPROVED";
            } else {
                status = "REJECTED";
            }
        }

        @Override
        public String toString() {
            return applicationId + "," +
                    citizen.getfullName() + "," +
                    service.getServicename() + "," +
                    service.getServiceDetails() + "," +
                    status;
        }


        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
