package com.example.casestudy.repository;

import com.example.casestudy.common.BaseRepository;
import com.example.casestudy.model.Directory;
import com.example.casestudy.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class ProductRepoImpl implements IProductRepo {



    private static BaseRepository baseRepository;
    static {
        baseRepository = new BaseRepository();
    }

    private static final String FIND_ALL = "CALL GetAllProduct()";

    private static final String INSERT_INTO = "INSERT INTO products (product_name, inventory, price, directory_id, note) VALUES (?,?,?,?,?)";
    @Override
    public List<Product> findAll() {
        Connection connection = baseRepository.getConnection();
        List<Product> list = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                int inventory = resultSet.getInt("inventory");
                double price = resultSet.getDouble("price");
                int directory_id = resultSet.getInt("directory_id");
                String directory_name = resultSet.getString("category");
                String description = resultSet.getString("note");
                Product product = new Product(id, name, inventory,new Directory(directory_id,directory_name),price, description);
                list.add(product);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Directory> findAllDirectory() {
        Connection connection = baseRepository.getConnection();
        List<Directory> list = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM directories");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("directory_id");
                String name = resultSet.getString("directory_name");
                list.add(new Directory(id, name));
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void saveProduct(Product product) {
        Connection connection = baseRepository.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getInventory());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getDirectory().getId());
            statement.setString(5, product.getDescription());
            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteProduct(int id) {
        Connection connection = baseRepository.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE product_id =?");
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Product findById(int id) {
        Connection connection = baseRepository.getConnection();
        Product product = null;
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE product_id =?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                int inventory = resultSet.getInt("inventory");
                double price = resultSet.getDouble("price");
                int directory_id = resultSet.getInt("directory_id");
                String directory_name = resultSet.getString("category");
                String description = resultSet.getString("note");
                product = new Product(productId, name, inventory, new Directory(directory_id, directory_name), price, description);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        Connection connection = baseRepository.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE products SET product_name =?, inventory =?, price =?, directory_id =?, note =? WHERE product_id =?");
            statement.setString(1, product.getName());
            statement.setInt(2, product.getInventory());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getDirectory().getId());
            statement.setString(5, product.getDescription());
            statement.setInt(6, product.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
