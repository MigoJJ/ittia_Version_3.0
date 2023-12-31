package je.panse.doro.samsara.EMR_OBJ_vitalsign;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import je.panse.doro.GDSEMR_frame;

public class EMR_vitalsign extends JFrame {

	public static JTextField inputTextField;
	public static JTextArea outputTextArea;
	public static JTextArea desoutputTextArea;

    public EMR_vitalsign() {
        // Set layout manager for the JFrame
        setTitle("GDS Vital Signs");
        setSize(330, 220);
        setLocation(1460, 215);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
	        // Create and add components to the JFrame
	        JPanel northPanel = new JPanel();
	        northPanel.setLayout(new BorderLayout());
	        northPanel.setPreferredSize(new Dimension(northPanel.getPreferredSize().width, 25));
	        inputTextField = new JTextField();
	        inputTextField.setHorizontalAlignment(JTextField.CENTER); // Set the cursor alignment to center
	        northPanel.add(inputTextField, BorderLayout.CENTER);
	        add(northPanel, BorderLayout.NORTH);
	
	        outputTextArea = new JTextArea();
	        outputTextArea.setRows(4);
	        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
	        
	        desoutputTextArea = new JTextArea();
	        desoutputTextArea.setRows(1);
	        desoutputTextArea.setText("at GDS, Left seated position, Regular");
	        JScrollPane desoutputScrollPane = new JScrollPane(desoutputTextArea);
	        
	        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, outputScrollPane, desoutputScrollPane);
	        add(splitPane, BorderLayout.CENTER);
	
	        JButton clearButton = new JButton("Clear");
	        clearButton.addActionListener(e -> clearFieldArea());
	        JButton saveButton = new JButton("Save");
	        saveButton.addActionListener(e -> buttonvitalsign());
	        JButton quitButton = new JButton("Quit");
	        quitButton.addActionListener(e -> buttonquit());
	
	        JPanel southPanel = new JPanel();
	        southPanel.setLayout(new FlowLayout());
	        southPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 35));
	        southPanel.add(clearButton);
	        southPanel.add(saveButton);
	        southPanel.add(quitButton);
        add(southPanel, BorderLayout.SOUTH);
        
        inputTextField.addActionListener(e -> {
            String input = inputTextField.getText();
            System.out.println(input + " <--- input");
            if (input != null && (input.equals("h") || input.equals("i") || input.equals("b") 
            		|| input.equals("g") || input.equals("r")) || input.equals("o")) {
                descriptionOfVitalSigns(input);
            } else {
            EMR_vitalsign_array.printInputArrayList(input);
            }
            inputTextField.setText("");
        });
    }

    private void clearFieldArea() {
    	outputTextArea.setText("");
        desoutputTextArea.setText("at GDS, Left seated position, Regular");
       EMR_vitalsign_array.clearmodifiedArray();
    }
    
    private void buttonvitalsign() {
        GDSEMR_frame.setTextAreaText(5, "\n   " + desoutputTextArea.getText() + "\n\t");
	        String text = outputTextArea.getText();
	        if (text.contains("] mmHg      BP [")) {
	            text = text.replace("] mmHg      BP ["," / "); // replace " / " with a space, modify this according to your needs
	            outputTextArea.setText(text);
	        }
        GDSEMR_frame.setTextAreaText(5, outputTextArea.getText());
        clearFieldArea();
    }
    
    private void buttonquit() {
    	clearFieldArea();
        dispose();
    }
    
    public void descriptionOfVitalSigns(String input) {
        String dta = desoutputTextArea.getText();
        String returndta = EMR_vitalsign_desreturn.main(input, dta);
        desoutputTextArea.setText(returndta);
    }
    
    public static void main(String[] args) {
        EMR_vitalsign emr = new EMR_vitalsign();
        emr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emr.setVisible(true);
    }
}
