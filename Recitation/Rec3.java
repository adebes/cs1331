public class Rec3 {

    public static void main(String[] args) {
        int[][] arr1 = new int[5][5];
        int[][] arr2 = {{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}};
        // for (int[] s : arr2) {
        //     for (int a : s) {
        //         System.out.println(a);
        //     }
        // }
        System.out.println(max(1, 8, 10, 5));
    }

    public static int max(int... numbers) {
        int[] other = numbers;
        int max = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            max = max < numbers[i] ? numbers[i] : max;
        }
        return max;
    }
}
