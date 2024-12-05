<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>

<div class="container">
    <table class="table table-striped table-bordered table-hover" id="mainTable">
        <thead class="table-dark">
        <tr>
            <h1 class="text-center mb-4">Danh sách sản phẩm</h1>
        </tr>

<%--        <tr>--%>
<%--            <div>--%>
<%--                <form action="?action=search" method="get" class="row g-3">--%>
<%--                    <input type="hidden" name="action" value="search">--%>

<%--                    <div class="col-sm-12 d-flex justify-content-start">--%>
<%--                        <input type="text" class="form-control me-2" name="productName" value="${key}" placeholder="Nhập tên sản phẩm để tìm kiếm">--%>
<%--                        <button type="submit" class="btn btn-primary btn-sm">--%>
<%--                            <i class="bi bi-search"></i>--%>
<%--                        </button>--%>
<%--                    </div>--%>
<%--                </form>--%>
<%--            </div>--%>
<%--        </tr>--%>

        <tr>
            <th>ID</th>
            <th>Tên sản phẩm</th>
            <th>Số lượng trong kho</th>
            <th>Danh mục</th>
            <th>Giá tiền</th>
            <th>Quy cách đóng gói</th>
            <th>Chỉnh sửa</th>
            <th>Xóa</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productList}" var="p">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.inventory}</td>
                <td>${p.directory.name}</td>
                <td>${p.price}</td>
                <td>${p.description}</td>
                <td><a href="?action=edit&id=${p.id}" class="btn btn-primary">
                    <i class="fas fa-edit"></i> Chỉnh sửa</a></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal" data-id="${p.id}" data-name="${p.name}">
                    <i class="fas fa-trash-alt"></i> Xóa
                </button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        <a href="?action=create" class="btn btn-primary">Thêm mới sản phẩm</a>
    </div>
</div>

<!-- Modal for deletion confirmation -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn xóa sản phẩm <strong id="productName"></strong> không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <a id="confirmDelete" href="#" class="btn btn-danger">Xóa</a>
            </div>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

<script>
    $(document).ready(function() {
        $('#mainTable').DataTable({
            "dom": 'lrtip',
            "lengthChange": false,
            "pageLength": 5,
            "columnDefs": [
                { "orderable": false, "targets": [6, 7] }
            ]
        });
    });

    // Handling modal data for delete
    var deleteModal = document.getElementById('deleteModal');
    deleteModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var productId = button.getAttribute('data-id');
        var productName = button.getAttribute('data-name');

        var modalBody = deleteModal.querySelector('.modal-body #productName');
        modalBody.textContent = productName;

        var confirmDeleteLink = deleteModal.querySelector('.modal-footer #confirmDelete');
        confirmDeleteLink.setAttribute('href', '?action=delete&id=' + productId);
    });
</script>

</body>
</html>
