package com.StackBros.StackBros_Backend.database_handling;

import com.StackBros.StackBros_Backend.model.Category;
import com.StackBros.StackBros_Backend.model.MenuItem;
import com.StackBros.StackBros_Backend.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return; // already seeded
        }

        createCategories();
    }


    public void createCategories() {
        Category burgerCat = new Category("burger", "Burger", "Burgers", "Burger", "Burgers", 1);
        burgerCat.setNoteDe("Hausgemachte Burger frisch zubereitet.");
        burgerCat.setNoteEn("Homemade burgers freshly prepared.");
        categoryRepository.save(burgerCat);
        burgerCategory(burgerCat);

        Category saladCat = new Category("salat", "Salat", "Salads", "Salate", "Salads", 2);
        saladCat.setNoteDe("Frische und knackige Salate.");
        saladCat.setNoteEn("Fresh and crisp salads.");
        categoryRepository.save(saladCat);
        saladCategory(saladCat);

        Category croqueCat = new Category("croque", "Croque", "Croques", "Croques", "Croques", 3);
        croqueCat.setNoteDe("Mittagsangebot: Jeder Croque + Getränk nur 10,99 €");
        croqueCat.setNoteEn("Lunch Offer: Any Croque + Drink for only €10.99");
        categoryRepository.save(croqueCat);
        croqueCategory(croqueCat);

        Category crepeCat = new Category("crepe", "Crêpe", "Crêpes", "Crêpes", "Crêpes", 4);
        crepeCat.setNoteDe("Süße Crêpes zum Genießen.");
        crepeCat.setNoteEn("Sweet crêpes to enjoy.");
        categoryRepository.save(crepeCat);
        crepeCategory(crepeCat);

    }
    private void burgerCategory(Category category) {

        MenuItem item1 = new MenuItem(category, "Stack Bros Spezial", "Stack Bros Special", 1);
        item1.setPrice(BigDecimal.valueOf(21.99));
        //item1.setImageUrl("../../images/burger.JPG");
        category.addMenuItem(item1);

        MenuItem item2 = new MenuItem(category, "Cheeseburger", "Cheeseburger", 2);
        item2.setPrice(BigDecimal.valueOf(15.00));
        //item2.setImageUrl("../../images/burger.JPG");
        category.addMenuItem(item2);

        MenuItem item3 = new MenuItem(category, "Mushroom Burger", "Mushroom Burger", 3);
        item3.setPrice(BigDecimal.valueOf(16.00));
        //item3.setImageUrl("../../images/burger.JPG");
        category.addMenuItem(item3);

        MenuItem item4 = new MenuItem(category, "Bacon Burger", "Bacon Burger", 4);
        item4.setPrice(BigDecimal.valueOf(16.00));
        //item4.setImageUrl("../../images/burger.JPG");
        category.addMenuItem(item4);

        MenuItem item5 = new MenuItem(category, "Chicken Burger", "Chicken Burger", 5);
        item5.setPrice(BigDecimal.valueOf(15.00));
        //item5.setImageUrl("../../images/burger.JPG");
        category.addMenuItem(item5);

        categoryRepository.save(category);
    }

    private void saladCategory(Category category) {

        MenuItem item1 = new MenuItem(category, "Caesar Salat mit Hähnchen", "Caesar Salad with Chicken", 1);
        item1.setPrice(BigDecimal.valueOf(15.90));
        //item1.setImageUrl("../../images/CaesarSalat.JPG");
        category.addMenuItem(item1);

        MenuItem item2 = new MenuItem(category, "Thunfisch Salat", "Tuna Salad", 2);
        item2.setPrice(BigDecimal.valueOf(12.90));
        //item2.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item2);

        MenuItem item3 = new MenuItem(category, "Ziegenkäse Salat", "Goat Cheese Salad", 3);
        item3.setPrice(BigDecimal.valueOf(11.50));
        //item3.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item3);

        MenuItem item4 = new MenuItem(category, "Schafskäse Salat", "Feta Cheese Salad", 4);
        item4.setPrice(BigDecimal.valueOf(11.50));
        //item4.setImageUrl("../../images/Schafskäse.JPG");
        category.addMenuItem(item4);

        MenuItem item5 = new MenuItem(category, "Chef Salat", "Chef Salad", 5);
        item5.setPrice(BigDecimal.valueOf(15.90));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item5);

        categoryRepository.save(category);
    }

    private void croqueCategory(Category category) {

        MenuItem item1 = new MenuItem(category, "Hawaii", "Hawaiian", 1);
        item1.setPrice(BigDecimal.valueOf(8.00));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item1);

        MenuItem item2 = new MenuItem(category, "Schinken Tomaten", "Ham & Tomato", 2);
        item1.setPrice(BigDecimal.valueOf(8.00));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item2);

        MenuItem item3 = new MenuItem(category, "Pute Tomaten", "Turkey & Tomato", 3);
        item1.setPrice(BigDecimal.valueOf(8.50));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item3);

        MenuItem item4 = new MenuItem(category, "Pute Ananas", "Turkey & Pineapple", 4);
        item1.setPrice(BigDecimal.valueOf(8.00));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item4);

        MenuItem item5 = new MenuItem(category, "Pute Bacon", "Turkey Bacon", 5);
        item1.setPrice(BigDecimal.valueOf(8.00));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item5);

        MenuItem item6 = new MenuItem(category, "Salami", "Salami", 6);
        item1.setPrice(BigDecimal.valueOf(8.00));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item6);

        MenuItem item7 = new MenuItem(category, "Thunfisch", "Tuna", 7);
        item1.setPrice(BigDecimal.valueOf(8.50));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item7);

        MenuItem item8 = new MenuItem(category, "Crispy Chicken", "Crispy Chicken", 8);
        item1.setPrice(BigDecimal.valueOf(9.00));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item8);

        MenuItem item9 = new MenuItem(category, "Schafskäse", "Feta Cheese", 9);
        item1.setPrice(BigDecimal.valueOf(8.50));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item9);

        MenuItem item10 = new MenuItem(category, "Ziegenkäse", "Goat Cheese", 10);
        item1.setPrice(BigDecimal.valueOf(8.50));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item10);

        MenuItem item11 = new MenuItem(category, "Mozzarella", "Mozzarella", 11);
        item1.setPrice(BigDecimal.valueOf(8.50));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item11);

        MenuItem item12 = new MenuItem(category, "Camembert", "Camembert", 12);
        item1.setPrice(BigDecimal.valueOf(8.50));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item12);

        MenuItem item13 = new MenuItem(category, "Frikadelle", "Meatball", 13);
        item1.setPrice(BigDecimal.valueOf(8.00));
        //item5.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item13);

        categoryRepository.save(category);

    }

    private void crepeCategory(Category category) {

        MenuItem item1 = new MenuItem(category, "Nutella", "Nutella", 1);
        item1.setPrice(BigDecimal.valueOf(5.50));
        //item1.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item1);

        MenuItem item2 = new MenuItem(category, "Nutella-Banane", "Nutella & Banana", 2);
        item2.setPrice(BigDecimal.valueOf(6.50));
        //item2.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item2);

        MenuItem item3 = new MenuItem(category, "Apfelmus", "Applesauce", 3);
        item3.setPrice(BigDecimal.valueOf(5.00));
        //item3.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item3);

        MenuItem item4 = new MenuItem(category, "Zimt-Zucker", "Cinnamon & Sugar", 4);
        item4.setPrice(BigDecimal.valueOf(4.50));
        //item4.setImageUrl("path/to/your/image.jpg");
        category.addMenuItem(item4);

        categoryRepository.save(category);

    }
}