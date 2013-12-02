package towerDefense;
import processing.core.*;
//import Minim.*;
import java.util.ArrayList;

public class playGame extends PApplet {
	public ArrayList<Enemy> myEnemies;
	public ArrayList<Tower> myTowers;
    float totalDistance;
    int score=0;
    int wait=0;
    int towerCost=200;
    int frames=60;
    boolean offBoard=false;
    int health=10,level=1;
    PImage zombie,hunter,mercenary;
    //Minim minim;
    //AudioPlayer player;
    //AudioInput input;
    
	void drawBoard(){ //CREATES THE PATHWAY OBJECTS WILL MOVE
		  stroke(117,117,117);
		  fill(117,117,117);//grey
		  rect(5,5,width-10,height-10);//board background
		  fill(255,217,0);//yellow
		  //Next three lines create yellow pathway
		  stroke(255,217,0);
		  rect(width/8,0,width/8, height*7/16); 
		  rect(width/8, height*7/16, width*6/8, height*2/16);
		  rect(width*6/8, height*9/16, width*1/8, height*7/16);
		  
	}
	
	public void setup(){
		  size(400,400);
		  totalDistance=width+height*5/8;
		  background(0,0,0); //black
		  ellipseMode(CENTER);
		  myEnemies = makeEnemies(level);
		  myTowers = new ArrayList<Tower>();
		  zombie=loadImage("zombie.png");hunter=loadImage("hunter.png");mercenary=loadImage("mercenary.png");
		  Animation.loadAllImages(zombie,hunter,mercenary); //load in all images
		  //minim = new Minim(this);
		  //player = minim.loadFile("music.mp3");
		  //input=minim.getLineIn();
		}
	public ArrayList<Enemy> makeEnemies(int level){ //return a list of enemies based on current level
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		int numEnemies=level*10;
		int placement=100-level*2;
		for (int i=0;i<numEnemies;i++){
			enemies.add(new Enemy(this,level));
			enemies.get(i).place=placement*(i+1);
		}
		return enemies;
	}
	public void draw(){ //game loop
		clear();
		totalDistance=width+height*5/8;
		if(frameCount%120==0){score+=100;} //increase score every 120th frame
		drawBoard();
		text("Health: "+str(health),width-100,50);
		text("score is "+str(score),width-100,20);
		text("level "+str(level),width-100,80);
		if(keyCode==16){towerCost=400;}
		else{towerCost=200;}
		if(keyPressed){ //USER INPUT!!
			if(score>=towerCost && (keyCode==16 || keyCode==0)){ //Two tower types!
				score-=towerCost;
				if(keyCode==16){ //shift!
					myTowers.add(new Tower(this,1));
				}else{ //spacebar!
					myTowers.add(new Tower(this,0));
				}
			//BELOW ARE SORT OF CHEAT CODES/TEST CONTROLS
			}else if(keyCode==38){ //up arrow creates enemies
				myEnemies.add(new Enemy(this,level));
			}else if(keyCode==37){ //left arrow increments score
				score+=100;
			}else if(keyCode==39){ //right arrow speeds up frame rate/ game
				frames+=1;
				frameRate(frames);
			}else if(keyCode==40){ //down arrow increments health!
				health+=1000;
			}
		}
		int i=0;
		while(i<myEnemies.size()){ //move enemies
			if(myEnemies.get(i).place==0){
				offBoard=myEnemies.get(i).moveEnemy(totalDistance);
			}else{offBoard=false;}
			if(offBoard){
				health-=1;
				myEnemies.remove(i);
			}
			else if(myEnemies.get(i).place==0){
				myEnemies.get(i).printEnemy(); //draw enemies
			}else{
				myEnemies.get(i).place-=1;
			}
			i++;
		}
		i=0;
		while(i<myTowers.size()){ //shoot at enemies
			myTowers.get(i).printTowers();
			myTowers.get(i).attack(myEnemies);
			if(myTowers.get(i).busy>0){myTowers.get(i).busy-=1;}
			if(myTowers.get(i).attacks.size()>0){
				int a=0;
				while (myTowers.get(i).attacks.size()>0){
					strokeWeight(10);
					stroke(0,255,0);//green
					line(myTowers.get(i).towerLocationX,myTowers.get(i).towerLocationY,myTowers.get(i).attacks.get(a),myTowers.get(i).attacks.get(a+1));
					myTowers.get(i).attacks.remove(a);myTowers.get(i).attacks.remove(a);
					strokeWeight(1);
					stroke(0,0,0);
				}
			}
			i++;
		}
		if(myEnemies.size()==0){//NEW ROUND IF ALL ENEMIES ARE DEAD
			level+=1;
			myEnemies=makeEnemies(level);
		}
		if(wait>0){wait-=1;}
		if(health<=0){Exit();}
	}
	public void Exit(){
		//player.close();
		//input.close();
		//minim.stop();
		exit();
	}
}



