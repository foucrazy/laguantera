
package com.laguantera.util.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Loosing logfiles?
 * There is a known issue with DailyRollingFileAppender and 1.2.x.<br>
 * It is fixed in the discontinued 1.3 and in the experimental 2.x, but
in production system
 * you really don't want to loose your log-files, and you are most
likely using the stable 1.2.x.
 *
 * </p>
 * The defects causing the need for this additional DatedFileAppender:<br>
 * log4j defects<br>
 * <ul>
 * <li>https://issues.apache.org/bugzilla/show_bug.cgi?id=29726</li>
 * <li>https://issues.apache.org/bugzilla/show_bug.cgi?id=39023</li>
 * <li>https://issues.apache.org/bugzilla/show_bug.cgi?id=41735</li>
 * <li>https://issues.apache.org/bugzilla/show_bug.cgi?id=43374</li>
 * </ul>
 * caused by java sun defect<br>
 * <ul>
 * <li>http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4167147</li>
 * </ul>
 *
 * <p>
 * The main reason for this is the defect in java.io.File.renameTo()
causing log-files to be lost occasionally.<br>
 * The RollingFileAppender tries to rename the old files during roll over.
 * However, it does not check if the rename succeeded and as a result,
it may end up deleting information.<br>
 * There is no exception thrown just a boolean return for success or
failure.<br>
 * In particular, on Windows, the file rename can fail if
 * another process has the file open.
 * To avoid the whole renaming-problem the log-file is created with date
suffix, and not postponed until rolling occurs.
 *
 * </p>
 *
 * DatedFileAppender appends Log4j logging events to a different log
file every
 * day. Today's date (as yyyy-MM-dd) is appended to the end of the
file.<br>
 * DatedFileAppender creates a new log file with the first message it
logs on a
 * given day and continues to use that same file throughout the day.
<br>Thus avoiding the whole rename-problem.<br><br>
 *
 * If you want hourly/minutely etc,modify the datepattern and
resolveNextRollOverTime method accordingly.
 *
 * The following sample log4j.xml appender block
 *
 * <pre>
 *  &lt;appender name=&quot;file&quot;
class=&quot;no.gwr.log4j.DatedFileAppender&quot;&gt;
 *  &lt;param name=&quot;File&quot;
value=&quot;application-log.log&quot; /&gt;
 *  &lt;layout class=&quot;org.apache.log4j.PatternLayout&quot;&gt;
 *  &lt;param name=&quot;ConversionPattern&quot;
value=&quot;[%d{dd.MM.yyyy HH:mm:ss}, %-5p, %c{1}] - %m%n&quot;/&gt;
 *  &lt;/layout&gt;
 *  &lt;/appender&gt;
 * </pre>
 *
 *
 * @author geirwr
 *
 */
public class AppenderDiario extends FileAppender {

    private Calendar calendar = Calendar.getInstance();
    private String datePattern = "'.'dd-MM-yyyy";//change this if you want hourly/minutely, i.e. different rolling than daily
    private long rotationTime = 0L;
    private String file;

    /**
     * Called by AppenderSkeleton.doAppend() to write a log message
formatted
     * according to the layout defined for this appender.
     */
    public void append(LoggingEvent event) {
        if (this.layout == null) {
            errorHandler.error("No layout set for the appender named ["
+ name+ "].");
            return;
        }
        long n = System.currentTimeMillis();
        if (n >= rotationTime) {
            rollOver(n);
        }
        if (this.qw == null) { // should never happen
            errorHandler.error("No output stream or file set for the appender named ["+ name + "].");
            return;
        }
        subAppend(event);
    }

    /**
     * rolling over to a new file.
     * No File.renameTo or File.copy necessary, as the appropriate
filename is logged to directly
     * not as in default DailyRollingFileAppender, which can cause
problems in Windows-env
     *
     * @param currentTimeInMillis
     */
    private void rollOver(long currentTimeInMillis) {
        calendar.setTime(new Date(currentTimeInMillis));
        File newFile = new File(getFileName());
        this.fileName = newFile.getAbsolutePath();
        rotationTime=resolveNextRollOverTime(calendar);
        super.activateOptions(); // close current file and open new file
    }
    /**
     * @return a filename containing the timestamp part
     */
    private String getFileName() {
        StringBuffer sb = new StringBuffer();
        sb.append(file);
        SimpleDateFormat fmt = new SimpleDateFormat(datePattern);
        sb.append(fmt.format(calendar.getTime()));
        return sb.toString();
    }

    @Override
    public void setFile(String f) {
        this.file = f;
        super.setFile(getFileName());
    }

    /**
     * resolving next rollover time-
     * improve with hourly, minutely,weekly,monthly if needed.
     * Must also have a datepattern to match it.
     * Could the rollover-frequency be resolved from datePattern, as in DailyRollingFileAppender?
     * sample
     * <pre>
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (DAILY) {hour=minute=0;}//due to daily rollover
        calendar.clear(); // clear all fields
        calendar.set(year,month,day,hour,minute); // setting next rollover
     * </pre>
     * Sets a calendar to the start of tomorrow, with all time values
reset to
     * zero.
     * This must be changed if different rolling wanted.
     */
    public static long resolveNextRollOverTime(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH)+1;//gives daily rolling
        //int hour = calendar.get(Calendar.HOUR_OF_DAY)+1;gives hourly rolling
        //int minute = calendar.get(Calendar.MINUTE)+1;gives minutely rolling
        calendar.clear(); 
        calendar.set(year,month,day);
        return calendar.getTime().getTime();
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getDatePattern() {
        return datePattern;
    }
}
