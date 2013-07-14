package com.google.BattleNavigationSimulator.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import org.vaadin.gwtgraphics.client.*;
import org.vaadin.gwtgraphics.client.Image; // imports (not all of them needed i.e. circle and rectangle can prob be deleted now)

public class BattleNavigationSimulator implements EntryPoint {
	
	private VerticalPanel v1 = new VerticalPanel();
	private VerticalPanel v2 = new VerticalPanel();
	private HorizontalPanel h1 = new HorizontalPanel();
	private Label l1 = new Label("Program Control Panel");
	private TextBox t1 = new TextBox();
	private Button bEnemyMove = new Button("Enemy Move");
	private DrawingArea battleScreen = new DrawingArea(600,400);
	private DrawingArea battleHelm = new DrawingArea(500,300);
	/*private Image upToken = new Image(168,23,108,28,"http://25.media.tumblr.com/571fd5b5a12512ee1f3450d1a3337f07/tumblr_mgt69jyCdZ1rkyzquo1_250.png");
	private Image leftToken = new Image(208,57,108,28,"http://25.media.tumblr.com/820832a9e6e2fa1a104b6b24f7858445/tumblr_mgt6v5JX0R1rkyzquo1_250.png");
	private Image rightToken = new Image(208,91,108,28,"http://25.media.tumblr.com/3dfb9e6deca1e523299e6c64bee701ec/tumblr_mgt75gCSwO1rkyzquo1_r1_250.png");*/
	private Image battleControls = new Image(0,0,330,168,"http://24.media.tumblr.com/05b3b25d0fb292bf0ca4a4fb643cd00b/tumblr_mgt48rx08m1rkyzquo1_r1_400.jpg");
	//private Image gunToken = new Image(194,32,108,11,"http://25.media.tumblr.com/985e44e0631144496b51d9aa9bbc895e/tumblr_mgt9amq0Kt1rkyzquo1_250.png");
	//private Image grappleToken = new Image(238,31,108,11,"http://25.media.tumblr.com/e193c96e49e78e879b8c226e1b99f43a/tumblr_mgt9mdmrq41rkyzquo1_250.png");
	
	//the previous code was for testing purposes, disregard
	
	private Image move1,move2,move3,move4;
	private Image action1,action2,action3,action4,action5,action6,action7,action8;
	private int move1Counter=0, move2Counter=0, move3Counter=0, move4Counter=0;
	private int action1Counter=0, action2Counter=0, action3Counter=0, action4Counter=0,
			action5Counter=0,action6Counter=0,action7Counter=0,action8Counter=0;
	
	//All important for user-interface (Clicking the helm results in moves appearing, similar to the similar I showed you earlier)

	private String[] moveTokens = {"http://25.media.tumblr.com/820832a9e6e2fa1a104b6b24f7858445/tumblr_mgt6v5JX0R1rkyzquo1_250.png","http://24.media.tumblr.com/bf3cb2ddeeb78938ecfa2737cf81c1b5/tumblr_mgt69jyCdZ1rkyzquo1_r1_250.png", "http://25.media.tumblr.com/3dfb9e6deca1e523299e6c64bee701ec/tumblr_mgt75gCSwO1rkyzquo1_r1_250.png","http://24.media.tumblr.com/7488f0db06950af108f2f0340fa69b28/tumblr_mgt8d4dmsc1rkyzquo1_r1_250.png"};
	private String[] actionTokens = {"http://25.media.tumblr.com/985e44e0631144496b51d9aa9bbc895e/tumblr_mgt9amq0Kt1rkyzquo1_250.png","http://24.media.tumblr.com/eb97ee98d557df4ae0fd23371eadddbc/tumblr_mgt9mdmrq41rkyzquo1_r2_250.png","http://24.media.tumblr.com/7dbb16aec8753cf2a7c48ee256bfafd0/tumblr_mgtaad5S7V1rkyzquo1_250.png"};
	
	//should have set these urls to strings like leftTurnURL = " "; to make the actual array neater. This array is used to bring up move displays on the helm

	public Group myShip = new Group();
	public Group enemyShip = new Group();
	
	//Groups are simply like arrays of objects. In each "myShip" there are three lines, in this way we can remove and add ships as one unit rather than three lines.
	
	private String shipColor = "blue";
	//put down yoru ship first on the actual UI
	private int myShipCoordinateX=0,myShipCoordinateY=0;
	private Button myShipButton = new Button("Place Your Ship");
	private int myShipTurnCount = 399;
	private int enemyShipCoordinateX=0,enemyShipCoordinateY=0;
	private Button enemyShipButton = new Button("Place Enemy Ship");
	private int enemyShipTurnCount=399;
	private Button calculateButton = new Button("Calculate Next Move");
	int bestMoveNumber=3;
	int enemyShipRandomMove=3;
	private int hitRating=0;
	private Button makeMove = new Button("Make Move");
	private Button clearButton = new Button("Clear Screen");
	HandlerRegistration handlerList;
	//super important. whenever you add a handler that you want to remove, you must put it in here
	public class MoveToken{
		int myXModifier,myYModifier,myTurn;
		public MoveToken(int xModifier, int yModifier, int turn){
			myXModifier=xModifier;
			myYModifier=yModifier;
			myTurn=turn;
		}
		public int getXModifier(){
			return myXModifier;
		}
		public int getYModifier(){
			return myYModifier;
		}
		public int getTurn(){
			return myTurn;
		}
		public void negateX(){
			myXModifier=0;
		}
		public void negateY(){
			myYModifier=0;
		}
		public void negateTurn(){
			myTurn=0;
		}
	}//this class simply creates "move tokens" or ordered pairs. it helps with coding.
	
