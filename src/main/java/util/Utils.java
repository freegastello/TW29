//package util;
//import model.User;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
//public class Utils {
//    public static boolean idIsNumber(HttpServletRequest request) {
//        final String id = request.getParameter("id");
//        return id != null && (id.length() > 0) && id.matches("[+]?\\d+");
//    }
//
//    public static boolean requestIsValid(HttpServletRequest request) {
//        final String name = request.getParameter("name");
//        final String login = request.getParameter("login");
//        final String password = request.getParameter("password");
//        return name != null && name.length() > 0 &&
//				login != null && login.length() > 0 &&
//				password != null && password.length() > 0;
//    }
//
//    public static User createStubUser(final int id, final String name, final String login, final String password) {
////        User user = new User();
//        User user = new User(id, name, login, password);
////        user.setId(id);
////        user.setName(name);
////        user.setLogin(login);
////        user.setPassword(password);
//        return user;
//    }
//
//    public static boolean idIsInvalid(final String id, Map<Integer, User> repo) {
//        return !(id != null && id.matches("[+]?\\d+") && repo.get(Integer.parseInt(id)) != null);
//    }
//}
