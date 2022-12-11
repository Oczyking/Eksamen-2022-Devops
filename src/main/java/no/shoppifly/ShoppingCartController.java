package no.shoppifly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@RestController()
public class ShoppingCartController {
    
    private Map<Cart, Item> cartValue = new HashMap();
    
    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private final CartService cartService;

    public ShoppingCartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(path = "/cart/{id}")
    public Cart getCart(@PathVariable String id) {
        return cartService.getCart(id);
    }

    /**
     * Checks out a shopping cart. Removes the cart, and returns an order ID
     *
     * @return an order ID
     */
    @Timed
    @PostMapping(path = "/cart/checkout")
    public String checkout(@RequestBody Cart cart) {
        meterRegistry.counter("checkouts").increment();
        meterRegistry.summary("checkout_latency", checkout(cart)).record(1);
        return cartService.checkout(cart);
    }

    /**
     * Updates a shopping cart, replacing it's contents if it already exists. If no cart exists (id is null)
     * a new cart is created.
     *
     * @return the updated cart
     */
    @Timed(value = "carts")
    @PostMapping(path = "/cart", value = "/cart")
    public Cart updateCart(@RequestBody Cart cart) {
        meterRegistry.counter("carts").increment();
        
        for (Item item : cart.getItems()) {
        cartValue.put(cart, item);
    }
        
        Gauge.builder("cartsvalue", cartValue, 
                        b -> b.values()
                                .stream()
                                .map(Item::getUnitPrice)
                                .mapToDouble(Float::floatValue)
                                .sum())
                .register(meterRegistry);
                
        return cartService.update(cart);
    }

    /**
     * return all cart IDs
     *
     * @return
     */
    @GetMapping(path = "/carts")
    public List<String> getAllCarts() {
        return cartService.getAllsCarts();
    }


}