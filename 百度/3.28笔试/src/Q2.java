import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Author:yy
 * Description:
 **/
public class Q2 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        if(n==1){
            System.out.println("r");
            return;
        }
        if(n==2){
            System.out.println("re");
            return;
        }
        if(n==3){
            System.out.println("red");
            return;
        }
        //看是能仅由一个字符构成
//        int sum=0;
//        StringBuilder sb=new StringBuilder();
//        HashMap<Integer,String> hashMap=new HashMap<>();
//        for(int i=1;sum<=n;i++){
//            sum+=i;
//            sb.append('d');
//            if(sum==n) {
//                System.out.println(sb.toString());
//                return;
//            }
//            hashMap.put(sum,sb.toString());
//        }
        /**
         * 思路：当字符串仅由同一个字符组成时，若长度为n，则该字符串一共有n(n-1)/2个回文子串，比如ddd这个字符串一共有3*2/2=3个回文子串
         * 当两个均仅由同一个字符组成的字符串拼起来时（但这两个字符不能相同），若两个字符串长度分别为n1、n2，则它们拼起来的大字符串一共有n1(n1-1)/2+n2(n2-1)/2个回文子串
         * 如果存在正整数n，使得a=n(n-1)/2,有一个数学定理：任何正整数都可以由若干个这样的a相加得到
         * 所以实际上要寻找的字符串可以转化为若干个仅由同一个字符组成的字符串拼接而成，但要注意相邻的这种字符串的那个字符不能相同
         *
         * 有一种傻瓜方法是直接redredred……三个字母轮流加进去
         */
        StringBuilder sb=new StringBuilder();
        char[] red=new char[]{'r','e','d'};
        int c=0;
        while(n>0){
            int sum=0;
            int i=1;
            while(sum+i<=n){
                sum+=i;
                i++;
                sb.append(red[c]);
            }
            n-=sum;
            c=(c+1)%3;
        }
        System.out.println(sb.toString());
    }
}
