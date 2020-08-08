import processing.opengl.PJOGL;
import java.io.File;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PFont;

public class SpiderMain extends PApplet {
	private PImage backgroundImage;
	private PImage restartButton;
	private PImage buildingButton;
	private PImage timeButton;
	private PImage loadingImage;

	private PImage arrow1, arrow2, arrow1r, arrow2r;
	private int animCounter = 0;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	public static boolean space;
	public static boolean h;
	public static boolean j;
	public static boolean r;
	public static boolean showBuildings;
	public static boolean mouseWasPressed;
	public static boolean falling;
	private static boolean timeOfDay;
	public static int backgroundColor;

	public static final int xSize = 11000;
	public static final int ySize = 2118;

	public static int width;
	public static int height;

	public static final int backgroundColorBlue = 135215251;
	public static final int backgroundColorBlack = 0;
	public int score = 0;

	public final String iconURL = "images" + File.separator + "spiderman" + File.separator + "spideyIcon.png";
	public final String timeIconURL = "images" + File.separator + "spiderman" + File.separator + "time3.png";
	public final String restartIconURL = "images" + File.separator + "spiderman" + File.separator + "restart2.png";
	public final String buildingIconURL = "images" + File.separator + "spiderman" + File.separator
			+ "buildingButton3.png";
	public final String backgroundDayURL = "images" + File.separator + "spiderman" + File.separator + "bg2day.png";
	public final String backgroundNightURL = "images" + File.separator + "spiderman" + File.separator + "bg1night.png";
	public final String loadingURL = "images" + File.separator + "spiderman" + File.separator + "loading.png";
	public final String arrow2URL = "images" + File.separator + "spiderman" + File.separator + "arrow2.png";
	public final String arrow1URL = "images" + File.separator + "spiderman" + File.separator + "arrow1.png";
	public final String arrow2rURL = "images" + File.separator + "spiderman" + File.separator + "arrow2r.png";
	public final String arrow1rURL = "images" + File.separator + "spiderman" + File.separator + "arrow1r.png";
	

	private int amountOfBuildings;

	public static int backXDraw;
	public static int backYDraw;

	public SpiderPlayer spiderman;
	private GameCamera gameCamera;
	private Building[] buildings;
	private Cloud[] clouds;
	private Tree[] trees;
	private Ring[] rings;
	private Ring mainRing;

	PFont terminal;

	private final String RENDERER = "processing.opengl.PGraphics2D";

	static {
		SpiderMain.width = 1480;
		SpiderMain.height = 720;
		SpiderMain.backXDraw = 0;
		SpiderMain.backYDraw = 0;
	}

	public SpiderMain() {
		this.amountOfBuildings = 20;
		Cloud.setProcessing((PApplet) this);
		Tree.setProcessing((PApplet) this);
		Building.setProcessing((PApplet) this);
		SpiderPlayer.setProcessing((PApplet) this);
		Ring.setProcessing((PApplet) this);
	}

	public void settings() {
		this.size(1480, 720, RENDERER);
		SpiderMain.timeOfDay = true;
		PJOGL.setIcon(iconURL);
	}

	public void loadImages() {
		this.timeButton = loadImage(timeIconURL);
		this.restartButton = loadImage(restartIconURL);
		this.buildingButton = loadImage(buildingIconURL);
		this.backgroundImage = loadImage(backgroundDayURL);
		this.loadingImage = loadImage(loadingURL);
		this.arrow2 = loadImage(arrow2URL);
		this.arrow1 = loadImage(arrow1URL);
		
		this.arrow2r = loadImage(arrow2rURL);
		this.arrow1r = loadImage(arrow1rURL);
	}

	public void loadGameElements() {
		Building.newXLocation = 700.0;
		Building.buildingNumber = 1;

		this.buildings = new Building[this.amountOfBuildings];

		for (int i = 0; i < this.buildings.length; ++i) {
			this.buildings[i] = new Building(this, this.gameCamera);
		}

		this.spiderman = new SpiderPlayer(this);

		Cloud.resetCloud();
		this.clouds = new Cloud[7];
		for (int i = 0; i < this.clouds.length; ++i) {
			this.clouds[i] = new Cloud(this, this.gameCamera);
		}

		mainRing = new Ring(this, gameCamera);

//		this.trees = new Tree[20];
//		for (int i = 0; i < this.trees.length; ++i) {
//			this.trees[i] = new Tree(this, this.gameCamera);
//		}
	}

