package Utils;

public class Board implements Comparable<Board> {
    /*
        -1 for blocked cells, 0 for non blocked and unvisited cells, 1 otherwise
     */
    Integer[][] state;

    int curRow;
    int curCol;
    int costSoFar;
    Integer manhattan;

    public int getCurRow() {
        return curRow;
    }

    public int getCurCol() {
        return curCol;
    }

    public int getNumRow(){
        return state.length;
    }

    public int getNumCol(){
        return state[0].length;
    }

    public Integer[][] get2DArray(){
        return this.state.clone();
    }

    public int getCostSoFar(){
        return this.costSoFar;
    }

    public Board(Integer[][] state, int curRow, int curCol, int costSoFar, int manhattan) {
        this.state = state;
        this.curRow = curRow;
        this.curCol = curCol;
        this.costSoFar = costSoFar;
        this.manhattan = manhattan;
    }

    @Override
    public int compareTo(Board board) {
        return this.manhattan.compareTo(board.manhattan);
    }
}
