package se.saltcode.saltfeed.domain.tag.mappers;

import se.saltcode.saltfeed.domain.tag.dtos.TagsResponse;
import se.saltcode.saltfeed.domain.tag.models.Tag;

import java.util.List;

public class TagMapper {

    public static TagsResponse mapToTagsResponse(List<Tag> tags) {
        return new TagsResponse(tags.stream().map(Tag::getName).toList());
    }
}
