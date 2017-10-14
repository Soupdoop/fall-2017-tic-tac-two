import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Game extends JFrame
{
	final int WIDTH = 1000;
	final int HEIGHT = 1000;
	
	JButton b1 = new JButton();
	JButton b2 = new JButton();
	JButton b3 = new JButton();
	JButton b4 = new JButton();
	JButton b5 = new JButton();
	JButton b6 = new JButton();
	JButton b7 = new JButton();
	JButton b8 = new JButton();
	JButton b9 = new JButton();
	JButton[] buttons = {b1, b2, b3, b4, b5, b6, b7, b8, b9};
	
	JTextArea a1 = new JTextArea();
	JTextArea a2 = new JTextArea();
	JTextArea a3 = new JTextArea();
	JTextArea a4 = new JTextArea();
	JTextArea a5 = new JTextArea();
	JTextArea a6 = new JTextArea();
	JTextArea a7 = new JTextArea();
	JTextArea a8 = new JTextArea();
	JTextArea a9 = new JTextArea();
	JTextArea[] texts = {a1, a2, a3, a4, a5, a6, a7, a8, a9};
	
	JButton btnAI = new JButton("AI Settings");
	JButton restart = new JButton("Restart");
	
	JTextArea turn = new JTextArea();
	JTextArea winner = new JTextArea();
	JTextArea settingsAI = new JTextArea("AI: Player 2");
	
	int computerPlayer = -1;
	
	boolean gamesOver = false;
	
	int turns = 0;
	String activePlayer = "Player 1";
	int[] values = new int[9]; //0 means no value- 1 means player1, -1 means player2
	boolean whosTurn = true; //true = player1, false = player2
	int[][] locations = {{WIDTH/10, HEIGHT/10}, {3*WIDTH/10, HEIGHT/10}, {5*WIDTH/10, HEIGHT/10}, {WIDTH/10, 3*HEIGHT/10}, {3*WIDTH/10, 3*HEIGHT/10}, {5*WIDTH/10, 3*HEIGHT/10}, {WIDTH/10, 5*HEIGHT/10}, {3*WIDTH/10, 5*HEIGHT/10}, {5*WIDTH/10, 5*HEIGHT/10}, {10, 10}, {10, 8*HEIGHT/10}, {8*WIDTH/10, 8*HEIGHT/10}, {6*WIDTH/10,8*HEIGHT/10}, {7*WIDTH/10,HEIGHT/10}};
	
	public Game() //more code
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Tic Tac Toe");
		addObjects();
		addActions();
	}
	
	public void addObjects()
	{
		for (int i = 0; i < buttons.length; i++)
		{
			drop(100, 100, locations[i], true, buttons[i]);
			drop(100, 100, locations[i], false, texts[i]);
			texts[i].setEditable(false);
			texts[i].setBackground(Color.WHITE);
		}
		drop(75, 300, locations[9], true, turn);
		turn.setEditable(false);
		turn.setText("Current Player: " + activePlayer);
		drop(75, 300, locations[10], true, winner);
		winner.setEditable(false);
		winner.setText("No winner yet!");
		drop(75, 75, locations[11], false, restart);
		drop(75, 100, locations[12], true, btnAI);
		drop(75, 125, locations[13], true, settingsAI);
		
		JLabel l = new JLabel(); //for some reason, adding a stupid label makes it all come together.
		add(l);
	}
	
	public void addActions()
	{
		buttons[0].addActionListener((ActionEvent event)->{
			buttonPress(buttons[0], 0);
		});
		buttons[1].addActionListener((ActionEvent event)->{
			buttonPress(buttons[1], 1);
		});
		buttons[2].addActionListener((ActionEvent event)->{
			buttonPress(buttons[2], 2);
		});
		buttons[3].addActionListener((ActionEvent event)->{
			buttonPress(buttons[3], 3);
		});
		buttons[4].addActionListener((ActionEvent event)->{
			buttonPress(buttons[4], 4);
		});
		buttons[5].addActionListener((ActionEvent event)->{
			buttonPress(buttons[5], 5);
		});
		buttons[6].addActionListener((ActionEvent event)->{
			buttonPress(buttons[6], 6);
		});
		buttons[7].addActionListener((ActionEvent event)->{
			buttonPress(buttons[7], 7);
		});
		buttons[8].addActionListener((ActionEvent event)->{
			buttonPress(buttons[8], 8);
		});
		restart.addActionListener((ActionEvent event)->{
			restart();
		});
		btnAI.addActionListener((ActionEvent event)->{
			setAI();
		});
	}
	
	public void buttonPress(JButton b, int index)
	{
		if (b.getText().equals("X") == false && b.getText().equals("O") == false)
		{
			if (activePlayer.equals("Player 1"))
			{
				b.setText("X");
				values[index] = 1;
			}
			else
			{
				b.setText("O");
				values[index] = -1;
			}
			turns++;
			if (turns == 9)
			{
				testWinConditions(true);
			}
			testWinConditions(false);
			if(!gamesOver)
			{
				if (activePlayer.equals("Player 1"))
				{
					activePlayer = "Player 2";
				}
				else
				{
					activePlayer = "Player 1";
				}
				turn.setText("Current Player: " + activePlayer);
				checkAITurn();
				btnAI.setVisible(false);
			}
		}
	}
	
	public void AITurn()
	{
		int randomSpace = (int) (Math.random() * 9); //random integer from 0 to 8
		
		while(buttons[randomSpace].getText().equals("X") || buttons[randomSpace].getText().equals("O")){
			randomSpace = (int) (Math.random() * 9);
		}
		
		buttonPress(buttons[randomSpace], randomSpace);
	}
	
	public void drop(int h, int w, int[] pt, boolean visible, JComponent comp)
	{
		comp.setLocation(pt[0], pt[1]);
		comp.setSize(w, h);
		add(comp);
		comp.setVisible(visible);
	}
	
	public void testWinConditions(boolean last)
	{
		for (int i = 0; i < values.length; i++)
		{
			if (i == 0)
			{
				if (Math.abs(values[i] + values[i+4] + values[i+8]) == 3) //L to R diagonal
				{
					//win
					winner.setText(activePlayer + " has won!");
					gameOver();
				}
			}
			if (i == 2)
			{
				if (Math.abs(values[i] + values[i+2] + values[i+4]) == 3) //R to L diagonal
				{
					//win
					winner.setText(activePlayer + " has won!");
					gameOver();
				}
			}
			if (i < 3)
			{
				if (Math.abs(values[i] + values[i+3] + values[i+6]) == 3) //vertical
				{
					//win
					winner.setText(activePlayer + " has won!");
					gameOver();
				}
			}
			if (i % 3 == 0)
			{
				if (Math.abs(values[i] + values[i+1] + values[i+2]) == 3) //horizontal
				{
					//win
					winner.setText(activePlayer + " has won!");
					gameOver();
				}
			}
		}
		if (last)
		{
			//cat game
			winner.setText("It's a cat game.");
			gameOver();
		}
	}
	
	public void gameOver()
	{
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setEnabled(false);
		}
		gamesOver = true;
		btnAI.setVisible(true);
		restart.setVisible(true);
	}
	
	public void restart()
	{
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setEnabled(true);
			buttons[i].setText("");
			values[i] = 0;
			turns = 0;
			activePlayer = "Player 1";
			turn.setText("Current Player: " + activePlayer);
		}
		gamesOver = false;
		restart.setVisible(false);
		checkAITurn();
	}
	
	public void setAI()
	{
		String input = JOptionPane.showInputDialog("How should the AI be set?\n0: No AI\n-1: AI is Player 2\n1: AI is Player 1");
		computerPlayer = Integer.parseInt(input);
		String AISet = "";
		if (computerPlayer == 0)
		{
			AISet = "off";
		}
		else if (computerPlayer == 1)
		{
			AISet = "Player 1";
		}
		else
		{
			AISet = "Player 2";
		}
		settingsAI.setText("AI: " + AISet);
		checkAITurn();
	}
	
	public void checkAITurn()
	{
		if (computerPlayer == 1 && activePlayer.equals("Player 1"))
		{
			AITurn();
		}
		else if (computerPlayer == -1 && activePlayer.equals("Player 2"))
		{
			AITurn();
		}
	}
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(() -> {
			Game go = new Game();
			go.setVisible(true);
		});
	}

}
