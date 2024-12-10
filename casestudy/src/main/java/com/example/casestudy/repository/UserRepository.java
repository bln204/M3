package com.example.casestudy.repository;

import com.example.casestudy.common.BaseRepository;
import com.example.casestudy.model.Role;
import com.example.casestudy.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final BaseRepository baseRepository = new BaseRepository();

    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT u.id, u.username, u.password, r.id AS role_id, r.role_name " +
                "FROM users u JOIN roles r ON u.role_id = r.id " +
                "WHERE u.username = ? AND u.password = ?";

        try (Connection connection = baseRepository.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));

                user.setRole(role);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
