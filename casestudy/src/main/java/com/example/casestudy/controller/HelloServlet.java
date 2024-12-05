package com.example.casestudy.controller;

import com.example.casestudy.model.Directory;
import com.example.casestudy.model.Product;
import com.example.casestudy.service.IProductService;
import com.example.casestudy.service.ProductServiceImpl;

import java.io.*;
import java.util.ArrayList;
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
        dispatcher.forward(request, response);
    }
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/product-list/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "save":
                saveProduct(request, response);
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

    private void saveProduct(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("productName");
        int inventory = Integer.parseInt(request.getParameter("inventory"));
        double price = Double.parseDouble(request.getParameter("price"));
        int directory_id = Integer.parseInt(request.getParameter("directory"));
        String description = request.getParameter("note");
        Directory directory = new Directory(directory_id,"");
        if(validateName(name)){
            request.setAttribute("errorMessage", "Tên sản phẩm chỉ chứa ký tự và khoảng trắng!!");
            forwardCreate(request, response, name, inventory, price, directory_id, description);
            return;
        }
        if(inventory < 0){
            request.setAttribute("errorMessage", "Số lượng phải lớn hơn 0!!");
            forwardCreate(request, response, name, inventory, price, directory_id, description);
            return;
        }
        if(price < 0){
            request.setAttribute("errorMessage", "Giá sản phẩm phải lớn hơn 0!!");
            forwardCreate(request, response, name, inventory, price, directory_id, description);
            return;
        }
        Product product = new Product(name,inventory,directory,price,description);
        productService.saveProduct(product);

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
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("productName");
        int inventory = Integer.parseInt(request.getParameter("inventory"));
        double price = Double.parseDouble(request.getParameter("price"));
        int directory_id = Integer.parseInt(request.getParameter("directory"));
        String description = request.getParameter("note");
        if(validateName(name)){
            request.setAttribute("errorMessage", "Tên sản phẩm chỉ chứa ký tự và khoảng trắng!!");
            forwardCreate(request, response, name, inventory, price, directory_id, description);
            return;
        }
        if(inventory < 0){
            request.setAttribute("errorMessage", "Số lượng phải l��n hơn 0!!");
            forwardCreate(request, response, name, inventory, price, directory_id, description);
            return;
        }
        if(price < 0){
            request.setAttribute("errorMessage", "Giá sản phẩm phải l��n hơn 0!!");
            forwardCreate(request, response, name, inventory, price, directory_id, description);
            return;
        }
        Product product = new Product(id,name,inventory,new Directory(directory_id),price,description);
        productService.saveProduct(product);
        try {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateName(String name) {
        return !name.matches("^[\\p{L}\\s]{1,255}$");
    }

    private void forwardCreate(HttpServletRequest request, HttpServletResponse response,String name, int inventory,double price, int directory_id, String description){
        Product product = new Product(name, inventory,new Directory(directory_id),price,description);
        request.setAttribute("product", product);
        List<Directory> directoryList = productService.findAllDirectory();
        request.setAttribute("directoryList", directoryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product-list/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    public void destroy() {
    }
}