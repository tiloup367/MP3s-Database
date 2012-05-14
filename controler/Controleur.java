package controler;

import java.sql.SQLException;

import model.AbstractModel;

public class Controleur extends AbstractControler{
	
	public Controleur(AbstractModel model){
		super(model);
	}

	@Override
	public void control(int executionCode) {
		// TODO Auto-generated method stub
		if(executionCode == 0){
			this.core.connectToDB("anonymus", "WqeDsaZxc12345");
		}else if(executionCode == 1){
			try {
				this.core.actualiser();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(executionCode == 2){
			this.core.ajouter();
		}else{
			this.core.notifyObserver("Invalid Execution Code!");
		}
	}
	public void control(int executionCode, String name){
		if(executionCode == 3){
			this.core.supprimer(name);
		}else if(executionCode == 4){
			this.core.modifier(name);
		}
	}
}
