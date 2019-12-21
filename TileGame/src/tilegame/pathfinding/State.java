package tilegame.pathfinding;

import java.awt.Point;
import java.util.ArrayList;

import tilegame.worlds.World;


//enum TileType{START, FINISH, PASSABLE, SOLID, PLAYER, ENEMY, HEART}

public class State {

	public int markI, markJ;
	public int cost = 0;
	public TileType lavirint[][];
	State parent;
	
	
	public State(){
		this.lavirint = AreaMap.lavirint;
	}
	
	public State(int markI, int markJ, TileType[][] lavirint){
		this.markI = markI;
		this.markJ = markJ;
		this.lavirint = lavirint;
		/*
		this.lavirint = new TileType[AreaMap.width][AreaMap.height];
		for (int i = 0; i < AreaMap.width; i++){
			for (int j = 0; j < AreaMap.height; j++){
				this.lavirint[i][j] = lavirint[i][j];
			}
		}
		*/
	}
	
	public State sledeceStanje(int markI, int markJ,TileType[][] lavirint){
		State retVal = new State(markI,markJ,lavirint);
		retVal.parent = this;
		retVal.cost = cost + 1;
		
		/*
		for (int i = 0; i < AreaMap.width; i++){
			for (int j = 0; j < AreaMap.height; j++){
				this.lavirint[i][j] = lavirint[i][j];
			}
		}
		*/
		return retVal;
	}
	
	public ArrayList<State> mogucaSledecaStanja(){
		
		ArrayList<State> retVal = new ArrayList<State>();
		int[] ii = new int[]{ 1, 0, 0, -1};
		int[] jj = new int[]{ 0, 1, -1, 0};
		
		for (int i = 0; i < ii.length; i++){
			int newI = markI + ii[i];
			int newJ = markJ + jj[i];
			
			if (newI >= 0 && newI <= AreaMap.width && newJ >= 0 && newJ <= AreaMap.height &&
					lavirint[newI][newJ] != TileType.SOLID &&
					lavirint[newI][newJ] != TileType.ENEMY &&
					lavirint[newI][newJ] != TileType.CHANGED){
				State st = sledeceStanje(newI, newJ,lavirint);
				retVal.add(st);
			}
		}
		
		return retVal;
	}
	
	public String GetStringHashCode(){
		String hashCode = "" + markI + ":" + markJ;
		return hashCode;
	}
	
	public boolean isFinishState(int finishX, int finishY){
		return markI == finishX && markJ == finishY;
	}
	
	public ArrayList<State> path(){
		
		ArrayList<State> putanja = new ArrayList<State>();
		State tt = this;
		while (tt != null){
			putanja.add(0, tt);
			tt = tt.parent;
		}
		
		return putanja;
		
	}
	
	
	
	public Point getCoordinates(){
		return World.getCoords(markI, markJ);
	}
	
	public Point getIndex(){
		return new Point(markI,markJ);
	}
	
	public void setStateCoords(int i, int j){
		this.markI = i;
		this.markJ = j;
	}
	
	
}
