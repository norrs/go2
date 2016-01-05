<!--<form action="/register" method="post"> -->
<div class="lead">
    <label for="shortName">Your memorizable URL</label>

    <div class="input-group">
        <span class="input-group-addon" id="basic-addon2">https://go.norrs.no/</span>
        <input type="text" class="form-control" id="shortName" aria-describedby="basic-addon3" <#if pathInfo??>
               value="${pathInfo}"</#if> />
    </div>
</div>
<div class="lead">
    <label for="redirectTarget">Redirects to URL</label>

    <div class="input-group">
        <span class="input-group-addon" id="basic-addon3">URL</span>
        <input type="text" class="form-control" id="redirectTarget" aria-describedby="basic-addon3">
    </div>
</div>

<p class="lead">
    <a href="#" class="btn btn-lg btn-default">Create</a>
    <a href="#" class="btn btn-lg btn-default">Learn more</a>
</p>
<!-- </form> -->