package com.xiaoyu.lingdian.tool.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swetake.util.Qrcode;
import com.xiaoyu.lingdian.tool.StringUtil;

public class QRCodeUtil {

	private static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");

	public static void main(String[] args) {
		createQRCode("http://www.baidu.com", "d://1.png", 260, null, 0);
		System.out.println(new QRCodeUtil().decoderQRCode("d://1.png"));
	}
	
	/**
	 * 生成二维码(QRCode)图片(png)
	 * 
	 * @param content 二维码图片的内容
	 * @param imgPath 生成二维码图片完整的路径
	 * @param iWidthHeight
	 * @param logoPath 二维码图片中间的LOGO路径,可不传
	 * @param lWidthHeight logo不需要可传0
	 */
	public static void createQRCode(String content, String imgPath, int iWidthHeight, String logoPath, int lWidthHeight) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小  
			qrcodeHandler.setQrcodeErrorCorrect('M');			
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			//int iWidthHeight = 67 + 12 * (size - 1); 
			long size = Math.round((iWidthHeight-67)/12.0+1);		
			qrcodeHandler.setQrcodeVersion((int)size);
			byte[] contentBytes = content.getBytes("utf-8");
	            
			BufferedImage bufImg = new BufferedImage(iWidthHeight, iWidthHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, iWidthHeight, iWidthHeight);
			gs.setColor(Color.BLACK);

			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容 > 二维码
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				logger.info("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
				return;
			}
			if (!StringUtil.isEmpty(logoPath)) {
				Image img = ImageIO.read(new File(logoPath));
				gs.drawImage(img, lWidthHeight, lWidthHeight, null);
			}
			
			gs.dispose();
			bufImg.flush();
			File imgFile = new File(imgPath);
			File parent = imgFile.getParentFile(); 
			if(parent != null && !parent.exists()){ 
				parent.mkdirs(); 
			}
			if (!imgFile.exists()) {
				try {
					imgFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ImageIO.write(bufImg, "png", imgFile);
		} catch (Exception e) {
			logger.info("生成二维码失败：" + e.getMessage());
		}
	}
      
    /** 
     * 解析二维码（QRCode） 
     * 
     * @param imgPath 图片路径 
     * @return 
     */  
    public String decoderQRCode(String imgPath) {  
        File imageFile = new File(imgPath);  
        BufferedImage bufImg = null;  
        String content = null;  
        try {  
            bufImg = ImageIO.read(imageFile);  
            QRCodeDecoder decoder = new QRCodeDecoder();
            content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");   
        } catch (IOException e) { 
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            dfe.printStackTrace();  
        }  
        return content;  
    }
    
	/**
	 * 生成二维码(Code39Bean)图片
	 * 
	 * @param content 二维码图片的内容
	 * @param imgPath 生成二维码图片完整的路径
	 */
	public static void createCode39Bean(String content, String imgPath) {
        try {
            Code39Bean bean = new Code39Bean();          
            final int dpi = 150;

            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
            bean.setWideFactor(3);
            bean.doQuietZone(false);
             
            File outputFile = new File(imgPath);
            OutputStream out = new FileOutputStream(outputFile);
            try {
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                        out, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
                bean.generateBarcode(canvas, content);
                canvas.finish();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public class TwoDimensionCodeImage implements QRCodeImage {  
		  
	    BufferedImage bufImg;  
	      
	    public TwoDimensionCodeImage(BufferedImage bufImg) {  
	        this.bufImg = bufImg;  
	    }  
	      
	    @Override  
	    public int getHeight() {  
	        return bufImg.getHeight();  
	    }  
	  
	    @Override  
	    public int getPixel(int x, int y) {  
	        return bufImg.getRGB(x, y);  
	    }  
	  
	    @Override  
	    public int getWidth() {  
	        return bufImg.getWidth();  
	    }  
	  
	}
}