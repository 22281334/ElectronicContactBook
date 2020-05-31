import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Phone_Book {
    ArrayList<Person_Infomation> personList = new ArrayList<Person_Infomation>();
    String queryStr="";


    /**
     * Read contact Phone book add in to person list and clean useless information
     *
     * @param fileName input file name
     *
     */
    public void readPhonebook(String fileName) {
        String name = "";
        String birthday = "";
        String address = "";
        String email = "";
        String phone = "";
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] lineList = line.split(" ", 2);
                switch (lineList[0].toLowerCase()) {
                    case "name":
                        if (lineList.length > 1) {
                            name = lineList[1];
                        }
                        break;
                    case "birthday":
                        if (lineList.length > 1) {
                            birthday = lineList[1];
                            if (birthdayCheck(birthday)) {
                                String[] bithdaylist = birthday.split("-");
                                if (bithdaylist[0].trim().length() == 1) {
                                    bithdaylist[0] = "0" + bithdaylist[0].trim();
                                }
                                if (bithdaylist[1].trim().length() == 1) {
                                    bithdaylist[1] = "0" + bithdaylist[1].trim();
                                }

                                if (Integer.parseInt(bithdaylist[2]) <= 2020) {
                                    birthday = bithdaylist[0] + "-" + bithdaylist[1] + "-" + bithdaylist[2];
                                } else {
                                    birthday = "";
                                }

                            } else {
                                birthday = "";
                            }
                        }
                        break;
                    case "address":
                        if (lineList.length > 1) {
                            address = lineList[1];

                            if (address.substring(address.length() - 1, address.length()).equals(" ")) {
                                address += scanner.nextLine().trim() + " " + scanner.nextLine().trim();
                            }
                            String state = address.substring(address.length() - 4);
                            if (!state.matches("(^[0-9]{4}$)")) {
                                address = "";
                            }
                        }
                        break;
                    case "email":
                        if (lineList.length > 1) {
                            email = lineList[1];
                        }
                        break;
                    case "phone":
                        if (lineList.length > 1) {
                            phone = lineList[1];
                        }
                        break;
                    default:
                        break;
                }

                if (line.equals("") || scanner.hasNextLine() == false) {
                    if (!name.equals("") && !birthday.equals("")) {
                        if (nameCheck(name) && birthdayCheck(birthday)) {
                            Person_Infomation newPerson = new Person_Infomation(name, birthday);
                            phone = phoneCheck(phone);

                            email=emailCheck(email);
                            newPerson.setPhone(phone);
                            newPerson.setEmail(email);
                            newPerson.setAddress(address);
                            personList.add(newPerson);
                        }
                    }
                    name = "";
                    birthday = "";
                    address = "";
                    phone = "";
                    email = "";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check email information
     *
     * @param email  input String value of email
     *
     * @return good format of email
     *
     */
    public String emailCheck(String email) {
        int count = 0;
        for (String i : email.split("")) {
            if (i.equals("@")) {
                count += 1;
            }
        }
        if (count == 1) {
            return email;
        }
        return "";
    }

    /**
     * Check phone information
     *
     * @param personphone  input String value of phone number
     *
     * @return good format of phone
     *
     */
    public String phoneCheck(String personphone) {
        if (personphone.matches("(^[0][0-9]{9}$|[0-9]{8})|(^[9][0-9]{9}$|[0-9]{9})")) {
            if (personphone.substring(0, 1).equals("0")) {

                personphone = personphone.substring(1);
            }
            return personphone;
        }
        return "";
    }

    /**
     * Check name information
     *
     * @param name  input String value of name
     *
     * @return boolean
     *
     */
    private boolean nameCheck(String name) {
        if (name.matches("([a-z]|[A-Z]| )+")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Read instruction query process the query
     *
     * @param inputFile  input filename
     *
     * @param outputFile output filename
     *
     *
     */
    public void readInstruction(String inputFile, String outputFile) {
        try {
            File instruction = new File(inputFile);
            Scanner scanner = new Scanner(instruction);
            File report = new File(outputFile);
            PrintWriter out = new PrintWriter(report);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] linelist = line.trim().split(" ", 2);
                String command = linelist[0].trim();

                if (command.equalsIgnoreCase("add")) {
                    if (linelist.length > 1) {
                        String personInfo = linelist[1].trim();
                        add(personInfo);
                    }

                } else if (command.equalsIgnoreCase("delete")) {
                    if (linelist.length > 1) {
                        String deleteInfo = linelist[1].trim();
                        delete(deleteInfo);
                    }
                } else if (command.equalsIgnoreCase("query")) {
                    if (linelist.length > 1) {
                        queryStr+=query(linelist[1]);
                    }
                } else if (command.equalsIgnoreCase("save")) {
                    if (linelist.length >= 1) {
                        saveResult(out);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Print Query Report
     *
     * @param outputFile  output filename
     *
     */
    public void printQueryReport(String outputFile){
        try {
            saveQuery(queryStr,new PrintWriter(new File(outputFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check birthday information
     *
     * @param birthday  input String value of birthday
     *
     * @return boolean
     *
     */
    private boolean birthdayCheck(String birthday) {
        if (birthday.matches("(0?[1-9]|[12][0-9]|30|31])-(0?[1-9]|10|11|12)-\\d\\d\\d\\d")) {
            return true;
        }
        return false;
    }

    /**
     * Output file
     *
     * @param out  output filename
     *
     */
    private void saveResult(PrintWriter out) {
        for (int i = 0; i < personList.size(); i++) {
            if (!personList.get(i).getName().equals("")) {
                out.println("name: " + personList.get(i).getName());
            }
            if (!personList.get(i).getBirthday().equals("")) {
                out.println("birthday: " + personList.get(i).getBirthday());
            }
            if (!personList.get(i).getAddress().equals("")) {
                out.println("address: " + personList.get(i).getAddress());
            }
            if (!personList.get(i).getPhone().equals("")) {
                out.println("phone: " + personList.get(i).getPhone());
            }
            if (!personList.get(i).getEmail().equals("")) {
                out.println("email: " + personList.get(i).getEmail());
            }
            if (i <= personList.size() - 2) {
                out.println();
            }

        }
        out.close();
    }

    /**
     * Edit the String value ready to output
     *
     * @param commend  commend of instruction query
     *
     * @param info information after commend
     *
     * @param queryList
     *
     * @return Report String value
     *
     */
    private String queryResult(String commend,String info,ArrayList<Person_Infomation> queryList){
        String queryStr="====== query "+commend+" "+info+" ======\n";
        for (int i = 0; i < queryList.size(); i++) {
            if (!queryList.get(i).getName().equals("")) {
                queryStr+="name: "+queryList.get(i).getName()+"\n";
            }
            if (!queryList.get(i).getBirthday().equals("")) {
                queryStr+="birthday: " +queryList.get(i).getBirthday()+"\n";
            }
            if (!queryList.get(i).getAddress().equals("")) {
                queryStr+="address: " + queryList.get(i).getAddress()+"\n";
            }
            if (!queryList.get(i).getPhone().equals("")) {
                queryStr+="phone: " + queryList.get(i).getPhone()+"\n";
            }
            if (!queryList.get(i).getEmail().equals("")) {
                queryStr+="email: " +queryList.get(i).getEmail()+"\n";
            }
            queryStr+="\n";
        }
        queryStr+="====== end of query "+commend+" "+info+" ======\n\n";

        return queryStr;
    }

    /**
     * Find all matched person information
     *
     * @param s
     *
     * @return query Report String
     *
     */
    private String query(String s) {
        ArrayList<Person_Infomation> queryList=new ArrayList<Person_Infomation>();
        String queryStr="";
        String[] queryInfo=s.split(" ",2);
        String commend=queryInfo[0];
        String info=queryInfo[1].trim();
        if (commend.equals("name")){
            for (int i=0;i<personList.size();i++){
                if (personList.get(i).getName().equals(info)){
                    queryList.add(personList.get(i));
                }
            }
            queryStr+=queryResult(commend,info,queryList);
        }
        else if (commend.equals("birthday")){
            for (int i=0;i<personList.size();i++){
                if (personList.get(i).getBirthday().equals(info)){
                    queryList.add(personList.get(i));
                }
            }
            queryStr+=queryResult(commend,info,queryList);

        }
        else if (commend.equals("address")){
            for (int i=0;i<personList.size();i++){
                if (personList.get(i).getAddress().equals(info)){
                    queryList.add(personList.get(i));
                }
            }
            queryStr+=queryResult(commend,info,queryList);

        }
        else if (commend.equals("phone")){
            for (int i=0;i<personList.size();i++){
                if (personList.get(i).getPhone().equals(info)){
                    queryList.add(personList.get(i));
                }
            }
            queryStr+=queryResult(commend,info,queryList);

        }
        else if (commend.equals("email")){
            for (int i=0;i<personList.size();i++){
                if (personList.get(i).getEmail().equals(info)){
                    queryList.add(personList.get(i));
                }
            }
            queryStr+=queryResult(commend,info,queryList);

        }
        return queryStr;
    }

    private void saveQuery(String queryStr,PrintWriter out) {
        out.println(queryStr);
        out.close();
    }

    /**
     * Find and delete the matched person from the person list
     *
     * @param deleteInfo  input delete information
     *
     */
    private void delete(String deleteInfo) {
        String name = "";
        String birthday = "";
        String[] info = deleteInfo.split(";");
        for (String i : info) {
            name = info[0].trim();
            birthday = info[1].trim();

            if (birthdayCheck(birthday)) {
                String[] bithdaylist = birthday.split("-");
                if (bithdaylist[0].trim().length() == 1) {
                    bithdaylist[0] = "0" + bithdaylist[0].trim();
                }
                if (bithdaylist[1].trim().length() == 1) {
                    bithdaylist[1] = "0" + bithdaylist[1].trim();
                }
                birthday = bithdaylist[0] + "-" + bithdaylist[1] + "-" + bithdaylist[2];

            }

        }


        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getName().equals(name) && personList.get(i).getBirthday().equals(birthday)) {
                personList.remove(i);
            }
        }

    }

    /**
     * Add new contact information
     *
     * @param perInfo  person contact information
     *
     */
    public void add(String perInfo) {
        String name = "";
        String birthday = "";
        String address = "";
        String email = "";
        String phone = "";
        String[] info = perInfo.split(";");
        for (String i : info) {
            String[] temp = i.trim().split(" ", 2);
            if (temp[0].trim().equalsIgnoreCase("name")) {
                if (temp.length > 1) {
                    name = temp[1];
                }
            } else if (temp[0].trim().equalsIgnoreCase("birthday")) {
                if (temp.length > 1) {
                    birthday = temp[1];
                    if (birthdayCheck(birthday)) {
                        String[] birthdaylist = birthday.split("-");
                        if (birthdaylist[0].trim().length() == 1) {
                            birthdaylist[0] = "0" + birthdaylist[0].trim();
                        }
                        if (birthdaylist[1].trim().length() == 1) {
                            birthdaylist[1] = "0" + birthdaylist[1].trim();
                        }

                        if (Integer.parseInt(birthdaylist[2]) <= 2020) {
                            birthday = birthdaylist[0] + "-" + birthdaylist[1] + "-" + birthdaylist[2];
                        } else {
                            birthday = "";
                        }

                    } else {
                        birthday = "";
                    }

                }
            } else if (temp[0].trim().equalsIgnoreCase("address")) {
                if (temp.length > 1) {
                    address = temp[1];
                    String state = address.substring(address.length() - 4);
                    if (!state.matches("(^[0-9]{4}$)")) {
                        address = "";
                    }
                }
            } else if (temp[0].trim().equalsIgnoreCase("email")) {
                if (temp.length > 1) {
                    email = temp[1];
                    email=emailCheck(email);
                }
            } else if (temp[0].trim().equalsIgnoreCase("phone")) {
                if (temp.length > 1) {
                    phone = temp[1];
                }
            }
        }


        boolean inList = false;

        int temp = 0;
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getName().equals(name) && personList.get(i).getBirthday().equals(birthday)) {
                inList = true;
                temp = i;
            }
        }
        if (!inList) {
            if (!name.equals("") && !birthday.equals("")) {
                if (nameCheck(name) && birthdayCheck(birthday)) {
                    Person_Infomation newPerson = new Person_Infomation(name, birthday);
                    newPerson.setPhone(phoneCheck(phone));
                    newPerson.setEmail(emailCheck(email));
                    newPerson.setAddress(address);
                    personList.add(newPerson);
                }
            }
        } else {
            if (!address.equals("")) {
                personList.get(temp).setAddress(address);
            }
            if (!email.equals("")) {
                personList.get(temp).setEmail(email);
            }
            if (!phone.equals("")) {
                personList.get(temp).setPhone(phone);
            }
        }

    }

}
