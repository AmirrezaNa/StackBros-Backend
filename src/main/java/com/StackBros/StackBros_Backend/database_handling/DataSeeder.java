package com.StackBros.StackBros_Backend.database_handling;

import com.StackBros.StackBros_Backend.model.Category;
import com.StackBros.StackBros_Backend.model.MenuItem;
import com.StackBros.StackBros_Backend.repository.MenuCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MenuCategoryRepository categoryRepository;

    public DataSeeder(MenuCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return; // already seeded
        }

        categoryRepository.save(burgerCategory());
        categoryRepository.save(saladCategory());
        categoryRepository.save(croqueCategory());
        categoryRepository.save(crepeCategory());
    }

    private Category burgerCategory() {
        Category category = new Category();
        category.setKey("burger");
        category.setEyebrowDe("Burger");
        category.setEyebrowEn("Burgers");
        category.setTitleDe("Burger");
        category.setTitleEn("Burgers");
        category.setNoteDe("Hausgemachte Burger frisch zubereitet.");
        category.setNoteEn("Homemade burgers freshly prepared.");
        category.setSortOrder(1);

        addItem(category, "Stack Bros Spezial", "Stack Bros Special", "../../images/burger.JPG", 1);
        addItem(category, "Cheeseburger", "Cheeseburger", "../../images/burger.JPG", 2);
        addItem(category, "Mushroom Burger", "Mushroom Burger", "../../images/burger.JPG", 3);
        addItem(category, "Bacon Burger", "Bacon Burger", "../../images/burger.JPG", 4);
        addItem(category, "Chicken Burger", "Chicken Burger", "../../images/burger.JPG", 5);

        return category;
    }

    private Category saladCategory() {
        Category category = new Category();
        category.setKey("salat");
        category.setEyebrowDe("Salat");
        category.setEyebrowEn("Salads");
        category.setTitleDe("Salate");
        category.setTitleEn("Salads");
        category.setNoteDe("Frische und knackige Salate.");
        category.setNoteEn("Fresh and crisp salads.");
        category.setSortOrder(2);

        addItem(category, "Caesar Salat mit Hähnchen", "Caesar Salad with Chicken", "../../images/CaesarSalat.JPG", 1);
        addItem(category, "Thunfisch Salat", "Tuna Salad", "path/to/your/image.jpg", 2);
        addItem(category, "Ziegenkäse Salat", "Goat Cheese Salad", "path/to/your/image.jpg", 3);
        addItem(category, "Schafskäse Salat", "Feta Cheese Salad", "../../images/Schafskäse.JPG", 4);
        addItem(category, "Chef Salat", "Chef Salad", "path/to/your/image.jpg", 5);

        return category;
    }

    private Category croqueCategory() {
        Category category = new Category();
        category.setKey("croque");
        category.setEyebrowDe("Croque");
        category.setEyebrowEn("Croques");
        category.setTitleDe("Croques");
        category.setTitleEn("Croques");
        category.setNoteDe("Mittagsangebot: Jeder Croque + Getränk nur 10,99 €");
        category.setNoteEn("Lunch Offer: Any Croque + Drink for only €10.99");
        category.setSortOrder(3);

        addItem(category, "Hawaii", "Hawaiian", "../../images/Hawaii.JPG", 1);
        addItem(category, "Schinken Tomaten", "Ham & Tomato", "../../images/Schinken.JPG", 2);
        addItem(category, "Pute Tomaten", "Turkey & Tomato", "../../images/PuteTomaten.JPG", 3);
        addItem(category, "Pute Ananas", "Turkey & Pineapple", "../../images/PuteAnanas.JPG", 4);
        addItem(category, "Pute Bacon", "Turkey Bacon", "../../images/PuteBacon.JPG", 5);
        addItem(category, "Salami", "Salami", "../../images/Salami.JPG", 6);
        addItem(category, "Thunfisch", "Tuna", "../../images/Thunfisch.JPG", 7);
        addItem(category, "Crispy Chicken", "Crispy Chicken", "../../images/CrispyChicken.JPG", 8);
        addItem(category, "Schafskäse", "Feta Cheese", "../../images/Schafskäse.JPG", 9);
        addItem(category, "Ziegenkäse", "Goat Cheese", "../../images/Ziegenkäse.JPG", 10);
        addItem(category, "Mozzarella", "Mozzarella", "../../images/Mozzarella.JPG", 11);
        addItem(category, "Camembert", "Camembert", "../../images/Camembert.JPG", 12);
        addItem(category, "Frikadelle", "Meatball", "../../images/Frikadelle.JPG", 13);

        return category;
    }

    private Category crepeCategory() {
        Category category = new Category();
        category.setKey("crepe");
        category.setEyebrowDe("Crêpe");
        category.setEyebrowEn("Crêpes");
        category.setTitleDe("Crêpes");
        category.setTitleEn("Crêpes");
        category.setNoteDe("Süße Crêpes zum Genießen.");
        category.setNoteEn("Sweet crêpes to enjoy.");
        category.setSortOrder(4);

        addItem(category, "Nutella", "Nutella", "path/to/your/image.jpg", 1);
        addItem(category, "Nutella-Banane", "Nutella & Banana", "path/to/your/image.jpg", 2);
        addItem(category, "Apfelmus", "Applesauce", "path/to/your/image.jpg", 3);
        addItem(category, "Zimt-Zucker", "Cinnamon & Sugar", "path/to/your/image.jpg", 4);

        return category;
    }

    private void addItem(Category category, String nameDe, String nameEn, String imageUrl, int sortOrder) {
        MenuItem item = new MenuItem();
        item.setCategory(category);
        item.setNameDe(nameDe);
        item.setNameEn(nameEn);
        item.setDescDe("");
        item.setDescEn("");
        item.setPrice(BigDecimal.ZERO); // TODO: replace with real price
        item.setImageUrl(imageUrl);
        item.setSortOrder(sortOrder);
        category.getItems().add(item);
    }
}