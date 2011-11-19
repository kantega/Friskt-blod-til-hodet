<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet – admin</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
</head>
<body>

<div class="status" id="adminstatus"></div>

<div class="admin" id="aktivitet">
  <form class="adminForm" id="aktivitetForm" method="post" action="/admin/aktivitet">
      <label for="aktivitetname">Aktivitetsnavn</label>
      <input name="name" id="aktivitetname" type="text">
      <input type="submit" value="Lagre">
  </form>

</div>

<div class="admin" id="person">
  <form class="adminForm" id="personForm" method="post" action="/admin/person">
      <label for="personname">Personnavn</label>
      <input name="name" id="personname" type="text">
      <input type="submit" value="Lagre">
  </form>

</div>

<div class="admin" id="avdeling">
  <form class="adminForm" id="avdelingForm" method="post" action="/admin/avdeling">
      <label for="avdelingname">Avdelingsnavn</label>
      <input name="name" id="acdelingname" type="text">
      <input type="submit" value="Lagre">
  </form>

</div>

<script type="text/javascript">
    $('.adminForm').submit(function(event){
        event.preventDefault();
        $.post($(this).attr('action'), $(this).serializeArray(), function(data){
            $("#adminstatus").html(data);
        }, 'json');
        return false;
    });
</script>
</body>
</html>