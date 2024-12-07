package com.example.casestudy.repository;

import com.example.casestudy.common.BaseRepository;
import com.example.casestudy.model.Directory;
import com.example.casestudy.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepoImpl implements IProductRepo {



    private static BaseRepository baseRepository;
    static {
        baseRepository = new BaseRepository();
    }

    private static final String FIND_ALL = "CALL GetAllProduct()";

    private static final String FIND_BY_ID = "CALL GetProductById(?)";

    private static final String INSERT_INTO = "CALL AddNewProduct(?,?,?,?,?)";

    private static final String FIND_ALL_DIRECTORY = "SELECT * FROM directories";

    private static final String UPDATE = "UPDATE products SET product_name =?, inventory =?," +
            " price =?, directory_id =?, note =? WHERE product_id =?";

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
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_DIRECTORY);
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
    public void saveProduct(String name, int inventory, double price, int directory_id, String description) {
        Connection connection = baseRepository.getConnection();
        try {
            CallableStatement statement = connection.prepareCall(INSERT_INTO);
            statement.setString(1, name);
            statement.setInt(2, inventory);
            statement.setDouble(3, price);
            statement.setInt(4, directory_id);
            statement.setString(5, description);
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
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
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
    public void updateProduct( int id,String name, int inventory, double price, int directory_id,String description) {
        Connection connection = baseRepository.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, name);
            statement.setInt(2, inventory);
            statement.setDouble(3, price);
            statement.setInt(4, directory_id);
            statement.setString(5, description);
            statement.setInt(6, id);
            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Product> searchProduct(double price, String directory) {
        Connection connection = baseRepository.getConnection();
        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder
                ("SELECT p.product_id, p.product_name, p.inventory, p.price, p.note, d.directory_name " +
                        "FROM products p JOIN directories d ON p.directory_id = d.directory_id WHERE 1=1 ");
        if(directory!= null && !directory.isEmpty()) {
            sql.append(" AND d.directory_name LIKE ?");
        }
        if(price > 0){
            sql.append(" AND p.price =?");
        }

        try(PreparedStatement statement = connection.prepareStatement(sql.toString())){
            if(directory!= null && !directory.isEmpty() ){
                statement.setString(1, directory);
            }
            if(price > 0 && (directory == null || directory.isEmpty())){
                statement.setDouble(1, price);
            }
            if(price > 0 && directory != null && !directory.isEmpty()){
                statement.setDouble(2, price);
            }

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                int inventory = resultSet.getInt("inventory");
                double priceValue = resultSet.getDouble("price");
                String description = resultSet.getString("note");
                String directoryName = resultSet.getString("directory_name");
                list.add(new Product(id, name, inventory, new Directory(directoryName), priceValue, description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
