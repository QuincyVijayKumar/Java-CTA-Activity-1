package sdmcet.cse.oop;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;

class GradingSystem extends JFrame implements ActionListener {

	// Creating of GUI Components
	Container contentPane1;
	JButton b;
	JPanel p1, p2, p3, p4, p5, p6, p7, p8;
	JLabel l, totalMarks, IA1, IA2, IA3, CTA, SEE, grade;
	JTextField t1, t2, t3, t4, t5;

	// Instantiation of GUI Components
	public GradingSystem(String title) {
		super(title);
		try {
			contentPane1 = this.getContentPane();

			b = new JButton("Calculate");
			p1 = new JPanel();
			p2 = new JPanel();
			p3 = new JPanel();
			p4 = new JPanel();
			p5 = new JPanel();
			p6 = new JPanel();
			p7 = new JPanel();
			p8 = new JPanel();

			l = new JLabel("Grade Calculator");
			totalMarks = new JLabel("Total Marks:");
			grade = new JLabel("Grade:");
			IA1 = new JLabel("Enter IA-1 Marks:");
			IA2 = new JLabel("Enter IA-2 Marks:");
			IA3 = new JLabel("Enter IA-3 Marks:");
			CTA = new JLabel("Enter CTA Marks:");
			SEE = new JLabel("Enter SEE Marks:");

			t1 = new JTextField(5);
			t2 = new JTextField(5);
			t3 = new JTextField(5);
			t4 = new JTextField(5);
			t5 = new JTextField(5);

			b.addActionListener(this);
			p1.add(l);
			p2.add(IA1);
			p2.add(t1);
			p3.add(IA2);
			p3.add(t2);
			p4.add(IA3);
			p4.add(t3);
			p5.add(CTA);
			p5.add(t4);
			p6.add(SEE);
			p6.add(t5);
			p7.add(b);

			// Creating a Border
			Border border = BorderFactory.createBevelBorder(2);
			totalMarks.setBorder(border);
			p8.add(totalMarks);

			grade.setBorder(border);
			p8.add(grade);

			// Setting a Layout
			this.setLayout(new GridLayout(8, 0, 5, 5));

			contentPane1.add(p1);
			contentPane1.add(p2);
			contentPane1.add(p3);
			contentPane1.add(p4);
			contentPane1.add(p5);
			contentPane1.add(p6);
			contentPane1.add(p7);
			contentPane1.add(p8);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,	"Exception: You must enter values to all the informations asked, if absent please put zero to it");
		}
	}

	double ia1;
	double ia2;
	double ia3;
	double cta;
	double cie;
	double see;
	double tMarks;

	// Computes CIE
	double computeCIE() {
		ia1 = Double.parseDouble(t1.getText());
		ia2 = Double.parseDouble(t2.getText());
		ia3 = Double.parseDouble(t3.getText());
		cta = Double.parseDouble(t4.getText());

		try {

			if (ia1 > 20 || ia2 > 20 || ia3 > 20)
				throw new IAMarksHighException();
			else if (ia1 < 0 || ia2 < 0 || ia3 < 0)
				throw new IAMarksLowException();
			else if (cta > 10)
				throw new CTAMarksHighException();
			else if (cta < 0)
				throw new CTAMarksLowException();
		} catch (IAMarksHighException imhe) {
			JOptionPane.showMessageDialog(null, "Exception Occurred: " + imhe);
			System.exit(EXIT_ON_CLOSE);
		} catch (IAMarksLowException imle) {
			JOptionPane.showMessageDialog(null, "Exception Occurred: " + imle);
			System.exit(EXIT_ON_CLOSE);
		} catch (CTAMarksHighException ctahe) {
			JOptionPane.showMessageDialog(null, "Exception Occurred: " + ctahe);
			System.exit(EXIT_ON_CLOSE);
		} catch (CTAMarksLowException ctale) {
			JOptionPane.showMessageDialog(null, "Exception Occurred: " + ctale);
			System.exit(EXIT_ON_CLOSE);
		}

		if (ia1 >= ia2 && ia3 >= ia2)
			cie = ia1 + ia3 + cta;
		else if (ia1 >= ia3 && ia2 >= ia3)
			cie = ia1 + ia2 + cta;
		else
			cie = ia2 + ia3 + cta;

		int roundCie = (int) Math.round(cie);

		return roundCie;

	}

	// Computes Total Marks
	double totalMarks() {
		see = Double.parseDouble(t5.getText());

		try {
			if (see > 100)
				throw new SEEMarksHighException();
			else if (see < 0)
				throw new SEEMarksLowException();
		} catch (SEEMarksHighException seemhe) {
			JOptionPane.showMessageDialog(null, "Exception Occurred: " + seemhe);
			System.exit(EXIT_ON_CLOSE);
		} catch (SEEMarksLowException seemle) {
			JOptionPane.showMessageDialog(null, "Exception Occurred: " + seemle);
			System.exit(EXIT_ON_CLOSE);
		}

		if (see == 38 || see == 39) {
			see = 40;
			tMarks = computeCIE() + (see / 2);
		} else
			tMarks = computeCIE() + (see / 2);

		int roundTMarks = (int) Math.round(tMarks);

		return roundTMarks;
	}

	// Computes Grade
	char computeGrade() {
		if (totalMarks() >= 90 && totalMarks() < 100)
			return 'S';
		else if (totalMarks() >= 80 && totalMarks() < 89)
			return 'A';
		else if (totalMarks() >= 70 && totalMarks() < 79)
			return 'B';
		else if (totalMarks() >= 60 && totalMarks() < 69)
			return 'C';
		else if (totalMarks() >= 50 && totalMarks() < 59)
			return 'D';
		else if (totalMarks() >= 40 && totalMarks() < 49)
			return 'E';
		else
			return 'F';
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			double marks = computeCIE();
			if (marks < 20)
				throw new DetainException();
			else {
				totalMarks.setText(" Total Marks:  " + totalMarks() + "     ");
				grade.setText("	Grade:  " + computeGrade());
			}
		} catch (NumberFormatException ne) {
			JOptionPane.showMessageDialog(null, "Exception: You must enter values to all the informations asked, if absent please put zero to it");
			System.exit(EXIT_ON_CLOSE);
		} catch (DetainException de) {
			JOptionPane.showMessageDialog(null, "Student Is Detained From Taking Sem End Examination");
			System.exit(EXIT_ON_CLOSE);
		}
	}

}

// Custom Exception Classes
class IAMarksHighException extends Exception {
	public String toString() {
		return "IA Marks should not be more than 20";
	}
}

class IAMarksLowException extends Exception {
	public String toString() {
		return "IA Marks should not be lower than 0";
	}
}

class CTAMarksHighException extends Exception {
	public String toString() {
		return "CTA Marks should not be more than 10";
	}
}

class CTAMarksLowException extends Exception {
	public String toString() {
		return "CTA Marks should not be less than 0";
	}
}

class SEEMarksHighException extends Exception {
	public String toString() {
		return "SEE Marks should not be more than 100";
	}
}

class SEEMarksLowException extends Exception {
	public String toString() {
		return "SEE Marks should not be less than 0";
	}
}

class DetainException extends Exception {
	public String toString() {
		return "Detained";
	}
}

//Class Containing Main Method
public class GradingSystemDemo {

	public static void main(String[] args) throws IOException {

		// Dynamic Method Dispatch
		JFrame f = new GradingSystem("Student Grading System");

		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 400, 300);
		f.setVisible(true);
	}

}
