<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="my-4 text-center">Chỉnh sửa sản phẩm</h2>
    <form action="?action=edit" method="post">

        <input type="hidden" name="productId" value="${product.id}">

        <div class="mb-3">
            <label for="productName" class="form-label">Tên sản phẩm:</label>
            <input type="text" class="form-control" id="productName" name="productName" value="${product.name}" required>
        </div>

        <div class="mb-3">
            <label for="inventory" class="form-label">Số lượng tồn kho:</label>
            <input type="number" class="form-control" id="inventory" name="inventory" value="${product.inventory}" min="0" required>
        </div>

        <div class="mb-3">
            <label for="price" class="form-label">Giá tiền:</label>
            <input type="number" class="form-control" id="price" name="price" value="${product.price}" min="0" step="0.01" required>
        </div>

        <div class="mb-3">
            <label for="directory" class="form-label">Danh mục:</label>
            <select class="form-select" id="directory" name="directory" required>
                <c:forEach items="${directories}" var="dir">
                    <option value="${dir.directory_id}" ${dir.directory_id == product.directory_id ? "selected" : ""}>
                            ${dir.directory_name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Quy cách đóng gói:</label>
            <textarea class="form-control" id="description" name="description" rows="3">${product.description}</textarea>
        </div>
        
        <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-primary">Cập nhật</button>
            <a href="/product-list/index.jsp" class="btn btn-secondary">Quay lại</a>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
