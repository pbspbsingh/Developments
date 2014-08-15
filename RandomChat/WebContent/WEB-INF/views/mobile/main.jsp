<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../head.jsp" />

<div data-role="page" data-theme="a" data-title="${name}" data-url="main.html">
	<div data-role="header" data-theme="a" data-position="fixed">
		<h2>${name}</h2>
	</div>
	
	<div data-role="content">
		<ul data-role="listview" data-inset="true" data-divider-theme="a">
		    <li data-role="list-divider">Select a Group</li>
		    <li><a href="chat.html?category=randomlyRandom" data-transition="slide" id="randomlyRandom">
		    	Randomly random <span class="ui-li-count">0</span>
	    	</a></li>
		    <li data-role="list-divider">Categories from facebook</li>
		  	<c:forEach var="category" items="${categories}">
		  		<li><a href="chat.html?category=random" data-transition="slide" id="${category}">
		  			${category} <span class="ui-li-count">0</span>
	  			</a></li>
		  	</c:forEach>
		</ul>
	</div>
	
	<div data-role="footer" data-position="fixed">
		<div data-role="navbar">
	 		<ul>
	 			<li><a href="#" id="chatRoom" class="ui-btn-active">Chat Room</a></li>
	 			<li><a href="favList.html" id="favList">Favorite List</a></li>
	 		</ul>
	 	</div>
	</div>
	<script type="text/javascript">
		
	</script>
</div>

<jsp:include page="../tail.jsp" />