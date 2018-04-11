<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : Kalender
    Created on : Apr 11, 2018, 5:17:22 PM
    Author     : Dynamitos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Terminkalender</title>
    </head>
    <body>
        <h1>Terminkalender</h1>
        <form method="POST" action="TerminServlet">
            <input type="submit" name="left" value="<"/>
            ${currentTag.format}
            <input type="submit" name="right" value=">"/>
        </form>
        <br/>    
        <form method="POST" action="TerminServlet">
            Termintext: <input type="text" name="text" value="Termintext" /><br/>
            Von:        <input type="text" name="von" value="8" /><br/>
            Bis:        <input type="text" name="bis" value="12" /><br/>
            <input type="submit" name="speichern" value="speichern" /><br/>
        </form>
        <table border="2">
            <c:forEach var="s" items="${currentTag.kalender}">
                <tr>
                    <td>
                        <c:out value="${s.key}"/>
                    </td>
                    <td>
                        <c:out value="${s.value}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
