import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class MyDateTest {

    @Test
    public void addDays() {
        /* 1) Какой день недели будет через 1024 дня? */
        Calendar calendar = new GregorianCalendar(2020, Calendar.NOVEMBER , 21);
        calendar.add(Calendar.DATE, 1024);
        Date date = calendar.getTime();
        MyDate cal = new MyDate(21, 11, 2020);
        cal.addDays(1024);
        // понедельник == 1
        assertEquals(date.getDay(), cal.getDayWeek());
    }

    @Test
    public void victoryDay(){
        /* 2) Сколько лет, месяцев и дней назад был день победы 9 мая 1945 года? */
        MyDate today = new MyDate(21, 11, 2020);
        MyDate victory = new MyDate(9, 5, 1945);
        MyDate difference = victory.getDiff(today);
        System.out.println(difference.getCurrYear() + " лет, " + difference.getCurrMonth() + " месяцев, " + difference.getCurrDay() + " дней");
    }

    @Test
    public void dayOfBirth(){
        /* 3) В какой день недели вы родились? */
        Calendar calendar = new GregorianCalendar(2002, Calendar.JUNE , 6);
        Date date = calendar.getTime();
        MyDate cal = new MyDate(6, 6, 2002);
        assertEquals(date.getDay(), cal.getDayWeek());
    }

    @Test
    public void monthWeeks(){
        /* 4) Какой месяц будет через 17 недель? */
        Calendar calendar = new GregorianCalendar(2020, Calendar.NOVEMBER , 21);
        calendar.add(Calendar.WEEK_OF_YEAR, 17);
        Date date = calendar.getTime();
        MyDate cal = new MyDate(21, 11, 2020);
        cal.addWeeks(17);
        /* нумерую месяцы с единицы (возможно, это диагноз) */
        assertEquals(date.getMonth(), cal.getCurrMonth()-1);
    }

    @Test
    public void newYear(){
        /* 5) Сколько дней до нового года? */
        MyDate today = new MyDate(21, 11, 2020);
        System.out.println("Блин-блинский, до нового года " + today.getDiffInDays(new MyDate(1,1,2021)) + " дней!");
    }

    @Test
    public void earliest13(){
        /* 6) Ближайшая пятница 13-го числа месяца? */
        MyDate toward = new MyDate(13,12,2020);
        MyDate backward = new MyDate(13,11,2020);
        while(toward.getDayWeek() != 5){
            toward.addMonths(1);
        }
        while(backward.getDayWeek() != 5){
            backward.addMonths(-1);
        }
        System.out.println("Следующая: " + toward);
        System.out.println("Предыдущая: " + backward);
    }
}