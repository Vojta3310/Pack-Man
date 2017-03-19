/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackMan;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vojta3310
 */
public class Game {
  private GameStat stav=GameStat.NonStarted;
  private World mapa;
  
  private boolean PUp=false;
  private boolean PDown=false;
  private boolean PLeft=false;
  private boolean PRight=false;
  private boolean MUp=false;
  private boolean MDown=false;
  private boolean MLeft=false;
  private boolean MRight=false; 
  
  private Font Font;
  
  public Game(){
    String tfName = "/Minecraftia-Regular.ttf";
    InputStream tf = getClass().getResourceAsStream(tfName); 
    try {
      Font= Font.createFont(Font.TRUETYPE_FONT, tf).deriveFont(Font.TRUETYPE_FONT, 12);
    } catch (FontFormatException ex) {
      Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void Start(String level){
    mapa=new World(this);
    mapa.load("/home/vojta3310/NetBeansProjects/soutěž/src/Maps/mapa"+level+".map");
       // mapa.load("/home/bleha/mapa"+level+".map");
    stav=GameStat.Running;
  }
  
  public void PressEvent(java.awt.event.KeyEvent evt){
    if(this.isRunning()){
      switch (evt.getExtendedKeyCode()) {
        case 37 :
        PRight=true;
        break;
        case 39:
        PLeft=true;
        break;
        case 38:
        PUp=true;
        break;
        case 40:
        PDown=true;
        break;
        case 65 :
        MRight=true;
        break;
        case 68:
        MLeft=true;
        break;
        case 87:
        MUp=true;
        break;
        case 83:
        MDown=true;
        break;
      }
    }
    if(evt.getExtendedKeyCode()==KeyEvent.VK_SPACE){
        this.Pause();
    }
  }
  
  public void UpEvent(java.awt.event.KeyEvent evt){
    if(this.isRunning()){
      switch (evt.getExtendedKeyCode()) {
        case 37 :
        PRight=false;
        break;
        case 39:
        PLeft=false;
        break;
        case 38:
        PUp=false;
        break;
        case 40:
        PDown=false;
        break;
        case 65 :
        MRight=false;
        break;
        case 68:
        MLeft=false;
        break;
        case 87:
        MUp=false;
        break;
        case 83:
        MDown=false;
        break;
      }
    }
  }
  
  public void update(){
    if (this.isRunning()){
      if (PRight) mapa.getPackman().moveRight();
      if (PLeft) mapa.getPackman().moveLeft();
      if (PUp) mapa.getPackman().moveUp();
      if (PDown) mapa.getPackman().moveDown();
      if (MRight) mapa.getMonster().moveRight();
      if (MLeft) mapa.getMonster().moveLeft();
      if (MUp) mapa.getMonster().moveUp();
      if (MDown) mapa.getMonster().moveDown();
    }
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

  public Font getFont() {
    return Font;
  }
  
  public void paint(java.awt.Canvas canvas){
    if (this.isRunning()) mapa.paint(canvas);
  }
}