	class Ship{
		
		Group thisShip = new Group();
		int myXCoordinate, myYCoordinate, myMyShipTurnCount;
		
		public Ship(int XCoordinate, int YCoordinate, int shipTurnCount){
			myXCoordinate = XCoordinate;
			myYCoordinate = YCoordinate;
			myMyShipTurnCount = shipTurnCount;
		}
		
		public int[] makeMove(int myMove, int enemyMove){
			int[] result = {0,0,0,0,0,0};
			
			int myShipXi,myShipYi,enemyShipXi,enemyShipYi;
			int myShipCoordinateX2=myShipCoordinateX;
			int myShipCoordinateY2=myShipCoordinateY;
			int myShipTurnCount2 = myShipTurnCount;
			int enemyShipCoordinateX2=enemyShipCoordinateX;
			int enemyShipCoordinateY2=enemyShipCoordinateY;
			int enemyShipTurnCount2 = enemyShipTurnCount;

			MoveToken[][] enemyShipMoves = moveTokenDirections.get(enemyShipTurnCount%4);
			MoveToken[][] myShipMoves = moveTokenDirections.get(myShipTurnCount%4);
			MoveToken[] enemyShipMove = enemyShipMoves[enemyMove];
			MoveToken[] myShipMove = myShipMoves[myMove];
			for(int ae=0;ae<2;ae++){
				if(ae==1&&collision){break;}
				myShipXi = myShipCoordinateX2;
				myShipYi = myShipCoordinateY2;
				enemyShipXi = enemyShipCoordinateX2;
				enemyShipYi = enemyShipCoordinateY2;
				int enemyShipX = enemyShipMove[ae].getXModifier();
				int enemyShipY = enemyShipMove[ae].getYModifier();
				int enemyShipTurnTemp = enemyShipMove[ae].getTurn();
				int myShipX = myShipMove[ae].getXModifier();
				int myShipY = myShipMove[ae].getYModifier();
				int myShipTurnTemp = myShipMove[ae].getTurn();
				enemyShipCoordinateX2 += enemyShipX;
				enemyShipCoordinateY2 += enemyShipY;
				enemyShipTurnCount2 += enemyShipTurnTemp;
				myShipCoordinateX2 += myShipX;
				myShipCoordinateY2 += myShipY;
				myShipTurnCount2 += myShipTurnTemp;
				boolean sameDestination = myShipCoordinateX2==enemyShipCoordinateX2&&myShipCoordinateY2==enemyShipCoordinateY2;
				if (sameDestination){
					collision = true;
					myShipTurnCount2 -= myShipTurnTemp;
					enemyShipTurnCount2 -= enemyShipTurnTemp;
					MoveToken[][] enemyShipCollides = collisionTokenDirections.get(enemyShipTurnCount2%4);
					MoveToken[][] myShipCollides = collisionTokenDirections.get(myShipTurnCount2%4);
					MoveToken myShipCollide[] = myShipCollides[myMove];
					MoveToken enemyShipCollide[] = enemyShipCollides[enemyMove];
					myShipCoordinateX2 -= myShipX;
					myShipCoordinateY2 -= myShipY;
					enemyShipCoordinateX2 -= enemyShipX;
					enemyShipCoordinateY2 -= enemyShipY;
					if(myMove==1&&enemyMove==3){enemyShipCollide=myShipMove;}
					else if(enemyMove==1&&myMove==3){myShipCollide=enemyShipMove;}
					myShipX = myShipCollide[ae].getXModifier();
					myShipY = myShipCollide[ae].getYModifier();
					enemyShipX = enemyShipCollide[ae].getXModifier();
					enemyShipY = enemyShipCollide[ae].getYModifier();
					myShipTurnTemp = myShipCollide[ae].getTurn();
					myShipCoordinateX2 += myShipX;
					myShipCoordinateY2 += myShipY;
					myShipTurnCount2 += myShipTurnTemp;	
					enemyShipTurnTemp = enemyShipCollide[ae].getTurn();
					enemyShipCoordinateX2 += enemyShipX;
					enemyShipCoordinateY2 += enemyShipY;
					enemyShipTurnCount2 += enemyShipTurnTemp;	
				}
				boolean doubleReplace = (myShipXi==enemyShipCoordinateX2&&myShipYi==enemyShipCoordinateY2)&&(myShipCoordinateX2==enemyShipXi&&myShipCoordinateY2==enemyShipYi);
				if(doubleReplace&&!collision){
					collision=true;
					myShipTurnCount2 -= myShipTurnTemp;
					enemyShipTurnCount2 -= enemyShipTurnTemp;
					MoveToken[][] enemyShipCollides = collisionTokenDirections.get(enemyShipTurnCount2%4);
					MoveToken[][] myShipCollides = collisionTokenDirections.get(myShipTurnCount2%4);
					MoveToken[] myShipCollide = myShipCollides[myMove];
					MoveToken[] enemyShipCollide = enemyShipCollides[enemyMove];
					myShipCoordinateX2 -= myShipX;
					myShipCoordinateY2 -= myShipY;
					enemyShipCoordinateX2 -= enemyShipX;
					enemyShipCoordinateY2 -= enemyShipY;
					myShipX = myShipCollide[ae].getXModifier();
					myShipY = myShipCollide[ae].getYModifier();
					enemyShipX = enemyShipCollide[ae].getXModifier();
					enemyShipY = enemyShipCollide[ae].getYModifier();
					myShipTurnTemp = myShipCollide[ae].getTurn();
					myShipCoordinateX2 += myShipX;
					myShipCoordinateY2 += myShipY;
					myShipTurnCount2 += myShipTurnTemp;	
					enemyShipTurnTemp = enemyShipCollide[ae].getTurn();
					enemyShipCoordinateX2 += enemyShipX;
					enemyShipCoordinateY2 += enemyShipY;
					enemyShipTurnCount2 += enemyShipTurnTemp;
				}
			}
			collision = false;
			result[0] = myShipCoordinateX2;
			result[1] = myShipCoordinateY2;
			result[2] = myShipTurnCount2;
			result[3] = enemyShipCoordinateX2;
			result[4] = enemyShipCoordinateY2;
			result[5] = enemyShipTurnCount2;
			return result;
		}
		
