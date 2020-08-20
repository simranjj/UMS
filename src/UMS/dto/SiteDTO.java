package UMS.dto;

public class SiteDTO {

    private String site;
    private String address;

    public SiteDTO(String site, String address) {
        this.site = site;
        this.address = address;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
