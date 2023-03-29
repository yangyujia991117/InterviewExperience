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
        hashMap=new HashMap<>();//hashmap里存的都是不重复的子字符串
        int l=0,r=0;
        int max=1;
        while(l<chars.length&&r<chars.length){
            char c=chars[r];
            if(!set.contains(c)){
                set.add(c);
                r++;
                if(r==chars.length) {//这时候到最后一个字符了，不能再延长了，所以也要把这个给存了
                    max = Math.max(max, r - l);
                    store(l, r);
                }
            }
            else{
                max=Math.max(max,r-l);
                store(l,r);
                //set里存在c，则将重复的那个c及其前面的一段都删除
                while(true){
                    set.remove(chars[l]);
                    if(chars[l]==c){//删掉的这个是c，后面的不用删了
                        l++;
                        break;
                    }
                    l++;
                }
            }
        }
        System.out.println("下面输出hashmap的全部内容");
        for(Integer key: hashMap.keySet()){
            System.out.println("长度为"+key+"的每一段滑动窗口有：");
            List<String> list= hashMap.get(key);
            for(String sg: list){
                System.out.println(sg);
            }
        }
        System.out.println("最终结果为：");
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
