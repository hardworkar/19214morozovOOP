import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
    private final Scanner scanner;
    private MyNotebook notebook;
    private final GsonWorker gsonWorker;
    public Runner() {
        scanner = new Scanner(System.in);
        gsonWorker = new GsonWorker();
    }

    /**
     * основной метод: сначала грустное общение через интерфейс командной строки, потом регистрация стандартных для данной задачи команд,
     * затем начинаем принимать и обрабатывать команды
     * @throws ParseException при разборе дат в -show "date1" "date2"
     */
    public void run() throws ParseException {
        /* грустное общение с cmd */
        System.out.println("So, you really decided to use this notebook-app. Do you have json file to work on? yes/no");
        String choice = scanner.nextLine();
        if(choice.contentEquals("yes")){
            System.out.println("Enter path for json file you want to use");
            Path path = Path.of(scanner.nextLine());
            try{
                /* пытаемся достать из существующего json-а. То, как пытаемся, заслуживает отдельного внимания (GsonWorker) */
                notebook = gsonWorker.deserialize(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            /* новому файлу - новая книжка */
            notebook = new MyNotebook();
        }
        /* добавляем дефолтные команды */
        registerCommand(new NoteAdder());
        registerCommand(new NoteRemover());
        registerCommand(new NoteAllSortedGetter());
        registerCommand(new NoteBetweenDatesWithKeywords());
        /* начинаем интерактивную работу */
        processCommands();
    }

    private void registerCommand(@NotNull ICommand handler){
        notebook.registerHandler(handler);
    }

    /**
     * обрабатывает команды: парсит на токены (см. CommandParser), затем пытается подобрать подходящую команду циклом по хендлерам
     * очень похоже на поддержку команд в калькуляторе, только здесь используется validateArgs для определения команды
     * @throws ParseException при парсинге дат из -show
     */
    private void processCommands() throws ParseException {
        while(true) {

            /* строка-приглашение */
            System.out.print("notebook> ");

            /* разбиваем на токены */
            CommandParser commandParser = new CommandParser();
            ArrayList<String> cmdline = commandParser.parseLine(scanner.nextLine());

            /* всеми силами пытаемся подобрать подходящую команду */
            boolean success = false;
            for(var handler : notebook.getHandlers()){
                if(handler.validateArgs(cmdline)){
                    success = true;
                    handler.apply(cmdline, notebook.records);
                }
            }

            /* обидно, но факт: вынести exit как поддерживаемую команду не выходит потому что он связан с gsonWorker :(
             *  может ли быть блокнот без команды выхода?.. */
            if(cmdline.get(0).contentEquals("-exit") && cmdline.size() == 1){
                if(notebook.getNotebookName() == null) {
                    System.out.println("Please, write name for your file");
                    notebook.setName(scanner.nextLine());
                }
                gsonWorker.serialize(notebook, notebook.getNotebookName());
                return;
            }
            /* если не нашли подходящей команды - кидаем исключение. По условию не описано, что делать, в принципе можно было просто ничего... */
            if(!success){
                throw new IllegalArgumentException();
            }

        }
    }
}
