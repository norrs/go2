<#include "base.ftl">

    <#macro page_title>
        Go2 - 404 - Not found
    </#macro>

    <#macro page_nav>
        <nav>
            <ul class="nav masthead-nav">
                <li><a href="/">Home</a></li>
            <!--    <li><a href="#">Latest creations</a></li>
                <li><a href="#">Contact</a></li> -->
            </ul>
        </nav>
    </#macro>

    <#macro page_body>
        <div class="inner cover">

            <h1 class="cover-heading"><@page_title/></h1>

            <p class="lead">The memoriazable URL ${pathInfo} does not exist.

            <p class="lead">You could register it below if you want
                to!</p>
            <#include "register_form.ftl" />

        </div>

    </#macro>

    <@display_page/>

