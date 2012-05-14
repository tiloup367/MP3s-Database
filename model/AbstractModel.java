package model;

import java.sql.SQLException;
import java.util.ArrayList;

import observer.Observable;
import observer.Observer;

public abstract class AbstractModel implements Observable{
	
	private ArrayList<Observer> listeObserver = new ArrayList<Observer>();
	
	public void addObserver(Observer obs){
		this.listeObserver.add(obs);
	}
	
	public void removeObserver(){
		this.listeObserver = new ArrayList<Observer>();
	}
	
	public void notifyObserver(String str){
		//if(str.matches("^A | 0[(A-Z) | (0-9)]+$")){
			str=str.substring(0,str.length());
			
			for(Observer obs : this.listeObserver){
				obs.update(str);
			}
		//}
	}
	
	public abstract void actualiser() throws SQLException;
	public abstract void ajouter();
	public abstract void supprimer(String nom);
	public abstract void modifier(String nom);
	public abstract void connectToDB(String username, String password);
	protected abstract void disconnectFromDB();
}
