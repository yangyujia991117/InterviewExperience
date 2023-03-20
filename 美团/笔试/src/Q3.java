import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q3 {
    /**
     * 现在小美获得了一个字符串。小美想要使得这个字符串是回文串。
     *
     * 小美找到了你。你可以将字符串中至多两个位置改为任意小写英文字符’a’-‘z’。
     *
     * 你的任务是帮助小美在当前制约下，获得字典序最小的回文字符串。
     *
     * 数据保证能在题目限制下形成回文字符串。
     *
     * 注：回文字符串：即一个字符串从前向后和从后向前是完全一致的字符串。
     *
     * 例如字符串abcba, aaaa, acca都是回文字符串。字符串abcd, acea都不是回文字符串。
     */
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        String s=scan.nextLine();
        char[] chars=s.toCharArray();
        int l=0,r=s.length()-1;
        List<Integer> notsame=new ArrayList<>();//不一样的字符
        while(l<=r){//注意这里必须是等于，因为奇数元素个数的字符串中间那个字符不应该算成是不一样的字符
            if(chars[l]!=chars[r]){
                notsame.add(l);
            }
            l++;
            r--;
        }
        if(notsame.size()==0){
            //已经是回文串了,直接把第一个不为a的一对数字改为a
            int i=0,j=s.length()-1;
            while(i<j){
                if(chars[i]!='a'){
                    chars[i]='a';
                    chars[j]='a';
                    break;
                }
                i++;
                j--;
            }
        }
        else{
            //不是回文串
            if(notsame.size()==1){//只有一对元素不对
                //两种情况：如果这个字符串元素个数为偶数个，那把这两个位置改成a就可以了
                if(chars.length%2==0){
                    int index=notsame.get(0);
                    chars[index]='a';
                    chars[chars.length-1-index]='a';
                }
                else{
                    //如果这个字符串的元素个数为奇数个，那先把这一对改成小的那个，然后把中间那个字符改为a
                    int index=notsame.get(0);
                    int index1=chars.length-1-index;
                    char min=(char)(Math.min(chars[index]-'a',chars[index1]-'a')+'a');
                    chars[index]=min;
                    chars[index1]=min;
                    chars[(chars.length-1)/2]='a';
                }
            }
            else{
                //有大于等于2对元素不对，这时候要把它改成回文，肯定改的字符至少有两个,所以不用管中间那个字符了
                for(int i=0;i<notsame.size();i++){
                    int index=notsame.get(i);
                    int index1=chars.length-1-index;
                    char min=(char)(Math.min(chars[index]-'a',chars[index1]-'a')+'a');
                    chars[index]=min;
                    chars[index1]=min;
                }
            }
        }
        for(char c:chars){
            System.out.print(c);
        }
        System.out.print('\n');

    }
}
