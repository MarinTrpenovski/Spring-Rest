import com.springgoals.model.Permission;
import com.springgoals.model.Role;
import com.springgoals.model.dto.UserDTO;
import com.springgoals.service.impl.UserServiceImpl;

import java.sql.SQLException;

public class Test {

    public static void main(String[] args) throws SQLException {
        UserServiceImpl use = new UserServiceImpl();

        UserDTO userDTO = use.getUserRolePermissionsByEmail("marin@ibm.com");
        System.out.println("User email is : " + userDTO.getEmail());
        for(Role role : userDTO.getRoles()) {
            System.out.println("Role " +  role.getName() + " has following permissions: ");
            for(Permission per : role.getPermissions()) {

                System.out.println(" permission : "  + per.getName());
            }

        }

    }
}
