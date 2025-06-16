# java-filmorate

# ER-диаграмма
![ER-диаграмма](dbdiagram.png)

# Таблица films (все фильмы)
``SELECT * FROM films;``

# Таблица users (все пользователи)
``SELECT * FROM users;``

# Таблица genres (справочник жанров)
``SELECT * FROM genres;``

# Таблица mpa_ratings (справочник рейтингов)
``SELECT * FROM mpa_ratings;``

# Таблица film_genres (все жанры по фильмам)
Все жанры фильма: ``SELECT * FROM film_genres WHERE film_id = %d;``

# Таблица film_likes (все лайки пользователей по фильмам)
Кол-во лайков у фильма: ``SELECT COUNT(user_id) FROM film_likes WHERE film_id = %d;``

# Таблица user_friends (все друзья пользователей)
Все друзья пользователя: ``SELECT * FROM user_friends WHERE user_id = %d OR friend_id = %d;``
