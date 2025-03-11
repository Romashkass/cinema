<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
   <meta charset="UTF-8">
   <title>viewMoviesInfoJSP</title>
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
        Movie movie = (Movie) request.getAttribute("movie");
        List<Rent> rents = (List<Rent>) request.getAttribute("rents");
        Genre genre = (Genre) request.getAttribute("genre");
    %>

    <div class="center flex full-vh">
        <div class="vertical-center">
            <a class="ml-20" href="/">На главную</a>
            <a class="ml-20" href="/viewMovies">Назад</a>
            <br />
            <br />
            <hr />
            <br />
            <table>
                <caption>Фильм</caption>
                <tr>
                    <th>Id</th>
                    <th>Название</th>
                    <th>Год выпуска</th>
                    <th>Жанр</th>
                </tr>
                <%if (movie==null) {%>
                    <tr><td colspan="10">Нет фильмов соответствующих параметрам</td></tr>
                <%}%>
                <tr>
                    <td><%=movie.getId()%></td>
                    <td><%=movie.getTitle()%></td>
                    <td><%=movie.getYear()%></td>
                    <td><%=genre.getName()%></td>
                </tr>
            </table>
            <br />
            <hr />
            <br />
            <table>
                <caption>Аренды</caption>
                <tr>
                    <th>Id</th>
                    <th>Фильм</th>
                    <th>Дата</th>
                    <th>Стоимость</th>
                </tr>
                <%if (rents.size()==0) {%>
                    <tr><td colspan="10">Этот фильм не арендовали</td></tr>
                <%}%>
                <%for(Rent rent : rents) {%>
                    <tr>
                        <td><%=rent.getId()%></td>
                        <td><%=movie.getTitle()%></td>
                        <td><%=rent.getDate()%></td>
                        <td><%=rent.getPrice()%></td>
                    </tr>
                <%}%>
            </table>
            <br />
            <hr />
            <br />
            <div>
                Сумма: <%=rents.stream().mapToDouble(Rent::getPrice).sum()%>
            </div>
        </div>
    </div>
</body>
</html>
