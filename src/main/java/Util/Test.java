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
    public static void main(String[] args) {
        String pass = "123456";
        System.out.println(GeneralUtils.EnCode(pass));
    }
}
