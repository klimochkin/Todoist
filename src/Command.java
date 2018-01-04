import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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

   public Command(IObjectSerializer objectSerializer){
       this.objectSerializer = objectSerializer;
       this.file = new File("C:/Temp/test.xml");
       readObject();
   }

   public void readObject() {
     //  if(new File("C:/Temp/test.xml").exists())
           this.objTaskList = objectSerializer.readObject(this.file);
   }

   public void writeObject() {
       objectSerializer.writeObject(this.objTaskList, this.file);
   }

    void help() {
        System.out.println("______________________ ");
        System.out.println("insert - добавить новую задачу");
        System.out.println("list - вывести задачи ");
        System.out.println("delete - удалить задачу");
        System.out.println("print - вывести содержимое XML");
        System.out.println("complete id - пометить как выполенную");
        System.out.println("______________________");
        return;
    }

    //==========================================================================================================================================

    void printXML() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ContainerTask.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        Marshaller marshaller = context.createMarshaller();

        StringWriter sw = new StringWriter();
        ContainerTask el = (ContainerTask) unmarshaller.unmarshal(new File("C:/Temp/test.xml"));;

        // устанавливаем флаг для читабельного вывода XML в JAXB
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(el, sw);

        String s = sw.toString();
        System.out.println(s);
        System.out.println("=============================================================");
        //  return;
    }

    //==========================================================================================================================================

    void insertTask() throws JAXBException, IOException, ParseException {

        int size = this.objTaskList.getTaskList().size();

        if (size != 0)
            this.objTaskList.addTask(newTask(this.objTaskList.getTaskList().get(size - 1).getId() + 1));
        else
            this.objTaskList.addTask(newTask(1));

        writeObject();

     //   System.out.println("Клиент добавлен со значением ID = " +this.objTaskList.getTaskList().get(size-1).getId());
        System.out.println("=============================================================");
    }

//===============================================================================================================


    TaskData newTask(int id) throws IOException, ParseException {

        TaskData obj = new TaskData();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Заполните данные по задаче: ");

// id
        obj.setId(id);

// Заголовок
        System.out.print("Заголовок >>");
        obj.setCaption(buffer.readLine());

// Описание
        System.out.print("Описание >>");
        obj.setDescription(buffer.readLine());

// Важность
        System.out.print("Важность (целое число в диапазоне 1-10) >>");
        obj.setPriority(Integer.parseInt(buffer.readLine()));

// Срок
        System.out.print("Срок (количество дней на выполнение) >>");
        int n = Integer.parseInt(buffer.readLine());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currantDate = GregorianCalendar.getInstance();
        currantDate.add(Calendar.DAY_OF_YEAR, n);
        Date finalDate = currantDate.getTime();
        obj.setDeadline(formatter.format(finalDate));

// Статус
        obj.setStatus(TaskData.eStatus.new_task);

// Дата завершения
        obj.setComplete("");

        return obj;
    }

    //==========================================================================================================================================

    void complete (String str){

        Pattern p = Pattern.compile("(-|\\+)?[1-9]+");
        if( !p.matcher(str).matches()) {
            System.out.println("ID должен быть числом!");
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
                System.out.println("Задача "+idTask+" помечена как \"выполненая\"");
                writeObject();
                return;
            }
        }
        System.out.println("Задача с ID = "+idTask+" не существует!");
    }

    //==========================================================================================================================================

    void test (){

        //  System.out.println("Выберете статус задачи:");
        //  System.out.println("1. Новая задача");
        //  System.out.println("2. В работе");
        //  System.out.println("3. Завершена");
        //  {new_task, in_progress, done}


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 7);

        Date tenDaysLater = calendar.getTime();
        String str = formatter.format(tenDaysLater);
        System.out.println(str);

    }

    //==========================================================================================================================================


}
