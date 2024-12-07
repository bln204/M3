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

        <input type="hidden" name="productId" value="${idUpdate}">

        <div class="mb-3">
            <label for="productName" class="form-label">Tên sản phẩm:</label>
            <input type="text" class="form-control" id="productName" name="productName" value="${nameUpdate}" >
        </div>

        <div class="mb-3">
            <label for="inventory" class="form-label">Số lượng tồn kho:</label>
            <input type="number" class="form-control" id="inventory" name="inventory" value="${inventoryUpdate}" min="0" >
        </div>

        <div class="mb-3">
            <label for="price" class="form-label">Giá tiền:</label>
            <input type="number" class="form-control" id="price" name="price" value="${priceUpdate}" min="0" step="0.01" >
        </div>

        <div class="mb-3">
            <label for="directory" class="form-label">Danh mục:</label>
            <select class="form-select" id="directory" name="directory" >
                <c:forEach items="${directories}" var="dir">
                    <option value="${dir.id}" <c:if test="${dir.id == directory_id_update}">selected</c:if>>
                            ${dir.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Quy cách đóng gói:</label>
            <textarea class="form-control" id="description" name="description" rows="3">${descriptionUpdate}</textarea>

        </div>
        
        <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-primary">Cập nhật</button>
            <a href="?action=" class="btn btn-secondary">Quay lại</a>
        </div>
    </form>
</div>
<div class="modal fade" id="emailErrorModal" tabindex="-1" aria-labelledby="emailErrorModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="emailErrorModalLabel">Lỗi</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ${errorMessage}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    <% if (request.getAttribute("errorMessage") != null) { %>
    let emailErrorModal = new bootstrap.Modal(document.getElementById('emailErrorModal'));
    emailErrorModal.show();
    <% } %>
</script>

</body>
</html>
