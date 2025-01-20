import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SudokuGenerator {

    private Difficulty difficulty;

    public SudokuGenerator(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void generateWholeGrid() {
        int[][] grid = createEmptyGrid();

        fillDiagonalBoxes(grid);

        fillGrid(grid);
    }

    public int[][] createEmptyGrid() {
        int[][] grid = new int[9][9];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = 0;
            }
        }

        return grid;
    }

    public void fillDiagonalBoxes(int[][] grid) {
        for (int i = 0; i < 9; i+=3) {
            fillBoxDiagonal(grid, i, i);
        }
    }

    public void fillBoxDiagonal(int[][] grid, int x, int y) {
        Set<Integer> numsInBox = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                while (true) {
                    int num = random.nextInt(9);
                    num++;

                    if(!numsInBox.contains(num)) {
                        numsInBox.add(num);
                        grid[x+i][y+j] = num;
                        break;
                    }
                }
            }
        }
    }

    public void fillGrid(int[][] grid) {

    }

}
