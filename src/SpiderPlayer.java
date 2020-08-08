
import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;

// LAST THING TO FIX IS PLAYING WHEN PLAYER STARTS SPINNING FAST AND PROJECTILE MOTION SHAPE,
// HOLDING LEFT AND RIGHT AT THE SAME TIME TRIGGERS WRONG ANIMATION WHEN WALKING

public class SpiderPlayer implements Swingable {
	// PROCESSING
	private static PApplet processing = null;

	// CONSTANTS
	private static final float maxAngle = (float) (Math.PI / 2);
	private final float VELOCITY_CHANGE = 0.01f;
	private final int RUNNING_SCALE = 65; // 40 originally

	// POSITIONS
	public float xPos = 10, yPos = 10;

	// DIRECTION FACING
	public static boolean facingDirection; // right == false, left == true

	// PENDULUM
	public float angle = (float) (-Math.PI / 2);
	float angleAccel, angleVelocity = 0, dt = (float) 0.15;
	float velocityY;
	float webLength = 400;
	public long currentTime;
	public long lastTime;
	public long timeDiff;
	public long elapsedTime;

	// SPIDERMAN IMAGE
	private PImage spRight1, spRight2, spRight2mid, spRight3, spRight4,
			spStandRight, spStandRight2, spRunRight1, spRunRight2, spRunRight3;
	private PImage spLeft1, spLeft2, spLeft2mid, spLeft3, spLeft4, spStandLeft,
			spStandLeft2, spRunLeft1, spRunLeft2, spRunLeft3;
	private PImage spJumpRightUp, spJumpRightDown, spJumpLeftUp, spJumpLeftDown;

	// OBJECTS
	public PImage currentSprite;
	public SpiderMain spidermain;
	private Building currentBuilding;

	// VARIABLES
	public int width, height;
	private boolean inMidAir, holdingCatchButton; // SPACEWASPRESSED ==FALLING!
	public boolean onGround;
	public static int webXOffset = 41, webYOffset = 7;
	private int animCounter = 0;
	boolean jumping = false;
	@SuppressWarnings("unused")
	private boolean skidLeft, skidRight;
	//private boolean doubleJump;

