import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User1 on 02.01.2018.
 */

@XmlRootElement(name = "root")
public class ContainerTask {

   @XmlElementWrapper(name = "ToDoList")
   private List<TaskData> taskList;
  // private int id;

   public List<TaskData> getTaskList() { return taskList; }

    public ContainerTask() {
        this.taskList = new LinkedList<>();
    }

    public void addTask(TaskData task){
        int size = this.taskList.size();
        if (size != 0)
            task.setId(this.taskList.get(size - 1).getId() + 1);
        else
            task.setId(1);

        taskList.add(task);

        System.out.println("Добавлена задача №"+task.getId());
        if(task.getId()==-1)
            System.out.println("не удалось присвоить идефикатор");
    }
}
