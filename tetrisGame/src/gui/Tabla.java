package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;

import gui.Pozicija.Smer;

public class Tabla extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private int x, y, poeni = 0;
	private List<Figura> figure=new ArrayList<>();
	private Figura tek, sled;
	private Thread nit = new Thread(this);
	private Color[] boje = new Color[]{
			Color.RED, Color.BLUE,
			Color.YELLOW, Color.GREEN
	};
	private boolean radi;
	private Label lpoeni, ldelova;
	public Tabla(int x, int y) {
		this.x = x; 
		this.y = y; 
		nit.start();
	}
	public void postaviLabele(Label pts, Label br) {
		lpoeni = pts; 
		ldelova = br;
	}
	private void azurirajLabele() {
		if(ldelova==null || lpoeni==null) return;
		lpoeni.setText("Poeni: "+poeni);
		ldelova.setText("Figura:"+(figure.size()+(tek==null?0:1)));
	}
	public void dodaj(Figura f) { figure.add(f); }
	public boolean zauzeto(Pozicija p) throws GNeMoze {
		if(p.x()>=x || p.y()>=y || p.x()<0 || p.y()<0)
		throw new GNeMoze();
		for(Figura f:figure)
		if(f.preko(p))return true;
		if(tek.preko(p)) return true;
		return false;
	}
	private Figura sledeci() {
		Pozicija p = new Pozicija(x/2, 0);
		Color boja = boje[(int)(Math.random()*boje.length)];
		double rnd = Math.random();
		if(rnd<0.5) return new Jednodelni(p,boja);
		else return new Dvodelni(p, boja);
	}
	private synchronized void azuriraj() {
		if(tek == null) {
			tek = sledeci();
			sled = sledeci();
			return;
		}
		try { 
			tek.pomeri(Smer.DOLE, this);
		} catch (GNeMoze e) {
			int r = tek.poz().y();
			boolean rowCompleted = true;
			for(int i=0;i<x;i++) {
				try {
					if(!zauzeto(new Pozicija(i, r))) { 
						rowCompleted = false;
						break;
					}
				} catch (GNeMoze e1) {}
			}
			if(rowCompleted) {
				figure.removeIf(x->x.poz().y()==r);
				poeni += 100;
				boolean imaJos = true;
				while(imaJos) {
					imaJos = false;
					for(Figura f:figure) {
						if(f.moze(Smer.DOLE, this)) {
							imaJos = true;
							try {
								f.pomeri(Smer.DOLE, this);
							} catch (GNeMoze e1) {}
						}
					}
				}
			}
			else figure.add(tek);
			tek = sled; sled = sledeci();
		}
	}
	public int x() { return x; }
	public int y() { return y; }
	public synchronized void pomeri(Smer smer) {
		if(tek == null) return;
		try { 
			tek.pomeri(smer, this); 
		} catch (GNeMoze e) {}
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized (this) {
					while(!radi) wait();
				}
				Thread.sleep(200);
				azuriraj();
				azurirajLabele(); 
				repaint();
			}
		} catch (InterruptedException e) {}
	}
	public synchronized void kreni(int x,int y) {
		this.x = x; this.y = y;
		figure.clear(); tek = sled = null;
		poeni = 0; azurirajLabele();
		repaint(); radi = true; notify();
	}
	public synchronized void stani() { radi = false; }
	public void zavrsi() { nit.interrupt(); }
	@Override
	public void paint(Graphics g) {
		int w = getWidth()/x; int h = getHeight()/y;
		g.setColor(new Color(220,220,220));
		g.fillRect(0, 0, w*x, h*y);
		g.setColor(Color.LIGHT_GRAY);
		for(int i=0;i<=x;i++)
			g.drawLine(w*i, 0, w*i, h*y);
		for(int i=0;i<=y;i++)
			g.drawLine(0, h*i, w*x, h*i);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, w*x, h*y);
		for(Figura f:figure) f.crtaj(this);
		if(tek != null) tek.crtaj(this);
		if(sled!=null&&!tek.poz().equals(sled.poz()))
		sled.crtaj(this);
	}
}