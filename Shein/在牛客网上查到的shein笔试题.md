## 在牛客网上查到的shein笔试题

10道单选题

5道多选题

### SQL

现有某乎问答创作者回答情况表answer tb如下（其中answer_date表示创作日期、author_id指创作者编号、issue_id表示问题id、char_len表示回答字数) 

![](https://uploadfiles.nowcoder.com/images/20230326/994431508_1679820369229/D2B5CA33BD970F64A6301FA75AE2EB22)

 请你统计11月份日人均回答量（回答问题数量/答题人数)，按回答日期排序，结果保留两位小数以上，例子的输出结果如下： 

![](https://uploadfiles.nowcoder.com/images/20230326/994431508_1679820405813/D2B5CA33BD970F64A6301FA75AE2EB22)

```mysql
SELECT answer_date,
	   FORMAT(COUNT(issue_id)/COUNT(DISTINCT author_id),2) as per_num 
	   #上面的FORMAT函数是把结果化为保留两位小数的作用，如果没规定一定保留两位小数那么可以不用这个函数，用COUNT(issue_id)/COUNT(DISTINCT author_id)即可
FROM answer_tb
GROUP BY answer_date
ORDER BY answer_date;
```



### 算法

 求最长子字符串，如果遇到多个长度相同的子串则都输出 

```java
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

```

