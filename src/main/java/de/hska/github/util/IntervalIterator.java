package de.hska.github.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


public class IntervalIterator implements Iterator<String> {

   private static final DateFormat FORMATTER       = new SimpleDateFormat("yyyy-MM-dd");
   private static final String     RANGE_QUALIFIER = "..";
   /**
    * The dates which are used to determine when the iterator has no next element
    */
   private final Date              TODAY           = new Date();
   private final Date              END_DATE;
   /**
    * The precision of the range queries for the dates which will be constructed
    */
   private final IntervalPrecision PRECISION;

   private Date                    _currentStartDate;
   private boolean                 _firstInterval  = true;

   /**
    * This iterator allows to iterate over intervals used to query the githup api. The range queries used
    * by the api looks like the following <b>yyyy-MM-dd..yyyy-MM-dd</b>. Using the next method will create such
    * a query term which can be used to to precise the query.
    * 
    * @param startDate The date where the queries should start. The string needs a special format. If this is not given
    * this class will throw an exception
    * @param endDate  The last date which should be analyzed
    * @param precision The precision of the interval see {@link IntervalPrecision} for the possible values
    */
   public IntervalIterator( String startDate, String endDate, IntervalPrecision precision ) {
      _currentStartDate = parse(startDate);
      END_DATE = parse(endDate);
      PRECISION = precision;
   }

   public boolean hasNext() {
      return _currentStartDate.before(TODAY) && _currentStartDate.before(END_DATE);
   }

   public String next() {
      String nextInterval = getNextInterval();
      incrementCurrentDate();
      return nextInterval;
   }

   private String getNextInterval() {
      Date startDate = getNextStartDate();
      Date endDate = getNextEndDate();
      String start = FORMATTER.format(startDate);
      String end = FORMATTER.format(endDate);
      return new StringBuilder(start).append(RANGE_QUALIFIER).append(end).toString();
   }

   private Date getNextStartDate() {
      if ( _firstInterval ) {
         _firstInterval = false;
         return _currentStartDate;
      }
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(_currentStartDate);
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      return calendar.getTime();
   }

   private Date getNextEndDate() {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(_currentStartDate);
      calendar.add(PRECISION.getPrecision(), 1);
      return calendar.getTime();
   }

   private void incrementCurrentDate() {
      _currentStartDate = getNextEndDate();
   }

   private Date parse( String date ) {
      try {
         return FORMATTER.parse(date);
      }
      catch ( ParseException ex ) {
         return null;
      }
   }

   public enum IntervalPrecision {
      DAY("day", Calendar.DAY_OF_MONTH), //
      WEEK("week", Calendar.WEEK_OF_MONTH), //
      MONTH("month", Calendar.MONTH), //
      YEAR("year", Calendar.YEAR);

      String _name;
      int    _precision;

      IntervalPrecision( String name, int precision ) {
         _name = name;
         _precision = precision;
      }

      public int getPrecision() {
         return _precision;
      }
   }

   public static void main( String[] args ) {
      IntervalIterator dateUtils = new IntervalIterator("2015-01-01", "2016-01-01", IntervalPrecision.DAY);
      while ( dateUtils.hasNext() ) {
         String next = dateUtils.next();
         System.out.println(next);
      }
   }
}
