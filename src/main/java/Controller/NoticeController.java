package Controller;

import Dao.NoticeDao;
import Entity.NoticeEntity;
import Entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Clanner on 2017/3/19.
 */
@RestController
@RequestMapping("Notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private Response response;

    /**
     * 获取校内通知
     */
    @RequestMapping(value = "/getNotice", method = RequestMethod.POST)
    public Response getNotice(@RequestParam("pageNo") int pageNo,
                              @RequestParam("pageSize") int pageSize) {
        long size = noticeDao.getCount();
        List<NoticeEntity> list = noticeDao.getNotice(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return response.successWithAll("获取数据成功", size, list);
        } else {
            return response.error("没有数据");
        }
    }

    /**
     * 获取校内通知详情
     */
    @RequestMapping(value = "/getNoticeDetails", method = RequestMethod.POST)
    public Response getNoticeDetails(@RequestParam("id") int id) {
        String content = noticeDao.getNoticeDetails(id);
        if (content == null)
            return response.error("获取文章详情失败");
        return response.successWithData("获取文章详成功", content);
    }
}
