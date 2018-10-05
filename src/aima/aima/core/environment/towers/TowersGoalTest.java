package aima.core.environment.towers;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.search.framework.GoalTest;
import aima.core.util.datastructure.XYLocation;

public class TowersGoalTest implements GoalTest {

	public boolean isGoalState(Object state) {
		System.out.println("aa");
		TowersBoard board = (TowersBoard) state;
		return isGoal(board);
	}

	private boolean isGoal(TowersBoard board) {
		/// IMPLEMENTAR: FUNCIÓN QUE DEVULEVE VERDADERO SI ESTAMOS EN UN ESTADO META
		/// (SOLUCIÓN)
		int pos1 = board.getValueAt(27);
		int pos2 = board.getValueAt(28);
		int pos3 = board.getValueAt(35);
		int pos4 = board.getValueAt(36);

		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::" + pos1 + ":::::" + pos2 + ":::::" + pos3);
		System.out.println("Paso por aqui 3");

		if ((pos1 == 1 || pos1 == 2 || pos1 == 3 || pos1 == 4) && (pos2 == 1 || pos2 == 2 || pos2 == 3 || pos2 == 4)
				&& (pos3 == 1 || pos3 == 2 || pos3 == 3 || pos3 == 4)
				&& (pos4 == 1 || pos4 == 2 || pos4 == 3 || pos4 == 4)) {
			return true;
		} else {
			return false;
		}

//		if (pos1 == 0 && pos2 == 0 && pos3 == 0 && pos4 == 0) {
//			return false;
//		} else {
//			return true;
//		}

	}
}
