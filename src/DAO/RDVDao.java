package DAO;
import Models.RDV;
public interface RDVDao{

    public void ajouterRDV(RDV rdv);

    public void supprimerRDV(RDV rdv);

    public void modifierRDV(RDV rdv);

}