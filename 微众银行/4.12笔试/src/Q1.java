import java.util.Scanner;

/**
 * 求一共有多少个非负整数满足以下要求：
 * 二进制串中1的个数大于等于L，小于等于R
 * 和X按位与运算的结果为X，和Y按位或运算的结果为Y
 *
 * 例如2 3 8 62结果是10
 */
public class Q1 {
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        int T=Integer.parseInt(scan.nextLine());
        for(int t=0;t<T;t++){
            String[] s=scan.nextLine().split(" ");
            int L=Integer.parseInt(s[0]);
            int R=Integer.parseInt(s[1]);
            int X=Integer.parseInt(s[2]);
            int Y=Integer.parseInt(s[3]);
            int res=0;
            String xs=Integer.toBinaryString(X);
            for(int n=0;n<=5000;n++){
                if(((n&X)==X)&&((n|Y)==Y)) {
                    String ns = Integer.toBinaryString(n);
                    int num_1 = 0;
                    for (int i = 0; i < ns.length(); i++) {
                        if (ns.charAt(i) == '1') {
                            num_1++;
                        }
                    }
                    if(num_1>=L&&num_1<=R){
                        res++;
                    }
                }
            }
            System.out.println(res);
        }

    }
}
