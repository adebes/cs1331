public class Loopy {

    public static void main(String[] args) {
        String aretha = "RESPECT YOURSELF";
        // for (int i = 0; i < 10; ++i) {
        //     System.out.format("Meow number %d%n", i);
        //     System.out.format("Mr. Larry Johnson%n");
        // }

        // for (int i = 0; i <= aretha.length() - 1; ++i) {
        //     System.out.println(aretha.charAt(i));
        // }

        char[] respect = new char[7];
        for (int i = 0; i < aretha.length(); i++) {
            respect[i] = aretha.charAt(i);
        }
    }
}
