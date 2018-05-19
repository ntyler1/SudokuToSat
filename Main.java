
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Noah Tyler
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if (!new File(args[0]).exists()) // check if a new file can be made
        {
            System.out.println("ERROR: A file object was not made from given filename!");
            System.exit(0);
        
        }
        new SudokuToSatReducer(new File(args[0]));
    } 
}
