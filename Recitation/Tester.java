public class Tester {
    public static void main(String[] args) {
        DayOfWeek currentDay = DayOfWeek.SUNDAY;
        System.out.println(currentDay);
        System.out.println(currentDay.isWeekend());
        System.out.println(currentDay.ordinal());
        System.out.println(java.util.Arrays.toString(DayOfWeek.values()));
    }
}
