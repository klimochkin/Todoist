import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by User1 on 03.01.2018.
 */
public class XmlSerializer implements IObjectSerializer {


    @Override
    public ContainerTask readObject( File file) {
        if (!file.exists() || file.length() == 0) {
            return new ContainerTask();
        }
            try {
                // создаем объект JAXBContext - точку входа для JAXB

                JAXBContext context = JAXBContext.newInstance(ContainerTask.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();

                return (ContainerTask) unmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                e.printStackTrace();
            }

        return new ContainerTask();
    }


    @Override
    public void writeObject(ContainerTask objTaskList, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ContainerTask.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(objTaskList, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
