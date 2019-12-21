package tilegame.pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

public class AStar {
	
	public int finishX, finishY;
	public AStar(int finishX, int finishY){
		this.finishX = finishX;
		this.finishY = finishY;
		
	}
	
	public State search(State pocetnoStanje){
		
		ArrayList<State> stanjaZaObradu = new ArrayList<State>();
		Hashtable<String,String> predjeniPut = new Hashtable<String,String>();
		stanjaZaObradu.add(pocetnoStanje);
		while (stanjaZaObradu.size() > 0){
			State naObradi = getBest(stanjaZaObradu);
			if (naObradi.isFinishState(finishX,finishY)){
				return naObradi;
			}
			
			if (!predjeniPut.containsKey(naObradi.GetStringHashCode())){
				predjeniPut.put(naObradi.GetStringHashCode(), "");
				ArrayList<State> sledecaStanja = naObradi.mogucaSledecaStanja();
				
				for (State state : sledecaStanja) {
					stanjaZaObradu.add(state);
				}
			}
			
			stanjaZaObradu.remove(naObradi);
		}
		return null;
	}
	
	public double heuristicFunction(State s){
		return Math.sqrt(Math.pow(s.markI - finishX,2) + Math.pow(s.markJ - finishY, 2));
	}
	
	public State getBest(ArrayList<State> stanja){
		State retVal = null;
		double min = Double.MAX_VALUE;
		
		for (State state : stanja) {
			
			double h = state.cost + heuristicFunction(state);
			if (h < min){
				min = h;
				retVal = state;
			}
		}
		return retVal;
	}
	
	public static ArrayList<Point> parseState(ArrayList<State> resenje, boolean cutCorners){
		
		ArrayList<Point> retVal = new ArrayList<Point>();		
		ArrayList<State> remove = new ArrayList<State>();
		for (int i = 0; i <resenje.size() - 2; i++){
			if (cutCorners){
				if (Math.abs(resenje.get(i).getIndex().x - resenje.get(i+2).getIndex().x) == 1 &&
					Math.abs(resenje.get(i).getIndex().y - resenje.get(i+2).getIndex().y) == 1){
					i++;
					remove.add(resenje.get(i));
				}
			}
		}
		
		resenje.removeAll(remove);
		
		for (int i = 1; i < resenje.size(); i++){
			retVal.add(resenje.get(i).getCoordinates());
		}
		return retVal;
	}
	
	public static ArrayList<Point> parseStateMapIndex(ArrayList<State> resenje, boolean cutCorners){
		ArrayList<Point> retVal = new ArrayList<Point>();		
		ArrayList<State> remove = new ArrayList<State>();
		if (cutCorners){
			for (int i = 0; i <resenje.size() - 2; i++){
				if (Math.abs(resenje.get(i).getIndex().x - resenje.get(i+2).getIndex().x) == 1 &&
						Math.abs(resenje.get(i).getIndex().y - resenje.get(i+2).getIndex().y) == 1){
					i++;
					remove.add(resenje.get(i));
				}
			}
		resenje.removeAll(remove);
		}
		
		
		
		for (int i = 1; i < resenje.size(); i++){
			retVal.add(resenje.get(i).getIndex());
		}
		return retVal;
	}
}
