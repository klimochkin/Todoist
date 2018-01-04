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

   public List<TaskData> getTaskList() { return taskList; }

    public ContainerTask() {
        this.taskList = new LinkedList();
    }


   // public void setTaskList(List<TaskData> taskList) {
    //    this.taskList = taskList;
  //  }

    public void addTask(TaskData task){
        taskList.add(task);
    }


}
