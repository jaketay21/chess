package handlers;

import service.DBService;
import io.javalin.http.Context;

public class DBHandler {
    private final DBService dbService;


    public DBHandler(DBService dbService){
        this.dbService = dbService;
    }

    public void handleClearAll(Context ctx){
        dbService.clearAll();
        ctx.json("{}");
        ctx.status(200);
    }
}
