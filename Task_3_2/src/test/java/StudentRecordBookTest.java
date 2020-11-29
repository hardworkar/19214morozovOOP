import org.junit.Test;

import static org.junit.Assert.*;

public class StudentRecordBookTest {

    @Test(expected = IllegalArgumentException.class)
    public void pushMark() {
        StudentRecordBook myRecordBook = new StudentRecordBook(2);
        myRecordBook.pushMark(5, "Императивное программирование", (byte) 1);


        assertEquals(myRecordBook.averageMark(), 2.75, 0.001);
    }

    @Test
    public void averageMark() {
        StudentRecordBook myRecordBook = new StudentRecordBook(8);
        myRecordBook.pushMark(1, "Императивное программирование", (byte) 1);
        myRecordBook.pushMark(1, "Декларативное программирование", (byte) 2);
        myRecordBook.pushMark(1, "Дискретная математика", (byte) 3);
        myRecordBook.pushMark(1, "Математический анализ", (byte) 5);

        assertEquals(myRecordBook.averageMark(), 2.75, 0.001);
    }

    @Test
    public void increasedScholarship() {
        StudentRecordBook myRecordBook = new StudentRecordBook(8);
        myRecordBook.pushMark(1, "Императивное программирование", (byte) 5);
        myRecordBook.pushMark(1, "Декларативное программирование", (byte) 5);
        myRecordBook.pushMark(1, "Дискретная математика", (byte) 3);
        myRecordBook.pushMark(1, "Математический анализ", (byte) 5);

        myRecordBook.pushMark(2, "Императивное программирование", (byte) 5);
        myRecordBook.pushMark(2, "Декларативное программирование", (byte) 5);
        myRecordBook.pushMark(2, "Дискретная математика", (byte) 4);
        myRecordBook.pushMark(2, "Математический анализ", (byte) 5);

        assertFalse(myRecordBook.increasedScholarship(1));
        assertTrue(myRecordBook.increasedScholarship(2));
    }

    @Test
    public void diplomaWithHonours() {
        StudentRecordBook myRecordBook = new StudentRecordBook(3);
        myRecordBook.pushMark(1, "Императивное программирование", (byte) 4);
        myRecordBook.pushMark(1, "Декларативное программирование", (byte) 4);
        myRecordBook.pushMark(1, "Дискретная математика", (byte) 4);
        myRecordBook.pushMark(1, "Математический анализ", (byte) 4);

        myRecordBook.pushMark(2, "Императивное программирование", (byte) 4);
        myRecordBook.pushMark(2, "Декларативное программирование", (byte) 4);
        myRecordBook.pushMark(2, "Дискретная математика", (byte) 4);
        myRecordBook.pushMark(2, "Математический анализ", (byte) 4);

        myRecordBook.pushMark(3, "Императивное программирование", (byte) 5);
        myRecordBook.pushMark(3, "Декларативное программирование", (byte) 4);
        myRecordBook.pushMark(3, "Дискретная математика", (byte) 4);
        myRecordBook.pushMark(3, "Математический анализ", (byte) 5);

        /* соотношение в последних оценках = 0.5 */
        assertFalse(myRecordBook.diplomaWithHonours());

        /* добавляем пятерок до > 0.75, но без квалификационной работы */
        myRecordBook.pushMark(3, "Операционные системы (ахаххахах)", (byte) 5);
        myRecordBook.pushMark(3, "Введение в ИИ", (byte) 5);
        myRecordBook.pushMark(3, "Классификация смешных картинок", (byte) 5);
        myRecordBook.pushMark(3, "Введение во введение", (byte) 5);
        assertFalse(myRecordBook.diplomaWithHonours());

        myRecordBook.putQualifying((byte)5);
        assertTrue(myRecordBook.diplomaWithHonours());
    }
}