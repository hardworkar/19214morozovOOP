import org.jetbrains.annotations.NotNull;

public class MyDate {
    private static final int DAYS_COMMON_YEAR = 365;
    private static final int DAYS_LEAP_YEAR = 366;
    private static final int DAYS_PER_WEEk = 7;
    private static final String[] monthNames = new String[]{"", "январь", "февраль", "март",
                                                            "апрель", "май", "июнь",
                                                            "июль", "август", "сентябрь",
                                                            "октябрь", "ноябрь", "декабрь"};
    private static final int[] monthDays = new int[]{-1000, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private boolean isLeap(int year) {
        if(year <= 0){
            throw new IllegalArgumentException();
        }
        if(year % 400 == 0) {
            return true;
        }
        else if(year % 100 == 0) {
           return false;
        }
        else return year % 4 == 0;
    }
    /**
     * @param day день месяца (должен укладываться в месяц)
     * @param month [1-12]
     * @param year [> 0]
     * */
    MyDate(int day, int month, int year) {
        if(year < 1 || month < 1 || month > 12 || day < 0 || day > monthDays[month]) {
            throw new IllegalArgumentException("Wrong date input");
        }
        this.days = toDays(day, month, year);
        this.currDay = day;
        this.currMonth = month;
        this.currYear = year;
    }
    /**
     * @return число месяца [1-31]
     * */
    public int getCurrDay(){
        return currDay;
    }
    /**
     * @return месяц [1-12]
     * */
    public int getCurrMonth(){
        return currMonth;
    }
    public int getCurrYear(){
        return currYear;
    }
    /**
     * @param b дата для которой нужно найти разницу
     * @return неотрицательное количество дней - модуль разности между датами
     * */
    public int getDiffInDays(@NotNull MyDate b){
        if(b.days > this.days) {
            return b.days - this.days;
        }
        else{
            return - b.days + this.days;
        }
    }
    /**
     * @param b дата для которой нужно найти разницу
     * @return дата == разность между двумя входными (поля так же неотрицательные)
     * */
    public MyDate getDiff(@NotNull MyDate b){
        MyDate res;
        if(b.days > days) {
            res = new MyDate(b.currDay - currDay, b.currMonth - currMonth, b.currYear - currYear);
        }
        else{
            res = new MyDate(-b.currDay + currDay, -b.currMonth + currMonth, -b.currYear + currYear);
        }
        if(res.currDay <= 0){
            res.currMonth--;
            res.currDay += monthDays[currMonth+1];
        }
        if(res.currMonth <= 0){
            res.currYear--;
            res.currMonth += 12;
        }
        return res;
    }

    /* измерять всё в днях звучит неплохо, т.к. это минимальная единица в нашей ситуации */
    private int days;
    private int currDay, currMonth, currYear;
    /**
     * переводит обычные поля в "дни с начала календаря"
     * */
    private int toDays(int day, int month, int year) {
        int allDays = 0;
        for(int i = 1 ; i < year ; i++) {
            if(isLeap(i)) {
                allDays ++;
            }
            allDays += 365;
        }
        for(int i = 1 ; i < month ; i++) {
            allDays += monthDays[i];
            if(i == 2 && isLeap(year)) {
                allDays++;
            }
        }
        allDays += day-1;
        return allDays;
    }
    /**
     * устанавливает обычные поля исходя из поля "дни с начала календаря"
     */
    private void setCurrFields(){
        int allDays = this.days;
        for(currYear = 1 ; ; currYear++) {
            if(isLeap(currYear)) {
                if(allDays >= DAYS_LEAP_YEAR) {
                    allDays -= DAYS_LEAP_YEAR;
                }
                else{
                    break;
                }
            }
            else {
                if(allDays >= DAYS_COMMON_YEAR) {
                    allDays -= DAYS_COMMON_YEAR;
                }
                else{
                    break;
                }
            }
        }
        for(currMonth = 1 ;  ; currMonth++) {
            if(currMonth == 2 && isLeap(currYear)) {
                if(allDays >= monthDays[currMonth] + 1){
                    allDays -= (monthDays[currMonth] + 1);
                }
                else{
                    break;
                }
            }
            else{
                if(allDays >= monthDays[currMonth]){
                    allDays -= (monthDays[currMonth]);
                }
                else{
                    break;
                }
            }
        }
        currDay = allDays + 1;
        assert (this.days == toDays(currDay,currMonth,currYear));
    }
    /**
     * 4) Поддержка арифметических операций сложение и вычитание (дата – дата; дата
     * ± день, месяц, год)
     * */
    public void addDays(int days) {
        this.days += days;
        setCurrFields();
    }
    public void addWeeks(int weeks) {
        this.days += weeks * DAYS_PER_WEEk;
        setCurrFields();
    }
    public void addMonths(int months) {
        currYear += months / 12;
        months %= 12;
        currMonth += months;
        if(currMonth <= 0){
            currMonth+=12;
            currYear--;
        }
        if(currMonth > 12){
            currMonth-=12;
            currYear++;
        }
        this.days = toDays(currDay, currMonth, currYear);
    }
    public void addYears(int years) {
        currYear += years;
        this.days = toDays(currDay, currMonth, currYear);
    }

    /** 3) для заданной даты определить день недели.
     * @return номер дня недели, 0 - Sunday, как в Date.getDay()
     */
    public int getDayWeek() {
        return (days+1) % 7;
    }

    public String toString() {
        return "\"" + currDay + " " + monthNames[currMonth] + " " + currYear + "\"";
    }
}
