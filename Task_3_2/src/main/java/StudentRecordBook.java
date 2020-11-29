import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StudentRecordBook {
    private final List<Semester> semesters;
    private int qualifying = 0;

    /**
     * создает новую зачетную книжку с заданным числом семестров
     * @param numOfSemesters количество семестров
     * @exception IllegalArgumentException если количество семестров < 0
     */
    public StudentRecordBook(int numOfSemesters) {
        if(numOfSemesters < 0){
            throw new IllegalArgumentException();
        }
        semesters = new ArrayList<>();
        for(int i = 0 ; i < numOfSemesters ; i++){
            semesters.add(new Semester());
        }
    }
    static class Semester{
        private final Map<String, List<Byte>> subjects = new HashMap<>();
        private Map<String, List<Byte>> getSubjects() {
            return subjects;
        }
    }

    /**
     * Добавляет оценку по заданному семестру и предмету. Если такого предмета в этом семестре не существует, он будет создан.
     * @exception IllegalArgumentException если такого семестра не существует
     * @param semester номер семестра, в котором был предмет subject
     * @param subject название предмета
     * @param mark добавляемая оценка
     */
    public void pushMark(int semester, @NotNull String subject, byte mark) {
        if(semester < 1 || semester > semesters.size() || mark < 0 || mark > 5) {
            throw new IllegalArgumentException();
        }
        semesters.get(semester - 1).getSubjects().putIfAbsent(subject, new ArrayList<>());
        semesters.get(semester - 1).getSubjects().get(subject).add(mark);
    }

    /**
     * поставить отметку за квалификационную работу
     * @param mark полученная оценка
     */
    public void putQualifying(byte mark){
        if(mark < 0 || mark > 5){
            throw new IllegalArgumentException();
        }
        qualifying = mark;
    }
    /**
     * возвращает 0.0, если оценок нет
     * @return средний балл по всем сохраненным оценкам
     */
    public double averageMark(){
        double avg = 0;
        int cnt = 0;
        for(Semester semester : semesters){
            for(List<Byte> subject : semester.getSubjects().values()){
                avg += subject.stream().mapToDouble(i -> i).average().getAsDouble();
                cnt ++;
            }
        }
        return avg / cnt;
    }

    /**
     * @param semester интересующий семестр
     * @return true если повышенная стипендия будет (семестр без троек)
     */
    public boolean increasedScholarship(int semester) {
        if(semester < 1 || semester > semesters.size()) {
            throw new IllegalArgumentException();
        }
        for(List<Byte> subject : semesters.get(semester-1).getSubjects().values()){
            if(subject.contains((byte)3)){
                return false;
            }
        }
        return true;
    }

    /**
     * возвращает возможность выдать красный диплом
     * @return true если студент может получить красный диплом
     */
    public boolean diplomaWithHonours(){
        Map<String, Byte> lastMarks = new HashMap<>();
        for(Semester semester : semesters){
            for(String subjectName : semester.getSubjects().keySet()){
                for(Byte mark : semester.getSubjects().get(subjectName)){
                    if(mark == (byte)3){
                        return false;
                    }
                    lastMarks.put(subjectName, mark);
                }
            }
        }
        int num5 = (int)lastMarks.values().stream().filter(s -> s == (byte)5).count();
        double proportionForDiploma = 0.75;
        return ((double) num5 / lastMarks.values().size()) >= proportionForDiploma && qualifying == 5;
    }
}
