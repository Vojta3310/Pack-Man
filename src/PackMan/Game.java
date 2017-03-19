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
public class Game {
  private GameStat stav=GameStat.NonStarted;
  private World mapa;
  
  public void Start(String level){
    mapa=new World(this);
    mapa.load("/home/vojta3310/NetBeansProjects/soutěž/src/Maps/mapa"+level+".map");
       // mapa.load("/home/bleha/mapa"+level+".map");
    stav=GameStat.Running;
  }

  public void Stop(){
    stav=GameStat.Finished;
  }
  
  public void Pause(){
    if (this.isRunning()){
      stav=GameStat.Paused;
    }else if (stav==GameStat.Paused){
      stav=GameStat.Running;
    }
  }
  
  public boolean isRunning() {
    return stav==GameStat.Running;
  }

  public World getMapa() {
    return mapa;
  }
  
  public void paint(java.awt.Canvas canvas){
    if (this.isRunning()) mapa.paint(canvas);
  }
}
