package Controller;

import Dao.UserDao;
import Entity.Dog;
import Entity.Response;
import Entity.UserEntity;
import Util.Constant;
import Util.GeneralUtils;
import Util.RandomVerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Clanner on 2017/3/6.
 */
@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    private Response.Builder builder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Dog d;

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Dog test() {
        return new Dog("name", "18");
    }

    /**
     * 登陆
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestParam("account") String account,
                          @RequestParam("password") String password,
                          @RequestParam("verificationCode") String code,
                          HttpSession session) {
        if (checkVerificationCode(code, session)) {
            UserEntity userEntity = userDao.login(account, GeneralUtils.EnCode(password));
            if (userEntity != null) {
                session.setAttribute("isLogin", userEntity.getId());
                if ("2".equals(userEntity.getRole())) session.setAttribute("isAdmin", userEntity.getRole());
                if ("3".equals(userEntity.getRole())) session.setAttribute("isSuperAdmin", userEntity.getRole());
                return builder.setCode(20000).setMessage("登陆成功").setData(userEntity).build();
            } else {
                return builder.setCode(40000).setMessage("用户名或密码错误").build();
            }
        } else {
            return builder.setCode(40000).setMessage("验证码错误或已失效").build();
        }
    }

    /**
     * 验证验证码
     */
    private boolean checkVerificationCode(String verificationCode, HttpSession session) {
        String code = (String) session.getAttribute("imageCode");
        if (verificationCode.equalsIgnoreCase(code)) {
            session.removeAttribute("imageCode");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.GET)
    public void getVerificationCode(HttpServletResponse response,
                                    HttpServletRequest request) {
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);
        new RandomVerificationCode.Builder().build().getRandomImage(request, response, "imageCode");
    }

    /**
     * 修改密码
     */
    @RequestMapping("/modifyPassword")
    public Response modifyPassword(@RequestParam("pre_password") String pre_password,
                                   @RequestParam("new_password") String new_password,
                                   HttpSession session) {
        switch (userDao.modifyPassword((Integer) session.getAttribute("isLogin"), pre_password, new_password)) {
            case Constant.UPDATE_SUCCESS:
                return builder.setCode(20000).setMessage("修改密码成功，下次请用新密码登陆").build();
            case Constant.UPDATE_FAILURE:
                return builder.setCode(40000).setMessage("修改密码失败").build();
            case Constant.PASSWORD_WRONG:
                return builder.setCode(40000).setMessage("原密码错误").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Response logout(HttpSession session) {
        session.removeAttribute("isLogin");
        session.removeAttribute("isAdmin");
        return builder.setCode(20000).setMessage("已退出").build();
    }
}
