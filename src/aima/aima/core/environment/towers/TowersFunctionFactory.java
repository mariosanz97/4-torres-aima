package aima.core.environment.towers;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

public class TowersFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new EPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new EPResultFunction();
		}
		return _resultFunction;
	}

	private static class EPActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			TowersBoard board = (TowersBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();
			for (int counter = 1; counter < 5; counter++) {
				switch (counter) {
				case 1:
					if (board.canMoveTower(counter, TowersBoard.UP_1)) {
						actions.add(TowersBoard.UP_1);
					}
					if (board.canMoveTower(counter, TowersBoard.DOWN_1)) {
						actions.add(TowersBoard.DOWN_1);
					}
					if (board.canMoveTower(counter, TowersBoard.LEFT_1)) {
						actions.add(TowersBoard.LEFT_1);
					}
					if (board.canMoveTower(counter, TowersBoard.RIGHT_1)) {
						actions.add(TowersBoard.RIGHT_1);
					}
					break;
				case 2:
					if (board.canMoveTower(counter, TowersBoard.UP_2)) {
						actions.add(TowersBoard.UP_2);
					}
					if (board.canMoveTower(counter, TowersBoard.DOWN_2)) {
						actions.add(TowersBoard.DOWN_2);
					}
					if (board.canMoveTower(counter, TowersBoard.LEFT_2)) {
						actions.add(TowersBoard.LEFT_2);
					}
					if (board.canMoveTower(counter, TowersBoard.RIGHT_2)) {
						actions.add(TowersBoard.RIGHT_2);
					}
					break;
				case 3:
					if (board.canMoveTower(counter, TowersBoard.UP_3)) {
						actions.add(TowersBoard.UP_3);
					}
					if (board.canMoveTower(counter, TowersBoard.DOWN_3)) {
						actions.add(TowersBoard.DOWN_3);
					}
					if (board.canMoveTower(counter, TowersBoard.LEFT_3)) {
						actions.add(TowersBoard.LEFT_3);
					}
					if (board.canMoveTower(counter, TowersBoard.RIGHT_3)) {
						actions.add(TowersBoard.RIGHT_3);
					}
					break;
				case 4:
					if (board.canMoveTower(counter, TowersBoard.UP_4)) {
						actions.add(TowersBoard.UP_4);
					}
					if (board.canMoveTower(counter, TowersBoard.DOWN_4)) {
						actions.add(TowersBoard.DOWN_4);
					}
					if (board.canMoveTower(counter, TowersBoard.LEFT_4)) {
						actions.add(TowersBoard.LEFT_4);
					}
					if (board.canMoveTower(counter, TowersBoard.RIGHT_4)) {
						actions.add(TowersBoard.RIGHT_4);
					}
					break;
				default:
					break;
				}
				
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			TowersBoard board = (TowersBoard) s;
			System.out.println("Paso por aqui 4");
			if (TowersBoard.UP_1.equals(a)
					&& board.canMoveTower(1, TowersBoard.UP_1)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerUp(1);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.DOWN_1.equals(a)
					&& board.canMoveTower(1, TowersBoard.DOWN_1)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerDown(1);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.LEFT_1.equals(a)
					&& board.canMoveTower(1, TowersBoard.LEFT_1)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerLeft(1);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.RIGHT_1.equals(a)
					&& board.canMoveTower(1, TowersBoard.RIGHT_1)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerRight(1);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.UP_2.equals(a)
					&& board.canMoveTower(2, TowersBoard.UP_2)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerUp(2);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.DOWN_2.equals(a)
					&& board.canMoveTower(2, TowersBoard.DOWN_2)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerDown(2);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.LEFT_2.equals(a)
					&& board.canMoveTower(2, TowersBoard.LEFT_2)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerLeft(2);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.RIGHT_2.equals(a)
					&& board.canMoveTower(2, TowersBoard.RIGHT_2)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerRight(2);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.UP_3.equals(a)
					&& board.canMoveTower(3, TowersBoard.UP_3)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerUp(3);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.DOWN_3.equals(a)
					&& board.canMoveTower(3, TowersBoard.DOWN_3)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerDown(3);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.LEFT_3.equals(a)
					&& board.canMoveTower(3, TowersBoard.LEFT_3)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerLeft(3);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.RIGHT_3.equals(a)
					&& board.canMoveTower(3, TowersBoard.RIGHT_3)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerRight(3);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.UP_4.equals(a)
					&& board.canMoveTower(4, TowersBoard.UP_4)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerUp(4);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.DOWN_4.equals(a)
					&& board.canMoveTower(4, TowersBoard.DOWN_4)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerDown(4);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.LEFT_4.equals(a)
					&& board.canMoveTower(4, TowersBoard.LEFT_4)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerLeft(4);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			} else if (TowersBoard.RIGHT_4.equals(a)
					&& board.canMoveTower(4, TowersBoard.RIGHT_4)) {
				TowersBoard newBoard = new TowersBoard(board);
				newBoard.moveTowerRight(4);
				System.out.println(":::::::::::"+newBoard);
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}