package com.github.surpassm.tool.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

/**
 * auther: mc
 * 验证码生成帮助类，用来绘制随机字母
 * date: 2018/4/24 23:05
 * projectName: works
 * version: 1.0
 */
public class GraphicHelper {
    /**
     * 以字符串形式返回生成的验证码，同时输出一个图片
     * @param width 图片的宽度
     * @param height 图片的高度
     * @param imgType 图片的类型
     * @param output  图片的输出流(图片将输出到这个流中)
     * @return 返回所生成的验证码(字符串)
     */
    public static String create(final int width, final int height, final String imgType, OutputStream output) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = image.getGraphics();

        graphic.setColor(Color.getColor("F8F8F8"));
        graphic.fillRect(0, 0, width, height);

        Color[] colors = new Color[] { Color.BLUE, Color.GRAY, Color.GREEN, Color.RED, Color.BLACK, Color.ORANGE,
                Color.CYAN };
        // 在 "画板"上生成干扰线条 ( 50 是线条个数)
        for (int i = 0; i < 50; i++) {
            graphic.setColor(colors[random.nextInt(colors.length)]);
            final int x = random.nextInt(width);
            final int y = random.nextInt(height);
            final int w = random.nextInt(20);
            final int h = random.nextInt(20);
            final int signA = random.nextBoolean() ? 1 : -1;
            final int signB = random.nextBoolean() ? 1 : -1;
            graphic.drawLine(x, y, x + w * signA, y + h * signB);
        }

        // 在 "画板"上绘制字母
        graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        for (int i = 0; i < 6; i++) {
            final int temp = random.nextInt(26) + 97;
            String s = String.valueOf((char) temp);
            sb.append(s);
            graphic.setColor(colors[random.nextInt(colors.length)]);
            graphic.drawString(s, i * (width / 6), height - (height / 3));
        }
        graphic.dispose();
        try {
            ImageIO.write(image, imgType, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void drawBackground(Graphics g) {
        g.setColor(new Color(72, 75, 83));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        /*for (int i = 0; i < 120; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            g.setColor(new Color(red, green, blue));
            g.drawOval(x, y, 1, 0);
        }*/
    }

    public static void drawRands(Graphics g, char[] rands) {
        g.setColor(new Color(0xe0e0e0));
        g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
        g.drawString("" + rands[0], 1, 27);
        g.drawString("" + rands[1], 19, 25);
        g.drawString("" + rands[2], 39, 27);
        g.drawString("" + rands[3], 58, 26);
    }

    public static char[] generateCheckCode() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] rands = new char[4];
        for (int i = 0; i < 4; i++) {
            int rand = (int) (Math.random() * 62);
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }
}
