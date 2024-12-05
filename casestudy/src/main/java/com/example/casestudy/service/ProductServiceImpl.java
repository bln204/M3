package com.example.casestudy.service;

import com.example.casestudy.model.Directory;
import com.example.casestudy.model.Product;
import com.example.casestudy.repository.IProductRepo;
import com.example.casestudy.repository.ProductRepoImpl;

import java.util.List;

public class ProductServiceImpl implements IProductService{
    private IProductRepo productRepo = new ProductRepoImpl();



    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Directory> findAllDirectory() {
        return productRepo.findAllDirectory();
    }

    @Override
    public void saveProduct(Product product) {
        productRepo.saveProduct(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepo.deleteProduct(id);
    }
    @Override
    public Product findById(int id) {
        return productRepo.findById(id);
    }

    @Override
    public void updateProduct(Product product) {
        productRepo.updateProduct(product);
    }
}
