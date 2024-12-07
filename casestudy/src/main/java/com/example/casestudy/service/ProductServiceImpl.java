package com.example.casestudy.service;

import com.example.casestudy.model.Directory;
import com.example.casestudy.model.Product;
import com.example.casestudy.repository.IProductRepo;
import com.example.casestudy.repository.ProductRepoImpl;
import com.example.casestudy.validation.ProductValidator;

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
        // Validate dữ liệu
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống");
        }
        if (!ProductValidator.validateName(name)) {
            throw new IllegalArgumentException("Tên sản phẩm chỉ chứa ký tự, số và khoảng trắng");
        }
        if (!ProductValidator.validateInventory(inventory)) {
            throw new IllegalArgumentException("Số lượng sản phẩm phải lớn hơn 0");
        }
        if (!ProductValidator.validatePrice(price)) {
            throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0");
        }
        if (!ProductValidator.validateDescription(description)) {
            throw new IllegalArgumentException("Mô tả sản phẩm không được để trống");
        }
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
        // Validate các trường thông qua ProductValidator
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống");
        }
        if (!ProductValidator.validateName(name)) {
            throw new IllegalArgumentException("Tên sản phẩm chỉ chứa ký tự, số và khoảng trắng");
        }

        if (!ProductValidator.validateInventory(inventory)) {
            throw new IllegalArgumentException("Số lượng sản phẩm phải lớn hơn 0 và không được để trống");
        }

        if (!ProductValidator.validatePrice(price)) {
            throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0 và không được để trống");
        }

        if (!ProductValidator.validateDescription(description)) {
            throw new IllegalArgumentException("Mô tả sản phẩm không được để trống");
        }

        // Nếu tất cả các trường đều hợp lệ, thực hiện cập nhật sản phẩm
        productRepo.updateProduct(id, name, inventory, price, directory_id, description);
    }

    @Override
    public List<Product> searchProduct(double price, String directory) {
        return productRepo.searchProduct(price, directory);
    }
}
