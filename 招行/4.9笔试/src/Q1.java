import java.util.Scanner;

public class Q1 {
    /**
     * 拿到一个字符串，每隔k个字符就删除一个字符（第一个字符必定删除），输出最终的字符串
     */
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int k=Integer.parseInt(scan.nextLine());
        String s=scan.nextLine();
        StringBuilder sb=new StringBuilder();
        int delete=0;
        for(int i=0;i<s.length();i++){
            if(i==delete){
                delete+=k+1;
            }
            else{
                sb.append(s.charAt(i));
            }
        }
        System.out.println(sb.toString());
    }
}
