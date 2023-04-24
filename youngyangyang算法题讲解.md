# youngyangyang算法题讲解



## 一、数组

**数组是存放在连续内存空间上的相同类型数据的集合**

c++/c的二维数组在内存上是连续存储的，java不是，可能外层那一维（n[i] [j]中的i）是连续的，然后外层的各个元素存着到内层那一维的地址（每个i对应的那一堆j），每个i的那一堆j是连续的，但不同i的j是不连续的。

#### 二分查找

注意区间的开闭以及每次查找时left是否能够等于right，我选择**左闭右闭区间**，那么每次查找时**left≤right**

middle的下标值取(left+right)/2或(left+right+1)/2都可以，我选择前者。

#### 移除元素

双指针法



## 二、链表

数组在内存中连续分布，链表在内存中不是连续分布。链表通过指针域的指针链接在内存中各个节点。

#### 203.删除链表元素

注意在前面添加一个虚拟头节点dummy更方便

#### 206.反转链表

**经典题目！**注意有递归和非递归两种做法

#### 24.两两交换链表中的节点

注意还是要用到虚拟头节点dummy（用来作为现在正在交换的两个节点的前一个结点）

#### 19.删除链表的倒数第N个节点

快慢指针，注意也要用虚拟头节点dummy

#### 160.求链表的相交节点

比较容易想到的方法是先比较两个链表的长度差，然后让它们两个尾部对齐，从同样开始部位开始往后遍历。

另一种代码量少但难想到的方法是双指针法：让p1从headA开始，p2从headB开始，二者同时往后遍历，当p1==null时,p1=headB，当p2==null时,p2=headA，直到找到p1==p2的节点。这种做法利用的其实也是长度差：假设两个链表重合部分长度为c，A前面的部分长a，B前面的部分长b，那有a+c+b+1=b+c+a+1（加多的那个1是加上了后面的null，这个必须要加，因为若两个链表没有相交部分，最后p1 p2是在同时等于null的时候相遇，也就是c=0的情况），也就是假如两个链表有重合的部分，两个指针走相同长度之后一定会相遇。

#### 142.环形链表

和上一题类似，也用到了链表长度的巧思。

- 设入环之前长度为a，环长度为b

- 设置两个快慢指针fast和slow，两者一起从head往后走，fast每走2格slow走一格，如果fast经过了null则说明没有环，返回null
- 如果有环的话两者会在环里相遇，设fast和slow各走了f、s，则f=2s，又因为fast比slow多走的那一部分其实就是在环里转，所以f=s+nb，所以2s=s+nb=>s=nb
- 又因为每个节点从头开始都是往后走a+nb就到环的入口结点，所以让slow再走a即可
- 重新设一个新指针p指向head，和slow一起往前走，走a步的时候肯定会在环的入口节点处相遇



## 三、哈希表

java中的hashmap：数组+链表，还有扩容等操作。

哈希表的核心思想是**可以根据一个索引来获得某个值，即查询的时间复杂度为O(1)**，所以按这个思想来看**数组也可以被当成一个简单的哈希表（索引是数组下标）**。

 

- 需要注意第454题和第15、18题的区别，第454题的要求是不同三元组的【下标不同就可以，元素可以相同】而第15、8题均要求不同三元组的【元素也不可以相同（注意是组间元素不能同，每个元素内元素可以同）】，所以第15、18题不适合用hash，用双指针比较好。（以此类推，要求元素不能相同的五数之和、六数之和……用的也是双指针法）



## 四、字符串

#### 344.反转字符串

双指针法，先让p1=0,p2=s.length-1，p1和p2的元素互换，然后p1++，p2--，直到两者相遇

#### 541.反转字符串Ⅱ

和上面思路一样，也是每次弄一个翻转区间，然后每一轮翻转区间往前走2k就可以了

#### 剑指Offer05.替换空格

可以用StringBuilder逐个添加字符串段，也可以用双指针法（不过注意要从后往前）

#### 28.找出字符串中第一个匹配项的下标<font color="red">KMP算法</font>

**KMP算法——用来解决字符串匹配问题**

前缀表：用于找到之前匹配过的内容

- 前缀：一定包含首字母，不包含尾字母的所有子串，例如字符串"abc"的前缀有a、ab

- 后缀：一定包含尾字母，不包含首字母的所有字串，例如字符串"abc"的后缀有c、bc

- **前缀表**存的就是字符串每一位的**最长相等前后缀**

  最长相等前后缀指的是最长的相同前后缀的长度，例如字符串"a"的最长相等前后缀为0，"aaa"的最长相等前后缀为2(aa)，"aabaa"的最长相等前后缀为2（aa）

- 前缀表用一个**叫next的int[]数组**来存，next[i]存的是**模式串**中**[0, i]这一段的最长相等前后缀**

  例如模式串"aabaaf"的对应的前缀表为：[0,1,0,1,2,0]

- 当模式串下标为i的字符不匹配了的时候，不是又从头开始匹配，而是<font color="red">得到前缀表里下标为**i-1**的字符对应的最长相等前后缀**next[i-1]**，从**模式串下标为next[i-1]的字符开始匹配**</font>

  ![image-20230416121240639](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230416121240639.png)

代码如下：

```java
class Solution {
    public int strStr(String haystack, String needle) {
        //haystack是文本串, needle是模式串
        if (needle.length() == 0) return 0;
        int[] next = new int[needle.length()];
        getNext(next, needle);//获得前缀表
		
        //根据前缀表进行匹配
        int i=0,j=0;
        while(i<haystack.length()&&j<needle.length()){
            if(haystack.charAt(i)==needle.charAt(j)){
                if(j==needle.length()-1){
                    return i+1-needle.length();
                }
                i++;
                j++;
            }
            else{
                if(j==0){
                    i++;
                }
                else {
                    j = next[j - 1];
                }
            }
        }
        return -1;
    }
    //实现前缀表
    private void getNext(int[] next, String s) {
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(j) != s.charAt(i)) 
                j = next[j - 1];
            if (s.charAt(j) == s.charAt(i)) 
                j++;
            next[i] = j; 
        }
    }
}
```



## 五、栈与队列

#### 232.用栈实现队列

两个栈，一个输入栈stack1，一个输出栈stack2，入队列的时候一律放回stack1，出队列的时候如果stack2不为空，那直接从stack2弹出元素即可，如果stack2为空那将stack1的元素全部pop出来放进stack2里，再从stack2出栈。

#### 225.用队列实现栈

一个需要注意的点是java的Queue是一个接口，一般用LinkedList对Queue进行实例化

**方法一：用两个队列来实现**

<font color="red">和用栈实现队列时一个栈负责输入另一个栈负责输出不同，这里的两个队列一个负责输入和输出，另一个负责备份</font>

