package com.github.surpassm.tool.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author mc
 * Create date 2019/5/22 9:34
 * Version 1.0
 * Description
 */
public class GzipUtil {
	/**
	 * utf-8编码
	 */
	public static final String GZIP_ENCODE_UTF_8 = "UTF-8";
	/**
	 * iso-8859-1编码
	 */
	public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";


	/**
	 * "utf-8"编码压缩
	 *
	 * @param string 要压缩的字符串
	 * @return 字节数组
	 */
	public static byte[] compress(String string) {
		return compress(string, GZIP_ENCODE_UTF_8);
	}

	/**
	 * 指定编码压缩
	 *
	 * @param string   要压缩的字符串
	 * @param encoding 编码
	 * @return 字节数组
	 */
	public static byte[] compress(String string, String encoding) {
		// 判断是否为空
		if (null == string || string.length() == 0) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzipOutputStream;
		try {
			gzipOutputStream = new GZIPOutputStream(baos);
			gzipOutputStream.write(string.getBytes(encoding));
			gzipOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	/**
	 * 解压为字节数组
	 *
	 * @param bytes 要解压的字节数组
	 * @return 字节数组
	 */
	public static byte[] uncompress(byte[] bytes) {
		if (null == bytes || bytes.length == 0) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		GZIPInputStream unGzip;
		try {
			unGzip = new GZIPInputStream(bais);
			byte[] buffer = new byte[256];
			int n;
			while ((n = unGzip.read(buffer)) >= 0) {
				baos.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	/**
	 * "utf-8"编码解压
	 *
	 * @param bytes 要解压的字节数组
	 * @return 字节数组
	 */
	public static String uncompressToString(byte[] bytes) {
		return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
	}

	/**
	 * 指定编码解压
	 *
	 * @param encoding   要压缩的字符串
	 * @param encoding 编码
	 * @return 字节数组
	 */
	public static String uncompressToString(byte[] bytes, String encoding) {
		if (null == bytes || bytes.length == 0) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		GZIPInputStream unGzip;
		try {
			unGzip = new GZIPInputStream(bais);
			byte[] buffer = new byte[256];
			int n;
			while ((n = unGzip.read(buffer)) >= 0) {
				baos.write(buffer, 0, n);
			}
			return baos.toString(encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	public static void main(String[] args) {
//		String string = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//		System.out.println("字符串长度: " + string.length());
//		System.out.println("压缩后: " + GzipUtil.compress(string).length);
//		System.out.println("解压后: " + GzipUtil.uncompress(GzipUtil.compress(string)).length);
//		System.out.println("解压后字符串长度: " + GzipUtil.uncompressToString(GzipUtil.compress(string)).length());
//	}
}
