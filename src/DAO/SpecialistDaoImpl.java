package DAO;

import Models.Specialist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpecialistDaoImpl extends UserDao implements SpecialistDao {

    private DaoFactory daoFactory;
    public SpecialistDaoImpl(DaoFactory daoFactory) {
        super(daoFactory);
        this.daoFactory = daoFactory;
    }

    public void ModifierSpecialiste(Specialist personne, String description, String schedule, float tarif) {
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
}
