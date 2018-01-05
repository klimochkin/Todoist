import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

/**
 * Created by User1 on 02.01.2018.
 */

public class Command {

   private File file;
   private IObjectSerializer objectSerializer;
   private ContainerTask objTaskList;
   private SimpleDateFormat formatter;

   public Command(IObjectSerializer objectSerializer){
       this.formatter = new SimpleDateFormat("yyyy-MM-dd");
       this.objectSerializer = objectSerializer;
       this.file = new File("C:/Temp/test.xml");
       readObject();
   }

   public void readObject() {
       this.objTaskList = objectSerializer.readObject(this.file);
   }

   public void writeObject() {
       objectSerializer.writeObject(this.objTaskList, this.file);
   }

    void help() {
        System.out.println("______________________ ");
        System.out.println("new - Команда для добавления новой задачи");

        System.out.println("list - Команда для вывода списка задач. При использовании без флага и параметра поиска, выводит список всех существующих задач");
        System.out.println("    Доступные флаги для команды list:");
        System.out.println("    -s — поиск по статусу, допустимые параметры: new_task, in_progress, done. Пример \"list -s new_task\"");
        System.out.println("    -p — поиск по приоритету, допустимые параметры: целое число в диапазоне 1-5. Пример \"list -p 4\"");

        System.out.println("edit - Команда для редактирования задач. Через пробел указывается номер нужной задачи. Пример: edit 84");

        System.out.println("delete - Команда для удаления задачи. Через пробел указывается номер нужной задачи. Пример: delete 8");


        System.out.println("print - вывести содержимое XML");

        System.out.println("complete - Команда что б пометить как выполенную. Через пробел указывается номер нужной задачи. Пример: complete 8");
        System.out.println("______________________");
    }

    //==========================================================================================================================================

