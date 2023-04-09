package wxy;

import java.util.*;

public class Q3 {
    //找最长不重复子序列
    static Map<Integer, Set<String>> hashmap;
    public static void main(String[] args) {
        hashmap=new HashMap<>();
        int res=findMaxArray("adghvbbsa");
        for(String s:hashmap.get(res)){
            System.out.println(s);
        }

    }
    public static int findMaxArray(String s){
        Map<Character,Integer> map=new HashMap<>();//map中存的是对应key的下一个下标

        int res=0,start=0,end=0;
        while(start<s.length()&&end<s.length()){
            char c=s.charAt(end);
            if(map.containsKey(c)){
                start=Math.max(start,map.get(c));//如果[start,end]这一段出现了重复数字，那么start移到最后一个重复数字的下一个下标
            }
            map.put(c,end+1);
            int len=end+1-start;
            res=Math.max(res,len);
            if(hashmap.containsKey(len)){
                hashmap.get(len).add(s.substring(start,end+1));
            }
            else{
                Set<String> set=new HashSet<>();
                set.add(s.substring(start,end+1));
                hashmap.put(len,set);
            }
            end++;
        }
        return res;
    }

}
