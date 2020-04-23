package com.wuliang.ncov.controller;


import org.junit.Test;

public class CharsetTest {

    @Test
    public void test() {
        ThreadDemo demo = new ThreadDemo();
        new Thread(demo).start();

        while (true) {
            if (demo.isFlag()) {
                System.out.println("wuliang");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {
    private boolean flag = false;

    @Override
    public void run() {
        System.out.println(flag);
        try {

            Thread.sleep(200);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.flag = true;

        System.out.println(this.isFlag());
        System.out.println(flag);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
