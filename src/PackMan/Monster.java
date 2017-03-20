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
public class Monster extends GameObject implements IgameObject {
  private final int defaultX;
  private final int defaultY;
  private int health;
  private int score;
  private boolean Super;
  private World map;
  
  public Monster(int x, int y, ObjectType typ, World map) {
    super(x, y, typ);
    defaultX=x;
    defaultY=y;
    this.map=map;
  }
  
  public void hit(){
    this.x=defaultX;
    this.y=defaultY;
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
      if(obj.getTyp()==ObjectType.PackMan){
        if (map.getPackman().isSuper()){
          this.hit();
        }else{
          map.getPackman().hit();
          this.x=x;
          this.y=y;
        }
      }else if (obj.getTyp()!=ObjectType.Zed){
        this.x=x;
        this.y=y;
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


