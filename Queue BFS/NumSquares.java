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
