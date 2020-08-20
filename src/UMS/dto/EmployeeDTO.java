package UMS.dto;

public class EmployeeDTO {
    private String empID;
    private String empName;
    private String phoneNumber;
    private String address;
    private String email;
    private String site;

    public EmployeeDTO(String empID, String empName, String phoneNumber, String address, String email, String site) {
        this.empID = empID;
        this.empName = empName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.site = site;
    }

    public EmployeeDTO() {

    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
