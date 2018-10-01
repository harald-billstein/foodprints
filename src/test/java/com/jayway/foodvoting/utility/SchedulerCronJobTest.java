package com.jayway.foodvoting.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

public class SchedulerCronJobTest {

  private final long ONE_DAY = 86400000;

  @Test
  public void testCronJob() {
    // EVERY DAY AT MIDNIGHT
    CronTrigger cronTrigger = new CronTrigger("0 0 0 * * *");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    Calendar calendar = Calendar.getInstance();
    System.out.println("time now " + simpleDateFormat.format(calendar.getTime().getTime()));

    Date nextExecutionTime = cronTrigger.nextExecutionTime(
        new TriggerContext() {

          @Override
          public Date lastScheduledExecutionTime() {
            return calendar.getTime();
          }

          @Override
          public Date lastActualExecutionTime() {
            return calendar.getTime();
          }

          @Override
          public Date lastCompletionTime() {
            return calendar.getTime();
          }
        });

    long futureInMs = calendar.getTime().getTime() + ONE_DAY;
    // CHECK SO THERE IS A TRIGGER IN FUTURE
    Assert.assertTrue(nextExecutionTime.getTime() > calendar.getTime().getTime());
    // CHECK SO FUTURE TRIGGER IS WITHIN A DAY
    Assert.assertTrue(futureInMs > nextExecutionTime.getTime());

    // CHECK SO THE TRIGGER IS SET TO 00:00:00 AT NIGHT
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
    String time = simpleDateFormat2.format(nextExecutionTime.getTime());
    String midnight = time.replace(':', '0');

    for (int i = 0; i < midnight.length(); i++) {
      char tempChar = 48;
      Assert.assertEquals(tempChar, midnight.charAt(i));
    }
  }
}
