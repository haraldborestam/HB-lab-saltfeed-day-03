package se.saltcode.saltfeed.mocking;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.saltcode.saltfeed.domain.article.models.Article;
import se.saltcode.saltfeed.domain.comment.models.Comment;
import se.saltcode.saltfeed.domain.tag.models.Tag;
import se.saltcode.saltfeed.domain.article.repos.ArticleRepository;
import se.saltcode.saltfeed.domain.comment.repos.CommentRepository;
import se.saltcode.saltfeed.domain.tag.repos.TagRepository;
import se.saltcode.saltfeed.domain.user.models.User;
import se.saltcode.saltfeed.domain.user.repositories.UserRepository;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public final class DatabaseMocker implements CommandLineRunner {

    private final static Faker faker = new Faker();
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public DatabaseMocker(
            UserRepository userRepository,
            ArticleRepository articleRepository,
            TagRepository tagRepository,
            CommentRepository commentRepository
    ){
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        generateMockData();
    }

    public void generateMockData() {

        List<User> users = generateUsers(255);
        users.add(makeCustomUser());
        Set<Tag> tags = generateTags(255);
        haveUsersFollowRandomUsers(users, 50);
        List<Article> articles = generateArticles(users, tags, 1000, 25);
        haveUsersfavoriteRandomArticles(articles, users, 50);
        List<Comment> comments = generateComments(users, articles, 10000);

        tagRepository.saveAll(tags);
        userRepository.saveAll(users);
        articleRepository.saveAll(articles);
        commentRepository.saveAll(comments);
    }

    private User makeCustomUser() {
        var user = new User();
        user.getProfile().setUsername("adam");
        user.setEmail("adam@hello.com");
        user.getProfile().setBio(faker.shakespeare().asYouLikeItQuote());
        user.setPassword("{noop}1234");
        user.getProfile().setImage("https://loremflickr.com/320/320/cat");
        return user;
    }

    private static List<User> generateUsers(int totalUsers) {
        List<User> users = new ArrayList<>();

        for(int i = 0; i < totalUsers; ++i) {
            User newUser = new User();
            newUser.getProfile().setUsername(faker.name().username());
            newUser.setEmail(faker.internet().emailAddress());
            newUser.getProfile().setBio(faker.shakespeare().asYouLikeItQuote());
            newUser.setPassword("{noop}"+faker.internet().password());
            newUser.getProfile().setImage("https://loremflickr.com/320/320/cat");

            users.add(newUser);
        }

        return users;
    }

    private static void haveUsersFollowRandomUsers(List<User> users, int maxFollows) {
        for (int i = 0; i < users.size(); ++i) {
            int totalToFollow = ThreadLocalRandom.current().nextInt(0, maxFollows + 1);

            for (int j = 0; j <= totalToFollow; ++j) {
                int indexOfUserToFollow = ThreadLocalRandom.current().nextInt(0, users.size());
                users.get(indexOfUserToFollow).getProfile().addFollower(users.get(i));
            }

        }
    }

    private static void haveUsersfavoriteRandomArticles(List<Article> articles, List<User> users, int maxFollows) {
        for (int i = 0; i < users.size(); ++i) {
            int totalToFollow = ThreadLocalRandom.current().nextInt(0, maxFollows + 1);

            for (int j = 0; j <= totalToFollow; ++j) {
                int indexOfArticleToFollow = ThreadLocalRandom.current().nextInt(0, articles.size());
                articles.get(indexOfArticleToFollow).addUserToFavoritedBy(users.get(i));
            }

        }
    }

    private static Set<Tag> generateTags(int totalTags) {
        Set<Tag> tags = new HashSet<>();

        for(int i = 0; i < totalTags; ++i) {
            tags.add(new Tag(faker.superhero().power()));
        }

        return tags;
    }
    
    private static Set<Tag> getRandomTags(Set<Tag> tags, int maxTotal) {

        int max = Math.min(tags.size(), maxTotal);
        int randomTotalTags = ThreadLocalRandom.current().nextInt(0, max - 1);
        List<Tag> tagsList = new ArrayList<>(tags);
        Collections.shuffle(tagsList);
        return tagsList.stream().limit(randomTotalTags).collect(Collectors.toSet());
    }
    
    

    private static List<Article> generateArticles(List<User> users, Set<Tag> tags, int totalArticles, int maxTags) {
        List<Article> articles = new ArrayList<>();

        for(int i = 0; i < totalArticles; ++i) {

            int randomUserIndex = ThreadLocalRandom.current().nextInt(0, users.size());

            String title = faker.color().name() + " " +
                    faker.hobbit().character() + " " +
                    faker.random().nextInt(0,9999) + " " +
                    faker.artist().name() + " " +
                    faker.animal().name();
            String slug = title.replace(" ", "-").toLowerCase(Locale.ROOT).strip();


            Article newArticle= new Article();
            newArticle.setTitle(title);
            newArticle.setSlug(slug);
            newArticle.setDescription(faker.backToTheFuture().quote());
            newArticle.setBody(faker.lorem().paragraph(99));
            newArticle.setAuthor(users.get(randomUserIndex));
            newArticle.setTags(getRandomTags(tags, maxTags));

            articles.add(newArticle);
        }

        return articles;
    }

    private static List<Comment> generateComments(List<User> users, List<Article> articles, int totalComments) {
       List<Comment> comments = new ArrayList<>();
        for(int i = 0; i < totalComments; ++i) {
            int randomUserIndex = ThreadLocalRandom.current().nextInt(0, users.size());
            int randomArticleIndex = ThreadLocalRandom.current().nextInt(0, articles.size());

            Comment newComment = new Comment();
            newComment.setBody(faker.hitchhikersGuideToTheGalaxy().quote());
            newComment.setAuthor(users.get(randomUserIndex));
            newComment.setArticle(articles.get(randomArticleIndex));
            comments.add(newComment);
        }
        return comments;
    }
}
