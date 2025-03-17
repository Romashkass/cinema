<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Просмотр фильмов</title>
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

        Set<String> uniqueGenres = movies.stream().map(MovieDTO::getGenreName).collect(Collectors.toSet());
        Set<String> uniqueTitles = movies.stream().map(MovieDTO::getTitle).collect(Collectors.toSet());
        Set<String> uniqueYears = movies.stream().map(movie -> String.valueOf(movie.getYear())).collect(Collectors.toSet());

        AtomicReference<Predicate<MovieDTO>> filter = new AtomicReference<>(movie -> true);

        Optional.ofNullable(request.getParameter("genre")).filter(s -> !s.isEmpty()).ifPresent(s -> {
           filter.set(filter.get().and(movie -> movie.getGenreName().equals(s)));
        });

        Optional.ofNullable(request.getParameter("title")).filter(s -> !s.isEmpty()).ifPresent(s -> {
           filter.set(filter.get().and(movie -> movie.getTitle().equals(s)));
        });

        Optional.ofNullable(request.getParameter("year")).filter(s -> !s.isEmpty()).ifPresent(s -> {
           filter.set(filter.get().and(movie -> movie.getYear().equals(Integer.parseInt(s))));
        });

        movies = movies.stream().filter(filter.get()).collect(Collectors.toList());
    %>

    <div class="center flex full-vh">
        <div class="vertical-center">
            <a class="ml-20" href="/">На главную</a>
            <a class="ml-20" href="/viewMovies">Очистить фильтры</a>
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
                    <th>&#x1F4CB;</th>
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
                        <td><a href="<%="/info?id=" + movie.getId()%>">&#x1F4C3;</a></td>
                    </tr>
                <%}%>
            </table>
            <br />
            <div>
                <hr />
                <br />
                <form method="get" action="/viewMovies" class="flex">
                    <div>
                        <p>Жанр</p>
                        <select name="genre" >
                            <option value="" <%=request.getParameter("genre")==null?"selected":""%>>Не выбрано</option>
                            <%for (String s : uniqueGenres) {%>
                                <option value="<%=s%>" <%=(request.getParameter("genre")!=null && s.equals(request.getParameter("genre"))?"selected":"")%>><%=s%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="ml-20">
                        <p>Название</p>
                        <select name="title" >
                            <option value="" <%=request.getParameter("title")==null?"selected":""%>>Не выбрано</option>
                            <%for (String s : uniqueTitles) {%>
                                <option value="<%=s%>" <%=(request.getParameter("title")!=null && s.equals(request.getParameter("title"))?"selected":"")%>><%=s%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="ml-20">
                        <p>Год выпуска</p>
                        <select name="year" >
                            <option value="" <%=request.getParameter("year")==null?"selected":""%>>Не выбрано</option>
                            <%for (String s : uniqueYears) {%>
                                <option value="<%=s%>" <%=(request.getParameter("year")!=null && s.equals(request.getParameter("year"))?"selected":"")%>><%=s%></option>
                            <%}%>
                        </select>
                    </div>

                    <button class="ml-20" type="submit">Выбрать</button>
                </form>
                <br />
                <hr />
            </div>
        </div>
    </div>
</body>
</html>