import com.baomidou.mybatisplus.extension.api.R;

import java.io.IOException;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        List list = Collections.synchronizedList(new ArrayList<>());

        testE();
    }

    public static void testE() {
        try {
            int i=1/0;
        }catch (ArithmeticException e){
            e.printStackTrace();
        }
        finally {
            System.out.println("finish");
        }
    }
}
