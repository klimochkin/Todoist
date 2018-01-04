import java.io.File;

/**
 * Created by User1 on 02.01.2018.
 */
public interface IObjectSerializer {

    ContainerTask readObject( File file);
    void writeObject( ContainerTask task, File file);
}
