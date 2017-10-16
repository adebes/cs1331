public class Test2 {

    public static void main(String[] args) {
        String a_h = "hi";
        System.out.println((a_h + 4)); // can only concatenate
                                        //Strings and ints
        String[] b = new String[3]; // objects default to null value
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i]);
        }
    }
}

