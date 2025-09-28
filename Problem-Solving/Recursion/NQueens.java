import java.util.*;
public class NQueens {
    /**
     * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
     * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
     *
     * Example:
     * Input n = 4
     * Output: 2
     * Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
     *
     * [
     *   [".Q..",  // Solution 1
     *    "...Q",
     *    "Q...",
     *    "..Q."],
     *   ["..Q.",  // Solution 2
     *    "Q...",
     *    "...Q",
     *    ".Q.."
     *    ]
     * ]
     *
     * Constraints:
     * 1. 1 <= n <= 9
     * 2. You may assume that n is a positive integer.
     *
     * @param n - The size of the chessboard and the number of queens to place.
     * @returns int - The number of distinct solutions to the n-queens puzzle.
     **/
    // Check if it's safe to place a queen at (row, col)
    public static boolean isSafe(int row, int col, char[][] board, int n) {
        int r = row, c = col;

        // Check upper-left diagonal
        while (r >= 0 && c >= 0) {
            if (board[r][c] == 'Q') return false;
            r--;
            c--;
        }

        // Check left row
        r = row; c = col;
        while (c >= 0) {
            if (board[r][c] == 'Q') return false;
            c--;
        }

        // Check lower-left diagonal
        r = row; c = col;
        while (r < n && c >= 0) {
            if (board[r][c] == 'Q') return false;
            r++;
            c--;
        }

        return true;
    }

    // Backtracking function
    public static int solve(int n, int col, char[][] board) {
        if (col == n) return 1; // Found a valid arrangement

        int count = 0;
        for (int row = 0; row < n; row++) {
            if (isSafe(row, col, board, n)) {
                board[row][col] = 'Q';
                count += solve(n, col + 1, board);
                board[row][col] = '.'; // backtrack
            }
        }
        return count;
    }
    public int totalNQueens(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(board[i], '.');
        return solve(n, 0, board);
    }

    /**
     * Main method for testing the NQueens class.
     */
    public static void main(String[] args) {
        NQueens nQueens = new NQueens();
        assert nQueens.totalNQueens(4) == 2 : "Test case 1 failed";
        assert nQueens.totalNQueens(1) == 1 : "Test case 2 failed";
        assert nQueens.totalNQueens(5) == 10 : "Test case 3 failed";
    }
}
