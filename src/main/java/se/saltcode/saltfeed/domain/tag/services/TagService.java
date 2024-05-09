package se.saltcode.saltfeed.domain.tag.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.saltcode.saltfeed.domain.tag.models.Tag;
import se.saltcode.saltfeed.domain.tag.repos.TagRepository;

import java.util.List;
import java.util.Locale;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) { this.tagRepository = tagRepository; }

    public Tag getOrCreateTag(String tagName) {
        String cleanedTagName = tagName.strip().toLowerCase(Locale.ROOT);

        return tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(cleanedTagName)));
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
