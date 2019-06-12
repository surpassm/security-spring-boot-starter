package com.github.surpassm.tool.compress;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import java.io.*;

/**
 * @author mc
 * Create date 2019/5/22 9:52
 * Version 1.0
 * Description BZip2解压缩工具类
 *
 * Apache Commons Compress解压缩 支持ar, cpio, Unix dump, tar, zip, gzip, XZ, Pack200, bzip2, 7z, arj, lzma, snappy, DEFLATE, lz4, Brotli and Z files格式
 *
 * 上面为BZip2解压缩代码, 若是想用其他格式的解压缩，可以将代码中的BZip2CompressorInputStream 和BZip2CompressorOutputStream相对应的替换就可以。
 *
 * .ar对应ArArchiveInputStream和ArArchiveOutputStream
 * .cpio对应CpioArchiveInputStream和CpioArchiveOutputStream
 * .dump对应DumpArchiveInputStream
 * .tar对应TarArchiveInputStream和TarArchiveOutputStream
 * .zip对应ZipArchiveInputStream和ZipArchiveOutputStream
 * .gzip对应GzipCompressorInputStream和GzipCompressorOutputStream
 * .xz对应XZCompressorInputStream和XZCompressorOutputStream
 * .pack200对应Pack200CompressorInputStream和Pack200CompressorOutputStream
 * .bzip2对应BZip2CompressorInputStream和BZip2CompressorOutputStream
 * .7z对应SevenZFile和SevenZOutputFile
 * .arj对应ArjArchiveInputStream
 * .lzma对应LZMACompressorInputStream和LZMACompressorOutputStream
 * .snappy对应SnappyCompressorInputStream和SnappyCompressorOutputStream
 * .deflate对应DeflateCompressorInputStream和DeflateCompressorOutputStream
 * .lz4对应BlockLZ4CompressorInputStream和BlockLZ4CompressorOutputStream
 * .brotli 对应BrotliCompressorInputStream
 * .z对应ZCompressorInputStream
 *
 */
public class BZip2Util {
	/**缓冲字节*/
	public static final int BUFFER = 1024;
	/**后缀名*/
	public static final String EXT = ".bz2";

	/**
	 * 数据压缩
	 * @param data 数据字节
	 * @return
	 * @throws IOException
	 */
	public static byte[] compress(byte[] data) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 压缩
		compress(bais, baos);

		byte[] output = baos.toByteArray();

		// 从缓冲区刷新数据
		baos.flush();
		// 关闭流
		baos.close();
		bais.close();

		return output;
	}

	/**
	 * 文件压缩
	 * @param file 文件
	 * @param delete 是否删除原文件
	 * @throws IOException
	 */
	public static void compress(File file, boolean delete) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

		compress(fis, fos);

		fos.flush();

		fos.close();
		fis.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 数据压缩
	 * @param is 输入流
	 * @param os 输出流
	 * @throws IOException
	 */
	private static void compress(InputStream is, OutputStream os) throws IOException {
		BZip2CompressorOutputStream bcos = new BZip2CompressorOutputStream(os);

		int count;
		byte data[] = new byte[BUFFER];

		while((count = is.read(data, 0, BUFFER)) != -1){
			bcos.write(data, 0, count);
		}

		bcos.finish();

		bcos.flush();
		bcos.close();
	}

	/**
	 * 文件压缩
	 * @param path 文件路径
	 * @param delete 是否删除原文件
	 * @throws IOException
	 */
	public static void compress(String path, boolean delete) throws IOException{
		File file = new File(path);
		compress(file, delete);
	}

	/**
	 * 数据解压缩
	 * @param data 数据
	 * @return
	 * @throws IOException
	 */
	public static byte[] deCompress(byte[] data) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 解压缩
		deCompress(bais, baos);

		data = baos.toByteArray();

		baos.flush();
		baos.close();
		bais.close();

		return data;
	}

	/**
	 * 文件解压缩
	 * @param file 文件
	 * @param delete 是否删除源文件
	 * @throws IOException
	 */
	public static void deCompress(File file, boolean delete) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT, ""));

		deCompress(fis, fos);

		fos.flush();
		fos.close();
		fis.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 解压缩
	 * @param is 输入流
	 * @param os 输出流
	 * @throws IOException
	 */
	private static void deCompress(InputStream is, OutputStream os) throws IOException {
		BZip2CompressorInputStream bcis = new BZip2CompressorInputStream(is);

		int count;
		byte data[] = new byte[BUFFER];

		while((count = bcis.read(data, 0, BUFFER)) != -1){
			os.write(data, 0, count);
		}

		bcis.close();
	}

	/**
	 * 文件解压缩
	 * @param path 文件路径
	 * @param delete 是否删除源文件
	 * @throws IOException
	 */
	public static void deCompress(String path, boolean delete) throws IOException{
		File file = new File(path);
		deCompress(file, delete);
	}
}
