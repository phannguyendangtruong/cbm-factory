package com.amitgroup.utils;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
public class TimeUtils {
    public static final String DB_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.MS";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String MOBILIANS_DATE_FORMAT = "yyyyMMdd";
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    public static Date stringToSimpleDateFormat(String source){
        if (StringUtils.isEmpty(source)) return null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            simpleDateFormat.parse(source);
        } catch (Exception e) {
            log.error(e);

        }
        return null;
    }

    public static String dateToStringSimpleDateFormat(Date date) {
        if (date == null) return null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            return simpleDateFormat.format(date);

        } catch (Exception e) {
            log.error(e);

        }
        return null;
    }

    public static String dateToStringSimpleDateFormat(Long time) {
        if (time == null) return null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            return simpleDateFormat.format(new Date(time));

        } catch (Exception e) {
            log.error(e);

        }
        return null;
    }

    public static Date convertToDBFormat(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DB_FORMAT);
            return simpleDateFormat.parse(date);

        } catch (Exception e){
            return null;
        }
    }

    public static Date convertToMobiliansFormat(String date) {
        try {
            SimpleDateFormat simpleMobiliansDateFormat = new SimpleDateFormat(MOBILIANS_DATE_FORMAT);
            return simpleMobiliansDateFormat.parse(date);
        } catch (Exception e){
            return null;
        }
    }

    public static Date convertDBTimeToData(long nanaTime){
        return new Date(nanaTime / 1000);
    }

    public static Date convertDBTimeToData(String key , Map<String , Object> data){

        Object timeData = data.get(key);

        if (timeData == null)
            return null;

        if (timeData instanceof  Long){
            Long time = (Long) timeData;

            if (time > 16424946836880L){
                return new Date(time / 1000);
            } else {
                return new Date(time);
            }
        }

        try {
            Long time = Long.parseLong(timeData.toString());

            return new Date(time / 1000);
        } catch (Exception e){
            return null;
        }
    }

    public static DateRemain getRemain(Date begin, Date expired) {
        long dateIff = expired.getTime() - begin.getTime();

        if (dateIff <= 0) {
            return new DateRemain(0, TimeUnit.MILLISECONDS);
        }

        if (dateIff >= 86400000) {
            return new DateRemain(TimeUnit.DAYS.convert(dateIff, TimeUnit.MILLISECONDS), TimeUnit.DAYS);
        } else if (dateIff >= 3600000) {
            return new DateRemain(TimeUnit.HOURS.convert(dateIff, TimeUnit.MILLISECONDS), TimeUnit.HOURS);
        } else if (dateIff >= 60000) {
            return new DateRemain(TimeUnit.MINUTES.convert(dateIff, TimeUnit.MILLISECONDS), TimeUnit.MINUTES);
        } else {
            return new DateRemain(0, TimeUnit.MILLISECONDS);
        }
    }

    public static String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(seconds);

        StringBuilder sb = new StringBuilder(64);
        if (days > 0) {
            sb.append(days);
            sb.append(" d ");
        }
        if (hours > 0 || minutes > 0) {
            sb.append(hours);
            sb.append(" h ");
        }
        if (minutes > 0 || seconds > 0) {
            sb.append(minutes);
            sb.append(" m ");
        }
        if (seconds > 0 || millis > 0) {
            sb.append(seconds);
            sb.append(" s ");
        }

        if (millis > 0) {
            sb.append(millis);
            sb.append(" ms ");
        }
        return (sb.toString().trim());
    }

    public static synchronized Date safeAddSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    public static synchronized Date safeAddBeginDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.add(Calendar.DAY_OF_MONTH, day);

        return cal.getTime();
    }

    public static DateRange getFirstAndEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        Date start = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        Date end = new Date(calendar.getTimeInMillis());

        return new DateRange(start, end);
    }

    public static String getRemainStr(Date begin, Date expired) {
        DateRemain d = getRemain(begin, expired);
        return d.getValue() + " " + getTimeLabel(d.getTimeUnit());
    }

    public static String getTimeLabel(TimeUnit timeUnit) {
        switch (timeUnit) {
            case DAYS:
                return "Ngày";
            case HOURS:
                return "Giờ";
            case MINUTES:
                return "Phút";
            case SECONDS:
                return "Giây";
            default:
                return "";
        }
    }

    @Data
    public static class DateRemain {
        private long value;
        private TimeUnit timeUnit;

        public DateRemain(long value, TimeUnit timeUnit) {
            this.value = value;
            this.timeUnit = timeUnit;
        }

        public DateRemain() {

        }
    }

    @Data
    public static class DateRange {
        private Date first;
        private Date end;

        public DateRange(Date first, Date end) {
            this.first = first;
            this.end = end;
        }
    }

    public static Date findNextDay(Date date)
    {
        return new Date(date.getTime() + MILLIS_IN_A_DAY);
    }

    public static Date findPlusDays(Date date, int days)
    {
        return new Date(date.getTime() + days * MILLIS_IN_A_DAY);
    }

    /**
     *
     * @param seconds
     * @return currentTime(in millisecond) + seconds(in millisecond)
     */
    public static Long getCurrentUnixTimeAfterSeconds(Long seconds) {
        return new Date().getTime() + seconds * 1000;
    }

    /**
     *
     * @param milliseconds
     * @return currentTime(in millisecond) + milliseconds:
     */
    public static Long getCurrentUnixTimeAfterMilliSeconds(Long milliseconds) {
        return new Date().getTime() + milliseconds;
    }
}

