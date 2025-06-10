package adrien.faouzi.enumeration;

import java.util.Arrays;
import java.util.List;

public enum StatusCopy
{
    DISPONIBLE ("disponible"),
    LOUER ("louer"),
    CASSER ("casser");

    /**
     * field
     */
    private String type;

    /**
     * one argument constructor
     */
    StatusCopy(String type) {
        this.type=type;
    }

    /**
     * getter method
     */
    public String getStatusCopy() {
        return type;
    }

    public static StatusCopy strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(StatusCopy.values())
                .filter(s -> s.getStatusCopy().toLowerCase().equals(type))
                .findFirst()
                .orElse(null);
    }

}
