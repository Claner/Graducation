package Controller;

import Dao.AvatarDao;
import Entity.Response;
import Util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Clanner on 2017/4/9.
 */
@RestController
@RequestMapping("Avatar")
public class AvatarController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private Response response;

    @Autowired
    private AvatarDao avatarDao;

    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public Response uploadAvatar(HttpSession session,
                                 @RequestParam("avatar") MultipartFile file) {
        int user_id = (Integer) session.getAttribute("isLogin");
        if (GeneralUtils.saveFile(user_id, file, request, "Avatar")) {
            if (avatarDao.saveAvatar(user_id,file)){
                return response.success("上传头像成功");
            }else {
                return response.error("上传头像失败");
            }
        }else {
            return response.error("保存文件失败");
        }
    }

    @RequestMapping(value = "/getAvatarImage/{imagePath}", method = RequestMethod.GET)
    public void getAvatarImage(@PathVariable(value = "imagePath") String avatar,
                               HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("image/gif");
        String[] str = avatar.split("-");
        System.out.println("debug"+avatar);
        System.out.println("debug"+str[1]);
        getImage(httpServletResponse, str, "Avatar");
    }

    private void getImage(HttpServletResponse httpServletResponse, String[] str, String dir) {
        FileInputStream fis = null;
        try {
            OutputStream out = httpServletResponse.getOutputStream();
            File file = new File(request.getSession().getServletContext().getRealPath("/" + dir + "/")
                    + "\\" + str[0] + "\\" + str[1]+".jpg");
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
