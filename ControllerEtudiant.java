package ma.projet.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.projet.manager.EtudiantManager;
import ma.projet.model.Etudiant;


public class ControllerEtudiant {
	@FXML 
	private TextField tfNom , tfPrenom , tfFiliere;
	
	@FXML 
	private ComboBox<String> cbSexe;
	
	@FXML 
	private TableView<Etudiant> tableEtudiants;
	
	@FXML 
	private TableColumn<Etudiant , Integer> colId;
	
	@FXML 
	private TableColumn<Etudiant , String> colNom , colPrenom , colSexe , colFiliere;
	
	@FXML 
	private Label lblMessage;
	
	private EtudiantManager manager=new EtudiantManager();
	
	@FXML
	public void initialize() {
		cbSexe.setItems(FXCollections.observableArrayList("M", "F"));
		
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colFiliere.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        
        tableEtudiants.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        tfNom.setText(newVal.getNom());
                        tfPrenom.setText(newVal.getPrenom());
                        cbSexe.setValue(String.valueOf(newVal.getSexe()));
                        tfFiliere.setText(newVal.getFiliere());
                    }
                }
            );
        
        chargerTous();
        
	}
	
	@FXML
    public void chargerTous() {
        ObservableList<Etudiant> classe =
            FXCollections.observableArrayList(manager.findAll());
        tableEtudiants.setItems(classe);
    }
	
	@FXML
    public void ajouterEtudiant() {
        if (!validerFormulaire()) return;

        Etudiant e = new Etudiant(
            tfNom.getText(),
            tfPrenom.getText(),
            cbSexe.getValue().charAt(0),
            tfFiliere.getText()
        );

        if (manager.create(e)) {
            lblMessage.setText("etudiant ajouter successfully");
            chargerTous();
            emptyForm();
        } 
        else 
            lblMessage.setText("erreur d'ajout");
        
    }
	
	@FXML
    public void supprimerEtudiant() {
        Etudiant selectionne = tableEtudiants.getSelectionModel().getSelectedItem();

        if (selectionne == null) {
            lblMessage.setText("veuillez selectionner un etudiant");
            return;
        }

        if (manager.delete(selectionne)) {
        	lblMessage.setText("etudiant supprimer successfully");
            chargerTous();
        } else 
        	lblMessage.setText("erreur de suppressio,");
        
    }
	
	@FXML
    public void modifierEtudiant() {
        Etudiant e = tableEtudiants.getSelectionModel().getSelectedItem();

        if (e == null || !validerFormulaire()) return;

        e.setNom(tfNom.getText());
        e.setPrenom(tfPrenom.getText());
        e.setSexe(cbSexe.getValue().charAt(0));
        e.setFiliere(tfFiliere.getText());

        if (manager.update(e)) {
        	lblMessage.setText("etudiant modifier successfully");
            chargerTous();
            emptyForm();
        } else 
        	lblMessage.setText("erreur de modification");
        
    }
	
	private boolean validerFormulaire() {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty()
                || cbSexe.getValue() == null || tfFiliere.getText().isEmpty()) {
            lblMessage.setText("tous les champs sont obligatoires");
            return false;
        }
        return true;
    }
	 
	private void emptyForm() {
        tfNom.clear();
        tfPrenom.clear();
        tfFiliere.clear();
        cbSexe.setValue(null);
    }
	 
}
