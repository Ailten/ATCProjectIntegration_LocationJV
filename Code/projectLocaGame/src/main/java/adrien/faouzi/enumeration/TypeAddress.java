package adrien.faouzi.enumeration;

public enum TypeAddress
{
    LIVRAISON ("livraison"),
    FACTURATION ("facturation");

    /**
     * field
     */
    private String type;

    /**
     * one argument constructor
     */
    TypeAddress(String type) {

        this.type =type;
    }

    /**
     * getter method
     */
    public String getTypeAddress() {
        return type;
    }
}