放进元素的时候都放入queue1，要弹出元素的时候先把queue1的元素逐个pop出来，并且判断该元素是否为queue1的最后一个元素（即pop出来之后queue1是否为空），如果不是的话就把这个元素放进queue2，是的话直接弹出，然后把queue2里的元素再放回queue1中

```java
public void push(int x) {
    queue1.offer(x);
}

public int pop() {
    int res=-1;
    while(!queue1.isEmpty()){
        int x=queue1.poll();
        if(!queue1.isEmpty()){
            //该元素不是最后一个元素
            queue2.offer(x);
        }
        else{
            res=x;
        }
    }
    while(!queue2.isEmpty()){
        //queue2中元素放回去
        queue1.offer(queue2.poll());
    }
    return res;
}
```

**方法二：用一个队列来实现**

只用一个队列的话就是不用再弄一个队列专门负责备份了，而是每次在弹出元素的时候将队列头部元素（除了最后一个元素外）重新添加到队列尾部，然后再把第一个元素弹出去

```java
public void push(int x) {
    queue1.offer(x);
}

public int pop() {
    int size=queue1.size();
    int i=1;
    while(true){
        int x=queue1.poll();
        if(i==size){//这是原本的最后一个元素
            return x;
        }
        queue1.offer(x);
        i++;
    }
}
```

#### 20.有效的括号

<font color="red">靠右的左括号必须先闭合了才轮到它左边的左括号闭合

普通做法：当遇到左括号的时候入栈，遇到右括号的时候出栈，如果这时候栈为空的那返回false，栈不为空但出栈的那个元素不是对应的左括号也返回false，到最后所有都处理完了但栈还是不为空也返回false

简化做法：当遇到左括号的时候将对应的右括号入栈，遇到有括号的时候比较栈顶元素是否和该右括号相同，如果栈为空那返回false，栈不为空但栈顶元素不和右括号相同那也返回false，栈顶元素和该右括号相同的话pop，到最后所有都处理完了但栈还是不为空也返回false

#### 1047.删除字符串的所有相邻重复项

可以用stringbuider或栈，每次加入新的新的字符后就比较是否最后两个字符相同，相同的话就删除，删除之后再比较再删除……

#### 150.逆波兰表达式求值

用栈——遇到数字则入栈；遇到运算符则取出栈顶两个数字进行计算，并将结果压入栈中

#### 239.滑动窗口最大值

这道题用暴力解法很容易，下面仅考虑进阶要求：在O(N)时间复杂度解决此题

使用**单调队列**的解法：构建一个队列，随着窗口的移动，队列也一进一出，每次移动之后队列都会更新一个“最大值”，可以直接取得这个最大值

单调队列要满足以下要求：

- **没有必要维护窗口里的所有元素，只需要维护<font color="red">有可能成为窗口里最大值的元素就可以了</font>,同时保证队列里的元素数值是由大到小的，**例如一个滑动窗口[2,3,5,1,4]，只维护{5,4}即可
- 保证队列的<font color="red">出口元素是窗口里的最大元素</font>
  1. pop(value)：如果窗口移除的元素value<font color="red">等于</font>单调队列的出口元素，那么<font color="red">队列出口弹出元素</font>，否则不用任何操作
  2. push(value)：如果push的元素value<font color="red">大于</font>入口元素的数值，那么就<font color="red">将队列入口的元素弹出</font>，<font color="red">直到push元素的数值小于等于队列入口元素的数值为止</font>

因此，实现该单调队列必须使用双端队列Deque，两边都能进出元素，Deque是一个接口，可以用LinkedList实例化它

```java
class MyQueue{
    Deque<Integer> deque;
    public MyQueue(){
        deque=new LinkedList<>();
    }
    //弹出元素时,看要弹出的这个元素是否和队头元素相同，相同的话弹出队头元素，不同的话不做任何操作
    public void pop(int x){
        if(deque.peek()==x){
            deque.pop();
        }
    }

    //放进元素时，把单调队列里比该元素小的元素都删除
    public void add(int x){
        while(!deque.isEmpty()&&deque.getLast()<x){
            deque.removeLast();
        }
        deque.addLast(x);
    }
    //最前面那个数就是最大的数
    public int peek(){
        return deque.peek();
    }
}
```

#### 347.前k个高频元素（map+小顶堆PriorityQueue）

首先遍历数组，构建<元素，出现次数>的map，然后遍历map的每一项，将它放入小顶堆中，当小顶堆的元素个数大于k了，就判断根元素的出现次数是否小于该元素的出现次数，是的话就将根出队列（根永远是最小的那个元素，因此都是把出现次数最小的那个元素弹出来）再把新的元素放进去，不是的话不用理，最后小顶堆里剩下的就是前k个高频元素。

**<font color="red">注意因为PriorityQueue默认是大顶堆，所以要改为小顶堆的话要重写它的接口</font>**

```java
PriorityQueue<int[]> priorityQueue=new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1]-o2[1];//改为小顶堆
            }
        });
```

## 六、二叉树

**二叉树的种类**

