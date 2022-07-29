package gui;

import java.awt.Color;
import java.awt.Graphics;

import gui.Pozicija.Smer;

public class Jednodelni extends Figura {
	public Jednodelni(Pozicija p, Color b) { super(p, b); }
	@Override
	public boolean preko(Pozicija po) { return p.equals(p); }
	@Override
	public boolean moze(Smer s, Tabla t) {
		try { 
			return !t.zauzeto(p.pom(s));
		} catch (GNeMoze e){ return false; }
	}
	@Override
	protected void crt(Graphics g,int w,int h) { g.fillOval(p.x()*w, p.y()*h, w, h); }
}