package in.sendza.logistics.Models;

import java.util.Map;

public class Presence {
    private boolean isLogin;
    private Map<String,String> lastOnline;

    public Presence(boolean isLogin,Map<String,String> lastOnline)
    {
        this.isLogin=isLogin;
        this.lastOnline=lastOnline;
    }
}
