package com.xiaoyu.lingdian.tool.qrcode;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public final class BarcodeUtil extends ImageHandler{

	private BarcodeUtil() {}

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static void generate(
			String content,
			int width,
			int height,
			ImageFormat format,
			OutputStream out) throws WriterException, IOException {
		BufferedImage image = toBufferedImage(content, width, height);
		writeToStream(image, format, out);
	}

	public static String generateBase64(String content,
			int width,
			int height,
			ImageFormat format) throws WriterException, IOException {
		BufferedImage bi = toBufferedImage(content, width, height);
		return codeBase64(bi, format);
	}

	public static BufferedImage toBufferedImage(String content, int width, int height) throws WriterException {
		Map<EncodeHintType, Object> hints = new HashMap<>();

		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix matrix = new MultiFormatWriter().encode(
				content, BarcodeFormat.CODE_128, width, height, hints);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < matrix.getWidth(); x++) {
			for (int y = 0; y < matrix.getHeight(); y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}

		return image;
	}

}