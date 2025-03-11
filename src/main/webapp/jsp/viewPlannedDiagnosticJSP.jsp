<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
   <meta charset="UTF-8">
   <title>viewPlannedDiagnosticJSP</title>
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
    <%@ page import="org.example.cinema.entity.Genre"%>
    <%@ page import="org.example.cinema.entity.Movie"%>
    <%@ page import="org.example.cinema.entity.Rent"%>

    <%
        List<Movie> movies = (List<Movie>) request.getAttribute("movies");
        List<Genre> genres = (List<Genre>) request.getAttribute("genres");
    %>

    <div class="center flex full-vh">
        <div class="vertical-center">
            <a class="ml-20" href="/">На главную</a>
            <br />
            <br />
            <hr />
            <div>
                <div>Пеирод: <%="test"%></div>
                <div>Последняя: <%="test1"%></div>
            </div>
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
                <%for(Movie movie : movies) {%>
                    <tr>
                        <td><%=movie.getId()%></td>
                        <td><%=movie.getTitle()%></td>
                        <td><%=movie.getYear()%></td>
                        <td><%=genres.stream().filter(genre -> genre.getId().equals(movie.getGenreId())).findFirst().orElse(null).getName()%></td>
                        <td>true</td>
                    </tr>
                <%}%>
            </table>
        </div>
    </div>
</body>
</html>
