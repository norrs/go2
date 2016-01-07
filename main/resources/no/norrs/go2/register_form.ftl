<form id="register" action="/register" method="post">
    <div class="lead">
        <label for="shortName">Your memorizable URL</label>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon2">http://${globals.main_vhost}/</span>
            <input type="text" class="form-control" id="shortName" name="shortName" aria-describedby="basic-addon3"
            <#if pathInfo??>
                value="${pathInfo}"
            </#if>
            />
        </div>
    </div>
    <div class="lead">
        <label for="redirectTarget">Redirects to URL</label>

        <div class="input-group">
            <span class="input-group-addon" id="basic-addon3">URL</span>
            <input type="text" class="form-control" id="redirectTarget" name="redirectTarget"
                   aria-describedby="basic-addon3">
        </div>
    </div>

    <p class="lead">

        <button type="submit" class="btn btn-default btn-lg btn-success">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create
        </button>

    </p>
</form>
