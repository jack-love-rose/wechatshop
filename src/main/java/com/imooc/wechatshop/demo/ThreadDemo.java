package com.imooc.wechatshop.demo;


/**
 * @author 褚佳鑫
 * @description TODO
 * @date 2020/11/16 10:56
 **/
public class ThreadDemo extends Thread {

    public static void main(String[] args) {
        new Thread(new RunnableDemo()).start();
        new Thread(new RunnableDemo()).start();
    }
}

class RunnableDemo implements Runnable{

    @Override
    public void run() {
        System.out.println("安徽佛祖和");
    }
}
