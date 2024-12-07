package com.example.casestudy.validation;

public class ProductValidator {
    // Kiểm tra xem giá sản phẩm có hợp lệ không
    public static boolean validatePrice(Double price) {
        if (price == null || price <= 0) {
            return false; // Giá không hợp lệ (null hoặc <= 0)
        }
        return true;
    }

    // Kiểm tra số lượng có hợp lệ không
    public static boolean validateInventory(Integer inventory) {
        if (inventory == null || inventory <= 0) {
            return false; // Số lượng không hợp lệ (null hoặc <= 0)
        }
        return true;
    }

    // Kiểm tra tên sản phẩm
    public static boolean validateName(String name) {
        // Đảm bảo tên không rỗng và chỉ chứa ký tự hợp lệ
        return name != null && name.matches("^[\\p{L}\\p{N}\\s-]{1,255}$");
    }

    // Kiểm tra mô tả sản phẩm
    public static boolean validateDescription(String description) {
        return description != null && !description.trim().isEmpty();
    }
}

