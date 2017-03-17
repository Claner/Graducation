package Util;

/**
 * Created by Clanner on 2017/3/13.
 */
public class Son extends Father {

    public Son() {
        age = 18;
        name = "son";
    }

    @Override
    protected void teach() {
        super.teach();
        System.out.println("教数学");
    }
}
