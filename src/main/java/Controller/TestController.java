package Controller;

import Entity.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonObject;
import java.io.IOException;

/**
 * Created by Clanner on 2017/3/11.
 */
@RestController
@RequestMapping("Test")
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Response test() {

        String url = "http://wyugrade.bensonwu.cn/Home/Student/getStudentGrade";

        try {
            Document document = Jsoup.connect(url)
                    .timeout(36000)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, sdch")
                    .header("Accept-Language", "zh-CN,zh;q=0.8")
                    .header("Connection", "keep-alive")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
                    .data("student_num", "3114002414")
                    .data("password", "950725")
                    .ignoreContentType(true)
                    .post();

            String json = document.body().text();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            Document doc = Jsoup.connect("http://jwc.wyu.edu.cn/student/logon.asp")
//                    .timeout(3000)
//                    .userAgent("test")
//                    .cookie("auth", "token")
//                    .data("UserCode", "3115001734")
//                    .data("UserPwd", "Lm123456")
//                    .data("Validate", "0312")
//                    .post();
//            String text = doc.body().text();
//            System.out.println("转码前：" + text);
//            String str = new String(text.getBytes(), "gbk");
//            System.out.println("转码后：" + str);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @RequestMapping(value = "/getStudentGrade", method = RequestMethod.POST)
    public Response getStudentGrade(@RequestParam("student_num") String[] student_num,
                                    @RequestParam("password") String[] password) {
        for (String s : student_num) {
            System.out.println(s);
        }
        for (String s : password) {
            System.out.println(s);
        }
        return null;
    }
}
