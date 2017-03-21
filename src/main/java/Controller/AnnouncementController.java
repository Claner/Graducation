package Controller;

import Dao.AnnouncementDao;
import Entity.AnnouncementEntity;
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
@RequestMapping("Announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementDao announcementDao;
    @Autowired
    private Response.Builder builder;

    /**
     * 获取公告
     */
    @RequestMapping(value = "/getAnnouncement", method = RequestMethod.POST)
    public Response getAnnouncement(@RequestParam("pageNo") int pageNo,
                                    @RequestParam("pageSize") int pageSize) {
        List<AnnouncementEntity> list = announcementDao.getAnnouncement(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return builder.setCode(20000).setMessage("获取数据成功").setDataList(list).build();
        }
        return builder.setCode(40000).setMessage("没有数据").build();
    }

    /**
     * 获取公告详情
     */
    @RequestMapping(value = "/getAnnouncementDetails",method = RequestMethod.POST)
    public Response getAnnouncementDetails(int id) {
        String content = announcementDao.getAnnouncementDetails(id);
        if (content != null) {
            return builder.setCode(20000).setMessage("获取公告详情成功").setData(content).build();
        }
        return builder.setCode(40000).setMessage("获取公告详情失败").build();
    }
}
