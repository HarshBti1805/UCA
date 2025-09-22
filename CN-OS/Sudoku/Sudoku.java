import java.util.*;
public class Sudoku {
    public static boolean isSudokuSolvable(char[][] board){
        HashSet<String> seen = new HashSet<>();

        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                    char num = board[i][j];
                    if(num != '.'){
                        String row = num + "r" + i;
                        String col = num + "c" + j;
                        String box = num + "b" + (i / 3) + "-" + (j / 3);

                        if(seen.contains(row) || seen.contains(col) || seen.contains(box)) 
                            return false;

                        seen.add(row);
                        seen.add(col);
                        seen.add(box);
                    }
                    
                }
        }
        return true;

    }
    public static void main(String[] args){
        char[][] board1 = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        char[][] board2 = {
            {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        System.out.println(isSudokuSolvable(board1));
        System.out.println(isSudokuSolvable(board2));
    }
}
