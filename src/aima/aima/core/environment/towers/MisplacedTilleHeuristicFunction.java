package aima.core.environment.towers;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

public class MisplacedTilleHeuristicFunction implements HeuristicFunction {

	public double h(Object state) {
		TowersBoard board = (TowersBoard) state;
		return getNumberOfMisplacedTiles(board);
	}

	private int getNumberOfMisplacedTiles(TowersBoard board) {
		int numberOfMisplacedTiles = 0;
		for (int i = 1; i < 5; i++) {
			if (isTowerPlaced(board, i))
				numberOfMisplacedTiles++;
		}

		return numberOfMisplacedTiles;
	}
	
	private boolean isTowerPlaced(TowersBoard board, int value) {
		boolean result = false;
		if ((board.getLocationOf(value).equals(new XYLocation(3, 3)))
				|| (board.getLocationOf(value).equals(new XYLocation(3, 4)))
				|| (board.getLocationOf(value).equals(new XYLocation(4, 3)))
				|| (board.getLocationOf(value).equals(new XYLocation(4, 4)))) {
			result = true;
		}
		
		return result;
	}
}