package util;

import bean.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date strToDate(String dateStr, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }
    public static String dateToStr(Date date,String format){
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}
