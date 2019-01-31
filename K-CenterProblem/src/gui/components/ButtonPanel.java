package gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import custom.event.ActiveChangeCentersNumberListener;

public class ButtonPanel extends JPanel implements ActiveChangeCentersNumberListener {

	public ButtonPanel(DrawingArea drawingArea)
	{
		k = 0;
		
		this.drawingArea = drawingArea;
		buttonInsertK = new JButton(INITIAL_TEXT_BUTTON_K);
//		buttonInsertUserCenters = new JButton();
		buttonReset = new JButton("Reset");
		
//		buttonInsertUserCenters.setEnabled(false);
//		buttonInsertUserCenters.setVisible(false);
		
		add(buttonInsertK);
//		add(buttonInsertUserCenters);
		add(buttonReset);
		addButtonsListener();
	}
	
	private void addButtonsListener()
	{
		buttonInsertK.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						int n = drawingArea.getCitiesNumber();
						Integer[] choices = new Integer[n];
						for (int i = 0; i < n; i++)
						{
							choices[i] = i + 1;
						}
						Object response = JOptionPane.showInputDialog(null, "Choose centers..", "Choose number of centers", JOptionPane.QUESTION_MESSAGE, null, choices, 1);
						if (null != response)
						{
							buttonInsertK.setEnabled(false);
							
							if (buttonInsertK.getText().equals(CHANGED_TITLE_BUTTOM_K))
							{
								drawingArea.deleteSolution();
							}
							
							k = (int) response;
							System.out.println("Number of centers: " + k);
							drawingArea.placeAlgorithmCenters(k);
							
//							buttonInsertUserCenters.setText("Insert your solution for " + k + " centers");
//							buttonInsertK.setVisible(false);
//							buttonInsertUserCenters.setEnabled(true);
//							buttonInsertUserCenters.setVisible(true);
							drawingArea.setDrawingAreaForUserCenters(k);
						}
					}
			
				});
		
//		buttonInsertUserCenters.addActionListener(new ActionListener()
//				{
//
//					@Override
//					public void actionPerformed(ActionEvent e)
//					{
//						buttonInsertUserCenters.setEnabled(false);
//						drawingArea.setDrawingAreaForUserCenters(k);
//					}
//			
//				});
		
		buttonReset.addActionListener(new ActionListener() 
				{
			
					@Override
					public void actionPerformed(ActionEvent e)
					{
						buttonInsertK.setText(INITIAL_TEXT_BUTTON_K);
						buttonInsertK.setEnabled(true);
//						buttonInsertK.setVisible(true);
//						buttonInsertUserCenters.setEnabled(false);
//						buttonInsertUserCenters.setVisible(false);
						drawingArea.reset();
					}
					
				});
	}
	
	@Override
	public void activeChangeCentersNumber()
	{
		buttonInsertK.setText(CHANGED_TITLE_BUTTOM_K);
		buttonInsertK.setEnabled(true);
	}

	private final static String INITIAL_TEXT_BUTTON_K = "INSERT Number of Centers";
	private final static String CHANGED_TITLE_BUTTOM_K = "CHANGE Number of Centers";
	private JButton buttonInsertK;
	private JButton buttonReset;
//	private JButton buttonInsertUserCenters;
	private DrawingArea drawingArea;
	private int k;
}
