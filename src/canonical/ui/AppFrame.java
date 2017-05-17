package canonical.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import canonical.ApplicationException;
import canonical.EquationTransformer;
import canonical.io.FileReader;
import canonical.io.FileWriter;
import canonical.util.CanonicalFile;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AppFrame extends JFrame {

	private JPanel contentPane;
	
	private JButton btnWriteOutput;
	private JButton btnTransform;
	
	private JTextField textCanonicalForm;
	private JTextField txtEquation;
	private JTextField txtFileName;
	
	/**
	 * Creates the frame.
	 */
	public AppFrame() {
        
		buildUIComponents();
		
		btnWriteOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> equationList = new ArrayList<String>();
				try {
					equationList = FileReader.readFile(txtFileName.getText());
					List<String> canonicalFormList = CanonicalFile.toCanonicalFile(equationList);
					FileWriter.writeToOutputFile(canonicalFormList);
					JOptionPane.showMessageDialog(AppFrame.this, "Contents successfully saved to CanonicalForms.out!", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (ApplicationException ex) {
					JOptionPane.showMessageDialog(AppFrame.this, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnTransform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					EquationTransformer transformer = new EquationTransformer();
					String equation = txtEquation.getText();
					String canonicalForm = transformer.toCanonicalForm(equation);
					textCanonicalForm.setText(canonicalForm);
				} catch (ApplicationException e1) {
					JOptionPane.showMessageDialog(AppFrame.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * Creates and configures all the frame components.
	 */
	private void buildUIComponents() {
        setTitle("Canonical Form Transformer");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[440.00,fill]", "[][][][][][][][][][][]"));
		
		JLabel lblFileMode = new JLabel("Enter the file name with extension to process");
		contentPane.add(lblFileMode, "cell 0 1,alignx left");
		
		JLabel lblFileName = new JLabel("File Name:   ");
		lblFileName.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblFileName, "flowx,cell 0 2,alignx left");
		
		txtFileName = new JTextField();
		txtFileName.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(txtFileName, "cell 0 2,growx");
		txtFileName.setColumns(16);
		
		btnWriteOutput = new JButton("Wite to Output File");
		
        contentPane.add(btnWriteOutput, "cell 0 3,growx");
		
		JLabel lblInteractive = new JLabel("Enter the Equation to transform");
		contentPane.add(lblInteractive, "cell 0 7,alignx left");
		
		JLabel lblEquation = new JLabel("Equation:");
		lblEquation.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblEquation, "flowx,cell 0 8");
		
		JLabel lblResult = new JLabel("Canonical Form:");
		contentPane.add(lblResult, "flowx,cell 0 9");
		
		btnTransform = new JButton("Transform To Canonical Form");
	
		contentPane.add(btnTransform, "cell 0 10,growx");
		
		txtEquation = new JTextField();
		contentPane.add(txtEquation, "cell 0 8,growx");
		txtEquation.setColumns(10);
		
		textCanonicalForm = new JTextField();
		contentPane.add(textCanonicalForm, "cell 0 9,growx");
		textCanonicalForm.setColumns(10);
	}
}
