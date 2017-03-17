package Controller;

import Entity.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import java.io.IOException;

/**
 * Created by Clanner on 2017/3/11.
 */
@RestController
@RequestMapping("Test")
@CrossOrigin
public class TestController {

    @Autowired
    private Response.Builder builder;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Response test() {

        try {
            Document document = Jsoup.connect("http://www.wyu.edu.cn/")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding","gzip, deflate, sdch")
                    .header("Accept-Language","zh-CN,zh;q=0.8")
                    .header("Cache-Contro","max-age=0")
                    .header("Connection","keep-alive")
                    .header("Cookie","safedog-flow-item=F18B78FF927EA883ED8AFE26F73E2FB8; ASPSESSIONIDAQABRQBC=OHCJIIIAJOELOHEKKGIGKEAC; fontsize=1")
                    .get();

            Elements elements = document.getElementsByClass("wyu-box-item");
            for (int i =0;i<elements.size();i++){
                System.out.println(elements.get(i).select("a[href]").text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.setCode(20000).setMessage("测试").build();
    }
}
