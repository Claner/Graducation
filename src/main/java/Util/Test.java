package Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/3/10.
 */
public class Test {

    static {
        System.out.println("aaa");
    }

    static {
        System.out.println("bbb");
    }

    public static void main(String[] args) {

        String cID = "[1,2,3,4]";
        String time = "[\"1-3\",\"2-3\",\"1-5\"]";
        JSONArray timeArray = JSON.parseArray(time);
        JSONArray cArray = JSON.parseArray(cID);
        String[] tArr = new String[timeArray.size()];
        Integer[] cArr = new Integer[cArray.size()];
        for (int i =0;i<timeArray.size();i++){
            tArr[i] = (String) timeArray.get(i);
            System.out.println(tArr[i]);
        }

        for (int i = 0;i<cArray.size();i++){
            cArr[i] = (Integer) cArray.get(i);
            System.out.println(cArr[i]);
        }
    }
}
