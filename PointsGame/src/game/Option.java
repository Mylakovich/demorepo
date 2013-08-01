package game;

import java.util.Random;

public class Option {

	String name;
	double chance;
	int value;
	int bonus;
	
	Random roller = new Random();
	
	public Option(String name, double chance, int value, int bonus){
		this.name = name;
		this.chance = chance;
		this.value = value;
		this.bonus = bonus;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("Name: " + name);
		s.append(" Chance ");
		s.append((chance * 100) + "% ");
		s.append("Value " + value + "(+" + bonus + ")");
		return s.toString();
	}
	
	public void updateValue(){
		value += bonus;
	}
	
	public int attempt(){
		int output = 0;
		
		if(roller.nextDouble() < chance){
				output = value;
				System.out.println("Success!");
				updateValue();
		} else {
			System.out.println("Failed.");
		}
		return output;
	}
	
}
