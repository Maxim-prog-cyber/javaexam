package tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {

        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }
        if (x.isEmpty()) {
            return true;
        }
        if (y.isEmpty()) {
            return false;
        }


        int counter = 0;
        int temp = 0;

        for (int i = 0; i < x.size(); i++) {

            if (y.contains(x.get(i))) {

                for (int j = temp; j < y.size(); j++) {

                    if (x.get(i).equals(y.get(j))) {
                        counter++;
                        temp = +j;
                        if (x.size() == counter) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

