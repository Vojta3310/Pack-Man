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
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    System.out.println("Aplication started!");
    
    World map=new World();
    map.load("/home/vojta3310/NetBeansProjects/soutěž/src/Maps/mapa1.map");
    
    new GameGUI(map).setVisible(true);
  }
  
}
