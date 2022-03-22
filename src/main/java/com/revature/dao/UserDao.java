package com.revature.dao;

        import com.revature.model.User;
        import com.revature.utility.ConnectionUtility;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

public class UserDao {

    public UserDao() {
    }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) { // Automatically closes the Connection object
            String query = "SELECT * FROM ers_users " +
                    "INNER JOIN ers_user_roles " +
                    "ON ers_users.user_role_id = ers_user_roles.ers_user_role_id " +
                    "WHERE ers_users.ers_username = ? AND ers_users.ers_password = ?";

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { // if there is actually a user to iterate over
                int userId = rs.getInt("ers_users_id");
                String uname = rs.getString("ers_username");
                String pass = rs.getString("ers_password");
                String role = rs.getString("user_role");
                String fname = rs.getString("user_first_name");
                String lname = rs.getString("user_last_name");
                String email = rs.getString("user_email");

                return new User(userId, uname, pass, fname, lname, email, role);
            }

            return null;
        }
    }

}
