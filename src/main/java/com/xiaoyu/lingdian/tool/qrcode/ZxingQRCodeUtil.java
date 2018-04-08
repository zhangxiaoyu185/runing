package com.xiaoyu.lingdian.tool.qrcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class ZxingQRCodeUtil {

	public static void main(String[] args) {
		try {
			createQrcodeImg("123", "D://zxing.png", 150);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(parseQrcodeImg("D://zxing.png"));
	}

	/**
	 * 生成图像
	 * 
	 * @param content
	 * @param filePathName
	 * @param widthHeight
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void createQrcodeImg(String content, String filePathName, int widthHeight) throws WriterException, IOException {
		String format = "png";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, widthHeight, widthHeight, hints);// 生成矩阵
		Path path = FileSystems.getDefault().getPath(filePathName);
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
	}

	/**
	 * 解析图像
	 */
	public static String parseQrcodeImg(String filePath) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
			return result.getText();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 生成带logo的二维码图片
	 * 
	 * @param content
	 * @param filePathName
	 * @param logoPic
	 * @param logoConfig logo配置
	 */
	public void addLogo_QRCode(String content, String filePathName, int widthHeight, String logoPic, LogoConfig logoConfig) {
		try {
			createQrcodeImg(content, filePathName, widthHeight);
			
			File imgFile = new File(filePathName);
			BufferedImage image = ImageIO.read(imgFile);
			Graphics2D g = image.createGraphics();

			BufferedImage logo = ImageIO.read(new File(logoPic));
			int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();

			// 计算图片放置位置
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - logo.getHeight()) / 2;

			// 开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);

			g.dispose();

			ImageIO.write(image, "jpeg", imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class LogoConfig {

		/**
		 * logo边框宽度
		 */
		private int border;
		
		/**
		 * logo边框颜色
		 */
		private Color borderColor;
		
		/**
		 * logo大小默认为照片的几分之一
		 */
		private int logoPart;

		public LogoConfig() {
			this(Color.WHITE, 5, 2);
		}

		public LogoConfig(Color borderColor, int logoPart, int border) {
			this.borderColor = borderColor;
			this.logoPart = logoPart;
			this.border = border;
		}

		public int getBorder() {
			return border;
		}

		public void setBorder(int border) {
			this.border = border;
		}

		public Color getBorderColor() {
			return borderColor;
		}

		public void setBorderColor(Color borderColor) {
			this.borderColor = borderColor;
		}

		public int getLogoPart() {
			return logoPart;
		}

		public void setLogoPart(int logoPart) {
			this.logoPart = logoPart;
		}
	}

}