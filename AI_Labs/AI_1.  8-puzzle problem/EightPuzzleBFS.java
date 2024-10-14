import java.util.*;

class State {
    String board; 
    int zeroIndex; 
    int depth; 
    String moves; 

    State(String board, int zeroIndex, int depth, String moves) {
        this.board = board;
        this.zeroIndex = zeroIndex;
        this.depth = depth;
        this.moves = moves;
    }

   
    boolean isGoal() {
        return board.equals("123456780"); 
    }

    
    List<State> getNeighbors() {
        List<State> neighbors = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; 
        int row = zeroIndex / 3;
        int col = zeroIndex % 3;

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int newIndex = newRow * 3 + newCol;
                // Swap zero with the target tile
                char[] newBoard = board.toCharArray();
                newBoard[zeroIndex] = newBoard[newIndex];
                newBoard[newIndex] = '0';
                neighbors.add(new State(new String(newBoard), newIndex, depth + 1, moves + getMove(zeroIndex, newIndex)));
            }
        }

        return neighbors;
    }

    
    private String getMove(int oldIndex, int newIndex) {
        if (newIndex == oldIndex + 3) return "D"; 
        if (newIndex == oldIndex - 3) return "U"; 
        if (newIndex == oldIndex + 1) return "R";
        if (newIndex == oldIndex - 1) return "L"; 
        return "";
    }
}

public class EightPuzzleBFS {
    public static void main(String[] args) {
        String initialBoard = "123456078"; 
        solve(initialBoard);
    }

    public static void solve(String initialBoard) {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        State initialState = new State(initialBoard, initialBoard.indexOf('0'), 0, "");
        queue.add(initialState);
        visited.add(initialState.board);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (currentState.isGoal()) {
                System.out.println("Solution found with moves: " + currentState.moves + " in depth: " + currentState.depth);
                return;
            }

            for (State neighbor : currentState.getNeighbors()) {
                if (!visited.contains(neighbor.board)) {
                    queue.add(neighbor);
                    visited.add(neighbor.board);
                }
            }
        }

        System.out.println("No solution found.");
    }
}
