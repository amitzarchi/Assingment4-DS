public class SkipListExperimentUtils {
    public static double measureLevels(double p, int x) {
        double sum = 0;
        for (int i = 0; i < x; i++) {
            IndexableSkipList skipList = new IndexableSkipList(p);
            sum += skipList.generateHeight();
        }
        return sum / x;
    }


    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */
    public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static void main(String[] args) {
        System.out.println(measureLevels(0.33, 1));
        System.out.println(measureLevels(0.33, 5));
        System.out.println(measureLevels(0.33, 10));
        System.out.println(measureLevels(0.33, 100));
        System.out.println(measureLevels(0.33, 1000));
        System.out.println(measureLevels(0.33, 10000));

        System.out.println();

        System.out.println(measureLevels(0.5, 1));
        System.out.println(measureLevels(0.5, 5));
        System.out.println(measureLevels(0.5, 10));
        System.out.println(measureLevels(0.5, 100));
        System.out.println(measureLevels(0.5, 1000));
        System.out.println(measureLevels(0.5, 10000));

        System.out.println();

        System.out.println(measureLevels(0.75, 1));
        System.out.println(measureLevels(0.75, 5));
        System.out.println(measureLevels(0.75, 10));
        System.out.println(measureLevels(0.75, 100));
        System.out.println(measureLevels(0.75, 1000));
        System.out.println(measureLevels(0.75, 10000));

        System.out.println();

        System.out.println(measureLevels(0.9, 1));
        System.out.println(measureLevels(0.9, 5));
        System.out.println(measureLevels(0.9, 10));
        System.out.println(measureLevels(0.9, 100));
        System.out.println(measureLevels(0.9, 1000));
        System.out.println(measureLevels(0.9, 10000));
    }
}
