package se.saltcode.saltfeed.domain.tag.dtos;

import java.util.List;

public record TagsResponse(
        List<String> tags
) {}
