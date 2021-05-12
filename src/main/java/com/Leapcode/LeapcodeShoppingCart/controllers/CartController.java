package com.Leapcode.LeapcodeShoppingCart.controllers;

import com.Leapcode.LeapcodeShoppingCart.models.Cart;
import com.Leapcode.LeapcodeShoppingCart.models.ProductRepository;
import com.Leapcode.LeapcodeShoppingCart.models.data.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/add/{id}")
    public String add(@PathVariable int id,
                      Model model,
                      HttpSession session,
                      @RequestParam(value="cartPage", required = false) String cartPage) {
        Product product = productRepo.getOne(id);

        if (session.getAttribute("cart") == null) {
            Map<Integer, Cart> cart = new HashMap<>();
            cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
            session.setAttribute("cart", cart);
        } else {
            Map<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
            if(cart.containsKey(id)) {
                int quantity = cart.get(id).getQuantity();
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), ++quantity, product.getImage()));
            } else {
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
                session.setAttribute("cart", cart);
            }
        }

        Map<Integer, Cart> cart = (HashMap)session.getAttribute("cart");
        int size = 0;
        double totalPrice = 0;
        for(Cart value: cart.values()) {
            size += value.getQuantity();
            totalPrice += (Double.parseDouble(value.getPrice())) * value.getQuantity();
        }
        model.addAttribute("size", size);
        model.addAttribute("totalPrice", totalPrice);

        if(cartPage != null) {
            return "redirect:/cart/view";
        }

        return "cart_view";
    }

    @GetMapping("/view")
    public String view(HttpSession session, Model model) {
        if(session.getAttribute("cart") == null) {
            return "redirect:/";
        }

        Map<Integer, Cart> cart = (HashMap)session.getAttribute("cart");
        model.addAttribute("cart", cart);
        model.addAttribute("notCartViewPage", true);
        return "cart";
    }

    @GetMapping("/subtract/{id}")
    public String subtract(@PathVariable int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {
        Product product = productRepo.getOne(id);
        Map<Integer, Cart> cart = (HashMap)session.getAttribute("cart");

        int quantity = cart.get(id).getQuantity();

        if(quantity ==  1) {
            cart.remove(id);
            if(cart.size() == 0) {
                session.removeAttribute("cart");
            }
        } else {
            cart.put(id, new Cart(id, product.getName(), product.getPrice(), --quantity, product.getImage()));
        }

        String refererLink = httpServletRequest.getHeader("referer");
        return "redirect:" + refererLink;
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, HttpSession session, HttpServletRequest httpServletRequest) {

        Map<Integer, Cart> cart = (HashMap)session.getAttribute("cart");

        cart.remove(id);
        if(cart.size() == 0) session.removeAttribute("cart");

        String refererLink = httpServletRequest.getHeader("referer");
        return "redirect:" + refererLink;
    }

    @GetMapping("/clear")
    public String clear(HttpSession session, HttpServletRequest httpServletRequest) {

        session.removeAttribute("cart");

        String refererLink = httpServletRequest.getHeader("referer");
        return "redirect:" + refererLink;
    }
}
