import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager
{
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	// ================================== CREATE ARRAYS  ===============================
	/**
	 * create an array of values that will represent a full-color image
	 * @param rows - the number of rows in this image
	 * @param columns - the number of columns in this image
	 * @return - the array representing the image.
	 */
	public static int[][][] createRGBArrayOfSize(int rows, int columns)
	{
		return new int[rows][columns][3];
	}
	/**
	 * create an array of values that will represent a grayscale image
	 * @param rows - the number of rows in this image
	 * @param columns - the number of columns in this image
	 * @return - the array representing the image.
	 */
	public static int [][] createGrayscaleArrayOfSize(int rows, int columns)
	{
		return new int[rows][columns];
	}
	
	/**
	 * create a BufferedImage of the given size. Note that this is width/height, not numRows/numCols.
	 * @param width
	 * @param height
	 * @return - it should start off black.
	 */
	public static BufferedImage createBufferedImageOfSize(int width, int height)
	{
		return new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

	}
	// ================================== DEEP COPY ARRAYS ==============================
	/**
	 * makes a "deep Copy of this RGB array - that is, the numbers are all copied into a new array, not just a reference to the same array.
	 * @param source
	 * @return
	 */
	public static int[][][] deepCopyArray(int[][][] source)
	{
		int[][][] result = new int[source.length][source[0].length][source[0][0].length];
		for (int r=0; r < source.length; r++)
			for (int c=0; c<source[0].length; c++)
				for (int z=0; z<source[0][0].length; z++)
					result[r][c][z] = source[r][c][z];
		return result;
	}
	
	/**
	 * makes a "deep Copy of this grayscale array - that is, the numbers are all copied into a new array, not just a reference to the same array.
	 * @param source
	 * @return
	 */
	public static int[][] deepCopyArray(int[][] source)
	{
		int[][] result = new int[source.length][source[0].length];
		for (int r=0; r < source.length; r++)
			for (int c=0; c<source[0].length; c++)
				result[r][c] = source[r][c];
		return result;
	}
	/**
	 * makes a deep copy of this Buffered Image. Frankly this is taken wholesale from StackOverflow by somebody named Klark:
	 * http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
	 * @param bi - the source buffered image
	 * @return a copy of the buffered image, independent from bi.
	 */
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	// ==================================  COLOR DEPTH CONVERSIONS =====================
	/**
	 * convert this color array into a grayscale image of the same spatial size
	 * @param colorArray 
	 * @return grayArray - an array of only two dimensions, matching rows and cols in the spatial dimensions, but only having one number per pixel - the average of the three color components in the original.
	 */
	public static int[][] toGrayArray(int[][][] colorArray)
	{
		int[][] grays = new int[colorArray.length][colorArray[0].length];
		for (int r = 0; r<colorArray.length; r++)
			for (int c=0; c<colorArray[0].length; c++)
				grays[r][c] = (colorArray[r][c][0]+colorArray[r][c][1]+colorArray[r][c][2])/3;
		return grays;
	}
	
	/**
	 * convert this grayscale array into a full-color image of the same spatial size
	 * @param grayArray
	 * @return colorArray - an array of three dimensions, matching rows and cols in the spatial dimensions, and copying the pixel value into all three R,G,B channels in the new image.
	 */
	public static int[][][] toColorArray(int[][] grayArray)
	{
		int [][][] colors = new int[grayArray.length][grayArray[0].length][3];
		for (int r = 0; r<grayArray.length; r++)
			for (int c=0; c<grayArray[0].length; c++)
			    for (int z=0; z<3; z++)
			    	colors[r][c][z] = grayArray[r][c];
		return colors;
	}
	
	// ==================================  LOAD IMAGES AND ARRAYS  ======================
	/**
	 * loads an image file and sets it into a full-color array
	 * @param filename
	 * @return an array representing the image
	 */
	public static int[][][] RGBArrayFromFile(String filename)
	{
		return RGBArrayFromImage(loadImage(filename));
	}
	
	/**
	 * loads an image file and sets it into a full-color array
	 * @param file - a file location
	 * @return an array representing the image
	 */
	public static int[][][] RGBArrayFromFile(File file)
	{
		return RGBArrayFromImage(loadImage(file));
	}
	
	/**
	 * loads an image file and sets it into a grayscale array
	 * @param filename
	 * @return an array representing the image
	 */
	public static int[][] grayscaleArrayFromFile(String filename)
	{	return grayscaleArrayFromImage(loadImage(filename));
	}
	/**
	 * loads an image file and sets it into a grayscale array
	 * @param file - a file location
	 * @return an array representing the image
	 */
	public static int[][] grayscaleArrayFromFile(File file)
	{
		return grayscaleArrayFromImage(loadImage(file));
	}
	/**
	 * loads an image file and returns a BufferedImage.
	 * @param filename
	 * @return - the buffered Image
	 */
	public static BufferedImage loadImage(String filename)
	{
		File theFile = new File(filename);
		return loadImage(theFile);
	}
	
	/**
	 * loads an image file and returns a BufferedImage.
	 * @param file - a file location
	 * @return - the buffered Image
	 */
	public static BufferedImage loadImage(File file)
	{
		BufferedImage sourceImage = null;
		try
		{
		   

		   if (file.canRead()) 
		   {
			   sourceImage = ImageIO.read(file);
		   }
		   else
			   throw new RuntimeException("Could not open file.");
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		return sourceImage;
		
	}
	// ====================================  IMAGE TO ARRAY ================================
	/**
	 * Converts a BufferedImage into an RGB integer array
	 * @param source
	 * @return
	 */
	public static int[][][] RGBArrayFromImage(BufferedImage source)
	{
		int [][][] rgbArray = new int [source.getHeight()][source.getWidth()][3];
		System.out.println(source.getHeight()+","+source.getWidth());
		for (int r=0; r<source.getHeight(); r++)
			for (int c=0; c<source.getWidth(); c++)	
			{
				rgbArray[r][c][0] = (source.getRGB(c, r) >> 16)& 255;
				rgbArray[r][c][1] = (source.getRGB(c, r) >> 8)& 255;
				rgbArray[r][c][2] = (source.getRGB(c, r) >> 0)& 255;
			}
		return rgbArray;
	}
	/**
	 * converts a BufferedImage into a grayscale integer array.
	 * @param source
	 * @return
	 */
	public static int[][] grayscaleArrayFromImage(BufferedImage source)
	{
		int [][] grayArray = new int [source.getHeight()][source.getWidth()];
		for (int r=0; r<source.getHeight(); r++)
			for (int c=0; c<source.getWidth(); c++)
			{
				grayArray[r][c] = (((source.getRGB(c, r) )& 255) + ((source.getRGB(c, r) >> 8)& 255) + ((source.getRGB(c, r) >> 16)& 255))/3;
			}
		return grayArray;
	}
	
	
	// =====================================  ARRAY TO IMAGE =============================
	/**
	 * converts a three-color array into a BufferedImage
	 * @param inArray 
	 * @return
	 */
	public static BufferedImage ImageFromArray(int [][][] inArray)  // RGB version
	{
		int width = inArray[0].length;
		int height = inArray.length;
		BufferedImage destination = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		for (int r=0; r<height; r++)
			for (int c=0; c<width; c++)
			{
				destination.setRGB(c, r, (inArray[r][c][2])+(inArray[r][c][1]<<8)+(inArray[r][c][0]<<16));
			}
		return destination;
	}
	
	/**
	 * converts a grayscale array into a BufferedImage
	 * @param inArray
	 * @return
	 */
	public static BufferedImage ImageFromArray(int [][] inArray) //grayscale version
	{
		int width = inArray[0].length;
		int height = inArray.length;
		BufferedImage destination = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		for (int r=0; r<height; r++)
			for (int c=0; c<width; c++)
			{
				destination.setRGB(c, r, (inArray[r][c])+(inArray[r][c]<<8)+(inArray[r][c]<<16));
			}
		return destination;
	}
	
	
	// ====================================  SAVE ===================================
	
	/**
	 * saves a three-color image array to a graphics file
	 * @param inArray
	 * @param filename - should end with .png, .jpg, jpeg, or .gif
	 * @throws IOException - so you'll need to try/catch for it if you use this method.
	 */
	public static void saveImage(int[][][] inArray, String filename) throws IOException
	{
		saveImage(ImageFromArray(inArray),filename);
	}
	/**
	 * saves a grayscale image array to a graphics file
	 * @param inArray
	 * @param filename - should end with .png, .jpg, jpeg, or .gif
	 * @throws IOException - so you'll need to try/catch for it if you use this method.
	 */
	public static void saveImage(int[][] inArray, String filename) throws IOException
	{
		saveImage(ImageFromArray(inArray),filename);
	}
	
	/**
	 * saves a BufferedImage to a graphics file
	 * @param image
	 * @param filename - should end with .png, .jpg, .jpeg, or .gif
	 * @throws IOException - so you'll need to try/catch for it if you use this method.
	 */
	public static void saveImage(BufferedImage image, String filename) throws IOException
	{
		System.out.println(filename);
		int prev = -1;
		while (filename.indexOf(".",prev+1)>-1)
		{
			System.out.println(prev);
			prev = filename.indexOf(".", prev+1);
		}
		
		if (prev == -1)
			throw new RuntimeException("Attempted to save a file with out a suffix.");
		String suffix = filename.substring(prev+1).toLowerCase();
		if (!suffix.equals("png") && !suffix.equals("jpg") && !suffix.equals("gif") && !suffix.equals("jpeg"))
			throw new RuntimeException("Invalid suffix: \""+suffix+"\"");
		
		File outputfile = new File(filename);
		ImageIO.write(image, suffix, outputfile);
		
	}
	
}
