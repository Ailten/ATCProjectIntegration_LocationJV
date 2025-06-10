package adrien.faouzi.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MultiPlayer
{
    SOLO ("solo"),
    MULTI ("multi"),
    SOLOMULTI ("solo-multi");

    /**
     * field
     */
    private String type;

    /**
     * one argument constructor
     */
    MultiPlayer(String type) {
        this.type=type;
    }

    /**
     * getter method
     */
    public String getMultiPlayer() {
        return type;
    }



    //public static List<MultiPlayer> getAllMultiPlayer(){
    //    return Arrays.asList(MultiPlayer.values());
    //}

    public static MultiPlayer strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(MultiPlayer.values())
                .filter(mp->mp.getMultiPlayer().equals(type))
                .findFirst()
                .orElse(null);
    }
}