package DAO;

//import Models.Specialist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.Specialist;




public class SpecialistDaoImpl extends GeneralDaoImpl implements SpecialistDao {





    public SpecialistDaoImpl() {
        super("specialiste");
    }

    public void returnProfilSpecialist(int id_specialist) {
        getSpecific("idUser", id_specialist);
    }

    public ResultSet returnAllProfiles() {
        ResultSet res = this.getAll();
        return res;
    }

    public void editProfileUser(int id_patient, String newName){
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.setById("idUser", id_patient, "name", newName);
    }

    public String getName(int id) throws SQLException {
        ResultSet res = getSpecific("idSpecialiste", id);
        int idUser;
        String name = "";
        UserDaoImpl userDao = new UserDaoImpl();
        if(res.next()) {
            try {
                idUser = res.getInt("idUser");
                name = userDao.getName(idUser);
                System.out.println("Specialiste name : " + name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 
        return name;
    }

    @Override
    public void editProfileSpecialist(Specialist specialiste) {
        int id = specialiste.getIdSpecialist();
        editProfileUser(specialiste.getUser().getUserId(), specialiste.getNameUser());
        setById("idSpecialiste", id, "description", specialiste.getDescription());
        setById("idSpecialiste", id, "tarif", specialiste.getTarif());
        setById("idSpecialiste", id, "moyenneNote", specialiste.getMoyenne_note());
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
