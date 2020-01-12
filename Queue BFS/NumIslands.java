package practice;

import java.util.*;

// 岛屿数量 使用BFS和DFS两种方法
public class NumIslands {
    public static void main(String[] args) {
        char[][] grid = {{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}};

        NumIslands obj = new NumIslands();
        int output = obj.numIslands(grid);
        System.out.println(output);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int n = grid.length;
        int m = grid[0].length;
        int islands = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
//                    markBFS(grid, i, j);
                    markDFS(grid, i, j);
                    islands++;
                }
            }
        }
        return islands;
    }

    // 根据当前坐标，BFS把1标记为0
    public void markBFS(char[][] grid, int x, int y) {
        int[] diX = {-1, 1, 0, 0};
        int[] diY = {0, 0, -1, 1};

        Queue<Coordinate> queue = new LinkedList<>();
        queue.offer(new Coordinate(x, y));
        grid[x][y] = '0';

        while (!queue.isEmpty()) {
            Coordinate coor = queue.poll();
            for (int i = 0; i < 4; i++) {
                Coordinate adj = new Coordinate(coor.x + diX[i], coor.y + diY[i]);
                if (!isInBound(adj, grid)) continue;
                if (grid[adj.x][adj.y] == '1') {
                    grid[adj.x][adj.y] = '0';
                    queue.offer(adj);
                }
            }
        }
    }

    // 根据当前坐标，DFS把1标记为0
    public void markDFS(char[][] grid, int x, int y) {
        int[] diX = {-1, 1, 0, 0};
        int[] diY = {0, 0, -1, 1};
        Stack<Coordinate> stack = new Stack<>();
        stack.push(new Coordinate(x, y));
        grid[x][y] = '0';
        while (!stack.isEmpty()) {
            Coordinate coor = stack.pop();

            for (int i = 0; i < 4; i++) {
                Coordinate adj = new Coordinate(coor.x + diX[i], coor.y + diY[i]);
                if (!isInBound(adj, grid)) continue;
                if (grid[adj.x][adj.y] == '1') {
                    grid[adj.x][adj.y] = '0';
                    stack.push(adj);
                }
            }
        }
    }

    // 判断adj这个点是否在二维网格中
    public boolean isInBound(Coordinate adj, char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        return adj.x >= 0 && adj.x < n && adj.y >= 0 && adj.y < m;
    }
}

class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
