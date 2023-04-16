import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length==0){
            //直接输入表达式
            Scanner scan=new Scanner(System.in);
            while(scan.hasNext()){
                System.out.println(calcu(scan.nextLine()));
            }
        }
        else {
            Scanner scan = new Scanner(new File(args[0]));
            //按行读取文件里的表达式
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                System.out.println(calcu(s));
            }
            scan.close();
        }
    }
    private static String calcu(String s){
        Stack<String> stack=new Stack<>();
        String ope = "";
        List<Integer> nums;
        while(s.length()>0){
            char c=s.charAt(0);
            if(c==' '){
                s=s.substring(1);
            }
            else if(c=='('||c=='+'||c=='-'||c=='*'){
                stack.push(String.valueOf(c));
                s=s.substring(1);
            }
            else if(c!=')'){
                //这开头的是若干个数字
                StringBuilder sb=new StringBuilder();
                int i=0;
                while(s.charAt(i)>='0'&&s.charAt(i)<='9'){
                    sb.append(s.charAt(i));
                    i++;
                }
                stack.push(sb.toString());
                s=s.substring(i);
            }
            else{
                s=s.substring(1);
                nums=new ArrayList<>();
                while(true){
                    String peek=stack.pop();
                    if(peek.equals("+")||peek.equals("-")||peek.equals("*")){
                        ope=peek;
                    }
                    else if(peek.equals("(")){
                        break;
                    }
                    else{
                        nums.add(Integer.parseInt(peek));
                    }
                }
                //计算
                int res=0;
                switch (ope) {
                    case "+":
                        for (int n : nums) {
                            res += n;
                        }
                        break;
                    case "-":
                        res = nums.get(nums.size()-1);
                        for (int i = 0; i < nums.size()-1; i++) {
                            res -= nums.get(i);
                        }
                        break;
                    case "*":
                        res = 1;
                        for (int n : nums) {
                            res *= n;
                        }
                        break;
                }
                stack.push(String.valueOf(res));
            }
        }
        return stack.peek();
    }
}
