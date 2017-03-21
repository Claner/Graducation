package Controller;

import Dao.NoticeDao;
import Entity.NoticeEntity;
import Entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Clanner on 2017/3/19.
 */
@RestController
@RequestMapping("Notice")
public class NoticeController {

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private Response.Builder builder;

    /**
     * 获取校内通知
     */
    @RequestMapping(value = "/getNotice", method = RequestMethod.POST)
    public Response getNotice(@RequestParam("pageNo") int pageNo,
                              @RequestParam("pageSize") int pageSize) {
        List<NoticeEntity> list = noticeDao.getNotice(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return builder.setCode(20000).setMessage("获取数据成功").setDataList(list).build();
        } else {
            return builder.setCode(40000).setMessage("没有数据").build();
        }
    }

    /**
     * 获取校内通知详情
     */
    @RequestMapping(value = "/getNoticeDetails", method = RequestMethod.POST)
    public Response getNoticeDetails(@RequestParam("id") int id) {
        String content = noticeDao.getNoticeDetails(id);
        if (content == null) return builder.setCode(40000).setMessage("获取文章详情失败").build();
        return builder.setCode(20000).setMessage("获取文章详成功").setData(content).build();
    }
}
