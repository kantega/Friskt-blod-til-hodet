<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.mobile-1.0.min.js"></script>
<script>
    $.urlParam = function(name){
        var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (!results) { return 0; }
        return results[1] || 0;
    }
</script>

<link rel="shortcut icon" href="/resources/favicon.ico">
<link href="/resources/css/jquery.mobile-1.0.min.css" rel="stylesheet">
<link href="/resources/css/main.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes"/>
<link rel="apple-touch-startup-image" href="/resources/images/logo.png"/>