    void printXML() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ContainerTask.class);

        Marshaller marshaller = context.createMarshaller();

        StringWriter sw = new StringWriter();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(this.objTaskList, new StringWriter());

        String s = sw.toString();
        System.out.println(s);
        System.out.println("=============================================================");

    }

    //==========================================================================================================================================

    void addNewTask() throws JAXBException, IOException, ParseException {

        this.objTaskList.addTask(newTask());
        writeObject();

        System.out.println("=============================================================");
    }

    //==========================================================================================================================================

    private TaskData newTask() throws IOException, ParseException {

        TaskData obj = new TaskData();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Заполните данные по задаче: ");

// id
        obj.setId(-1);

// Заголовок
        System.out.print("Заголовок >>");
        obj.setCaption(buffer.readLine());

// Описание
        System.out.print("Описание >>");
        obj.setDescription(buffer.readLine());

// Важность
        System.out.print("Важность (целое число в диапазоне 1-5) >>");

        Pattern p = Pattern.compile("[1-5]");
        String str = buffer.readLine();

        while (!p.matcher(str).matches()){
            System.out.println("Некорректное значение!");
            System.out.print("Важность (целое число в диапазоне 1-5) >>");
            str = buffer.readLine();
        }
        obj.setPriority(Integer.parseInt(str));

// Дата создания
       // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currantDate = GregorianCalendar.getInstance();

        Date finalDate = currantDate.getTime();
        obj.setCreate(this.formatter.format(finalDate));

// Срок
        System.out.print("Срок (количество дней на выполнение) >>");

        p = Pattern.compile("[1-9]+");
        str = buffer.readLine();

        while (!p.matcher(str).matches()){
            System.out.println("Некорректное значение! Допустимо только натуральное число");
            System.out.print("Срок (количество дней на выполнение) >>");
            str = buffer.readLine();
        }
        int n = Integer.parseInt(str);

        currantDate.add(Calendar.DAY_OF_YEAR, n);
        finalDate = currantDate.getTime();
        obj.setDeadline(this.formatter.format(finalDate));

// Статус
        obj.setStatus(TaskData.eStatus.new_task);

// Дата завершения
        obj.setComplete("");

        return obj;
    }

    //==========================================================================================================================================

    void complete (String str){

        Pattern p = Pattern.compile("[1-9]+");
        if( !p.matcher(str).matches()) {
            System.out.println("Индефикатор должен быть натуральным числом!");
            return;
        }
        int idTask = Integer.parseInt(str);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currantDate = GregorianCalendar.getInstance();
        Date finalDate = currantDate.getTime();

        for (TaskData task : this.objTaskList.getTaskList()){
            if(task.getId() == idTask){
                task.setStatus(TaskData.eStatus.done);
                task.setComplete(formatter.format(finalDate));
                System.out.println("Задача "+idTask+" помечена как \"выполненная\"");
                writeObject();
                return;
            }
        }
        System.out.println("Задача с ID = "+idTask+" не существует!");
    }

    //==========================================================================================================================================

    void list (){
        for (TaskData task : this.objTaskList.getTaskList()) {
            printTask(task);
        }
    }

    //==========================================================================================================================================

    void delete(String str) {

        Pattern p = Pattern.compile("[1-9]+");
        if( !p.matcher(str).matches()) {
            System.out.println("Индефикатор должен быть натуральным числом!");
            return;
        }
        int idTask = Integer.parseInt(str);

        this.objTaskList.getTaskList().removeIf(x->x.getId()==idTask);

        writeObject();
    }

    //==========================================================================================================================================

    private void printTask(TaskData task){
        System.out.println(" №"+task.getId()+"\t - "+task.getCaption());
        System.out.println("\t Описание:\t- " +  task.getDescription());
        System.out.println("\t Важность: \t- " +  task.getPriority());
        System.out.println("\t Создана: \t- " +  task.getCreate());
        System.out.println("\t Срок до: \t- " +  task.getDeadline());
        System.out.println("\t Статус: \t- " +  task.getStatus());
        System.out.println("\t Завершена: - " +  task.getComplete() );
        System.out.println("----------------------------------------------------------");
    }

    //==========================================================================================================================================

    void findList(String flag, String param){
        switch (flag){
            case "-s":
                Pattern p = Pattern.compile("new_task|in_progress|done|[1-3]]");
                if( !p.matcher(param).matches()) {
                    System.out.println("Некорректный параметр, допустимые параметры: целое число в диапазоне 1-5");
                    return;
                }
                for(TaskData task : this.objTaskList.getTaskList()){
                 if (task.getStatus().toString().equals(param))
                     printTask(task);
                }
                System.out.println("Поиск завершен");
                break;
            case "-p":
                p = Pattern.compile("[1-9]+");
                if( !p.matcher(param).matches()) {
                    System.out.println("Некорректный параметр, необходимо целое число в диапазоне 1-5");
                    return;
                }
                for(TaskData task : this.objTaskList.getTaskList()){
                    if (task.getPriority()==Integer.parseInt(param))
                        printTask(task);
                }
                System.out.println("Поиск завершен");
                break;
            default:
                System.out.println("Некорректный флаг поиска");
                System.out.println("    Доступные флаги для команды list:");
                System.out.println("    -s — поиск по статусу, допустимые параметры: new_task, in_progress, done");
                System.out.println("    -p — поиск по приоритету, допустимые параметры: целое число в диапазоне 1-5");
        }

    }

    //==========================================================================================================================================

    void edit(String id) throws IOException, ParseException {
        TaskData obj=null;

        Pattern p = Pattern.compile("[1-9]+");
        if( !p.matcher(id).matches()) {
            System.out.println("Индефикатор должен быть натуральным числом!");
            return;
        }
        int idTask = Integer.parseInt(id);

        for (TaskData task : this.objTaskList.getTaskList()) {
            if(task.getId() == idTask) {
                obj = task;
                break;
            }
        }

        if(obj!=null) {
            System.out.println("Редактирование полей задачи №"+id+". Для пропуска нажмите \"Enter\"");

            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String str;
// Заголовок
            System.out.print("Заголовок >>");
            str = buffer.readLine();
            if(str.length()!=0)
                 obj.setCaption(str);

// Описание
            System.out.print("Описание >>");
            str = buffer.readLine();
            if(str.length()!=0)
                  obj.setDescription(str);

// Важность
            System.out.print("Важность (целое число в диапазоне 1-5) >>");
            str = buffer.readLine();

            if(str.length()!=0) {
                p = Pattern.compile("[1-5]");
                while (!p.matcher(str).matches()) {
                    System.out.println("Некорректное значение!");
                    System.out.print("Важность (целое число в диапазоне 1-5) >>");
                    str = buffer.readLine();
                }
                obj.setPriority(Integer.parseInt(str));
            }
// Срок
            System.out.print("Изменить срок (количество дней на выполнение) >>");
            str = buffer.readLine();

            if(str.length()!=0) {
                p = Pattern.compile("[1-9]+");
                while (!p.matcher(str).matches()) {
                    System.out.println("Некорректное значение! Допустимо только натуральное число");
                    System.out.print("Срок (количество дней на выполнение) >>");
                    str = buffer.readLine();
                }
                int n = Integer.parseInt(str);


                Date createDate = this.formatter.parse(this.objTaskList.getTaskList().get(idTask-1).getCreate());
                Calendar currantDate = GregorianCalendar.getInstance();
                currantDate.setTime(createDate);
                currantDate.add(Calendar.DAY_OF_YEAR, n);
                Date finalDate = currantDate.getTime();
                obj.setDeadline(this.formatter.format(finalDate));
            }
// Статус
            System.out.println("Выберете статус задачи. Допустимые значения 1, 2, 3 соответсвующие пунктам:");
            System.out.println("1. Новая задача");
            System.out.println("2. В работе");
            System.out.println("3. Завершена");
            System.out.print(">>");
            str = buffer.readLine();

            if(str.length()!=0) {
                p = Pattern.compile("[1-3]");
                while (!p.matcher(str).matches()) {
                    System.out.println("Некорректное значение! Допустимые значения 1, 2, 3 соответсвующие пунктам:");
                    System.out.println("1. Новая задача");
                    System.out.println("2. В работе");
                    System.out.println("3. Завершена");
                    System.out.print(">>");
                    str = buffer.readLine();
                }
                int n = Integer.parseInt(str);

                TaskData.eStatus[] ages = TaskData.eStatus.values();
                obj.setStatus(ages[n - 1]);
            }

// Дата завершения
            System.out.println("Отметить задачу как выполенную?");
            System.out.print("введите y/n >>");
            str = buffer.readLine();

            if(str.length()!=0) {
                p = Pattern.compile("y|n");
                while (!p.matcher(str).matches()) {
                    System.out.print("введите y/n >>");
                    str = buffer.readLine();
                }
                if(str.equals("y")) {
                    Calendar currantDate = GregorianCalendar.getInstance();
                    Date finalDate = currantDate.getTime();
                    obj.setComplete(this.formatter.format(finalDate));
                }
            }

            writeObject();
            System.out.println("Задача отредактирована");

        }
        else {
            System.out.println("Задачи с таким номером не существует");
            return;
        }
    }
}
