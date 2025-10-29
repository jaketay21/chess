package handlers;

import models.ResponseException;
import service.DBService;
import io.javalin.http.Context;

import java.util.Map;

public class DBHandler extends BaseHandler{
    private final DBService dbService;


    public DBHandler(DBService dbService){
        this.dbService = dbService;
    }

    public void handleClearAll(Context ctx)throws ResponseException {
        try {
            dbService.clearAll();
            ctx.json("{}");
            ctx.status(200);
        }catch (ResponseException e){
            ctx.status(e.getCode());
            ctx.result(toJson(Map.of("message", e.getMessage())));
        }
    }
}
