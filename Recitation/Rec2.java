import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Rec2 {
    public static void main(String[] args) {
        // int i = 0;
        // do {
        //     System.out.println(i);
        //     i++;
        // }while (i < 10);

        // int k = 0;
        // for(; k < 10; k++) {
        //     int j = k + 2;
        //     System.out.println(j);

        // Scanner scan = new Scanner(System.in);
        // String s = "";
        // do {
        //     System.out.println("Enter y for yes, n for no:");
        //     s = scan.nextLine();
        // }while(!(s.equals("y") || s.equals("n")));

        // System.out.println("What is your age?");
        // int age = scan.nextInt();
        // scan.nextLine();
        // System.out.println("What is your favorite color?");
        // String color = scan.nextLine();
        // System.out.println("I like " + color + " too!");

        // Scanner scan = new Scanner(new File("text.txt"));
        // while(scan.hasNextLine()) {
        //     System.out.println(scan.nextLine());
        // }

        // PrintStream outFile = new PrintStream(new File("somefile.txt"));
        // outFile.println("Hello World");
        // for(int i = 0; i < 10; i++) {
        //     outFile.println(i);
        // }
        // outFile.flush();
        // scan.close();
        // outFile.close();

        // int[] scores = new int[5];
        // scores[4] = 7;
        // scores[3] = 6;
        // for(int i = 0; i < scores.length; i++) {
        //     System.out.println(scores[i]);
        // }

        for(int i = 0; i < args.length; i++) {
            String s = args[i];
            System.out.println(s);
        }

        for(String s : args) {
            System.out.println(s);
        }
    }
}
