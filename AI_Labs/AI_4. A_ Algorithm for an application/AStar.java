import java.util.*;

class Node {
    int x, y;
    double f, g, h;
    Node parent;

    Node(int x, int y) {
        this.x = x; this.y = y;
    }
}

public class AStar {
    private final int[][] grid;
    private final boolean[][] closedList;
    private final List<Node> openList;

    AStar(int[][] grid) {
        this.grid = grid;
        this.closedList = new boolean[grid.length][grid[0].length];
        this.openList = new ArrayList<>();
    }

    private double heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public List<Node> findPath(Node start, Node goal) {
        openList.add(start);
        while (!openList.isEmpty()) {
            Node current = openList.stream().min(Comparator.comparingDouble(n -> n.f)).orElse(null);
            if (current.x == goal.x && current.y == goal.y) return constructPath(current);

            openList.remove(current);
            closedList[current.x][current.y] = true;

            for (Node neighbor : getNeighbors(current)) {
                if (closedList[neighbor.x][neighbor.y]) continue;
                double tentativeG = current.g + 1;

                if (!openList.contains(neighbor)) {
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;
                    openList.add(neighbor);
                } else if (tentativeG < neighbor.g) {
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;
                }
            }
        }
        return Collections.emptyList(); // No path found
    }

    private List<Node> constructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newX = node.x + dir[0], newY = node.y + dir[1];
            if (newX >= 0 && newY >= 0 && newX < grid.length && newY < grid[0].length && grid[newX][newY] == 0) {
                neighbors.add(new Node(newX, newY));
            }
        }
        return neighbors;
    }

    public static void main(String[] args) {
        int[][] grid = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0}
        };

        AStar aStar = new AStar(grid);
        Node start = new Node(0, 0), goal = new Node(4, 4);
        List<Node> path = aStar.findPath(start, goal);

        if (!path.isEmpty()) {
            System.out.println("Path found:");
            path.forEach(node -> System.out.println("(" + node.x + ", " + node.y + ")"));
        } else {
            System.out.println("No path found");
        }
    }
}

