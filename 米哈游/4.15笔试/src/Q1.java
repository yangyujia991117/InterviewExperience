import java.util.*;

/**
 * 输入一个数字，把它转为多个不同的3次幂的数的和或差,数字按从大到小排
 * 如输入30，输出27+3
 * 输入20，输出27-9+3-1
 * 输入243，输出243
 */
public class Q1 {
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        int N= scan.nextInt();
        //将数字转为三进制
        String threeString="";
        boolean onlyContains1and0=true;
        while(N>0){
            int remainer=N%3;
            if(remainer!=1&&remainer!=0){
                onlyContains1and0=false;
            }
            threeString=String.valueOf(remainer)+threeString;
            N=(N-remainer)/3;
        }
        System.out.println(threeString);
        if(onlyContains1and0){
            //变为若干个三次幂的和即可,没有减法
            int len=threeString.length();
            List<Integer> res=new ArrayList<>();
            for(int i=0;i<len;i++){
                if(threeString.charAt(i)=='1') {
                    res.add((int) Math.pow(3, len - 1 - i));
                }
            }
            for(int i=0;i<res.size();i++){
                System.out.print(res.get(i));
                if(i<res.size()-1){
                    System.out.print("+");
                }
            }
            return;
        }
        //如果有2，那必须加以处理，有2的位+1变为0，然后前面进位，原来2对应的这一位用减法
        int i=threeString.length()-1;
        List<Integer> negetive=new ArrayList<>();
        while (i>=0){
            if(threeString.charAt(i)=='2'){
                System.out.println(i+"为2");
                negetive.add(threeString.length()-1-i);//需要做减法的对应下标
                threeString=getNewString(threeString,i);
            }
            i--;
        }
        System.out.println(threeString);
        List<Integer> res=new ArrayList<>();
        for(i=threeString.length()-1;i>=0;i--){
            if(threeString.charAt(i)!='0'){
                if(negetive.contains(threeString.length()-1-i)){
                    res.add(-(int)Math.pow(3,threeString.length()-1-i));
                }
                else{
                    res.add((int)Math.pow(3,threeString.length()-1-i));
                }
            }
        }

        Collections.sort(res, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Math.abs(o2)-Math.abs(o1);
            }
        });
        for(i=0;i<res.size();i++){
            if(i>0&&res.get(i)>0){
                System.out.print("+"+res.get(i));
            }
            else {
                System.out.print(res.get(i));
            }
        }

    }
    static String getNewString(String s,int i){
        System.out.println(i);
        char[] chars=s.toCharArray();
        chars[i]=0;
        int jinwei=1;
        i=i-1;
        while(i>=0){
            if(chars[i]=='0'){
                chars[i]='1';
                jinwei=0;
                break;
            }
            else if(chars[i]=='1'){
                chars[i]='2';
                jinwei=0;
                break;
            }
            else{
                chars[i]='0';
                //还要继续进位
                i--;
            }
        }
        StringBuilder res= new StringBuilder();
        for(char c:chars){
            res.append(c);
        }
        System.out.println(res                                                                                                        );
        if(jinwei==1){
            return "1"+ res.toString();
        }
        return res.toString();

    }
}
