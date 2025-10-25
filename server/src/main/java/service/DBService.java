package service;

import dataaccess.MemAuthDAO;
import dataaccess.MemGameDAO;
import dataaccess.MemUserDAO;

public class DBService {
    private final MemUserDAO userDao;
    private final MemGameDAO gameDao;
    private final MemAuthDAO authDao;

    public DBService(MemUserDAO userDao, MemGameDAO gameDao, MemAuthDAO authDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
        this.authDao = authDao;
    }

    /**
     * Clears all data from all DAOs
     */
    public void clearAll() {
        userDao.clearUsers();
        gameDao.clearGames();
        authDao.clearAuths();
    }
}
