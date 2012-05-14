package model;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.vdheide.mp3.FrameDamagedException;
import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.MP3File;
import de.vdheide.mp3.NoMP3FrameException;
import de.vdheide.mp3.TagContent;
import de.vdheide.mp3.TagFormatException;

public class Modifier extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2272592820281183607L;
	private JPanel panLabel = null;
	private JPanel panInput = null;
	private JLabel[] label = new JLabel[6];
	private JTextField[] champ = new JTextField[6];
	private MP3File mp3 = null;
	private JButton modifier = null;
	private TagContent info = null;

	public Modifier(String nom){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Mutateur d'info");
		this.setSize(512,384);
		this.panLabel = new JPanel();
		this.panInput = new JPanel();
		this.modifier = new JButton("Appliquer");
		this.modifier.addActionListener(this);
		this.info = new TagContent();
		
		try {
			this.mp3 = new MP3File("/home/anonymus/Musique/"+nom);
			for(int i =0;i<6;i++){
				if(i == 0){
					this.label[i] = new JLabel("Tag : ");
					this.panLabel.add(this.label[i]);
					this.champ[i] = new JTextField(32);
					this.info = mp3.getComments();
					this.champ[i].setText(info.getTextContent());
					this.panInput.add(this.champ[i]);
				}else if(i == 1){
					this.label[i] = new JLabel("Artiste : ");
					this.panLabel.add(this.label[i]);
					this.champ[i] = new JTextField(32);
					this.info = mp3.getArtist();
					this.champ[i].setText(info.getTextContent());
					this.panInput.add(this.champ[i]);
				}else if(i == 2){
					this.label[i] = new JLabel("Genre : ");
					this.panLabel.add(this.label[i]);
					this.champ[i] = new JTextField(32);
					this.info = mp3.getGenre();
					this.champ[i].setText(info.getTextContent());
					this.panInput.add(this.champ[i]);
				}else if(i == 3){
					this.label[i] = new JLabel("Titre : ");
					this.panLabel.add(this.label[i]);
					this.champ[i] = new JTextField(32);
					this.info = mp3.getTitle();
					this.champ[i].setText(info.getTextContent());
					this.panInput.add(this.champ[i]);
				}else if(i == 4){
					this.label[i] = new JLabel("Album : ");
					this.panLabel.add(this.label[i]);
					this.champ[i] = new JTextField(32);
					this.info = mp3.getAlbum();
					this.champ[i].setText(info.getTextContent());
					this.panInput.add(this.champ[i]);
				}else if(i == 5){
					this.label[i] = new JLabel("Annee : ");
					this.panLabel.add(this.label[i]);
					this.champ[i] = new JTextField(32);
					this.info = mp3.getYear();
					this.champ[i].setText(info.getTextContent());
					this.panInput.add(this.champ[i]);
				}
			}
		} catch (ID3v2WrongCRCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ID3v2DecompressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ID3v2IllegalVersionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoMP3FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FrameDamagedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.add(this.panLabel, BorderLayout.WEST);
		this.add(this.panInput, BorderLayout.EAST);
		this.add(this.modifier, BorderLayout.NORTH);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton source = (JButton)e.getSource();
		if(source == modifier)appliquer();
	}
	
	private void appliquer(){
try{
		if(!this.champ[1].getText().isEmpty()){
			this.info.setContent(this.champ[1].getText());
			this.mp3.setComments(info);
		}
		//Ajouter des conditions pour gerer les champs de donnees
}catch(TagFormatException e){
	
}
	}
}
