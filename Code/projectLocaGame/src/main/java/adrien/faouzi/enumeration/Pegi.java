package adrien.faouzi.enumeration;


import java.util.Arrays;
import java.util.List;

public enum Pegi
{
    A_TROIS ("3"),
    B_SEPT ("7"),
    C_DOUZE ("12"),
    D_SEIZE ("16"),
    E_DIXHUIT ("18");

    /**
     * field
     */
    private String type;

    /**
     * one argument constructor
     */
    Pegi(String type) {
        this.type=type;
    }

    /**
     * getter method
     */
    public String getPegi() {
        return type;
    }

    //public int getPegiInt(){
    //    return Integer.parseInt(type);
    //}

    public static Pegi intToEnum(int typeNum){
        if(typeNum==0)
            return null;
        String typeStr = String.valueOf(typeNum);
        return Arrays.stream(Pegi.values())
                .filter(s->s.getPegi().equals(typeStr))
                .findFirst()
                .orElse(null);
    }


    //public static List<Pegi> getAllPegi(){
    //    return Arrays.asList(Pegi.values());
    //}
}
