class AleaIactaEst {
    public static void main (String[] args) {
        System.out.println("Game Board: ");

        GameBoard board = new GameBoard();
        Token i_token = new Token("I", new int[][]{{0,0},{1,0},{2,0}});
        Token L_token = new Token("L", new int[][]{{0,0},{1,0},{2,0},{3,0},{0,1}});
        Token l_token = new Token("l", new int[][]{{0,0},{1,0},{2,0},{0,1}});
        Token Z_token = new Token("Z", new int[][]{{2,0},{0,1},{0,2},{1,1},{2,1}});
        Token M_token = new Token("M", new int[][]{{1,0},{2,0},{0,1},{0,2},{1,1}});
        Token F_token = new Token("F", new int[][]{{1,0},{2,0},{0,1},{1,1},{1,2}});
        Token S_token = new Token("S", new int[][]{{0,0},{1,0},{2,0},{2,1},{3,1}});
        Token C_token = new Token("C", new int[][]{{0,0},{1,0},{2,0},{0,1},{2,1}});
        Token P_token = new Token("P", new int[][]{{0,0},{0,1},{0,2},{1,1},{1,2}});

        board.placeToken(0,0,i_token,90);
        board.placeToken(0,1,Z_token,0);
        board.placeToken(3,0,L_token,0);
        //board.placeToken(4,4,l_token,0);
        board.placeToken(1,3,S_token,90);
        //board.gameBoard[0][0] = new Cell<Integer,String>(0, "C");
        System.out.println(board.toString());
    }
}