	// CONSTRUCTOR
	public SpiderPlayer(SpiderMain s) {
		spidermain = s;
		xPos = 10;
		yPos = 10;
		facingDirection = false; // right == false, left == true

		// RIGHT IMAGES
		spRight1 = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "spiderman31.png");
		spRight2 = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "spiderman32.png");
		spRight2mid = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spiderman325.png");
		spRight3 = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "spiderman33.png");
		spRight4 = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "spiderman34.png");
		spStandRight = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanRightStand.png");
		spStandRight2 = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanRightStand2.png");
		spRunRight1 = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanRightRun1.png");
		spRunRight2 = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanRightRun2.png");
		spRunRight3 = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanRightRun3.png");

		// LEFT IMAGES
		spLeft1 = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "spiderman44.png");
		spLeft2 = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "spiderman43.png");
		spLeft2mid = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spiderman435.png");
		spLeft3 = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "spiderman42.png");
		spLeft4 = processing.loadImage("images" + File.separator + "spiderman"
				+ File.separator + "spiderman41.png");
		spStandLeft = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanLeftStand.png");
		spStandLeft2 = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanLeftStand2.png");
		spRunLeft1 = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanLeftRun1.png");
		spRunLeft2 = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanLeftRun2.png");
		spRunLeft3 = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanLeftRun3.png");

		// JUMP IMAGES
		spJumpRightUp = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanRightJumpUp.png");
		spJumpRightDown = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanRightJumpDown.png");
		spJumpLeftUp = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanLeftJumpUp.png");
		spJumpLeftDown = processing.loadImage("images" + File.separator
				+ "spiderman" + File.separator + "spidermanLeftJumpDown.png");

		currentSprite = spRight1;
		width = spRight1.pixelWidth;
		height = spRight1.pixelHeight;
		currentBuilding = spidermain.getBuildings()[0];
	}

	private void animatePlayer() {
		// HANDLES SWINGING ANIMATIONS
		float x = this.xPos - webXOffset
				- spidermain.getGameCamera().getxOffset();
		float y = this.yPos - webYOffset
				- spidermain.getGameCamera().getyOffset();

		// WALKING/STANDING ANIMATIONS
		if (jumping) {
			if (facingDirection == false) { // RIGHT FACE
				if (velocityY < 0) { // ON UPWARD
					currentSprite = spJumpRightUp;
				} else {
					currentSprite = spJumpRightDown;
				}
			} else {
				if (velocityY < 0) { // ON UPWARD
					currentSprite = spJumpLeftUp;
				} else {
					currentSprite = spJumpLeftDown;
				}
			}
		} else if (onGround) {
			// WALKING
//			if (skidLeft) {
//				currentSprite = spRunRight3;
//				processing.image(spRunRight3, x, y);
//			} else if (skidRight) {
//				currentSprite = spRunLeft3;
//				processing.image(spRunLeft3, x, y);
//			} else 
			if (SpiderMain.right == true) { // RESTARTS ANIMATION
				if (animCounter > 39)
					animCounter = 0;

				if (animCounter >= 0 && animCounter < 10) {
					currentSprite = spRunRight1;
					// processing.image(spRunRight1, x, y);
				} else if (animCounter >= 10 && animCounter < 20) {
					currentSprite = spRunRight2;
					// processing.image(spRunRight2, x, y);
				} else if (animCounter >= 20 && animCounter < 30) {
					currentSprite = spRunRight3;
					// processing.image(spRunRight3, x, y);
				} else if (animCounter >= 30 && animCounter < 40) {
					currentSprite = spRunRight2;
					// processing.image(spRunRight2, x, y);
				}
				animCounter++;

				// find a way to speed up/ slow down animation with velocity
//				if (animCounter > 19)
//					animCounter = 0;
//
//				if (animCounter >= 0 && animCounter < 5)
//					processing.image(spRunRight1, x, y);
//				else if (animCounter >= 5 && animCounter < 10)
//					processing.image(spRunRight2, x, y);
//				else if (animCounter >= 10 && animCounter < 15)
//					processing.image(spRunRight3, x, y);
//				else if (animCounter >= 15 && animCounter < 20)
//					processing.image(spRunRight2, x, y);
//
//				animCounter++;

			} else if (SpiderMain.left == true) { // RESTARTS ANIMATION
				if (animCounter > 39)
					animCounter = 0;

				if (animCounter >= 0 && animCounter < 10) {
					currentSprite = spRunLeft1;
					// processing.image(spRunLeft1, x, y);
				} else if (animCounter >= 10 && animCounter < 20) {
					currentSprite = spRunLeft2;
					// processing.image(spRunLeft2, x, y);
				} else if (animCounter >= 20 && animCounter < 30) {
					currentSprite = spRunLeft3;
					// processing.image(spRunLeft3, x, y);
				} else if (animCounter >= 30 && animCounter < 40) {
					currentSprite = spRunLeft2;
					// processing.image(spRunLeft2, x, y);
				}

				animCounter++;
			}

//			else {
//				animCounter = 0;
//			}

			// STANDING STILL
			else if (facingDirection == false && SpiderMain.right == false) {
////				currentSprite = spStandRight;
////				processing.image(spStandRight, x, y);
//				
				if (animCounter > 19)
					animCounter = 0;

				if (animCounter >= 0 && animCounter < 10) {
					currentSprite = spStandRight;
					// processing.image(spStandRight, x, y);
				} else if (animCounter >= 10 && animCounter < 20) {
					currentSprite = spStandRight2;
					// processing.image(spStandRight2, x, y);
				}

				animCounter++;
			} else if (facingDirection && SpiderMain.left == false) {
////				currentSprite = spStandLeft;
////				processing.image(spStandLeft, x, y);
//				
				if (animCounter > 19)
					animCounter = 0;

				if (animCounter >= 0 && animCounter < 10) {
					currentSprite = spStandLeft;
					// processing.image(spStandLeft, x, y);
				} else if (animCounter >= 10 && animCounter < 20) {
					currentSprite = spStandLeft2;
					// processing.image(spStandLeft2, x, y);
				}

				animCounter++;
			} else {
//				
				animCounter = 0;
			}
		} else if (!onGround) {

			if (aboutToTouchGround) {
				if (facingDirection) {
					currentSprite = spStandLeft;
				} else {
					currentSprite = spStandRight;
				}
			}

			// SWINGING
			if (angleVelocity > 0) {
				if (angle < -.50) {
					currentSprite = spRight1;
					// processing.image(spRight1, x, y);
				} else if (angle > -.50 && angle < -.15) {
					currentSprite = spRight2;
					// processing.image(spRight2, x, y);
				} else if (angle > -.15 && angle < .15) {
					currentSprite = spRight2mid;
					// processing.image(spRight2mid, x, y);
				} else if (angle > .15 && angle < .50) {
					currentSprite = spRight3;
					// processing.image(spRight3, x, y);
				} else if (angle > .50) {
					currentSprite = spRight4;
					// processing.image(spRight4, x, y);
				}
			} else {
				if (angle < -.50) {
					currentSprite = spLeft1;
					// processing.image(spLeft1, x, y);
				} else if (angle > -.50 && angle < -.15) {
					currentSprite = spLeft2;
					// processing.image(spLeft2, x, y);
				} else if (angle > -.15 && angle < .15) {
					currentSprite = spLeft2mid;
					// processing.image(spLeft2mid, x, y);
				} else if (angle > .15 && angle < .50) {
					currentSprite = spLeft3;
					// processing.image(spLeft3, x, y);
				} else if (angle > .50) {
					currentSprite = spLeft4;
					// processing.image(spLeft4, x, y);
				}
			}
		}

		processing.image(currentSprite, x, y);
	}

	public void drawSpiderPlayer() {
		// Either moving on the ground or swinging
		swing();

		// DRAW THE SPIDERMAN (Animation)
		animatePlayer();

		// DRAW SPIDERMAN'S WEB
		drawWeb();
	}

	private void drawWeb() {
		// IF SWINGING (NOT RELEASED)
		if (!onGround && SpiderMain.space == false && inMidAir == false) {
			processing.stroke(255);
			processing.line(this.xPos - spidermain.getGameCamera().getxOffset(),
					this.yPos - spidermain.getGameCamera().getyOffset(),
					(float) currentBuilding.anchorX
							- spidermain.getGameCamera().getxOffset(),
					(float) Building.anchorY
							- spidermain.getGameCamera().getyOffset());
		}
	}

	public Building findNearestBuilding() {
		double shortestAnchorDistance = Math
				.abs(spidermain.getBuildings()[0].anchorX - this.xPos);
		int closest = currentBuilding.thisBuildingNumber - 1;

		for (int i = 0; i < spidermain.getBuildings().length; i++) {
			double distanceToCompare = Math
					.abs(spidermain.getBuildings()[i].anchorX - this.xPos);
			if (distanceToCompare <= shortestAnchorDistance) {
				shortestAnchorDistance = distanceToCompare;
				closest = i;
			}
		}
		return spidermain.getBuildings()[closest];
	}

	/**
	 * CATCH NEAREST BUILDING ALGORITHM:
	 * 
	 * 1. Calculate the distances from anchor 2. Calculate theta (tangent)
	 * depending on which side of building 3. Adjust angle by pi/2, set that as
	 * swinging angle 4. Fix web length
	 * 
	 * @param normalSwing USELESS!
	 * @return true if building caught is different from current building, false
	 *         otherwise
	 */
	private boolean catchNearestBuilding(boolean jumpOccur) {
		// Find Nearest building
		boolean toReturn = false;
		Building foundBuilding = findNearestBuilding();

		if (foundBuilding != currentBuilding) {
			currentBuilding = foundBuilding;
			toReturn = true;
		} // else building stays same

		double height = yPos - Building.anchorY;
		double length = xPos - currentBuilding.anchorX;
		// 2. Determine new angle to catch at after falling/new angle
		// New angle for normal swing is just inverted angle

		// Determine what side of the building yer on WORKS :)
		int sideOfBuilding = -1;
		if (currentBuilding.anchorX - xPos < 0) {
			sideOfBuilding = 1;
		} else if (currentBuilding.anchorX - xPos > 0) {
			sideOfBuilding = -1;
		}
		
		if (height / length > 0) {
			double angleTemp = Math.atan(height / length) - Math.PI / 2;
			angle = (float) Math.abs(angleTemp) * sideOfBuilding;
		} else {
			double angleTemp = Math.atan(height / length) + Math.PI / 2;
			angle = (float) Math.abs(angleTemp) * sideOfBuilding;
		}

		// 4. Adjust the weblength WORKS :)
		webLength = (float) Math
				.sqrt(Math.pow(length, 2) + (Math.pow(height, 2)));

		inMidAir = false;
		onGround = false;
		jumping = false;
		holdingCatchButton = true;
		return toReturn;
	}

	
	public float maxVelocity = 0.23f;
	
	@Override
	public void swing() {

		/******** ANGLE/VELOCITY/WEB CHANGES *********/

		if (SpiderMain.down) {
			webLength += 5;
		}
		if (SpiderMain.up) {
			if (webLength - 5 >= 50)
				webLength -= 5;
		}

		// FOR RUNNING AND SWINGING
		if (SpiderMain.right) // .5 originally
			angleVelocity += VELOCITY_CHANGE;
		if (angleVelocity >= maxVelocity)
			angleVelocity = maxVelocity;

		if (SpiderMain.left)
			angleVelocity -= VELOCITY_CHANGE;
		if (angleVelocity <= -maxVelocity)
			angleVelocity = -maxVelocity;

		if (jumping) {
			velocityY += 0.5f;
			yPos += velocityY;
		}

		/****************** TO FIX LOG: ******************/

		// MAYBE MAKE IT SO WHEN ANGLE pi/2 REACHED, start to fall?
		// -max angle collision and ground collision glitches
		// - input weirdness?
		// - make him flip when he begins to fall and the angle velocity is
		// high enough

		/************** SWINGING ***********/
		// 1. Determine Status
		boolean normalSwing = false, falling = false, catchBuilding = false,
				grounded = false, jumpingActive = false;

		if (holdingCatchButton && SpiderMain.h == false) {
			holdingCatchButton = false;
		}

		if (jumping) {
			// jumping no catch requested
			jumpingActive = true;
		} else if (onGround || collisionWithGround()) { // GROUNDED
			grounded = true;
		} else if (!SpiderMain.h && !holdingCatchButton && !SpiderMain.space
				&& !inMidAir) { // SWINGING AND NO INPUT
			normalSwing = true;
		} else if (SpiderMain.h && !holdingCatchButton && !SpiderMain.space
				&& !inMidAir) {// SWINGING AND CATCH REQUESTED
			normalSwing = catchBuilding = true;
		} else if (SpiderMain.h && holdingCatchButton && !SpiderMain.space
				&& !inMidAir) {// SWINGING AND CATCH Completed but holding
			normalSwing = true;
		} else if (!SpiderMain.h && !holdingCatchButton
				&& (SpiderMain.space || inMidAir)) {// FALLING,NOCATCHREQUEST
			falling = true;
		} else if (SpiderMain.h && !holdingCatchButton && !SpiderMain.space
				&& inMidAir) { // FALLING AND CATCH REQUESTED
			falling = catchBuilding = true;
		}

		// 2. Call appropriate action methods
		if (grounded) { // ON GROUND
			onGround();
		} else if (jumpingActive) {// && !catchBuilding) {
			jump();
		} else if (normalSwing && !catchBuilding) { // SWINGING NO INPUT
			normalSwing();
		} else if (falling && catchBuilding) { // CATCH WHEN FALLING
			catchBuilding(jumping);
		} else if (falling) { // SIMPLE FALLING
			falling();
		} else if (catchBuilding) { // CATCH WHEN SWINGING
			catchBuilding(jumping);
		}

		// KEEP SWINGING UNDER pi/2
		if (angle > maxAngle) {
			angle = (float) maxAngle;
			angleVelocity = 0;
		} else if (angle < -maxAngle) {
			angle = (float) -maxAngle;
			angleVelocity = 0;
		}

	}

	@Override
	public void normalSwing() {
		// NORMAL SWINGING
		angleAccel = (float) (-9.81 / webLength * Math.sin(angle));
		angleVelocity += angleAccel * dt;
		angle += angleVelocity * dt;

		// WRONG velocityY = (float) ((-9.81) / webLength * Math.cos(angle));
		xPos = (float) (currentBuilding.anchorX
				+ (Math.sin(angle) * webLength)); // anchor offset
		yPos = (float) (Building.anchorY + (Math.cos(angle) * webLength));

		// RESET FALLING VARS
		holdingCatchButton = false;
		inMidAir = false;
	}

	@Override
	public void onGround() {
		groundMovement();
	}

	@Override
	public void falling() {
		if (!inMidAir) {
			velocityY = -7f; // TEMPORARY FIX
			// this works for now, but for this to properly work:
			// y velocity needs to be carried from the swing, then if it truly
			// is carried, if you're going up on a swing, the swing will
			// arc upward, and dropping down when not moving will let you
			// straight down. this calculation needs to happen in the normal
			// swing method. ALSO, you can now reach above pi/2, so consider
			// coding that number range for a swing
		}

		xPos += (float) angleVelocity * currentSprite.pixelWidth;
		velocityY += 0.5f;
		yPos += velocityY;

		holdingCatchButton = false;
		inMidAir = true;
	}

	@Override
	public void catchBuilding(boolean jumpOccur) {
		if (!catchNearestBuilding(jumpOccur))
			normalSwing();
		else { // caught!
			if (SpiderMain.h == false)
				holdingCatchButton = false;
			else
				holdingCatchButton = true;

			inMidAir = false;
		}
	}

	private void groundMovement() {

		if (SpiderMain.left) {
			facingDirection = true;
			skidRight = false;
			skidLeft = false;
			xPos += (float) angleVelocity * RUNNING_SCALE;
		} else if (SpiderMain.right) {
			facingDirection = false;
			skidRight = false;
			skidLeft = false;
			xPos += (float) angleVelocity * RUNNING_SCALE;
		} else if (!SpiderMain.left && !SpiderMain.right) { // Slow down
			if (angleVelocity > 0) { // to the right
				facingDirection = false;
				skidRight = true;
				skidLeft = false;
				if (angleVelocity - VELOCITY_CHANGE > 0) {
					xPos += (angleVelocity -= VELOCITY_CHANGE) * RUNNING_SCALE;
				} else {
					angleVelocity = 0;
				}
			} else if (angleVelocity < 0) { // to the left
				facingDirection = true;
				skidRight = false;
				skidLeft = true;
				if (angleVelocity + VELOCITY_CHANGE < 0) {
					xPos += (angleVelocity += VELOCITY_CHANGE) * RUNNING_SCALE;
				} else {
					angleVelocity = 0;
				}
			}
		}

		// JUMPING BROKEN
		if (SpiderMain.space && onGround) {
			jumping = true;
			jump();
		}

		return;
	}

	private void startJump() {
		velocityY = -15.0f;
		onGround = false;
		inMidAir = true;
	}

	private int jumpCounter = 0;

	public void jump() {
		// CATCHING HANDLED
		if (SpiderMain.h) {
			catchBuilding(true);
			jumping = false;
			jumpCounter = 0;
			return;
		}

		if (angleVelocity > 0) { // to the right
			facingDirection = false;
		} else if (angleVelocity < 0) { // to the left
			facingDirection = true;
		}

		System.out.println(jumpCounter);
		if (SpiderMain.space && inMidAir) {
			startJump();
			jumpCounter++;
		}

		// FIX THAT JUMP WHEN STILL CHANGES FACING DIRECTION TO LEFT
		if (SpiderMain.space && onGround && !inMidAir) {
			startJump();
			jumpCounter++;
		}

		// start jump
		if (collisionWithGround()) {
			return;
		}

		xPos += angleVelocity * RUNNING_SCALE;
		velocityY += 0.1;
		yPos += velocityY;

		onGround = false;
		jumping = true;
		inMidAir = true;
	}

	private boolean aboutToTouchGround;

	// TO FIX:
	public boolean collisionWithGround() {
		if (yPos >= 2011) {
			yPos = 2010;
			aboutToTouchGround = true;
			velocityY = 0;
			jumpCounter = 0;
			onGround = true;
			jumping = false;
			inMidAir = false;
			return true;
		}
		return false;
	}

	// GETTERS AND SETTERS
	public static void setProcessing(PApplet processing) {
		SpiderPlayer.processing = processing;
	}

	public boolean isFacingDirection() {
		return facingDirection;
	}

	public void setFacingDirection(boolean facingDirection) {
		SpiderPlayer.facingDirection = facingDirection;
	}
	
	public Building getCurrentBuilding() {
		return currentBuilding;
	}

}