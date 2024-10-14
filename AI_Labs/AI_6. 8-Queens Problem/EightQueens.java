import java.util.ArrayList;
import java.util.List;

public class EightQueens {

    //  8- queens problem
    public static boolean isSafe(int row, int col,char[][] board){
        // horizontal
        for(int i=0;i<board.length;i++){
            if(board[row][i]=='Q'){
                return false;
            }
        }

        // vertical
        for(int i=0;i<board.length;i++){
            if(board[i][col]=='Q'){
                return false;
            }
        }

        // left-up
        for(int i=row,j=col;i>=0 && j>=0;i--,j--){
            if(board[i][j]=='Q'){
                return false;
            }
        }

        // right-up
        for(int i=row,j=col;i>=0 && j<board.length;i--,j++){
            if(board[i][j]=='Q'){
                return false;
            }
        }

        // left-down
        for(int i=row,j=col;i<board.length && j>=0;i++,j--){
            if(board[i][j]=='Q'){
                return false;
            }
        }

        // right-down
        for(int i=row,j=col;i<board.length && j<board.length;i++,j++){
            if(board[i][j]=='Q'){
                return false;
            }
        }

        return true;
    }
    public static void saveBoard(char[][] board,List<List<String>> allBoards){
        
        List<String> newBoard=new ArrayList<>();

        for(int i=0;i<board.length;i++){
            String row="";
            for(int j=0;j<board.length;j++){
                if(board[i][j]=='Q'){
                    row+='Q';
                }else{
                    row+='_';
                }
            }
            newBoard.add(row);
        }

        allBoards.add(newBoard);
    }
    public static void helper(char[][] board, List<List<String>> allBoards,int col){
        if(col==board.length){
            saveBoard(board,allBoards);
            return;
        }
        for(int row=0;row<board.length;row++){
            if(isSafe(row,col,board)){
                board[row][col]='Q';
                helper(board, allBoards, col+1);
                board[row][col]='.';
            }
        }
    }
    public static List<List<String>> solveQueens(int n){
        List<List<String>> allBoards=new ArrayList<>();
        char[][] board=new char[n][n];

        helper(board, allBoards, 0);

        return allBoards;
    }
    public static void main(String args[]){
        // 8-queens problem
        int n=8;
        System.out.println(solveQueens(n));
    }
}
