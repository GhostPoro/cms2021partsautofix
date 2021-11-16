package core;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class main {

	public static void main(String[] args) {
		
		int waitTime = 10; // sec
		
	 	try {
			  
			System.out.println("Waiting...");
		 	
			for (int i = waitTime; i > 0; i--) {
				Thread.sleep(1000);
				System.out.println(i);
			}
			
			boolean fix = parseParameters(args);
			 
			Rectangle screenRectangle = new Rectangle(700,400,530,226);
			Robot robot = new Robot();
			
 	  		System.out.println("Start!");
			
			long start = System.currentTimeMillis();
			boolean work = true;
			
			while(work) {
				
				// wait before new cycle
				Thread.sleep(200);
				//Thread.sleep(1000);
				
				// start reparing
	        	robot.keyPress(KeyEvent.VK_SPACE);
	        	robot.keyRelease(KeyEvent.VK_SPACE);
	        	if(fix) { Thread.sleep(100); } else { Thread.sleep(1100); }
				
	        	// process frames
	        	start = System.currentTimeMillis();
				for (int i = 0; i < 200; i++) {
	 				BufferedImage image = robot.createScreenCapture(screenRectangle);
					
					int img_height = image.getHeight();
					int img_width = image.getWidth();
					
					int[] rgbData = image.getRGB(0, 0, img_width, img_height, null, 0, img_width);
					
					boolean processImage = true;

	 				for (int x = 0; x < img_width && processImage; x++) {
						for (int y = 0; y < img_height && processImage; y++) {
					        int pixel = rgbData[y * img_width + x];
					        int r = (pixel >> 16) & 0xff;
					        int g = (pixel >> 8) & 0xff;
					        int b = (pixel) & 0xff;
					        
				        	if(fix && r == 170 && g == 219 && b == 15) {
					        	robot.keyPress(KeyEvent.VK_SPACE);
					        	robot.keyRelease(KeyEvent.VK_SPACE);
					        	processImage = false;
					        }
				        	else if(!fix && r == 173 && g == 218 && b == 207) {
					        	robot.keyPress(KeyEvent.VK_SPACE);
					        	robot.keyRelease(KeyEvent.VK_SPACE);
					        	processImage = false;
					        }
						}
					}
	 				
					long now = System.currentTimeMillis();
					long delta = now - start;
					start = now;
					
					System.out.println("Frame processing time: " + delta + " ms.");
				}
			}
			
			System.out.println("Done!");
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private static boolean parseParameters(String[] prms) {
		if(prms != null && prms.length > 0) {
			for(String prm : prms) {
				switch (prm) {
					case "d"  : return false;
					case "-d" : return false;
					case "c"  : return false;
					case "-c" : return false;
					default : break;
				}
			}
		}
		return true;
	}

}
