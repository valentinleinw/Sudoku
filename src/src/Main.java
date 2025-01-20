public class Main {
    public static void main(String[] args) {

        SudokuGenerator sg = new SudokuGenerator(Difficulty.EASY);

        sg.generateWholeGrid();

        System.out.println(sg);

    }
}