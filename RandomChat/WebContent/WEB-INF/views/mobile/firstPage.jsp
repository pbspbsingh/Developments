<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../head.jsp" />
<div data-role="page" data-theme="a" data-title="${name}">
	<div data-role="header" data-theme="a" data-position="fixed">
		<h2>${name}</h2>
	</div>
	<div data-role="content">
		<section class="center" id="defaultMsg">
			<p><img alt="loading" src="${pageContext.servletContext.contextPath}/static/ajax-loader.gif" /></p>
			<p><spring:message code="main.loading" /></p>
		</section>
		<section class="center" id="errorMsg" style="display: none; color: red;">
			<p><spring:message code="main.error" /></p>
			<a href="../index.html" data-ajax="false" data-role="button" class="ui-btn ui-btn-inline ui-icon-refresh">Retry</a>
		</section>
	</div>
</div>
<script type="text/javascript">
	$(document).on('pageinit', function(){
		if(location.hash === "#_=_")
			location.hash = "";
		
		$.ajax({
			 url: 'refresh.html',
			 cache: false,
			 dataType: 'json'
		})
		.done(function(data){
			$.mobile.changePage("preference.html", { transition: "slideup" });
		})
		.fail(function(error){
			$('#defaultMsg').hide();
			$('#errorMsg').show();
		});
	})
</script>
<jsp:include page="../tail.jsp" />