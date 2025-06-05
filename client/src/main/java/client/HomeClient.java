package client;

public class HomeClient {
    private ServerFacade facade;
    HomeClient(String url) {
        facade = new ServerFacade(url);
    }

    public Boolean eval (String in) {
        return false;
    }

    private boolean quit() {
        System.out.println("Goodbye :)");
        return true;
    }
}