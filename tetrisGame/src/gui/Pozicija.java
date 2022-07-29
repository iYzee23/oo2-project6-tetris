package gui;

public class Pozicija {
	public enum Smer{ LEVO, DOLE, DESNO };
	private int x, y;
	public Pozicija(int x, int y) {
		this.x = x; this.y = y;
	}
	public int x() { return x; }
	public int y() { return y; }
	public void pomeri(Smer s) {
		x += s.ordinal()-1;
		y += s.ordinal()%2;
	}
	public Pozicija pom(Smer s) { return new Pozicija(x+s.ordinal()-1, y+s.ordinal()%2); }
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Pozicija)) return false;
		Pozicija p = (Pozicija)o;
		return x == p.x && y == p.y;
	}
}
