import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        IndexableSkipList skipList = new IndexableSkipList(p);
        List<Integer> l = new LinkedList<Integer>();
        for (int i = 0; i < size*2; i = i + 2) {
            l.add(i);
        }
        Collections.shuffle(l);
        double sum = 0;
        for (Integer integer : l) {
            double start = System.nanoTime();
            skipList.insert(integer);
            double end = System.nanoTime();
            sum += end - start;
        }
        return new Pair<AbstractSkipList, Double>(skipList, sum / size);
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
        List<Integer> l = new LinkedList<Integer>();
        for (int i = 0; i < size*2; i++) {
            l.add(i);
        }
        Collections.shuffle(l);
        double sum = 0;
        for (Integer integer : l) {
            double start = System.nanoTime();
            skipList.search(integer);
            double end = System.nanoTime();
            sum += end - start;
        }
        return sum / (size*2);
        
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        List<Integer> l = new LinkedList<Integer>();
        for (int i = 0; i < size*2; i = i + 2) {
            l.add(i);
        }
        Collections.shuffle(l);
        double sum = 0;
        for (Integer integer : l) {
            double start = System.nanoTime();
            skipList.delete(skipList.search(integer));
            double end = System.nanoTime();
            sum += end - start;
        }
        return sum / size;
    }

    public static void main(String[] args) {

        System.out.println("-------- Experiment 1 --------");
        List<Double> p = new LinkedList<Double>();
        p.add(0.33); p.add(0.5); p.add(0.75); p.add(0.9);
        List<Integer> x = new LinkedList<Integer>();
        x.add(1); x.add(5); x.add(10); x.add(100); x.add(1000); x.add(10000);
        for (Double d : p) {
            for (Integer i : x) {
                System.out.println("p = " + d + " -- x = " + i + " -- levels = " + measureLevels(d, i));
            }
            System.out.println();
        }

        System.out.println("-------- Experiment 2 --------");
        List<Integer> size = new LinkedList<Integer>();
        size.add(1000); size.add(2500); size.add(5000); size.add(10000); size.add(15000); size.add(20000); size.add(50000);
        System.out.println("Measurements for insertions:");
        for (double d : p) {
            for (Integer i : size) {
                double insertionAvg = 0;
                double searchAvg = 0;
                double deletionAvg = 0;
                for (int j = 0; j <= 30; j++) {
                    Pair<AbstractSkipList, Double> InsertionEx = measureInsertions(d, i);
                    insertionAvg += InsertionEx.second();
                    searchAvg += measureSearch(InsertionEx.first(), i);
                    deletionAvg += measureDeletions(InsertionEx.first(), i);

                }
                System.out.println("p = " + d + " -- size = " + i + " -- insertion = " + insertionAvg/30 + " -- search = " + searchAvg/30 + " -- deletion = " + deletionAvg/30);
            }
        }
        
    }
}
