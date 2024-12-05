<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm mới sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="my-4 text-center">Thêm mới sản phẩm</h2>
    <form action="?action=create" method="post">
        <!-- Tên sản phẩm -->
        <div class="mb-3">
            <label for="productName" class="form-label">Tên sản phẩm:</label>
            <input type="text" class="form-control" id="productName" name="productName" required>
        </div>

        <!-- Số lượng tồn kho -->
        <div class="mb-3">
            <label for="inventory" class="form-label">Số lượng nhập vào:</label>
            <input type="number" class="form-control" id="inventory" name="inventory" min="0" required>
        </div>

        <!-- Giá sản phẩm -->
        <div class="mb-3">
            <label for="price" class="form-label">Giá tiền:</label>
            <input type="number" class="form-control" id="price" name="price" min="0" step="0.01" required>
        </div>

        <!-- Chọn danh mục -->
        <div class="mb-3">
            <label for="directory" class="form-label">Danh mục:</label>
            <select class="form-select" id="directory" name="directory" required>
                <c:forEach items="${directories}" var="dir">
                    <option value="${dir.directory_id}">${dir.directory_name}</option>
                </c:forEach>
            </select>
        </div>

        <!-- Ghi chú -->
        <div class="mb-3">
            <label for="note" class="form-label">Quy cách đóng gói:</label>
            <textarea class="form-control" id="note" name="note" rows="3"></textarea>
        </div>

        <!-- Nút submit -->
        <button type="submit" class="btn btn-primary">Thêm mới sản phẩm</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
