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
public class Food extends GameObject implements IgameObject {
  private int energy; //mnozství dodaného jídla

  public int getEnergy() {
    return energy;
  }
  
  public Food(int x, int y, ObjectType typ, int energy) {
    super(x, y, typ);
    this.energy=energy;
  }
  
}
