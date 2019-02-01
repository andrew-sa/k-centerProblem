package gui.frames;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import gui.components.LegendPanel;

/**
 * This frame contains: the text area that shows messages to user, and the legend panel.
 * @author Andrea Mogavero
 *
 */
public class ComputingFrame extends JFrame {
	
//	public ComputingFrame(double shapeSize)
//	{
//		textArea = new TextArea();
//		textArea.setEditable(false);
//		textArea.setBackground(new Color(255, 255, 255));
//		legendPanel = new LegendPanel(shapeSize);
//		getContentPane().add(textArea, BorderLayout.CENTER);
//		getContentPane().add(legendPanel, BorderLayout.SOUTH);
//	}
	
	public ComputingFrame(double shapeSize, Color cityColor, Color centerColor, Color firstCenterColor,
			Color userCenterColor)
	{
		textArea = new JTextArea(20, 1);
		textArea.setEditable(false);
		textArea.setBackground(new Color(255, 255, 255));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		scrollPaneTextArea = new JScrollPane(textArea);
		legendPanel = new LegendPanel(cityColor, centerColor, firstCenterColor, userCenterColor);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(scrollPaneTextArea);
		getContentPane().add(legendPanel);
	}
	
	public ComputingFrame(Color cityColor, Color centerColor, Color firstCenterColor,
			Color userCenterColor)
	{
		textArea = new JTextArea(20, 1);
		textArea.setEditable(false);
		textArea.setBackground(new Color(255, 255, 255));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		scrollPaneTextArea = new JScrollPane(textArea);
		legendPanel = new LegendPanel(cityColor, centerColor, firstCenterColor, userCenterColor);
//		scrollPaneLegendPanel = new JScrollPane(legendPanel);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(scrollPaneTextArea);
		getContentPane().add(legendPanel);
	}

	/**
	 * Delete the text of the text area.
	 */
	public void clear()
	{
		textArea.setText(null);
		scrollDown();
	}
	
	/**
	 * Write the string in the text area.
	 * @param str the string to write
	 */
	public void write(String str)
	{
		textArea.append(str);
		scrollDown();
	}
	
	/**
	 *  Write the string and new line in the text area.
	 * @param str the string to write
	 */
	public void writeln(String str)
	{
		textArea.append(str + "\n");
		scrollDown();
	}
	
	/**
	 * Write a new line in the text area.
	 */
	public void writeln()
	{
		textArea.append("\n");
		scrollDown();
	}
	
	/**
	 * Write the separator in the text area.
	 */
	public void writeSeparator()
	{
		textArea.append("\t- - - - - - - - - - - - - - - - - - -");
		scrollDown();
	}
	
	/**
	 * Scroll down the text area.
	 */
	private void scrollDown()
	{
		textArea.setCaretPosition(textArea.getText().length());
	}
	
	private JTextArea textArea;
	private JScrollPane scrollPaneTextArea;
	private LegendPanel legendPanel;
//	private JScrollPane scrollPaneLegendPanel;

}
