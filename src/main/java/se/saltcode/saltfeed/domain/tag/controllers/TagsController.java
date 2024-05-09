package se.saltcode.saltfeed.domain.tag.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.saltcode.saltfeed.domain.tag.dtos.TagsResponse;
import se.saltcode.saltfeed.domain.article.mappers.ArticleMapper;
import se.saltcode.saltfeed.domain.tag.services.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    private final TagService tagService;

    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public TagsResponse getAllTags() {
        return ArticleMapper.mapToTagsResponse(tagService.getAllTags());
    }
}
