import java.util.*;

/**
 * 求没有重复字符的最长子字符串，如果遇到多个长度相同的子串则都输出
 */
public class niuke {
    static HashMap<Integer, List<String>> hashMap;
    static String s;
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        s=scan.nextLine();
        char[] chars=s.toCharArray();
        Set<Character> set=new HashSet<>();
        hashMap=new HashMap<>();
        int l=0,r=0;
        int max=1;
        while(l<chars.length&&r<chars.length){
            int len=r-l;
            while(r<chars.length){
                char c=chars[r];
                if(set.contains(c)){
                    store(l,r);
                    //出现了重复字符，那需要把set里重复字符及其之前的字符都删了
                    for(int i=l;i<r;i++){
                        set.remove(chars[i]);
                        if(chars[i]==c){
                            l=i+1;
                            break;
                        }
                    }
                    break;
                }
                set.add(c);
                r++;
                len++;
            }
            max=Math.max(max,len);
        }
        for(String sg: hashMap.get(max)){
            System.out.println(sg);
        }

    }
    static void store(int l,int r){
        int len=r-l;
        if(hashMap.containsKey(len)){
            List<String> list=hashMap.get(len);
            if(!list.contains(s.substring(l,r))){
                list.add(s.substring(l,r));
            }
        }
        else{
            List<String> list=new ArrayList<>();
            list.add(s.substring(l,r));
            hashMap.put(len,list);
        }
    }
}
