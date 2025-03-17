<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
   <meta charset="UTF-8">
   <title>viewReportJSP</title>
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
    <%@ page import="org.example.cinema.entity.dto.RentDTO"%>

    <%
        List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
        List<RentDTO> rents = (List<RentDTO>) request.getAttribute("rents");
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
                    <th>Доход</th>
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
                        <td><%=rents.stream().filter(rent -> rent.getMovieId().equals(movie.getId())).mapToDouble(RentDTO::getPrice).sum()%></td>
                    </tr>
                <%}%>
            </table>
            <br />
            <hr />
            <br />
            <div>
                Сумма: <%=rents.stream().mapToDouble(RentDTO::getPrice).sum()%>
            </div>
        </div>
    </div>
</body>
</html>
