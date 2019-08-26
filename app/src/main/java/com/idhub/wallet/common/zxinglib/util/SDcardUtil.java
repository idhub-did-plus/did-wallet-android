package com.idhub.wallet.common.zxinglib.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SDcardUtil {

	private static File file = null;

	public static File getFile() {
		return file;
	}

	/**
	 * 判断文件是否已经存在
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String dir, String fileName) {
		String filePath = getFilePath(dir, fileName);
		file = new File(filePath);
		return file.exists();
	}

	public static boolean isFileExist(String filePath) {
		file = new File(filePath);
		return file.exists();
	}

	/**
	 * 获取路径下的文件集合
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static File[] getFiles(String dir, String fileName) {
		if (isFileExist(dir, fileName))
			return file.listFiles();
		else
			return null;
	}

	/**
	 * 获取路径下的文件集合
	 * 
	 * @param filePath
	 * @return
	 */
	public static File[] getFiles(String filePath) {
		if (isFileExist(filePath))
			return file.listFiles();
		else
			return null;
	}

	/**
	 * 删除该文件夹及该目录下的所以文件
	 * 
	 * @param file
	 */
	public static void deleteDir(File file) {
		File[] files = file.listFiles();
		if (files.length == 0)
			file.delete();
		else {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory())
					deleteDir(files[i]);
				else
					files[i].delete();
			}
			file.delete();
		}
	}

	/**
	 * 获取目录下的所以视频
	 * 
	 * @param files
	 * @return
	 */
	public static List<File> getImageVideoFiles(File files[]) {
		List<File> list = new ArrayList<File>();
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					String filepath = files[i].getAbsolutePath().toLowerCase();
					if (filepath.endsWith("jpg") || filepath.endsWith("jpeg")
							|| filepath.endsWith("gif")
							|| filepath.endsWith("bmp")
							|| filepath.endsWith("png")
							|| filepath.endsWith("3gp")
							|| filepath.endsWith("asf")
							|| filepath.endsWith("avi")
							|| filepath.endsWith("m4u")
							|| filepath.endsWith("m4v")
							|| filepath.endsWith("mov")
							|| filepath.endsWith("mp4")
							|| filepath.endsWith("mpe")
							|| filepath.endsWith("mpeg")
							|| filepath.endsWith("mpg")
							|| filepath.endsWith("mpg4")
							|| filepath.endsWith("rmvb")
							|| filepath.endsWith("flv")
							|| filepath.endsWith("f4v")
							|| filepath.endsWith("mkv"))
						list.add(files[i]);
				}
			}
		if (list.size() == 0)
			return null;
		return list;
	}

	/**
	 * 获取目录下的所以图片
	 * 
	 * @param files
	 * @return
	 */
	public static List<File> getImgFiles(File files[]) {
		List<File> list = new ArrayList<File>();
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					String filepath = files[i].getAbsolutePath().toLowerCase();
					if (filepath.endsWith("jpg") || filepath.endsWith("jpeg")
							|| filepath.endsWith("gif")
							|| filepath.endsWith("bmp")
							|| filepath.endsWith("png"))
						list.add(files[i]);
				}
			}
		if (list.size() == 0)
			return null;
		return list;
	}

	/**
	 * 获取目录下的所有png图片
	 * 
	 * @param files
	 * @return
	 */
	public static List<File> getImgPngFiles(File files[]) {
		List<File> list = new ArrayList<File>();
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					String filepath = files[i].getAbsolutePath().toLowerCase();
					if (filepath.endsWith("png"))
						list.add(files[i]);
				}
			}
		if (list.size() == 0)
			return null;
		return list;
	}

	public static String getImgPath(File files[]) {
		String filepath = "";
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				filepath = files[i].getAbsolutePath().toLowerCase();
				if (filepath.endsWith("jpg") || filepath.endsWith("jpeg")
						|| filepath.endsWith("gif") || filepath.endsWith("bmp")
						|| filepath.endsWith("png"))
					return filepath;
				else
					return "";
			}
		}
		return filepath;
	}

	/**
	 * 获取目录下的所以视频
	 * 
	 * @param files
	 * @return
	 */
	public static List<File> getVideoFiles(File files[]) {
		List<File> list = new ArrayList<File>();
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					String filepath = files[i].getAbsolutePath().toLowerCase();
					if (filepath.endsWith("3gp") || filepath.endsWith("asf")
							|| filepath.endsWith("avi")
							|| filepath.endsWith("m4u")
							|| filepath.endsWith("m4v")
							|| filepath.endsWith("mov")
							|| filepath.endsWith("mp4")
							|| filepath.endsWith("mpe")
							|| filepath.endsWith("mpeg")
							|| filepath.endsWith("mpg")
							|| filepath.endsWith("mpg4")
							|| filepath.endsWith("rmvb")
							|| filepath.endsWith("flv")
							|| filepath.endsWith("f4v")
							|| filepath.endsWith("mkv"))
						list.add(files[i]);
				}
			}
		if (list.size() == 0)
			return null;
		return list;
	}

	/**
	 * 获取目录下的所有音频
	 * 
	 * @param files
	 * @return
	 */
	public static List<File> getAudioFiles(File files[]) {
		List<File> list = new ArrayList<File>();
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					String filepath = files[i].getAbsolutePath().toLowerCase();
					if (filepath.endsWith("mp3") || filepath.endsWith("mpeg-4"))
						list.add(files[i]);
				}
			}
		if (list.size() == 0)
			return null;
		return list;
	}

	/**
	 * 获取文件的路径
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static String getFilePath(String dir, String fileName) {
		String sdcardPath = getSdcardPath();
		if (dir == null || dir.equals("")) {
			return sdcardPath + fileName;
		} else {
			if (dir.substring(0).equals("/"))
				dir = dir.substring(1, dir.length());
			if (dir.substring(dir.length() - 1).equals("/"))
				dir = dir.substring(0, dir.length() - 1);
			return sdcardPath + dir + File.separator + fileName;
		}
	}

	/**
	 * 判断SD卡是否可用
	 * 
	 * @return
	 */
	public static boolean isSdCardExist() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	/**
	 * 得到扩展存储卡所在的路径/目录
	 * 
	 * @return
	 */
	public static String getSdcardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 获取文件最后的修改时间
	 * 
	 * @param time
	 *            时间
	 * @param format
	 *            格式：yyyy/mm/dd  hh:mm
	 * @return
	 */
	public static String getFileModifiedTime(long time, String format) {
		Date d = new Date(time);
		Format simpleFormat = new SimpleDateFormat(format);
		return simpleFormat.format(d);
	}

	/**
	 * 获取文件夹下文件的个数
	 * 
	 * @param file
	 * @return
	 */
	public static String getDirSize(File file) {
		return file.listFiles().length + "个文件";
	}

	/**
	 * 获取文件大小 单位：GB/MB/KB/Byte
	 * 
	 * @param size
	 *            Byte
	 * @return
	 */
	public static String getFileSize(long size) {
		if (size == 0)
			return "";
		long bt = size;
		long kb = bt / 1024;
		long mb = kb / 1024;
		long gb = mb / 1024;
		long t = gb / 1024;
		if (bt > 1024)
			if (kb > 1024)
				if (mb > 1024)
					if (gb > 1024)
						return String.valueOf(t) + "T";
					else
						return String.valueOf(gb) + "GB";
				else
					return String.valueOf(mb) + "MB";
			else
				return String.valueOf(kb) + "KB";
		else
			return String.valueOf(bt) + "Byte";
	}

	/**
	 * 从SD卡中读取文件信息
	 * 
	 * @param context
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static String getFileFromSdcard(Context context, String dir,
                                           String fileName) {
		int len = 0;
		byte data[] = new byte[1024];
		FileInputStream inputStream = null;
		// 缓存流，和磁盘无关，不需要关闭
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (!isSdCardExist())
			return "";
		if (!isFileExist(dir, fileName))
			return "";
		try {
			inputStream = new FileInputStream(file);
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new String(outputStream.toByteArray());
	}

	public static int getFileType(String filePath) {
		String path = filePath.toLowerCase();
		if (path.endsWith("3gp") || path.endsWith("asf")
				|| path.endsWith("avi") || path.endsWith("m4u")
				|| path.endsWith("m4v") || path.endsWith("mov")
				|| path.endsWith("mp4") || path.endsWith("mpe")
				|| path.endsWith("mpeg") || path.endsWith("mpg")
				|| path.endsWith("mpg4") || path.endsWith("rmvb")
				|| path.endsWith("flv") || path.endsWith("f4v")
				|| path.endsWith("mkv"))
			return 2;
		else if (path.endsWith("m3u") || path.endsWith("m4a")
				|| path.endsWith("m4b") || path.endsWith("m4p")
				|| path.endsWith("mp2") || path.endsWith("mp3")
				|| path.endsWith("ogg") || path.endsWith("mpga")
				|| path.endsWith("wma") || path.endsWith("wmv")
				|| path.endsWith("wav"))
			return 5;
		else if (path.endsWith("jpg") || path.endsWith("jpeg")
				|| path.endsWith("gif") || path.endsWith("bmp")
				|| path.endsWith("png"))
			return 1;
		else if (path.endsWith("txt") || path.endsWith("doc")
				|| path.endsWith("docx") || path.endsWith("htm")
				|| path.endsWith("html") || path.endsWith("pdf"))
			return 4;
		else
			return 0;
	}

	public static int getIcon(Context context, String filePath) {
		String path = filePath.toLowerCase();
		if (path.endsWith("apk"))
			return ResourceUtil.getDrawableId(context,"apk_file_icon");
		else if (path.endsWith("txt"))
			return ResourceUtil.getDrawableId(context,"txt_icon");
		else if (path.endsWith("3gp") || path.endsWith("asf")
				|| path.endsWith("avi") || path.endsWith("m4u")
				|| path.endsWith("m4v") || path.endsWith("mov")
				|| path.endsWith("mp4") || path.endsWith("mpe")
				|| path.endsWith("mpeg") || path.endsWith("mpg")
				|| path.endsWith("mpg4") || path.endsWith("rmvb")
				|| path.endsWith("flv") || path.endsWith("f4v")
				|| path.endsWith("mkv"))
			return ResourceUtil.getDrawableId(context,"vedio_icon");
		else if (path.endsWith("m3u") || path.endsWith("m4a")
				|| path.endsWith("m4b") || path.endsWith("m4p")
				|| path.endsWith("mp2") || path.endsWith("mp3")
				|| path.endsWith("ogg") || path.endsWith("mpga")
				|| path.endsWith("wma") || path.endsWith("wmv")
				|| path.endsWith("wav"))
			return ResourceUtil.getDrawableId(context,"audio_icon");
		else if (path.endsWith("jpg") || path.endsWith("jpeg")
				|| path.endsWith("gif") || path.endsWith("bmp")
				|| path.endsWith("png"))
			return ResourceUtil.getDrawableId(context,"picture_icon");
		else if (path.endsWith("c"))
			return ResourceUtil.getDrawableId(context,"c_icon");
		else if (path.endsWith("doc") || path.endsWith("docx"))
			return ResourceUtil.getDrawableId(context,"word_icon");
		else if (path.endsWith("xls") || path.endsWith("xlsx"))
			return ResourceUtil.getDrawableId(context,"xls_icon");
		else if (path.endsWith("exe"))
			return ResourceUtil.getDrawableId(context,"exe_icon");
		else if (path.endsWith("htm") || path.endsWith("html"))
			return ResourceUtil.getDrawableId(context,"html_icon");
		else if (path.endsWith("java"))
			return ResourceUtil.getDrawableId(context,"java_icon");
		else if (path.endsWith("pdf"))
			return ResourceUtil.getDrawableId(context,"pdf_icon");
		else if (path.endsWith("pps") || path.endsWith("ppt")
				|| path.endsWith("pptx"))
			return ResourceUtil.getDrawableId(context,"ppt_icon");
		else if (path.endsWith("rtf"))
			return ResourceUtil.getDrawableId(context,"rtf_icon");
		else if (path.endsWith("rar") || path.endsWith("rar5"))
			return ResourceUtil.getDrawableId(context,"compressed_icon_rar");
		else if (path.endsWith("7z"))
			return ResourceUtil.getDrawableId(context,"compressed_icon_7");
		else if (path.endsWith("zip") || path.endsWith("gz")
				|| path.endsWith("bz2") || path.endsWith("tar")
				|| path.endsWith("xz") || path.endsWith("lzh")
				|| path.endsWith("wim"))
			return ResourceUtil.getDrawableId(context,"compressed_icon");
		else if (path.endsWith("ai"))
			return ResourceUtil.getDrawableId(context,"ai_icon");
		else if (path.endsWith("chm"))
			return ResourceUtil.getDrawableId(context,"chm_file_icon");
		else if (path.endsWith("epub"))
			return ResourceUtil.getDrawableId(context,"epub_icon");
		else if (path.endsWith("fla"))
			return ResourceUtil.getDrawableId(context,"fla_icon");
		else if (path.endsWith("ipa"))
			return ResourceUtil.getDrawableId(context,"ipa_icon");
		else if (path.endsWith("php"))
			return ResourceUtil.getDrawableId(context,"php_icon");
		else if (path.endsWith("psd"))
			return ResourceUtil.getDrawableId(context,"psd_picture_icon");
		else if (path.endsWith("xap"))
			return ResourceUtil.getDrawableId(context,"xap_icon");
		else if (path.endsWith("swf"))
			return ResourceUtil.getDrawableId(context,"swf_icon");
		else
			return ResourceUtil.getDrawableId(context,"unknow_file_icon");
	}

	/**
	 * 从SD卡中读取文件信息
	 * 
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static String getFileFromSdcard(Context context, String filePath) {
		String fileContent = "";
		if (!isSdCardExist())
			return "";
		if (!isFileExist(filePath))
			return "";
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), Charset.forName("UTF-8"));
			BufferedReader reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent += line;
			}
			read.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	/**
	 * 保存文件到SD卡
	 * 
	 * @param context
	 * @param dir
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static boolean saveToSdcard(Context context, String dir,
                                       String fileName, String content) {
		boolean flag = false;
		FileOutputStream fileOutputStream = null;
		if (!isSdCardExist())
			return false;
		if (!isFileExist(dir, fileName))
			return false;
		try {
			fileOutputStream = new FileOutputStream(file);
			// 把当前字符串转成一个字节数组
			fileOutputStream.write(content.getBytes());
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	public static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	/**
	 * MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组：
	 */
	public static final String[][] MIME_MapTable = {
			// {后缀名，MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };
}
