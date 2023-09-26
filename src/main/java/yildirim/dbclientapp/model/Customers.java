package yildirim.dbclientapp.model;

public class Customers {
    private String divisionName;
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionID;
    private int countryID;

    /**
     * @param customerID  customer id
     * @param customerName customer name
     * @param divisionID division id
     * @param countryID country id
     * @param customerAddress customer address
     * @param customerPhoneNumber customer phone number
     * @param customerPostalCode customer zip code
     * @param divisionName division name
     */

    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber,int divisionID, int countryID, String divisionName) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    /**
     * @return country id
     * */

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * @return customerID
     */
    public Integer getCustomerID() {

        return customerID;
    }

    /**
     * @param customerID customer id
     */
    public void setCustomerID(Integer customerID) {

        this.customerID = customerID;
    }

    /**
     * @return customerName
     */
    public String getCustomerName() {

        return customerName;
    }

    /**
     * @param customerName customer name
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    /**
     * @return customerAddress
     */
    public String getCustomerAddress() {

        return customerAddress;
    }

    /**
     * @param address address
     */
    public void setCustomerAddress(String address) {

        this.customerAddress = address;
    }

    /**
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() {

        return customerPostalCode;
    }

    /**
     * @param postalCode postal code
     */
    public void setCustomerPostalCode(String postalCode) {

        this.customerPostalCode = postalCode;
    }

    /**
     * @return customerPhoneNumber
     */
    public String getCustomerPhone() {

        return customerPhoneNumber;
    }

    /**
     * @param phone phone number
     */
    public void setCustomerPhone(String phone) {

        this.customerPhoneNumber = phone;
    }

    /**
     * @return divisionID
     */
    public Integer getCustomerDivisionID() {

        return divisionID;
    }

    /**
     * @return divisionID
     */
    public String getDivisionName() {

        return divisionName;
    }

    /**
     * @param divisionID division id
     */
    public void setCustomerDivisionID(Integer divisionID) {

        this.divisionID = divisionID;
    }

    @Override
    public String toString(){
        return customerName;
    }

}
