<html>
<body>
<h2>go2 - Not found</h2>
<p>Not found: ${pathInfo}</p>

<form action="/register" method="post">
    <input name="shortName" type="hidden" value="${pathInfo}" />
    <input name="redirectTarget" type="text" width="1100px" style="width: 1100px;" />
</form>
</body>
</html>