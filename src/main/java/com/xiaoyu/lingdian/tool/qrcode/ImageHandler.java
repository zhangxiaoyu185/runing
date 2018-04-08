package com.xiaoyu.lingdian.tool.qrcode;

import org.apache.commons.codec.binary.Base64OutputStream;

import com.xiaoyu.lingdian.tool.StringUtil;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class ImageHandler {

	public enum ImageFormat {
		png, jpg, jpeg, gif
	}

	private static final String BASE64_PREFIX = "data:image/{0};base64,";

	public static String codeBase64(BufferedImage bi, ImageFormat format) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStream b64 = new Base64OutputStream(os);
		ImageIO.write(bi, format.name(), b64);
		String base64Code = os.toString("UTF-8");
		if (base64Code != null) {
			base64Code = base64Code.replace(System.lineSeparator(), "");
		}
		return StringUtil.formatMessage(BASE64_PREFIX, format) + base64Code;
	}

	public static void writeToStream(BufferedImage image, ImageFormat format, OutputStream stream) throws IOException {
		if (!ImageIO.write(image, format.name(), stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

}