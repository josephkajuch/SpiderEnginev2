import java.io.File;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class Ring {

	// PROCESSING
	private static PApplet processing = null;

	public double xPos, yPos;
	public SpiderMain spidermain;
	// public PImage building12, building2, building3;
	private GameCamera gameCamera;

	static Random rndm, rndm2;

	public static PImage ringFront, ringBack;
	public final String ringFrontURL = "images" + File.separator + "spiderman" + File.separator + "ringFront3.png";
	public final String ringBackURL = "images" + File.separator + "spiderman" + File.separator + "ringBack3.png";

	public Ring(SpiderMain s, GameCamera g) {
		spidermain = s;
		gameCamera = g;

		if (ringFront == null) {
			ringFront = processing.loadImage(ringFrontURL);
		}

		if (ringBack == null) {
			ringBack = processing.loadImage(ringBackURL);
		}

		if (rndm == null) {
			rndm = new Random(10);

		}

		if (rndm2 == null) {
			rndm2 = new Random(20);

		}

		moveRing();
	}

	public void moveRing() {
		xPos = rndm.nextInt(SpiderMain.xSize - ringFront.pixelWidth);
		yPos = rndm2.nextInt(SpiderMain.ySize - ringFront.pixelHeight); // PLUS THE AMOUNT ABOVE THE BUILDING YOU CAN'T
																		// SWING
	}

	public void drawRingFront() {
		// MAKE IT SO YOU HAVE TO FIND each ring, make it hover, and program collision
		// with top and bottom. if you hit it,
		// velocity becomes zero. ring appears in random location on map and you have to
		// find it
		processing.image(ringFront, (float) (-gameCamera.getxOffset() + xPos),
				(float) (-gameCamera.getyOffset() + yPos));
	}

	public void drawRingBack() {
		// MAKE IT SO YOU HAVE TO FIND each ring, make it hover, and program collision
		// with top and bottom. if you hit it,
		// velocity becomes zero. ring appears in random location on map and you have to
		// find it
		processing.image(ringBack, (float) (-gameCamera.getxOffset() + xPos),
				(float) (-gameCamera.getyOffset() + yPos));
	}

	public int thickness = 10;

	public boolean ringCollision() {
//		int x = (int) spidermain.spiderman.xPos;
//		int y = (int) spidermain.spiderman.yPos;
//		int spidermanHeight = spidermain.spiderman.currentSprite.pixelHeight;
//
//		if (x > xPos + (ringFront.pixelWidth / 2) - 10 && x < xPos + (ringFront.pixelWidth / 2) + 10) {
//			// System.out.println("STEP 1");
//			if (y >= yPos + ringFront.pixelHeight - thickness - spidermanHeight
//					&& y <= yPos + ringFront.pixelHeight - spidermanHeight) {
//				if (y >= yPos + thickness && y <= yPos + ringFront.pixelHeight - spidermanHeight) {
//					
//					// System.out.println("Step 2");
//					return true;
//				}
//			}
//		}
//
//		return false;
		
		
		
		// MAKE IT SOMEHOW BE THE OPPOSITE OF SCORING
		int x = (int) spidermain.spiderman.xPos;
		int spidermanHeight = spidermain.spiderman.currentSprite.pixelHeight;
		int y = (int) spidermain.spiderman.yPos;

		//// THE TOP BOUNDARY
		
		//////////
		//
		//IN HERE IS SCORE
		//
		//////////
		
		//// THE BOTTOM BOUNDARY
		if (x > xPos + (ringFront.pixelWidth / 2) - 10 && x < xPos + (ringFront.pixelWidth / 2) + 10) {
			System.out.println("STEP 1");
			
			// if y position is between uper ring and lower upper 
			if (y > yPos + ringFront.pixelHeight && y < yPos && !checkScore()) {

			//if (y < yPos - spidermanHeight + thickness - thickness && y > yPos - spidermanHeight - thickness && checkScore() == false) {
				System.out.println("Step 2");
				//return false;
			}
		}

		return false;
	
	
	}

	public boolean checkScore() {
		int x = (int) spidermain.spiderman.xPos;
		int y = (int) spidermain.spiderman.yPos;
		int spidermanHeight = spidermain.spiderman.currentSprite.pixelHeight;

		if (x > xPos + (ringFront.pixelWidth / 2) - 25 && x < xPos + (ringFront.pixelWidth / 2) + 25) {
			// System.out.println("STEP 1");
			if (y < yPos + ringFront.pixelHeight - thickness - spidermanHeight && y > yPos + thickness) {
				// System.out.println("Step 2");
				return true;
			}
		}

		return false;
	}

	public boolean findRing() {
		int x = (int) spidermain.spiderman.xPos;
		int y = (int) spidermain.spiderman.yPos;

		if (x < xPos + ringFront.pixelWidth/2) {
			return true; // TRUE == RIGHT
		}

		return false; // FALSE == LEFT

	}

	public static void setProcessing(PApplet processing) {
		Ring.processing = processing;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public void setGameCamera(GameCamera gameCamera) {
		this.gameCamera = gameCamera;
	}
}
