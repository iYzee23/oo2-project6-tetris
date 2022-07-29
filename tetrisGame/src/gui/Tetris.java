package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gui.Pozicija.Smer;

public class Tetris extends Frame{
	private static final long serialVersionUID = 1L;
	private static final int W = 10, H = 20;
	private Tabla tabla = new Tabla(W, H);
	private Label poeni = new Label("Poeni: 0");
	private Label delova = new Label("Figura: 0");
	private TextField sirina = new TextField("10"), visina = new TextField("20");
	private Button stani = new Button("Stani");
	public Tetris() {
		super("Tetris");
		setSize(317, 600); dodajMeni();
		add(tabla, BorderLayout.CENTER);
		add(bottom(), BorderLayout.SOUTH);
		dodajOsluskivace();setVisible(true);
	}
	private void azuriraj(boolean traje) {
		stani.setEnabled(traje);
		sirina.setEnabled(!traje);
		visina.setEnabled(!traje);
	}
	private void dodajMeni() {
		MenuBar bar = new MenuBar();
		Menu menu = new Menu("Akcija");
		setMenuBar(bar); bar.add(menu);
		MenuItem novaIgra = new MenuItem("Nova igra", new MenuShortcut('N'));
		menu.add(novaIgra);
		novaIgra.addActionListener(
			e->{tabla.kreni(
				Integer.parseInt(sirina.getText()),
				Integer.parseInt(visina.getText())
			);
			azuriraj(true);
		});
		menu.addSeparator();
		MenuItem zatvori = new MenuItem("Zatvori",new MenuShortcut('Z'));
		menu.add(zatvori);
		zatvori.addActionListener(
				e->{tabla.zavrsi(); 
				dispose();
		});
	}
	private Panel bottom() {
		Panel p = new Panel(new GridLayout(2,2));
		poeni.setAlignment(Label.CENTER);
		delova.setAlignment(Label.CENTER);
		poeni.setFont(new Font(null,Font.BOLD, 18));
		delova.setFont(new Font(null,Font.BOLD, 18));
		tabla.postaviLabele(poeni, delova);
		stani.setEnabled(false);
		stani.addActionListener(e->{
			tabla.stani();
			azuriraj(false);
		});
		Panel xy = new Panel();
		xy.add(new Label("x, y:"));
		xy.add(sirina);xy.add(visina);
		p.add(poeni);p.add(stani);
		p.add(delova);p.add(xy);
		return p;
	}
	private void dodajOsluskivace() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				tabla.zavrsi();
				dispose();
			}
		});
		tabla.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					tabla.pomeri(Smer.LEVO);
					break;
				case KeyEvent.VK_RIGHT:
					tabla.pomeri(Smer.DESNO);
					break;
				case KeyEvent.VK_DOWN:
					tabla.pomeri(Smer.DOLE);
					break;
				}
			}
		});
	}
	public static void main(String[] args) { new Tetris(); }
}