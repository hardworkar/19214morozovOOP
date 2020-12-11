import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyNotebook{
    protected List<NotebookRecord> records = new ArrayList<>();
    private String name = null;
    private final List<ICommand> handlers = new ArrayList<>();

    /**
     * добавить команду в список поддерживаемых
     * @param handler команда
     */
    public void registerHandler(@NotNull ICommand handler){
        handlers.add(handler);
    }

    /**
     * задать имя для блокнота (для дальнейшего сохранения)
     * @param name что-то вроде file.json :)
     */
    public void setName(@NotNull String name){
        this.name = name;
    }

    /**
     * получить все команды
     * @return список поддерживаемых команд
     */
    public List<ICommand> getHandlers() {
        return handlers;
    }

    /**
     * получить имя блокнота
     * @return имя, под которым сохраняется файл
     */
    public String getNotebookName() {
        return name;
    }

}

/**
 * абстрактный класс от которого наследуются все команды блокнота
 */
abstract class ICommand{
    abstract boolean validateArgs(@NotNull ArrayList<String> cmdline);
    abstract void apply(@NotNull ArrayList<String> cmdline, @NotNull List<NotebookRecord> records) throws ParseException;
}

/**
 * добавить запись в список
 */
class NoteAdder extends ICommand{

    /* у команды -add должно быть 2 аргумента */
    @Override
    public boolean validateArgs(@NotNull ArrayList<String> cmdline) {
        return cmdline.get(0).contentEquals("-add") && cmdline.size() == 3;
    }

    @Override
    public void apply(@NotNull ArrayList<String> cmdline, @NotNull List<NotebookRecord> records) {
        records.add(new NotebookRecord(cmdline.get(1), cmdline.get(2), new Date()));
    }
}

/**
 * удаляет запись по названию
 */
class NoteRemover extends ICommand{

    @Override
    public boolean validateArgs(@NotNull ArrayList<String> cmdline) {
        return cmdline.get(0).contentEquals("-rm") && cmdline.size() == 2;
    }

    @Override
    public void apply(@NotNull ArrayList<String> cmdline, @NotNull List<NotebookRecord> records) {
        var found = records.stream().filter(s -> s.getTitle().compareTo(cmdline.get(1)) == 0).findFirst().orElseThrow();
        records.remove(found);
    }
}

/**
 * вывести все записи, отсортированные по времени добавления
 * */
class NoteAllSortedGetter extends ICommand{
    /* это простой -show без дополнительных аргументов */
    @Override
    public boolean validateArgs(@NotNull ArrayList<String> cmdline) {
        return cmdline.get(0).contentEquals("-show") && cmdline.size() == 1;
    }

    @Override
    public void apply(@NotNull ArrayList<String> cmdline, @NotNull List<NotebookRecord> records) {
        records.stream().sorted(
                Comparator.comparing(NotebookRecord::getCreationDate)
        ).forEach(s -> System.out.println(s.getTitle() + " : " + s.getText()));
    }
}

/**
 * возвращает отсортированные по времени добавления записи из заданного
 * интервала времени, содержащие в заголовке ключевые слова
 */
class NoteBetweenDatesWithKeywords extends ICommand{

    @Override
    public boolean validateArgs(@NotNull ArrayList<String> cmdline) {
        return cmdline.get(0).contentEquals("-show") && cmdline.size() >= 4;
    }

    @Override
    public void apply(@NotNull ArrayList<String> cmdline, @NotNull List<NotebookRecord> records) {
        SimpleDateFormat formatter = new SimpleDateFormat("\"dd.MM.yyyy HH:mm\"");
        try {
            /* парсим (пытаемся) даты */
            Date start = formatter.parse(cmdline.get(1));
            Date end = formatter.parse(cmdline.get(2));

            /* делаем ключевые слова */
            List<String> keywords = new ArrayList<>();
            for(int i = 3 ; i < cmdline.size() ; i++){
                keywords.add(cmdline.get(i));
            }

            /* пытаемся найти подходящие штуки */
            List<NotebookRecord> answer = new ArrayList<>();
            for(NotebookRecord rec : records){
                for(String keyword : keywords){
                    if(rec.getTitle().contains(keyword) && rec.getCreationDate().after(start) && rec.getCreationDate().before(end)){
                        if(!answer.contains(rec)) {
                            answer.add(rec);
                        }
                    }
                }
            }
            /* выводим */
            answer.stream().sorted(
                    Comparator.comparing(NotebookRecord::getCreationDate)).
                    forEach(s -> System.out.println(s.getTitle() + " : " + s.getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

