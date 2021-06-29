package medima;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CalculatorFrame {

		private static final int WINDOW_WIDTH = 410;
		private static final int WINDOW_HEIGHT = 600;

		private static final int BUTTON_WIDTH = 80;
		private static final int BUTTON_HEIGHT = 70;

		private static final int MARGIN_X = 20;
		private static final int MARGIN_Y = 60;

		private final JFrame window;
		private final JFrame Record;

		private final JTextArea recordText;
		private final JTextArea inText;

		private final JMenuBar menuBar;

		public CalculatorFrame() {
				window = new JFrame("Calculator");
				window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				window.setLocationRelativeTo(null);

				int[] x = {MARGIN_X, MARGIN_X + 90, 200, 290, 380};
				int[] y = {MARGIN_Y, MARGIN_Y + 100, MARGIN_Y + 180, MARGIN_Y + 260, MARGIN_Y + 340,
						MARGIN_Y + 420};

				inText = new JTextArea("0");
				inText.setEditable(false);
				inText.setFont(new Font("Comic Sans MS", Font.PLAIN, 33));
				inText.setBackground(Color.yellow);
				JScrollPane scrollPane = new JScrollPane(inText);
				scrollPane.setBounds(x[0], y[0], 350, 70);
				scrollPane.setBackground(Color.CYAN);
				window.add(scrollPane);
				initBtns(x, y);

				Record = new JFrame("历史记录");
				recordText = new JTextArea("\n");
				initRecord();

				JTextField hello = new JTextField("    你好！老师");
				hello.setBounds(x[1], 30, x[3]-x[1]-10, 30);
				hello.setVisible(true);
				hello.setEditable(false);
				hello.setFont(new Font("宋体", Font.BOLD, 20));
				hello.setForeground(Color.orange);
				hello.setBackground(Color.CYAN);
				hello.setOpaque(true);
				window.add(hello);

				menuBar = new JMenuBar();
				menuBar.setVisible(true);
				menuBar.setBackground(Color.orange);
				menuBar.setOpaque(true);
				initMenuitem();

				window.setJMenuBar(menuBar);
				window.setLayout(null);
				window.setResizable(false);
				window.setDefaultCloseOperation(
						JFrame.EXIT_ON_CLOSE); // Close button clicked? = End The process
				window.setVisible(true);

		}

		private void initRecord() {
				Record.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				Record.setLocationRelativeTo(null);
				Record.setLayout(null);
				Record.setResizable(false);
				recordText.setEditable(false);
				JScrollPane scrollPane_hist = new JScrollPane(recordText);
				scrollPane_hist.setBounds(0, 0, 410, 600);
				Record.add(scrollPane_hist);
		}

		private void initMenuitem() {
				JMenu record = new JMenu("记录");
				JMenu Exit = new JMenu("退出");
				menuBar.add(record);
				menuBar.add(Exit);
				JMenuItem save = new JMenuItem("保存记录");
				JMenuItem read = new JMenuItem("查看记录");
				JMenuItem exit = new JMenuItem("退出");
				record.add(read);
				record.add(save);
				Exit.add(exit);

				record.setBackground(Color.yellow);
				exit.setBackground(Color.yellow);
				menuBar.add(record);
				menuBar.add(Exit);
				menuBar.setVisible(true);
				addActionListen(save, read, exit);
		}

		private void addActionListen(JMenuItem save, JMenuItem read, JMenuItem exit) {
				save.addActionListener(this::saveRecord);
				read.addActionListener(event ->
						Record.setVisible(true));
				exit.addActionListener(event ->
						System.exit(0));
		}

		private void initBtns(int[] x, int[] y) {
				initBtn("C", x[0], y[1], event -> inText.setText("0")).setForeground(Color.CYAN);
				initBtn("<-", x[1], y[1], event -> {
						String str = inText.getText();
						StringBuilder str2 = new StringBuilder();
						for (int i = 0; i < (str.length() - 1); i++) {
								str2.append(str.charAt(i));
						}
						if (str2.toString().equals("")) {
								inText.setText("0");
						} else {
								inText.setText(str2.toString());
						}
				}).setForeground(Color.green);
				initBtn("%", x[2], y[1], event -> inText.setText(inText.getText() + "%"))
						.setForeground(Color.blue);
				initBtn("/", x[3], y[1], event -> inText.setText(inText.getText() + "/"))
						.setForeground(Color.blue);
				initBtn("7", x[0], y[2],
						event -> inText.setText(isText0OrError() ? "7" : inText.getText() + "7"));
				initBtn("8", x[1], y[2],
						event -> inText.setText(isText0OrError() ? "8" : inText.getText() + "8"));
				initBtn("9", x[2], y[2],
						event -> inText.setText(isText0OrError() ? "9" : inText.getText() + "9"));
				initBtn("*", x[3], y[2], event -> inText.setText(inText.getText() + "*"))
						.setForeground(Color.blue);
				initBtn("4", x[0], y[3],
						event -> inText.setText(isText0OrError() ? "4" : inText.getText() + "4"));
				initBtn("5", x[1], y[3],
						event -> inText.setText(isText0OrError() ? "5" : inText.getText() + "5"));
				initBtn("6", x[2], y[3],
						event -> inText.setText(isText0OrError() ? "6" : inText.getText() + "6"));
				initBtn("-", x[3], y[3],
						event -> inText.setText(isText0OrError() ? "-" : inText.getText() + "-"))
						.setForeground(Color.blue);
				initBtn("1", x[0], y[4],
						event -> inText.setText(isText0OrError() ? "1" : inText.getText() + "1"));
				initBtn("2", x[1], y[4],
						event -> inText.setText(isText0OrError() ? "2" : inText.getText() + "2"));
				initBtn("3", x[2], y[4],
						event -> inText.setText(isText0OrError() ? "3" : inText.getText() + "3"));
				initBtn("+", x[3], y[4], event -> inText.setText(inText.getText() + "+"))
						.setForeground(Color.blue);
				initBtn(".", x[0], y[5], event -> {
						char c = inText.getText().charAt(inText.getText().length() - 1);
						if (c >= '0' && c <= '9') {
								inText.setText(inText.getText() + ".");
						} else {
								inText.setText(inText.getText() + "0.");
						}
				});

				initBtn("0", x[1], y[5],
						event -> inText.setText(isText0OrError() ? "0" : inText.getText() + "0"));

				initBtn("=", x[2], y[5], event -> {
						String res = Calculator.calculate(inText.getText());
						if (!"输入错误".equals(res)) {
								recordText
										.append(new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date()) + "\n");
								recordText.setFont(new Font("宋体", Font.PLAIN, 20));
								recordText.append(inText.getText() + "=" + (isNumber(res) ? res : "") + "\n\n");
						}
						inText.setText(res);
				}).setSize(2 * BUTTON_WIDTH + 10, BUTTON_HEIGHT);

		}

		/**
		 * @param label Button的名字
		 * @param x     Button的横坐标
		 * @param y     Button的纵坐标
		 * @param event 监听器
		 * @return Button的一个实例对象
		 */
		private JButton initBtn(String label, int x, int y, ActionListener event) {
				JButton btn = new JButton(label);
				if ("=".equals(label)) {
						btn.setForeground(Color.RED);
				}
				btn.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
				btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 28));
				btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btn.addActionListener(event);
				btn.setOpaque(true);
				btn.setBackground(Color.yellow);
				btn.setFocusable(false);
				window.add(btn);
				return btn;
		}

		private static boolean isNumber(String str) {
				try {
						Double.parseDouble(str);
						return true;
				} catch (Exception e) {
						return false;
				}
		}

		private boolean isText0OrError() {
				return "0".equals(inText.getText()) || "输入错误".equals(inText.getText());
		}

		private void saveRecord(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "pdf", "docx");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showSaveDialog(new JPanel());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
						String path = chooser.getSelectedFile().getPath();
						try {
								File f = new File(path + ".txt");
								DataOutputStream out = new DataOutputStream(
										new FileOutputStream(f.getAbsolutePath()));
								f.createNewFile();
								out.writeUTF(recordText.getText());
						} catch (Exception e) {
								JOptionPane.showMessageDialog(window, "保存失败", "提示", JOptionPane.WARNING_MESSAGE);

						}
				}

		}
}
