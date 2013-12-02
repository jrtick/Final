package towerDefense;
import java.util.ArrayList;
import java.lang.Math.*;

import processing.core.*;

public class Tower {
	private PApplet parent;
	private int color1,color2,color3;
	private int radius,type;
	public int busy;
	public int cost,towerType;
	public int towerLocationX,towerLocationY;
	private int towerTypes=2;
	private int[] towerCosts = {200,400};
	public ArrayList<Integer> attacks;
	private Animation image;
	//private Sprite image;
	public Tower(PApplet parent,int type){
		this.parent=parent;
		this.radius=20;
		this.type=type;
		this.image=image;
		//if(type==0){this.image=new Animation(parent,"hunter");}
		//else{this.image=new Animation(parent,"mercenary");}
		this.color1=255;
		this.color2=255;
		this.color3=255;
		this.towerLocationX=0;
		this.towerLocationY=0;
		this.towerType=type;
		this.cost=(0+1)*200;
		this.newTower();
		this.busy=0;
		this.attacks=new ArrayList<>();
	}
	private void makeTower(float centerX,float centerY,float type){
		parent.stroke(255,255,255);
		if(type==0){
			parent.fill(this.color1,this.color2,this.color3);
			parent.rect(centerX-this.radius, centerY-5, radius*2, 10);
			parent.rect(centerX-5,centerY-this.radius,10,radius*2);
			parent.ellipse(centerX,centerY,this.radius,this.radius);
			parent.line(centerX, centerY, centerX-this.radius, centerY-this.radius);
		}
		if(type==1){
			parent.fill(this.color1,this.color2,this.color3);
			parent.beginShape();
			int numOfVertices=5;
			float angle=parent.PI/2;
			float xdisplace,ydisplace;
			for(int vertice=0;vertice<numOfVertices;vertice++){
				xdisplace=this.radius*parent.cos(angle-2*parent.PI*vertice/numOfVertices);
				ydisplace=this.radius*parent.sin(angle-2*parent.PI*vertice/numOfVertices);
				parent.vertex(centerX+xdisplace,centerY-ydisplace);
			}
			parent.endShape();
		}
	}
	public void newTower(){
		int centerX=parent.mouseX;
		int centerY=parent.mouseY;
		this.towerLocationX=centerX;
		this.towerLocationY=centerY;
	}
	public void printTowers(){
		if (this.towerLocationX!=0 && this.towerLocationY!=0){
			//this.image.display(parent.round(this.xposition), parent.round(this.yposition));
			makeTower(this.towerLocationX,this.towerLocationY,this.towerType);
		}
	}
	public boolean attack(ArrayList<Enemy> curEnemies){
		if(this.busy>0){return false;}
		else{
			int radius=parent.width/5;
			int damage=2;
			int timeToRecharge;
			if(this.type==0){timeToRecharge=100;}
			else{timeToRecharge=50;}
			boolean dead;
			int i=0;
			while(i<curEnemies.size()){
				float[] position = new float[2];
				float eX,eY,tX,tY;
				position=curEnemies.get(i).getPosition();
				eX=position[0];eY=position[1];
				tX=this.towerLocationX;tY=this.towerLocationY;
				double dist=Math.sqrt((eX-tX)*(eX-tX)+(eY-tY)*(eY-tY));
				if(dist<radius){
					this.attacks.add((int)curEnemies.get(i).xposition);
					this.attacks.add((int)curEnemies.get(i).yposition);
					dead=curEnemies.get(i).decreaseHealthBy(damage);
					if(dead){curEnemies.remove(i);}
					this.busy=timeToRecharge;
					return true;
				}
				i++;
			}
			return false;
		}
	}
}


