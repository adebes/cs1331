public class ArrayFun {

    // private static int[] a1 = {1, 2, 3};
    // private static int[] a2 = {1, 3, 5};

    public static String arraytoString(int[] a) {
        // [1, 2, 3]
        String result = "[";
        for (int i = 0; i < a.length; i++) {
            result += a[i];
            if (i < (a.length - 1)) {
                result += ",";
            }
        }
        return result + "]";
    }

    public static int sum(int[] b) {
        // an integer
        int result = 0;
        int i = 0;
        while (i < b.length) {
            result += b[i];
            i++;
        }
        return result;
    }

    public static void main(String[] args) {
        // int x = 1;
        // int y = x;
        // y = 2;
        // System.out.format("x: %d%n", x);
        // System.out.format("y: %d%n", y);

        // int[] a1 = {1, 2, 3};
        // int[] a2 = {1, 2, 3};
        // a2[0] = 42;
        // System.out.format("a1: %s%n", arraytoString(a1));
        // System.out.format("a2: %s%n", arraytoString(a2));

        int[] xs = {10, 20, -4};
        System.out.format("sum(%s) = %d%n", arraytoString(xs), sum(xs));
    }
}
