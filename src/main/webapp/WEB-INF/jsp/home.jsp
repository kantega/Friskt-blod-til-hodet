                                                                                            <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet – admin</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
</head>
<body>

<div id="aktiviteter">
    <ul id="aktivitetlist">

    </ul>
</div>

<script type="text/javascript">
   $.get('/aktiviteter', [], function(data){
      $('#aktivitetlist').each(data, function(i, value){
          this.append('<li>' + value + '</li>')
      })
   }, 'json');
</script>
</body>
</html>