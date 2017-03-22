/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackMan;

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
  
  public PackMan(int x, int y, ObjectType typ, World map) {
    super(x, y, typ);
    defaultX=x;
    defaultY=y;
    health=3;
    score=0;
    Super=false;
    this.map=map;
  }
  
  public void hit(){
    this.x=defaultX;
    this.y=defaultY;
    if(this.health>1) {
      this.health--;
    }else{
      System.out.println("konec");
    }
  }
  
  private void moveNa(int x,int y){
    
    if (map.getObject(x, y)==null){
      this.x=x;
      this.y=y;
    }else{
      IgameObject obj=map.getObject(x, y);
      System.out.println(obj.getTyp());
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
            //timer start
          }
        break;
        case Monstr: {
            this.hit();
          }
      }   
    }
  }
  
  public void moveUp(){
    moveNa(x,y-1);
  }
  
  public void moveDown(){
    moveNa(x,y+1);
  }
  
  public void moveLeft(){
    moveNa(x+1,y);
  }
  
  public void moveRight(){
    moveNa(x-1,y);
  }
}
