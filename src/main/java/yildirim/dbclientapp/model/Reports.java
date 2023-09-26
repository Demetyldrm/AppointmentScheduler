package yildirim.dbclientapp.model;

public class Reports {
    private int countryCount;
    private String countryName;
    public String appointmentMonth;
    public int appointmentTotal;

    /**
     * @param countryName country name
     * @param countryCount country count
     */
    public Reports(String countryName, int countryCount) {
        this.countryCount = countryCount;
        this.countryName = countryName;

    }


    /**
     * Returns country name for custom report.
     *
     * @return countryName
     */
    public String getCountryName() {

        return countryName;
    }

    /**
     * Total for each country.
     *
     * @return countryCount
     */
    public int getCountryCount() {

        return countryCount;
    }

}
