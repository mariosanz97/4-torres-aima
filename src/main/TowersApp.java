import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Environment;
import aima.core.agent.EnvironmentState;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;
import aima.core.environment.towers.TowersBoard;
import aima.core.environment.towers.TowersFunctionFactory;
import aima.core.environment.towers.TowersGoalTest;
import aima.core.environment.towers.ManhattanHeuristicFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.util.datastructure.XYLocation;
import aima.gui.framework.AgentAppController;
import aima.gui.framework.AgentAppEnvironmentView;
import aima.gui.framework.AgentAppFrame;
import aima.gui.framework.MessageLogger;
import aima.gui.framework.SimpleAgentApp;
import aima.gui.framework.SimulationThread;


public class TowersApp extends SimpleAgentApp {

	/** List of supported search algorithm names. */
	protected static List<String> SEARCH_NAMES = new ArrayList<String>();
	/** List of supported search algorithms. */
	protected static List<Search> SEARCH_ALGOS = new ArrayList<Search>();

	/** Adds a new item to the list of supported search algorithms. */
	public static void addSearchAlgorithm(String name, Search algo) {
		SEARCH_NAMES.add(name);
		SEARCH_ALGOS.add(algo);
	}

	static {
		addSearchAlgorithm("Greedy Best First Search (ManhattanHeursitic)",
				new GreedyBestFirstSearch(new GraphSearch(),
						new ManhattanHeuristicFunction()));
	}

	/** Returns an <code>TowersView</code> instance. */
	public AgentAppEnvironmentView createEnvironmentView() {
		return new TowersView();
	}

	/** Returns a <code>TowersFrame</code> instance. */
	@Override
	public AgentAppFrame createFrame() {
		return new TowersFrame();
	}

	/** Returns a <code>TowersController</code> instance. */
	@Override
	public AgentAppController createController() {
		return new TowersController();
	}

	// ///////////////////////////////////////////////////////////////
	// main method

	/**
	 * Starts the application.
	 */
	public static void main(String args[]) {
		new TowersApp().startApplication();
	}

	// ///////////////////////////////////////////////////////////////
	// some inner classes

	/**
	 * Adds some selectors to the base class and adjusts its size.
	 */
	protected static class TowersFrame extends AgentAppFrame {
		private static final long serialVersionUID = 1L;
		public static String ENV_SEL = "EnvSelection";
		public static String SEARCH_SEL = "SearchSelection";

		public TowersFrame() {
			setTitle("Towers Application");
			setSelectors(new String[] { ENV_SEL, SEARCH_SEL }, new String[] {
					"Select Environment", "Select Search" });
			setSelectorItems(ENV_SEL, new String[] { "" }, 0);
			setSelectorItems(SEARCH_SEL, (String[]) SEARCH_NAMES
					.toArray(new String[] {}), 0);
			setEnvView(new TowersView());
			setSize(800, 600);
		}
	}

	/**
	 * Displays the informations provided by a
	 * <code>TowersEnvironment</code> on a panel using an grid of buttons.
	 * By pressing a button, the user can move the corresponding tile to the
	 * adjacent gap.
	 */
	protected static class TowersView extends AgentAppEnvironmentView
			implements ActionListener {
		private static final long serialVersionUID = 1L;
		protected JButton[] squareButtons;
		protected int selectedTower;

		protected TowersView() {
			setLayout(new GridLayout(8, 8));
			Font f = new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 32);
			squareButtons = new JButton[64];
			for (int i = 0; i < 64; i++) {
				JButton square = new JButton("");
				square.setFont(f);
				square.addActionListener(this);
				squareButtons[i] = square;
				add(square);
			}
			selectedTower = 1;
		}

		@Override
		public void setEnvironment(Environment env) {
			super.setEnvironment(env);
			showState();
		}

		/** Agent value null indicates a user initiated action. */
		@Override
		public void agentActed(Agent agent, Action action,
				EnvironmentState resultingState) {
			showState();
			if (action != null)
				notify((agent == null ? "User: " : "") + action.toString());
		}

		@Override
		public void agentAdded(Agent agent, EnvironmentState resultingState) {
			showState();
		}

		/**
		 * Displays the board state by labeling and coloring the square buttons.
		 */
		protected void showState() {
			int[] vals = ((TowersEnvironment) env).getBoard().getState();
			for (int i = 0; i < 64; i++) {	
				squareButtons[i].setBorder(new LineBorder(Color.BLACK, 1));
				squareButtons[i].setIcon(null);
				alternateColors(i);
				if (i == 27 || i == 28 || i == 35 || i == 36)
					squareButtons[i].setBorder(new LineBorder(Color.RED, 4));
				if (vals[i] == 1 || vals[i] == 2 || vals[i] == 3 || vals[i] == 4) {
					squareButtons[i].setIcon(new ImageIcon("res/tower.png"));
				}
				if (vals[i] == selectedTower)
					squareButtons[i].setBorder(new LineBorder(Color.BLUE, 4));			
			}
		}
		
