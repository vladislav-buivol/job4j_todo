<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="js/index/script.js"></script>
    <title>Todo items</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div class="container">
    <div class="card">
        <div class="card-header">Add new Todo item</div>
        <div class="card-body">
            <form autocomplete="off" method="post">
                <div class="form-group">
                    <div class="row">
                        <label for="desc">Description</label>
                        <input id="desc" name="desc" type="text" placeholder="Enter description" class="form-control"
                               required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label for="status">Status</label>
                        <div class="form-check" id="status">
                            <input class="from-check-input" type="radio" name="status" value="done" id="done" required>
                            <label for="done">
                                Done
                            </label>
                            <input class="from-check-input" type="radio" name="status" value="in_progress"
                                   id="inProgress" required>
                            <label for="inProgress">
                                In progress
                            </label>
                        </div>
                    </div>
                </div>
                <span type="submit" class="btn btn-primary" onclick="return add()">Add</span>
            </form>
        </div>
    </div>
    <div class="card">
        <div class="card-header">Todo list</div>
        <div class="card-body">
            <input type="checkbox" id="showAll" name="showAll" onclick="updateTable()" checked>
            <label for="showAll">Show al</label>
            <table class="table" id="todos">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Description</th>
                    <th scope="col">Created</th>
                    <th scope="col">Status</th>
                    <th scope="col">Author</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody id="todosBody">
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
