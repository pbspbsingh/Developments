<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="not isAnonymous()">
	<c:redirect url="decider.html"></c:redirect>
</sec:authorize>

<jsp:include page="head.jsp" />
<div data-role="page" data-theme="a" data-title="<spring:message code="app.title"/>" id="login">
	<div data-role="header" data-theme="a" data-position="fixed">
		<h1><spring:message code="login.title"/></h1>
	</div>
	<div data-role="content">
		<section style="margin-top:20px;">
			<p style="text-align: justify;">
				<spring:message code="login.message" />
			</p>
			<form action="signin/facebook" method="post" data-ajax="false" style="margin-top:20px; text-align:center;" id="signInForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<input type="hidden" name="scope" value="email,public_profile,user_birthday,user_education_history,user_groups,user_hometown,user_likes,user_location,user_work_history">
				<input type="hidden" name="display" value="popup"> 
				<a href="#" id="facebook" data-role="none"><spring:message code="login.facebook" /></a>
			</form>
		</section>
	</div>
</div>
<div data-role="footer" data-position="fixed">
	 <div data-role="navbar">
	 	<ul>
	        <li><a href="#login" data-transition="slide" data-direction="reverse"><spring:message code="login.login"/></a></li>
	        <li><a href="#about" data-transition="slide"><spring:message code="login.about"/></a></li>
	    </ul>
	 </div>
</div>
<div data-role="page" data-theme="a" data-title="<spring:message code="login.about"/>" id="about">
	<div data-role="header" data-theme="a" data-position="fixed">
		<h3><spring:message code="login.about"/></h3>
	</div>
	<div data-role="content">
		<p>
			<spring:message code="login.aboutMsg" />
		</p>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("[data-role='navbar']").navbar();
	$("[data-role='footer']").toolbar({ theme: "a" });
	$('#facebook').on('click', function(event){
		event.preventDefault();
		$('#signInForm').trigger('submit');
	})
});
$(document).on('pageshow', "[data-role='page']", function(){
	$("[data-role='navbar'] a.ui-btn-active").removeClass("ui-btn-active");
	if(location.hash && location.hash=="#about"){
		$("[data-role='navbar'] a").eq(1).addClass("ui-btn-active");
	} else {
		$("[data-role='navbar'] a").eq(0).addClass("ui-btn-active");
	}
});
</script>
<jsp:include page="tail.jsp" />