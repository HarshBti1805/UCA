public class SudokuSolver {
    public static boolean isSafe(int i, int j, char num, char[][] board) {
        for(int k = 0 ; k < 9 ; k++){
            if(board[k][j] == num || board[i][k] == num) return false;
            if(board[3 * (i / 3) + k / 3][3 * (j / 3) + k % 3] == num) 
                return false; 
        }
        return true;
    }
    public static boolean solve(char[][] board){
        int n = board.length, m = board[0].length;

        for(int i  = 0 ; i < 9; i++){
            for(int j = 0 ; j < 9; j++){
                if(board[i][j] == '.'){
                    for(char num = '1'; num <= '9' ; num++){
                        if(isSafe(i,j,num, board)){
                            board[i][j] = num;
                            if(solve(board) == true) return true;
                            board[i][j] = '.';
                        }
                    }
                    return false;
                } 
            }
        }
        return true;

    }
    public static void main(String[] args){
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
            };
        solve(board);

        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

    }
}
