package bo.com.tigo.comodato.shared.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
    public static final int ONE_DAY_IN_HOUR = 24;
    public static final long DAY_LONG = 3600 * ONE_DAY_IN_HOUR * 1000;
    public final static String SIMPLE_DAY_FORMAT_MOTOR = "dd-MM-yyyy";
    public final static String SIMPLE_DAY_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final DateFormat sdf = new SimpleDateFormat(SIMPLE_DAY_FORMAT_MOTOR);

    public static boolean validateSimpleDayFormat(String date) {
        boolean valid = false;
        if (date.matches("^([0-2][0-9]|(3)[0-1])(\\-)(((0)[0-9])|((1)[0-2]))(\\-)\\d{4}$"))
            valid = true;
        return valid;
    }

    public static String formatDate(XMLGregorianCalendar date) {
        Calendar c = date.toGregorianCalendar();
        sdf.setTimeZone(c.getTimeZone());
        return sdf.format(c.getTime());
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar(String dateStr)
            throws ParseException, DatatypeConfigurationException {
        Date date = sdf.parse(dateStr);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        return xmlCalendar;
    }

    public static String fromDate(final Date date) {
        String formatted = date != null ? new SimpleDateFormat(SIMPLE_DAY_FORMAT_MOTOR).format(date) : "";
        return formatted.toString();
    }

    public static String fromDate(final Date date, String format) {
        String formatted = new SimpleDateFormat(format).format(date);
        return formatted.toString();
    }

    public static Date toDate(final String string) throws ParseException {
        return toDate(string, SIMPLE_DAY_FORMAT_MOTOR);
    }

    public static Date toDate(final String string, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(string);
        return date;
    }

    public static String now() {
        return fromDate(new Date());
    }

    public static String now(String format) {
        return fromDate(new Date(), format);
    }

    public static Date getDateWFirstHms(long datelong) {
        Date date = new Date(setHmsFirst(datelong).getTimeInMillis());
        return date;
    }

    public static Date getDateWLastHms(long datelong) {
        Date date = new Date(setHmsLast(datelong).getTimeInMillis());
        return date;
    }

    public static Calendar setHmsFirst(long dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        setCalendarHmsToFirst(calendar);
        return calendar;
    }

    public static Calendar setHmsLast(long dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        setCalendarHmsToLast(calendar);
        return calendar;
    }

    private static void setCalendarHmsToFirst(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    private static void setCalendarHmsToLast(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 59);
    }

    public static Date getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static boolean isBeforeToday(Date date) {
        Date dateNow = getCurrentDateWOHms();
        return date.before(dateNow);
    }

    public static boolean isBefore(Date date, Date when) {
        if (date == null || when == null)
            return false;
        return getDateWFirstHms(date.getTime()).before(getDateWFirstHms(when.getTime()));
    }

    public static boolean isAfter(Date date, Date when) {
        if (date == null || when == null)
            return false;
        return getDateWFirstHms(date.getTime()).after(getDateWFirstHms(when.getTime()));
    }

    public static Date getCurrentDateWOHms() {
        return new Date(setHmsFirst(getCurrentDate().getTime()).getTime().getTime());
    }

    public static boolean daysBetweenDates(Date dateStart, Date dateEnd, int daysQuantity) {
        long dayDiff = (dateEnd.getTime() - dateStart.getTime()) / DAY_LONG;
        return (dayDiff < daysQuantity);
    }

    public static Integer daysBetweenDatesToInteger(Date startDate, Date endDate, int daysQuantity) {
        return Integer.valueOf(Math.round((endDate.getTime() - startDate.getTime()) / DAY_LONG) + 1);
    }

    public static long daysBetweenDate(Date initialDate, Date endDate) {
        return (endDate.getTime() - initialDate.getTime()) / DAY_LONG;
    }

    public static Integer getMinuteBetweenDate(Date startDate, Date endDate) {
        long diferenciaMils = endDate.getTime() - startDate.getTime();
        long segundos = diferenciaMils / 1000;
        // long horas = segundos / 3600;
        return Integer.valueOf(String.valueOf(segundos / 60));
    }

    public static String getHourMinuteSecondBetweenDate(Date startDate, Date endDate) {
        long diferenciaMils = endDate.getTime() - startDate.getTime();
        long segundos = diferenciaMils / 1000;
        long horas = segundos / 3600;
        segundos -= horas * 3600;
        long minutos = segundos / 60;
        segundos -= minutos * 60;
        if (horas > ONE_DAY_IN_HOUR) {
            String vReturn = getNumWZero(2, (int) horas % ONE_DAY_IN_HOUR) + ":" + getNumWZero(2, (int) minutos) + ":"
                    + getNumWZero(2, (int) segundos);
            vReturn = String.valueOf((int) horas / ONE_DAY_IN_HOUR) + ":" + vReturn;
            return vReturn;
        }
        return getNumWZero(2, (int) horas) + ":" + getNumWZero(2, (int) minutos) + ":" + getNumWZero(2, (int) segundos);
    }

    public static String getNumWZero(int quantityZero, int number) {
        return String.format("%0" + String.valueOf(quantityZero) + "d", number);
    }
}