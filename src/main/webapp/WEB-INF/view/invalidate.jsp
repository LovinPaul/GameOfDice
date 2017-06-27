<%String cPath = request.getContextPath();%>

<html>
    <body>

        //testing purposes only
        <%session.invalidate();%>
        <%response.sendRedirect(cPath + "/login");%>


    </body>

</html>