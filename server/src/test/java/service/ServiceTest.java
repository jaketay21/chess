package service;

import dataaccess.MemAuthDAO;
import dataaccess.MemGameDAO;
import dataaccess.MemUserDAO;
import models.Authtoken;
import models.GameData;
import models.ResponseException;
import models.UserData;
import org.junit.jupiter.api.*;
import request.CreateGameRequest;
import request.JoinRequest;
import request.LoginRequest;
import request.RegisterRequest;
import response.GameListResponse;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


class ServiceTest {
    static final MemGameDAO GAME_DAO = new MemGameDAO();
    static final MemUserDAO USER_DAO = new MemUserDAO();
    static final MemAuthDAO AUTH_DAO = new MemAuthDAO();
    static final AuthService AUTH_SERVICE = new AuthService(AUTH_DAO);
    static final UserService USER_SERVICE = new UserService(USER_DAO, AUTH_SERVICE);
    static final GameService GAME_SERVICE = new GameService(GAME_DAO, AUTH_SERVICE);
    static final DBService DB_SERVICE = new DBService(USER_DAO,
            GAME_DAO, AUTH_DAO);
    @BeforeEach
    void setUp() throws ResponseException {
            DB_SERVICE.clearAll();
    }
    //----------
    //User Service Tests
    //----------

    @Test
    void registerUserPositive() throws ResponseException {
        RegisterRequest request = new RegisterRequest("test1", "beast","hotmail");
        Authtoken token = USER_SERVICE.registerUser(request);
        UserData test = new UserData(request.username(), request.password(), request.email());
        assertNotNull(token);
        assertEquals(USER_DAO.getData(test.username()),test);
    }

    @Test
    void registerUserNegative() throws ResponseException {
        RegisterRequest badRequest = new RegisterRequest("","","");
        assertThrows(ResponseException.class, () -> USER_SERVICE.registerUser(badRequest));

    }

    @Test
    void loginPositive() throws ResponseException{
        LoginRequest request = new LoginRequest("test1", "beast");
        UserData test = new UserData("test1","beast","email");
        USER_DAO.addUser(test);
        Authtoken token = USER_SERVICE.login(request);
        assertNotNull(token);

    }

    @Test
    void loginNegative() throws ResponseException{
        LoginRequest badRequest = new LoginRequest("","");
        assertThrows(ResponseException.class, () -> USER_SERVICE.login(badRequest));
    }

    @Test
    void logoutPositive() throws ResponseException{
        String token = "tehe";
        AUTH_DAO.addAuth(token,"test1");
        USER_SERVICE.logout(token);
        assertFalse(AUTH_DAO.contains(token));
    }

    @Test
    void logoutNegative() throws ResponseException{
        assertThrows(ResponseException.class, () -> USER_SERVICE.logout("whoops"));

    }

    //---------
    // Game Service Tests
    //---------

    @Test
    void createGamePositive() throws ResponseException{
        String token = "tehe";
        AUTH_DAO.addAuth(token,"test1");
        CreateGameRequest request = new CreateGameRequest("testGame");
        int actual = GAME_SERVICE.createGame(token,request);
        assertNotNull(GAME_DAO.getGames());
        assertEquals(actual, 1);
    }

    @Test
    void createGameNegative() throws ResponseException{
        String token = "tehe";
        AUTH_DAO.addAuth(token,"test1");
        CreateGameRequest badRequest = new CreateGameRequest("");
        assertThrows(ResponseException.class, () -> GAME_SERVICE.createGame(token,badRequest));
    }

    @Test
    void joinGamePositive() throws ResponseException{
        String token = "tehe";
        AUTH_DAO.addAuth(token,"test1");
        GAME_DAO.createGame("test");
        JoinRequest request = new JoinRequest("WHITE", 1);
        GAME_SERVICE.joinGame(token,request);
        GameData actual = GAME_DAO.getGame(1);
        assertEquals(actual.getWhiteUsername(),"test1");

    }

    @Test
    void joinGameNegative() throws  ResponseException{
        JoinRequest badRequest = new JoinRequest("blue", 1);
        assertThrows(ResponseException.class, () -> GAME_SERVICE.joinGame("",badRequest));

    }

    @Test
    void listGamesPositive() throws ResponseException{
        String token = "tehe";
        AUTH_DAO.addAuth(token,"test1");
        GAME_DAO.createGame("test");
        Collection<GameListResponse> games;
        games = GAME_SERVICE.listGames(token);
        assertNotNull(games);
    }

    @Test
    void listGamesNegative() throws ResponseException{
        assertThrows(ResponseException.class, () -> GAME_SERVICE.listGames(""));
    }

    //---------
    // Auth Service Tests
    //---------

    @Test
    void addAuthPositive() throws ResponseException{
        Authtoken token = AUTH_SERVICE.addAuth("test1");
        String actual = AUTH_DAO.getKey(token.authToken());
        assertEquals("test1", actual);
    }

    @Test
    void addAuthNegative() throws ResponseException{
        assertThrows(ResponseException.class, () -> AUTH_SERVICE.addAuth(""));
    }

    @Test
    void deleteTokenPositive() throws ResponseException{
        AUTH_DAO.addAuth("lol","jake");
        AUTH_SERVICE.deleteToken("lol");
        assertFalse(AUTH_DAO.contains("lol"));

    }

    @Test
    void deleteTokenNegative() throws ResponseException{
        assertThrows(ResponseException.class, () -> AUTH_SERVICE.deleteToken(""));
    }

    @Test
    void getKeyPositive() throws ResponseException{
        AUTH_DAO.addAuth("lol","jake");
        String actual = AUTH_SERVICE.getKey("lol");
        assertEquals("jake",actual);
    }

    @Test
    void getKeyNegative() throws ResponseException{
        assertThrows(ResponseException.class, () -> AUTH_SERVICE.getKey(""));

    }

    @Test
    void isAuthorizedPositive() throws ResponseException{
        AUTH_DAO.addAuth("lol","jake");
        assertTrue(AUTH_SERVICE.isAuthorized("lol"));
    }

    @Test
    void isAuthorizedNegative() throws ResponseException{
        assertThrows(ResponseException.class, () -> AUTH_SERVICE.isAuthorized(""));


    }
}
