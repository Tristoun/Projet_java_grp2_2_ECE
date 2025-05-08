package DAO;

//import Models.Specialist;
import java.sql.ResultSet;

public class SpecialistDaoImpl extends GeneralDaoImpl implements SpecialistDao, UserDao {



    public SpecialistDaoImpl() {
        super("specialiste");
    }

    @Override //Jcrois que ca sert a rien de Override il y a rien qui s'appelle comme ca
    public void returnProfilSpecialist(int idSpecialist) {
        getSpecific("idUser", idSpecialist);
    }

    @Override
    public ResultSet returnAllProfiles() {
        ResultSet res = this.getAll();
        return res;
    }

    @Override
    public void editProfileSpecialist(int idSpecialist, String newName) {
        setById("idUser", idSpecialist, "name", newName);





    /*public void ModifierSpecialiste(Specialist personne, String description, String schedule, float tarif) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "UPDATE specialiste SET description = ?, schedule = ?, tarif = ? WHERE idSpecialiste = ?"
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

    @Override
    public void returnProfilPatient(int idPatient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnProfilPatient'");
    }

    @Override
    public void editProfileUser(int idPatient, String newName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editProfileUser'");
    }

    @Override
    public void supprimerUser(int idPatient) {
        //a faire
    }
}
