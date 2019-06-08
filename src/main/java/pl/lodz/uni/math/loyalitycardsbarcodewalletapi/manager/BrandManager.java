package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.BrandRepo;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.Brand;

import java.util.Optional;

@Service
public final class BrandManager {
    private BrandRepo brandRepo;

    @Autowired
    public BrandManager(BrandRepo brandRepo) {
        this.brandRepo = brandRepo;
    }

    public Optional<Brand> findById(Long id) {
        return brandRepo.findById(id);
    }

    public Iterable<Brand> findAll() {
        return brandRepo.findAll();
    }

    public Brand save(Brand brand) {
        return brandRepo.save(brand);
    }

    public void deleteById(Long id) {
        brandRepo.deleteById(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fill() {
        save(new Brand("Biedronka", "#123563"));
        save(new Brand("Tesco", "#421356"));
        save(new Brand("Auchan", "#421356"));
        save(new Brand("Stokrotka", "#421356"));
        save(new Brand("Dino", "#421356"));
        save(new Brand("Vitay", "#421356"));
        save(new Brand("Payback", "#421356"));
        save(new Brand("Empik", "#421356"));
        save(new Brand("CircleK", "#421356"));
        save(new Brand("Zabka", "#421356"));
        save(new Brand("Mcdonalds", "#421356"));
        save(new Brand("Kfc", "#421356"));
        save(new Brand("Tokyo", "#421356"));
        save(new Brand("Smyk", "#421356"));
        save(new Brand("Deichman", "#421356"));
        save(new Brand("CCC", "#421356"));
        save(new Brand("Cropp", "#421356"));
        save(new Brand("House", "#421356"));
        save(new Brand("H&M", "#421356"));
        save(new Brand("Sinsay", "#421356"));
        save(new Brand("Rossmann", "#421356"));
        save(new Brand("Kasztan", "#421356"));
    }


}