		private void alternateColors(int value) {
			int row = value / 8;
			switch (row % 2) {
			case 0:
				switch (value % 2) {
				case 0:
					squareButtons[value].setBackground(Color.WHITE);
					break;
				default:
					squareButtons[value].setBackground(Color.BLACK);
					break;
				}
				break;
			default:
				switch (value % 2) {
				case 0:
					squareButtons[value].setBackground(Color.BLACK);
					break;
				default:
					squareButtons[value].setBackground(Color.WHITE);
					break;
				}
				break;
			}
		}

		/**
		 * When the user presses square buttons the board state is modified
		 * accordingly.
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			TowersBoard board = ((TowersEnvironment) env).getBoard();
			TowersController contr = (TowersController) getController();
			int[] towersIndex = board.getTowersIndex();
			boolean isTowerSelection = false;
			for (int i = 0; i < 64; i++) {
				if (ae.getSource() == squareButtons[i]) {
					if (towersIndex[0] == i || towersIndex[1] == i || towersIndex[2] == i || towersIndex[3] == i) {
						isTowerSelection = true;
						selectedTower = board.getValueAt(i);
						contr.executeUserAction(null);
					}
				}
			}
			if (!isTowerSelection)
				for (int i = 0; i < 64; i++) {
					if (ae.getSource() == squareButtons[i]) {
						XYLocation locSelectedTower = board.getLocationOf(selectedTower);
						if (locSelectedTower.getXCoOrdinate() == i / 8) {
							if (locSelectedTower.getYCoOrdinate() < i % 8 && board.canMoveTower(selectedTower, chooseAction(selectedTower, "RIGHT")))
								contr.executeUserAction(chooseAction(selectedTower, "RIGHT"));
							else if (locSelectedTower.getYCoOrdinate() > i % 8 && board.canMoveTower(selectedTower, chooseAction(selectedTower, "LEFT")))
								contr.executeUserAction(chooseAction(selectedTower, "LEFT"));
						} else if (locSelectedTower.getYCoOrdinate() == i % 8) {
							if (locSelectedTower.getXCoOrdinate() < i / 8 && board.canMoveTower(selectedTower, chooseAction(selectedTower, "DOWN")))
								contr.executeUserAction(chooseAction(selectedTower, "DOWN"));
							else if (locSelectedTower.getXCoOrdinate() > i / 8 && board.canMoveTower(selectedTower, chooseAction(selectedTower, "UP")))
								contr.executeUserAction(chooseAction(selectedTower, "UP"));
						}
					}
				}
		}
		
		private Action chooseAction(int i, String direction) {
			switch (i) {
			case 1:
				if (direction.equals("LEFT"))
					return TowersBoard.LEFT_1;
				if (direction.equals("RIGHT"))
					return TowersBoard.RIGHT_1;
				if (direction.equals("UP"))
					return TowersBoard.UP_1;
				if (direction.equals("DOWN"))
					return TowersBoard.DOWN_1;
				break;
			case 2:
				if (direction.equals("LEFT"))
					return TowersBoard.LEFT_2;
				if (direction.equals("RIGHT"))
					return TowersBoard.RIGHT_2;
				if (direction.equals("UP"))
					return TowersBoard.UP_2;
				if (direction.equals("DOWN"))
					return TowersBoard.DOWN_2;
				break;
			case 3:
				if (direction.equals("LEFT"))
					return TowersBoard.LEFT_3;
				if (direction.equals("RIGHT"))
					return TowersBoard.RIGHT_3;
				if (direction.equals("UP"))
					return TowersBoard.UP_3;
				if (direction.equals("DOWN"))
					return TowersBoard.DOWN_3;
				break;
			case 4:
				if (direction.equals("LEFT"))
					return TowersBoard.LEFT_4;
				if (direction.equals("RIGHT"))
					return TowersBoard.RIGHT_4;
				if (direction.equals("UP"))
					return TowersBoard.UP_4;
				if (direction.equals("DOWN"))
					return TowersBoard.DOWN_4;
				break;
			default:
				break;
			}
			
			return null;
		}
	}

	/**
	 * Defines how to react on standard simulation button events.
	 */
	protected static class TowersController extends AgentAppController {

		protected TowersEnvironment env = null;
		protected SearchAgent agent = null;

		/** Prepares next simulation. */
		@Override
		public void clear() {
			prepare(null);
		}

