package adrien.faouzi.enumeration;

public enum RentalModOfPayment
{
    CASH ("cash"),
    MAESTRO ("maestro"),
    MASTERCARD ("mastercard"),
    ENATTENTE ("en attente");

    /**
     * field
     */
    private String type;

    /**
     * one argument constructor
     */
    RentalModOfPayment(String type) {

        this.type =type;
    }

    /**
     * getter method
     */
    public String getRentalModOfPayement() {
        return type;
    }
}
