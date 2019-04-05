package PFModules;

import Utils.Board;

import java.util.PriorityQueue;

public class Astar implements PFAlg{
    private PriorityQueue<Board> pq = new PriorityQueue<>();
    int endRow;
    int endCol;
    Integer[][] persistenceRefernce;
    Integer[] path;
    int dimension;
    int startRow;
    int startCol;

    public Astar(Integer[][] persistenceRefernce, int startRow, int startCol, int endRow, int endCol) {
        this.persistenceRefernce = persistenceRefernce;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.dimension = persistenceRefernce[0].length;
        path = new Integer [dimension*dimension];
        for (int i = 0; i < dimension * dimension; i++){
            path[i] = -1;
        }
        findPath();
    }

    private void print(Integer[][] state){
        for (Integer row = 0; row < state.length; row++){
            for ( Integer col = 0; col < state[0].length; col++){
                System.out.print(state[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int manhattanDistance(int row, int col){
        return Math.abs(row - endRow) + Math.abs(col - endCol);
    }

    private boolean isFinal(Board board){
        return board.getCurRow() == this.endRow && board.getCurCol() == this.endCol;
    }

    private int rowColToInt(int row, int col){
        return row * dimension  + col;
    }
    // row = arr[0], col = arr[1]
    private int[] intToRowCol(int v){
        int[] retVal = new int[2];
        retVal[1] = v % dimension;
        retVal[0] = v / dimension;
        return retVal;
    }

    static Integer[][] clone(Integer[][] a) {
        Integer[][] b = new Integer[a.length][];
        for (int i = 0; i < a.length; i++) {
            b[i]= new Integer[a[i].length];
            for (int j = 0; j < a[i].length; j++)
                b[i][j] = a[i][j];
        }
        return b;
    }

    @Override
    public Board process() {
        Board curBoard = pq.poll();
        int row = curBoard.getCurRow();
        int col = curBoard.getCurCol();
        if ( isFinal(curBoard) ){
            return curBoard;
        }

        int backLinkPos = rowColToInt(row, col);
        if ((row -1) >= 0 && persistenceRefernce[row - 1][col] != -1){
            int trueCostSoFar = curBoard.getCostSoFar() + 1;
            int fx = trueCostSoFar + manhattanDistance(row - 1, col);
            if (persistenceRefernce[row - 1][col] == 0 ^ fx < persistenceRefernce[row - 1][col]) {
                persistenceRefernce[row - 1][col] = fx;
                pq.add(new Board(row - 1,  col, trueCostSoFar, fx));
                path[rowColToInt(row - 1, col)] = backLinkPos;
            }
        }
        if ((col -1) >= 0 && persistenceRefernce[row][col - 1] != -1){
            int trueCostSoFar = curBoard.getCostSoFar() + 1;
            int fx = trueCostSoFar + manhattanDistance(row, col - 1);
            if (persistenceRefernce[row][col - 1] == 0 ^ fx < persistenceRefernce[row][col - 1]) {
                persistenceRefernce[row][col - 1] = fx;
                pq.add(new Board(row, col - 1, trueCostSoFar, fx));
                path[rowColToInt(row, col - 1)] = backLinkPos;
            }
        }
        if ((row +1) < dimension && persistenceRefernce[row + 1][col] != -1){
            int trueCostSoFar = curBoard.getCostSoFar() + 1;
            int fx = trueCostSoFar + manhattanDistance(row + 1, col);
            if (persistenceRefernce[row + 1][col] == 0 ^ fx < persistenceRefernce[row + 1][col]) {
                persistenceRefernce[row + 1][col] = fx;
                pq.add(new Board(row + 1, col, trueCostSoFar, fx));
                path[rowColToInt(row + 1, col)] = backLinkPos;
            }
        }
        if ((col +1) < dimension && persistenceRefernce[row][col + 1] != -1){
            int trueCostSoFar = curBoard.getCostSoFar() + 1;
            int fx = trueCostSoFar + manhattanDistance(row, col + 1);
            if (persistenceRefernce[row][col + 1] == 0 ^ fx < persistenceRefernce[row][col + 1]) {
                persistenceRefernce[row][col + 1] = fx;
                pq.add(new Board(row, col + 1, trueCostSoFar, fx));
                path[rowColToInt(row, col + 1)] = backLinkPos;
            }
        }
        return curBoard;
    }

    private void findPath(){
        pq.add(new Board(this.startRow, this.startCol, 0, manhattanDistance(this.startRow, this.startCol)));
        boolean pathFound = false;
        while (!pq.isEmpty()){
            Board b = process();
            print(persistenceRefernce);
            if (isFinal(b)){
                pathFound = true;
                break;
            }
        }
        if (!pathFound){
            System.out.println("No path exists");
        }
        else{
//            print(persistenceRefernce);
            int vertex = rowColToInt(endRow, endCol);
            while (path[vertex] != -1){
                System.out.print(vertex + "<-");
                vertex = path[vertex];
            }
//            for(int i = 0; i < dimension*dimension; i++){
//                System.out.print(path[i] + " ");
//            }
            System.out.print(vertex);
        }
    }

    public static void main(String[] args) {
        int startRow = 0;
        int startCol = 0;
        int endRow = 1;
        int endCol = 9;
        int manhattan = Math.abs(startRow - endRow) + Math.abs(startCol - endCol);
        Integer[][] startState = {{0,  0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  -1,  -1,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  -1,  0,  0,  0,  0,  0}  };
        startState[startRow][startCol] = manhattan;
        Astar PFalg = new Astar(startState, startRow, startCol , endRow, endCol);
    }

}
