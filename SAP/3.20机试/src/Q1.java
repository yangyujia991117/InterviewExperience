import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        int k=scan.nextInt();
        int[] a=new int[k];
        List<Integer> res=new ArrayList<>();
        for(int i=0;i<k;i++){
            a[i]=scan.nextInt();//改为报到a的小朋友出局
        }
        List<Integer> num=new ArrayList<>();
        for(int i=1;i<=n;i++){
            num.add(i);
        }
        int startIdx=0;
        for(int i=0;i<k;i++){
            int step=a[i];
            if(i%2==1){
                //往后走a[i]格相当于往前走len-a[i]格
                step=num.size()-(a[i]%num.size());
            }
            int newIdx=(startIdx+step)%num.size();
            res.add(num.get(newIdx));
            //System.out.println(num.get(newIdx)+"出局");
            num.remove(newIdx);
            if(i%2==0){
                //接下来要从后一个走
                if(newIdx==0){
                    startIdx=num.size()-1;
                }
                else{
                    startIdx=newIdx-1;
                }
            }
            else{
                //接下来要从前一个走
                if(newIdx>=num.size()){
                    startIdx=0;
                }
                else{
                    startIdx=newIdx;
                }
            }
        }
        for(int i=0;i<res.size();i++){
            System.out.print(res.get(i));
            if(i<res.size()-1){
                System.out.print(" ");
            }
        }

    }
}
