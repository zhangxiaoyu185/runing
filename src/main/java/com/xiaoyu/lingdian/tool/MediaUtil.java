package com.xiaoyu.lingdian.tool;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaUtil {

	private static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");
	
	/**
	 * 视频转码
	 * 
	 * @param ffmpegPath 转码工具的存放路径
	 * @param upFilePath 用于指定要转换格式的文件,要截图的视频源文件
	 * @param codcFilePath 格式转换后的的文件保存路径
	 * @param mediaPicPath 截图保存路径
	 * @return
	 * @throws Exception
	 */
	public static boolean executeCodecs(String ffmpegPath, String upFilePath,
			String codcFilePath, String mediaPicPath) throws Exception {
		// 创建一个List集合来保存转换视频文件为flv格式的命令
		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // 添加转换工具路径
		convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		convert.add(upFilePath); // 添加要转换格式的视频文件的路径
		convert.add("-qscale"); // 指定转换的质量
		convert.add("6");
		convert.add("-ab"); // 设置音频码率
		convert.add("64");
		convert.add("-ac"); // 设置声道数
		convert.add("2");
		convert.add("-ar"); // 设置声音的采样频率
		convert.add("22050");
		convert.add("-r"); // 设置帧频
		convert.add("24");
		convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
		convert.add(codcFilePath);

		// 创建一个List集合来保存从视频中截取图片的命令
		List<String> cutpic = new ArrayList<String>();
		cutpic.add(ffmpegPath);
		cutpic.add("-i");
		cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
		cutpic.add("-y");
		cutpic.add("-f");
		cutpic.add("image2");
		cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
		cutpic.add("17"); // 添加起始时间为第17秒
		cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
		cutpic.add("0.001"); // 添加持续时间为1毫秒
		cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
		cutpic.add("640*480"); // 添加截取的图片大小
		cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		try {
			builder.command(convert);
			builder.redirectErrorStream(true);
			builder.start();

			builder.command(cutpic);
			builder.redirectErrorStream(true);
			// 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
			// 因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
			builder.start();
		} catch (Exception e) {
			mark = false;
			e.printStackTrace();
		}
		return mark;
	}

	/**
	 * 音频转码
	 * @return
	 */
	public static String convertAmr2Ilbc(String ffmpegPath, File amr, String path) {
		String savename = amr.getName().substring(0, amr.getName().lastIndexOf("."));
		List<String> cmd = new ArrayList<String>();
		cmd.add(ffmpegPath);
		cmd.add("-i");
		cmd.add(amr.getPath());
		cmd.add("-f");
		cmd.add("caf");
		cmd.add("-acodec");
		cmd.add("ilbc");
		cmd.add(path + savename + ".ilbc");
		try {
			ProcessBuilder builder = new ProcessBuilder(cmd);
			builder.command(cmd);
			builder.start();
			return savename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 音频转码
	 * @return
	 */
	public static String convertIlbc2Amr(String ffmpegPath, File ilbc, String path) {
		List<String> cmd = new ArrayList<String>();
		String savename = ilbc.getName().substring(0, ilbc.getName().lastIndexOf("."));
		cmd.add(ffmpegPath);
		cmd.add("-i");
		cmd.add(ilbc.getPath());
		cmd.add("-vn");
		cmd.add("-ac");
		cmd.add("1");
		cmd.add("-ab");
		cmd.add("4.75k");
		cmd.add("-ar");
		cmd.add("8000");
		cmd.add("-f");
		cmd.add("amr");
		cmd.add(path + savename + ".amr");
		try {
			ProcessBuilder builder = new ProcessBuilder(cmd);
			builder.command(cmd);
			builder.start();
			return savename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 音频转码
	 * @return
	 */
	public static String convertAmrOrIlbcToMp3(String ffmpegPath, File amrOrIlbc, String path) {
		String savename = amrOrIlbc.getName().substring(0, amrOrIlbc.getName().lastIndexOf("."));
		List<String> cmd = new ArrayList<String>();
		cmd.add(ffmpegPath);
		cmd.add("-i");
		cmd.add(amrOrIlbc.getPath());
		cmd.add("-f");
		cmd.add("mp3");
		cmd.add(path + savename + ".mp3");
		logger.info(ffmpegPath + ";" + amrOrIlbc.getPath() + ";" + path + savename + ".mp3");
		try {
			ProcessBuilder builder = new ProcessBuilder(cmd);
			builder.command(cmd);
			builder.start();
			return savename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void changeMp3(String sourceFile, String outFile) {
		logger.info(sourceFile + ";" + outFile);
		File source = new File(sourceFile);
		if (!source.exists()) {
			logger.info(sourceFile + "不存在");
			return;
		}
		File target = new File(outFile);
		if (target.exists()) {
			logger.info(target + "已存在");
			return;
		}
		
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();
		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);

		try {
			encoder.encode(source, target, attrs);
			source.delete();
		} catch (IllegalArgumentException e) {
			logger.info(e.getMessage());
		} catch (InputFormatException e) {
			logger.info(e.getMessage());
		} catch (EncoderException e) {
			logger.info(e.getMessage());
		}
	}

	public static void main(String[] args) {
		changeMp3("D://oSICX9xbU-VMQZCsm7IpQeyY-hiQSPnntXPwIuHZgHxWRHpLBwgjCSf0IPOrZaLR.amr", "D://111.mp3");
	}
}