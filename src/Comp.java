public class Comp {
    public static void main(String[] args) {
        Phone_Book main=new Phone_Book();
        main.readPhonebook(args[0]);
        main.readInstruction(args[1],args[2]);
        main.printQueryReport(args[3]);
    }
}