	public void setup() {
		this.orientation(2);
		SpiderMain.width = this.sketchWidth();
		SpiderMain.height = this.sketchHeight();

		this.surface.setResizable(true);
		this.surface.setTitle("SpiderEngine v2");

		System.out.print("Loading...");

		terminal = createFont("terminal.ttf", 32);
		this.setGameCamera(this.gameCamera = new GameCamera(this, 10.0f, 10.0f));
		loadImages();
		loadGameElements();
		SpiderMain.backgroundColor = backgroundColorBlue;
		SpiderMain.showBuildings = true;

		score = 0;
		System.out.println(" Done!");

		this.image(loadingImage, (float) (SpiderMain.width / 2 - 200), (float) (SpiderMain.height / 2 - 100));
	}

	@SuppressWarnings("unused")
	private void buttonsAndControls() {
		if (this.mousePressed) {
			if (this.mouseX <= 90 && this.mouseY < 104) {
				this.setup();
			}
			if (!SpiderMain.mouseWasPressed && this.mouseX >= 100 && this.mouseX <= 190 && this.mouseY < 104) {
				if (SpiderMain.showBuildings) {
					SpiderMain.showBuildings = false;
					SpiderMain.mouseWasPressed = true;
				} else if (!SpiderMain.showBuildings) {
					SpiderMain.showBuildings = true;
					SpiderMain.mouseWasPressed = true;
				}
			}
			if (!SpiderMain.mouseWasPressed && this.mouseX >= 200 && this.mouseX <= 290 && this.mouseY < 104) {
				if (SpiderMain.timeOfDay) {
					SpiderMain.backgroundColor = 0;
					SpiderMain.timeOfDay = false;
					SpiderMain.mouseWasPressed = true;
					this.backgroundImage = this.loadImage(
							"images" + File.separator + "spiderman" + File.separator + "nyc7InstructionsNight.jpg");
				} else if (!SpiderMain.timeOfDay) {
					SpiderMain.backgroundColor = 135215251;
					SpiderMain.timeOfDay = true;
					SpiderMain.mouseWasPressed = true;
					this.backgroundImage = this.loadImage(
							"images" + File.separator + "spiderman" + File.separator + "nyc14Instructions.png");
				}
			}
			if (this.keyCode == 104 || (this.mouseX < SpiderMain.width / 2 && this.mouseY > 200)) {
				SpiderMain.h = true;
				if (SpiderMain.falling) {
					SpiderMain.falling = false;
				}
			} else {
				SpiderMain.h = false;
			}
			if (this.keyCode == 32 || (this.mouseX > SpiderMain.width / 2 && this.mouseY > 200)) {
				SpiderMain.space = true;
				SpiderMain.falling = true;
			} else {
				SpiderMain.space = false;
			}
			if (this.spiderman.onGround && this.mouseY > 200) {
				if (this.mouseX < SpiderMain.width / 2) {
					SpiderMain.left = true;
				} else {
					SpiderMain.left = false;
				}
				if (this.mouseX > SpiderMain.width / 2) {
					SpiderMain.right = true;
				} else {
					SpiderMain.right = false;
				}
			}
		} else {
			SpiderMain.h = false;
			SpiderMain.space = false;
			SpiderMain.left = false;
			SpiderMain.right = false;
			SpiderMain.mouseWasPressed = false;
		}
	}

