import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;

	private JButton b_start, b_stop;

	private Board board;

	// TODO update with number of group label. See practice statement.
	private final int N_BALL = 10;
	private Ball[] balls = new Ball[N_BALL];
	private Hilo[] hilo = new Hilo[N_BALL];

	public Billiards() {

		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));

		initBalls();

		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("Práctica programación concurrente objetos móviles independientes");
		setResizable(false);
		setVisible(true);
	}

	private void initBalls() {
		// TODO init balls
		for (int i = 0; i < N_BALL; i++) {
			balls[i] = new Ball();
		}
	}

	private class StartListener implements ActionListener {
		@Override
		public synchronized void actionPerformed(ActionEvent arg0) {
			// TODO Code is executed when start button is pushed
			board.setBalls(balls);
			for (int i = 0; i < N_BALL; i++) {
				hilo[i] = new Hilo(balls[i]);
				hilo[i].start();
			}
		}
	}

	private class StopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Code is executed when stop button is pushed
			for (int i = 0; i < N_BALL; i++) {
				hilo[i].interrupt();
			}
		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
		public class Hilo extends Thread {
		private Ball ball;

		public Hilo(Ball bola) {
			ball = bola;
		}

		@Override
		public void run() {
			try {
				for (;;) {
					ball.move();
					board.repaint();
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