- 完全二叉树：除了最下面一层节点未填满外其余节点都填满了，并且最下面一层的节点都集中在该层最左边

  ![](https://camo.githubusercontent.com/3df703d22acfefd0d37f9b4ef5983c87547410329b5903dd903b23d124e52766/68747470733a2f2f636f64652d7468696e6b696e672d313235333835353039332e66696c652e6d7971636c6f75642e636f6d2f706963732f32303230303932303232313633383930332e706e67)

- 二叉搜索树（节点有数值的二叉树）：若某节点的左子树不为空，则左子树上所有节点的值都比它小；若右子树不为空，则右子树上所有节点的值都比它大
- 平衡二叉搜索树（AVL树）：空树或者左右两个子树的高度差绝对值不超过1

**二叉树的存储方式**

数组存储：父节点的数组下标是i，则它的左孩子的数组下标为2i+1，右孩子的数组下标为2i+2

链式存储：就是传统的定义TreeNode，然后通过指针连接到左右子树

**二叉树的遍历方式**

- 深度优先遍历：递归或迭代，一般用<font color="red">递归</font>
- 广度优先遍历（层次遍历）：递归或迭代，一般用<font color="red">迭代+队列</font>

#### 110.平衡二叉搜索树

递归，看每个结点的左右子树的最大深度，返回的是以该节点为根的子树的最大深度

```java
class Solution {
    boolean res;
    public boolean isBalanced(TreeNode root) {
        res=true;
        dfs(root,0);
        return res;
    }
    int dfs(TreeNode node,int len){
        if(node==null){
            return len;
        }
        int l=dfs(node.left,len+1),r=dfs(node.right,len+1);//l对应左子树的最大深度，r对应右子树的最大深度
        if(Math.abs(l-r)>1){
            res=false;
        }
        return Math.max(l,r);//返回以该节点为根的子树的最大深度
    }
}
```

#### 257.二叉树所有路径

这道题需要注意的是在每次递归返回的时候要把list中新加的自己给删掉，因为要回溯，并且传递的list是一个引用

#### 530.二叉搜索树的最小绝对差值

这个最小绝对差值出现在根节点-其左子树的最右子节点，或者根节点-其右子树的最左子节点

#### 501.二叉搜索树中的众数

最简单的方法就是用一个额外的Hashmap<数字，出现次数>存各元素的出现次数，然后重写hashmap的sort根据其value进行排序，然后取最大的那几个

**进阶做法：不使用hashmap求众数**

可以利用二叉搜索树中序遍历的结果是一个有序数组这一特点，相同的数在进行中序遍历的时候一定也是相邻的

#### 236.二叉树的最近公共祖先

<font color="red">利用后序遍历</font>转化为看以该树为根的子树里是否同时含p和q节点 

- 第一种情况：左、右子树均没有找到p或q（本题中不会出现这种情况）
- 第二种情况：左子树能找到到右子树找不到，那应返回左子树的查找结果         
- 第三种情况：右子树能找到但左子树找不到，那应返回右子树的查找结果         
- 第四种情况：左右子树均能找到，那应直接返回这个节点

#### 450.删除二叉搜索树中的节点

递归函数的返回值就是该节点被改为什么节点，有以下五种情况：

- 第一种情况：遍历到空节点了还没找到删除的节点，返回null

- 第二-五种情况：找到了要删除的节点

  - 第二种情况：该节点的左右子树均为空，直接返回null

  - 第三种情况：该节点的左子树不为空，右子树为空，返回左子节点

  - 第四种情况：该节点的右子树不为空，左子树为空，返回右子节点

  - 第五种情况：该节点的左右子树均不为空，则将该节点的左子节点变为该节点的右子树的最左面节点的左子节点，返回该节点右子节点为新的根节点，如下图所示。

    ![image-20230417193451543](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230417193451543.png)

#### 669.修剪二叉搜索树

这道题要注意一种情况，就是虽然某个节点被删掉了但是它的左子节点或右子节点却被保留了下来

#### 538.把二叉搜索树转为累加树

所有节点从大往小遍历即可，也就是反中序遍历：右→自己→左

## 六、回溯

回溯通过递归进行，回溯函数指的就是**递归函数**

回溯法可解决的问题：

- 组合问题：N个数按一定规则找出k个数的集合（注意组合是**无序**的）
- 切割问题：字符串按一定规则有几种切割方式
- 子集问题：N个数的集合里有几个符合条件的子集
- 排列问题：N个数按一定规则全排列有几种排列方式（注意排序是**有序**的）
- 棋盘问题：N皇后、解数独等

回溯函数代码模板<font color="red">一般回溯函数内部会有一个在本层内的迭代，每次迭代调用一次递归函数</font>：

```java
void backtracking(参数) {
    if (终止条件) {
        存放结果;
        return;
    }

    for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
        处理节点;
        backtracking(路径，选择列表); // 递归
        回溯，撤销处理结果
    }
}

```

### 组合

由于每个组合内的数字是无序的，因此回溯函数中的for i要从指定的start位置开始往后取，并且每次开启新的递归start相应的值都要i+1

#### 77.组合

这道题需要注意的两个点：

- 回溯函数的迭代中每次调用一次递归之后path中新增的数都要remove掉
- <font color="red">**res中新增的List必须要new一个新的,否则在后面会都变成空的！！！（因为传递的都是引用）**</font>
- 一个剪枝：迭代中i <= n - (k - path.size()) + 1

不用全局变量的写法：

```java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> combine(int n, int k) {
        combineHelper(n, k, 1,new ArrayList<>());
        return result;
    }

    private void combineHelper(int n, int k, int startIndex,List<Integer> path){
        //终止条件
        if (path.size() == k){
            result.add(new ArrayList<>(path));//new一个新的List!!!!!
            return;
        }
        for (int i = startIndex; i <= n - (k - path.size()) + 1; i++){
            path.add(i);
            combineHelper(n, k, i + 1,path);
            path.remove(path.size()-1);
        }
    }
}
```

使用全局变量的写法：

```java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> combine(int n, int k) {
        combineHelper(n, k, 1);
        return result;
    }

    private void combineHelper(int n, int k, int startIndex){
        //终止条件
        if (path.size() == k){
            result.add(new ArrayList<>(path));//new一个新的List!!!!!
            return;
        }
        for (int i = startIndex; i <= n - (k - path.size()) + 1; i++){
            path.add(i);
            combineHelper(n, k, i + 1);
            path.removeLast();
        }
    }
}
```

#### 216.组合总和Ⅲ

和上一题类似，就是回溯终止条件有不同。

#### 40.组合总和Ⅱ

这里同一个组合中的元素可以重复，但是不能有相同的组合，因此要进行<font color="red">去重**【回溯问题中只要是数组中可能有重复元素的都要进行去重】**</font>

回溯可以用树形图来表示，树中的同一层表示不同的组合，树枝表示同一组合中每一次选择元素的过程。因此这里的去重是要对<font color="red">同一层</font>的进行去重（也就是<font color="red">**在for循环内部相同的元素只能取一次**</font>）有两种方法：

- 要先把数组<font color="red">排序</font>，然后回溯函数内部for语句中<font color="red">若i>start&&nums[i]==nums[i-1]则不能取nums[i]</font>，【只适用于不要求取得的集合中各元素保持初始的相对位置】
- 回溯函数<font color="red">每一层</font>中弄一个hashmap或hashset标明这个元素在这一层是否被使用过【适用于所有情况】
- 弄一个<font color="Red">全局数组used</font>表示对应那一位数字是否被取过，这种方法<u>不仅能判断这一层是否被取过，还能判断上一层是否被取过</u>，要注意的点是<font color="red">开启新的回溯返回之后used[i]要重新置为false</font>【适用于所有情况】

### 切割

切割问题可以看作是特殊的组合问题，组合问题是选了一个数之后下一次递归就从后面的数字选

切割问题是在某处切了一刀之后下一次递归就在后面的位置切割

#### 131.分割回文串

包含两个部分：回溯切割+判断割出来的新部分是否为回文子串

```java
public class Solution {
    List<List<String>> res=new ArrayList<>();
    List<String> pre=new ArrayList<>();
    String s;
    public List<List<String>> partition(String s) {
        this.s=s;
        backTracking(0);
        return res;
    }
    void backTracking(int start){
        if(start>=s.length()){
            res.add(new ArrayList<>(pre));
            return;
        }
        for(int i=start;i<s.length();i++){
            if(isPalindrome(start,i)){
                pre.add(s.substring(start,i+1));
                dfs(i+1);
                pre.remove(pre.size()-1);
            }
        }
    }
    boolean isPalindrome(int start,int end){
        int l=start,r=end;
        while(l<r){
            if(s.charAt(l)!=s.charAt(r)){
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
```

#### 93.复原IP地址

其实就是在一个字符串中任选三个位置做切割，将这个字符串切成合适的四段

### 子集

每个子集组合内部的数字都是无序的，因此递归函数也要有一个start表明开始的位置，每次开启新的递归也要i+1，比较特别的是不是遍历到树枝的末尾才将组合添加进去，而是递归的过程中就要将新的组合添加进去。

<font color="red">另外不要忘了空集是任何集合的子集</font>

#### 78.子集

```java
List<List<Integer>> res=new ArrayList<>();
    List<Integer> pre=new ArrayList<>();
    int[] nums;
    public List<List<Integer>> subsets(int[] nums) {
        this.nums=nums;
        res.add(new ArrayList<>());
        backTracking(0);
        return res;
    }
    void backTracking(int start){
        if(start>=nums.length){
            return;
        }
        for(int i=start;i<nums.length;i++){
            pre.add(nums[i]);
            res.add(new ArrayList<>(pre));
            backTracking(i+1);
            pre.remove(pre.size()-1);
        }
    }
 }
```

#### 90.子集Ⅱ

和组合问题中的 40.组合总和Ⅱ 类似，由于数组中存在重复元素，也是要一开始进行数组的排序，然后回溯的时候进行<font color="red">层内去重</font>，具体方法是在同一层的for循环内相同的元素只能取一次

```java
public class subsetsWithDup_90 {
    List<List<Integer>> res=new ArrayList<>();
    List<Integer> pre=new ArrayList<>();
    int[] nums;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        this.nums=nums;
        res.add(new ArrayList<>());
        backTracking(0);
        return res;
    }
    void backTracking(int start){
        if(start>=nums.length){
            return;
        }
        for(int i=start;i<nums.length;i++){
            if(i>start&&nums[i]==nums[i-1]){
                continue;
            }
            pre.add(nums[i]);
            res.add(new ArrayList<>(pre));
            backTracking(i+1);
            pre.remove(pre.size()-1);
        }
    }
}

```

#### 491.递增子序列

这道题因为要取子序列，<font color="Red">子序列是有相对顺序关系的</font>，因此注意<font color="red">不能对数组进行排序</font>，因此<font color="red">不能用看nums[i]是否等于nums[i-1]的方法进行去重</font>

因此去重方法改为以下的：<font color="Red">在同一层内使用一个hashset或hashmap来标记是否取过这一数字</font>，取过了就不再取了

### 排列

排列中元素是有序的，也就是说[1,2]和[2,1]是两个不同的排序，取了一个后面的数还可能会取前面的数，因此<font color="red">回溯函数不能用start来表示起始位置</font>。在**同一个树枝**上只要满足取过的数不能再取即可，如果没有重复数字的话可以看<font color="red">pre里面没有这个数即可</font>，如果有重复数字的话<font color="red">同一层内要进行去重</font>

#### 46.全排列

这题数组中没有重复数字，直接看pre里有没有这个数即可

```java
class Solution {
    List<List<Integer>> res=new ArrayList<>();
    List<Integer> pre=new ArrayList<>();
    int[] nums;
    public List<List<Integer>> permute(int[] nums) {
        this.nums=nums;
        backTracking();
        return res;
    }
    private void backTracking(){
        if(pre.size()==nums.length){
            res.add(new ArrayList<>(pre));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if(!pre.contains(nums[i])){
                //可以取这个数
                pre.add(nums[i]);
                backTracking();
                pre.remove(pre.size()-1);
            }
        }
    }
}
```

#### 47.全排列Ⅱ

这道题中数组里可能有重复的数字，因此要进行去重，去重做法也是同一层内相同的元素只能取一次

这里使用全局used数组用于去重，不仅能知道这一层对应数字是否被取过，还能知道上一层是否被取过

去重的时候有一个需要注意的点就是不仅要满足i>0、nums[i]=nums[i-1]，<font color="red">还要当used[i-1]为false的时候才能跳过i</font>，因为这时候nums[i-1]和nums[i]是这一层也可以取的，两数存在重复。如果used[i-1]为true，则这个数在上一层被取过了，这一层本来就不能取这个数，所以<font color="red">nums[i]和nums[i-1]**虽然相等但不存在重复**</font>

```java
class Solution {
    List<List<Integer>> res=new ArrayList<>();
    List<Integer> pre=new ArrayList<>();
    boolean[] used;
    int[] nums;
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        this.nums=nums;
        used=new boolean[nums.length];
        backTracking();
        return res;
    }
    void backTracking(){
        if(pre.size()==nums.length){
            res.add(new ArrayList<>(pre));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if(i>0&&nums[i]==nums[i-1]&& used[i - 1] == false){
                //去重
                continue;
            }
            if(!used[i]) {
                //不仅要保证去重，而且要保证同一根树枝不能取之前取过的数
                used[i]=true;
                pre.add(nums[i]);
                backTracking();
                pre.remove(pre.size() - 1);
                used[i]=false;
            }
        }
    }
}
```

### 棋盘

#### 332.重新安排行程

几个步骤：

1.首先按照每个航班的目的地将航班排序，保证最终返回的结果一定是字典序最小的，然后用一个boolean[] used数组表示是否用了这个航班

2.回溯函数的返回值是boolean，表明这条路径是否行得通，函数内部for循环里遍历整个航班链表，比较该航班的起始地是否和pre中最后一个地方一样，并且这一航班没有被使用过，如果是的话，调用回溯函数并得到返回值，如果返回值是true则返回true，如果遍历完最后一个航班但返回值都是false的话就返回false

3.回溯结束条件：pre长度=航班链表长度+1，直接令结果res=pre，然后返回true

```java
public class findItinerary_332 {
    List<String> pre=new ArrayList<>();
    List<String> res=new ArrayList<>();
    boolean[] used;
    List<List<String>> tickets;
    public List<String> findItinerary(List<List<String>> tickets) {
        Collections.sort(tickets, new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                return o1.get(1).compareTo(o2.get(1));
            }
        });
        this.tickets=tickets;
        used=new boolean[tickets.size()];
        pre.add("JFK");
        backTracking();
        return res;
    }
    boolean backTracking(){
        if(pre.size()==tickets.size()+1){
            res=new ArrayList<>(pre);
            return true;
        }
        String prePlace=pre.get(pre.size()-1);//上一个地方
        for(int i=0;i< tickets.size();i++){
            if(!used[i]&&prePlace.equals(tickets.get(i).get(0))){//上一个地方是这趟航班的起点
                used[i]=true;
                pre.add(tickets.get(i).get(1));
                if(backTracking()){
                    return true;
                }
                pre.remove(pre.size()-1);
                used[i]=false;
            }
        }
        return false;
    }
}

```

#### 51.N皇后

皇后的约束条件：不能同行、同列、同斜线，

利用这个约束条件来回溯搜索整个棋盘，每一次回溯是遍历新的一行，直到遍历到最后一行

在回溯函数内遍历整的这一行，遍历到每一格都判断这格是否能够放皇后，能的话再放并开启新的回溯，

#### 37.解数独

规则： 数字 1-9 在每一行只能出现一次。 数字 1-9 在每一列只能出现一次。 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次

这道题和上一道题的不同之处：这道题遍历的时候要<font color="red">三层遍历</font>，上一题每一行放一个皇后就可以了，但这一题每个空格都需要放数字，所以前两层是遍历每一个格子<font color="red">找到第一个现在为空可以放数字的格子</font>，第三层是遍历数字1-9看这一格能不能放这个数字

注意回溯函数的返回值是boolean，也就是<font color="red">当找到一种可能了就要返回，而不是要找到所有的可能</font>，只要是<font color="red">**只需要找到一种可能性的返回值都是boolean**</font>

这里要注意的是每一次回溯都是<font color="red">找到第一个没放有数字的格子，这个格子只要数字1-9都不合适那就立马返回false（因为这个数独是一个一个格子按顺序放数字，如果下一格无论放什么格子都不合适那这个数独就是错误的，找不到解决数独问题的解），而不是等到遍历完了所有的格子才返回false</font>

## 七、贪心算法

- 本质：每一阶段选局部最优，从而达到全局最优
- 什么时候用：感觉可以局部最优推出整体最优，而且想不到反例，那么就试一试贪心。
- 步骤：将问题分解为子问题，求解子问题的局部最优，从而得到全局最优
- 贪心和动态规划的区别：动态规划一定和前一个阶段有关，贪心无关

#### 455.分发饼干

为了不造成饼干尺寸的浪费，那就要尽量把大尺寸的饼干分给大胃口的孩子

把饼干和孩子胃口都增序排序后，从后向前遍历饼干：

局部最优：每次尽量使大饼干分给大胃口的孩子

全局最优：所有饼干都分给了它所能满足的最大胃口的孩子，没有饼干被浪费

（作者网站写的也对，按我下面这样写也对，我下面这样其实更好理解）

```java
public int findContentChildren(int[] g, int[] s) {//g是胃口，s是饼干尺寸
        /**
         * 全局最优：每个饼干都分给了它所能满足的最大胃口的孩子，没有饼干被浪费
         * 局部最优：从后往前遍历饼干，把它分给它所能满足的最大胃口的孩子
         */
        Arrays.sort(g);
        Arrays.sort(s);
        int res=0;
        int index=g.length-1;
        for(int i=s.length-1;i>=0;i--){
            int size=s[i];
            //从后往前找到该饼干所能满足的最大胃口的孩子
            while(index>=0){
                if(g[index]<=size){
                    res++;
                    index--;
                    break;
                }
                index--;
            }
        }
        return res;
    }
```

#### 376. 摆动序列

局部最优：每次取局部极值（即峰值）

需要考虑平坡以及首尾两个元素的特殊情况

#### 53.最大子序列和

贪心思想：遍历整个数组，如果前面的连续和是负数，那立刻抛弃前面的从现在这个数开始

动态规划：dp[i]=max(dp[i-1]+nums[i], nums[i])，然后记录下max

#### 122.买卖股票的最佳时机Ⅱ

这里需要注意的是可以多次买卖哦股票，并且当天是可以先把股票卖了再买入这个股票的，所以只要第i天比第i-1天大就把nums[i]-nums[i-1]加进去就可以了

#### 055.跳跃游戏

贪心思想：每次取最大能到达的范围cover,然后一直在cover这个范围之内往前走，看是否能覆盖最后一个点

#### <font color="red">045.跳跃游戏Ⅱ</font>

要求跳的次数最少

贪心思想：每次若能到的最远距离是nextCover，则看这个nextCover是否覆盖了最后一个元素，如果覆盖了那就可以结束了，如果不能覆盖那说明肯定还要多走一次到nextCover范围内的某个元素处，走到这个元素了后又重复之前的判断……

#### 860.柠檬水找零

当放入5和10的时候其实操作都是固定的，只有放入20的时候有找一张10和一张5或者找三张5的情况

局部最优：当放入20的时候优先找一张10+一张5，因为5可以用来在放入10的时候找5，更“万能”

#### 135.分发糖果

注意：这里是说相邻情况如果a>b，那么a分得的糖果一定要比b多，如果a=b，那么其实是可以随便的

贪心思想：分两次遍历！第一次考虑右边比左边大的情况，第二次考虑左边比右边大的情况，不能一次考虑两边，否则会顾此失彼！

第一次从左往右，下标0的默认分得1，若下标i的大于i-1的，那么i分得的比i-1多1个；否则分1个

第二次从右往左，下标len-1的不动，若下标i的大于i+1的，那么i分得的个数=max(i在上一次分得的个数，i+1分得的个数)，以保证它如果比左右两边的都大，那它分得的比左右两边都多；否则还是按它上次分到的

#### 406.根据身高重建队列

这道题和135.分发糖果类似，都是先按一个条件排之后，再按第二个条件排，如果一次同时考虑两个条件那就会顾此失彼

这道题先考虑h，先把所有数按照从大到小排（身高相同的k小的站前面），

再从左往右遍历，考虑k，把这个数组往ki的位置插

这道题需要注意的一个点是重写Arrays.sort函数里Comparator接口的compare方法，而且是二维数组的compare

#### 435.无重叠区间【二维数组重叠区间问题】

选择按左边界排序，不管右边界

排序之后遍历排序后的二维数组，用一个preEnd记录之前最左端的右边界

如果nums [i] [0]≥end，那肯定没有重叠，否则肯定重叠了preEnd更改为nums [i] [1]，这时候选择删除右边界最大的那个（贪心思想：为了尽可能少地影响后面的数），所以preEnd改为preEnd和nums [i] [1]中较小的一个

#### 56.合并区间【二维数组重叠区间问题】

和上一题思路类似，把二维数组按左边界排序，然后遍历排序后的二维数组

维护一个start和一个end，如果nums [i] [0]>end则说明start和end要从新的开始了，把之前的[start,end]加入结果中，否则说明肯定该区间和之前的存在重合，令end=max(end,nums[i] [1])



## 动态规划

- 动规五部曲

  1.确定dp数组（dp table）以及下标的含义

  2.确定递推公式

  3.dp数组如何初始化

  4.确定遍历顺序

  5.举例推导dp数组

## 动态规划中的背包问题

01背包：每个物品数量只有一个，选或不选

完全背包：每个物品数量有无数个，可以不选或选若干个

##### 01背包的二维数组dp[i] [j]含义：从下标为[0-i]的物品里任意取，放进容量为j的背包，价值总和最大是多少

dp[i] [j]中，i的个数是物品的对应下标，j是从0到背包最大容量

推导dp[i] [j]的方法——放或不放物品i：

- 当weight[i]>j时：不放，dp[i] [j]=dp[i-1] [j]

- 当weight[i]≤j时：放，dp[i] [j]=dp[i - 1] [j - weight[i]] + value[i]

- 综上：

  weight[i]>j时，dp[i] [j]=dp[i-1] [j]；

  weight[i]≤j时，dp[i] [j]=max(dp[i-1] [j], dp[i - 1] [j - weight[i]] + value[i])

初始化：

- j=0时：dp[i] [0]=0
- i=0时：当j<weight[0]时，dp[0] [j]=0；j≥weight[0]时，dp[0] [j]=value[0]

遍历顺序：

- 嵌套两个遍历，for i里包着for j也行，**反过来也行**

返回结果：当有m个物品，背包容量为n时，返回**dp[m] [n]**

##### 01背包问题也可以把二维数组降为一维滚动数组

因为从上面可知，i这一层只和i-1这一层有关，且dp[i-1] [j]和dp[i - 1] [j - weight[i]]一定是在dp[i] [j]左上方的，所以其实可以把i这一层去掉，只留下j这一层

weight[i]>j时，dp[j]=dp[j]；

weight[i]≤j时，dp[i] [j]=max(dp[j], dp[j - weight[i]] + value[i])

初始化：全为0（或者和二维数组的版本那样初始化，不同的是下面遍历时i要从1开始遍历）

遍历顺序：

也是一个嵌套循环，i套着j（不能反），i是遍历物品，如果初始化时dp全为0则从i=0开始，如果和二维数组那样初始化那从i=1开始，j是遍历背包，**需要注意的是j要从最大容量开始倒序遍历（因为算右边的数时需要用到左边的数，如果先算左边的数，那左边的数就会被i层的值覆盖了，就不是原来i-1层的值了；而算左边的数不会用到右边的数，所以右边的数先被改也没有什么影响）**

#### 416.分割等和子集【给定背包容量，求能不能装满背包】

可以化为01背包问题——背包体积为sum/2，问能不能放入若干个数使得背包刚好放满

- dp的含义：dp[i] [j]表示遍历到下标为i的数时将下标为0-i的数放入容量为j的背包时背包的最大重量
- 初始化：i=0时，若j大于等于nums[0]，则dp [i] [j]=nums[0]，否则dp [i] [j]=0；j=0时，dp [i] [j]=0
- 遍历顺序：i嵌套着j，两个都是从左到右
- 返回：当j=target时，若有某i使得dp [i] [target]==target，则返回true

#### 1049.最后一块石头的重量【给定背包容量，尽可能装满，能装多满】

可以转为背包问题，就是尽量把所有石头划为重量相等的两堆

即求出背包最大容量target=sum/2时背包重量的最大值，

因为sum/2是向下取整，所以这个最大值一定小于另一堆

所以结果是sum-dp[stones.length-1] [target]-dp[stones.length-1] [target]

#### <font color="red">494.目标和(这道题建议死记硬背，佛了……）</font>【给定背包容量，装满背包有多少种方法】

这道题漏说了一个条件：数组中的数一定是非负数！

这道题设前面+号的数之和为x，-号的数之和为y，x-y=target, x+y=sum  =>x=(target+sum)/2

注意：当(target+sum)不能整除2以及target的绝对值比sum要大的时候肯定是不能满足的（因为nums[i]都是非负数）

所以就转化为背包问题，就有没有和为x=(target+sum)/2的情况，并且要求有多少种满足这种情况的<font color="red">**组合**（注意不是求最大重量了，而是求组合数！）</font>

- dp[j]：从数组中取数一共有多少种方法使得取得的数之和为j

- 初始化：<font color="red">dp[0]=1</font>

- 当有一个nums[i]时，dp[j]=dp[j]+dp[j-nums[i]]，原因：

  ![image-20230405221338055](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230405221338055.png)

- 代码如下：

  ```java
  public int findTargetSumWays(int[] nums, int target) {
          int sum = 0;
          for (int n : nums) {
              sum += n;
          }
          if ((target + sum) % 2 != 0) {
              return 0;
          }
          if (Math.abs(target) > sum) {
              return 0;
          }
          int x = (target + sum) / 2;
  
          int[] dp = new int[x + 1];
          dp[0] = 1;
          for (int i = 0; i < nums.length; i++) {
              for (int j = x; j >= nums[i]; j--) {
                  dp[j] += dp[j - nums[i]];
              }
          }
          return dp[x];
      }
  ```

#### 474.一和零【给定背包容量，装满背包最多有多少个物品】



## 打家劫舍

打家劫舍问题的要点在于dp[i]表示的是<font color="red">0-i这一段可以偷的最大金额，而不是说i这个房间一定要被偷</font>

#### 198.打家劫舍1

- dp[i]表示<font color="red">考虑下标0-i的房屋最多</font>可以偷窃的金额
- 当走到i时，有两种情况，偷或不偷，如果偷，则dp[i] = dp[i - 2] + nums[i]，如果不偷则dp[i]=dp[i-1]，取两者中大的
- 返回dp最后一个数

#### 213.打家劫舍Ⅱ

这题的难点在于成环了，其实特殊的点在于首尾元素，当考虑首部元素时，一定不能考虑尾部元素，当考虑尾部元素时，一定不能考虑首部元素

所以其实得遍历两次，第一次是从下标0到len-2，也就是首部元素到倒数第二个元素；第二次是从下标1到len-1，也就是第二个元素开始到尾部元素

取两次遍历的结果中的最大值即可

![](https://camo.githubusercontent.com/7b9cd836f98b01ea8a146920e53015d909783ef8c4ceb066657e2f90f00915bf/68747470733a2f2f636f64652d7468696e6b696e672d313235333835353039332e66696c652e6d7971636c6f75642e636f6d2f706963732f32303231303132393136303832313337342d32303233303331303133343030333936312e6a7067)

![](https://camo.githubusercontent.com/3a5cfd80c966a0c512d8b4b57c51403b8982c7a4be89e5e994fb08d17fa19c94/68747470733a2f2f636f64652d7468696e6b696e672d313235333835353039332e66696c652e6d7971636c6f75642e636f6d2f706963732f32303231303132393136303834323439312d32303233303331303133343030383133332e6a7067)

注意打家劫舍问题中dp[i]是考虑是否放这个元素的意思，不是一定要放这个元素的意思，如果没有遍历到i那么说明这个元素不需要被考虑

#### 337.打家劫舍Ⅲ

<font color="red">树形</font>打家劫舍

在动规的思想上融合DFS+后序遍历，当遍历到每个节点时先遍历其两个子节点，分别得到fl（取左子节点）、gl（不取左子节点）、fr（取右子节点）、gr（不取右子节点）四个数，然后如果取这个节点的话那就是val+gl+gr，不取的话就是val+max(fl,gl)+max(fr,gl)，返回这两个值到上一步递归，最后返回的是根节点对应的那两个值，取最大的即可。

<font color="voilet">建议可以看一下我之前写的代码</font>



## 股票问题

核心思想：利用二维dp数组来记录多个状态

需要注意初始化的问题：在i=0时，一律将当前持有股票设为-prices[0]，将当前未持有股票设为0

#### 121.买卖股票的最大时机

只能买卖股票一次

这道题用贪心也可以做且更为简单，下面说说动规的解法

构建一个二维数组dp[len] [2]

- dp [i] [0]表示**第i天持有股票所得的最多现金**（注意：“持有”可以是之前就持有了也可以是这一天买入的，去最大的一个，所以<font color="red">dp [i] [0]=max(dp [i-1] [0], -prices[i])</font>
- dp [i] [1]表示**第i天不持有股票所得的最多现金**（注意：“不持有”可以是之前就卖掉了也可以是今天刚卖出，取大的一个，所以<font color="red">dp [i] [1]=max(dp [i-1] [1], dp[i-1] [0]+prices[i])</font>
- 初始化：dp [0] [0]=-prices[0], dp [0] [1]=0
- 返回dp [len-1] [1]，因为按照题意，最后一天肯定是不持有股票所得的现金多

#### 122.买卖股票的最佳时机Ⅱ

可以买卖股票无限次买卖，并且同一天可以卖出之后再买入

该题在贪心也讲过了，只要prices[i]>prices[i-1]就将prices[i]>prices[i-1]加进最终的利润里，下面讲一下动规的写法。

dp [i] [0]和dp [i] [1]所表示的含义和前一道题一样，但因为可以多次买卖股票，所以递推公式有所区别：

- dp [i] [0]=max(dp [i-1] [0], dp[i-1] [1]-prices[i])
- dp [i] [1]=max(dp [i-1] [1], dp[i-1] [0]+prices[i])

#### 714.买卖股票的最佳时机含手续费

跟前一题相比，再卖出股票时交个手续费即可

- dp [i] [0]=max(dp [i-1] [0], dp[i-1] [1]-prices[i])
- dp [i] [1]=max(dp [i-1] [1], dp[i-1] [0]+prices[i]-手续费)

#### 123.买卖股票的最佳时机Ⅲ

<font color="red">最多只能买卖两次股票，可以一次都不买卖，也可以只买卖一次</font>

构建一个二维数组dp[len] [5] 

![image-20230413194413045](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230413194413045.png)

- dp [i] [0]：第i天还**没有操作**，就是这一天及其之前都没有买卖任何股票，dp [i] [0]=0
- dp [i] [1]：第i天处于**第一次持有股票**状态所拥有的最大现金，注意不一定是一定在这一天买入，可以是之前就持有。dp [i] [1]=max(dp [i-1] [1], dp[i-1] [0]-prices[i])
- dp [i] [2]：第i天处于**第一次不持有股票**状态所拥有的最大现金，注意不一定是一定在这一天卖出，可以是之前就不持有。dp [i] [2]=max(dp [i-1] [2], dp[i-1] [1]+prices[i])
- dp [i] [3]：第i天处于**第二次持有股票**状态所拥有的最大现金，dp [i] [3]=max(dp [i-1] [3], dp[i-1] [2]-prices[i])
- dp [i] [4]：第i天处于**第二次不持有股票**状态所拥有的最大现金，dp [i] [4]=max(dp [i-1] [4], dp[i-1] [3]+prices[i])

初始化：dp [i] [0]=0，dp[0] [1]=-prices[0], dp[0] [2]=0, <font color="red">dp[0] [3]=-prices[0]</font>, dp[0] [4]=0，注意第0天第二次持有股票也要初始化为-prices[0]，因为第二次买入其实只依赖于第一次卖出的状态，其实相当于第0天第一次买入了，第一次卖出了，然后再买入一次（第二次买入），那么现在手头上没有现金，只要买入，现金就做相应的减少。

返回max(dp [len-1] [0], dp [len-1] [1], dp [len-1] [2], dp [len-1] [3], dp[len-1] [4])

#### 188.买卖股票的最佳时机Ⅳ

<font color="red">最多可以买卖k次股票，和上一题的区别是**k不确定**</font>

参考上一题，二维数组dp[i] [j]中第二维的个数是2k+1

- j=0表示不操作，dp [i] [0]=0
- j=1表示第一次持有，dp [i] [1]=max(dp [i-1] [1], dp[i-1] [0]-prices[i])
- j=2表示第一次不持有，dp [i] [2]=max(dp [i-1] [2], dp[i-1] [1]+prices[i])
- j=3表示第二次持有，dp [i] [3]=max(dp [i-1] [3], dp[i-1] [2]-prices[i])
- j=4表示第二次不持有，dp [i] [4]=max(dp [i-1] [4], dp[i-1] [3]+prices[i])
- ……
- j=2k-1表示第k次持有，dp [i] [2k-1]=max(dp [i-1] [2k-1], dp[i-1] [2k-2]-prices[i])
- j=2k表示第k次不持有，dp [i] [2k]=max(dp [i-1] [2k], dp[i-1] [2k-1]+prices[i])

初始化：i=0时当j为奇数时，dp[0] [j]=-prices[0]，j为偶数时dp[0] [j]=0

返回的也是最后一维中最大的那个数

#### 309.最佳买卖股票时机含冷冻期

可以无限次买卖，但<font color="red">卖出股票后会有一天的冷冻期，这一天内无法再买股票</font>

也是定义二维数组dp [i] [j]表示第i天手上有的最多现金，j的含义如下：

- j=0，这天<font color="red">持有股票</font>，可以是之前就持有的也可以是这天买入，这一天一定不能是冷冻期，就是说前一天一定不能卖出股票

  dp [i] [0]=max(dp [i-1] [0], dp [i-1] [1]-prices[i],  dp [i-1] [3]-prices[i])

- j=1，这天不持有股票且<font color="red">不在冷冻期，除了不可能是前一天卖出股票外，可能是前一天不持有股票的任何情况</font>

  dp [i] [1]=max(dp [i-1] [1], dp [i-1] [3])

- j=2，这天不持有股票且<font color="red">卖出了股票</font>dp[i] [2]=dp[i-1] [0]+prices[i]

- j=3，这天不持有股票且<font color="red">在冷冻期，也就是前一天一定卖出了股票</font>，dp[i] [3]=dp [i-1] [2]

初始化：dp [0] [0]=-prices[0], dp [0] [1]=0, dp [0] [2]=0, dp [0] [3]=0





## 动态规划中的字符串子序列操作问题

#### 300.最长递增子序列

<font color="red">注意子序列和连续序列不同，子序列中间是可以断开的</font>

dp[i]表示以nums[i]结尾的最长递增子序列的长度

初始化：每个dp[i]初始都为1

```java
int res=1;
for(int i=0;i<nums.length;i++){
	dp[i]=1;
	for(int j=0;j<i;j++){
        if(nums[i]>nums[j]){
            dp[i]=Math.max(dp[i],dp[j]+1);
        }
    }
    res=Math.max(res,dp[i]);
}
return res;
```

#### 674.最长连续递增序列

dp[i]表示以nums[i]结尾的最长连续递增序列的长度

```java
int res=1;
dp[0]=1;
for(int i=1;i<nums.length;i++){
    dp[i]=1;
    if(nums[i]>nums[i-1]){
        dp[i]=dp[i-1]+1;
    }
    res=Math.max(res,dp[i]);
}
return res;
```

#### 718.最长重复子数组

dp [i] [j]：以下标<font color="red"> i-1为结尾</font>的A和以下标<font color="red">j-1为结尾</font>的B的最长重复子数组长度

i嵌套遍历j，当A[i-1]=B[j-1]时，dp[i] [j]=dp[i-1] [j-1]+1

初始化：dp[i] [0]、dp[0] [j]初始化为0

```java
int res=0;
for(int i=0;i<nums1.length;i++){
    dp[i][0]=0;
}
for(int j=0;j<nums2.length;j++){
    dp[0][j]=0;
}
for(int i=1;i<=nums1.length;i++){
    for(int j=1;j<=nums2.length;j++){
        if(nums1[i-1]==nums2[j-1]){
            dp[i][j]=dp[i-1][j-1]+1;
            res=Math.max(res,dp[i][j]);
        }
    }
}
return res;
```

#### 1143.最长公共子序列

dp [i] [j]表示以下标i-1为结尾的A和以下标j-1为结尾的B的最长公共子序列的长度

- 当A[i-1]=B[j-1]时，dp [i] [j]=dp [i-1] [j-1]+1
- 当A[i-1]≠B[j-1]时，dp [i] [j]=max(dp [i-1] [j],dp [i] [j-1])

初始化：dp[0] [j]=0, dp[i] [0]=0

#### 583.两个字符串的删除操作

第一种思路是利用上面那道题找出两个字符串的最长公共子序列，<font color="red">除了最长公共子序列以外的字符都要删除</font>

第二种思路如下：

dp[i] [j]：以i-1为结尾的字符串A，和以j-1位结尾的字符串B，想要达到相等，所需要删除元素的最少次数。

- A[i-1]==B[j-1]时，dp[i] [j]=dp[i-1] [j-1]
- A[i-1]不等于B[j-1]时，可以删A[i-1]、B[j-1]或两个都删，取操作数最少的那个，所以dp[i] [j]=min(dp[i-1] [j]+1, dp[i] [j-1]+1, dp[i-1] [j-1]+2)

返回dp[word1.length()] [word2.length()]

#### 72.字符串最小编辑距离

给两个字符串A、B，每次可以插入/删除或修改一个字符，求经过多少次字符操作能够使两字符串变为相同

**edit\[i][j]**表示以下标i-1为结尾的字符串A和以下标j-1为结尾的字符串B的最小编辑距离

确定递推公式的过程——当遍历到A[i-1]、B[j-1]时，一共有以下四种情况：

- A[i-1]=B[j-1]：不做任何操作，edit\[i][j]=edit\[i-1][j-1]

- A[i-1]≠B[j-1]：

  - A删除掉下标i-1的元素，那么就是以下标i-2为结尾的A与 下标j-1为结尾的B的最近编辑距离 再加上一个操作：edit\[i][j]=edit\[i-1][j]+1

  - B删除掉下标j-1的元素，那么就是以下标i-1为结尾的A与 下标j-2为结尾的B的最近编辑距离 再加上一个操作：edit\[i][j]=edit\[i][j-1]+1

    <font color="red">【需要注意的是，字符串删除结尾字符其实相当于在另一个字符串结尾添加字符，例如 word1 = “ad” ，word2 = “a”，word1删除元素’d’ 之后变为word1=“a”, word2=“a”，和 word2添加一个元素’d’，变成word1=“ad”, word2=“ad”， 最终的操作数是一样的】</font>

  - A替换A\[i-1]为B\[j-1]：edit\[i][j]=edit\[i-1][j-1]+1

  综上有：
  
  ```java
  if (A[i - 1] == B[j - 1]) {
      edit[i][j] = edit[i - 1][j - 1];
  }
  else {
      edit[i][j] = min({edit[i - 1][j - 1], edit[i - 1][j], edit[i][j - 1]}) + 1;
  }
  ```

初始化：

- `edit[i][0]` ：以下标`i-1`为结尾的字符串`A`，和空字符串`B`，最近编辑距离，为i
- `edit[0][j]` ：以下标`j-1`为结尾的字符串`B`，和空字符串`A`，最近编辑距离，为j

遍历顺序：i中嵌套j，两者都是从1开始到结尾

完整代码：

```java
int minEditDistance(String A,String B){
    int[][] edit=new int[A.length()+1][B.length()+1];
    for(int i=0;i<=A.length();i++){
        edit[i][0]=i;
    }
    for(int j=0;j<=B.length();j++){
        edit[0][j]=j;
    }
    for(int i=1;i<=A.length();i++){
        for(int j=1;j<=B.length();j++){
            if(A.charAt(i)==B.charAt(j)){
                edit[i][j]=edit[i-1][j-1];
            }
            else{
                edit[i][j]=Math.min(edit[i-1][j],Math.min(edit[i][j-1],edit[i-1][j-1]))+1;
            }
        }
    }
    return edit[A.length()][B.length()];
}
```

