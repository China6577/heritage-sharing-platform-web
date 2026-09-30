package com.example.heritage_sharing_api.config;

import com.example.heritage_sharing_api.entity.Category;
import com.example.heritage_sharing_api.entity.ContributorRequestStatus;
import com.example.heritage_sharing_api.entity.Tag;
import com.example.heritage_sharing_api.entity.User;
import com.example.heritage_sharing_api.entity.UserRole;
import com.example.heritage_sharing_api.repository.CategoryRepository;
import com.example.heritage_sharing_api.repository.TagRepository;
import com.example.heritage_sharing_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing database with default data...");

        seedUsersIfEmpty();
        seedCategoriesIfEmpty();
        seedTagsIfEmpty();

        System.out.println("\n========================================");
        System.out.println("Database initialization completed!");
        System.out.println("========================================\n");
    }

    void seedUsersIfEmpty() {
        seedUserIfMissing("user", "test@user.com", "12345678", "Test user for heritage sharing", UserRole.USER, ContributorRequestStatus.NONE);
        seedUserIfMissing("contributor", "test@contributor.com", "12345678", "Test contributor for heritage sharing", UserRole.CONTRIBUTOR, ContributorRequestStatus.APPROVED);
        seedUserIfMissing("admin1", "admin1@admin.com", "admin1", "Administrator user", UserRole.ADMIN, ContributorRequestStatus.NONE);
        seedUserIfMissing("admin2", "admin2@admin.com", "admin2", "Administrator user", UserRole.ADMIN, ContributorRequestStatus.NONE);
        seedUserIfMissing("admin3", "admin3@admin.com", "admin3", "Administrator user", UserRole.ADMIN, ContributorRequestStatus.NONE);
        System.out.println("Default users initialized successfully");
    }

    private void seedUserIfMissing(String username, String email, String rawPassword, String personalDescription,
                                   UserRole role, ContributorRequestStatus roleRequestStatus) {
        if (userRepository.findByEmail(email).isEmpty()) {
            userRepository.save(buildUser(username, email, rawPassword, personalDescription, role, roleRequestStatus));
        }
    }

    void seedCategoriesIfEmpty() {
        if (categoryRepository.count() > 0) {
            System.out.println("Categories already initialized, skipping category initialization");
            return;
        }

        categoryRepository.save(new Category(null, "World Cultural Heritage"));
        categoryRepository.save(new Category(null, "Intangible Cultural Heritage"));
        categoryRepository.save(new Category(null, "Natural Heritage"));
        categoryRepository.save(new Category(null, "Archaeological Sites"));
        categoryRepository.save(new Category(null, "Religious Buildings"));
        categoryRepository.save(new Category(null, "Historic Cities"));
        System.out.println("Categories initialized successfully (6 categories)");
    }

    void seedTagsIfEmpty() {
        if (tagRepository.count() > 0) {
            System.out.println("Tags already initialized, skipping tag initialization");
            return;
        }

        tagRepository.save(new Tag(null, "Ancient Architecture"));
        tagRepository.save(new Tag(null, "Traditional Festival"));
        tagRepository.save(new Tag(null, "Religious Art"));
        tagRepository.save(new Tag(null, "Folk Crafts"));
        tagRepository.save(new Tag(null, "Historical Artifacts"));
        tagRepository.save(new Tag(null, "Cultural Practice"));
        tagRepository.save(new Tag(null, "Natural Landscape"));
        tagRepository.save(new Tag(null, "Artistic Heritage"));
        tagRepository.save(new Tag(null, "Architectural Design"));
        tagRepository.save(new Tag(null, "Traditional Medicine"));
        System.out.println("Tags initialized successfully (10 tags)");
    }

    private User buildUser(String username, String email, String rawPassword, String personalDescription, UserRole role, ContributorRequestStatus roleRequestStatus) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setPersonalDescription(personalDescription);
        user.setRole(role);
        user.setRoleRequestStatus(roleRequestStatus);
        return user;
    }
}
