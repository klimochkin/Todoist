import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by User1 on 03.01.2018.
 */
public class XmlSerializer implements ITaskListSerializer {


    @Override
    public ContainerTask readTaskList( File file) {
        if (!file.exists() || file.length() == 0) {
            return new ContainerTask();
        }
        try {
            JAXBContext context = JAXBContext.newInstance(ContainerTask.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (ContainerTask) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Не удалось считать данные файла! Завершите программу, или данные будут утеряны!");
        }
        return new ContainerTask();
    }

    @Override
    public void writeTaskList(ContainerTask objTaskList, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ContainerTask.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(objTaskList, file);
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Не удалось записать данные в файл!");
        }
    }
}
