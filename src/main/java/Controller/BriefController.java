package Controller;

import Dao.BriefDao;
import Entity.BriefEntity;
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
@RequestMapping("Brief")
public class BriefController {

    @Autowired
    private BriefDao briefDao;

    @Autowired
    private Response.Builder builder;

    /**
     * 获取校内简讯
     */
    @RequestMapping(value = "/getBrief", method = RequestMethod.POST)
    public Response getBrief(@RequestParam("pageNo") int pageNo,
                             @RequestParam("pageSize") int pageSize) {
        List<BriefEntity> list = briefDao.getBrief(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return builder.setCode(20000).setMessage("获取数据成功").setDataList(list).build();
        }
        return builder.setCode(40000).setMessage("没有数据").build();
    }

    /**
     * 获取校内简讯详情
     */
    @RequestMapping(value = "/getBriefDetails", method = RequestMethod.POST)
    public Response getBriefDetails(int id) {
        String content = briefDao.getBriefDetails(id);
        if (content != null) {
            return builder.setCode(20000).setMessage("获取校内简讯详情成功").setData(content).build();
        } else {
            return builder.setCode(40000).setMessage("获取校内简讯详情失败").build();
        }
    }
}
