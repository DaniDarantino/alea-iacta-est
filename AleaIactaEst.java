import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

class AleaIactaEst {
    public static void main (String[] args) {
        System.out.println("Game Board: ");

        GameBoard board = new GameBoard();

        Token I_token = new Token("I", new int[][]{{0,0},{1,0},{2,0}});
        Token T_token = new Token("T", new int[][]{{0,0},{1,0},{2,0},{3,0},{0,1}});
        Token L_token = new Token("L", new int[][]{{0,0},{1,0},{2,0},{0,1}});
        Token Z_token = new Token("Z", new int[][]{{2,0},{0,1},{0,2},{1,1},{2,1}});
        Token M_token = new Token("M", new int[][]{{1,0},{2,0},{0,1},{0,2},{1,1}});
        Token F_token = new Token("F", new int[][]{{1,0},{2,0},{0,1},{1,1},{1,2}});
        Token S_token = new Token("S", new int[][]{{0,0},{1,0},{2,0},{2,1},{3,1}});
        Token C_token = new Token("C", new int[][]{{0,0},{1,0},{2,0},{0,1},{2,1}});
        Token P_token = new Token("P", new int[][]{{0,0},{0,1},{0,2},{1,1},{1,2}});

        Token[] tokens = {I_token, T_token, L_token, Z_token, M_token, F_token, S_token, C_token, P_token};
        List<Token> tokenList = new ArrayList<>();
        for(Token t : tokens){
            tokenList.add(t);
        }

        // Get dice input
        //List<Integer> diceRolls = getDiceInput();
        List<Integer> diceRolls = new ArrayList<>(){{add(1);add(1);add(1);add(1);add(1);add(1);}};
        System.out.println("Solving for dice rolls: "+diceRolls.toString());
        
        int iterations = 0;
        int thousands = 0;

        while(!board.isSolved(diceRolls)){
            iterations++;
            if(iterations % 10 == 0){
                thousands++;
                System.out.println(thousands+".., ");
            }
            Collections.shuffle(tokenList);
            board = new GameBoard();
            for (Token token : tokenList) {
                boolean bTokenWasPlaced = false;
                System.out.println("Trying to place token: "+token.getName());
                int tokenIterator = 0;
                int progressSteps = 1;
                int maxProgress = 10*progressSteps;
                // try to place token at random point until it was placed
                while(!bTokenWasPlaced){
                    tokenIterator++;
                    if(tokenIterator % progressSteps == 0){
                        System.out.print(".");
                    }
                    if(tokenIterator % maxProgress == 0) {
                        // abort and start new placement
                        break;
                    }
                    int row = getRandomInt(0,8);
                    int col = getRandomInt(0,8);
                    int rot = getRandomInt(0,4) * 90;
                    bTokenWasPlaced = board.placeToken(row, col, token, rot);
                }
                System.out.println("Placement found for token: "+token.getName());
                System.out.println(board.toString());
            }
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
            return diceRolls;
        } else {
            System.out.println("Not a valid input. Try Again!");
            return getDiceInput();
        }
    }
    // min is inclusive, max is exclusive
    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}