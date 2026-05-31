<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>Kết quả</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
</head>

<body>

<div class="container mt-5 text-center">

    <h1>Kết quả thi</h1>

    <h2>
        Điểm:
        <span th:text="${score}"></span>
        /
        <span th:text="${total}"></span>
    </h2>

    <a th:href="@{/home}" class="btn btn-primary mt-3">
        Về trang chủ
    </a>

</div>

</body>
</html>