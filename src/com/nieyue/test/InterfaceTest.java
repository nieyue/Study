package com.nieyue.test;

import java.util.HashMap;
import java.util.Map;

public class InterfaceTest {
    interface Interface {
        String a();
        default  int b(){
           // System.err.println(22);
            return 22;
        }
    }


    class InterfaceImpl implements Interface{

      @Override
      public String a() {
          return "a!";
      }

        @Override
        public int b() {
           // System.err.println(2233);
            return 2233;
        }
    }
    public static void main(String[] args) {
        InterfaceTest it = new InterfaceTest();
        InterfaceImpl ii = it.new InterfaceImpl();
       // System.out.println(ii.a());
       // System.out.println(ii.b());

        ThreadLocal<Integer> tl=new ThreadLocal<>();
        System.out.println(tl.get());
        tl.set(0);
        System.out.println(tl.get());
        tl.set(2);
        System.out.println(tl.get());
        tl.remove();
        System.out.println(tl.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name="线程2";
                System.out.println(name+tl.get());
                tl.set(0);
                System.out.println(name+tl.get());
                tl.set(2);
                System.out.println(name+tl.get());
                tl.remove();
                System.out.println(name+tl.get());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name="线程1";
                System.out.println(name+tl.get());
                tl.set(0);
                System.out.println(name+tl.get());
                tl.set(2);
                System.out.println(name+tl.get());
                tl.remove();
                System.out.println(name+tl.get());
            }
        }).start();

    }
}
