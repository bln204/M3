package com.example.casestudy.service;

import com.example.casestudy.model.Directory;
import com.example.casestudy.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    void saveProduct(Product product);
    List<Directory> findAllDirectory();
    void deleteProduct(int id);
    Product findById(int id);
    void updateProduct(Product product);
}