		public Group drawShip(){
			if (myMyShipTurnCount%4==0){
				myXCoordinate = myXCoordinate/50*50+25;
				myYCoordinate = myYCoordinate/50*50+5;
				Line shipBody = new Line(myXCoordinate,myYCoordinate,myXCoordinate,myYCoordinate+40);
				shipBody.setStrokeColor(shipColor);
				shipBody.setStrokeWidth(4);
				Line shipLeftHead = new Line(myXCoordinate-10,myYCoordinate+10,myXCoordinate,myYCoordinate);
				shipLeftHead.setStrokeColor(shipColor);
				shipLeftHead.setStrokeWidth(4);
				Line shipRightHead = new Line(myXCoordinate+10,myYCoordinate+10,myXCoordinate,myYCoordinate);
				shipRightHead.setStrokeColor(shipColor);
				shipRightHead.setStrokeWidth(4);
				thisShip.add(shipBody);
				thisShip.add(shipLeftHead);
				thisShip.add(shipRightHead);
				return thisShip;
			}
			else if (myMyShipTurnCount%4==1){
				myXCoordinate = myXCoordinate/50*50+5;
				myYCoordinate = myYCoordinate/50*50+25;
				Line shipBody = new Line(myXCoordinate,myYCoordinate,myXCoordinate+40,myYCoordinate);
				shipBody.setStrokeColor(shipColor);
				shipBody.setStrokeWidth(4);
				Line shipLeftHead = new Line(myXCoordinate+30,myYCoordinate-10,myXCoordinate+40,myYCoordinate);
				shipLeftHead.setStrokeColor(shipColor);
				shipLeftHead.setStrokeWidth(4);
				Line shipRightHead = new Line(myXCoordinate+30,myYCoordinate+10,myXCoordinate+40,myYCoordinate);
				shipRightHead.setStrokeColor(shipColor);
				shipRightHead.setStrokeWidth(4);
				thisShip.add(shipBody);
				thisShip.add(shipLeftHead);
				thisShip.add(shipRightHead);
				return thisShip;
			}
			else if(myMyShipTurnCount%4==2){
				myXCoordinate = myXCoordinate/50*50+25;
				myYCoordinate = myYCoordinate/50*50+5;
				Line shipBody = new Line(myXCoordinate,myYCoordinate,myXCoordinate,myYCoordinate+40);
				shipBody.setStrokeColor(shipColor);
				shipBody.setStrokeWidth(4);
				Line shipLeftHead = new Line(myXCoordinate-10,myYCoordinate+30,myXCoordinate,myYCoordinate+40);
				shipLeftHead.setStrokeColor(shipColor);
				shipLeftHead.setStrokeWidth(4);
				Line shipRightHead = new Line(myXCoordinate+10,myYCoordinate+30,myXCoordinate,myYCoordinate+40);
				shipRightHead.setStrokeColor(shipColor);
				shipRightHead.setStrokeWidth(4);
				thisShip.add(shipBody);
				thisShip.add(shipLeftHead);
				thisShip.add(shipRightHead);
				return thisShip;
			}
			else{
				myXCoordinate = myXCoordinate/50*50+5;
				myYCoordinate = myYCoordinate/50*50+25;
				Line shipBody = new Line(myXCoordinate,myYCoordinate,myXCoordinate+40,myYCoordinate);
				shipBody.setStrokeColor(shipColor);
				shipBody.setStrokeWidth(4);
				Line shipLeftHead = new Line(myXCoordinate+10,myYCoordinate-10,myXCoordinate,myYCoordinate);
				shipLeftHead.setStrokeColor(shipColor);
				shipLeftHead.setStrokeWidth(4);
				Line shipRightHead = new Line(myXCoordinate+10,myYCoordinate+10,myXCoordinate,myYCoordinate);
				shipRightHead.setStrokeColor(shipColor);
				shipRightHead.setStrokeWidth(4);
				thisShip.add(shipBody);
				thisShip.add(shipLeftHead);
				thisShip.add(shipRightHead);
				return thisShip;
			}
		}
	}
	private MoveToken[][] NORTHmovetokens = {{new MoveToken(0,-1,-1),new MoveToken(-1,0,0)}, {new MoveToken(0,-1,0),new MoveToken(0,0,0)},
			{new MoveToken(0,-1,1),new MoveToken(1,0,0)}, {new MoveToken(0,0,0),new MoveToken(0,0,0)}};
	private MoveToken[][] NORTHCollisionmovetokens = {{new MoveToken(0,0,-1), new MoveToken(0,0,0)}, {new MoveToken(0,0,0), new MoveToken(0,0,0)},
			{new MoveToken(0,0,1), new MoveToken(0,0,0)}, {new MoveToken(0,0,0), new MoveToken(0,0,0)}};
	private MoveToken[] NORTHObstructionmovetokens = {new MoveToken(0,0,-1), new MoveToken(0,0,0), new MoveToken(0,0,1),
			new MoveToken(0,0,0)};
	private MoveToken[][] EASTmovetokens = {{new MoveToken(1,0,-1),new MoveToken(0,-1,0)}, {new MoveToken(1,0,0),new MoveToken(0,0,0)},
			{new MoveToken(1,0,1),new MoveToken(0,1,0)}, {new MoveToken(0,0,0),new MoveToken(0,0,0)}};
	private MoveToken[][] EASTCollisionmovetokens = {{new MoveToken(0,0,-1), new MoveToken(0,0,0)}, {new MoveToken(0,0,0), new MoveToken(0,0,0)}, 
			{new MoveToken(0,0,1), new MoveToken(0,0,0)}, {new MoveToken(0,0,0), new MoveToken(0,0,0)}};
	private MoveToken[] EASTObstructionmovetokens = {new MoveToken(0,0,-1),new MoveToken(0,0,0),
			new MoveToken(0,0,1),new MoveToken(0,0,0)};
	private MoveToken[][] SOUTHmovetokens = {{new MoveToken(0,1,-1),new MoveToken(1,0,0)}, {new MoveToken(0,1,0),new MoveToken(0,0,0)},
			{new MoveToken(0,1,1),new MoveToken(-1,0,0)}, {new MoveToken(0,0,0),new MoveToken(0,0,0)}};
	private MoveToken[][] SOUTHCollisionmovetokens = {{new MoveToken(0,0,-1), new MoveToken(0,0,0)},{new MoveToken(0,0,0), new MoveToken(0,0,0)}, 
			{new MoveToken(0,0,1), new MoveToken(0,0,0)},{ new MoveToken(0,0,0), new MoveToken(0,0,0)}};
	private MoveToken[] SOUTHObstructionmovetokens = {new MoveToken(0,0,-1), new MoveToken(0,0,0),
			new MoveToken(0,0,1),new MoveToken(0,0,0)};
	private MoveToken[][] WESTCollisionmovetokens = {{new MoveToken(0,0,-1), new MoveToken(0,0,0)},{new MoveToken(0,0,0), new MoveToken(0,0,0)}, 
			{new MoveToken(0,0,1), new MoveToken(0,0,0)},{new MoveToken(0,0,0), new MoveToken(0,0,0)}};
	private MoveToken[] WESTObstructionmovetokens = {new MoveToken(0,0,-1),new MoveToken(0,0,0),
			new MoveToken(0,0,1),new MoveToken(0,0,0)};
	private MoveToken[][] WESTmovetokens = {{new MoveToken(-1,0,-1),new MoveToken(0,1,0)}, {new MoveToken(-1,0,0),new MoveToken(0,0,0)},
			{new MoveToken(-1,0,1),new MoveToken(0,-1,0)}, {new MoveToken(0,0,0),new MoveToken(0,0,0)}};
	private List<MoveToken[][]> moveTokenDirections = new ArrayList<MoveToken[][]>();
	private List<MoveToken[][]> collisionTokenDirections = new ArrayList<MoveToken[][]>();
	private List<MoveToken[]> obstructionTokenDirections = new ArrayList<MoveToken[]>();
	private boolean collision = false;
	
