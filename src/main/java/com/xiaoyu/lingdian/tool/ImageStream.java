package com.xiaoyu.lingdian.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class ImageStream {

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * 
	 * @param imgFilePath
	 * @return
	 */
	public static String GetImageStr(String imgFilePath) {
		byte[] data = null;
		InputStream in = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 保存网页图片到本地
	 * @param destUrl
	 * @param fileName
	 */
	public static void saveToFile(String destUrl, String fileName) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(fileName);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
		} catch (IOException e) {
		} catch (ClassCastException e) {
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
	}
	
    private static String PNG = "png";  
    private static String JPG = "jpg"; 
  
    /** 
     * load image 
     * */  
    public static BufferedImage loadImage(String filePath) {  
        try {  
            return ImageIO.read(new File(filePath));  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * simple save use ImageIO.write 
     */  
    public static void saveImage(BufferedImage image, String format,  
            String filePath) {  
        try {  
            ImageIO.write(image, format, new File(filePath));  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * convert to byte array. 
     * */  
    public static byte[] convert2bytes(BufferedImage image, String format) {  
        try {  
            ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);  
            ImageIO.write(image, format, bout);  
            byte[] data = bout.toByteArray();  
            return data;  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * create new buffer image and save using png format. 
     * */  
    public static void savePngImage(BufferedImage image, String format,  
            String filePath) {  
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image  
                .getHeight(), BufferedImage.TYPE_3BYTE_BGR);  
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);  
        saveImage(bufferedImage, PNG, filePath);  
    }  
  
    /** 
     * create new buffer image and save using jpg format. 
     * */  
    public static void saveJpgImage(BufferedImage image, String format,  
            String filePath) {  
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image  
                .getHeight(), BufferedImage.TYPE_3BYTE_BGR);  
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);  
        saveImage(bufferedImage, JPG, filePath);  
    }  
  
    /** 
     * save jpg with specified quality. 为了图片质量，quality的值不能太低,0-1
     * */  
    public static void saveImageUsingJPGWithQuality(BufferedImage image,  
            String filePath, float quality) throws Exception {  
  
        BufferedImage newBufferedImage = new BufferedImage(image.getWidth(),  
                image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);  
        newBufferedImage.getGraphics().drawImage(image, 0, 0, null);  
  
        Iterator<ImageWriter> iter = ImageIO  
                .getImageWritersByFormatName("jpeg");  
  
        ImageWriter imageWriter = iter.next();  
        ImageWriteParam iwp = imageWriter.getDefaultWriteParam();  
  
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);  
        iwp.setCompressionQuality(quality);  
  
        File file = new File(filePath);  
        FileImageOutputStream fileImageOutput = new FileImageOutputStream(file);  
        imageWriter.setOutput(fileImageOutput);  
        IIOImage iio_image = new IIOImage(newBufferedImage, null, null);  
        imageWriter.write(null, iio_image, iwp);  
        imageWriter.dispose();  
    }  
  
    public static void drawString(Graphics2D g2, String value, int x, int y) {  
        Font font = new Font("Arial", Font.BOLD, 16);  
        g2.setColor(Color.BLACK);  
        g2.setFont(font);  
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,  
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  
        g2.drawString(value, x, y);  
    }

}