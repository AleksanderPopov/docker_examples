const enterCode = 13;

document.addEventListener(
  'DOMContentLoaded',
  function() {
    document.getElementById('new-todo').addEventListener('keyup', addTodo)
	  initTodos();
  }
);

let initTodos = function() {
	todos = JSON.parse(http('GET', 'getAllTasks'));
	for (count = 0; count < todos.length; count++) {
		document.getElementById('todo-list').appendChild(createTodo(todos[count]));
	}
}

let createTodo = function(todoText) {
  let newli = document.createElement('li');

  newli.textContent = todoText;

  newli.addEventListener(
    'dblclick',
    (event) =>  {
      document.getElementById('todo-list').innerHTML = '';

      http('DELETE', '?text=' + todoText);
      initTodos();
    }
  );

  return newli;
};

let addTodo = function(event) {
  let inputField = event.target;
  let text = inputField.value;

  if (event.which === enterCode && text !== '') {
    inputField.value = '';
	  document.getElementById('todo-list').innerHTML = '';

    http('POST', '?text=' + text);
	  initTodos();
  }
};

function http(requestType, theUrl) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open(requestType, theUrl, false);
    xmlHttp.send(null);
	  return xmlHttp.response;
}
