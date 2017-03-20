/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackMan;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
  private int level=1;
  
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
  
  public void Win(){
    stav=GameStat.Win;
  }

  public void Lost(){
    stav=GameStat.Lost;
    level=1;
  }
  
  public void Start(int level){
    stav=GameStat.loading;
    PUp=false;
    PDown=false;
    PLeft=false;
    PRight=false;
    MUp=false;
    MDown=false;
    MLeft=false;
    MRight=false; 
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
      switch (stav) {
        case Lost :{
            stav=GameStat.loading;  
            this.Start(1);
          }
        break;
        case Win :{
            stav=GameStat.loading;
            level++;  
            this.Start(level);
          }   
        break;
        default: this.Pause();
      }
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
    if (mapa.isWon()&&(stav!=GameStat.loading)) stav=GameStat.Win;
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
    if (stav==GameStat.loading) return;
    if (this.isRunning()) mapa.paint(canvas);
    if (stav==GameStat.Paused) {
      int H=180;
      int W=400;
      BufferedImage a=new BufferedImage(W, H,BufferedImage.TYPE_4BYTE_ABGR);   
      Graphics g=a.getGraphics();
      g.setColor(Color.black);
      g.fillRect(0, 0, W, H);
      g.setColor(Color.red);
      g.drawRect(5, 5, W-11, H-11);
      g.drawRect(6, 6, W-13, H-13);
      g.drawRect(7, 7, W-15, H-15);
      g.drawRect(8, 8, W-17, H-17);
      g.setColor(Color.YELLOW);
      Font font = this.getFont().deriveFont((float) 50);
      g.setFont(font);
      FontMetrics fm = g.getFontMetrics();
      String t="Pause";
      g.drawString(t, (W-fm.stringWidth(t))/2,110);
      font = this.getFont().deriveFont((float) 20);
      g.setFont(font);
      g.setColor(Color.white);
      fm = g.getFontMetrics();
      t="Press space to continue!";
      g.drawString(t, (W-fm.stringWidth(t))/2,150); 
      mapa.paint(canvas);
      canvas.getGraphics().drawImage(a,(canvas.getWidth()-W)/2, (canvas.getHeight()-H)/2, W, H,null);
    }
    if (stav==GameStat.Lost) {
      int H=180;
      int W=400;
      BufferedImage a=new BufferedImage(W, H,BufferedImage.TYPE_4BYTE_ABGR);   
      Graphics g=a.getGraphics();
      g.setColor(Color.black);
      g.fillRect(0, 0, W, H);
      g.setColor(Color.red);
      g.drawRect(5, 5, W-11, H-11);
      g.drawRect(6, 6, W-13, H-13);
      g.drawRect(7, 7, W-15, H-15);
      g.drawRect(8, 8, W-17, H-17);
      g.setColor(Color.YELLOW);
      Font font = this.getFont().deriveFont((float) 50);
      g.setFont(font);
      FontMetrics fm = g.getFontMetrics();
      String t="You Lost";
      g.drawString(t, (W-fm.stringWidth(t))/2,110);
      font = this.getFont().deriveFont((float) 20);
      g.setFont(font);
      g.setColor(Color.white);
      fm = g.getFontMetrics();
      t="Press space to continue!";
      g.drawString(t, (W-fm.stringWidth(t))/2,150); 
      mapa.paint(canvas);
      canvas.getGraphics().drawImage(a,(canvas.getWidth()-W)/2, (canvas.getHeight()-H)/2, W, H,null);
    }
    if (stav==GameStat.Win) {
      int H=180;
      int W=400;
      BufferedImage a=new BufferedImage(W, H,BufferedImage.TYPE_4BYTE_ABGR);   
      Graphics g=a.getGraphics();
      g.setColor(Color.black);
      g.fillRect(0, 0, W, H);
      g.setColor(Color.red);
      g.drawRect(5, 5, W-11, H-11);
      g.drawRect(6, 6, W-13, H-13);
      g.drawRect(7, 7, W-15, H-15);
      g.drawRect(8, 8, W-17, H-17);
      g.setColor(Color.YELLOW);
      Font font = this.getFont().deriveFont((float) 50);
      g.setFont(font);
      FontMetrics fm = g.getFontMetrics();
      String t="You WON!!";
      g.drawString(t, (W-fm.stringWidth(t))/2,110);
      font = this.getFont().deriveFont((float) 20);
      g.setFont(font);
      g.setColor(Color.white);
      fm = g.getFontMetrics();
      t="Press space to continue!";
      g.drawString(t, (W-fm.stringWidth(t))/2,150); 
      mapa.paint(canvas);
      canvas.getGraphics().drawImage(a,(canvas.getWidth()-W)/2, (canvas.getHeight()-H)/2, W, H,null);
    }
  }
}
