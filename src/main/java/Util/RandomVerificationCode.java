package Util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * Created by Clanner on 2017/2/20.
 * 验证码生成类
 */
public class RandomVerificationCode {

    private StringBuffer stringBuffer = new StringBuffer();

    private RandomVerificationCode() {
    }

    private final Random random = new Random();
    private String randomString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串

    private int width;//图片宽
    private int height;//图片高
    private int lineNum;//干扰线数量
    private int codeNum;//验证码长度
    private int textSize;//字号

    /**
     * 获得字体
     */
    private Font getFont() {
        //字体，风格，字号
        return new Font("Clanner", Font.CENTER_BASELINE, textSize);
    }

    /**
     * 获得颜色
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 绘制字符串
     */
    private String drawString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),
                random.nextInt(111), random.nextInt(121)));
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(randomString, 13 * i, 16);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.setColor(new Color(random.nextInt(101),
                random.nextInt(111), random.nextInt(121)));
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 生成随机图片
     */
    public void getRandomImage(HttpServletRequest request, HttpServletResponse response, String key) {

        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= lineNum; i++) {
            drawLine(g);
        }
        // 绘制随机字符
        for (int i = 0; i < codeNum; i++) {
            stringBuffer.append(drawString
                    (g, String.valueOf
                            (this.randomString.charAt(random.nextInt(this.randomString.length()))), i));
        }
        String randomString = stringBuffer.toString();
        request.getSession().setAttribute(key, randomString);
        System.out.println("验证码是"+randomString);

        g.dispose();
        try {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            ImageIO.write(image, "png", tmp);
            tmp.close();
            Integer contentLength = tmp.size();
            response.setHeader("content-length", contentLength + "");
            response.getOutputStream().write(tmp.toByteArray());// 将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static class Builder {
        private int width = 80;//图片宽
        private int height = 26;//图片高
        private int lineNum = 40;//干扰线数量
        private int codeNum = 4;//验证码长度
        private int textSize = 18;//字号

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setLineNum(int lineNum) {
            this.lineNum = lineNum;
            return this;
        }

        public Builder setCodeNum(int codeNum) {
            this.codeNum = codeNum;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public RandomVerificationCode build() {
            RandomVerificationCode randomVerificationCode = new RandomVerificationCode();
            randomVerificationCode.width = width;
            randomVerificationCode.height = height;
            randomVerificationCode.lineNum = lineNum;
            randomVerificationCode.codeNum = codeNum;
            randomVerificationCode.textSize = textSize;
            return randomVerificationCode;
        }
    }
}
