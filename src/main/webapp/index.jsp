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
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <title>Todo items</title>
</head>
<body>
<div class="container">

    <div class="card">
        <div class="card-header">Add new Todo item</div>
        <div class="card-body">
            <form>
                <div class="form-group">
                    <div class="row">
                        <label for="name">Name </label>
                        <input id="name" type="text" placeholder="Enter todo name" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label for="desc">Description</label>
                        <input id="desc" type="text" placeholder="Enter description" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label for="deadline">Deadline</label>
                        <div class="input-group date">
                            <input type="date" id="deadline" name="trip-start"
                                   min="2000-01-01" max="2040-12-31">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label for="status">Status</label>
                        <div class="form-check" id="status">
                            <input class="from-check-input" type="radio" name="done" value="done" id="done">
                            <label for="done">
                                Done
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="from-check-input" type="radio" name="inProgress" value="inProgress"
                                   id="inProgress">
                            <label for="inProgress">
                                In progress
                            </label>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Add</button>
            </form>
        </div>
    </div>


    <div class="card">
        <div class="card-header">Todo list</div>
        <div class="card-body">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Created</th>
                    <th scope="col">Description</th>
                    <th scope="col">Deadline</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${todos}" var="todo">
                </c:forEach>
                <tr>
                    <td>
                        Item 1
                    </td>
                    <td>
                        Today
                    </td>
                    <td>
                        Desc
                    </td>
                    <td>
                        Tomorrow
                    </td>
                    <td>
                        In progress
                    </td>
                </tr>
                </tbody>

            </table>
        </div>
    </div>

</div>
</body>
</html>
