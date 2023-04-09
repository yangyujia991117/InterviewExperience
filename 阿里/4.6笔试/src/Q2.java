import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Q2 {
    /**
     * 多线程实现一个生产者、多个消费者的模型
     */
    public static void main(String[] args) {
        List<Food> foods=new ArrayList<>();
        int size=3;
        for(int i=0;i<size;i++){
            foods.add(new Food());
        }
        Producer producer=new Producer(foods,size);
        Thread p=new Thread(producer);
        Consumer c1=new Consumer(foods,1);
        Consumer c2=new Consumer(foods,2);
        Consumer c3=new Consumer(foods,3);
        Thread c1Thread=new Thread(c1);
        Thread c2Thread=new Thread(c2);
        Thread c3Thread=new Thread(c3);
        p.start();
        c1Thread.start();
        c2Thread.start();
        c3Thread.start();

    }
    //食物
    static class Food{

    }
    //生产者
    static class Producer implements Runnable{
        List<Food> foods;
        int size;
        public Producer(List<Food> foods,int size){
            this.foods=foods;
            this.size=size;
        }
        @Override
        public void run() {
            while(true){
                if(foods.size()<size){
                    synchronized (foods){
                        foods.add(new Food());
                        System.out.println("生产者生产一个食物，现在有食物"+foods.size());
                        foods.notifyAll();
                    }
                }
                else{
                    synchronized (foods){
                        try {
                            foods.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    //消费者
    static class Consumer implements Runnable{
        List<Food> foods;
        int id;
        int eated;
        public Consumer(List<Food> foods,int id){
            this.foods=foods;
            this.id=id;
            eated=0;
        }
        @Override
        public void run() {
            while(eated<3){//吃够三个了就退出
                if(foods.size()>0){
                    synchronized (foods){
                        System.out.println("线程"+id+"进来了,现在有食物"+foods.size());
                        foods.remove(0);
                        eated++;
                        System.out.println("线程"+id+"拿到了食物,现在有食物"+foods.size());
                        foods.notifyAll();
                    }
                }
                else{
                    synchronized (foods){
                        try {
                            foods.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //注意后面一定要有这一段，我也不知道为什么
//                try {
//                    Thread.sleep(new Random().nextInt(3000));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            }
        }
    }
}
