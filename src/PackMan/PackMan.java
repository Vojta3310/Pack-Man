/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackMan;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

/**
 *
 * @author vojta3310
 */
public class PackMan extends GameObject implements IgameObject {
  private final int defaultX;
  private final int defaultY;
  private int health;
  private int score;
  private boolean Super;
  private World map;
  private int lastdir;
  private Image Sobrazek;
  private Timer timer = new Timer();
  private Game game;
  
  public PackMan(int x, int y, ObjectType typ, World map, Game game) {
    super(x, y, typ);
    defaultX=x;
    defaultY=y;
    health=3;
    score=0;
    Super=false;
    this.map=map;
    lastdir=0;
    this.game=game;
    try {
      //File pathToFile = new File("/home/vojta3310/NetBeansProjects/soutěž/src/Icons/SPackMan.png");
      this.Sobrazek = ImageIO.read(getClass().getResourceAsStream("/Icons/SPackMan.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  public void hit(){
    this.x=defaultX;
    this.y=defaultY;
    if(this.health>1) {
      this.health--;
    }else{
      System.out.println("konec");
      game.Stop();
    }
  }
  
  @Override
  public Image getImage(){
    BufferedImage image;
    if (Super){
      image=new BufferedImage(Sobrazek.getWidth(null), Sobrazek.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
      image.getGraphics().drawImage(Sobrazek,0,0,image.getWidth(), image.getHeight(),null);
 
    }else{
      image=new BufferedImage(obrazek.getWidth(null), obrazek.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
      image.getGraphics().drawImage(obrazek,0,0,image.getWidth(), image.getHeight(),null);
    }

    double rotationRequired = Math.toRadians (lastdir);
    double locationX = image.getWidth() / 2;
    double locationY = image.getHeight() / 2;
    AffineTransform tx;
    if (lastdir==180){
      tx = AffineTransform.getScaleInstance(-1, 1);
      tx.translate(-image.getWidth(null), 0);
    }else{
      tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
    }
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    return op.filter(image, null);
  }
  
  private void moveNa(int x,int y){
    if(x>map.getSizeX()) x=0;
    if(y>map.getSizeY()) y=0;
    if(x<0) x=map.getSizeX();
    if(y<0) y=map.getSizeY();
    if (map.getObject(x, y)==null){
      this.x=x;
      this.y=y;
    }else{
      IgameObject obj=map.getObject(x, y);
      switch (obj.getTyp()) {
        case Jidlo: {
            this.x=x;
            this.y=y;
            this.score+=10;
            map.removeObject(obj, this);
          }
        break;
        case Sjidlo: {
            this.x=x;
            this.y=y;
            this.score+=50;
            this.Super=true; 
            map.removeObject(obj, this);
            timer.schedule(new TimerTask() {
              @Override
              public void run() {
                Super=false;
              }
            }, 10*1000);
          }
        break;
        case Monstr: {
            if (this.Super){
              map.getMonster().hit();
              this.score+=150;
              this.x=x;
              this.y=y;
            } else{ 
              this.hit();
            }
          }
      }   
    }
  }
  
  public void moveUp(){
    moveNa(x,y-1);
    lastdir=270;
  }
  
  public void moveDown(){
    moveNa(x,y+1);
    lastdir=90;
  }
  
  public void moveLeft(){
    moveNa(x+1,y);
    lastdir=0;
  }
  
  public void moveRight(){
    moveNa(x-1,y);
    lastdir=180;
  }

  public int getScore() {
    return score;
  }

  public int getHealth() {
    return health;
  }

  public boolean isSuper() {
    return Super;
  }
  
}
