package PFModules;

import Utils.Board;

import java.util.PriorityQueue;
import java.util.Queue;

public class Astar implements PFAlg{
    private PriorityQueue<Board> pq = new PriorityQueue<>();
    int endRow;
    int endCol;
    Board initState;
    Integer[] path;
    int dimension;

    public Astar(Board initState, int endRow, int endCol) {
        this.initState = initState;
        this.endRow = endRow;
        this.endCol = endCol;
        this.dimension = initState.get2DArray()[0].length;
        path = new Integer [dimension*dimension];
        for (int i = 0; i < dimension * dimension; i++){
            path[i] = -1;
        }
        findPath();
    }

    private void print(Board board){
        Integer[][] state = board.get2DArray();
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

    private int translate(int row, int col){
        return row * dimension  + col;
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

        int backLinkPos = translate(row, col);
        Integer[][] curState = curBoard.get2DArray();
        if ((row -1) >= 0 && curState[row - 1][col] != -1){
            int trueCostSoFar = curBoard.getCostSoFar() + 1;
            int fx = trueCostSoFar + manhattanDistance(row - 1, col);
            if (curState[row - 1][col] == 0 ^ fx < curState[row - 1][col]) {
                Integer[][] newState = clone(curState);
                newState[row - 1][col] = fx;
                pq.add(new Board(newState, row - 1, col, trueCostSoFar, fx));
                path[translate(row - 1, col)] = backLinkPos;
            }
        }
        if ((col -1) >= 0 && curState[row][col - 1] != -1){
            int trueCostSoFar = curBoard.getCostSoFar() + 1;
            int fx = trueCostSoFar + manhattanDistance(row, col - 1);
            if (curState[row][col - 1] == 0 ^ fx < curState[row][col - 1]) {
                Integer[][] newState = clone(curState);
                newState[row][col - 1] = fx;
                pq.add(new Board(newState, row, col - 1, trueCostSoFar, fx));
                path[translate(row, col - 1)] = backLinkPos;
            }
        }
        if ((row +1) < curBoard.getNumRow() && curState[row + 1][col] != -1){
            int trueCostSoFar = curBoard.getCostSoFar() + 1;
            int fx = trueCostSoFar + manhattanDistance(row + 1, col);
            if (curState[row + 1][col] == 0 ^ fx < curState[row + 1][col]) {
                Integer[][] newState = clone(curState);
                newState[row + 1][col] = fx;
                pq.add(new Board(newState, row + 1, col, trueCostSoFar, fx));
                path[translate(row + 1, col)] = backLinkPos;
            }
        }
        if ((col +1) < curBoard.getNumCol() && curState[row][col + 1] != -1){
            int trueCostSoFar = curBoard.getCostSoFar() + 1;
            int fx = trueCostSoFar + manhattanDistance(row, col + 1);
            if (curState[row][col + 1] == 0 ^ fx < curState[row][col + 1]) {
                Integer[][] newState = clone(curState);
                newState[row][col + 1] = fx;
                pq.add(new Board(newState, row, col + 1, trueCostSoFar, fx));
                path[translate(row, col + 1)] = backLinkPos;
            }
        }
        return curBoard;
    }

    private void findPath(){
        pq.add(initState);
        boolean pathFound = false;
        while (!pq.isEmpty()){
            Board top = process();
            print(top);
            if (isFinal(top)){
                pathFound = true;
                break;
            }
        }
        if (!pathFound){
            System.out.println("No path exists");
        }
        else{
            int vertex = translate(endRow, endCol);
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
        int startCol = 1;
        int endRow = 3;
        int endCol = 3;
        int manhattan = Math.abs(startRow - endRow) + Math.abs(startCol - endCol);
        Integer[][] startState = {{0,0,0,0},{0,-1,-1,0},{0,0,0,0},{0,0,0,0}};
        startState[startRow][startCol] = manhattan;
        Board initBoard = new Board(startState, startRow, startCol,
                0, manhattan);
        Astar PFalg = new Astar(initBoard, endRow, endCol);
    }

}
