package DAO;

//import Models.Specialist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

public class SpecialistDaoImpl extends GeneralDaoImpl implements SpecialistDao, UserDAO {



    public SpecialistDaoImpl() {
        super("specialiste");
    }

    @Override
    public void returnProfilPatient(int id_patient) {
        getSpecific("id_user", id_patient);
    }

    @Override
    public ResultSet returnAllProfiles() {
        ResultSet res = this.getAll();
        return res;
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

