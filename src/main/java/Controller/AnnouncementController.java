package Controller;

import Dao.AnnouncementDao;
import Entity.AnnouncementEntity;
import Entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Clanner on 2017/3/19.
 */
@RestController
@RequestMapping("Announcement")
@CrossOrigin
public class AnnouncementController {

    @Autowired
    private AnnouncementDao announcementDao;

    @Autowired
    private Response response;

    /**
     * 获取公告
     */
    @RequestMapping(value = "/getAnnouncement", method = RequestMethod.POST)
    public Response getAnnouncement(@RequestParam("pageNo") int pageNo,
                                    @RequestParam("pageSize") int pageSize) {
        long size = announcementDao.getCount();
        List<AnnouncementEntity> list = announcementDao.getAnnouncement(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return response.successWithAll("获取数据成功",size,list);
        }
        return response.error("没有数据");
    }

    /**
     * 获取公告详情
     */
    @RequestMapping(value = "/getAnnouncementDetails",method = RequestMethod.POST)
    public Response getAnnouncementDetails(int id) {
        String content = announcementDao.getAnnouncementDetails(id);
        if (content != null) {
            return response.successWithData("获取公告详情成功",content);
        }
        return response.error("获取公告详情失败");
    }
}
