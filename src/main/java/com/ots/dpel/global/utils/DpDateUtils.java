package com.ots.dpel.global.utils;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.Assert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * @author sdimitriadis
 */
public class DpDateUtils {
    
    /**
     * Μετατροπή του δοθέντος αντικειμένου ημερομηνίας σε string μορφής dd/MM/yyyy.
     */
    public static String getDateString(Object dateObject) {
        
        if (dateObject == null) {
            return null;
        }
        
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format((Date) dateObject);
        } catch (ClassCastException e) {
            return null;
        }
    }
    
    /**
     * Μετατροπή του δοθέντος αντικειμένου ημερομηνίας/ώρας σε string μορφής dd/MM/yyyy HH:mm.
     */
    public static String getDateTimeString(Object dateTimeObject) {
        
        if (dateTimeObject == null) {
            return null;
        }
        
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").format((Date) dateTimeObject);
        } catch (ClassCastException e) {
            return null;
        }
    }
    
    /**
     * Μετατροπή του δοθέντος αντικειμένου ημερομηνίας/ώρας σε string μορφής dd_MM_yyyy_HH_mm_ss.
     */
    public static String getDateTimeTillSecondsString(Object dateTimeObject) {
        
        if (dateTimeObject == null) {
            return null;
        }
        
        try {
            return new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format((Date) dateTimeObject);
        } catch (ClassCastException e) {
            return null;
        }
    }
    
    public static String getDateTimeTillSecondsPlainString(Object dateTimeObject) {
        
        if (dateTimeObject == null) {
            return null;
        }
        
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format((Date) dateTimeObject);
        } catch (ClassCastException e) {
            return null;
        }
    }
    
    /**
     * Εξαγωγή του έτους σε Long από μία ημερομηνία<br/>
     * Σε περίπτωση που η ημερομηνία είναι null επιστρέφεται το έτος της τρέχουσας ημερομηνίας του συστήματος
     * @param date Η ημερομηνία
     * @return Το έτος της ημερομηνίας σε Long
     */
    public static Long getDateYear(Date date) {
        
        Calendar calendar = new GregorianCalendar();
        
        if (date != null) calendar.setTime(date);
        
        return new Long(calendar.get(Calendar.YEAR));
    }
    
    public static Integer getDateMonth(Date date) {
        
        Calendar calendar = new GregorianCalendar();
        
        if (date != null) calendar.setTime(date);
        
        return calendar.get(Calendar.MONTH);
    }
    
    public static Integer getDateDay(Date date) {
        
        Calendar calendar = new GregorianCalendar();
        
        if (date != null) calendar.setTime(date);
        
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Μετατροπή ημερομηνίας από {@link XMLGregorianCalendar} σε {@link Date}
     * @param calendar
     * @return
     */
    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar calendar) {
        return (calendar == null) ? null : calendar.toGregorianCalendar().getTime();
    }
    
    /**
     * Μετατροπή ημερομηνίας από {@link Date} σε {@link XMLGregorianCalendar}
     * @param date
     * @return
     */
    public static XMLGregorianCalendar dateToXmlGregorianCalendar(Date date) {
        
        XMLGregorianCalendar xmlGregorianCalendar = null;
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException ex) {
            //Γίνεται swallow το exception και επιστρέφεται από τη μέθοδο η τιμή null
            ex.printStackTrace();
        }
        
        return xmlGregorianCalendar;
    }
    
    /**
     * Ανάκτηση τέλους συγκεκριμένης ημερομηνίας σε επίπεδο δευτερολέπτου (πχ. 10/10/2016 23:59:59)
     * @param date Ημερομηνία
     * @return
     */
    public static Date getEndOfDay(Date date) {
        return DateUtils.addSeconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }
    
    /**
     * Μετατροπή συγκεκριμένης ημερομηνίας σε String περιγραφής
     * χρονοσφραγίδας εκτέλεσης λειτουργίας Scheduler
     * Για παράδειγμα η χρονική στιγμή "12/01/2017 23:10" μετατρέπεται σε "0 10 23 12 1 ? 2017"
     * http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param date Ημερομηνία
     * @return
     */
    public static String convertDateToCronExpression(Date date) {
        
        Calendar calendar = new GregorianCalendar();
        
        if (date == null) return null;
        
        calendar.setTime(date);
        
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1;
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendar.get(java.util.Calendar.MINUTE);
        
        return String.format("0 %d %d %d %d ? %d", minute, hour, day, month, year);
    }
    
    /**
     * Έλεγχος εάν μία ημερομηνία βρίσκεται στο παρελθόν
     * @param date Ημερομηνία
     * @return
     */
    public static boolean dateInPast(Date date) {
        Assert.notNull(date, "Date should not be null");
        return date.before(new Date());
    }
    
    /**
     * Έλεγχος εάν μία ημερομηνία βρίσκεται στο μέλλον
     * @param date Ημερομηνία
     * @return
     */
    public static boolean dateInFuture(Date date) {
        Assert.notNull(date, "Date should not be null");
        return date.after(new Date());
    }
    
    /**
     * Ανάκτηση πρωτοχρονιάς του έτους ημερομηνίας
     * Μηδενίζονται και τα στοιχεία της ώρας (εάν υπάρχουν)
     * @param date Ημερομηνία
     * @return
     */
    public static Date getFirstDateOfYear(Date date) {
        
        Calendar calendar = new GregorianCalendar();
        
        if (date != null) {
            calendar.setTime(date);
        } else {
            return null;
        }
        
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
    
    /**
     * Πρόσθεση λεπτών σε ημερομηνία με τα δευτερόλεπτα να ορίζονται σε 0
     * Για παράδειγμα η χρονική στιγμή "12/01/2017 23:10:24" +2 μετατρέπεται σε "12/01/2017 23:12:00"
     * @param date    Ημερομηνία
     * @param minutes Λεπτά
     * @return
     */
    public static Date addMinutesToDate(Date date, int minutes) {
        
        Calendar calendar = new GregorianCalendar();
        
        if (date != null) {
            calendar.setTime(date);
        } else {
            return null;
        }
        
        calendar.add(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
    
    /**
     * Δημιουργία αντικειμένου ημερομηνίας από τις διακριτές τιμές
     * @param year   Έτος
     * @param month  Μήνας (zero-based)
     * @param day    Ημέρα
     * @param hour   Ώρα (οι απογευματινές ώρες πάνε ως εξής 20 για τις 8 το απόγευμα κτλ.)
     * @param minute Λεπτά
     * @param second Δευτερόλεπτα
     * @return
     */
    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
    
    /**
     * Έλεγχος αν δύο ημερομηνίες είναι όμοιες
     * Λαμβάνονται υπόψη και οι null τιμές. Αν είναι και οι δύο null, τότε θεωρούνται όμοιες.
     * @param d1 Ημερομηνία Α
     * @param d2 Ημερομηνία Β
     */
    public static boolean datesAreEqual(Date d1, Date d2) {
        return d1 == null ? d2 == null : d1.equals(d2);
    }
    
    /**
     * Έλεγχος αν δύο ημερομηνίες είναι όμοιες χωρίς να κοιτάμε την ώρα.
     * Λαμβάνονται υπόψη και οι null τιμές. Αν είναι και οι δύο null, τότε θεωρούνται όμοιες.
     */
    public static boolean datesAreEqualIgnoringTime(Date d1, Date d2) {
        
        if (d1 == null && d2 == null) {
            return true;
        } else if (d1 == null && d2 != null) {
            return false;
        } else if (d1 != null && d2 == null) {
            return false;
        } else {
            return DateUtils.isSameDay(d1, d2);
        }
    }
    
    /**
     * Έλεγχος αν η πρώτη ημερομηνία είναι μεγαλύτερη από τη δεύτερη χωρίς να κοιτάμε την ώρα.
     * Λαμβάνονται υπόψη και οι null τιμές. Αν είναι και οι δύο null, τότε θεωρούνται όμοιες.
     */
    public static boolean firstDateGreaterIgnoringTime(Date d1, Date d2) {
        
        if (d1 == null && d2 == null) {
            return false;
        } else if (d1 == null && d2 != null) {
            return false;
        } else if (d1 != null && d2 == null) {
            return true;
        } else {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(d1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(d2);
            if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
                return true;
            } else if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
                return false;
            } else {
                if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) {
                    return true;
                } else if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) {
                    return false;
                } else {
                    if (cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        
    }
    
    public static Date getNewDateSecond() {
        
        Calendar calendar = new GregorianCalendar();
        
        Date date = new Date();
        
        calendar.setTime(date);
        
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
    
    public static Long getSecondsBetweenDates(Date fromDate, Date toDate) {
    
        if (fromDate == null || toDate == null) {
            return null;
        }
        
        return (toDate.getTime() - fromDate.getTime()) / 1000;
    }
    
    public static Date addSecondsToDate(Date date, int seconds) {
        
        Calendar calendar = new GregorianCalendar();
        
        if (date != null) {
            calendar.setTime(date);
        } else {
            return null;
        }
        
        calendar.add(Calendar.MINUTE, 0);
        calendar.add(Calendar.SECOND, seconds);
        calendar.add(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
}
