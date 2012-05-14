package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JOptionPane;

import com.sun.rowset.*;

import de.vdheide.mp3.FrameDamagedException;
import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.MP3File;
import de.vdheide.mp3.NoMP3FrameException;
import de.vdheide.mp3.TagContent;

public class Model extends AbstractModel{
	
	private CachedRowSet database = null;
	private String commande = "";
	
	@SuppressWarnings("restriction")
	public Model(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.database = new CachedRowSetImpl();
			this.database.setUrl("jdbc:mysql://localhost/mp3db");
			this.database.setType(ResultSet.TYPE_SCROLL_SENSITIVE);
			this.database.setConcurrency(ResultSet.CONCUR_UPDATABLE);
		}catch(SQLException e){
			notifyObserver("Error during initialization : " + e.getMessage().toString());
			disconnectFromDB();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			notifyObserver("Error during initialization : " + e.getMessage().toString());
			disconnectFromDB();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void actualiser() throws SQLException{
		File repertoire = new File("/home/anonymus/Musique");
		File[] liste = repertoire.listFiles();
		String show = "";
		String search ="";
		String add = "(";
		MP3File mp3 = null;
		TagContent info = new TagContent();
		for(int i=0;i<liste.length;i++){
			try {
				mp3 = new MP3File("/home/anonymus/Musique/"+liste[i].getName());
				info = mp3.getTitle();
			} catch (ID3v2WrongCRCException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ID3v2DecompressionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ID3v2IllegalVersionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoMP3FrameException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FrameDamagedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			search = ("title=\""+info.getTextContent().toString() + "\" AND home=\"/home/anonymus/Musique/\"");
			this.commande = ("SELECT * FROM musics WHERE "+search);
			this.database.setCommand(this.commande);
			this.database.execute();
			if(this.database.first() == true){
				show += liste[i].getName() + "\n";
			}else if(this.database.first() == false){
				String keep = "";
				for(int j =0; j <6;j++){
					try{
					if(j == 0){
						add = "('/home/anonymus/Musique/',";
						info = mp3.getComments();
						keep = info.getTextContent();
						if(keep.length() <= 32 && keep.length() != 0){
							keep = keep.substring(0, keep.length()-1);
						}else if(keep.length() > 32){
							keep = keep.substring(0, 31);
						}else if(keep.length() == 0){
							keep = "unknown";
						}
						info.setContent(keep);
						add += "'"+info.getTextContent()+"',";
					}else if(j == 1){
						info = mp3.getArtist();
						keep = info.getTextContent();
						if(keep.length() <= 32 && keep.length() != 0){
							keep = keep.substring(0, keep.length()-1);
						}else if(keep.length() > 32){
							keep = keep.substring(0, 31);
						}else if(keep.length() == 0){
							keep = "unknown";
						}
						info.setContent(keep);
						add += "'"+info.getTextContent()+"',";
					}else if(j == 2){
						info = mp3.getGenre();
						keep = info.getTextContent();
						if(keep.length() <= 32 && keep.length() != 0){
							keep = keep.substring(0, keep.length()-1);
						}else if(keep.length() > 32){
							keep = keep.substring(0, 31);
						}else if(keep.length() == 0){
							keep = "unknown";
						}
						info.setContent(keep);
						add += "'"+info.getTextContent()+"',";
					}else if(j == 3){
						info = mp3.getTitle();
						keep = info.getTextContent();
						if(keep.length() <= 32 && keep.length() != 0){
							keep = keep.substring(0, keep.length()-1);
						}else if(keep.length() > 32){
							keep = keep.substring(0, 31);
						}else if(keep.length() == 0){
							keep = "unknown";
						}
						info.setContent(keep);
						add += "'"+info.getTextContent()+"',";
					}else if(j == 4){
						info = mp3.getAlbum();
						keep = info.getTextContent();
						if(keep.length() <= 32 && keep.length() != 0){
							keep = keep.substring(0, keep.length()-1);
						}else if(keep.length() > 32){
							keep = keep.substring(0, 31);
						}else if(keep.length() == 0){
							keep = "unknown";
						}
						info.setContent(keep);
						add += "'"+info.getTextContent()+"',";
					}else if(j == 5){
						info = mp3.getYear();
						keep = info.getTextContent();
						keep = keep.substring(0, 3);
						info.setContent(keep);
						add += +Integer.parseInt(info.getTextContent())+")";
					}
					}catch(FrameDamagedException e){
						e.getMessage();
					}
					
				}
				this.commande = ("INSERT INTO musics VALUES "+ add);
				this.database.setCommand(this.commande);
				this.database.execute();
				this.database.updateRow();
				this.database.acceptChanges();
			}
			
		}
		disconnectFromDB();
		this.commande = "";
		notifyObserver(show);
		
	}
	
	public void ajouter(){
		String path = "";
		String add = "";
		path = JOptionPane.showInputDialog("Chemin absolu vers le fichier MP3 : ");
		if(path.equals("") || path.equals(null)){
			JOptionPane.showMessageDialog(null, "No path was entered!", "File not found", JOptionPane.WARNING_MESSAGE);
		}else{
			try{
				MP3File mp3 = new MP3File(path);
				TagContent info = new TagContent();
				info = mp3.getTitle();
				this.commande = "SELECT * FROM musics WHERE title=\""+info.getTextContent().toString()+"\" AND home=\"/home/anonymus/Musique/\"";
				this.database.setCommand(this.commande);
				this.database.execute();
				//ajouter methodes de controle
				if(this.database.first() == false){
					File dest = new File("/home/anonymus/Musique/"+mp3.getName());
					mp3.renameTo(dest);
					String keep = "";
					for(int j =0; j <6;j++){
						try{
						if(j == 0){
							add = "('/home/anonymus/Musique/',";
							info = mp3.getComments();
							keep = info.getTextContent();
							keep = keep.substring(0, 31);
							info.setContent(keep);
							add += "'"+info.getTextContent()+"',";
						}else if(j == 1){
							info = mp3.getArtist();
							keep = info.getTextContent();
							keep = keep.substring(0, 31);
							info.setContent(keep);
							add += "'"+info.getTextContent()+"',";
						}else if(j == 2){
							info = mp3.getGenre();
							keep = info.getTextContent();
							keep = keep.substring(0, 31);
							info.setContent(keep);
							add += "'"+info.getTextContent()+"',";
						}else if(j == 3){
							info = mp3.getTitle();
							keep = info.getTextContent();
							keep = keep.substring(0, 31);
							info.setContent(keep);
							add += "'"+info.getTextContent()+"',";
						}else if(j == 4){
							info = mp3.getAlbum();
							keep = info.getTextContent();
							keep = keep.substring(0, 31);
							info.setContent(keep);
							add += "'"+info.getTextContent()+"',";
						}else if(j == 5){
							info = mp3.getYear();
							keep = info.getTextContent();
							keep = keep.substring(0, 3);
							info.setContent(keep);
							add += +Integer.parseInt(info.getTextContent())+")";
						}
						}catch(FrameDamagedException e){
							e.getMessage();
						}
						
					}
					this.commande = ("INSERT INTO musics VALUES "+add);
					this.database.setCommand(this.commande);
					this.database.execute();
					this.database.updateRow();
					this.database.acceptChanges();
				}else{
					JOptionPane.showMessageDialog(null, "File already Present!", "File exist!", JOptionPane.INFORMATION_MESSAGE);
				}
				disconnectFromDB();//*/
			}catch(SQLException e){
				notifyObserver(e.getMessage().toString());
				disconnectFromDB();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ID3v2WrongCRCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ID3v2DecompressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ID3v2IllegalVersionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoMP3FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FrameDamagedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.commande = "";
	}
	
	public void supprimer(String nom){
		
		if(nom.equals("") || nom.equals(null)){
			JOptionPane.showMessageDialog(null, "No file was entered!", "File not found", JOptionPane.WARNING_MESSAGE);
		}else{
			try{
				MP3File mp3 = new MP3File("/home/anonymus/Musique/"+nom);
				TagContent info = new TagContent();
				info = mp3.getTitle();
				this.commande = "SELECT * FROM musics WHERE title=\""+info.getTextContent()+"\" AND home=\"/home/anonymus/Musique/\"";
				this.database.setCommand(this.commande);
				this.database.execute();
				//ajouter methodes de controle
				if(this.database.first() == false){
					JOptionPane.showMessageDialog(null, "File not found!", "File not found!", JOptionPane.INFORMATION_MESSAGE);
					this.database.updateRow();
					this.database.acceptChanges();
				}else{
					this.commande = "DELETE FROM musics WHERE title=\""+info.getTextContent()+"\" AND home=\"/home/anonymus/Musique/\"";
					this.database.setCommand(this.commande);
					this.database.execute();
					mp3.delete();
					this.database.updateRow();
					this.database.acceptChanges();
				}
				disconnectFromDB();//*/
			}catch(SQLException e){
				notifyObserver(e.getMessage().toString());
				disconnectFromDB();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ID3v2WrongCRCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ID3v2DecompressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ID3v2IllegalVersionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoMP3FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FrameDamagedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.commande="";
	}
	
	public void modifier(String nom){
		if(nom.equals("") || nom.equals(null)){
			JOptionPane.showMessageDialog(null, "No file was entered!", "File not found", JOptionPane.WARNING_MESSAGE);
		}else{
			try{
			MP3File mp3 = new MP3File("/home/anonymus/Musique/"+nom);
			TagContent info = new TagContent();
			info = mp3.getTitle();
			this.commande = "SELECT * FROM musics WHERE title=\""+info.getTextContent()+"\" AND home=\"/home/anonymus/Musique/\"";
			this.database.setCommand(this.commande);
			this.database.execute();
			//ajouter methodes de controle
			if(this.database.first() == false){
				JOptionPane.showMessageDialog(null, "File not found!", "File Not Found", JOptionPane.INFORMATION_MESSAGE);
			}else{
				Modifier modifieur = new Modifier(nom);
			}
			//mise a jour des donnees
			
			//Definir la commande sql pour modifier un enregistrement
			this.database.updateRow();
			this.database.acceptChanges();
			//affichage des donnees
			while(this.database.next()){
				
			}
			disconnectFromDB();
		}catch(SQLException e){
			notifyObserver(e.getMessage().toString());
			disconnectFromDB();
		} catch (FrameDamagedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		}
		}
	}
	
	public void connectToDB(String username, String password){
		try{
			this.database.setUsername(username);
			this.database.setPassword(password);
		}catch(SQLException e){
			notifyObserver(e.getMessage().toString());
			disconnectFromDB();
		}
		
	}
	
	protected void disconnectFromDB(){
		try {
			this.database.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			notifyObserver(e.getMessage().toString());
		}
	}
	
}
