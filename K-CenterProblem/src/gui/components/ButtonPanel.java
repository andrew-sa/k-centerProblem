package gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import custom.events.ActiveChangeCentersNumberListener;

/**
 * This panel contains the buttons used by the user during the interaction.
 * @author Andrea Mogavero
 *
 */
public class ButtonPanel extends JPanel implements ActiveChangeCentersNumberListener {

	public ButtonPanel(DrawingArea drawingArea)
	{
		k = 0;
		
		this.drawingArea = drawingArea;
		buttonInsertK = new JButton(INITIAL_TEXT_BUTTON_K);
//		buttonInsertUserCenters = new JButton();
		buttonReset = new JButton("Reset");
		buttonToggleDelete = new JButton("Delete cities");
		buttonToggleDelete.setEnabled(false);
		
		isDeleteMode = false;
		
//		buttonInsertUserCenters.setEnabled(false);
//		buttonInsertUserCenters.setVisible(false);
		
		add(buttonInsertK);
		add(buttonToggleDelete);
//		add(buttonInsertUserCenters);
		add(buttonReset);
		addButtonsListener();
	}
	
	/**
	 * This method adds a listener to each button.
	 */
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
				Object response = JOptionPane.showInputDialog(null, "Choose number of centers:", "Choose centers..", JOptionPane.QUESTION_MESSAGE, null, choices, 1);
				if (null != response)
				{
					buttonInsertK.setEnabled(false);
					
					/* Back to the normal mode */
					buttonToggleDelete.setText("Delete cities");
					buttonToggleDelete.setEnabled(false);
					isDeleteMode = false;
//					drawingArea.unsetDrawingDeleteCities();		//It's unless because at line 82 this method calls drawingArea.setDrawingAreaForUserCenters(k) that provide to delete and, then, set the correct mouse listeners 
					
					if (buttonInsertK.getText().equals(CHANGED_TITLE_BUTTOM_K))
					{
						drawingArea.deleteSolution();
					}
					
					k = (int) response;
					System.out.println("Number of centers: " + k);
					drawingArea.placeAlgorithmCenters(k);
					
//					buttonInsertUserCenters.setText("Insert your solution for " + k + " centers");
//					buttonInsertK.setVisible(false);
//					buttonInsertUserCenters.setEnabled(true);
//					buttonInsertUserCenters.setVisible(true);
					drawingArea.setDrawingAreaForUserCenters(k);
				}
			}
	
		});
		
//		buttonInsertUserCenters.addActionListener(new ActionListener()
//		{
//
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				buttonInsertUserCenters.setEnabled(false);
//				drawingArea.setDrawingAreaForUserCenters(k);
//			}
//	
//		});
		
		buttonReset.addActionListener(new ActionListener() 
		{
	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				buttonInsertK.setText(INITIAL_TEXT_BUTTON_K);
				buttonInsertK.setEnabled(true);
				buttonToggleDelete.setEnabled(false);
//				buttonInsertK.setVisible(true);
//				buttonInsertUserCenters.setEnabled(false);
//				buttonInsertUserCenters.setVisible(false);
				drawingArea.reset();
			}
			
		});
		
		
		buttonToggleDelete.addActionListener(new ActionListener() 
		{
	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (isDeleteMode)
				{
					buttonToggleDelete.setText("Delete cities");
					isDeleteMode = false;
					drawingArea.unsetDrawingDeleteCities();
				}
				else
				{
					buttonToggleDelete.setText("Back to normal mode");
					isDeleteMode = true;
					drawingArea.setDrawingDeleteCities();
				}
				
			}
			
		});
	}
	
	@Override
	public void activeChangeCentersNumber()
	{
		buttonInsertK.setText(CHANGED_TITLE_BUTTOM_K);
		buttonInsertK.setEnabled(true);
		buttonToggleDelete.setEnabled(true);
	}

	private final static String INITIAL_TEXT_BUTTON_K = "INSERT Number of Centers";
	private final static String CHANGED_TITLE_BUTTOM_K = "CHANGE Number of Centers";
	private JButton buttonInsertK;
	private JButton buttonReset;
	private JButton buttonToggleDelete;
//	private JButton buttonInsertUserCenters;
	private DrawingArea drawingArea;
	private int k;
	private boolean isDeleteMode;
}
