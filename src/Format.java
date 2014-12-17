import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;


public class Format extends JFrame {

	private JPanel contentPane;
	private JTextField tf_source;
	private JTextField tf_dest;
	private JTextArea ta_source;
	private JTextArea ta_dest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		Format frame = new Format();
		frame.setVisible(true);
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Format frame = new Format();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	public Format() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 664);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tf_source = new JTextField();
		tf_source.setText("= & %2C %5B %7B %22 %3A %7D %5D");
		tf_source.setBounds(26, 37, 86, 20);
		contentPane.add(tf_source);
		tf_source.setColumns(10);
		
		tf_dest = new JTextField();
		tf_dest.setText(": NL , [ { \" : } ]");
		tf_dest.setBounds(386, 37, 86, 20);
		contentPane.add(tf_dest);
		tf_dest.setColumns(10);
		
		ta_dest = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(ta_dest);
		scrollPane.setBounds(386, 68, 338, 477);
		contentPane.add(scrollPane);
		
		JButton btnFormat = new JButton("Format");
		btnFormat.addActionListener(format);
		btnFormat.setBounds(344, 574, 89, 23);
		contentPane.add(btnFormat);
		
		ta_source = new JTextArea();
		JScrollPane scrollPanedest = new JScrollPane(ta_source);
		scrollPanedest.setBounds(26, 68, 338, 477);
		contentPane.add(scrollPanedest);
		
		ta_source.setLineWrap(true);
		ta_source.setWrapStyleWord(true);
		
		ta_dest.setLineWrap(true);
		ta_dest.setWrapStyleWord(true);
		
		tf_discard = new JTextField();
		tf_discard.setText("?");
		tf_discard.setBounds(26, 6, 86, 20);
		contentPane.add(tf_discard);
		tf_discard.setColumns(10);
		
		DefaultCaret caret = (DefaultCaret)ta_source.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		DefaultCaret caret2 = (DefaultCaret)ta_dest.getCaret();
		caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		
	}
	
	private ActionListener format = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			if(!validateInputs()){
				JOptionPane.showMessageDialog(Format.this, "No hay la misma cantidad de regexs que de replacements.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String discard = tf_discard.getText();
			
			String source = ta_source.getText();
			
			int index;
			
			if(source.contains(discard))
				index = source.indexOf(discard)+1;
			else
				index = 0;
			
			
			source = source.substring(index);
			
			String[] regexs = tf_source.getText().split(" "); 
			String[] replacements = tf_dest.getText().split(" ");
			
			for(int i=0; i<regexs.length;i++){
				String regex = regexs[i];
				String replacement = replacements[i];
				
				if("NL".equals(replacement))
					replacement = "\n";
				
				source = source.replaceAll(regex, replacement);
				
			}
			source = source.replaceAll(",", ",\n");
			source = source.replaceAll("%20", " ");
			
			ta_dest.setText("");
			ta_dest.append(source);
			ta_dest.setCaretPosition(ta_dest.getDocument().getLength());
			
			
		}
	};
	private JTextField tf_discard;
	
	private boolean validateInputs(){
		
		int regexs = tf_source.getText().split(" ").length;
		int replacements = tf_dest.getText().split(" ").length;
		
		if(regexs==replacements)
			return true;
		else
			return false;
		
	}
}
