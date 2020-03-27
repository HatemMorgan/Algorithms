package combinatorialSearch;

import java.util.ArrayList;

/**
 * Backtracking algorithm fro finding Sudoku solution.
 *
 * The following heuristics can be used to prune search space and enhance this algorithm:
 *
 * 1- Constrained Square Selection: we check each of the open squares (i, j) to see how many
 *     number candidates remain for each, i.e. , have not already been used in either
 *     row i, column j, or the sector containing (i, j). We pick the square with the
 *     fewest number of candidates to be the next cell to process in the backtracking algorithm. This
 *     can be done efficiently in O(n log n) (n is size of board) by
 *          1- create a cell class that holds the position and number of remaining candidates
 *          2- create a Priority Queue to hold the cells and return the cell instance with smallest
 *             number of remaining candidates. Initially all the empty cells are in the queue.
 *          3- For each update in the board, we have to update the objects in the queue.
 *
 * 2- Look ahead termination: if the current partial solution has some other open square where
 *    there are no candidates remaining under the local count criteria. There is no possible way to
 *    complete this partial solution into a full Sudoku grid. Thus there really are zero possible
 *    moves to consider for (i, j) because of what is happening elsewhere on the board. We are much
 *    better off backtracking immediately and moving on. This can be done by checking while updating
 *    the queue, if an empty cell has no remaining candidates.
 *
 * Reference: The algorithm design manual book section 7.3
 */
public class Sudoku {

    static int dim;

    public static void print(ArrayList<String> board){
        // we got the answer, just print it
        StringBuilder builder = new StringBuilder();
        for (int r = 0; r < board.size(); r++)
        {
            for (int d = 0; d < board.size(); d++)
            {
                builder.append(board.get(r).charAt(d));
            }

            builder.append("  ");
        }

        System.out.println(builder.toString());
    }

    private static boolean isValidMove(ArrayList<ArrayList<Integer>> a, int row, int col, int num){
        // validate row
        for(int i=0; i<dim; ++i)
            if(a.get(row).get(i) == num)
                return false;

        // validate column
        for(int i=0; i<dim; ++i)
            if(a.get(i).get(col) == num)
                return false;

        // validate sub-grid
        int subgirdRowIdx = row / 3;
        int subgridColIdx = col / 3;
        int[] gridRowDim = new int[] {subgirdRowIdx * 3, subgirdRowIdx * 3 + 2};
        int[] gridColDim = new int[] {subgridColIdx * 3, subgridColIdx * 3 + 2};

        for(int i = gridRowDim[0]; i <= gridRowDim[1]; ++i)
            for(int j = gridColDim[0]; j <= gridColDim[1]; ++j)
                if(a.get(i).get(j) == num)
                    return false;

        return true;
    }


    private static int[] findNextCell(ArrayList<ArrayList<Integer>> a){
        for(int i=0; i<dim; ++i)
            for(int j=0; j<dim; ++j)
                if(a.get(i).get(j) == 0)
                    return new int[] {i, j};

        return null;
    }


    static boolean finished = false;

    private static void solveSudokuHelper(ArrayList<ArrayList<Integer>> a) {
        dim = a.size();

        int[] cell = findNextCell(a);
        if(cell == null){ // no remaining empty box
            finished = true;
            return;
        }

        int rowIdx = cell[0];
        int colIdx = cell[1];

        for(int num=1; num<=9; ++num){
            if(isValidMove(a, rowIdx, colIdx, num)){
                a.get(rowIdx).set(colIdx, num); // make move
                // print(a);
                solveSudokuHelper(a); // backtrack
                if(finished)
                    return; // found solution then terminate
                else
                    a.get(rowIdx).set(colIdx, 0); // undo move
            }
        }
    }

    /**
     * partial solution is the board after setting a free cell to a number.
     *
     * ConstructCandidates method:
     *
     * The candidates of the partial solution is to find an empty cell and assign numbers
     * to it if the assigning does not violate the constraint.
     *
     * isSolution method:
     *  set global finished to true if there is no more empty cells
     *
     * processSolution method:
     *  return the current board configuration
     *
     * makeMove method:
     *  add number to an empty square if it does not violate the constriants
     *
     * undoMove method:
     *  remove added number when the current number didnot result in a valid board after
     *  backtracking.
     *
     *
     * // Pruning ideas:
     *  1- Keep track of the numbers tried for each cell
     *  2- Find the next cell with smallest # not tried numbers
     *  3- Before assigning numbers to an empty square, search the whole grid for
     *     sqaures that has no possible assignments (so we will backtrack earlier).
     */
    public static void solveSudoku(ArrayList<String> a){
        ArrayList<ArrayList<Integer>> board = new ArrayList<>();
        for(int i=0; i<a.size(); ++i)
            board.add(new ArrayList<Integer>());

        for(int rowIdx=0; rowIdx < a.size(); ++rowIdx){
            for(int colIdx=0; colIdx < a.size(); ++colIdx){
                char c = a.get(rowIdx).charAt(colIdx);
                if(c == '.')
                    board.get(rowIdx).add(0);
                else
                    board.get(rowIdx).add(c - '0');
            }
        }

        solveSudokuHelper(board);

        for(int rowIdx=0; rowIdx < a.size(); ++rowIdx){
            StringBuilder builder = new StringBuilder();
            for(int colIdx=0; colIdx < a.size(); ++colIdx){
                builder.append((char) (board.get(rowIdx).get(colIdx)+'0'));
            }

            a.set(rowIdx, builder.toString());
        }

    }

    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        a.add("53..7....");
        a.add("6..195...");
        a.add(".98....6.");
        a.add("8...6...3");
        a.add("4..8.3..1");
        a.add("7...2...6");
        a.add(".6....28.");
        a.add("...419..5");
        a.add("....8..79");

        solveSudoku(a);

        // sol: 534678912, 672195348, 198342567, 859761423, 426853791, 713924856, 961537284, 287419635, 345286179
        print(a);
    }
}

