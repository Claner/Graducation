package Controller;

import Dao.BriefDao;
import Entity.BriefEntity;
import Entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Clanner on 2017/3/19.
 */
@RestController
@RequestMapping("Brief")
@CrossOrigin
public class BriefController {

    @Autowired
    private BriefDao briefDao;

    @Autowired
    private Response response;

    /**
     * 获取校内简讯
     */
    @RequestMapping(value = "/getBrief", method = RequestMethod.POST)
    public Response getBrief(@RequestParam("pageNo") int pageNo,
                             @RequestParam("pageSize") int pageSize) {
        long size = briefDao.getCount();
        List<BriefEntity> list = briefDao.getBrief(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return response.successWithAll("获取数据成功", size, list);
        }
        return response.error("没有数据");
    }

    /**
     * 获取校内简讯详情
     */
    @RequestMapping(value = "/getBriefDetails", method = RequestMethod.POST)
    public Response getBriefDetails(int id) {
        String content = briefDao.getBriefDetails(id);
        if (content != null) {
            return response.successWithData("获取校内简讯详情成功", content);
        } else {
            return response.error("获取校内简讯详情失败");
        }
    }
}
