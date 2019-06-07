package pl.lodz.uni.math.loyalitycardsbarcodewalletapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.filters.JwtResourcesFilter;

@SpringBootApplication
public class LoyalityCardsBarcodeWalletApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(LoyalityCardsBarcodeWalletApiApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtResourcesFilter());
        filterRegistrationBean.addUrlPatterns(
                "/api/card",
                "/api/card/*",
                "/api/brand",
                "/api/brand/*",
                "/api/user/changepassword");
        return filterRegistrationBean;
    }


}
