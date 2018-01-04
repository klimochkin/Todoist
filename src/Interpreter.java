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

        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        String str;
        //String[] strSplit = null;

        Command com = new Command(new XmlSerializer());

        do {
            System.out.print(">>");
            str = buffer.readLine();

            String[] strSplit = str.split(" {1,}");
            str = strSplit[0];

            switch(str) {
                case "help":
                    com.help();
                    break;
                case "insert":
                    com.insertTask();
                    break;
                case "complete":
                    if (strSplit.length > 1)
                        com.complete(strSplit[1]);
                    else
                        System.out.println("Не указан идефикатор задачи!");
                    break;
                case "delete":
                    if (strSplit.length > 1)
                        com.delete(strSplit[1]);
                    else
                        System.out.println("Не указан идефикатор задачи!");
                    break;
                case "list":
                    com.list();
                    break;
                case "print":
                    com.printXML();
                    break;
                case "test":
                    com.test();
                    break;
                case "stop":
                    break;
                default:
                    System.out.println("Команда не найдена");
            }
        } while (!str.equals("stop"));
    }

}


