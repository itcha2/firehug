package net.programmingpandas.firehug;

import java.awt.Button;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.programmingpandas.firehug.Main;

public class GUILayout extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	static JFrame jf = new JFrame();

	Label label = new Label("Target Server");
	TextField target = new TextField();
	Frame fdf = new Frame();
	Button go = new Button("Start Firehug!");
	Button stop = new Button("Stop Firehug!");
	CheckboxGroup cbg = new CheckboxGroup();
	Checkbox insidecb = new Checkbox("inside", cbg, true);
	Checkbox outsidecb = new Checkbox("outside", cbg, false);
	FileDialog fd = new FileDialog(fdf, "Select Configuration...");

	public GUILayout() {
		this.init();
	}

	public void init() {
		this.removeAll();
		setSize(100, 35);
		setLayout(new GridLayout(2, 1));
		Panel p = new Panel(new GridLayout(2, 2));
		p.add(label);
		p.add(target);
		p.add(insidecb);
		p.add(outsidecb);
		add(p);
		add(go);
		fdf.setSize(100, 100);
		fd.setLocationRelativeTo(null);
		fd.setVisible(true);
		go.addActionListener(this);
		stop.addActionListener(this);
	}

	public void start() {
		this.removeAll();
		this.setLayout(new GridLayout(2, 1));
		this.add(new Label("Firehug is running.\n"
				+ " The developers would like to say\n"
				+ " that you're a cool person\n"
				+ " and we love you.\n"
				+ " Have a nice day"));
		add(stop);
		jf.pack();
		Main.running = true;
		Main.main(new String[] { insidecb.getState() ? "inside" : "outside",
				target.getText(), fd.getFile() });
	}

	public void stop() {
		this.removeAll();
		setLayout(new GridLayout(2, 1));
		Panel p = new Panel(new GridLayout(2, 2));
		p.add(label);
		p.add(target);
		p.add(insidecb);
		p.add(outsidecb);
		add(p);
		add(go);
		jf.pack();
		fdf.setSize(100, 100);
		fd.setLocationRelativeTo(null);
		fd.setVisible(true);
		Main.running = false;
	}

	public static void main(String args[]) {
		jf.add(new GUILayout());
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == go)
			start();
		if (source == stop)
			stop();
	}
}
