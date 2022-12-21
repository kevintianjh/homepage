<%@page import="java.util.ArrayList"%>
<html>
<head>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/line-awesome.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/style.css" />
</head>
<body>
	<!-- NAV BAR-->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>"> <span
				class="h3 fw-bold">Kevin Tian</span>
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="<%=request.getContextPath()%>/static/index.html#home">Home</a>
					</li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="<%=request.getContextPath()%>/static/index.html#about">About</a>
					</li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="<%=request.getContextPath()%>/static/index.html#skills">Skills</a>
					</li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="<%=request.getContextPath()%>/static/index.html#contact">Contact</a>
					</li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> Tools </a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="pwapp">Password
									Generator</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- NAV BAR END-->

	<div class="container" style="padding-top: 100px;">

		<form method="post">
			<table class="table"
				style="border-color: black; border-style: solid;">
				<tr>
					<td style="width: 30%;"></td>
					<td style="width: 70%;"><h1>Password Generator</h1></td>
				</tr>
				<tr>
					<td><h3>How many?</h3></td>
					<td><select id="form_howmany" name="form_howmany"
						class="form-control" style="width: 65%;" onchange="onChange()">

							<%
							String howmanyVal = request.getParameter("form_howmany") == null ? "" : request.getParameter("form_howmany");
							%>

							<option value="1" <%=howmanyVal.equals("1") ? "selected" : ""%>>1</option>
							<option value="2" <%=howmanyVal.equals("2") ? "selected" : ""%>>2</option>
							<option value="3" <%=howmanyVal.equals("3") ? "selected" : ""%>>3</option>
							<option value="4" <%=howmanyVal.equals("4") ? "selected" : ""%>>4</option>
							<option value="5" <%=howmanyVal.equals("5") ? "selected" : ""%>>5</option>
							<option value="6" <%=howmanyVal.equals("6") ? "selected" : ""%>>6</option>
							<option value="7" <%=howmanyVal.equals("7") ? "selected" : ""%>>7</option>
							<option value="8" <%=howmanyVal.equals("8") ? "selected" : ""%>>8</option>
							<option value="9" <%=howmanyVal.equals("9") ? "selected" : ""%>>9</option>
					</select></td>
				</tr>
				<tr>
					<td>
						<h3>Labels (Optional)</h3>
					</td>
					<td>
						<%
						int howMany = (Integer) request.getAttribute("howMany");
						%> <%
						 String labels[] = request.getParameterValues("form_pwlabel");
						 %> <%
						 for (int c = 0; c < howMany; c++) {
						 %> <%
						 String label = null;
						 %> <%
						 if (labels != null && c < labels.length) {
						 %> <%
						 label = labels[c];
						 %> <%
						 } else {
						 %> <%
						 label = "";
						 %> <%
						 }
						 %> <input maxlength="20"
						placeholder="<%=c == 0 ? "(Optional) Place a description beside each generated pw" : ""%>"
						type="text" name="form_pwlabel" class="form-control"
						style="width: 65%; margin-top: 5px;" value="<%=label%>"></input> <%
						 }
						 %>
					</td>
				</tr>
				<tr>
					<td>
						<h3>Options</h3>
					</td>
					<td><input type="checkbox" id="form_lowercase"
						name="form_lowercase" value="1"
						<%=request.getParameter("form_lowercase") == null ? "" : "checked"%>><b
						style="padding-left: 5px; padding-right: 5px;"><label
							for="form_lowercase">Lower Case</label></b> <input type="checkbox"
						id="form_uppercase" name="form_uppercase" value="1"
						<%=request.getParameter("form_uppercase") == null ? "" : "checked"%>><b
						style="padding-left: 5px; padding-right: 5px;"><label
							for="form_uppercase">Upper Case</label></b> <input type="checkbox"
						id="form_number" name="form_number" value="1"
						<%=request.getParameter("form_number") == null ? "" : "checked"%>><b
						style="padding-left: 5px; padding-right: 5px;"><label
							for="form_number">Number</label></b> <input type="checkbox"
						id="form_specialchar" name="form_specialchar" value="1"
						<%=request.getParameter("form_specialchar") == null ? "" : "checked"%>><b
						style="padding-left: 5px; padding-right: 5px;"><label
							for="form_specialchar">Special Char</label></b>
				</tr>
				<tr>
					<td><h3>Password (Optional)</h3></td>
					<td><input maxlength="20" name="form_filepw"
						placeholder="(Optional) Password for generated file, 6 to 20 chars"
						type="password" class="form-control"
						style="width: 65%; margin-top: 5px;"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="submit" class="btn btn-primary"
						value="Generate"
						style="background-color: orange; color: black; width: 25%; border-color: orange;"></input>
					</td>
				</tr>

				<%
				Object obj = request.getAttribute("errors");
				%>
				<%
				if (obj != null) {
				%>
				<%
				ArrayList<String> errors = (ArrayList<String>) obj;
				%>
				<tr>
					<td></td>
					<td>
						<div class="alert alert-danger" role="alert">
							<ul>
								<%
								for (String error : errors) {
								%>
								<li><%=error%></li>
								<%
								}
								%>
							</ul>

						</div>
					</td>
				</tr>
				<%
				}
				%>
			</table>
		</form>

	</div>
	<script>
		function onChange() {
			var val = document.getElementById("form_howmany").value;
			window.location.href = "pwapp?form_howmany=" + val;
		}
	</script>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/static/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/static/js/main.js"></script>
</body>
</html>