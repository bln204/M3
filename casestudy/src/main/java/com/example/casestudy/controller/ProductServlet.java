package com.example.casestudy.controller;

import com.example.casestudy.model.Directory;
import com.example.casestudy.model.Product;
import com.example.casestudy.service.IProductService;
import com.example.casestudy.service.ProductServiceImpl;

import java.io.*;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/")
public class ProductServlet extends HttpServlet {
    private IProductService productService;

    public void init() {
        productService = new ProductServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showUpdateForm(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            case"search":
                searchProduct(request, response);
                break;
            default:
                showProductList(request, response);
                break;
        }
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.findAll();
        request.setAttribute("productList", products);
        request.getRequestDispatcher("/management_product/index.jsp").forward(request, response);
    }
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/management_product/create.jsp");
        request.setAttribute("directories",productService.findAllDirectory());
        dispatcher.forward(request, response);
    }
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        forwardUpdate(request, response,product.getId(),product.getName(),product.getInventory()+"",product.getPrice()+"",product.getDirectory().getId(),product.getDescription());
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response){
        String sPrice = request.getParameter("price");
        double price = 0;
        if(sPrice != null && !sPrice.isEmpty()){
            if ((price > 0)) {
                price = Double.parseDouble(sPrice);
            } else {
                request.setAttribute("errorMessage", "Giá không thể nhỏ hơn 0. Vui lòng nhập lại.");
                List<Product> list = productService.findAll(); // Thêm phương thức này để lấy toàn bộ sản phẩm
                request.setAttribute("productList", list);
                try {
                    request.getRequestDispatcher("/management_product/index.jsp").forward(request, response);
                } catch (ServletException | IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }

        String directory = request.getParameter("directory");
        List<Product> list = productService.searchProduct(price,directory);
        request.setAttribute("productList", list);
        request.setAttribute("sDirectory", directory);
        request.setAttribute("sPrice", (sPrice == null || sPrice.isEmpty()) ? null : price);
        try {
            request.getRequestDispatcher("/management_product/index.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                addNewProduct(request, response);
                break;
            case "edit":
                updateProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
                default:
                    showProductList(request, response);
                    break;
        }
    }

    private void addNewProduct(HttpServletRequest request, HttpServletResponse response) {
        // Lấy dữ liệu từ form
        String name = request.getParameter("productName");
        String sInventory = request.getParameter("inventory");
        String sPrice = request.getParameter("price");
        int directory_id = Integer.parseInt(request.getParameter("directory"));
        String description = request.getParameter("description");

        try {
            // Chuyển đổi các giá trị từ chuỗi sang kiểu số
            int inventory = sInventory.isEmpty() ? 0 : Integer.parseInt(sInventory);
            double price = sPrice.isEmpty() ? 0 : Double.parseDouble(sPrice);

            // Gọi service để thêm sản phẩm mới
            productService.saveProduct(name, inventory, price, directory_id, description);

            // Redirect về trang danh sách sản phẩm sau khi thêm thành công
            response.sendRedirect(request.getContextPath() + "/management_product/index");

        } catch (IllegalArgumentException e) {
            // Nếu validation thất bại, thông báo lỗi cho người dùng
            request.setAttribute("errorMessage", e.getMessage());
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
        } catch (Exception e) {
            // Nếu có lỗi khác xảy ra (như lỗi hệ thống), thông báo lỗi chung
            request.setAttribute("errorMessage", "Đã có lỗi xảy ra, vui lòng thử lại sau.");
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
        }
    }
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        if(product!= null){
            productService.deleteProduct(id);
        }
        try {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateProduct(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("productName");
        String sInventory = request.getParameter("inventory");
        String sPrice = request.getParameter("price");
        int directory_id = Integer.parseInt(request.getParameter("directory"));
        String description = request.getParameter("description");

        try {
            // Chuyển đổi giá trị số lượng và giá từ chuỗi nhập vào
            int inventory = sInventory.isEmpty() ? 0 : Integer.parseInt(sInventory);
            double price = sPrice.isEmpty() ? 0 : Double.parseDouble(sPrice);

            // Gọi service để cập nhật sản phẩm
            productService.updateProduct(id, name, inventory, price, directory_id, description);

            // Redirect về trang danh sách sản phẩm sau khi cập nhật thành công
            response.sendRedirect(request.getContextPath() + "/management_product/index");

        } catch (IllegalArgumentException e) {
            // Xử lý thông báo lỗi nếu validation thất bại
            request.setAttribute("errorMessage", e.getMessage());
            forwardUpdate(request, response, id, name, sInventory, sPrice, directory_id, description);
        } catch (Exception e) {
            // Xử lý các lỗi hệ thống khác
            request.setAttribute("errorMessage", "Đã có lỗi xảy ra, vui lòng thử lại sau.");
            forwardUpdate(request, response, id, name, sInventory, sPrice, directory_id, description);
        }
    }

    private void forwardCreate(HttpServletRequest request, HttpServletResponse response,String name, String inventory,String price, int directory_id, String description){
        List<Directory> directoryList = productService.findAllDirectory();
        request.setAttribute("name", name);
        request.setAttribute("inventory", inventory);
        request.setAttribute("price", price);
        request.setAttribute("directory_id", directory_id);
        request.setAttribute("description", description);

        request.setAttribute("directories", directoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/management_product/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    private void forwardUpdate(HttpServletRequest request, HttpServletResponse response, int id, String name, String inventory, String price, int directory_id, String description){
        List<Directory> directoryList = productService.findAllDirectory();
        request.setAttribute("idUpdate", id);
        request.setAttribute("nameUpdate", name);
        request.setAttribute("inventoryUpdate", inventory);
        request.setAttribute("priceUpdate", price);
        request.setAttribute("directory_id_update", directory_id);
        request.setAttribute("descriptionUpdate", description);

        request.setAttribute("directories", directoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/management_product/edit.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    public void destroy() {
    }
}