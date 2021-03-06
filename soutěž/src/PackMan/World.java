/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackMan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.rmi.Naming.list;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vojta3310
 */
public class World {
  private List<IgameObject> Objects; //vse co je ve hre
  private int sizeX;
  private int sizeY;
  private PackMan packman;
  private Monster monster;
  
  public World(){
   Objects= new ArrayList<>();
  }
  
  public void load(String file){
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      int y=0;
      while ((line = br.readLine()) != null) {
        int x=0;
        while(x<line.length()){
          System.out.println("loading "+x+line.charAt(x));
          switch (line.charAt(x)) {
            case 'x':  
              Objects.add(new GameObject(x,y,ObjectType.Zed));
            break;
            case '.':  
              Objects.add(new Food(x,y,ObjectType.Jidlo,10));
            break;
            case 'S':  
              Objects.add(new Food(x,y,ObjectType.Sjidlo,50));
            break;
            case 'P': { 
                packman=new PackMan(x,y,ObjectType.PackMan, this);
                Objects.add(packman);
              }
            break;
            case 'M':  
                monster=new Monster(x,y,ObjectType.Monstr, this);
                Objects.add(monster);
            break;
          }
        
          x++;
        }
        //System.out.println(x);
        sizeX=x-1;
        //System.out.println(sizeX);
        y++;
      }
      sizeY=y-1;
    } catch (IOException ex) {
      Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public boolean removeObject(IgameObject co, IgameObject kdo){
    if(((co.getTyp()==ObjectType.Jidlo)||(co.getTyp()==ObjectType.Sjidlo))&&(kdo.getTyp()==ObjectType.PackMan)){
      Objects.remove(co);
      return true;
    }
    return false;    
  }
  
  public IgameObject getObject(int x,int y){
    for(IgameObject object : Objects) {
        if((object.getX()==x)&&(object.getY()==y)) {
            return object;
        }
    }
    return null;
  }

  public int getSizeX() {
    return sizeX;
  }

  public int getSizeY() {
    return sizeY;
  }

  public PackMan getPackman() {
    return packman;
  }

  public Monster getMonster() {
    return monster;
  }
  
  public void paint(java.awt.Canvas canvas){
    System.out.println(canvas.getHeight());
    Graphics g=canvas.getGraphics();
    g.setColor(Color.black);
    g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    int imH=canvas.getHeight()/(sizeY+1);
    int imW=canvas.getWidth()/(sizeX+1);
    System.out.println(imW);
    for(IgameObject object : Objects) {
      System.out.println(object.getX()*imW);
      g.drawImage(object.getImage(), object.getX()*imW, object.getY()*imH, imW, imH, null);
    }
  }

}
