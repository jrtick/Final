package towerDefense;

import processing.core.*;

public class Sprite {
	public static enum Verticality { HORIZONTAL, VERTICAL };
	private static final int LEFT = 0, CORNER = 1, CENTER = 2, RIGHT = 3, TOP = 4, BOTTOM = 5;
	
    private PApplet parent;
    private PImage sprite_strip, current_frame;
    private int originx, originy;
  
    private final int height, width, nframes;
    private float frame, speed, angle, xscale, yscale;
    private boolean vertical;
  
  Sprite(String filename, int nframes, int originx, int originy, Verticality v)
  {
	  if (v == Verticality.VERTICAL) vertical = true;
	  this.parent = parent;
	  this.nframes = nframes;
	  this.originx = originx;
	  this.originy = originy;
	  xscale = yscale = 2;
	  frame = 0;
	  speed = 0.25f;
	  angle = 0;
	  sprite_strip = parent.loadImage(filename);
	  
	  if (vertical) {
		  this.height = sprite_strip.height / nframes;
		  this.width = sprite_strip.width;
	  }
	  else {
		  this.height = sprite_strip.height;
		  this.width = sprite_strip.width/nframes;
	  }
	  
	  current_frame = sprite_strip.get(0, 0, this.width, this.height);
  }
  
  Sprite(String filename, int nframes, int originMode, Verticality v)
  {
    this(filename, nframes, 0, 0, v);
    if (originMode == BOTTOM) {
      originx = this.width/2;
      originy = this.height;
    } else if (originMode == CENTER) {
      originx = this.width/2;
      originy = this.height/2;
    } else if (originMode == LEFT) {
      originx = 0;
      originy = this.height/2;
    } else if (originMode == RIGHT) {
      originx = this.width;
      originy = this.height/2;
    } else if (originMode == TOP) {
      originx = this.width/2;
      originy = 0;
    }
  }
  
  Sprite(String filename, int nframes, Verticality v)
  {
    this(filename, nframes, CENTER, v);
  }
  
  void update()
  {
    frame += speed;
    while (frame > nframes)
      frame -= nframes;
    if (vertical == false)
    	current_frame = sprite_strip.get((parent.round(frame) % nframes) * this.width, 0, this.width, this.height);
    else 
    	current_frame = sprite_strip.get(0, (parent.round(frame) % nframes) * this.height, this.width, this.height);
  }
  
  void render(int x, int y, boolean flipHorizontal)
  {
    parent.pushMatrix();
    parent.translate(x, y);
    parent.rotate(angle);
    parent.scale(xscale, yscale);
    if (flipHorizontal) parent.scale(-1.0f, 1.0f);
    parent.image(current_frame, -originx, -originy);
    parent.popMatrix();
  }

}
