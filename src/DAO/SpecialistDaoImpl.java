package DAO;

//import Models.Specialist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpecialistDaoImpl extends GeneralDaoImpl implements SpecialistDao, UserDao {

    public SpecialistDaoImpl() {
        super("Specialiste");
    }


    @Override
    public void returnProfilPatient(int id_patient) {
        getSpecific("id_user", id_patient, "name");
    }

    @Override
    public void returnAllProfiles() {
        getAll();
    }

    @Override
    public void editProfileUser(int id_patient, String newName){
        setById("id_user", id_patient, "name", newName);
    }


    /*public void ModifierSpecialiste(Specialist personne, String description, String schedule, float tarif) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "UPDATE specialiste SET description = ?, schedule = ?, tarif = ? WHERE id_specialiste = ?"
            );
            int tempId = personne.getUserId();
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

