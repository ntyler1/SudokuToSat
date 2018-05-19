import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Noah Tyler 
 */
public class SudokuToSatReducer {

    private final PrintWriter writer;
    static final String tempFileName = "temp.cnf";
    private SudokuBoard board;
    private String extraClauses = "";

    public SudokuToSatReducer(File inputFile) throws FileNotFoundException {
        writer = new PrintWriter(tempFileName);
        int numExtraClauses = createBoard(inputFile);
        write("p cnf\t" + board.getNumberOfCells() * board.getBoardSize() + "\t" + (((board.getBoardSize()*(board.getBoardSize() - 1))/2 + 1) * board.getNumberOfCells() * 4 + numExtraClauses) + "\r\n");
        Timer timer = new Timer();
        timer.start();
        reduceBoard();
        timer.stop();
        System.out.println("Total SAT Reducer Execution Time: " + timer.getDuration() + " ms!");
    }

    private int createBoard(File file) throws FileNotFoundException {
        int numExtraClauses = 0;
        Scanner fileScanner = new Scanner(file);
        board = new SudokuBoard(fileScanner.nextInt(), fileScanner.nextInt());
            for (int i = 0; i < board.getBoardSize(); i++)
                for (int j = 0; j < board.getBoardSize(); j++) {
                    int value = fileScanner.nextInt();
                    if (value != 0) {
                        numExtraClauses++;
                        extraClauses += convert(i,j,value)+"\t0\r\n";
                        board.set(i, j, value);  
                    }
                }
        return numExtraClauses;
    }

    private void reduceBoard() {
        write(extraClauses);
        for (int i = 0; i < board.getBoardSize(); i++){
            for (int value = 1; value <= board.getBoardSize(); value++){
                atLeastOneInRow(i, value);
                atMostOneInRow(i, value);
                atLeastOneInCol(i, value);
                atMostOneInCol(i, value);
                atLeastOneInBox(i, value);
                atMostOneInBox(i, value);
            }
            atLeastOneInRowAndCol(i);
            atMostOneInRowAndCol(i);
        }
    }

    private void atLeastOneInRow(int row, int value) {
        for (int j = 0; j < board.getBoardSize(); j++)
            write(convert(row, j, value) + "\t");
        write("0\r\n");
    }

    private void atMostOneInRow(int row, int value) {
        for (int j = 0, k = 1; j < board.getBoardSize() - 1; k++) {
            write(-convert(row, j, value) + "\t" + -convert(row, k, value) + "\t0\r\n");
            if (k == board.getBoardSize() - 1){
                j++;
                k = j;
            }
        }
    }

    private void atLeastOneInCol(int col, int value) {
        for (int i = 0; i < board.getBoardSize(); i++)
             write(convert(i, col, value) + "\t");
        write("0\r\n");
    }

    private void atMostOneInCol(int col, int value) {
        for (int i = 0, k = 1; i < board.getBoardSize() - 1; k++) {
            write(-convert(i, col, value) + "\t" + -convert(k, col, value) + "\t0\r\n");
            if (k == board.getBoardSize() - 1) {
                i++;
                k = i;
            }
        }
    }

    private void atLeastOneInBox(int boxNum, int value) {
        for (int i = board.getBoxHeight() * (boxNum / board.getBoxHeight()); i < board.getBoxHeight() + board.getBoxHeight() * (boxNum / board.getBoxHeight());i++)
            for (int j = board.getBoxWidth() * (boxNum % board.getBoxWidth()); j < board.getBoxWidth() + board.getBoxWidth() * (boxNum % board.getBoxWidth()); j++)
                write(convert(i, j, value) + "\t");
        write("0\r\n");
    }

    private void atMostOneInBox(int boxNum, int value) {
        int[] boxVars = new int[board.getBoardSize()];
        for (int i = board.getBoxHeight() * (boxNum / board.getBoxHeight()), curpos = 0; i < board.getBoxHeight() + board.getBoxHeight() * (boxNum / board.getBoxHeight()); i++)
                for (int j = board.getBoxWidth() * (boxNum % board.getBoxWidth()); j < board.getBoxWidth() + board.getBoxWidth() * (boxNum % board.getBoxWidth()); j++) {
                    boxVars[curpos] = convert(i, j, value);
                    curpos++;
                }       
        for (int i = 0, j = 1; i < board.getBoardSize() - 1; j++) {
            write(-boxVars[i] + "\t" + -boxVars[j] + "\t0\r\n");
            if (j == board.getBoardSize() - 1) {
                i++;
                j = i;
            }
        }
    }

    private void atLeastOneInRowAndCol(int row) {
        for (int j = 0; j < board.getBoardSize(); j++){
            for(int value = 1; value <= board.getBoardSize(); value++)
                write(convert(row, j, value) + "\t");
            write("0\r\n");
        }
    }

    private void atMostOneInRowAndCol(int row) {
        for(int j = 0; j < board.getBoardSize(); j++)
            for (int x = 1, y = 2; x < board.getBoardSize(); y++) {
                write(-convert(row, j, x) + "\t" + -convert(row, j, y)  + "\t0\r\n");
                if (y == board.getBoardSize()) {
                    x++;
                    y = x;
                }
            }
    }

    private void write(String s) {
        writer.print(s);
        writer.flush();
    }

    private int convert(int row, int col, int value) {
        return row * board.getNumberOfCells() + col * board.getBoardSize() + value;
    }  
}