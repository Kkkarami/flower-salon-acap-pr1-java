package fedelesh.flowersalon.presentation.pages;

import fedelesh.flowersalon.domain.contract.BouquetService;
import fedelesh.flowersalon.domain.contract.FlowerService;
import fedelesh.flowersalon.domain.impl.Bouquet;
import fedelesh.flowersalon.domain.impl.Flower;
import java.util.Collection;

public class FlowersView {

    private final FlowerService flowerService;
    private final BouquetService bouquetService;

    public FlowersView(FlowerService flowerService, BouquetService bouquetService) {
        this.flowerService = flowerService;
        this.bouquetService = bouquetService;
    }

    public void render() {
        System.out.println("\n--- КАТАЛОГ ТОВАРІВ ---");
        System.out.println("\n[ КВІТИ ]");
        Collection<Flower> flowers = flowerService.getAll();

        if (flowers.isEmpty()) {
            System.out.println("Квіти відсутні на складі.");
        } else {
            System.out.printf("%-20s | %-10s | %-10s%n", "Назва", "Ціна", "Кількість");
            for (Flower f : flowers) {
                System.out.printf("%-20s | %-10.2f | %-10d%n", f.getName(), f.getPrice(),
                      f.getQuantity());
            }
        }

        System.out.println("\n[ ГОТОВІ БУКЕТИ ]");
        Collection<Bouquet> bouquets = bouquetService.getAll();

        if (bouquets.isEmpty()) {
            System.out.println("Готових букетів немає в каталозі.");
        } else {
            System.out.printf("%-20s | %-10s | %-30s%n", "Назва", "Ціна", "Опис");
            for (Bouquet b : bouquets) {
                System.out.printf("%-20s | %-10.2f | %-30s%n", b.getName(), b.getPrice(),
                      b.getDescription());
            }
        }
    }
}
