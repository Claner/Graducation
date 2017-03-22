package Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/3/10.
 */
public class Test {

    static {
        System.out.println("Test");
    }

    public static void main(String[] args) {
        Thread t = new Thread() {
            public void run() {
                pong();
            }
        };
        t.run();
        System.out.print("ping");
    }

    static void pong() {
        System.out.print("pong");
    }

    public static void test(String b) {
        b = "hello";
        System.out.println("b："+b);
    }

    public static class Single {

        static {
            System.out.println("Single");
        }

        public static Single getInstance() {
            return Holder.single;
        }

        private static final class Holder {

            static {
                System.out.println("Holder");
            }

            private static final Single single = new Single();
        }
    }
}
