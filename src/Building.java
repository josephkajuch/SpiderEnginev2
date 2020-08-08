import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;

public class Building {

  // PROCESSING
  private static PApplet processing = null;
  
  public static double newXLocation = 200;
  public static int buildingNumber = 1;
  
  private int width = 200;
  
  public int thisBuildingNumber;
  public double xPos;
  public double anchorX;
  public static double anchorY = 300;
  public static final double yPos = 0;
  public SpiderMain spidermain;
  //public PImage building12, building2, building3; 
  private GameCamera gameCamera;

  public static PImage building1;
  public final String buildingURL = "images" + File.separator + "spiderman" + File.separator
	        + "building" + File.separator + "building6transparent.png";
  
  public Building(SpiderMain s, GameCamera g) {
    spidermain = s;
    gameCamera = g;
    
    if (building1 == null)
    building1 = processing.loadImage(buildingURL);
    
    // random generate one of 3 building types
    thisBuildingNumber = buildingNumber++;

    xPos = newXLocation;
    newXLocation += 500;
    anchorX = xPos + width / 2;
    
  }
  
  public void drawBuilding() {
    processing.image(building1, (float) (-gameCamera.getxOffset() + xPos), (int)-gameCamera.getyOffset());
  }
  
  public static void setProcessing(PApplet processing){
    Building.processing = processing;
  }
  
  public GameCamera getGameCamera() {
    return gameCamera;
  }

  public void setGameCamera(GameCamera gameCamera) {
    this.gameCamera = gameCamera;
  }
}
