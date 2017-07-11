package wgh.dubbo.common.utils;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil
{

	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	// 二维码尺寸
	private static final int QRCODE_SIZE = 300;
	// LOGO宽度
	private static final int WIDTH = 60;
	// LOGO高度
	private static final int HEIGHT = 60;

	/**
	 * 生成二维码
	 */
	private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception
	{
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);

		int[] rec = bitMatrix.getEnclosingRectangle();
		int resWidth = rec[2] + 1;
		int resHeight = rec[3] + 1;
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++)
		{
			for (int j = 0; j < resHeight; j++)
			{
				if (bitMatrix.get(i + rec[0], j + rec[1]))
				{
					resMatrix.set(i, j);
				}
			}
		}

		int width = resMatrix.getWidth();
		int height = resMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				image.setRGB(x, y, resMatrix.get(x, y) == true ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
			}
		}

		// int width = bitMatrix.getWidth();
		// int height = bitMatrix.getHeight();
		// BufferedImage image = new BufferedImage(width, height,
		// BufferedImage.TYPE_INT_RGB);
		// for (int x = 0; x < width; x++)
		// {
		// for (int y = 0; y < height; y++)
		// {
		// image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
		// }
		// }
		// if (imgPath == null || "".equals(imgPath))
		// {
		// return image;
		// }
		// 插入图片
		QRCodeUtil.insertImage(image, imgPath, needCompress);
		return image;
	}

	// public static Bitmap Create2DCode(String str, int picWidth, int
	// picHeight) throws WriterException {
	// // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
	// Hashtable hints = new Hashtable();
	// hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
	// BitMatrix matrix = new MultiFormatWriter().encode(str,
	// BarcodeFormat.QR_CODE, picWidth, picHeight, hints);
	// int width = matrix.getWidth();
	// int height = matrix.getHeight();
	// // 二维矩阵转为一维像素数组,也就是一直横着排了
	// int[] pixels = new int[width * height];
	// for (int y = 0; y < height; y++) {
	// for (int x = 0; x < width; x++) {
	// if (matrix.get(x, y)) {
	// pixels[y * width + x] = 0xff000000;
	// } else {
	// pixels[y * width + x] = 0xffffffff;
	// }
	//
	// }
	// }
	//
	// Bitmap bitmap = Bitmap.createBitmap(width, height,
	// Bitmap.Config.ARGB_8888);
	// // 通过像素数组生成bitmap,具体参考api
	// bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	// return bitmap;
	// }

	
	private static BufferedImage createImageCircle(String content, String imgPath, boolean needCompress) throws Exception
	{
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);

	 
		BitMatrix resMatrix = updateBit(bitMatrix, 8);//设置白边大小
		int width = resMatrix.getWidth();
		int height = resMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				image.setRGB(x, y, resMatrix.get(x, y) == true ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
			}
		}
		// 插入图片
		QRCodeUtil.insertImage(image, imgPath, needCompress);
		return image;
	}
	
	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param imgPath
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception
	{
		File file = new File(imgPath);
		if (!file.exists())
		{
			System.err.println("" + imgPath + "   该文件不存在！");
			return;
		}
		Image src = ImageIO.read(new File(imgPath));
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress)
		{ // 压缩LOGO
			if (width > WIDTH)
			{
				width = WIDTH;
			}
			if (height > HEIGHT)
			{
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2 - 8;
		int y = (QRCODE_SIZE - height) / 2 - 8;
		graph.drawImage(src, x, y, width - 10, height - 10, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width - 10, width - 10, 0, 0);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存放目录
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public static void encodeTemp(String content, String imgPath, String destPath, boolean needCompress) throws Exception
	{
		BufferedImage image = QRCodeUtil.createImageCircle(content, imgPath, needCompress);
		mkdirs(destPath);
		String file = new Random().nextInt(99999999) + ".jpg";
		ImageIO.write(image, "png", new File(destPath + "/" + file));
	}

	/**
	 * 改变二维码白边大小
	 */
	private static BitMatrix updateBit(BitMatrix matrix, int margin)
	{
		int tempM = margin * 2;
		int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
		int resWidth = rec[2] + tempM;
		int resHeight = rec[3] + tempM;
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
		resMatrix.clear();
		for (int i = margin; i < resWidth - margin; i++)
		{ // 循环，将二维码图案绘制到新的bitMatrix中
			for (int j = margin; j < resHeight - margin; j++)
			{
				if (matrix.get(i - margin + rec[0], j - margin + rec[1]))
				{
					resMatrix.set(i, j);
				}
			}
		}
		return resMatrix;
	}
	
	
	/* ======================================================================== */
	/**
	 * 生成二维码(内嵌LOGO) 返回byte[]数据
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public static BufferedImage encodeToByteData(String content, String imgPath, boolean needCompress) throws Exception
	{
		BufferedImage bImage = QRCodeUtil.createImage(content, imgPath, needCompress);
		// java.io.ByteArrayOutputStream out = new
		// java.io.ByteArrayOutputStream();
		// ImageIO.write(bImage, FORMAT_NAME, out);
		return bImage;
	}
	/**
	 * 生成二维码(内嵌LOGO) 返回byte[]数据
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public static BufferedImage encodeToByteDataCircle(String content, String imgPath, boolean needCompress) throws Exception
	{
		BufferedImage bImage = QRCodeUtil.createImageCircle(content, imgPath, needCompress);
		// java.io.ByteArrayOutputStream out = new
		// java.io.ByteArrayOutputStream();
		// ImageIO.write(bImage, FORMAT_NAME, out);
		return bImage;
	}

	/**
	 * 
	 * @param image
	 *            原始图片
	 * @param insertImage
	 *            插入有logo的二维码图片
	 * @param needCompress
	 *            是否压缩
	 * @return
	 */
	private static BufferedImage createInsertImage(String sourceImagePath, BufferedImage image, BufferedImage insertImage, boolean needCompress, String userName, String fontType, int style, int size, BufferedImage headerImage) throws Exception
	{
		// 插入图片
		insertImage(sourceImagePath, image, insertImage, needCompress, userName, fontType, style, size, headerImage);
		return image;
	}

	/**
	 * 
	 * @param source
	 *            原始图片
	 * @param insertImage
	 *            插入有logo的二维码图片
	 * @param needCompress
	 *            是否压缩
	 * @throws Exception
	 */
	private static void insertImage(String sourceImagePath, BufferedImage source, BufferedImage insertImage, boolean needCompress, String userName, String fontType, int style, int size, BufferedImage headerImage) throws Exception
	{
		Image src = insertImage;
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) // 是否压缩
		{ // 压缩插入图片
			if (width > WIDTH)
			{
				width = WIDTH;
			}
			if (height > HEIGHT)
			{
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 不压缩插入图片
		int x = (source.getWidth()) / 3;
		int y = (source.getHeight()) / 3;
		Graphics2D graph = source.createGraphics();

		// 插入带Logo的二维码图片
		Shape shape = null;
		if (sourceImagePath.contains("1.png") || sourceImagePath.contains("2.png") || sourceImagePath.contains("shareLoginMiddle.png"))
		{
			graph.drawImage(src, 134 * 2, 509 * 2, width - 45, height - 45, null);// 画图大小
			shape = new RoundRectangle2D.Float(134 * 2, 509 * 2, width - 45, height - 45, 0, 0);// 轮廓大小
		} else if (sourceImagePath.contains("3.png"))
		{
			graph.drawImage(src, 133 * 2, 314 * 2, width - 45, height - 45, null);// 画图大小
			shape = new RoundRectangle2D.Float(133 * 2, 314 * 2, width - 45, height - 45, 0, 0);// 轮廓大小
		} else if (sourceImagePath.contains("shareLoginSmall.png"))
		{
			graph.drawImage(src, 113 * 2, 433 * 2, width - 77, height - 77, null);// 画图大小
			shape = new RoundRectangle2D.Float(113 * 2, 433 * 2, width - 77, height - 77, 0, 0);// 轮廓大小
		} else if (sourceImagePath.contains("shareLoginBig.png"))
		{
			graph.drawImage(src, 225 * 2, 848 * 2, width + 75, height + 75, null);// 画图大小
			shape = new RoundRectangle2D.Float(225 * 2, 848 * 2, width + 75, height + 75, 0, 0);// 轮廓大小
		}
		// Shape shape = new Ellipse2D.Double(x, y, width / 2, height / 2); 圆形轮廓
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);

		// 插入用户头像
		Shape shape1 = null;
		if (sourceImagePath.contains("2.png") || sourceImagePath.contains("1.png"))
		{
			graph.setColor(Color.WHITE);
			graph.drawImage(headerImage, 160 * 2, 350 * 2, width / 3, height / 3, null);// 画图大小

			shape1 = new RoundRectangle2D.Float(160 * 2, 350 * 2, width / 3, height / 3, 0, 0);// 轮廓大小
			// // 插入文字
			// Font font = new Font(fontType, style, size);
			// Color color = Color.BLACK;
			// graph.setFont(font);
			// graph.setColor(color);
			// graph.drawString("我是" + userName, 148 * 2, 435 * 2);
			graph.setStroke(new BasicStroke(3f));
			graph.draw(shape1);
		} else if (sourceImagePath.contains("3.png"))
		{
			graph.setColor(Color.WHITE);
			graph.drawImage(headerImage, 160 * 2, 80 * 2, width / 3, height / 3, null);// 画图大小
			shape1 = new RoundRectangle2D.Float(160 * 2, 80 * 2, width / 3, height / 3, 0, 0);// 轮廓大小
			// // 插入文字
			// Font font = new Font(fontType, style, size);
			// Color color = Color.BLACK;
			// graph.setFont(font);
			// graph.setColor(color);
			// graph.drawString("我是" + userName, 160 * 2, 160 * 2);
			graph.setStroke(new BasicStroke(3f));
			graph.draw(shape1);
		}

		if (sourceImagePath.contains("2.png") || sourceImagePath.contains("1.png"))
		{
			// 插入文字
			Font font = new Font(fontType, style, size);
			Color color = Color.BLACK;
			graph.setFont(font);
			graph.setColor(color);
			graph.drawString("我是" + userName, 157 * 2, 415 * 2);
			graph.setStroke(new BasicStroke(3f));
			graph.dispose();
		} else if (sourceImagePath.contains("3.png"))
		{
			// 插入文字
			Font font = new Font(fontType, style, size);
			Color color = Color.BLACK;
			graph.setFont(font);
			graph.setColor(color);
			graph.drawString("我是" + userName, 158 * 2, 145 * 2);
			graph.setStroke(new BasicStroke(3f));
			graph.dispose();
		}

	}


	/**
	 * 图片剪切
	 * 
	 * @param srcImageFile
	 * @param x
	 * @param y
	 * @param width
	 * @param heigh
	 * @throws IOException
	 */
	public static void cut(String srcImageFile, int x, int y, int width, int heigh) throws IOException
	{
		Image img;
		ImageFilter cropFilter;
		String dir = null;
		// 读取源图像
		BufferedImage src = ImageIO.read(new File(srcImageFile));
		cropFilter = new CropImageFilter(x, y, width, heigh);
		img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(src.getSource(), cropFilter));
		BufferedImage tag = new BufferedImage(width, heigh, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		// 输出为文件
		dir = "E:\\cut_image.jpg";
		File f = new File(dir);
		ImageIO.write(tag, "PNG", f);
	}

	/**
	 * 
	 * @param sourceImagePath
	 *            原始图片地址
	 * @param insertImage
	 *            插入二维码图片
	 * @param saveImagePath
	 *            生成后保存地址
	 * @param needCompress
	 *            是否压缩
	 * @throws Exception
	 */
	public static void encode(String sourceImagePath, BufferedImage insertImage, String saveImagePath, boolean needCompress, String userName, String fontType, int style, int size, BufferedImage headerImage) throws Exception
	{
		BufferedImage image = ImageIO.read(new FileInputStream(sourceImagePath)); // 本地图片

		BufferedImage imageTemp = createInsertImage(sourceImagePath, image, insertImage, needCompress, userName, fontType, style, size, headerImage);

		mkdirs(saveImagePath);
		String file = new Random().nextInt(99999999) + ".jpg";
		ImageIO.write(imageTemp, FORMAT_NAME, new File(saveImagePath + "/" + file));
	}

	public static byte[] encodeByte(String sourceImagePath, BufferedImage insertImage, boolean needCompress, String userName, String fontType, int style, int size, BufferedImage headerImage) throws Exception
	{
		BufferedImage image = ImageIO.read(new FileInputStream(sourceImagePath)); // 本地图片

		BufferedImage imageTemp = createInsertImage(sourceImagePath, image, insertImage, needCompress, userName, fontType, style, size, headerImage);

		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		ImageIO.write(imageTemp, FORMAT_NAME, out);
		return out.toByteArray();

	}

	/**
	 * 生成图片返回byte数据方法
	 * 
	 * @param sourceImagePath
	 *            原始图片地址
	 * @param insertImage
	 *            插入二维码图片
	 * @param saveImagePath
	 *            生成后保存地址
	 * @param needCompress
	 *            是否压缩
	 * @return
	 */
	public static byte[] encodeToByte(String sourceImagePath, BufferedImage insertImage, String saveImagePath, boolean needCompress, String userName, String fontType, int style, int size, BufferedImage headerImage) throws Exception
	{
		BufferedImage image = ImageIO.read(new FileInputStream(sourceImagePath)); // 本地图片

		BufferedImage imageTemp = createInsertImage(sourceImagePath, image, insertImage, needCompress, userName, fontType, style, size, headerImage);

		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

		ImageIO.write(imageTemp, FORMAT_NAME, out);

		return out.toByteArray();
	}

	/* ======================================================================= */

	/**
	 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
	 * 
	 * @author lanyuan Email: mmm333zzz520@163.com
	 * @date 2013-12-11 上午10:16:36
	 * @param destPath
	 *            存放目录
	 */
	public static void mkdirs(String destPath)
	{
		File file = new File(destPath);
		// 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory())
		{
			file.mkdirs();
		}
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存储地址
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath, String destPath) throws Exception
	{
		QRCodeUtil.encodeTemp(content, imgPath, destPath, false);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param destPath
	 *            存储地址
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String destPath, boolean needCompress) throws Exception
	{
		QRCodeUtil.encodeTemp(content, null, destPath, needCompress);
	}

	/**
	 * 图片中添加文字内容测试
	 * 
	 * @param buffImage
	 * @param userName
	 * @param fontType
	 * @param style
	 * @param size
	 * @throws Exception
	 */
	public static void addText(BufferedImage buffImage, String userName, String fontType, int style, int size) throws Exception
	{
		// BufferedImage buffImage = ImageIO.read(new File("E:\\Penguins.jpg"));
		Graphics g = buffImage.getGraphics();
		Font font = new Font(fontType, style, size);
		g.setFont(font);
		g.drawString("我是" + userName, 100, 100);
		FileOutputStream outImg = new FileOutputStream(new File("E:\\test.jpg"));
		ImageIO.write(buffImage, "jpg", outImg);
		outImg.flush();
		outImg.close();
	}

}