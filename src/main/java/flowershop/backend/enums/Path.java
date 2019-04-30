package flowershop.backend.enums;

import java.util.HashMap;
import java.util.Map;

public enum Path {
    LOGIN           ("/user/login",     false,"/login.jsp"),
    REGISTER        ("/user/register",  false,"/register.jsp"),
    LOGOUT          ("/user/logout",    false),
    USER_INDEX      ("/user",           false,"/userIndex.jsp"),
    ORDER_NEW       ("/order/new",      false),
    ORDER_SHOW      ("/order/detail",   true, "/orderShow.jsp" ),
    ADD_TO_CART     ("/order/add_to_cart",true),
    REMOVE_FROM_CART("/order/remove_from_cart",true),
    REMOVE_CART_ITEM("/order/remove_cart_item",true),
    ORDER_PAY       ("/order/pay",      true),
    ORDER_CLOSE     ("/order/close",    true),
    ORDER_DELETE    ("/order/delete",   true),
    ORDER_INDEX     ("/order",          false,"/orderIndex.jsp"),
    FLOWER_NEW      ("/flower/new",     false,"/flowerForm.jsp"),
    FLOWER_UPDATE   ("/flower/update",  true, "/flowerForm.jsp"),
    FLOWER_DELETE   ("/flower/delete",  true),
    FLOWER_INDEX    ("/flower",         false,"/flowerIndex.jsp");

    private String page;
    private String path;
    private boolean hasParam;

    Path(String path, boolean hasParam, String page) {
        this.path = path;
        this.hasParam = hasParam;
        this.page = page;
    }

    Path(String path, boolean hasParam) {
        this.path = path;
        this.hasParam = hasParam;
    }

    public String getPage() {
        return page;
    }

    public String getPath(){
        return path;
    }

    public static Path get(String path) {
        if(path.equals("/"))
            return FLOWER_INDEX;

        for (Path p : Path.values())
            if (path.equals(p.path) || (p.hasParam && path.contains(p.path)))
                return p;
        return null;
    }

    public static Map<String, String> getPathMap(){
        Map<String, String> map = new HashMap<>();
        for (Path p : Path.values())
            map.put(p.name(), p.path);

        return map;
    }
}