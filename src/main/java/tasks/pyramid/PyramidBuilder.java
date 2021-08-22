package tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        if (inputNumbers == null) {
            throw new CannotBuildPyramidException();
        }

        int size = inputNumbers.size();


        if (size > Integer.MAX_VALUE - 2 || inputNumbers.size() < 3) {
            throw new CannotBuildPyramidException();
        }
        for (int i = 0; i < inputNumbers.size(); i++) {
            if (inputNumbers.get(i) == null) {
                throw new CannotBuildPyramidException();
            }
        }


        int width = 1;
        int height = 1;
        int amount = 0;
        while (amount < size) {
            amount = amount + height;
            height++;
            width = width + 2;
        }

        height = height - 1;
        width = width - 2;
        if (amount != size) {
            throw new CannotBuildPyramidException();
        }
        Collections.sort(inputNumbers);
        int[][] pyramid = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pyramid[i][j] = 0;
            }
        }

        int center = (width / 2);

        int count = 1;

        int offset = 0;

        int listIndex = 0;
        for (int i = 0; i < height; i++) {
            int start = center - offset;
            for (int j = 0; j < count * 2; j += 2) {
                pyramid[i][start + j] = inputNumbers.get(listIndex);
                listIndex++;
            }
            offset++;
            count++;
        }

        return pyramid;

    }


}
