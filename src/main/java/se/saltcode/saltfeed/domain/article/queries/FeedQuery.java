package se.saltcode.saltfeed.domain.article.queries;

public record FeedQuery(

        Integer offset,
        Integer limit,
        String tag
) {}
