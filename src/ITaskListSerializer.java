import java.io.File;

/**
 * Created by User1 on 02.01.2018.
 */
public interface ITaskListSerializer {

    ContainerTask readTaskList( File file);
    void writeTaskList( ContainerTask task, File file);
}
