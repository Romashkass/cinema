<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
   <meta charset="UTF-8">
   <title>viewDiagnosticJSP</title>
   <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
    <%@ page import="java.util.List"%>
    <%@ page import="java.util.Set"%>
    <%@ page import="java.util.Optional"%>
    <%@ page import="java.util.stream.Collectors"%>
    <%@ page import="java.util.concurrent.atomic.AtomicReference"%>
    <%@ page import="java.util.function.Predicate"%>
    <%@ page import="java.util.Comparator"%>
    <%@ page import="org.example.cinema.entity.dto.MovieDTO"%>

    <%
        List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
    %>

    <div class="center flex full-vh">
        <div class="vertical-center">
            <a class="ml-20" href="/">На главную</a>
            <br />
            <br />
            <hr />
            <br />
            <table>
                <caption>Фильмы</caption>
                <tr>
                    <th>Id</th>
                    <th>Название</th>
                    <th>Год выпуска</th>
                    <th>Жанр</th>
                    <th>Обслужено</th>
                </tr>
                <%if (movies.size()==0) {%>
                    <tr><td colspan="10">Нет фильмов соответствующих параметрам</td></tr>
                <%}%>
                <%for(MovieDTO movie : movies) {%>
                    <tr>
                        <td><%=movie.getId()%></td>
                        <td><%=movie.getTitle()%></td>
                        <td><%=movie.getYear()%></td>
                        <td><%=movie.getGenreName()%></td>
                        <td>true</td>
                    </tr>
                <%}%>
            </table>
        </div>
    </div>
</body>
</html>
