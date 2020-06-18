<%@page import="java.io.FileOutputStream"%>
<%@page import="com.sampling.entities.User"%>
<%@page import="com.sampling.entities.Image"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%-- 
  - Description: Checking if user has logged in or not.
  - Using Cookies
  - redirects to login page if current user is invalid.
--%>

<%
	Cookie cookies [] = request.getCookies() ;
	if (cookies==null) {
		System.out.println("you should login first");
		response.sendRedirect("loginPage.html");
		return ;
	}
	String userName = null ;
	for (Cookie c : cookies) {
		if(c.getName().equals("username"))
			userName = c.getValue() ;
	}
	if (userName==null) {
		response.sendRedirect("loginPage.html");
		return ;
	}
%>
<!DOCTYPE html >
<html>
<head>
<title>Image Utility</title>
<style type="text/css">
body{
display: inline;
margin: 0px ;
}
table,th,td{
  border:1px solid black ;
  border-collapse:collapse ;
  text-align: center ;
}
img{
margin:5px;
}
#hiddenDiv{
position: fixed;
display:none ;
top:33% ;
left:45% ;
background-color: white ;
}
#overlay{
width: 100% ;
height: 100% ;
background-color: grey ;
opacity:0.7 ;
position: fixed;
display:none ;
}
legend {
     margin: auto;
}
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</style>
</head>
<body>
<div id ="overlay"></div>
<div id="hiddenDiv" >
<form id= "changeImgNameForm" method="post" action="ImageEdit" >
<fieldset>
<legend >Change Image Name</legend>
<label>Enter Name :  <Input name="name" required></label><br><br>
<input type="submit">
</fieldset>
</form>
</div>

<div style="border-bottom: solid 1px black">
<input type = "button" value = "Logout" style="float: right;margin-right: 40px;" >
<h1>Image Management Utility</h1>
</div><br>

<div>
<form method="post" action="ImageSave" enctype="multipart/form-data" id ="imgSaveForm">
<input type = "file" name="imgFile" style="margin-right: 400px" accept="image/*" required/>
<input type="submit" style="margin-right: 5px">
<input type="reset" >
</form>
</div>

<div>
<h2 style="margin-top: 40px">Uploaded Images</h2>
<table style="width: 100% ">
<tr>
<th>S.NO</th>
<th>Name</th>
<th>Size</th>
<th>Preview</th>
<th>Action</th>
</tr>

<%-- 
  - Description: Used to display the
  - Images under a User instance in a table.
  - Also provides for Editing, deleting or Adding Images.
--%>

<% 
List<Image> li =  User.getImageList(userName);
if(li==null)
{
	Cookie c =  new Cookie("username", "") ;
	c.setMaxAge(0);
	response.addCookie(c);
	response.sendRedirect("loginPage.html");
	return  ;
}
String id, name, size, preview , action ,imgPath ;

String SAVE_DIR,appPath,savePath,filePath ;
SAVE_DIR =  "Images";
appPath = request.getServletContext().getRealPath("");
savePath = appPath + SAVE_DIR;
System.out.println("save Path is "+savePath);

FileOutputStream fos ;
action = "<label class = 'del'>Delete</label>&nbsp;&nbsp;<label class = 'edit'>Edit</label>" ;
//action = "<img src= 'Images/cross_48.png ' class = 'del'><img src= 'Images/pen-checkbox-64.png' class = 'edit'>" ;

for(Image i : li){
	id = i.getId()+"";
	name = i.getImgName();
	size = i.getImgSize()+" Bytes";
		filePath = savePath + File.separator + name;
		fos = new FileOutputStream(filePath);
    fos.write(i.getImageData());
	preview = name;
	System.out.println("Fetching from "+filePath);
	out.println("<tr>");
	out.println("<td>"+id+"</td>");
	out.println("<td class='ImgName'>"+name+"</td>");
	out.println("<td>"+size+"</td>");
	out.println("<td><img width='150px' height = '150px' src = '" + filePath + "'></td>");
	out.println("<td>"+action+"</td>");
	out.println("</tr>");	
}
%>

</table>
</div>

<script src="jquery-3.1.0.js"></script>
<script>
var glObj  = null ; 
$("input[value='Logout']").on("click",function(){
	document.cookie = 'username' + '=; expires=Thu, 01-Jan-1970 00:00:01 GMT;';
	window.location.replace("Welcome");
});
$(".edit").on("click",function(){
	glObj = $(this).parents("tr") ;
	changeView();
	var str = glObj.children("td.ImgName").html();
});
$(".del").on("click",function(){
	var id = $(this).parents("tr").children("td:first").html();
	$(this).parents("tr").remove();
	$.ajax({url: "ImageDelete", method:"post", data: { imageId: id }, });
});
$("#changeImgNameForm").on("submit",function(e){
	e.preventDefault();
	var str = $("Input[name='name']").val();
	var id = glObj.children("td:first").html();
	changeView();
	$.ajax({url: "ImageEdit", method:"post", data: { imageId: id , newName:str},success: function(data){
		if(data=="error")
		{
		alert('Image Name could not be changed !!');
		}
	}});
	window.location.replace("ImageUtility");
});
$("#overlay").click(changeView);
function changeView(){
	$("#overlay").toggle();
	$("#hiddenDiv").toggle();
	$("Input[name='name']").val("");
}
</script>
</body>
</html>