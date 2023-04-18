import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 多多和皮皮轮流拿金币，多多先拿
 * 规则：每轮如果金币剩余数量为偶数，则可以拿一半或者只拿一个；如果金币剩余数量为奇数，则只能拿一个
 * 每一轮多多和皮皮都是以最佳策略拿金币，求两人各自能拿到的最多金币
 */
public class Q4 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int T=scan.nextInt();
        while(T>0) {
            int N = scan.nextInt();
            int duoduo = 0;
            int pipi = 0;
            int take = 0;
            for (int i = 0; ; i++) {
                if (N == 0) {
                    break;
                }
                if (N % 2 == 0) {
                    //可以拿走一半的金币或一枚金币
                    //贪心策略：让自己多拿也就是让对方少拿！
                    //如果拿走一半之后剩下有偶数个金币，那对方下一轮能够拿的个数大于等于1个，那为了恶心对方，肯定不能这样，所以故意只拿一个让剩下有奇数个金币，对方只能拿一个
                    //如果拿走一半之后剩下有奇数个金币，那刚好对方下一轮也只能拿一个，所以这种情况就拿走一半的金币
                    if (N / 2 % 2 == 0) {
                        take = 1;
                    } else {
                        take = N / 2;
                    }
                } else {
                    //只能拿走一枚
                    take = 1;
                }
                N -= take;
                if (i % 2 == 0) {
                    //多多走
                    duoduo += take;
                    //System.out.println("多多拿"+take);
                } else {
                    //皮皮走
                    pipi += take;
                    //System.out.println("皮皮拿"+take);
                }
            }
            System.out.println(duoduo + " " + pipi);
            T--;
        }
    }
}
