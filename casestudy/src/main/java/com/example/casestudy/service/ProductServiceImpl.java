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
    public void saveProduct(String name, int inventory, double price, int directory_id, String description) {
        productRepo.saveProduct(name, inventory, price, directory_id, description);
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
    public void updateProduct(int id,String name, int inventory, double price, int directory_id, String description) {
        productRepo.updateProduct(id,name,inventory, price, directory_id, description);
    }

    @Override
    public List<Product> searchProduct(double price, String directory) {
        return productRepo.searchProduct(price, directory);
    }
}
