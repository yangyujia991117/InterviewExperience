import java.util.HashMap;
import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        String[] strs=scan.next().split(";");
        HashMap<String,Integer> map=new HashMap<>();
        for(int i=0;i<strs.length;i++){
            String s=strs[i];
            //System.out.println(s);
            if(s.length()>0){
                int idx=s.indexOf('=');
                String key=s.substring(0,idx);
                String value=s.substring(idx+1);
                map.put(key,i);
                strs[i]=value;
            }
        }
        int q=scan.nextInt();
        for(int i=0;i<q;i++){
            String s=scan.next();
            if(!map.containsKey(s)){
                System.out.println("EMPTY");
            }
            else{
                System.out.println(strs[map.get(s)]);
            }
        }
    }
}
