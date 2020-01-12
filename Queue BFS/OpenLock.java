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