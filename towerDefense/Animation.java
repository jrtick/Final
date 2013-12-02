package towerDefense;
import processing.core.*;

public class Animation {
	public int xpos,ypos;
	private PImage picture;
	private PApplet parent;
	private int width,height,xOrigin,yOrigin;
	private static PImage zombie,hunter,mercenary;
	Animation(PApplet parent,String type){
		if(type=="zombie"){
			this.picture=zombie;
		}else if(type=="hunter"){
			this.picture=hunter;
		}else if(type=="mercenary"){
			this.picture=mercenary;
		}else{
			System.out.println(type);
		}
		this.width=this.picture.width;
		this.height=this.picture.height;
		this.xOrigin=this.width/2;
		this.yOrigin=this.height/2;
	}
	
	void display(int xpos, int ypos){
		this.xpos=xpos;
		this.ypos=ypos;
		parent.pushMatrix();
		parent.translate(this.xpos, this.ypos);
		parent.image(this.picture, -this.xOrigin,-this.yOrigin);
		parent.popMatrix();
	}
	
	static void loadAllImages(PImage zombie,PImage hunter,PImage mercenary){
		zombie=zombie;
		hunter=hunter;
		mercenary=mercenary;
	}
}
