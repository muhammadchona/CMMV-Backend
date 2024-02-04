package mz.org.fgh.cmmv.backend.utilities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Utilities {

    private static Utilities instance;

    public static final String END_DAY_TIME="23:59:59";

    private Utilities() {
    }

    public static Utilities getInstance(){
        if (instance == null){
            instance = new Utilities();
        }
        return instance;
    }

    public static boolean stringHasValue(String string){
        return string != null && !string.isEmpty() && string.trim().length() > 0;
    }

    public static String ensureXCaractersOnNumber(long number, int x){
        String formatedNumber = "";
        int numberOfCharacterToIncrise = 0;

        formatedNumber = number + "";

        numberOfCharacterToIncrise = x - formatedNumber.length();

        for(int i = 0; i < numberOfCharacterToIncrise; i++) formatedNumber = "0" + formatedNumber;

        return formatedNumber;
    }

    public static String concatStrings(String currentString, String toConcant, String scapeStr){
        if (!stringHasValue(currentString)) return toConcant;

        if (!stringHasValue(toConcant)) return currentString;

        return currentString + scapeStr+ toConcant;
    }

    public static boolean isStringIn(String value, String... inValues){
        if (inValues == null || value == null) return false;

        for (String str : inValues){
            if (value.equals(str)) return true;
        }

        return false;
    }

    public static boolean listHasElements(ArrayList<?> list){
        return list != null && !list.isEmpty() && list.size() > 0;
    }

    public static String parseToJSON(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(object);
    }

    public static Date getCurrentDate(){
        return Calendar.getInstance().getTime();
    }

    public static Date getDateOfForwardDays(Date date, int days)
    {
        if (days < 0)
        {
            throw new IllegalArgumentException(
                    "The days must be a positive value");
        }

        return DateUtils.addDays(date, days);
    }

    public static Date getDateOfPreviousDays(Date date, int days)
    {
        if (days < 0)
        {
            throw new IllegalArgumentException(
                    "The days must be a positive value");
        }

        return DateUtils.addDays(date, (-1) * days);
    }

    public static String parseDateToYYYYMMDDString(Date toParse){
        SimpleDateFormat datetemp = new SimpleDateFormat("dd-MM-yyyy");
        String data = datetemp.format(toParse);
        return data;
    }

    public static Date getDateToYYYYMMDDString(String toParse){
        SimpleDateFormat datetemp = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return datetemp.parse(toParse);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /** Returns the given date with time set to the end of the day */
    public static Date getDateEnding(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }


    public static Date getDateBegining(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static int extractMonthInDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    public static int extractYearInDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }


}
