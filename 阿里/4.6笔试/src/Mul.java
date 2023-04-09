import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 第二题的标准答案
 */
public class Mul {
    public static void main(String[] args) {
        List<Goods> goods=new ArrayList<>();
        for(int i=0;i<3;i++){
            goods.add(new Goods());
        }
        Mul m = new Mul();
        Thread p = new Thread(m.new Producer(goods));
        Thread c1 = new Thread(m.new Consumer(goods, 1));
        Thread c2 = new Thread(m.new Consumer(goods, 2));
        Thread c3 = new Thread(m.new Consumer(goods, 3));
        Thread c4 = new Thread(m.new Consumer(goods, 4));
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        p.start();
    }

    // 商品类
    static class Goods {
    };
    // 生产者类
    class Producer implements Runnable {
        final int MAX_SIZE = 3;
        private List<Goods> goods;
        public Producer(List<Goods> goods) {
            this.goods = goods;
        }

        @Override
        public void run() {
            while (true) {
                if (MAX_SIZE == goods.size()) {
//                    synchronized (goods) {
//                        try {
//                            System.out.println("货架已满");
//                            goods.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                } else {
                    synchronized (goods) {
                        Goods g = new Goods();
                        goods.add(g);
                        System.out.println("Producer" + "生产，货架商品有:" + goods.size()+"个");
                        goods.notify();
                    }
                }
                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // 消费者类
    class Consumer implements Runnable {
        private List<Goods> goods;
        private int num;
        private int eated;
        public Consumer(List<Goods> goods, int num) {
            this.goods = goods;
            this.num = num;
            this.eated=0;
        }

        @Override
        public void run() {
            while (eated<3) {
                if (goods.size() > 0) {
                    synchronized (goods) {
                        goods.remove(0);
                        eated++;
                        System.out.println("ConsumerThread" + this.num + "消费，货架还剩商品:" + goods.size());
                        goods.notifyAll();
                    }
                } else {
                    synchronized (goods) {
                        System.out.println("货架已空");
                        try {
                            goods.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
