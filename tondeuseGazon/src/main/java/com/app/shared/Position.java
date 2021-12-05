package com.app.shared;

public class Position {

	private int x;

	private int y;

	public Position() {
		this(0, 0);
	}

	public Position(Position p) {
		this(p.x, p.y);
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Position getLocation() {
		return new Position(x, y);
	}

	public void setLocation(Position p) {
		setLocation(p.x, p.y);
	}

	public void setLocation(int x, int y) {
		move(x, y);
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void translate(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			Position pt = (Position) obj;
			return (x == pt.x) && (y == pt.y);
		}
		return super.equals(obj);
	}

	public String toString() {
		return x + " " + y;
	}

}
