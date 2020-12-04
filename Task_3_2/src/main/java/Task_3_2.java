public class Task_3_2 {
    public static void main(String[] args){
        StudentRecordBook myRecordBook = new StudentRecordBook(8);
        myRecordBook.pushMark(1, "Императивное программирование", (byte) 2);
        myRecordBook.pushMark(1, "Декларативное программирование", (byte) 2);
        myRecordBook.pushMark(1, "Дискретная математика", (byte) 2);
        myRecordBook.pushMark(1, "Математический анализ", (byte) 2);

        myRecordBook.pushMark(2, "Императивное программирование", (byte) 5);
        myRecordBook.pushMark(2, "Декларативное программирование", (byte) 5);
        myRecordBook.pushMark(2, "Дискретная математика", (byte) 5);
        myRecordBook.pushMark(2, "Математический анализ", (byte) 5);
        myRecordBook.diplomaWithHonours();
        System.out.println(myRecordBook.averageMark());
    }
}
