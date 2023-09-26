package yildirim.dbclientapp.model;

public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;
    public int country_ID;

    /**
     * @param divisionName  division name
     * @param divisionID division id
     * @param countryId country id
     */

    public FirstLevelDivisions(int divisionID, String divisionName, int countryId) {
        this.divisionID=divisionID;
        this.divisionName=divisionName;
        this.country_ID=countryId;
    }

    /**
     * @param divisionID division id
     * @param country_ID country id
     * @param divisionName division name
     */
    public void firstLevelDivision(int divisionID, String divisionName, int country_ID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.country_ID = country_ID;
    }

    /**
     * @return divisionID
     */
    public int getDivisionID() {

        return divisionID;
    }

    /**
     * @return divisionName
     */
    public String getDivision() {

        return divisionName;
    }

    /**
     * @return country_ID
     */
    public int getCountry_ID() {

        return country_ID;
    }

    @Override
    public String toString(){
        return divisionName;
    }

}
