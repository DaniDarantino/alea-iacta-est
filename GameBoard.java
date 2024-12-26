import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameBoard {

    Cell<Integer,String>[][] gameBoard;
    // whether to search for an exact match for the given dices (currently not working since algorithm is too slow)
    public boolean exactMatch = true; 
    int GRID_SIZE = 7;

    Cell<Integer, String> element0 = new Cell<Integer,String>(0, "");
    Cell<Integer, String> element1 = new Cell<Integer,String>(1, "");
    Cell<Integer, String> element2 = new Cell<Integer,String>(2, "");
    Cell<Integer, String> element3 = new Cell<Integer,String>(3, "");
    Cell<Integer, String> element4 = new Cell<Integer,String>(4, "");
    Cell<Integer, String> element5 = new Cell<Integer,String>(5, "");
    Cell<Integer, String> element6 = new Cell<Integer,String>(6, "");


    public GameBoard() {
        // initiallize game board with default values
        // every cell is empty and not occupied by a token
        gameBoard = new Cell[][]{
            {element0,element5,element4,element1,element3,element2,element6},
            {element6,element4,element0,element5,element2,element1,element3},
            {element2,element6,element1,element0,element5,element3,element4},
            {element3,element2,element5,element6,element1,element4,element0},
            {element4,element1,element3,element2,element0,element6,element5},
            {element5,element3,element2,element4,element6,element0,element1},
            {element1,element0,element6,element3,element4,element5,element2}
        };
    }

    // custom toString function used to print the game board to the console
    // when an cell is occupied, the name of the token occupying it is printed, otherwise, the dice value is printed or 0 in case of an empty cell
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(100);
        for(int i=0; i<gameBoard.length; i++) {
            
            if(i==0){
                str.append("   ||      0:    1:    2:    3:    4:    5:    6:   \n");
                str.append("---------------------------------------------------\n");
                str.append("---------------------------------------------------\n");
            }
            str.append(i+": ||\t|");
            for(int j=0; j<gameBoard[i].length; j++) {
                Cell<Integer,String> currElement = gameBoard[i][j];
                // if the current cell is occupied (it is not equal to "") write down the tile that this cell is occupied by
                if(currElement.getTokenStr() != ""){
                    str.append("  "+currElement.getTokenStr() + "  |");
                } else {
                    str.append("  "+currElement.getValue()+"  |");
                }
            }
            str.append("\n");
            str.append("\t-------------------------------------------\n");
        }
        return str.toString();
    }

    // test if a token can be placed at the given location in the given orientation 
    private boolean canPlaceToken(int startRow, int startCol, Token token, int rotation) {
        int[][] rotatedShape = token.rotate(rotation);

        for (int[] cell : rotatedShape) {
            int row = startRow + cell[0];
            int col = startCol + cell[1];

            // Check bounds
            if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE) {
                return false;
            }

            // Check if the cell is already occupied
            if (gameBoard[row][col].getTokenStr() != "") {
                return false;
            }
        }
        return true;
    }

    // actually place the token if it is possible
    public boolean placeToken(int startRow, int startCol, Token token, int rotation) {
        if(canPlaceToken(startRow, startCol, token, rotation)){
            int[][] rotatedShape = token.rotate(rotation);
            for(int[] cell : rotatedShape){
                //System.out.println(Arrays.toString(cell));
                int row = 0;
                int col = 0;
                for(int i = 0; i < cell.length; i++){
                    if(i == 0){
                        row = startRow + cell[i];
                    }else{
                        col = startCol + cell[i];
                    }
                }
                //System.out.println("row: "+row+" column: "+col);
                Cell<Integer,String> cc = gameBoard[row][col];
                gameBoard[row][col] = new Cell<Integer,String>(cc.getValue(), token.getName());
            }
            return true;
        } else {
            //System.out.println("CANNOT place token: " + token.getName()+ " at location "+startRow+","+startCol);
            return false;
        }
    }
    
    public void removeToken(int startRow, int startCol, Token token) {
            //System.out.println("row: "+row+" column: "+col);

        for (int[] cell : token.getShape()) {
            int row = startRow + cell[0];
            int col = startCol + cell[1];
            Cell<Integer,String> cc = gameBoard[row][col];
            gameBoard[row][col] = new Cell<Integer,String>(cc.getValue(), "");
        }
    }

    // check if the current board state is a valid solution
    public boolean isSolved(List<Integer> diceRolls) {
        
        List<Integer> winingNumbers = diceRolls;
        // we always need to have an empty field left (indicated by 0) which we add to the dice rolls
        winingNumbers.add(0);
        // Count empty cells
        int emptyCount = 0;
        for (Cell[] row : gameBoard) {
            for (Cell cell : row) {
                if (cell.getTokenStr() == "") {
                    emptyCount++;
                }
            }
        }
        // Check if empty cells match dice rolls
        if (emptyCount != 7) {
            return false;
        }
        List<Integer> emptyNumbers = new ArrayList<>();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // check if current cell is not occupied
                if (gameBoard[i][j].getTokenStr() == "") {
                    emptyNumbers.add((int) gameBoard[i][j].getValue());
                }
            }
        }

        // Compare empty cell numbers with winning numbers (dice rolls plus empty cell (0))
        if(exactMatch){
            Collections.sort(emptyNumbers);
            Collections.sort(winingNumbers);
            return winingNumbers.equals(emptyNumbers);
        } else {
            // for now, just check if i have 7 empty cells with one cell having value 0
            int emptyCells = 0;
            for (Integer i : emptyNumbers) {
                if(i==0) emptyCells++;
            }
            if(emptyCells==1){
                System.out.println("\nFound match for dices: "+emptyNumbers.toString());
                return true;
            } else {
                return false;
            }
        }
    }
}
