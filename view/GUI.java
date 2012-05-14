package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controler.AbstractControler;
import observer.Observer;

public class GUI extends JFrame implements Observer, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8045121660678247656L;
	//Declaration de variable avec valeur null afin d'eviter
	//d'avoir une valeur != null a leur adresse memoire reserver
	private AbstractControler controleur = null;
	private JTextArea cadreVue = null;
	private JScrollPane scrollPane = null;
	private JButton actualiser = null;
	private JButton ajouter = null;
	private JButton modifier = null;
	private JButton supprimer = null;
	private JPanel leftPanButton = null;
	private JPanel rightPanButton = null;
	private JPanel bottomPan = null;
	private JTextField input = null;
	private JLabel nom = null;
	
	public GUI(AbstractControler controleur){
		//Initialisation des parametres de l'objet GUI
		this.controleur = controleur;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("MPEG-2 Layer 3 Manager");
		this.setSize(1024, 768);
		this.cadreVue = new JTextArea("MP3s : \n");
		this.cadreVue.setColumns(32);
		this.cadreVue.setEnabled(true);
		this.cadreVue.setEditable(false);
		this.scrollPane = new JScrollPane(this.cadreVue);
		this.add(scrollPane, BorderLayout.CENTER);
		this.actualiser = new JButton("Actualiser");
		this.actualiser.addActionListener(this);
		this.ajouter = new JButton("Ajouter");
		this.ajouter.addActionListener(this);
		this.modifier = new JButton("Modifier");
		this.modifier.addActionListener(this);
		this.supprimer = new JButton("Supprimer");
		this.supprimer.addActionListener(this);
		this.leftPanButton = new JPanel();
		this.rightPanButton = new JPanel();
		this.leftPanButton.add(this.actualiser);
		this.leftPanButton.add(this.modifier);
		this.rightPanButton.add(this.ajouter);
		this.rightPanButton.add(this.supprimer);
		this.input = new JTextField(32);
		this.input.setText(null);
		this.nom = new JLabel("Nom du fichier : ");
		this.bottomPan = new JPanel();
		this.bottomPan.add(this.nom);
		this.bottomPan.add(this.input);
		this.add(this.leftPanButton, BorderLayout.WEST);
		this.add(this.rightPanButton, BorderLayout.EAST);
		this.add(this.bottomPan, BorderLayout.NORTH);
		this.setVisible(true);
		this.controleur.control(0);
		
	}
	
	//Methode de mise a jour du champ de texte
	@Override
	public void update(String str) {
		// TODO Auto-generated method stub
		this.cadreVue.setText("MP3s : \n" + str);
	}
	
	//Gestionnaire d'evenement sur les JButtons
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton source = (JButton)e.getSource();
		if(source == actualiser)this.controleur.control(1);
		if(source == ajouter)this.controleur.control(2);
		if(source == supprimer)this.controleur.control(3, getNomFichier());
		if(source == modifier)this.controleur.control(4, getNomFichier());
	}
	
	private String getNomFichier(){
		return this.input.getText().toString();
	}

}
