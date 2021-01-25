//package pl.dkobylarz.garage_system_api.factory;
//
//import pl.dkobylarz.garage_system_api.user.constant.UserRoles;
//import pl.dkobylarz.garage_system_api.car.domain.Car;
//import pl.dkobylarz.garage_system_api.user.domain.User;
//
//import java.util.Collections;
//import java.util.Set;
//
//public class UserFactory {
//
//    public static User createUserWith(int userId,String username) {
//        return User.builder()
//                .userId(userId)
//                .password("admin")
//                .username(username)
//                .userAvatar("")
//                .roleId(1)
//                .cars(Collections.emptySet())
//                .build();
//    }
//
//    public static User createUserWithCars(int userId, String username, Set<Car> cars){
//        return User.builder()
//                .userId(userId)
//                .password("12345")
//                .username(username)
//                .userAvatar("")
//                .roleId(1)
//                .cars(cars)
//                .build();
//    }
//
//    public static User createUserWithCarsAndRole(int userId, String username, UserRoles userRole, Set<Car> cars){
//        return User.builder()
//                .userId(userId)
//                .password("12345")
//                .username(username)
//                .userAvatar("")
//                .roleId(userRole.getRoleId())
//                .cars(cars)
//                .build();
//    }
//}
