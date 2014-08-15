<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../head.jsp" />

<div data-role="page" data-theme="a" data-title="Preference">
	<div data-role="header" data-theme="a" data-position="fixed">
		<h3>Preferences</h3>
	</div>
	
	<div data-role="content">
		<form action="main.html" method="post" id="preferenceForm">
			<fieldset data-role="controlgroup">
				<legend>Choose yourself a nickname:</legend>
				<label for="nickName">Text input:</label>
				<input name="nickName" id="nickName" placeholder="${userPref.nickName}" type="text" autofocus="autofocus" value="" />
				<input type="hidden" id="prefId" name="prefId" value="${userPref.prefId}" />
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			</fieldset>
			<fieldset data-role="controlgroup">
			    <legend>You are interested in:</legend>
			    <input name="likesFemale" id="likesFemale" type="checkbox" 
			    	<c:if test="${userPref.likesFemale}">
			    		checked="checked"
			    	</c:if>
			    />
			    <label for="likesFemale">Female</label>
			    
			    <input name="likesMale" id="likesMale" type="checkbox"
			    	<c:if test="${userPref.likesMale}">
			    		checked="checked"
			    	</c:if>
			    />
			    <label for="likesMale">Male</label>
			</fieldset>
			<div data-role="rangeslider">
				<label for="ageRange-1a">Age range:</label>
	       		<input name="ageRange-1a" id="ageRange-1a" min="-40" max="40" value="${userPref.ageBelow}" type="range">
	       		<label for="ageRange-1b">Age range:</label>
	       		<input name="ageRange-1b" id="ageRange-1b" min="-40" max="40" value="${userPref.ageAbove}" type="range">
	       	</div>
       	</form>
	</div>
	
	<div data-role="popup" id="errorMsg">
		<p>Hoho</p>
	</div>
	
	<div data-role="footer" data-position="fixed">
		<div data-role="navbar">
	 		<ul>
	 			<li><a href="#main" id="submit">Save &amp; Continue</a></li>
	 		</ul>
	 	</div>
	</div>
	<script type="text/javascript">
	$(document).on('pageinit', function(){
		$('#errorMsg').popup();
		$('#submit').on('click', function(event){
			event.stopPropagation();
		    event.preventDefault();
			
			var male = $('#likesMale');
			var female = $('#likesFemale');
			if(!(male.prop('checked') || female.prop('checked'))) {
				$('#errorMsg').find('p').text('Please select either Male/Female or both.');
				$('#errorMsg').popup('open');
				return;
			} 
			var lowerLimit = parseInt($('#ageRange-1a').val());
			var upperLimit = parseInt($('#ageRange-1b').val());
			if(lowerLimit>-5 || upperLimit<5) {
				$('#errorMsg').find('p').text('For age range, please select lower limit below -5 and uppper limit above 5.');
				$('#errorMsg').popup('open');
				return;
			}
			var nickName = $('#nickName');
			if(nickName.val()=='')
				nickName.val(nickName.attr('placeholder'));
			
			$.mobile.loading('show');
			$.ajax({
				 url: "savePref.html",
				 data: $('#preferenceForm').serialize(),
				 dataType: 'json',
				 type: 'POST'
			})
			.done(function(){
				$.mobile.loading('hide');
				//$.mobile.navigate('main.html', {transition: 'none'});
			})
			.fail(function(){
				$.mobile.loading('hide');
				$('#errorMsg').find('p').text('Saving of preferences has failed, please try again.');
				$('#errorMsg').popup('open');
			});
		})
	});
	</script>	
</div>

<jsp:include page="../tail.jsp" />