/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackMan;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author vojta3310
 */
public class GameObject implements IgameObject {
  int x; //pozice x
  int y; //pozyce y
  Image obrazek;
  ObjectType typ; //typ obektu mapy (zet packman jidlo ....)
  
  public GameObject(int x, int y, ObjectType typ){ //konstruktor: pri vytvoreni se ulozo promene
    this.x=x;
    this.y=y;
    this.typ=typ; 
    String obr="none";
    
    switch (typ) {
      case Zed :  
        obr="zed";
      break;
      case Jidlo:  
        obr="jidlo";
      break;
      case Sjidlo:  
        obr="sjidlo";
      break;
      case PackMan:  
        obr="PackMan";
      break;
      case Monstr:  
        obr="monstr";
      break;
    }
    
    //System.out.println("/home/vojta3310/NetBeansProjects/soutěž/src/Icons/"+obr+".png");
    try {
        //File pathToFile = new File("/home/vojta3310/NetBeansProjects/soutěž/src/Icons/"+obr+".png");
        this.obrazek = ImageIO.read(getClass().getResourceAsStream("/Icons/"+obr+".png"));
    } catch (IOException ex) {
        ex.printStackTrace();
    }

    //this.obrazek = Toolkit.getDefaultToolkit().getImage("/home/vojta3310/NetBeansProjects/soutěž/src/Icons"+obr+".png");
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public ObjectType getTyp() {
    return typ;
  }
  
  public Image getImage(){
    return obrazek;
  }
  
}
