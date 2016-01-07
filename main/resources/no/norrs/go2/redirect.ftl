
<#include "base.ftl">

    <#macro page_title>
        Go2 - ${pathInfo} -> ${redirectTarget}
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

            <p class="lead">${pathInfo} wants to redirect you to <a href="${redirectTarget}">${redirectTarget}</a>. </p>

            <p class="lead">Click link if you want to go there!</p>

        </div>

    </#macro>

    <@display_page/>
