import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;

public class Cloud {

	// PROCESSING
	private static PApplet processing = null;

	public static final int xStart = 100, yStart = 235;
	public static double newXLocation = 100, newYLocation = 235;
	// public static int buildingNumber = 1;

	private int width = 300, height = 163;

	// RANDOMLY GENERATE SPEEDS
	private int moveSpeed = 2;
	public double xPos = 100, yPos = 235;
	public SpiderMain spidermain;
	
	private GameCamera gameCamera;

	private int yIncrement = 150;
	private int xIncrement = 100;

	private final String cloudImageURL = "images" + File.separator + "spiderman" + File.separator
			+ "cloud2transparent.png";
	public static PImage cloudImage;

	public Cloud(SpiderMain s, GameCamera g) {
		spidermain = s;
		gameCamera = g;

		if (cloudImage == null)
			cloudImage = processing.loadImage(cloudImageURL);

		// random generate one of 3 building types

		xPos = newXLocation;
		yPos = newYLocation;
		if (newXLocation + 1400 > SpiderMain.xSize) {
			xPos = (xIncrement += 20);
		}
		newXLocation += 1400;
		newYLocation += yIncrement;

		if (newYLocation > 750) {
			yIncrement *= -1;
		} else if (newYLocation < 235) {
			yIncrement *= -1;
		}

	}

	public static void resetCloud() {
		newXLocation = xStart;
		newYLocation = yStart;
	}

	public void drawCloud() {
		processing.image(cloudImage, (float) (-gameCamera.getxOffset() + xPos),
				(float) (-gameCamera.getyOffset() + yPos));
	}

	public static void setProcessing(PApplet processing) {
		Cloud.processing = processing;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public void automateCloud() {
		if (xPos + moveSpeed <= SpiderMain.xSize) {
			xPos += moveSpeed;
		} else {
			xPos = 0 - width;
		}

	}

	public void setGameCamera(GameCamera gameCamera) {
		this.gameCamera = gameCamera;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}