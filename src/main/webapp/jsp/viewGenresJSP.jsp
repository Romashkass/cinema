<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Просмотр жанров фильмов</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
    <%@ page import="java.util.List"%>
    <%@ page import="java.util.Comparator"%>
    <%@ page import="org.example.cinema.entity.dto.GenreDTO"%>

    <%
        String sortKey = null;
        String order = null;
        if (request.getParameter("name") != null) sortKey = "name";
        if (request.getParameter("asc") != null) order = "asc";
        if (request.getParameter("desc") != null) order = "desc";
    %>

    <div class="center flex full-vh">
        <div class="vertical-center">
            <a class="ml-20" href="/">На главную</a>
            <br />
            <br />
            <hr />
            <br />
            <%if (sortKey != null) {%>
                <%
                    String clearPath = "/viewTypes";
                    String ascPath = "?" + sortKey + "&asc";
                    String descPath = "?" + sortKey + "&desc";
                %>
                <div>
                    <a class="ml-20" href="<%=descPath%>">Сортировать по убыванию</a>
                    <a class="ml-20" href="<%=ascPath%>">Сортировать по возрастанию</a>
                    <a class="ml-20" href="<%=clearPath%>">Очистить параметры поиска</a>
                </div>
                <br />
                <hr />
                <br />
            <%}%>
            <table>
                <caption>Жанры фильмов</caption>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                </tr>
                <%
                    List<GenreDTO> genres = (List<GenreDTO>) request.getAttribute("genres");
                    Comparator<GenreDTO> comparator = null;
                    if (sortKey != null && sortKey.equals("name")) {
                        comparator = Comparator.comparing(genre -> genre.getName());
                    }
                    if (order != null && comparator != null && order.equals("desc") ) {
                        comparator = comparator.reversed();
                    }
                    if (comparator != null) {
                        genres.sort(comparator);
                    }
                %>
                <%for(GenreDTO genre : genres) {%>
                    <tr>
                        <td><%=genre.getId()%></td>
                        <td><%=genre.getName()%></td>
                    </tr>
                <%}%>
            </table>
            <br />
            <hr />
            <br />
            <div>
                <% if (request.getParameter("name") == null) {%><a class="ml-20" href="/viewTypes?name">Сортировать по названию</a><%}%>
            </div>
        </div>
    </div>
</body>
</html>