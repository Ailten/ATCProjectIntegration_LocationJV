package adrien.faouzi.utility;

import adrien.faouzi.entities.User;
import adrien.faouzi.managedBeans.I18nBean;
import adrien.faouzi.managedBeans.StoreBean;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityProcessing {
    private UtilityProcessing(){}

    /**
     * function to check regex with data
     * @return
     */
    public static boolean checKDataWithRegex(String data, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }

    /**
     * date and time today method
     * @return
     */
    public static LocalDateTime getDateTimeNow()
    {
        return LocalDateTime.now();
    }


    /**
     * function to convert a localdatetime to a string.
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String localdatetimeInPattern (LocalDateTime localDateTime, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * function to convert a localdatetime to a string format FR.
     * @param localDateTime
     * @return
     */
    public static String localdatetimeInPattern (LocalDateTime localDateTime){
        return UtilityProcessing.localdatetimeInPattern(localDateTime, "dd/MM/yyyy");
    }

    /**
     * function to convert a localdatetime to a date.
     * @param localDateTime
     * @return
     */
    public static Date castLocalDateTimeToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }


    /**
     * function to vonvert a date to a localdatetime
     */
    public static LocalDateTime castDateToLocalDateTime(Date dateConvert)
    {
        return LocalDateTime.ofInstant(dateConvert.toInstant(), ZoneId.systemDefault());
    }


    /**
     * function to debug a string in console.
     * @param message
     * @return
     */
    public static void debug (String message){
        Logger log = Logger.getAnonymousLogger();
        log.info("debug ----------------> "+message);
    }



    public static String floatToStrTwoDigit(float number){
        String outStr = String.valueOf(Math.floor(number*100));
        if(outStr.equals("0") || outStr.equals("0.0"))
            return "0.00";
        return outStr.substring(0, outStr.length()-4)+"."+outStr.substring(outStr.length()-4, outStr.length()-2);
    }

    /**
     * Verification password method
     * @param password
     * @return
     */
    public static boolean checkPassword (String password, User user){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        return result.verified;
    }


    /**
     * Crypt Password method
     * @param password
     * @return
     */
    public static String cryptPassword(String password){
        return BCrypt.withDefaults().hashToString(10,password.toCharArray());
    }
}
