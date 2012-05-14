package controler;

import model.AbstractModel;

public abstract class AbstractControler {
	protected AbstractModel core;
	
	public AbstractControler(AbstractModel core){
		this.core = core;
	}
	
	public abstract void control(int executionCode);
	public abstract void control(int executionCode, String name);
	
}
