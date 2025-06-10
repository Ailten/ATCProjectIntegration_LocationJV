package adrien.faouzi.enumeration;

public enum ReturnModOfPayment
{
    CASH ("cash"),
    MAESTRO ("maestro"),
    MASTERCARD ("mastercard"),
    AUCUNFRAIS ("aucun frais");

    /**
     * field
     */
    private String type;

    /**
     * one argument constructor
     */
    ReturnModOfPayment(String type) {

        this.type =type;
    }

    /**
     * getter method
     */
    public String getReturnModOfPayment() {
        return type;
    }
}