		/**
		 * Creates the environment and clears the current search
		 * agent.
		 */
		@Override
		public void prepare(String changedSelector) {
			int[] temp = new int[64];
			for (int i = 0; i < 64;i++) {
				if (i == 0)
					temp[i] = 1;
				else if (i == 7)
					temp[i] = 2;
				else if (i == 56)
					temp[i] = 3;
				else if (i == 63)
					temp[i] = 4;
				else temp[i] = 0;
			}

			TowersBoard board = new TowersBoard(temp);
			env = new TowersEnvironment(board);
			agent = null;
			frame.getEnvView().setEnvironment(env);
		}

		/**
		 * Creates a new search agent and adds it to the current environment if
		 * necessary.
		 */
		protected void addAgent() throws Exception {
			if (agent == null) {
				int pSel = frame.getSelection().getIndex(
						TowersFrame.SEARCH_SEL);
				Problem problem = new Problem(env.getBoard(),
						TowersFunctionFactory.getActionsFunction(),
						TowersFunctionFactory.getResultFunction(),
						new TowersGoalTest());
				Search search = SEARCH_ALGOS.get(pSel);
				agent = new SearchAgent(problem, search);
				env.addAgent(agent);
			}
		}

		/** Checks whether simulation can be started. */
		@Override
		public boolean isPrepared() {
			return (agent == null || !agent.isDone());
		}

		/** Starts simulation. */
		@Override
		public void run(MessageLogger logger) {
			logger.log("<simulation-log>");
			try {
				addAgent();
				while (!agent.isDone() && !frame.simulationPaused()) {
					Thread.sleep(500);
					env.step();
				}
			} catch (InterruptedException e) {
				// nothing to do...
			} catch (Exception e) {
				e.printStackTrace(); // probably search has failed...
			}
			if (agent != null)
				logger.log(getStatistics());
			logger.log("</simulation-log>\n");
		}

		/** Executes one simulation step. */
		@Override
		public void step(MessageLogger logger) {
			try {
				addAgent();
				env.step();
			} catch (Exception e) {
				e.printStackTrace(); // probably search has failed...
			}
		}

		/** Updates the status of the frame after simulation has finished. */
		public void update(SimulationThread simulationThread) {
			if (simulationThread.isCanceled()) {
				frame.setStatus("Task canceled.");
			} else if (frame.simulationPaused()) {
				frame.setStatus("Task paused.");
			} else {
				frame.setStatus("Task completed.");
			}
		}

		/** Provides a text with statistical information about the last run. */
		private String getStatistics() {
			StringBuffer result = new StringBuffer();
			Properties properties = agent.getInstrumentation();
			Iterator<Object> keys = properties.keySet().iterator();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String property = properties.getProperty(key);
				result.append("\n" + key + " : " + property);
			}
			return result.toString();
		}

		public void executeUserAction(Action action) {
			env.executeAction(null, action);
			agent = null;
			frame.updateEnabledState();
		}
	}

	/** Simple environment maintaining just the current board state. */
	protected static class TowersEnvironment extends AbstractEnvironment {
		TowersBoard board;

		protected TowersEnvironment(TowersBoard board) {
			this.board = board;
		}

		protected TowersBoard getBoard() {
			return board;
		}

		/** Executes the provided action and returns null. */
		@Override
		public EnvironmentState executeAction(Agent agent, Action action) {
			if (action == TowersBoard.UP_1)
				board.moveTowerUp(1);
			else if (action == TowersBoard.DOWN_1)
				board.moveTowerDown(1);
			else if (action == TowersBoard.LEFT_1)
				board.moveTowerLeft(1);
			else if (action == TowersBoard.RIGHT_1)
				board.moveTowerRight(1);
			else if (action == TowersBoard.UP_2)
				board.moveTowerUp(2);
			else if (action == TowersBoard.DOWN_2)
				board.moveTowerDown(2);
			else if (action == TowersBoard.LEFT_2)
				board.moveTowerLeft(2);
			else if (action == TowersBoard.RIGHT_2)
				board.moveTowerRight(2);
			else if (action == TowersBoard.UP_3)
				board.moveTowerUp(3);
			else if (action == TowersBoard.DOWN_3)
				board.moveTowerDown(3);
			else if (action == TowersBoard.LEFT_3)
				board.moveTowerLeft(3);
			else if (action == TowersBoard.RIGHT_3)
				board.moveTowerRight(3);
			else if (action == TowersBoard.UP_4)
				board.moveTowerUp(4);
			else if (action == TowersBoard.DOWN_4)
				board.moveTowerDown(4);
			else if (action == TowersBoard.LEFT_4)
				board.moveTowerLeft(4);
			else if (action == TowersBoard.RIGHT_4)
				board.moveTowerRight(4);
			if (agent == null)
				updateEnvironmentViewsAgentActed(agent, action, null);
			return null;
		}

		/** Returns null. */
		@Override
		public EnvironmentState getCurrentState() {
			return null;
		}

		/** Returns null. */
		@Override
		public Percept getPerceptSeenBy(Agent anAgent) {
			return null;
		}
	}
}
