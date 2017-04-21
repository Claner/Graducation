package Util;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Clanner on 2016/12/20.
 * 通用工具类
 */
public class GeneralUtils<T> {

    /**
     * 采用加密算法加密字符串数据
     *
     * @param str       需要加密的数据
     * @param algorithm 采用的加密算法
     * @return 字节数据
     */
    private static byte[] EncryptionStrBytes(String str, String algorithm) {
        // 加密之后所得字节数组
        byte[] bytes = null;
        try {
            // 获取MD5算法实例 得到一个md5的消息摘要
            MessageDigest md = MessageDigest.getInstance(algorithm);
            //添加要进行计算摘要的信息
            md.update(str.getBytes());
            //得到该摘要
            bytes = md.digest();
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(bytes);
            System.out.println("BASE64 " + base64);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("加密算法: " + algorithm + " 不存在: ");
        }
        return null == bytes ? null : bytes;
    }


    /**
     * 把字节数组转化成字符串返回
     *
     * @param bytes
     * @return
     */
    private static String BytesConvertToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String s = Integer.toHexString(0xff & aByte);
            if (s.length() == 1) {
                sb.append("0" + s);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    /**
     * 采用加密算法加密字符串数据
     *
     * @param str 需要加密的数据
     * @return 字节数据
     */
    public static String EnCode(String str) {
        // 加密之后所得字节数组
        byte[] bytes = EncryptionStrBytes(str, "MD5");
        return BytesConvertToHexString(bytes);
    }

    public static boolean isParamsEmpty(String... params) {
        for (String param : params) {
            if ("".equals(param)) return true;
        }
        return false;
    }

    public static boolean saveFile(Integer user_id, MultipartFile file, HttpServletRequest request, String path) {
        if (!file.isEmpty()) {
            String[] s = file.getOriginalFilename().split("\\.");
            //文件保存路径
            String filePath = request.getSession().getServletContext().getRealPath("/" + path + "/")
                    + "\\" + user_id + "\\" + s[0] + ".jpg";
            try {
                File f = new File(filePath);
                if (!f.isDirectory()) f.mkdirs();
                file.transferTo(f);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
