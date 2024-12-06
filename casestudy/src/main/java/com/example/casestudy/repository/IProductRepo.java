package com.example.casestudy.repository;

import com.example.casestudy.model.Directory;
import com.example.casestudy.model.Product;

import java.util.List;

public interface IProductRepo {
    List<Product> findAll();
    void saveProduct(String name, int inventory, double price, int directory_id,String description );
    List<Directory> findAllDirectory();
    void deleteProduct(int id);
    Product findById(int id);
    void updateProduct(int id, String name, int inventory, double price, int directory_id,String description);
    List<Product> searchProduct(double price,String directory);
}
