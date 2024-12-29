import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

class AleaIactaEst {
    
    public static List<Token> tokenList;
    public static List<Integer> diceRolls;
    public static List<Integer> winningNumbers; // dice rolls + 0
    public static GameBoard board;
    public static int countSolutions = 0;
    public static int updateSteps = 5000;

    public AleaIactaEst(){
        // create a new game board  (7x7 grid, each grid cell has a number associated to it and can be occupied by a token)
        board = new GameBoard();
        
        // define the layout of the different tokens available
        Token I_token = new Token("I", new int[][]{{0,0},{1,0},{2,0}});
        Token T_token = new Token("T", new int[][]{{0,0},{1,0},{2,0},{3,0},{0,1}});
        Token L_token = new Token("L", new int[][]{{0,0},{1,0},{2,0},{2,1}});
        Token Z_token = new Token("Z", new int[][]{{2,0},{0,1},{0,2},{1,1},{2,1}});
        Token M_token = new Token("M", new int[][]{{1,0},{2,0},{0,1},{0,2},{1,1}});
        Token F_token = new Token("F", new int[][]{{1,0},{2,0},{0,1},{1,1},{1,2}});
        Token S_token = new Token("S", new int[][]{{0,0},{1,0},{2,0},{2,1},{3,1}});
        Token C_token = new Token("C", new int[][]{{0,0},{1,0},{2,0},{0,1},{2,1}});
        Token P_token = new Token("P", new int[][]{{0,0},{0,1},{0,2},{1,1},{1,2}});
        
        // store all tokens in a list
        Token[] tokens = {I_token, T_token, L_token, Z_token, M_token, F_token, S_token, C_token, P_token};
        tokenList = new ArrayList<>();
        for(Token t : tokens){
            tokenList.add(t);
        }
        // shuffle the list, to find unique solutions
        Collections.shuffle(tokenList);

        // prompt the user to enter the dice values a solution should be found for
        diceRolls = getDiceInput();
        winningNumbers = new ArrayList<Integer>();
        for (int i : diceRolls){
            winningNumbers.add(i);
        }
        winningNumbers.add(0);

        System.out.println("Solving for dice rolls: "+diceRolls.toString());

        // for testing, whether to search for a result for the provided dice values or any result
        board.exactMatch = true;
    }
    public static void main (String[] args) {
        // create a new game object
        AleaIactaEst game = new AleaIactaEst();

        // solve layout for each token until a solution is found
        if (game.solve(0)) {
            System.out.println("Solution found:");

            // print final layout of tokens
            System.out.println(board.toString());
        } else {
            System.out.println("No solution exists.");
        }
    }

    // string in the form "[1,2,..]"
    public static List<Integer> getDiceInput(){
        List<Integer> diceRolls = new ArrayList<>();
        System.out.println("Add dice rolls in the form of: `[1,2,3,4,5,6]`");
        Scanner scanner = new Scanner(System.in);
        String diceString = scanner.nextLine();
        
        int[] diceArray = Stream.of(diceString.replaceAll("[\\[\\]\\, ]", "").split("")).mapToInt(Integer::parseInt).toArray();
        diceRolls = new ArrayList<Integer>(diceArray.length);

        for (int i : diceArray)
        {
            if (i > 0 && i < 7){
                diceRolls.add(i);
            }
        }
        if(diceRolls.size() == 6){
            scanner.close();
            return diceRolls;
        } else {
            System.out.println("Not a valid input. Try Again!");
            scanner.close();
            return getDiceInput();
        }
    }
     
    // min is inclusive, max is exclusive
    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    //Backtracking approach
    public boolean solve(int tokenIndex) {
        if (tokenIndex == tokenList.size()) {
            if(board.isSolved(winningNumbers)){
                return true;
            }else{
                countSolutions++;
                if(countSolutions%updateSteps==0){
                    // print progress
                    System.out.print(".");
                }
                if(countSolutions%(10*updateSteps)==0){
                    System.out.println("This is hard still solving!");
                }
                return false;
            }
        }

        Token token = tokenList.get(tokenIndex);
        List<int[][]> rotations = token.rotations;

        int rowStart = getRandomInt(0, 8);
        int colStart = getRandomInt(0, 8);
        int row = rowStart;
        int col = colStart;
        for (int i = 0; i<board.GRID_SIZE;i++) {
            for (int j = 0; j<board.GRID_SIZE;j++) {
                for (int[][] rotatedTile : rotations) {
                    if (board.canPlaceToken(row, col, rotatedTile)) {
                        // if we were able to place the token place the next token
                        board.placeToken(row, col, rotatedTile, token.getName());
                        //if(board.isSolutionStillPossible(winningNumbers)){
                            if (solve(tokenIndex + 1)) {
                                return true; // Found a solution
                            }
                        //}
                        // if we cannot place the next token, remove the last one
                        board.removeToken(row, col, rotatedTile); // Backtrack
                    }
                }
                col = (col+1)%7;
            }
            row = (row+1)%7;
        }
        return false; // No valid placement for this tile
    }
}