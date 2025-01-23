package Gestion_Livres;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class MainFrame2 extends JFrame{
    private static final int PREVIEW_SIZE = 150;
    private static final Font FONT = new Font("Segoe UI", Font.BOLD, 14);
    
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTitre, txtAuteur, txtGenre;
    private JTextField txtAnneePublication;
    private JCheckBox checkExiste;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnRechercher, btnImage,btnClear,btnReload;
    private JFileChooser fc;
    private File selectedFile;
    private String imagePath;
    private JLabel previewLabel;
    
    
	public MainFrame2() {
		initialiserFrame();
		initialiserComposants();
        layoutComposants();
        addEventListeners();
        refreshTable();
        this.setLocationRelativeTo(null);


		}
	
    private void initialiserFrame() {
        this.setTitle("Gestion des Livres et Auteurs par HACHEM SQUALLI EL HOUSSAINI");
        this.setSize(1000, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(15, 15));
        this.getContentPane().setBackground(new Color(240, 240, 245));
    }
    
    private void initialiserComposants() {
        // form pour ajouter livre ou modifier
        txtTitre = createStyledTextField();
        txtAuteur = createStyledTextField();
        txtGenre = createStyledTextField();
        txtAnneePublication = createStyledTextField();
        checkExiste = createCheckbox("existe", Color.DARK_GRAY);
        
        // buttons de manupulation
        btnAjouter = createStyledButton("Ajouter", new Color(39, 174, 96));
        btnModifier = createStyledButton("Modifier", new Color(41, 128, 185));
        btnSupprimer = createStyledButton("Supprimer", new Color(192, 57, 43));
        btnRechercher = createStyledButton("Rechercher", new Color(142, 68, 173));
        btnClear = createStyledButton("Clear", new Color(127, 140, 141));
        btnReload = createStyledButton("Reload", new Color(52, 152, 219));

        btnImage = createStyledButton("Choisir une image", new Color(41, 128, 185));        
        
        // file choisir
        fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Images", "jpg", "jpeg", "png", "gif"));
            
        // Initialiser table
        String[] colonnes = {"ID", "Titre", "Auteur", "Date de publication", "Existe", "Genre", "Image"};
        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        table = new JTable(model);
        creationTable();
    }
    
    
    private void layoutComposants() {
        JPanel controlPanel = new JPanel(new BorderLayout(20, 5));
        controlPanel.setBackground(new Color(250, 250, 255));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 0, 10));

        controlPanel.add(createFormPanel(), BorderLayout.CENTER);
        controlPanel.add(createImagePanel(), BorderLayout.EAST);
        controlPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(1, 3, 5, 3));

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    
    //Debut Layout Coposant de ControlPanl---
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
        formPanel.setBackground(new Color(250, 250, 255));

        addLabelAndField(formPanel, "Titre :", txtTitre);
        addLabelAndField(formPanel, "Auteur :", txtAuteur);
        addLabelAndField(formPanel, "Date de publication :", txtAnneePublication);
        addLabelAndField(formPanel, "Est-t-il existe? :", checkExiste);
        addLabelAndField(formPanel, "Genre :", txtGenre);

        return formPanel;
    }
    
    
  
    //pour facilite manupulation sans reecrire chose multiple fois
    private void addLabelAndField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(FONT);
        panel.add(label);
        panel.add(field);
    }
    
    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new BorderLayout(10, 10));
        imagePanel.setBackground(new Color(250, 250, 255));

        previewLabel = new JLabel("No Image", SwingConstants.CENTER);
        previewLabel.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
        previewLabel.setForeground(new Color(120, 120, 120));
        previewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        

        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
        previewPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
        previewPanel.setBackground(Color.WHITE);
        previewPanel.add(previewLabel, BorderLayout.CENTER);
        

        JPanel imageControlPanel = new JPanel(new BorderLayout(10, 10));
        imageControlPanel.setBackground(new Color(250, 250, 255));
        JLabel imageTitle = new JLabel("Image:", SwingConstants.CENTER);
        imageTitle.setFont(FONT);
        
        imageControlPanel.add(imageTitle, BorderLayout.NORTH);
        imageControlPanel.add(previewPanel, BorderLayout.CENTER);
        imageControlPanel.add(btnImage, BorderLayout.SOUTH);
        
        imagePanel.add(imageControlPanel, BorderLayout.NORTH);
        return imagePanel;
    }
    
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(250, 250, 255));
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnRechercher);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnReload);
        return buttonPanel;
    }
    
    //Fin Layout Coposant de ControlPanl*****


    private void creationTable() {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.getTableHeader().setFont(FONT);
        table.getTableHeader().setBackground(new Color(240, 240, 245));
        table.getTableHeader().setForeground(new Color(60, 60, 60));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);

    }
    
    //Debut de style
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return field;
    }


    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }
    
    
    private JCheckBox createCheckbox(String text, Color color) {
        JCheckBox checkbox = new JCheckBox(text);

        checkbox.setForeground(Color.WHITE);
        checkbox.setFont(FONT); 
        checkbox.setBackground(color); 
        checkbox.setFocusPainted(false); 
        checkbox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 

        return checkbox;
    }

    
    //Fin de style
    
    
    
    
    //Debut de image previsualiser 
    private void updateImagePreview(File file) {
        if (file != null && isValidImageFile(file)) {
            try {
                BufferedImage originalImage = ImageIO.read(file);
                if (originalImage != null) {
                    Image scaledImage = originalImage.getScaledInstance(
                        PREVIEW_SIZE, 
                        PREVIEW_SIZE, 
                        Image.SCALE_SMOOTH
                    );
                    previewLabel.setIcon(new ImageIcon(scaledImage));
                    previewLabel.setText("");
                }
            } catch (IOException ex) {
            	photoProblem("Error loading image");
            }
        } else {
        	photoProblem("Invalid image file");
        }
    }
    
    
    //fonction de button image
    private void ImageSelectionerLivre() {
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            imagePath = selectedFile.getAbsolutePath();
            updateImagePreview(selectedFile);
        }
    }
    
    //retour image au default
    private void photoProblem(String message) {
        previewLabel.setIcon(null);
        previewLabel.setText(message);
    }
    
    private boolean isValidImageFile(File file) {
        try {
            return ImageIO.read(file) != null;
        } catch (IOException e) {
            return false;
        }
    }
    
    //fin de image previsualiser
    
    
    private void refreshTable() {
        try {
            ManupileLivreBD dao = new ManupileLivreBD();
            List<Livre> livres = dao.getTous();
            
            model.setRowCount(0);
            
            for (Livre livre : livres) {
                model.addRow(new Object[]{
                    livre.getId(),
                    livre.getTitre(),
                    livre.getAuteur(),
                    String.valueOf(livre.getAnneePublication()),
                    livre.isExiste(),
                    livre.getGenre(),
                    livre.getImage() 
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors du rafraîchissement des données: " + e.getMessage(), 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        txtTitre.setText(""); 
        txtAuteur.setText(""); 
        txtAnneePublication.setText("");
        checkExiste.setSelected(false); 
        txtGenre.setText(""); 

        updateImagePreview(null);
        table.clearSelection();
        refreshTable();
    }
    
    private void handleTableSelection() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtTitre.setText((String) table.getValueAt(selectedRow, 1));
            txtAuteur.setText((String) table.getValueAt(selectedRow, 2));
            txtAnneePublication.setText((String) table.getValueAt(selectedRow, 3));
            checkExiste.setSelected((Boolean) table.getValueAt(selectedRow, 4));
            txtGenre.setText((String) table.getValueAt(selectedRow, 5));
            
            
            Object fileValue = table.getValueAt(selectedRow, 6);
            if (fileValue instanceof File) {
                updateImagePreview((File) fileValue);
            } else if (fileValue instanceof String) {
                File file = new File((String) fileValue);
                updateImagePreview(file);
            } else {
                photoProblem("Invalid file reference in table");
            }            
            
        }
    }
    
    
    private void addEventListeners() {
        btnImage.addActionListener(e -> ImageSelectionerLivre());
        btnAjouter.addActionListener(e -> AjouterLivre());
        btnModifier.addActionListener(e -> ModifierLivre());
        btnSupprimer.addActionListener(e -> SupprimerLivre());
        btnRechercher.addActionListener(e -> RechercherLivre());
        btnClear.addActionListener(e -> clearFields());
        btnReload.addActionListener(e -> refreshTable());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTableSelection();
            }
        });
    }
    
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame2 frame = new MainFrame2();
            frame.setVisible(true);
        });
    }
    
    

    
    private void AjouterLivre() {
        try {
            // Validation des champs
            if (txtTitre.getText().trim().isEmpty() ||
                txtAuteur.getText().trim().isEmpty() ||
                txtAnneePublication.getText().trim().isEmpty() ||
                txtGenre.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                    "Tous les champs sont obligatoires",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validation de l'image
            if (selectedFile == null || imagePath == null) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une image",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Récupération des valeurs
            String titre = txtTitre.getText().trim();
            String auteur = txtAuteur.getText().trim();
            
            // Validation et conversion de l'année de publication
            int anneePublication;
            try {
                anneePublication = Integer.parseInt(txtAnneePublication.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "L'année de publication doit être un nombre valide",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            boolean existe = checkExiste.isSelected();
            String genre = txtGenre.getText().trim();
            String image = imagePath;
            
            Livre livre = new Livre(titre, auteur, anneePublication, existe, genre, image);
            
            ManupileLivreBD dao = new ManupileLivreBD();
            dao.ajouter(livre);
            
            // Message de confirmation
            JOptionPane.showMessageDialog(this,
                "Livre modification avec succès!",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Rafraîchir la table et vider les champs
            refreshTable();
            clearFields();
        } catch (SQLException e) {
        	
            // gestion spécifique pour l'email en doublon
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("titre")) {
                JOptionPane.showMessageDialog(this,
                    "Ce titre existe déjà dans la base de données",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'ajout: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

	
    
   


	private void SupprimerLivre() {
		int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre à supprimer", 
                "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer cet livre?", 
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                int id = (int) table.getValueAt(selectedRow, 0);
                ManupileLivreBD dao = new ManupileLivreBD();
                dao.supprimer(id);

                JOptionPane.showMessageDialog(this, "livre supprimée avec succès !");
                refreshTable();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppressio: " + e.getMessage(), 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



	private void ModifierLivre() {
	    // vérifier si une ligne est sélectionnée
	    int selectedRow = table.getSelectedRow();
	    if (selectedRow == -1) {
	        JOptionPane.showMessageDialog(this, 
	            "Veuillez sélectionner un livre à modifier",
	            "Erreur", 
	            JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    
	    System.out.println(selectedRow);
	    // Validation 
	    if (txtTitre.getText().trim().isEmpty() || 
	        txtAuteur.getText().trim().isEmpty() || 
	        txtAnneePublication.getText().trim().isEmpty() || 
	        txtGenre.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this,
	            "Tous les champs sont obligatoires",
	            "Erreur",
	            JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    int confirmation = JOptionPane.showConfirmDialog(this,
	        "Êtes-vous sûr de vouloir modifier ce livre ?",
	        "Confirmation de Modification",
	        JOptionPane.YES_NO_OPTION);

	    if (confirmation == JOptionPane.YES_OPTION) {
	        try {
	            // Récupération de l'ID du livre sélectionné
	            int id = (int) table.getValueAt(selectedRow, 0);

	            // Récupération des valeurs des champs
	            String titre = txtTitre.getText().trim();
	            String auteur = txtAuteur.getText().trim();

	            // Validation et conversion de l'année de publication
	            int anneePublication;
	            try {
	                anneePublication = Integer.parseInt(txtAnneePublication.getText().trim());
	                // Validation supplémentaire de l'année si nécessaire Important pour pas avoir problem 
	                if (anneePublication < 0 || anneePublication > Calendar.getInstance().get(Calendar.YEAR)) {
	                    JOptionPane.showMessageDialog(this,
	                        "Veuillez entrer une année valide",
	                        "Erreur",
	                        JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	            } catch (NumberFormatException e) {
	                JOptionPane.showMessageDialog(this,
	                    "L'année de publication doit être un nombre valide",
	                    "Erreur",
	                    JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            boolean existe = checkExiste.isSelected();
	            String genre = txtGenre.getText().trim();
	            String image = imagePath; 

	            // création de l'objet Livre avec l'ID
	            Livre livre = new Livre(titre, auteur, anneePublication, existe, genre, image);
	            livre.setId(id); 
	            
	            // modification dans la base de données
	            ManupileLivreBD dao = new ManupileLivreBD();
	            dao.modifier(livre);

	            // message de succès et rafraîchissement
	            JOptionPane.showMessageDialog(this, "Livre modifié avec succès !");
	            refreshTable();
	            clearFields();
	            
	        } catch (SQLException e) {
	            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("titre")) {
	                JOptionPane.showMessageDialog(this,
	                    "Ce titre existe déjà dans la base de données",
	                    "Erreur",
	                    JOptionPane.ERROR_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(this,
	                    "Erreur lors de la modification : " + e.getMessage(),
	                    "Erreur",
	                    JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }
	}

	

    private void RechercherLivre() {
        //panneau personnalisé pour la recherche
        JPanel searchPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        
        // Champ de recherche
        JTextField searchField = new JTextField(20);
        searchPanel.add(new JLabel("Critère de recherche:"));
        searchPanel.add(searchField);
        
        // Checkboxes pour les options de recherche
        JCheckBox titreBox = new JCheckBox("Rechercher dans le titre ", true);
        JCheckBox auteurBox = new JCheckBox("Rechercher dans l'auteur ");
        JCheckBox genreBox = new JCheckBox("Rechercher dans le genre ");
        JCheckBox anneeBox = new JCheckBox("Rechercher dans l'année ");
        
        // ajouter les checkboxes au panneau
        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.add(titreBox);
        checkBoxPanel.add(auteurBox);
        checkBoxPanel.add(genreBox);
        checkBoxPanel.add(anneeBox);
        searchPanel.add(checkBoxPanel);
        
        
        // groupe un tableau de checkboxes pour faciliter le code travail
        JCheckBox[] checkBoxes = {titreBox, auteurBox, genreBox, anneeBox};

        for (JCheckBox currentBox : checkBoxes) {
            currentBox.addActionListener(e -> {
                // déselectioner toutes les checkboxes dans groupee
                for (JCheckBox box : checkBoxes) {
                    if (box != currentBox) {
                        box.setSelected(false);
                    }
                }
            });
        }
        
        int result = JOptionPane.showConfirmDialog(this, searchPanel, 
            "Rechercher un livre", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        if (result == JOptionPane.OK_OPTION) {
            String critere = searchField.getText().trim();
            
            if (!critere.isEmpty()) {
                try {
                    ManupileLivreBD dao = new ManupileLivreBD();
                    List<Livre> resultats = new ArrayList<>();
                    
                    if (!titreBox.isSelected() && !auteurBox.isSelected() && !genreBox.isSelected() && !anneeBox.isSelected()) {
                        resultats = dao.rechercherGeneral(critere);
                    } else {
                        if (titreBox.isSelected()) {
                            resultats.addAll(dao.rechercherParTitre(critere));
                        }
                        if (auteurBox.isSelected()) {
                            resultats.addAll(dao.rechercherParAuteur(critere));
                        }
                        if (genreBox.isSelected()) {
                            resultats.addAll(dao.rechercherParGenre(critere));
                        }
                        if (anneeBox.isSelected()) {
                            try {
                                int annee = Integer.parseInt(critere);
                                resultats.addAll(dao.rechercherParAnnee(annee));
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Veuillez entrer une année valide.", 
                                    "Erreur", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                        }
                    }
                    
                    // Vider la tablau
                    model.setRowCount(0);
                    
                    for (Livre livre : resultats) {
                        model.addRow(new Object[]{
                            livre.getId(),
                            livre.getTitre(),
                            livre.getAuteur(),
                            String.valueOf(livre.getAnneePublication()),
                            livre.isExiste(),
                            livre.getGenre(),
                            livre.getImage() 
                        });
                    }
                    
                    if (resultats.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Aucun livre trouvé.");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la recherche: " + e.getMessage(), 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un critère de recherche.", 
                    "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        }        
    }




}
