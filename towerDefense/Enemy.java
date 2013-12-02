package towerDefense;
import processing.core.*;
public class Enemy{
	private int minSpeed=5,maxSpeed=15;
	private int minRadius=10,maxRadius=40;
	private int objectRadius,velocity,health;
	public float xposition,yposition;
	private int color1,color2,color3;
	public int place=0;
	//private Sprite image;
	private PApplet parent;
	private Animation image;
	public Enemy(PApplet parent,int level){
		this.parent=parent;
		//this.image=new Animation(parent,"zombie");
		resetVariables(level);
	}
	private void resetVariables(int level){
		this.velocity=parent.floor(parent.random(minSpeed,maxSpeed));
		this.objectRadius=parent.floor(parent.random(minRadius,maxRadius));
		this.xposition=parent.width*3/16;
		this.yposition=0;
		this.color1=parent.floor(parent.random(0,255));
		this.color2=parent.floor(parent.random(0,255));
		this.color3=parent.floor(parent.random(0,255));
		this.health=((int)(level/4)+1)*2;
	}
	public boolean moveEnemy(float totalDistance){
		//find object location on board!
		float distanceTravelled=this.yposition+(this.xposition-parent.width*3/16);
		if(distanceTravelled>totalDistance){
			return true;
		}else if(distanceTravelled>parent.height/2 && distanceTravelled<totalDistance-parent.height/2){ //moving horizontally
			this.yposition=parent.height/2;
			this.xposition+=this.velocity;
		}else{ //if moving vertically
			this.yposition+=this.velocity;
		}
		return false;
	}
	public void printEnemy(){
		//this.image.display(parent.round(this.xposition), parent.round(this.yposition));
		parent.fill(this.color1, this.color2, this.color3);
		parent.ellipse(this.xposition, this.yposition, this.objectRadius, this.objectRadius); //place object on board
	}
	public float[] getPosition(){
		float[] temp=new float[2];
		temp[0]=this.xposition;
		temp[1]=this.yposition;
		return temp;
	}
	public boolean decreaseHealthBy(int num){
		this.health-=num;
		return (this.health<=0);
	}
}


