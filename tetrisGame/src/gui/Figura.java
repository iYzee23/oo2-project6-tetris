package gui;

import java.awt.Color;
import java.awt.Graphics;

import gui.Pozicija.Smer;

public abstract class Figura {
	protected Pozicija p;
	protected Color boja;
	public Figura(Pozicija po, Color b) {
		p = po; 
		boja = b; 
	}
	protected abstract void crt(Graphics g, int w, int h);
	public void crtaj(Tabla t) {
		Graphics g = t.getGraphics();
		int w = t.getWidth() / t.x();
		int h = t.getHeight() / t.y();
		g.setColor(boja); crt(g, w, h);
	}
	public abstract boolean preko(Pozicija p);
	public abstract boolean moze(Smer smer, Tabla t);
	public void pomeri(Smer smer, Tabla p) throws GNeMoze {
		if(!moze(smer,p)) throw new GNeMoze();
		this.p.pomeri(smer);
	}
	public Pozicija poz() { return p; }
}