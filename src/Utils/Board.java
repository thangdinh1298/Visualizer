package Utils;

public class Board implements Comparable<Board> {
    /*
        -1 for blocked cells, 0 for non blocked and unvisited cells, 1 otherwise
     */
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

    public int getCostSoFar(){
        return this.costSoFar;
    }

    public Board(int curRow, int curCol, int costSoFar, int manhattan) {
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
