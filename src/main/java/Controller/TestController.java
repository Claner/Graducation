package Controller;

import Entity.Info;
import Entity.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/3/11.
 */
@RestController
@RequestMapping("Test")
@CrossOrigin
public class TestController {

    private String baseUrl = "http://www.wyu.edu.cn/news/";
    private Elements elements;

    @Autowired
    private Response.Builder builder;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Response test() {

        try {
            Document document = Jsoup.connect("http://www.wyu.edu.cn/news/index.asp?pg=1&m=0&tid=0&pid=0&cid=0")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, sdch")
                    .header("Accept-Language", "zh-CN,zh;q=0.8")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    .timeout(5000)
                    .header("Cookie", "fontsize=1; ASPSESSIONIDAQABRQBC=OHCJIIIAJOELOHEKKGIGKEAC; n%5Fident%5FSF%5Fs=0; n%5Fident%5Fname%5Fs=3113001234; fontsize=1; ASPSESSIONIDCQBBRRBD=CEBENEFBDJLINEDHJGPMKJNJ; safedog-flow-item=5E87C1CC4BDA854B6E284336BDF67812")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
                    .get();

            elements = document.getElementsByClass("wyu-lineheight-s");
            int type = 0;
            switch (type) {
                case 0:
                    return builder.setCode(20000).setData("校内通知")
                            .setDataList(getItemList(0)).build();
                case 1:
                    return builder.setCode(20000).setData("校内简讯")
                            .setDataList(getItemList(1)).build();
                case 2:
                    return builder.setCode(20000).setData("公示公告")
                            .setDataList(getItemList(2)).build();
                default:
                    return builder.setCode(40000).setMessage("未知错误").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return builder.setCode(40000).setMessage("获取学校数据失败").build();
        }
    }

    private List<Info> getItemList(int which) {
        Elements titleElements = elements.get(which).select("a");
        Elements fromElements = elements.get(which).select("span");
        if (titleElements.size() != fromElements.size()) return null;
        List<Info> infoList = new ArrayList<Info>();
        for (int j = 0; j < titleElements.size(); j++) {
            String from = fromElements.get(j).text().replaceAll(Jsoup.parse("&nbsp;").text(),
                    "-");
            String[] strings = from.split("-");
            String publish = strings[0];
            String time = strings[1];
            String title = titleElements.get(j).text();
            String link = baseUrl + titleElements.get(j).attr("href");
            infoList.add(new Info.Builder().setTime(time)
                    .setPublish(publish)
                    .setTitle(title)
                    .setLink(link).build());
        }
        return infoList;
    }


    public String getInfo(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, sdch")
                    .header("Accept-Language", "zh-CN,zh;q=0.8")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    .timeout(5000)
                    .header("Cookie", "fontsize=1; n%5Fident%5Fname%5Fs=3113001234; n%5Fident%5FSF%5Fs=0; ASPSESSIONIDCQCBRRBD=GFBMEBCCKAFJGJKFGEAKJOAA; fontsize=1; ASPSESSIONIDAQDAQSCA=HBGFFNOCOCDCJBEJCKBLJCEA; safedog-flow-item=95F42CB2962216948460C002A6D4FF68")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
                    .get();

            Elements elements = document.getElementsByClass("news_view_content");
            String content = elements.get(0).text().replaceAll(Jsoup.parse("&nbsp;").text(),
                    " ");

            return content;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
