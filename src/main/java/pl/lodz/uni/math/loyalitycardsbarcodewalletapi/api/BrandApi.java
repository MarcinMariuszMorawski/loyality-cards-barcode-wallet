package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.Brand;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager.BrandManager;

@RestController
@RequestMapping("/api/brand")
public final class BrandApi {
    private BrandManager brandManager;

    @Autowired
    public BrandApi(BrandManager brandManager) {
        this.brandManager = brandManager;
    }

    @GetMapping("/all")
    public Iterable<Brand> getAll() {
        return brandManager.findAll();
    }
}