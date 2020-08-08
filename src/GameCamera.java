public class GameCamera {

  public SpiderMain spidermain;
  private float xOffset, yOffset;
  
  public GameCamera(SpiderMain s, float xOffset, float yOffset) {
    this.spidermain = s;
    this.setxOffset(xOffset);
    this.setyOffset(yOffset);
  }
  
  public void centerOnPlayer(SpiderPlayer s) {

      //left wall camera restriction
      if (s.xPos - SpiderPlayer.webXOffset - SpiderMain.width / 2 + s.width/2 < 0 || s.xPos + SpiderPlayer.webXOffset + SpiderMain.width / 2 + s.width/2 > 11000){
        if(!(s.yPos - SpiderPlayer.webYOffset - SpiderMain.height / 2 + s.height/2 < 0 || s.yPos + SpiderPlayer.webYOffset + SpiderMain.height / 2 + s.height/2 > 2200)){
            yOffset = s.yPos - SpiderPlayer.webYOffset - SpiderMain.height / 2 + s.height/2;
        } else {
            return;
        }
        return;
    }

      // upper limit camera restriction -- 2200 is for the lower bound, close to actual ySize of SpiderMain
    if(s.yPos - SpiderPlayer.webYOffset - SpiderMain.height / 2 + s.height/2 < 0 || s.yPos + SpiderPlayer.webYOffset + SpiderMain.height / 2 + s.height/2 > 2200){
        if (!(s.xPos - SpiderPlayer.webXOffset - SpiderMain.width / 2 + s.width/2 < 0 || s.xPos + SpiderPlayer.webXOffset + SpiderMain.width / 2 + s.width/2 > 11000)) {
            xOffset = s.xPos - SpiderPlayer.webXOffset - SpiderMain.width / 2 + s.width / 2;
        } else{
            return;
        }
        return;
    }
    xOffset = s.xPos - SpiderPlayer.webXOffset - SpiderMain.width / 2 + s.width/2;
    yOffset = s.yPos - SpiderPlayer.webYOffset - SpiderMain.height / 2 + s.height/2;
    
  }
  
  public void move(float xAmt, float yAmt) {
    setxOffset(getxOffset() + xAmt);
    setyOffset(getyOffset() + yAmt);
  }

  public float getyOffset() {
    return yOffset;
  }

  public void setyOffset(float yOffset) {
    this.yOffset = yOffset;
  }

  public float getxOffset() {
    return xOffset;
  }

  public void setxOffset(float xOffset) {
    this.xOffset = xOffset;
  }
}
