package aima.core.environment.towers;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;


public class ManhattanHeuristicFunction implements HeuristicFunction {

	public double h(Object state) {
		TowersBoard board = (TowersBoard) state;
		int retVal = 0;
		for (int i = 1; i < 5; i++) {
			retVal += evaluateManhattanDistanceOf(board.getLocationOf(i));
		}
		return retVal;

	}

	private int evaluateManhattanDistanceOf(XYLocation loc) {
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();

		return getValue(xpos, ypos);
	}
	
	private int getValue(int xpos, int ypos) {
		int value = 0;
		
		int first = Math.abs(xpos - 3) + Math.abs(ypos - 3);
		int second = Math.abs(xpos - 3) + Math.abs(ypos - 4);
		int third = Math.abs(xpos - 4) + Math.abs(ypos - 3);
		int fourth = Math.abs(xpos - 4) + Math.abs(ypos - 4);
		
		value = Math.min(first, second);
		value = Math.min(value, third);
		value = Math.min(value, fourth);
		
		return value;
	}
}