	//arrays of move tokens. the four movetokens are as follows in this order: Left, Forward, Right, Nothing. Each is different depending on initial orientation
	
	private List<Integer> moveRecommendation= new ArrayList<Integer>(); //This should be cleared each time calculation is brought to screen to allow for more calculations to occur.
	
	
	//(main)
	public void onModuleLoad() { 
		moveTokenDirections.add(NORTHmovetokens);
		moveTokenDirections.add(EASTmovetokens);
		moveTokenDirections.add(SOUTHmovetokens);
		moveTokenDirections.add(WESTmovetokens); //non-static loading of array
		collisionTokenDirections.add(NORTHCollisionmovetokens);
		collisionTokenDirections.add(EASTCollisionmovetokens);
		collisionTokenDirections.add(SOUTHCollisionmovetokens);
		collisionTokenDirections.add(WESTCollisionmovetokens);
		obstructionTokenDirections.add(NORTHObstructionmovetokens);
		obstructionTokenDirections.add(EASTObstructionmovetokens);
		obstructionTokenDirections.add(WESTObstructionmovetokens);
		obstructionTokenDirections.add(SOUTHObstructionmovetokens);
		for(int ai = 0; ai<30; ai++){
			Line gridLineX = new Line(ai*50,0,ai*50,600);
			battleScreen.add(gridLineX);
			Line gridLineY = new Line(0, ai*50,600, ai*50);
			battleScreen.add(gridLineY);
		} //creates grid
		
		battleHelm.add(battleControls); //image i want to say
		v1.add(battleScreen); //the grid that you see
		
		handlerList = battleScreen.addMouseDownHandler(new MouseDownHandler(){ //this simply lets you create ships. I'm currently working on code to simplify this
	
			@Override
			public void onMouseDown(MouseDownEvent e){
				myShip.clear();
				myShipTurnCount++;
				if(myShipTurnCount%4==0){
					myShipCoordinateX = e.getX()/50;
					myShipCoordinateY = e.getY()/50;
					Ship temp = new Ship(e.getX(),e.getY(),myShipTurnCount);		
					myShip = temp.drawShip();						
				}
				else if(myShipTurnCount%4==1){
					myShipCoordinateX = e.getX()/50;
					myShipCoordinateY = e.getY()/50;
					Ship temp = new Ship(e.getX(),e.getY(),myShipTurnCount);		
					myShip = temp.drawShip();	
				}
				else if(myShipTurnCount%4==2){
					myShipCoordinateX = e.getX()/50;
					myShipCoordinateY = e.getY()/50;
					Ship temp = new Ship(e.getX(),e.getY(),myShipTurnCount);		
					myShip = temp.drawShip();	
				}
				else if(myShipTurnCount%4==3){
					myShipCoordinateX = e.getX()/50;
					myShipCoordinateY = e.getY()/50;
					Ship temp = new Ship(e.getX(),e.getY(),myShipTurnCount);		
					myShip = temp.drawShip();	
				}					
				battleScreen.remove(myShip);
				battleScreen.add(myShip);
			}
		});
		
		battleHelm.addMouseDownHandler(new MouseDownHandler(){ //allows you to click to create yoru OWN moves. later we can have an interface which allows the computer to rate the move that is currently being fed.
	
			@Override
			public void onMouseDown(MouseDownEvent e){
				if(e.getX()>207&&e.getX()<236){
					if(e.getY()>22&&e.getY()<52){
						move1 = new Image(208,23,108,28,moveTokens[move1Counter%4]);
						bestMoveNumber= move1Counter%4;
						battleHelm.add(move1);
						move1Counter++;
					}
					else if(e.getY()>56&&e.getY()<85){
						move2 = new Image(208,57,108,28,moveTokens[move2Counter%4]);
						battleHelm.add(move2);
						move2Counter++;
					}
					else if(e.getY()>90&&e.getY()<109){
						move3 = new Image(208,91,108,28,moveTokens[move3Counter%4]);
						battleHelm.add(move3);
						move3Counter++;
					}
					else if(e.getY()>124&&e.getY()<142){
						move4 = new Image(208,125,108,28,moveTokens[move4Counter%4]);
						battleHelm.add(move4);
						move4Counter++;
					}
				}
				else if(e.getX()>193&&e.getX()<207){
					if(e.getY()>31&&e.getY()<44){
						action1 = new Image(194,32,108,11,actionTokens[action1Counter%3]);
						battleHelm.add(action1);
						action1Counter++;
					}
					else if(e.getY()>67&&e.getY()<74){
						action2 = new Image(194,66,108,11,actionTokens[action2Counter%3]);
						battleHelm.add(action2);
						action2Counter++;
					}
					else if(e.getY()>101&&e.getY()<115){
						action3 = new Image(194,100,108,11,actionTokens[action3Counter%3]);
						battleHelm.add(action3);
						action3Counter++;
					}	
					else if(e.getY()>135&&e.getY()<147){
						action4 = new Image(194,134,108,11,actionTokens[action4Counter%3]);
						battleHelm.add(action4);
						action4Counter++;
					}	
				}
				else if(e.getX()>237&&e.getX()<251){
					if(e.getY()>31&&e.getY()<44){
						action5 = new Image(238,32,108,11,actionTokens[action5Counter%3]);
						battleHelm.add(action5);
						action5Counter++;
					}
					else if(e.getY()>67&&e.getY()<74){
						action6 = new Image(238,66,108,11,actionTokens[action6Counter%3]);
						battleHelm.add(action6);
						action6Counter++;
					}
					else if(e.getY()>101&&e.getY()<115){
						action7 = new Image(238,100,108,11,actionTokens[action7Counter%3]);
						battleHelm.add(action7);
						action7Counter++;
					}	
					else if(e.getY()>135&&e.getY()<147){
						action8 = new Image(238,134,108,11,actionTokens[action8Counter%3]);
						battleHelm.add(action8);
						action8Counter++;
					}	
				}
			}
		});
		
		clearButton.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent e){
				battleScreen.remove(myShip);
				battleScreen.remove(enemyShip);
				myShip.clear();
				enemyShip.clear();
			}
		}); //needs to also clear all arrays. Working on it.
	
		//FROM HERE TO
		
		myShipButton.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent e){
				shipColor="blue";
				
				handlerList.removeHandler();
				handlerList = battleScreen.addMouseDownHandler(new MouseDownHandler(){
					@Override
					public void onMouseDown(MouseDownEvent g){
						myShip.clear();
						myShipTurnCount++;

						if(myShipTurnCount%4==0){
							myShipCoordinateX = g.getX()/50;
							myShipCoordinateY = g.getY()/50;
							Ship temp = new Ship(g.getX(),g.getY(),myShipTurnCount);		
							myShip = temp.drawShip();	
						}
						else if(myShipTurnCount%4==1){
							myShipCoordinateX = g.getX()/50;
							myShipCoordinateY = g.getY()/50;
							Ship temp = new Ship(g.getX(),g.getY(),myShipTurnCount);		
							myShip = temp.drawShip();	
						}
						else if(myShipTurnCount%4==2){	
							myShipCoordinateX = g.getX()/50;
							myShipCoordinateY = g.getY()/50;
							Ship temp = new Ship(g.getX(),g.getY(),myShipTurnCount);		
							myShip = temp.drawShip();	
						}
						else if(myShipTurnCount%4==3){	
							myShipCoordinateX = g.getX()/50;
							myShipCoordinateY = g.getY()/50;
							Ship temp = new Ship(g.getX(),g.getY(),myShipTurnCount);		
							myShip = temp.drawShip();	
						}	
						battleScreen.remove(myShip);
						battleScreen.add(myShip);
					}
			});
			}
		});
		enemyShipButton.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent e){
				shipColor = "red";
				handlerList.removeHandler();
				handlerList = battleScreen.addMouseDownHandler(new MouseDownHandler(){
					@Override
					public void onMouseDown(MouseDownEvent f){
						enemyShip.clear();
						enemyShipTurnCount++;

						if(enemyShipTurnCount%4==0){	
							enemyShipCoordinateX = f.getX()/50;
							enemyShipCoordinateY = f.getY()/50;
							Ship temp = new Ship(f.getX(),f.getY(),enemyShipTurnCount);		
							enemyShip = temp.drawShip();	
						}
						else if(enemyShipTurnCount%4==1){	
							enemyShipCoordinateX = f.getX()/50;
							enemyShipCoordinateY = f.getY()/50;
							Ship temp = new Ship(f.getX(),f.getY(),enemyShipTurnCount);		
							enemyShip = temp.drawShip();	
						}
						else if(enemyShipTurnCount%4==2){		
							enemyShipCoordinateX = f.getX()/50;
							enemyShipCoordinateY = f.getY()/50;
							Ship temp = new Ship(f.getX(),f.getY(),enemyShipTurnCount);		
							enemyShip = temp.drawShip();	
						}
						else if(enemyShipTurnCount%4==3){		
							enemyShipCoordinateX = f.getX()/50;
							enemyShipCoordinateY = f.getY()/50;
							Ship temp = new Ship(f.getX(),f.getY(),enemyShipTurnCount);		
							enemyShip = temp.drawShip();	
						}	
						battleScreen.remove(enemyShip);
						battleScreen.add(enemyShip);
					}
			});
			}
		});
		// HERE IS ALL JUST ALLOWING FOR ALTERNATION OF RED AND BLUE COLORED SHIPS. FUCKIN RIDICULOUS YO
	
	

		calculateButton.addMouseDownHandler(new MouseDownHandler(){ //note did not add to handler because i do NOT want this listener to ever go away
			@Override
			public void onMouseDown(MouseDownEvent e){
				Ship x = new Ship(1,1,1);	
				int gunMin,gunMax; 
				
				int[] finalPosition = null;
	
				for (int bi=0;bi<4;bi++){
					int enemyShipCoordinateXS1 = enemyShipCoordinateX; 
					int enemyShipCoordinateYS1 = enemyShipCoordinateY;
					int enemyShipTurnCountS1 = enemyShipTurnCount;
					for(int ci=0;ci<4;ci++){
						int myShipCoordinateXS = myShipCoordinateX; 
						int myShipCoordinateYS = myShipCoordinateY;
						int myShipTurnCountS = myShipTurnCount;
						finalPosition = x.makeMove(bi,ci);


						if(finalPosition[2]%4==0||finalPosition[2]%4==2){ //if our ship is facing N or S
							gunMin = finalPosition[0]-3;
							gunMax = finalPosition[0]+3;
							if(finalPosition[1]==finalPosition[4]&&(finalPosition[3]>=gunMin&&
									finalPosition[3]<=gunMax&&finalPosition[3]!=finalPosition[0])){
								hitRating++; //every time enemy is in range, hitRating increases
							}
						}
						else if(finalPosition[2]%4==1||finalPosition[2]%4==3){ //if our ship is facing E or W
							gunMin = finalPosition[1]-3;
							gunMax = finalPosition[1]+3;
							if(finalPosition[0]==finalPosition[3]&&(finalPosition[4]>=gunMin&&
									finalPosition[4]<=gunMax&&finalPosition[4]!=finalPosition[1])){
								hitRating++;
							}
						}
						System.out.println();
						
						for(int ei=0;ei<6;ei++){
							System.out.print(finalPosition[ei]+" ");
						}
						System.out.print(hitRating);
						enemyShipCoordinateX = finalPosition[3];
						enemyShipCoordinateY = finalPosition[4];
						enemyShipTurnCount = finalPosition[5];
						int enemyShipCoordinateXS2 = enemyShipCoordinateX; 
						int enemyShipCoordinateYS2 = enemyShipCoordinateY;
						int enemyShipTurnCountS2 = enemyShipTurnCount;
						for(int ci2=0;ci2<4;ci2++){
							myShipCoordinateX = finalPosition[0];
							myShipCoordinateY = finalPosition[1];
							myShipTurnCount = finalPosition[2];
							finalPosition = x.makeMove(3,ci2);
							if(finalPosition[2]%4==0||finalPosition[2]%4==2){ //if our ship is facing N or S
								gunMin = finalPosition[0]-3;
								gunMax = finalPosition[0]+3;
								if(finalPosition[1]==finalPosition[4]&&(finalPosition[3]>=gunMin&&
										finalPosition[3]<=gunMax&&finalPosition[3]!=finalPosition[0])){
									hitRating++; //every time enemy is in range, hitRating increases
								}
							}
							else if(finalPosition[2]%4==1||finalPosition[2]%4==3){ //if our ship is facing E or W
								gunMin = finalPosition[1]-3;
								gunMax = finalPosition[1]+3;
								if(finalPosition[0]==finalPosition[3]&&(finalPosition[4]>=gunMin&&
										finalPosition[4]<=gunMax&&finalPosition[4]!=finalPosition[1])){
									hitRating++;
								}
							}
							System.out.println();
							for(int ei=0;ei<6;ei++){
								System.out.print(finalPosition[ei]+" ");
							}
							System.out.print(hitRating);
							enemyShipCoordinateX = finalPosition[3];
							enemyShipCoordinateY = finalPosition[4];
							enemyShipTurnCount = finalPosition[5];
							int enemyShipCoordinateXS3 = enemyShipCoordinateX; 
							int enemyShipCoordinateYS3 = enemyShipCoordinateY;
							int enemyShipTurnCountS3 = enemyShipTurnCount;
							for(int ci3=0;ci3<4;ci3++){
								
								myShipCoordinateX = finalPosition[0];
								myShipCoordinateY = finalPosition[1];
								myShipTurnCount = finalPosition[2];
								finalPosition = x.makeMove(3,ci3);

								if(finalPosition[2]%4==0||finalPosition[2]%4==2){ //if our ship is facing N or S
									gunMin = finalPosition[0]-3;
									gunMax = finalPosition[0]+3;
									if(finalPosition[1]==finalPosition[4]&&(finalPosition[3]>=gunMin&&
											finalPosition[3]<=gunMax&&finalPosition[3]!=finalPosition[0])){
										hitRating++; //every time enemy is in range, hitRating increases
									}
								}
								else if(finalPosition[2]%4==1||finalPosition[2]%4==3){ //if our ship is facing E or W
									gunMin = finalPosition[1]-3;
									gunMax = finalPosition[1]+3;
									if(finalPosition[0]==finalPosition[3]&&(finalPosition[4]>=gunMin&&
											finalPosition[4]<=gunMax&&finalPosition[4]!=finalPosition[1])){
										hitRating++;
									}
								}
								System.out.println();
								for(int ei=0;ei<6;ei++){
									System.out.print(finalPosition[ei]+" ");
								}
								System.out.print(hitRating);
								enemyShipCoordinateX = finalPosition[3];
								enemyShipCoordinateY = finalPosition[4];
								enemyShipTurnCount = finalPosition[5];
								int enemyShipCoordinateXS4 = enemyShipCoordinateX; 
								int enemyShipCoordinateYS4 = enemyShipCoordinateY;
								int enemyShipTurnCountS4 = enemyShipTurnCount;
								for(int ci4=0; ci4<4; ci4++){
									myShipCoordinateX = finalPosition[0];
									myShipCoordinateY = finalPosition[1];
									myShipTurnCount = finalPosition[2];
									finalPosition = x.makeMove(3,ci4);
									if(finalPosition[2]%4==0||finalPosition[2]%4==2){ //if our ship is facing N or S
										gunMin = finalPosition[0]-3;
										gunMax = finalPosition[0]+3;
										if(finalPosition[1]==finalPosition[4]&&(finalPosition[3]>=gunMin&&
												finalPosition[3]<=gunMax&&finalPosition[3]!=finalPosition[0])){
											hitRating++; //every time enemy is in range, hitRating increases
										}
									}
									else if(finalPosition[2]%4==1||finalPosition[2]%4==3){ //if our ship is facing E or W
										gunMin = finalPosition[1]-3;
										gunMax = finalPosition[1]+3;
										if(finalPosition[0]==finalPosition[3]&&(finalPosition[4]>=gunMin&&
												finalPosition[4]<=gunMax&&finalPosition[4]!=finalPosition[1])){
											hitRating++;
										}
									}
									System.out.println();
									for(int ei=0;ei<6;ei++){
										System.out.print(finalPosition[ei]+" ");
									}
									System.out.print(hitRating);
									enemyShipCoordinateX = enemyShipCoordinateXS4;
									enemyShipCoordinateY = enemyShipCoordinateYS4;
									enemyShipTurnCount = enemyShipTurnCountS4;									
								}
								enemyShipCoordinateX = enemyShipCoordinateXS3;
								enemyShipCoordinateY = enemyShipCoordinateYS3;
								enemyShipTurnCount = enemyShipTurnCountS3;
							}
							enemyShipCoordinateX = enemyShipCoordinateXS2;
							enemyShipCoordinateY = enemyShipCoordinateYS2;
							enemyShipTurnCount = enemyShipTurnCountS2;
						}
						enemyShipCoordinateX = enemyShipCoordinateXS1;
						enemyShipCoordinateY = enemyShipCoordinateYS1;
						enemyShipTurnCount = enemyShipTurnCountS1;
						myShipCoordinateX = myShipCoordinateXS;
						myShipCoordinateY = myShipCoordinateYS;
						myShipTurnCount = myShipTurnCountS;
					}
					moveRecommendation.add(hitRating); //this array was initialized eralier but id idn't explain it. it essentially is the scores for all four move syou make
					hitRating=0; //after adding to the array, you can recycle this to be used for every other case. 4 main cases, 16 subcases
					System.out.print(moveRecommendation);
				}
	
				bestMoveNumber=0;
				int di = 1;
				for(int ei=0;ei<4;ei++){
					System.out.println(moveRecommendation.get(ei));
				}
				while(di<4){
					int temp = moveRecommendation.get(di);
					if(moveRecommendation.get(bestMoveNumber)<=temp){
						bestMoveNumber=di;
					}
					di++;
				}//these 11 or so lines basically find the index of the maximum integer in that array.
	
				Image bestMove = new Image(208,23,108,28,moveTokens[bestMoveNumber]); //spits out proper image
				battleHelm.add(bestMove);
				moveRecommendation.clear();
			}
		});
		bEnemyMove.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent e){
				enemyShipRandomMove = Integer.parseInt(t1.getText());
			}
		});
		makeMove.addMouseDownHandler(new MouseDownHandler(){
			public void onMouseDown(MouseDownEvent e){
				int myShipXi,myShipYi,enemyShipXi,enemyShipYi;
				MoveToken[][] enemyShipMoves = moveTokenDirections.get(enemyShipTurnCount%4);
				MoveToken[][] myShipMoves = moveTokenDirections.get(myShipTurnCount%4);
				MoveToken[] enemyShipMove = enemyShipMoves[enemyShipRandomMove];
				MoveToken[] myShipMove = myShipMoves[bestMoveNumber];
				for(int ae=0;ae<2;ae++){
					if(ae==1&&collision){break;}
					myShipXi = myShipCoordinateX;
					myShipYi = myShipCoordinateY;
					enemyShipXi = enemyShipCoordinateX;
					enemyShipYi = enemyShipCoordinateY;
					int enemyShipX = enemyShipMove[ae].getXModifier();
					int enemyShipY = enemyShipMove[ae].getYModifier();
					int enemyShipTurnTemp = enemyShipMove[ae].getTurn();
					int myShipX = myShipMove[ae].getXModifier();
					int myShipY = myShipMove[ae].getYModifier();
					int myShipTurnTemp = myShipMove[ae].getTurn();
					enemyShipCoordinateX += enemyShipX;
					enemyShipCoordinateY += enemyShipY;
					enemyShipTurnCount += enemyShipTurnTemp;
					myShipCoordinateX += myShipX;
					myShipCoordinateY += myShipY;
					myShipTurnCount += myShipTurnTemp;
					
					boolean sameDestination = myShipCoordinateX==enemyShipCoordinateX&&myShipCoordinateY==enemyShipCoordinateY;
					if (sameDestination){
						collision = true;
						myShipTurnCount -= myShipTurnTemp;
						enemyShipTurnCount -= enemyShipTurnTemp;
						MoveToken[][] enemyShipCollides = collisionTokenDirections.get(enemyShipTurnCount%4);
						MoveToken[][] myShipCollides = collisionTokenDirections.get(myShipTurnCount%4);
						MoveToken myShipCollide[] = myShipCollides[bestMoveNumber];
						MoveToken enemyShipCollide[] = enemyShipCollides[enemyShipRandomMove];
						myShipCoordinateX -= myShipX;
						myShipCoordinateY -= myShipY;
						enemyShipCoordinateX -= enemyShipX;
						enemyShipCoordinateY -= enemyShipY;
						if(bestMoveNumber==1&&enemyShipRandomMove==3){enemyShipCollide=myShipMove;}
						else if(enemyShipRandomMove==1&&bestMoveNumber==3){myShipCollide=enemyShipMove;}
						myShipX = myShipCollide[ae].getXModifier();
						myShipY = myShipCollide[ae].getYModifier();
						enemyShipX = enemyShipCollide[ae].getXModifier();
						enemyShipY = enemyShipCollide[ae].getYModifier();
						myShipTurnTemp = myShipCollide[ae].getTurn();
						myShipCoordinateX += myShipX;
						myShipCoordinateY += myShipY;
						myShipTurnCount += myShipTurnTemp;	
						enemyShipTurnTemp = enemyShipCollide[ae].getTurn();
						enemyShipCoordinateX += enemyShipX;
						enemyShipCoordinateY += enemyShipY;
						enemyShipTurnCount += enemyShipTurnTemp;	
					}
					boolean doubleReplace = (myShipXi==enemyShipCoordinateX&&myShipYi==enemyShipCoordinateY)&&(myShipCoordinateX==enemyShipXi&&myShipCoordinateY==enemyShipYi);
					if(doubleReplace&&!collision){
						collision=true;
						myShipTurnCount -= myShipTurnTemp;
						enemyShipTurnCount -= enemyShipTurnTemp;
						MoveToken[][] enemyShipCollides = collisionTokenDirections.get(enemyShipTurnCount%4);
						MoveToken[][] myShipCollides = collisionTokenDirections.get(myShipTurnCount%4);
						MoveToken[] myShipCollide = myShipCollides[bestMoveNumber];
						MoveToken[] enemyShipCollide = enemyShipCollides[enemyShipRandomMove];
						myShipCoordinateX -= myShipX;
						myShipCoordinateY -= myShipY;
						enemyShipCoordinateX -= enemyShipX;
						enemyShipCoordinateY -= enemyShipY;
						myShipX = myShipCollide[ae].getXModifier();
						myShipY = myShipCollide[ae].getYModifier();
						enemyShipX = enemyShipCollide[ae].getXModifier();
						enemyShipY = enemyShipCollide[ae].getYModifier();
						myShipTurnTemp = myShipCollide[ae].getTurn();
						myShipCoordinateX += myShipX;
						myShipCoordinateY += myShipY;
						myShipTurnCount += myShipTurnTemp;	
						enemyShipTurnTemp = enemyShipCollide[ae].getTurn();
						enemyShipCoordinateX += enemyShipX;
						enemyShipCoordinateY += enemyShipY;
						enemyShipTurnCount += enemyShipTurnTemp;
					}
				}
				collision = false;
				Ship eTemp = new Ship(enemyShipCoordinateX*50,enemyShipCoordinateY*50, enemyShipTurnCount);
				Ship temp = new Ship(myShipCoordinateX*50, myShipCoordinateY*50, myShipTurnCount);
				battleScreen.remove(myShip);
				battleScreen.remove(enemyShip);
				shipColor = "blue";
				myShip = temp.drawShip();
				shipColor = "red";
				enemyShip = eTemp.drawShip();
				battleScreen.add(myShip);
				battleScreen.add(enemyShip);
			}
		});
		v2.add(l1);
		v2.add(myShipButton);
		v2.add(enemyShipButton);
		v2.add(calculateButton);
		v2.add(clearButton);
		v2.add(makeMove);
		h1.add(v2);
		h1.add(battleHelm);
		h1.add(t1);
		h1.add(bEnemyMove);
		v1.add(h1);
		RootPanel.get().add(v1); //UI
		
	}
}
//Facing, Up and turn; Right angle, up Right
//side-by-side case