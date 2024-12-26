import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

class AleaIactaEst {
    
    public static List<Token> tokenList;
    public static List<Integer> diceRolls;
    public static GameBoard board;
    public static void main (String[] args) {
        System.out.println("Game Board: ");
        board = new GameBoard();
        
        // define layout of each token
        Token I_token = new Token("I", new int[][]{{0,0},{1,0},{2,0}});
        Token T_token = new Token("T", new int[][]{{0,0},{1,0},{2,0},{3,0},{0,1}});
        Token L_token = new Token("L", new int[][]{{0,0},{1,0},{2,0},{2,1}});
        Token Z_token = new Token("Z", new int[][]{{2,0},{0,1},{0,2},{1,1},{2,1}});
        Token M_token = new Token("M", new int[][]{{1,0},{2,0},{0,1},{0,2},{1,1}});
        Token F_token = new Token("F", new int[][]{{1,0},{2,0},{0,1},{1,1},{1,2}});
        Token S_token = new Token("S", new int[][]{{0,0},{1,0},{2,0},{2,1},{3,1}});
        Token C_token = new Token("C", new int[][]{{0,0},{1,0},{2,0},{0,1},{2,1}});
        Token P_token = new Token("P", new int[][]{{0,0},{0,1},{0,2},{1,1},{1,2}});
        
        Token[] tokens = {I_token, T_token, L_token, Z_token, M_token, F_token, S_token, C_token, P_token};
        tokenList = new ArrayList<>();
        for(Token t : tokens){
            tokenList.add(t);
        }

        // Get dice input
        //List<Integer> diceRolls = getDiceInput();

        // hardcode dice input for testing
        diceRolls = new ArrayList<>(){{add(6);add(5);add(3);add(4);add(5);add(6);}};
        System.out.println("Solving for dice rolls: "+diceRolls.toString());
        
        int iterations = 0;
        int thousands = 0;

        while(!board.isSolved(diceRolls)){
            iterations++;
            // progress bar
            if(iterations % 10 == 0){
                thousands++;
                System.out.println(thousands+".., ");
            }

            Collections.shuffle(tokenList);
            board = new GameBoard();
            board.exactMatch = false;

            for (Token token : tokenList) {
                boolean bTokenWasPlaced = false;
                System.out.println("\nTrying to place token: "+token.getName());
                int tokenIterator = 0;
                int progressSteps = 100;
                int maxProgress = 10*progressSteps;

                // try to place token at random point until it was placed
                while(!bTokenWasPlaced){
                    tokenIterator++;
                    if(tokenIterator % progressSteps == 0){
                        System.out.print(".");
                    }
                    if(tokenIterator % maxProgress == 0) {
                        // abort after certain amount of tries to speed up search
                        break;
                    }
                    int row = getRandomInt(0,8);
                    int col = getRandomInt(0,8);
                    int rot = getRandomInt(0,4) * 90;
                    bTokenWasPlaced = board.placeToken(row, col, token, rot);
                }
            }
        }
        System.out.println(board.toString());
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
    /* TODO make algorithm faster by backtracking
    public boolean solve(int tileIndex) {
        if (tileIndex == tokenList.size()) {
            // Base case: All tiles placed, validate empty cells
            return board.isSolved(diceRolls);
        }

        Token token = tokenList.get(tileIndex);
        List<int[][]> rotations = token.rotations;

        for (int row = 0; row < board.GRID_SIZE; row++) {
            for (int col = 0; col < board.GRID_SIZE; col++) {
                for (int[][] rotatedTile : rotations) {
                    if (board.placeToken(row, col, rotatedTile) {
                         // Mark tile placement
                        if (solve(tileIndex + 1)) {
                            return true; // Found a solution
                        }
                        board.removeToken(row, col, rotatedTile); // Backtrack
                    }
                }
            }
        }

        return false; // No valid placement for this tile
    }*/
}