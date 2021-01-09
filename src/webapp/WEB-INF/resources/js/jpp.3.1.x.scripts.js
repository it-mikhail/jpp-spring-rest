function showUsersTable() {
  $("#users_table").show();
  $("#new_user_form").hide();
  $("#nav_tab_users_table").addClass("active");
  $("#nav_tab_new_user_form").removeClass("active");
}

function showNewUserForm() {
  $("#users_table").hide();
  $("#new_user_form").show();
  $("#nav_tab_users_table").removeClass("active");
  $("#nav_tab_new_user_form").addClass("active");
}

function fillForm(action, id) {
  $.getJSON("/admin/user/" + id, function(data) {
    $("#" + action + "_id").val(data.id);
    $("#" + action + "_first_name").val(data.firstName);
    $("#" + action + "_last_name").val(data.lastName);
    $("#" + action + "_age").val(data.age);
    $("#" + action + "_email").val(data.email);

    $.each(data.roles, function(key, val) {
      $("select#" + action + "_roles option[value="+val.id+"]").prop("selected", true);
    });
  
  });
}

function unselectOptions(action) {
  $("select option").prop("selected", false);
}

function addNewUser() {
  var firstName = $("#new_first_name").val();
  var lastName = $("#new_last_name").val();
  var age = $("#new_age").val();
  var email = $("#new_email").val();
  var password = $("#new_password").val();
  
  var user = {
    firstName: firstName,
    lastName: lastName,
    age: age,
    email: email,
    password: password,
    roles: []
  }
  
  var rolesText = "";
  $("#new_roles > option:selected").each(function() {
    var role = {
      id: $(this).val(),
      role: $(this).text()
    }
    rolesText += $(this).text() + " ";
    user.roles.push(role);
  });

  $.ajax({
    type: "POST",
    url: "/admin/users",
    data: JSON.stringify(user),
    contentType: "application/json; charset=UTF-8",
    success: function(response) {
      var id = response.id;

      var newTableRow = '<tr id="tr_' + id + '">'
                      + generateRowCells(id, firstName, lastName, age, email, rolesText)
                      + '</tr>';
                      
      
      $('#users_list_table > tbody:last-child').append(newTableRow);
      
      showUsersTable();  
      $("#new_user_data_form")[0].reset(); 
    }
  });
}

function generateRowCells(id, firstName, lastName, age, email, rolesText) {
  return '<td>'      + id
       + '</td><td>' + firstName
       + '</td><td>' + lastName
       + '</td><td>' + age
       + '</td><td>' + email
       + '</td><td>' + rolesText
       + '</td><td><button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#editModal" onclick="fillForm(\'edit\', \'' + id
       + '\');">Edit</button></td><td><button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteModal" onclick="fillForm(\'delete\', \'' + id + '\');">Delete</button></td>';
}

function editUser() {
  var id = $("#edit_id").val();
  var row = $("#tr_" + id);
  var firstName = $("#edit_first_name").val();
  var lastName = $("#edit_last_name").val();
  var age = $("#edit_age").val();
  var email = $("#edit_email").val();
  var password = $("#edit_password").val();

  var user = {
    id: id,
    firstName: firstName,
    lastName: lastName,
    age: age,
    email: email,
    password: password,
    roles: []
  }
  
  var rolesText = "";
  $("#edit_roles > option:selected").each(function() {
    var role = {
      id: $(this).val(),
      role: $(this).text()
    }
    rolesText += $(this).text() + " ";
    user.roles.push(role);
  });
 
  $.ajax({
    type: "PUT",
    url: "/admin/user/" + id,
    data: JSON.stringify(user),
    contentType: "application/json; charset=UTF-8",
    success: function(response) {
      $("#tr_" + id).html(generateRowCells(id, firstName, lastName, age, email, rolesText));
      unselectOptions("edit");
    }
  });

}

function deleteUser() {
  var id = $("#delete_id").val();
  var row = $("#tr_" + id);
  var firstName = $("#delete_first_name").val();
  var lastName = $("#delete_last_name").val();
  var age = $("#delete_age").val();
  var email = $("#delete_email").val();
  var password = $("#delete_password").val();
  
  var user = {
    id: id,
    firstName: firstName,
    lastName: lastName,
    age: age,
    email: email,
    password: password,
    roles: []
  }
  
  var rolesText = "";
  $("#delete_roles > option:selected").each(function() {
    var role = {
      id: $(this).val(),
      role: $(this).text()
    }
    rolesText += $(this).text() + " ";
    user.roles.push(role);
  });
  
  $.ajax({
    type: "DELETE",
    url: "/admin/user/" + id,
    data: JSON.stringify(user),
    contentType: "application/json; charset=UTF-8",
    success: function(response) {
      // row.html(generateRowCells(id, firstName, lastName, age, email, rolesText));
      $("#tr_" + id).remove();
      unselectOptions("delete");
    }
  });
  
}
