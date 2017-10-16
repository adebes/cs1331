public class FinalTarget {

    public static void main(String[] args) {
        double hwAvg = 80;
        double examAvg = 85;
        double targetAvg = 80;
        double requiredFinalScore = (targetAvg
                                        - (0.2*hwAvg)
                                        - (0.6*examAvg))
                                        / 0.2;
        System.out.println("Given a hw avg of " + hwAvg
            + " and an exam avg of " + examAvg
            + " you need score of " + requiredFinalScore
            + " to get a final course avg of " + targetAvg);
    }
}
