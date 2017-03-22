/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackMan;

import java.awt.Image;

/**
 *
 * @author vojta3310
 */
public interface IgameObject {

  public int getX();

  public int getY();

  public ObjectType getTyp();
  
  public Image getImage();
  
}
