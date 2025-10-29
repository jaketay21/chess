package service;

import dataaccess.MemAuthDAO;
import dataaccess.MemGameDAO;
import dataaccess.MemUserDAO;
import models.Authtoken;
import models.GameData;
import models.ResponseException;
import models.UserData;
import org.eclipse.jetty.util.log.Log;
import org.junit.jupiter.api.*;
import request.CreateGameRequest;
import request.JoinRequest;
import request.LoginRequest;
import request.RegisterRequest;
import response.GameListResponse;

import java.lang.module.ResolutionException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


class ServiceTest {
    static final MemGameDAO gameDao = new MemGameDAO();
    static final MemUserDAO userDao = new MemUserDAO();
    static final MemAuthDAO authDao = new MemAuthDAO();
    static final AuthService authService = new AuthService(authDao);
    static final UserService userService = new UserService(userDao, authService);
    static final GameService gameService = new GameService(gameDao, authService);
    static final DBService dBService = new DBService(userDao,
            gameDao, authDao);
    @BeforeEach
    void setUp() throws ResponseException {
            dBService.clearAll();
    }
    //----------
    //User Service Tests
    //----------

    @Test
    void registerUserPositive() throws ResponseException {
        RegisterRequest request = new RegisterRequest("test1", "beast","hotmail");
        Authtoken token = userService.registerUser(request);
        UserData test = new UserData(request.username(), request.password(), request.email());
        assertNotNull(token);
        assertEquals(userDao.getData(test.username()),test);
    }

    @Test
    void registerUserNegative() throws ResponseException {
        RegisterRequest badRequest = new RegisterRequest("","","");
        assertThrows(ResponseException.class, () -> userService.registerUser(badRequest));

    }

    @Test
    void loginPositive() throws ResponseException{
        LoginRequest request = new LoginRequest("test1", "beast");
        UserData test = new UserData("test1","beast","email");
        userDao.addUser(test);
        Authtoken token = userService.login(request);
        assertNotNull(token);

    }

    @Test
    void loginNegative() throws ResponseException{
        LoginRequest badRequest = new LoginRequest("","");
        assertThrows(ResponseException.class, () -> userService.login(badRequest));
    }

    @Test
    void logoutPositive() throws ResponseException{
        String token = "tehe";
        authDao.addAuth(token,"test1");
        userService.logout(token);
        assertFalse(authDao.contains(token));
    }

    @Test
    void logoutNegative() throws ResponseException{
        assertThrows(ResponseException.class, () -> userService.logout("whoops"));

    }

    //---------
    // Game Service Tests
    //---------

    @Test
    void createGamePositive() throws ResponseException{
        String token = "tehe";
        authDao.addAuth(token,"test1");
        CreateGameRequest request = new CreateGameRequest("testGame");
        int actual = gameService.createGame(token,request);
        assertNotNull(gameDao.getGames());
        assertEquals(actual, 1);
    }

    @Test
    void createGameNegative() throws ResponseException{
        String token = "tehe";
        authDao.addAuth(token,"test1");
        CreateGameRequest badRequest = new CreateGameRequest("");
        assertThrows(ResponseException.class, () -> gameService.createGame(token,badRequest));
    }

    @Test
    void joinGamePositive() throws ResponseException{
        String token = "tehe";
        authDao.addAuth(token,"test1");
        gameDao.createGame("test");
        JoinRequest request = new JoinRequest("WHITE", 1);
        gameService.joinGame(token,request);
        GameData actual = gameDao.getGame(1);
        assertEquals(actual.getWhiteUsername(),"test1");

    }

    @Test
    void joinGameNegative() throws  ResponseException{
        JoinRequest badRequest = new JoinRequest("blue", 1);
        assertThrows(ResponseException.class, () -> gameService.joinGame("",badRequest));

    }

    @Test
    void listGamesPositive() throws ResponseException{
        String token = "tehe";
        authDao.addAuth(token,"test1");
        gameDao.createGame("test");
        Collection<GameListResponse> games;
        games = gameService.listGames(token);
        assertNotNull(games);
    }

    @Test
    void listGamesNegative() throws ResponseException{
        assertThrows(ResponseException.class, () -> gameService.listGames(""));
    }

    //---------
    // Auth Service Tests
    //---------

    @Test
    void generateTokenPositive() throws ResponseException{

    }

    @Test
    void generateTokenNegative() throws ResponseException{

    }

    @Test
    void addAuthPositive() throws ResponseException{

    }

    @Test
    void addAuthNegative() throws ResponseException{

    }

    @Test
    void deleteTokenPositive() throws ResponseException{

    }

    @Test
    void deleteTokenNegative() throws ResponseException{

    }

    @Test
    void getKeyPositive() throws ResponseException{

    }

    @Test
    void getKeyNegative() throws ResponseException{

    }

    @Test
    void isAuthorizedPositive() throws ResponseException{

    }

    @Test
    void isAuthorizedNegative() throws ResponseException{

    }
}
