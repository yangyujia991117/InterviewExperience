import java.util.Scanner;

/**
 * 小美因乐于助人的突出表现获得了老师的嘉奖。老师允许小美从一堆n个编号分别为1,2,...,n的糖果中选择任意多个糖果作为奖励（每种编号的糖果各一个），但为了防止小美一次吃太多糖果有害身体健康，老师设定了一个限制：如果选择了编号为 i 的糖果，那么就不能选择编号为 i-1, i-2, i+1, i+2的四个糖果了。在小美看来，每个糖果都有一个对应的美味值，小美想让她选出的糖果的美味值之和最大！作为小美的好朋友，请你帮帮她！
 *
 *
 *
 * 输入描述
 * 第一行一个整数n，表示糖果数量。
 *
 * 第二行n个整数a1,a2,...,an，其中ai表示编号为 i 的糖果的美味值。
 *
 * 1≤n≤50000 , 1≤ai≤10000
 *
 * 输出描述
 * 输出一行一个数，表示小美能获得的糖果美味值之和最大值。
 *
 *
 * 样例输入
 * 7
 * 3 1 2 7 10 2 4
 * 样例输出
 * 14
 */


public class Q2_2 {
    static int n;
    static int max=0;
    //static int[] num;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        n=scan.nextInt();
        int[] num=new int[n];
        for(int i=0;i<n;i++){
            num[i]=scan.nextInt();
        }
//        int sum1=0,sum2=0,sum3=0;
//        for(int i=0;i<n;i=i+3){
//            sum1+=num[i];
//        }
//        for(int i=1;i<n;i=i+3){
//            sum2+=num[i];
//        }
//        for(int i=2;i<n;i=i+3){
//            sum3+=num[i];
//        }
//        System.out.println(Math.max(sum1,Math.max(sum2,sum3)));
        //暴力法递归实现
        eatOrNot(0,0,num);
        System.out.println(max);

    }
    static void eatOrNot(int idx,int preSum,int[] num){
        if(idx>=n){
            max=Math.max(preSum,max);
            return;
        }

        //吃的情况
        eatOrNot(idx+3,preSum+num[idx],num);
        //不吃的情况
        eatOrNot(idx+1,preSum,num);

    }

}