	public void draw() {
		SpiderMain.width = this.sketchWidth();
		SpiderMain.height = this.sketchHeight();
		this.background(SpiderMain.backgroundColor);

		// BACKGROUND
		this.image(this.backgroundImage, (float) (int) (-this.gameCamera.getxOffset()),
				(float) (int) (-this.gameCamera.getyOffset()));

		// CLOUDS BEHIND
		for (int i = 0; i < this.clouds.length; i += 2) {
			this.clouds[i].automateCloud();
			this.clouds[i].drawCloud();
		}

		// BUILDINGS
		if (SpiderMain.showBuildings) {
			for (int i = 0; i < this.buildings.length; ++i)
				this.buildings[i].drawBuilding();
		}

		if (mainRing.ringCollision()) {
			spiderman.angleVelocity = 0;
		}
		
		// RINGS BACK
		mainRing.drawRingBack();

		// SPIDERMAN
		this.spiderman.drawSpiderPlayer();

		// RINGS FRONT
		mainRing.drawRingFront();

		// CHECK FOR SCORE!!
		if (mainRing.checkScore()) {
			score++;
			mainRing.moveRing();
		}

		// CLOUDS IN FRONT
		for (int i = 1; i < this.clouds.length; i += 2) {
			this.clouds[i].automateCloud();
			this.clouds[i].drawCloud();
		}

		// CENTER CAMERA
		this.gameCamera.centerOnPlayer(this.spiderman);

		// UI ON SCREEN
		this.image(this.restartButton, 0.0f, 0.0f);
		this.image(this.buildingButton, 100.0f, 0.0f);
		this.image(this.timeButton, 200.0f, 0.0f);

		// SCORE -- 0 to 10000 only works
		int rectWidth = 0;
		if (score < 100) {
			rectWidth = 150;
		} else if (score >= 100 && score < 1000) {
			rectWidth = 162;
		} else {
			rectWidth = 175;
		}

		fill(255);
		stroke(0);
		this.rect(-1, height - 55, rectWidth, 55);
		fill(0, 0, 0);
		textFont(terminal);
		textAlign(LEFT);
		this.text("Score: " + score + "\n", 15, height - 20);
		
		if (mainRing.findRing()) {
			// RESTARTS ANIMATION
			if (animCounter > 59)
				animCounter = 0;

			if (animCounter >= 0 && animCounter < 20) {
				image(arrow1r, width - 50, height / 2 - arrow2.pixelHeight/2);
			} else if (animCounter >= 20 && animCounter < 40) {
				image(arrow2r, width - 50, height / 2 - arrow2.pixelHeight/2);
				// processing.image(spRunRight2, x, y);
			} else {
				// blank
			}
			animCounter++;
			//image(arrow2, width - 50, height / 2);
			
		} else {
//			fill(255, 00, 172);
//			ellipse(0, height / 2, 50, 50);
		
			// RESTARTS ANIMATION
				if (animCounter > 59)
					animCounter = 0;

				if (animCounter >= 0 && animCounter < 20) {
					image(arrow1, 0, height / 2 - arrow2.pixelHeight/2);
				} else if (animCounter >= 20 && animCounter < 40) {
					image(arrow2, 0, height / 2 - arrow2.pixelHeight/2);
					// processing.image(spRunRight2, x, y);
				} else {
					// blank
				}
				animCounter++;
		}
		
		

	}

	@SuppressWarnings("unused")
	private void drawBackground() {
		Tree[] trees;
		for (int length = (trees = this.trees).length, i = 0; i < length; ++i) {
			Tree tree = trees[i];
			tree.drawTree();
		}
	}

	public void keyPressed() {
		if (this.key == 'a' || this.keyCode == 37) {
			SpiderMain.left = true;
			SpiderMain.right = false;
		} else if (this.key == 'd' || this.keyCode == 39) {
			SpiderMain.right = true;
			SpiderMain.left = false;
		}
		if (this.key == 'w' || this.keyCode == 38) {
			SpiderMain.up = true;
		}
		if (this.key == 's' || this.keyCode == 40) {
			SpiderMain.down = true;
		}
		if (this.key == ' ') {
			SpiderMain.space = true;
		}
		if (this.key == 'h') {
			SpiderMain.h = true;
		}
		if (this.key == 'j') {
			SpiderMain.j = true;
		}
		if (this.key == 'r') {
			this.setup();
		}
		if (this.key == 'n') {
			if (SpiderMain.timeOfDay) {
				SpiderMain.backgroundColor = backgroundColorBlack;
				SpiderMain.timeOfDay = false;
				SpiderMain.mouseWasPressed = true;
				backgroundImage = this.loadImage(backgroundNightURL);
			} else if (!SpiderMain.timeOfDay) {
				SpiderMain.backgroundColor = backgroundColorBlue;
				SpiderMain.timeOfDay = true;
				SpiderMain.mouseWasPressed = true;
				backgroundImage = this.loadImage(backgroundDayURL);
			}
		}
	}

	public void keyReleased() {
		if (this.key == 'a' || this.keyCode == 37) {
			SpiderMain.left = false;
		} else if (this.key == 'd' || this.keyCode == 39) {
			SpiderMain.right = false;
		}
		if (this.key == 'w' || this.keyCode == 38) {
			SpiderMain.up = false;
		}
		if (this.key == 's' || this.keyCode == 40) {
			SpiderMain.down = false;
		}
		if (this.key == ' ') {
			SpiderMain.space = false;
		}
		if (this.key == 'h') {
			SpiderMain.h = false;
		}
		if (this.key == 'j') {
			SpiderMain.j = false;
		}
		if (this.key == 'r') {
			SpiderMain.r = false;
		}
	}

	public static void main(String[] args) {
		PApplet.main("SpiderMain");
	}

	public GameCamera getGameCamera() {
		return this.gameCamera;
	}

	public void setGameCamera(final GameCamera gameCamera) {
		this.gameCamera = gameCamera;
	}

	public int getWidth() {
		return SpiderMain.width;
	}

	public int getHeight() {
		return SpiderMain.height;
	}

	public Building[] getBuildings() {
		return this.buildings;
	}

}