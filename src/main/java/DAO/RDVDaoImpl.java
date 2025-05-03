package DAO;
public class RDVDaoImpl extends GeneralDaoImpl implements RDVDao{
    private DaoFactory daoFactory;
    public RDVDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory; //comprendre quoi changer mais il y a un daofactory ds general
    }
}