package com.mgroschupf.daily;

import java.util.ArrayList;
import java.util.List;

public class Position {
	String name = null;
	int number = 0;
	List<Position> positions = new ArrayList<Position>();

	public Position() {
	}
	public Position(String name, int number) {
		this.name = name;
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<Position> getPositions() {
		return positions;
	}
	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
}