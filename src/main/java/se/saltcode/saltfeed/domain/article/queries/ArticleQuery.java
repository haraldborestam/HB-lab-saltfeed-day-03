package se.saltcode.saltfeed.domain.article.queries;

public record ArticleQuery(

        Integer offset,
        Integer limit,
        String author,
        String favorited,
        String tag
) {}
