import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * Created by User1 on 02.01.2018.
 */

public class Interpreter {

    public static void main(String[] args) throws IOException, JAXBException, ParseException {

        System.out.println(" ");
        System.out.println("stop - завершить программу");
        System.out.println("help - вызов справки");
        System.out.println("Ведите команду: ");

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in))) {

            String str;

            Command com = new Command(new XmlSerializer());

            do {
                System.out.print(">>");
                str = buffer.readLine();

                String[] strSplit = str.split(" +");

                switch (strSplit[0]) {
                    case "help":
                        com.help();
                        break;
                    case "new":
                        com.addNewTask();
                        break;
                    case "complete":
                        if (strSplit.length > 1)
                            com.complete(strSplit[1]);
                        else
                            System.out.println("Не указан идефикатор задачи!");
                        break;
                    case "remove":
                        if (strSplit.length > 1)
                            com.remove(strSplit[1]);
                        else
                            System.out.println("Не указан идефикатор задачи!");
                        break;
                    case "list":
                        if (strSplit.length == 1)
                            com.list();
                        if (strSplit.length == 3)
                            com.findList(strSplit[1], strSplit[2]);
                        if (strSplit.length != 1 && strSplit.length != 3)
                            System.out.println("Не указан параметр");
                        break;
                    case "edit":
                        if (strSplit.length > 1)
                            com.edit(strSplit[1]);
                        else
                            System.out.println("Не указан идефикатор задачи!");
                        break;
                    case "print":
                        com.printXML();
                        break;
                    case "stop":
                        break;
                    default:
                        System.out.println("Команда не найдена");
                }
            } while (!str.equals("stop"));
        }
    }
}


