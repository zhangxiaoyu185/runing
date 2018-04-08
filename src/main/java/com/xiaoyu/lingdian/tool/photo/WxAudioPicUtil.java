package com.xiaoyu.lingdian.tool.photo;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * 微信语音图片生成
 *
 */
@SuppressWarnings("restriction")
public class WxAudioPicUtil {

	protected final Logger logger = LoggerFactory.getLogger("BASE_LOGGER");
	
	public static void main(String[] args) {
		String iconPath = "d://1.png";// 二维码
		byte[] bytes = new WxAudioPicUtil().outPic(iconPath);
		try{
			FileOutputStream fout = new FileOutputStream("d://2.png");
			//将字节写入文件
			fout.write(bytes);
			fout.close();
		}catch (Exception e){
		}
	}

	Font f = null;
	Graphics2D g = null;

	public byte[] outPic(String codePath) {
		String bgImgPath = Thread.currentThread().getContextClassLoader().getResource("\\commImg\\wxaudio_result.jpg").getPath();
		//String bgImgPath = "C:\\Users\\Administrator\\Desktop\\game\\src\\main\\resources\\commImg\\wxaudio_result.jpg";
		String afterFormat = getAfterLastPoint(bgImgPath).replace(".", "").toUpperCase();		
		byte[] bytes = null;

		try {
			Image bgImgSrc = ImageIO.read(new File(bgImgPath));
			BufferedImage buffImg = new BufferedImage(bgImgSrc.getWidth(null), bgImgSrc.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
			g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(bgImgSrc, 0, 0, null);

			float alpha = 1f; // 透明度

			// 二维码
			ImageIcon imgIcon = new ImageIcon(codePath);
			// 得到Image对象
			Image img = imgIcon.getImage();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 表示水印图片的位置
			g.drawImage(img, 244, 616, null);
			g.dispose();			
			
			ByteOutputStream out = new ByteOutputStream();
			ImageIO.write(buffImg, afterFormat, out);
            bytes = out.getBytes();
            try {
            	if (out != null) {
            		out.close();
     			}
			} catch (Exception e) {
			}           
            logger.info("文件图片成功");
		} catch (Exception e) {
			logger.info("生成图片失败");
		}

		return bytes;
	}

	/**
	 * 获取一个文件名的后缀名（最后一个点后面的字符串）,带上点
	 * 
	 * @param fileName
	 * @return
	 */
	public String getAfterLastPoint(String fileName) {
		int last = fileName.lastIndexOf(".");
		if (last <= 0) {
			return "";
		}
		return fileName.substring(last, fileName.length());
	}

}
