<#include "base.ftl">
    <#macro page_title>
        Go2 - Memorizable URLs service
    </#macro>

    <#macro page_nav>
        <nav>
            <ul class="nav masthead-nav">
                <li class="active"><a href="/">Home</a></li>
              <!--  <li><a href="#">Latest creations</a></li>
                <li><a href="#">Contact</a></li>-->
            </ul>
        </nav>
    </#macro>

    <#macro page_body>
        <div class="inner cover">

            <h1 class="cover-heading"><@page_title/></h1>

            <p class="lead">Go2 is a redirector service for memorizable URLs. </p>

            <#include "register_form.ftl" />

        </div>

    </#macro>

    <@display_page/>

