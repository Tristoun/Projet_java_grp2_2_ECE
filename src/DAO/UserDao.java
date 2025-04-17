package DAO;

public class UserDao extends GeneralDaoImpl{
    
    public UserDao(DaoFactory dao) {
        super(dao, "user");
    }
}
