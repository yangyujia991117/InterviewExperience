import java.util.Scanner;

public class 第一题_蓝牙校验 {
    /**
     * 给3的倍数长的字符串，每3位代表一位数据，这3位里0多这一位就是0，1多就是1
     */
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        String s=scan.nextLine();
        char[] chars=s.toCharArray();
        StringBuilder res=new StringBuilder();
        int num_0=0,num_1=0;
        for(int i=0; i<chars.length; i++){
            if(chars[i]=='0'){
                num_0++;
            }
            else{
                num_1++;
            }
            if(num_0+num_1==3){
                if(num_0>num_1){
                    res.append("0");
                }
                else{
                    res.append("1");
                }
                num_0=0;
                num_1=0;
            }
        }
        System.out.println(res);

    }
}
