import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Read_File {
    ArrayList<Person_Infomation> personList = new ArrayList<Person_Infomation>();

    public void ReadFile() {

    }

    public void readPhonebook(String fileName) {
        String name;
        String birthday;
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
