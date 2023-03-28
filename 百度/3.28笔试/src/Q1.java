import java.util.HashMap;
import java.util.Scanner;

/**
 * Author:yy
 * Description:
 **/
public class Q1 {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        int N= Integer.parseInt(scan.nextLine());
        int n=0;
        while(n<N){
            String res="Yes";
            String s=scan.nextLine();
            if(s.length()!=5){
                res="No";
            }
            else {
                char[] chars = s.toCharArray();
                int[] baidu = new int[5];
                for (char c : chars) {
                    if (c == 'B') {
                        baidu[0] += 1;
                    } else if (c == 'a') {
                        baidu[1] += 1;
                    } else if (c == 'i') {
                        baidu[2] += 1;
                    } else if (c == 'd') {
                        baidu[3] += 1;
                    } else if (c == 'u') {
                        baidu[4] += 1;
                    }
                }
                for (int i : baidu) {
                    if (i != 1) {
                        res = "No";
                        break;
                    }
                }
            }
            System.out.println(res);
            n++;
        }
    }
}
