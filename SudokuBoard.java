/**
 * @author NoahTyler
 */
public class SudokuBoard {

    private final int[] boardCells;
    private final int boxHeight, boxWidth, boardSize, numOfCells;

    public SudokuBoard(int bHeight, int bWidth) {
        boxHeight = bHeight;
        boxWidth = bWidth;
        boardSize = boxHeight * boxWidth;
        numOfCells = boardSize * boardSize;
        boardCells = new int[numOfCells];
    }

    public void set(int row, int column, int value){
        boardCells[row*getBoardSize()+column] = value;
    }
    
    @Override
    public String toString() {
        String board = boardCells[0] + " "; //account for zero in mod == 0
        for (int index = 1; index < boardCells.length; index++)
            board += index % boardSize != 0 
                    ? boardCells[index] + " " 
                    : "\n" + boardCells[index] + " ";
        return board;
    }
    
    public int getBoxNumber(int cell){  
        return getRow(cell)/boxHeight*boxWidth + getColumn(cell)/boxWidth;
    }

    public int getRow(int cell){
        return cell/boardSize;
    }
    
    public int getColumn(int cell){
        return cell%boardSize;
    }
    
    public int getValue(int cell){
        return boardCells[cell];
    }
    
    public int getBoxHeight() {
        return boxHeight;
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getNumberOfCells() {
        return numOfCells;
    }
}