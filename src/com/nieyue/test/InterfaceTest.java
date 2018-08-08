package com.nieyue.test;

public class InterfaceTest {
    interface Interface {
        String a();
        default  int b(){
            System.err.println(22);
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
            System.err.println(2233);
            return 2233;
        }
    }
    public static void main(String[] args) {
        InterfaceTest it = new InterfaceTest();
        InterfaceImpl ii = it.new InterfaceImpl();
        System.out.println(ii.a());
        System.out.println(ii.b());
    }
}
