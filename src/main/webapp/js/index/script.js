$(document).ready(
    requestTodos(createTable, logError)
)

function logError(err) {
    console.log(err);
}

function createTable(todos) {
    let tbody = document.createElement("tbody");
    tbody.setAttribute('id', 'todosBody');
    for (let i in todos) {
        let todo = todos[i];
        let categories = "";
        if (todo.categories.length > 0) {
            for (let catI in todo.categories) {
                categories += todo.categories[catI].name;
                if (catI < todo.categories.length - 1) {
                    categories += ", ";
                }
            }
        }

        if (!$('#showAll').is(':checked') && todo.done) {
            continue;
        }
        let statusImg = "in_progress.png";
        if (todo.done) {
            statusImg = "done.png";
        }
        let btnText = "Mark as Undone";
        if (!todo.done) {
            btnText = "Mark as Done";
        }
        let aName;
        if (todo.author == undefined) {
            aName = "UNKNOWN"
        } else {
            aName = todo.author.name;
        }
        tbody.innerHTML += `
                    <tr>
                    <td>
                    ${todo.id}
                    </td>
                    <td>
                    ${todo.description}
                    </td>
                    <td>
                    ${todo.created}
                    </td>
                    <td>
                    ${aName}
                    </td>
                    <td>
                    <img src="img/status/${statusImg}">
                    </td> 
                     <td>
                    ${categories}
                    </td>
                    <td>
                    <button type="button" class="btn btn-light" onclick="changeStatus(${todo.id})">${btnText}</button>
                    </td>
                    </tr>`;
    }
    $('#todos > tbody').replaceWith(tbody);
    return tbody;
}

function requestTodos(successCallback, unSuccessCallback) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_todo/loadtodos',
        dataType: 'json'
    }).done(function (response) {
        return successCallback(response);
    }).fail(function (err) {
        return unSuccessCallback(err);
    });
}

function changeStatus(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_todo/changeStatus',
        dataType: 'json',
        data: {
            "id": id
        },
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    }).done(function (response) {
        requestTodos(createTable, logError);
    }).fail(function (response) {
        requestTodos(createTable, logError)
    })
}

function updateTable() {
    requestTodos(createTable, logError);
}

function add() {
    let description = $('#desc').val();
    let status = $('input[name="status"]:checked').val();
    let cIds = $('#cIds').val();
    if (isEmpty(description) || isEmpty(status) || $('#cIds').val().length === 0) {
        return;
    }
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_todo/add',
        data: {
            desc: description,
            status: status,
            cIds: cIds
        }
    }).done(function (response) {
        updateTable();
    }).fail(function (err) {
        logError(err);
    })
}

function isEmpty(value) {
    return value == null || !value.trim().length;
}
