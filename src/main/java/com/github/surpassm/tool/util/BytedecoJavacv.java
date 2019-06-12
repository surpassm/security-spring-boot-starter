package com.github.surpassm.tool.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author minchao
 * @version V1.0
 * Title:LatLonUtil
 * Description:视频处理工具类
 * Company: com.github.surpassm
 * Package com.github.surpassm.tool.util
 * date 2017年8月31日下午3:10:42
 */
public class BytedecoJavacv {

    protected static String ffmpegApp;

    public BytedecoJavacv(String ffmpegApp){
        this.ffmpegApp = ffmpegApp;
    }
    /****
     * 获取指定时间内的图片
     * @param videoFilename:视频路径
     * @param thumbFilename:图片保存路径
     * @param width:图片长
     * @param height:图片宽
     * @param hour:指定时
     * @param min:指定分
     * @param sec:指定秒
     * @throws IOException
     * @throws InterruptedException
     */
    public static void getThumbWindows(String videoFilename, String thumbFilename, int width, int height, int hour, int min, float sec) throws IOException,InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
                "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min
                + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height,
                "-an", thumbFilename);
        Process process = processBuilder.start();
        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null);
        process.waitFor();
        if(br != null){
            br.close();
        }
        if(isr != null) {
            isr.close();
        }
        if(stderr != null) {
            stderr.close();
        }
    }

    /**
     *
     * @param inFile
     * @param outFile
     * @param startTime
     * @return
     */
    public static boolean getThumbLinux(String inFile, String outFile,String startTime) {
        String command = "ffmpeg -i " + inFile
                + " -y -f image2 -ss "+startTime+" -t 00:00:01 -s 480x450 "
                + outFile;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 获取指定视频的帧并保存为图片至指定目录
     * @throws Exception
     */
    public static String randomGrabberFFmpegImage(String filePath, String targerFilePath, String targetFileName)
            throws Exception {
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();

        int ffLength = ff.getLengthInFrames();
        Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabImage();
            if ((i > 5) && (f.image != null)) {
                doExecuteFrame(f, targerFilePath, targetFileName);
                break;
            }
            i++;
        }
        String length ="" ;
        long lengthInTime = ff.getLengthInTime()/1000000;
        int hour = (int) (lengthInTime/3600);
        int minute = (int) (lengthInTime%3600)/60;
        int second = (int) (lengthInTime-hour*3600-minute*60);
        length = minute+":"+second;
        ff.stop();
        return length;
    }

    public static void doExecuteFrame(Frame f, String targerFilePath, String targetFileName) {
        if (null == f || null == f.image) {
            return;
        }

        Java2DFrameConverter converter = new Java2DFrameConverter();
        String imageMat = "jpg";
        String FileName = targerFilePath + File.separator + targetFileName;
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(FileName);
        try {
            ImageIO.write(bi, imageMat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> random(int baseNum, int length) {
        List<Integer> list = new ArrayList<>(length);
        while (list.size() < length) {
            Integer next = (int) (Math.random() * baseNum);
            if (list.contains(next)) {
                continue;
            }
            list.add(next);
        }
        Collections.sort(list);
        return list;
    }

    /**
     * 获取视频时长
     * @param source
     * @return
     */
    private String ReadVideoTime(File source) {
//        Encoder encoder = new Encoder();
//        String length = "";
//        try {
//            MultimediaInfo m = encoder.getInfo(source);
//            long ls = m.getDuration()/1000;
//            int hour = (int) (ls/3600);
//            int minute = (int) (ls%3600)/60;
//            int second = (int) (ls-hour*3600-minute*60);
//            length = hour+"'"+minute+"''"+second+"'''";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return length;
        return null;
    }

    /**
     * 获取视频大小
     * @param source
     * @return
     */
    private String ReadVideoSize(File source) {
        FileChannel fc= null;
        String size = "";
        try {
            @SuppressWarnings("resource")
            FileInputStream fis = new FileInputStream(source);
            fc= fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=fc){
                try{
                    fc.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return size;
    }
}
