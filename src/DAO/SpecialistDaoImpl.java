package DAO;

//import Models.Specialist;

public class SpecialistDaoImpl extends GeneralDaoImpl implements SpecialistDao {



    public SpecialistDaoImpl() {
        super("specialiste");
    }

    @Override //Jcrois que ca sert a rien de Override il y a rien qui s'appelle comme ca
    public void returnProfilSpecialist(int id_specialist) {
        getSpecific("id_user", id_specialist);
    } //ca renvoie un resultset mais la c void donc sert a rien

    @Override
    public void returnAllProfiles() {
        getAll();
    } //est ce qu'il y a besoin de cette fonction ou est ce qu on peut faire ca direct avec general ?
    //la aussi c void enft

    @Override
    public void editProfileSpecialist(int id_specialist, String newName) {
        setById("id_user", id_specialist, "name", newName);
    }




    /*public void ModifierSpecialiste(Specialist personne, String description, String schedule, float tarif) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "UPDATE specialiste SET description = ?, schedule = ?, tarif = ? WHERE id_specialiste = ?"
            );
            int tempId = personne.getIdUser();
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, schedule);
            preparedStatement.setFloat(3, tarif);
            preparedStatement.setInt(4, tempId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Spécialiste mis à jour !");
            } else {
                System.out.println("ID non trouvé");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur - durant la MAJ d'un spécialiste");
        }
    }
    */
}

