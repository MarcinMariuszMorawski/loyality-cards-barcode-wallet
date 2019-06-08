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
        return brandRepo.findAllByOrderByName();
    }

    public Brand save(Brand brand) {
        return brandRepo.save(brand);
    }

    public void deleteById(Long id) {
        brandRepo.deleteById(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fill() {
        save(new Brand(1L, "Biedronka", "#e5bf16"));
        save(new Brand(2L, "Tesco", "#1f26ef"));
        save(new Brand(3L, "Auchan", "#d31d2f"));
        save(new Brand(4L, "Payback", "#2027db"));
        save(new Brand(5L, "Empik", "#38383a"));
        save(new Brand(6L, "Rossmann", "#ad0a15"));
        save(new Brand(7L, "Orlen", "#ef1726"));
        save(new Brand(8L, "ClrcleK", "#6b030a"));
    }
}