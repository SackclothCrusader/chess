package service;

import org.junit.jupiter.api.Test;
import service.*;
import server.Result;
import server.Request;

public class ServiceTests {
    @Test
    public void clearTest() {
        Request.RegisterRequest req = new Request.RegisterRequest("a", "a", "a");
        new ClearService().clear(new Request.DeleteRequest());
    }
}
