import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 拿到了一个长度为n的数组a，数组a中元素均为正整数，构建长度为n-1的数组b，b的元素满足每个元素都是a中任意两个元素之和，
 * 现在已知数组b，求数组a有多少种不同的可能
 */
public class Q2 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        int[] b=new int[n-1];
        Set<Integer> hashset=new HashSet<>();
        for(int i=0;i<n-1;i++){
            b[i]=scan.nextInt();
            hashset.add(b[i]);
        }
        if(n==3){
            if(hashset.size()==2){
                System.out.println(2);
            }
            else{
                System.out.println(1);
            }
        }
        System.out.println(hashset.size()+1);

    }
}
