package acn.clickstream.dsc.model;

public class Location {
    private String countryName;
    private String provinceName;
    private String cityName;
    private String postal;
    private String state;

    public Location() {
    }

    public Location(String countryName, String provinceName, String cityName, String postal, String state) {

        this.countryName = countryName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.postal = postal;
        this.state = state;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
