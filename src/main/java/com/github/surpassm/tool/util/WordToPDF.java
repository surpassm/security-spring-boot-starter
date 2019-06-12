package com.github.surpassm.tool.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author mc
 * Create date 2019/5/26 15:52
 * Version 1.0
 * Description OpenOffice 的安装路径自己定义。 代码中要使用对应路径调用启动服务
 *
 * OpenOffice下载地址: https://www.openoffice.org/download/
 */

public class WordToPDF {

	static Logger logger = LoggerFactory.getLogger(WordToPDF.class);


	/**
	 * 将word格式的文件转换为pdf格式
	 *
	 * @param wordFilePath word路径
	 * @param pdfFilePath  PDF路径
	 * @param openOfficeExePath "C:/Program Files (x86)/OpenOffice 4/program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
	 * @throws IOException  下面的写法自己修改编辑就可以
	 */
	public static void wordToPDF(String wordFilePath, String pdfFilePath,String openOfficeExePath) throws IOException {
		// 源文件目录
		File inputFile = new File(wordFilePath);
		if (!inputFile.exists()) {
			logger.info("源文件不存在！");
			return;
		}

		// 输出文件目录
		File outputFile = new File(pdfFilePath);
		File parentFile = outputFile.getParentFile();
		if (parentFile != null) {
			if (!parentFile.exists()) {
				parentFile.exists();
			}
		}

		// 调用openoffice服务线程
		Process p = Runtime.getRuntime().exec(openOfficeExePath);

		// 连接openoffice服务
		OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
		connection.connect();

		// 转换
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(inputFile, outputFile);

		// 关闭连接
		connection.disconnect();

		// 关闭进程
		p.destroy();
	}
}
