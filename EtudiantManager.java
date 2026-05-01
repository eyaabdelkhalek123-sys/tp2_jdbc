package ma.projet.manager;

import ma.projet.connexion.Connexion;
import ma.projet.model.Etudiant;

import java.sql.Connection;       
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantManager {
	private Connection cn=Connexion.getCn();
	
	public boolean create(Etudiant o) {
		String sql="insert into etudiant (nom , prenom , sexe ,  filiere) values (? , ? , ? , ?)";
		
		try {
			PreparedStatement ps=cn.prepareStatement(sql);
			
			ps.setString(1 , o.getNom());
			ps.setString(2 , o.getPrenom());
			ps.setString(3 , String.valueOf(o.getSexe()));
			ps.setString(4 , o.getFiliere());
			
			return ps.executeUpdate() > 0;
		}
		catch (SQLException e) {
			System.out.println("Erreur de create: " + e.getMessage());
			return false;
		}
		
	}
	
	public boolean delete(Etudiant o) {
        String sql = "delete from etudiant where id=?";

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, o.getId());

            return ps.executeUpdate() > 0;

        } 
        catch (SQLException ex) {
            System.out.println("Erreur de delete: " + ex.getMessage());
            return false;
        }
    }
	
	public boolean update(Etudiant o) {
        String sql = "update etudiant set nom=?, prenom=?, sexe=?, filiere=? WHERE id=?";

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, String.valueOf(o.getSexe()));
            ps.setString(4, o.getFiliere());
            ps.setInt(5, o.getId()); 

            return ps.executeUpdate() > 0;

        } 
        catch (SQLException e) {
            System.out.println("Error de update: " + e.getMessage());
            return false;
        }
    }
	
	public Etudiant findById(int id) {
        String sql = "select * from etudiant where id=?";

        try {
            PreparedStatement ps =cn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) { 
                return new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("sexe").charAt(0),
                    rs.getString("filiere")
                );
            }
        } 
        catch (SQLException e) {
            System.out.println("Erreur de findById : " + e.getMessage());
        }
        return null; 
    }
	
	public List<Etudiant> findAll() {
        List<Etudiant> classe=new ArrayList<>();
        String sql = "select * from etudiant";

        try {
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);

            while (rs.next()) {
                Etudiant e = new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("sexe").charAt(0),
                    rs.getString("filiere")
                );
                classe.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur de findAll : " + ex.getMessage());
        }
        return classe;
    }
	
}
