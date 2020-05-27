import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Read_File {
    ArrayList<Person_Infomation> personList = new ArrayList<Person_Infomation>();

    public void ReadFile() {

    }

    public void readPhonebook(String fileName) {
        String name = null;
        String birthday = null;
        String address=null;
        String email=null;
        String phone=null;
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineList = line.trim().split(" ",2);
                System.out.println(lineList[0]);
                switch (lineList[0].trim().toLowerCase()){
                    case "name":
                        if (lineList.length>1){
                            name=lineList[1].trim();
                        }
                        System.out.println(name);

                        break;
                    case "birthday":
                        if (lineList.length>1){
                            birthday=lineList[1].trim();
                        }
                        System.out.println(birthday);
                        break;
                    case "address":
                        if (lineList.length>1){
                            address=lineList[1].trim();
                        }
                        System.out.println(address);
                        break;
                    case "email":
                        if (lineList.length>1){
                            email=lineList[1].trim();
                        }
                        System.out.println(email);
                        break;
                    case "phone":
                        if (lineList.length>1){
                            phone=lineList[1].trim();
                        }
                        System.out.println(phone);
                        break;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }



    public void readInstruction(String arg1, String arg2) {
        try {
            File instruction = new File(arg1);
            Scanner s = new Scanner(instruction);
            File report = new File(arg2);
            PrintWriter out = new PrintWriter(report);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] linelist = line.trim().split(" ", 2);
                if (linelist[0].trim().equalsIgnoreCase("add")) {
                    if (linelist.length > 1) {
                        add(linelist[1], out);
                    }
                } else if (linelist[0].trim().equalsIgnoreCase("delete")) {
                    if (linelist.length > 1) {
                        delete(linelist[1], out);
                    }
                } else if (linelist[0].trim().equalsIgnoreCase("query")) {
                    if (linelist.length > 1) {
                        query(linelist[1], out);
                    }
                } else if (linelist[0].trim().equalsIgnoreCase("save")) {
                    if (linelist.length > 1) {
                        save(linelist[1], out);
                    }
                }
            }
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void save(String s, PrintWriter out) {

    }

    private void query(String s, PrintWriter out) {

    }

    private void delete(String s, PrintWriter out) {

    }

    public void add(String s, PrintWriter out){

    }


}
