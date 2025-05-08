package DAO;

//import Models.Specialist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SpecialistDaoImpl extends GeneralDaoImpl implements SpecialistDao, UserDao {

    public SpecialistDaoImpl() {
        super("specialiste");
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

    public void deleteSpecialist(int idSpecialist){
        deleteFromTable("id_specialiste",idSpecialist,"specialiste");
        deleteFromTable("id_specialiste",idSpecialist,"specialisation_doc");
        deleteFromTable("id_specialiste",idSpecialist,"rdv");
        deleteFromTable("id_specialiste",idSpecialist,"lieu_specialiste");
    }

    public void insertSpecialist(int idUser, Object nomSpecialisation, String adresse, int codePostal, String ville) {
        Object IDSpecialist = insertSomething("id_user",idUser,"id_specialiste","specialiste");
        System.out.println("ID SPEcialist : " + IDSpecialist);
        setByIdOtherTable("id_specialiste", (Integer) IDSpecialist, "id_user", IDSpecialist,"specialiste");

        String IDSpecialisation2 = String.valueOf(getSpecificFromTable("nom",nomSpecialisation,"id_specialisation","specialisation"));
        System.out.println("IDSPECIALISATIONN : " + IDSpecialisation2);

        ResultSet rs = getSpecificFromTable("nom", nomSpecialisation, "id_specialisation", "specialisation");
        String IDSpecialisation = null;

        try {
            if (rs != null && rs.next()) {
                IDSpecialisation = rs.getString("id_specialisation");
                System.out.println("ID Specialisation: " + IDSpecialisation);
            } else {
                System.out.println("No specialisation found for: " + nomSpecialisation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<String, Object> mapSpec = new HashMap<>();
        mapSpec.put("id_specialiste", IDSpecialist);
        mapSpec.put("id_specialisation", IDSpecialisation);
        insertInOtherTable(mapSpec, "specialisation_doc");

        System.out.println("ID SPEcialisation : " + IDSpecialisation);


        //Object IDSpecialisation = insertSomething("nom",nomSpecialisation,"id_specialisation","specialisation");


      //  Object tempResult = insertSomething("id_specialisation",IDSpecialisation,"id_specialiste","specialisation_doc");
        //setByIdOtherTable("id_specialiste", (Integer) IDSpecialist, "id_specialisation", tempResult,"specialisation_doc");
        Map<String, Object> lieuMap = new HashMap<>();
        lieuMap.put("adresse", adresse);
        lieuMap.put("ville", ville);
        lieuMap.put("code_postal", codePostal);
        insertInOtherTable(lieuMap, "lieu");

        ResultSet resultLieu = getSpecificFromTable("adresse",adresse,"id_lieu","lieu");
        String idLieu = null;

        try {
            if (resultLieu != null && resultLieu.next()) {
                idLieu = resultLieu.getString("id_lieu");
                System.out.println("ID Lieu: " + idLieu);
            } else {
                System.out.println("nothing found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Map<String, Object> lieuSpecialisteMap = new HashMap<>();
        lieuSpecialisteMap.put("id_specialiste", IDSpecialist);
        lieuSpecialisteMap.put("id_lieu", idLieu);
        insertInOtherTable(lieuSpecialisteMap, "lieu_specialiste");
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

