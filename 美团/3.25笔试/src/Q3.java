import javax.security.sasl.SaslClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();//巧克力数量
        int m=scan.nextInt();//包装数量
        long[] weight=new long[n];
        for(int i=0;i<n;i++){
            int num=scan.nextInt();
            weight[i]=num*num;
        }
        int sum=0;
        int num=0;
        int idx=0;
        List<Integer> res=new ArrayList<>();
        for(int i=0;i<m;i++){
            long w=scan.nextLong();
            while(idx<n){
                if(w>=sum+weight[idx]){
                    sum+=weight[idx];
                    num++;
                    idx++;
                }
                else{
                    break;
                }
            }
            res.add(num);
        }
        for(int i=0;i<res.size();i++){
            System.out.print(res.get(i));
            if(i<res.size()-1){
                System.out.print(" ");
            }
        }
    }
}
