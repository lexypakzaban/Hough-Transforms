import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Test;

public class ImageManagerTest
{

	@Test
	public void testRGBFunctions()
	{
		int[][][] img = ImageManager.RGBArrayFromFile("IMTest/colorBlocks.png");
		assertEquals(128,img[0].length);
		assertEquals(196,img.length);
		BufferedImage bImg = ImageManager.ImageFromArray(img);
		assertEquals(128,bImg.getWidth());
		assertEquals(196,bImg.getHeight());
		assertEquals(255,img[50][50][ImageManager.GREEN]);
		assertEquals(255,img[50][100][ImageManager.GREEN]);
		assertEquals(255,img[50][100][ImageManager.RED]);
		
		for (int c=0; c<img[0].length; c++)
			img[98][c][ImageManager.GREEN] = 0;
		
		
		int[][][] copy = ImageManager.deepCopyArray(img);
		for (int i=0; i<30; i++)
		{
			int r = (int)(Math.random()*img.length);
			int c = (int)(Math.random()*img[0].length);
			int z = (int)(Math.random()*3);
			assertEquals(img[r][c][z],copy[r][c][z]);
		}
		int[][][] baby = ImageManager.createRGBArrayOfSize(64, 128);
		assertEquals(64,baby.length);
		assertEquals(128,baby[0].length);
		assertEquals(3,baby[0][0].length);
		assertEquals(0,baby[0][0][0]);
		
		try
		{
			ImageManager.saveImage(img, "IMTest/colorResult.png");
			ImageManager.saveImage(baby, "IMTest/Black.png");
		}
		catch (IOException ioExp)
		{
			ioExp.printStackTrace();
		}
	}

	@Test
	public void testGrayFunctions()
	{
		int[][] img = ImageManager.grayscaleArrayFromFile("IMTest/colorBlocks.png");
		assertEquals(196,img.length);
		assertEquals(128,img[0].length);
		BufferedImage bImg = ImageManager.ImageFromArray(img);
		assertEquals(128,bImg.getWidth());
		assertEquals(196,bImg.getHeight());
		
		assertEquals(85,img[50][50]);
		assertEquals(170,img[50][100]);
		assertEquals(85,img[100][50]);
		
		int[][] copy = ImageManager.deepCopyArray(img);
		for (int i=0; i<30; i++)
		{
			int r = (int)(Math.random()*img.length);
			int c = (int)(Math.random()*img[0].length);
			assertEquals(img[r][c],copy[r][c]);
		}
		
		try
		{
			ImageManager.saveImage(img, "IMTest/grayResult.png");
		}
		catch (IOException ioExp)
		{
			ioExp.printStackTrace();
		}
		
	}
	
}
