import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SudokuGenerator {

    private Difficulty difficulty;
    private final Random random = new Random();

    private final int BOX_SIZE = 3;
    private final int ROW_LENGTH = 9;
    private final int COLUMN_LENGTH = 9;

    private int[][] grid = new int[ROW_LENGTH][COLUMN_LENGTH];

    public SudokuGenerator(Difficulty difficulty) {
        this.difficulty = difficulty;

        this.grid = createEmptyGrid();
    }

    public void generateWholeGrid() {

        fillDiagonalBoxes();

        fillGridUsingBacktracking();

        removeDigitsBasedOnDifficulty();
    }

    private void removeDigitsBasedOnDifficulty() {

        int cellsToRemove = 0;

        switch (difficulty) {
            case EASY -> cellsToRemove = 30;
            case MEDIUM -> cellsToRemove = 45;
            case HARD -> cellsToRemove = 60;
        }

        int count = 0;
        while (count < cellsToRemove) {
            int row = random.nextInt(ROW_LENGTH);
            int col = random.nextInt(COLUMN_LENGTH);

            // Ensure the cell is not already 0 (blank)
            if (grid[row][col] != 0) {
                grid[row][col] = 0;  // Set the cell to 0 (remove the digit)
                count++;
            }
        }

    }

    private int[][] createEmptyGrid() {

        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COLUMN_LENGTH; j++) {
                grid[i][j] = 0;
            }
        }

        return grid;
    }

    private void fillDiagonalBoxes() {
        for (int i = 0; i < ROW_LENGTH; i+=3) {
            fillBoxDiagonal(i, i);
        }
    }

    private void fillBoxDiagonal(int x, int y) {
        Set<Integer> numsInBox = new HashSet<>();

        for (int i = 0; i < BOX_SIZE; i++) {
            for (int j = 0; j < BOX_SIZE; j++) {
                while (true) {
                    int num = generateNewNumber();

                    if(!numsInBox.contains(num)) {
                        numsInBox.add(num);
                        grid[x+i][y+j] = num;
                        break;
                    }
                }
            }
        }
    }

    private boolean fillGrid(int row, int col) {
        if (row == ROW_LENGTH) {
            return true;  // Finished filling the grid
        }
        if (col == COLUMN_LENGTH) {
            return fillGrid(row + 1, 0);  // Move to the next row
        }

        if (grid[row][col] != 0) {
            return fillGrid(row, col + 1);  // Skip pre-filled cells
        }

        for (int num = 1; num <= 9; num++) {
            if (isSafe(row, col, num)) {
                grid[row][col] = num;
                if (fillGrid(row, col + 1)) {
                    return true;
                }
                grid[row][col] = 0;  // Backtrack
            }
        }
        return false;
    }

    private void fillGridUsingBacktracking() {
        if (!fillGrid(0, 0)) {
            System.out.println("Grid couldn't be generated.");
        }
    }

    private boolean isSafe(int row, int col, int num) {
        return !isInRow(num, row) && !isInColumn(num, col) && !isInBox(num, row - row % 3, col - col % 3);
    }

    public boolean isInBox(int num, int x, int y) {
        for (int i = 0; i < BOX_SIZE; i++) {
            for (int j = 0; j < BOX_SIZE; j++) {
                if (grid[x + i][y + j] == num) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isInRow(int num, int row) {
        for (int i = 0; i < ROW_LENGTH; i++) {
                if (grid[row][i] == num) {
                    return true;
                }
        }

        return false;
    }

    public boolean isInColumn(int num, int column) {
        for (int i = 0; i < COLUMN_LENGTH; i++) {
            if (grid[i][column] == num) {
                return true;
            }
        }

        return false;
    }

    private int generateNewNumber() {
        return random.nextInt(9) + 1;
    }

    public int[][] getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COLUMN_LENGTH; j++) {
                result.append(grid[i][j]);
                if (j < COLUMN_LENGTH - 1) {
                    result.append("|");
                }
            }
            result.append("\n");
            if (i % 3 == 2 && i < ROW_LENGTH - 1) {
                result.append("---------------------\n");
            }
        }
        return result.toString();
    }

}
