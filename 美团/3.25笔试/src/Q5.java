import java.util.Scanner;

/**
 * 和第2题类似，不同的是这题的要求是一天吃了之后第二天就不能吃（也就是最多隔天吃一次），不过提供k次机会，是可以昨天吃了今天又吃的
 *
 *
 * 样例输入
 * 7 1
 * 1 2 3 4 5 6 7
 * 样例输出
 * 19
 */


public class Q5 {
    static int n;
    static int max=0;
    static int k;
    static int[] num;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        n=scan.nextInt();
        k=scan.nextInt();
        num=new int[n];
        for(int i=0;i<n;i++){
            num[i]=scan.nextInt();
        }
        //暴力法递归实现
        eatOrNot(0,0,k);
        System.out.println(max);

    }
    static void eatOrNot(int idx,int preSum,int lastK){
        if(idx>=n){
            max=Math.max(preSum,max);
            return;
        }
        //不吃的情况
        eatOrNot(idx+1,preSum,lastK);
        //吃的情况
        eatOrNot(idx+2,preSum+num[idx],lastK);

        if(lastK>0) {
            //打破原则吃i和i+1,不吃i+2
            if (idx < n - 1) {
                eatOrNot(idx + 2, preSum + num[idx]+num[idx+1], num, lastK-1);
            }
            //打破原则吃i和i+2,不吃i+1
            if (idx < n - 2) {
                eatOrNot(idx + 2, preSum + num[idx]+num[idx+2], num, lastK-1);
            }
            if(lastK>=2&&idx < n - 2){
                //打破原则吃i、i+1 i+2
                eatOrNot(idx + 2, preSum + num[idx]+num[idx+1]+num[idx+2], num, lastK-2);
            }
        }


    }

}
