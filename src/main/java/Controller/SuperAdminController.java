package Controller;

import Dao.AdminDao;
import Dao.SuperAdminDao;
import Dao.UserDao;
import Entity.AcademyEntity;
import Entity.AdminEntity;
import Entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Clanner on 2017/3/7.
 */
@RestController
@RequestMapping("SuperAdmin")
public class SuperAdminController{

    @Autowired
    private Response.Builder builder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private SuperAdminDao superAdminDao;

    /**
     * 添加管理员
     */
    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public Response addAdmin(@RequestParam("account") String account,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("sex") String sex,
                             @RequestParam("phone") String phone,
                             @RequestParam("address") String address) {
        if (userDao.isUserExist(account)) return builder.setCode(40000).setMessage("管理员已存在").build();
        int id = userDao.saveAdmin(account, password);
        if (id != 0) {
            if (adminDao.saveAdmin(id, name, sex, phone, address)) {
                return builder.setCode(20000).setMessage("添加管理员成功").build();
            } else {
                return builder.setCode(40000).setMessage("添加管理员成功但初始化信息失败").build();
            }
        } else {
            return builder.setCode(40000).setMessage("添加管理员失败").build();
        }
    }

    /**
     * 删除管理员
     */
    @RequestMapping(value = "/deleteAdmin", method = RequestMethod.POST)
    public Response deleteAdmin(@RequestParam("id") int id) {
        if (superAdminDao.deleteAdmin(id)) {
            return builder.setCode(20000).setMessage("删除管理员成功").build();
        } else {
            return builder.setCode(40000).setMessage("删除管理员失败").build();
        }
    }

    /**
     * 获取所有管理员
     */
    @RequestMapping(value = "/getAllAdmin", method = RequestMethod.POST)
    public Response getAllAdmin(@RequestParam("pageNo") int pageNo,
                                @RequestParam("pageSize") int pageSize) {
        List<AdminEntity> list = adminDao.getAllAdmin(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return builder.setCode(20000).setMessage("获取数据成功").setDataList(list).build();
        } else {
            return builder.setCode(40000).setMessage("没有数据").build();
        }
    }
}
