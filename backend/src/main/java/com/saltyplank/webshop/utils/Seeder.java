package com.saltyplank.webshop.utils;

import com.saltyplank.webshop.enums.Role;
import com.saltyplank.webshop.models.Category;
import com.saltyplank.webshop.models.Gebruiker;
import com.saltyplank.webshop.models.Product;
import com.saltyplank.webshop.repository.CategoryRepository;
import com.saltyplank.webshop.repository.GebruikerRepository;
import com.saltyplank.webshop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Seeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    GebruikerRepository gebruikerRepository;
    PasswordEncoder passwordEncoder;

    public Seeder(CategoryRepository categoryRepository, ProductRepository productRepository,
                  GebruikerRepository gebruikerRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.gebruikerRepository = gebruikerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) seedCategories();
        if (productRepository.count() == 0) seedProducts();
        if (gebruikerRepository.count() == 0) seedAdmin();
    }

    private void seedAdmin() {
        Gebruiker admin = new Gebruiker();
        admin.setFirstName("Admin");
        admin.setLastName("Salty Plank");
        admin.setEmail("admin@saltyplank.nl");
        admin.setPassword(passwordEncoder.encode("Admin1234!"));
        admin.setRole(Role.ADMIN);
        gebruikerRepository.save(admin);
        System.out.println("Admin seeded");
    }

    private void seedCategories() {
        categoryRepository.save(new Category("Shortboard"));
        categoryRepository.save(new Category("Longboard"));
        categoryRepository.save(new Category("Fish"));
        categoryRepository.save(new Category("Funboard"));
        System.out.println("Categories seeded");
    }

    private void seedProducts() {
        Category shortboard = categoryRepository.findByName("Shortboard").orElseThrow();
        Category longboard = categoryRepository.findByName("Longboard").orElseThrow();
        Category fish = categoryRepository.findByName("Fish").orElseThrow();
        Category funboard = categoryRepository.findByName("Funboard").orElseThrow();

        // Shortboards
        productRepository.save(new Product("Lost Puddle Jumper",
                "Perfect small wave shortboard for punchy beach breaks",
                549.99, 10, shortboard, "shortboard.jpg"));

        productRepository.save(new Product("Channel Islands Happy",
                "High performance shortboard for powerful waves",
                649.99, 8, shortboard, "shortboard2.jpg"));

        productRepository.save(new Product("Firewire Seaside",
                "Versatile shortboard that works in all conditions",
                599.99, 12, shortboard, "shortboard3.jpg"));

        productRepository.save(new Product("DHD Switch Blade",
                "Aggressive shortboard for experienced surfers",
                679.99, 6, shortboard, "shortboard4.jpg"));

        productRepository.save(new Product("JS Industries Monsta",
                "High volume shortboard great for all conditions",
                619.99, 8, shortboard, "shortboard5.jpg"));

        // Longboards
        productRepository.save(new Product("Torq Longboard Classic",
                "Traditional longboard perfect for cruising",
                749.99, 6, longboard, "longboard1.jpg"));

        productRepository.save(new Product("NSP Elements Longboard",
                "Durable longboard great for beginners and intermediates",
                699.99, 8, longboard, "longboard2.jpg"));

        productRepository.save(new Product("Firewire Domingo",
                "Modern longboard with great glide and trim",
                799.99, 5, longboard, "longboard3.jpg"));

        productRepository.save(new Product("CI Log",
                "Classic noserider longboard for style surfers",
                849.99, 4, longboard, "longboard4.jpg"));

        // Fish
        productRepository.save(new Product("Firewire Seaside Fish",
                "Retro fish shape great for small mushy waves",
                579.99, 7, fish, "fish1.jpg"));

        productRepository.save(new Product("DHD Mini Twin",
                "Modern twin fin fish with great speed and flow",
                629.99, 5, fish, "fish2.jpg"));

        productRepository.save(new Product("Lost Crowd Killer",
                "High performance fish for punchy beach breaks",
                599.99, 6, fish, "fish3.jpg"));

        // Funboards (softtops)
        productRepository.save(new Product("NSP Softtop Funboard",
                "Easy to ride softtop perfect for beginners",
                399.99, 10, funboard, "softtop1.jpg"));

        productRepository.save(new Product("Torq Softtop",
                "Stable and forgiving softtop for all levels",
                429.99, 9, funboard, "softtop2.jpg"));

        productRepository.save(new Product("Catch Surf Odysea",
                "Fun and playful softtop for all conditions",
                449.99, 8, funboard, "softtop3.jpg"));

        productRepository.save(new Product("Softech Flash",
                "Versatile softtop great for small waves",
                379.99, 12, funboard, "softtop4.jpg"));

        System.out.println("Products seeded");
    }
}
