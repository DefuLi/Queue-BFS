# Queue-BFS
基于队列的广度优先搜索，编程试题来自LeetCode。如果对如果在BFS中使用队列，建议看[LeetCode这个动画](https://leetcode-cn.com/explore/learn/card/queue-stack/217/queue-and-bfs/869/)，保证看懂。在使用BFS进行遍历时，一般基于队列这个数据结构。使用BFS需要格外主要一下几点：<br>
1）确定每个节点可以转移的状态；<br>
2）如果需要在遍历的时候，确定最小路径，需要使用queue.size计算出队列每一轮的个数，然后轮数++；<br>
3）如果节点放入队列中，需要每一次都去重，防止同一节点多次放入，需要使用Set集合去重。<br>
## 岛屿数量
给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。<br>
```java
输入:
11110
11010
11000
00000

输出: 1

```

解答这个题采用的策略是，从左上角的节点开始遍历，每个节点有4个状态转移，分别为上下左右。遍历完每个'1'后，将其标记为'0'。在代码中，我给出了使用BFS和DFS两种遍历策略，如果只想看BFS，尽可忽略DFS的代码。<br>
```java
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

```

## 打开转盘锁
你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。每个拨轮可以自由旋转：例如把 '9' 变为  '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。<br>
锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。<br>
列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。<br>
字符串 target 代表可以解锁的数字，你需要给出最小的旋转次数，如果无论如何不能解锁，返回 -1。<br>
```java
输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
输出：6
解释：
可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
因为当拨动到 "0102" 时这个锁就会被锁定。
```
同样，如果使用BFS进行遍历的话，首先需要确定每个节点的转移状态。不难发现，这道题每个节点的转移状态有8种，每个转盘有2种转移状态，分别是加减1。对于deadends，我们可以把它和访问过的节点一同放入到Set集合中，在遍历一个节点的时候，首先去集合中查看是否已经有了，有的话就不对该节点进行遍历。<br>
```java
package practice;
import java.util.*;

// 打开转盘锁
public class OpenLock {
    public static void main(String[] args) {
        OpenLock openLock = new OpenLock();
        String[] deadends = {"8887","8889","8878","8898","8788","8988","7888","9888"};
        String target = "8888";
        System.out.println(openLock.getNum(deadends, target));
    }

    public int getNum(String[] deadends, String target) {
        LockNum targetLockNum = new LockNum(target);
        Queue<LockNum> queue = new LinkedList<>();
        Set<String> set = new HashSet<>();  // 保存以访问过的和不能访问的
        for (int i = 0; i < deadends.length; i++) {
            set.add(deadends[i]);
        }
        if(set.contains("0000")){
            return -1;
        }

        int num = 0;
        LockNum root = new LockNum("0000");
        queue.offer(root);
        while (!queue.isEmpty()) {
            int sizeQueue = queue.size();
            while (sizeQueue > 0) {
                sizeQueue--;
                LockNum head = queue.poll();
//                set.add(String.valueOf(head.num1) + String.valueOf(head.num2) + String.valueOf(head.num3) + String.valueOf(head.num4));
                if (isEqual(head, targetLockNum))
                    return num;
                List list = getUpDownLock(head);
                for (int i = 0; i < list.size(); i++) {
                    if (!(set.contains(String.valueOf(((LockNum) list.get(i)).num1) + String.valueOf(((LockNum) list.get(i)).num2) + String.valueOf(((LockNum) list.get(i)).num3) + String.valueOf(((LockNum) list.get(i)).num4)))){
                        queue.offer((LockNum) list.get(i));
                        set.add(String.valueOf(((LockNum) list.get(i)).num1) + String.valueOf(((LockNum) list.get(i)).num2) + String.valueOf(((LockNum) list.get(i)).num3) + String.valueOf(((LockNum) list.get(i)).num4));
                    }
                }
            }
            num++;
        }
        return -1;
    }

    //判断两个LockNum是否相同
    public boolean isEqual(LockNum lockNum1, LockNum lockNum2) {
        if (lockNum1.num1 == lockNum2.num1 && lockNum1.num2 == lockNum2.num2 && lockNum1.num3 == lockNum2.num3 && lockNum1.num4 == lockNum2.num4) {
            return true;
        } else {
            return false;
        }
    }

    // 获取当前选取节点的加减1的相邻节点
    public List<LockNum> getUpDownLock(LockNum lockNum) {
        List<LockNum> list = new ArrayList<>();

        int numTemp = lockNum.num1 + 1;
        if (numTemp > 9) numTemp = 0;
        LockNum lockNum1 = new LockNum(String.valueOf(numTemp) + String.valueOf(lockNum.num2) + String.valueOf(lockNum.num3) + String.valueOf(lockNum.num4));

        numTemp = lockNum.num1 - 1;
        if (numTemp < 0) numTemp = 9;
        LockNum lockNum2 = new LockNum(String.valueOf(numTemp) + String.valueOf(lockNum.num2) + String.valueOf(lockNum.num3) + String.valueOf(lockNum.num4));

        numTemp = lockNum.num2 + 1;
        if (numTemp > 9) numTemp = 0;
        LockNum lockNum3 = new LockNum(String.valueOf(lockNum.num1) + String.valueOf(numTemp) + String.valueOf(lockNum.num3) + String.valueOf(lockNum.num4));

        numTemp = lockNum.num2 - 1;
        if (numTemp < 0) numTemp = 9;
        LockNum lockNum4 = new LockNum(String.valueOf(lockNum.num1) + String.valueOf(numTemp) + String.valueOf(lockNum.num3) + String.valueOf(lockNum.num4));

        numTemp = lockNum.num3 + 1;
        if (numTemp > 9) numTemp = 0;
        LockNum lockNum5 = new LockNum(String.valueOf(lockNum.num1) + String.valueOf(lockNum.num2) + String.valueOf(numTemp) + String.valueOf(lockNum.num4));

        numTemp = lockNum.num3 - 1;
        if (numTemp < 0) numTemp = 9;
        LockNum lockNum6 = new LockNum(String.valueOf(lockNum.num1) + String.valueOf(lockNum.num2) + String.valueOf(numTemp) + String.valueOf(lockNum.num4));

        numTemp = lockNum.num4 + 1;
        if (numTemp > 9) numTemp = 0;
        LockNum lockNum7 = new LockNum(String.valueOf(lockNum.num1) + String.valueOf(lockNum.num2) + String.valueOf(lockNum.num3) + String.valueOf(numTemp));

        numTemp = lockNum.num4 - 1;
        if (numTemp < 0) numTemp = 9;
        LockNum lockNum8 = new LockNum(String.valueOf(lockNum.num1) + String.valueOf(lockNum.num2) + String.valueOf(lockNum.num3) + String.valueOf(numTemp));
        list.add(lockNum1);
        list.add(lockNum2);
        list.add(lockNum3);
        list.add(lockNum4);
        list.add(lockNum5);
        list.add(lockNum6);
        list.add(lockNum7);
        list.add(lockNum8);
        return list;
    }
}

// 转盘数的封装类
class LockNum {
    public int num1;
    public int num2;
    public int num3;
    public int num4;

    LockNum(String string) {
        this.num1 = Integer.parseInt(String.valueOf(string.charAt(0)));
        this.num2 = Integer.parseInt(String.valueOf(string.charAt(1)));
        this.num3 = Integer.parseInt(String.valueOf(string.charAt(2)));
        this.num4 = Integer.parseInt(String.valueOf(string.charAt(3)));
    }
}

```
## 完全平方数
给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
```java
输入: n = 12
输出: 3 
解释: 12 = 4 + 4 + 4.
```
对于这道题，刚开始确实不容易发现每个节点的转移状态是什么。对于每个节点，其最大的完全平方数是节点的平方根向下取整，这一点相比不难理解。例如24，它的最大完全平方数是4，因为如果是5的话，仅5的平方就是25了。<br>
所以，每个节点的转移状态共有该节点的完全平方数的个数，例如24，它的转移状态有，减1、减4、减9、减16这四种。<br>
```java
package practice;

import java.util.*;

public class NumSquares {
    public static void main(String[] args) {
        NumSquares numSquares = new NumSquares();
        int a = 70000;
        System.out.println(numSquares.getNumbyN(a));
    }

    public int getNumbyN(int n) {
        if (n <= 0) return 0;
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        queue.offer(n);
        int num = 0;
        while (!queue.isEmpty()) {
            int sizeQueue = queue.size();
            while (sizeQueue > 0){
                int head = queue.poll();
                if (head == 0) return num;
                List<Integer> list = getSquareByMax(head);
                for (int i = 0; i < list.size(); i++) {
                    if(set.contains(head - list.get(i))) continue;
                    queue.offer(head - list.get(i));
                    set.add(head - list.get(i));
                }
                sizeQueue--;
            }
            num++;
        }
        return num;
    }

    // 获得n的最大平方数的列表
    // 输入n=12; return:[1, 4, 9]
    public List<Integer> getSquareByMax(int n) {
        double nSqrt = Math.sqrt(n);
        int maxSquare = (int) Math.floor(nSqrt);
        List<Integer> list = new ArrayList<>();
        for (int i = maxSquare; i >= 1; i--) {
            list.add(i * i);
        }
        return list;
    }
}


```
