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
public class HelloServlet extends HttpServlet {
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
        request.getRequestDispatcher("/product-list/index.jsp").forward(request, response);
    }
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product-list/create.jsp");
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
                    request.getRequestDispatcher("/product-list/index.jsp").forward(request, response);
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
            request.getRequestDispatcher("/product-list/index.jsp").forward(request, response);
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
        String name = request.getParameter("productName");
        String sInventory = request.getParameter("inventory");
        String sPrice = request.getParameter("price");
        int directory_id = Integer.parseInt(request.getParameter("directory"));
        String description = request.getParameter("description");
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Tên sản phẩm không được để trống!!");
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
            return;
        }
        if(validateName(name)){
            request.setAttribute("errorMessage", "Tên sản phẩm chỉ chứa ký tự và khoảng trắng!!");
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
            return;
        }

        if(sInventory == null || sInventory.isEmpty()) {
            request.setAttribute("errorMessage", "Số lượng không được để trống!!");
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
            return;
        }
        int inventory = Integer.parseInt(sInventory);

        if(inventory <= 0){
            request.setAttribute("errorMessage", "Số lượng phải lớn hơn 0!!");
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
            return;
        }

        if(sPrice == null || sPrice.isEmpty()) {
            request.setAttribute("errorMessage", "Giá sản phẩm không được để trống!!");
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
            return;
        }
        double price = Double.parseDouble(sPrice);
        if(price <= 0){
            request.setAttribute("errorMessage", "Giá sản phẩm phải lớn hơn 0!!");
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
            return;
        }
        if(description == null || description.isEmpty()){
            request.setAttribute("errorMessage", "Mô tả sản phẩm không được để trống!!");
            forwardCreate(request, response, name, sInventory, sPrice, directory_id, description);
            return;
        }

        productService.saveProduct(name,inventory,price,directory_id,description);

        try {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (IOException e) {
            e.printStackTrace();
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
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Tên sản phẩm không được để trống!!");
            forwardUpdate(request, response,id, name, sInventory, sPrice, directory_id, description);
            return;
        }
        if(validateName(name)){
            request.setAttribute("errorMessage", "Tên sản phẩm chỉ chứa ký tự và khoảng trắng!!");
            forwardUpdate(request, response, id, name, sInventory, sPrice, directory_id, description);
            return;
        }

        if(sInventory == null || sInventory.isEmpty()) {
            request.setAttribute("errorMessage", "Số lượng không được để trống!!");
            forwardUpdate(request, response,id, name, sInventory, sPrice, directory_id, description);
            return;
        }
        int inventory = Integer.parseInt(sInventory);

        if(inventory <= 0){
            request.setAttribute("errorMessage", "Số lượng phải lớn hơn 0!!");
            forwardUpdate(request, response,id, name, sInventory, sPrice, directory_id, description);
            return;
        }

        if(sPrice == null || sPrice.isEmpty()) {
            request.setAttribute("errorMessage", "Giá sản phẩm không được để trống!!");
            forwardUpdate(request, response,id, name, sInventory, sPrice, directory_id, description);
            return;
        }
        double price = Double.parseDouble(sPrice);
        if(price <= 0){
            request.setAttribute("errorMessage", "Giá sản phẩm phải lớn hơn 0!!");
            forwardUpdate(request, response,id, name, sInventory, sPrice, directory_id, description);
            return;
        }
        if(description == null || description.isEmpty()){
            request.setAttribute("errorMessage", "Mô tả sản phẩm không được để trống!!");
            forwardUpdate(request, response,id, name, sInventory, sPrice, directory_id, description);
            return;
        }
        productService.updateProduct(id, name, inventory, price, directory_id, description);
        try {
            response.sendRedirect(request.getContextPath() + "/product-list/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateName(String name) {
        return !name.matches("^[\\p{L}\\d\\s-]{1,255}$");
    }

    private void forwardCreate(HttpServletRequest request, HttpServletResponse response,String name, String inventory,String price, int directory_id, String description){
        List<Directory> directoryList = productService.findAllDirectory();
        request.setAttribute("name", name);
        request.setAttribute("inventory", inventory);
        request.setAttribute("price", price);
        request.setAttribute("directory_id", directory_id);
        request.setAttribute("description", description);

        request.setAttribute("directories", directoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product-list/create.jsp");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product-list/edit.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    public void destroy() {
    }
}