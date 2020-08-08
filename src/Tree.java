import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;

public class Tree {

	// PROCESSING
	private static PApplet processing = null;

	public static double newXLocation = 100, newYLocation = 235;
	// public static int buildingNumber = 1;

	private int width = 400, height = 440;

	// RANDOMLY GENERATE SPEEDS
	private int moveSpeed = 2;
	public double xPos, yPos = 2000;
	public SpiderMain spidermain;
	public PImage treeImage;
	private GameCamera gameCamera;

	public Tree(SpiderMain s, GameCamera g) {
		spidermain = s;
		gameCamera = g;

		treeImage = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "treeSprite.png");

		// random generate one of 3 building types

		yPos = 2075 - treeImage.pixelHeight;

		xPos = newXLocation;
		// yPos = newYLocation;
		newXLocation += 1000;
		// newYLocation += 150;

	}

	public void drawTree() {
		processing.image(treeImage,
				0.1f * (float) (-gameCamera.getxOffset() + xPos),
				0.9f * (float) (-gameCamera.getyOffset() + yPos));
	}

	public static void setProcessing(PApplet processing) {
		Tree.processing = processing;
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