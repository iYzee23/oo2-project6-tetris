package gui;

import java.awt.Color;
import java.awt.Graphics;

import gui.Pozicija.Smer;

public class Dvodelni extends Figura {
	public Dvodelni(Pozicija p, Color b) { super(p, b); }
		@Override
	public boolean preko(Pozicija p) {
		return this.p.equals(p) || this.p.pom(Smer.DESNO).equals(p);
	}
	@Override
	public boolean moze(Smer s, Tabla t) {
		try {
			if(s == Smer.DOLE) {
				return !t.zauzeto(p.pom(Smer.DOLE)) && !t.zauzeto(p.pom(Smer.DOLE).pom(Smer.DESNO));
			} 
			else { 
				if(s == Smer.LEVO) return !t.zauzeto(p.pom(Smer.LEVO));
				else return !t.zauzeto(p.pom(Smer.DESNO).pom(Smer.DESNO));
			}
		} catch (GNeMoze e) { return false; }
	}
	@Override
	protected void crt(Graphics g, int w, int h){ g.fillRect(p.x()*w, p.y()*h, w*2, h); }
}