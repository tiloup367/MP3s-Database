package coeur;

import controler.AbstractControler;
import controler.Controleur;
import model.AbstractModel;
import model.Model;
import view.GUI;

public class ExecutionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//*
		AbstractModel model = new Model();
				
		AbstractControler controleur = new Controleur(model);
				
		GUI fen = new GUI(controleur);
		model.addObserver(fen);//*/
	}

}
