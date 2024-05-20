package edu.lngd.xtgl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.lngd.xtgl.collection.Product;
import edu.lngd.xtgl.collection.Recommend;
import edu.lngd.xtgl.collection.User;
import edu.lngd.xtgl.service.ActionService;
import edu.lngd.xtgl.service.ProductService;
import edu.lngd.xtgl.service.RecommendService;
import edu.lngd.xtgl.service.UserService;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping(value = "/recommend")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private ActionService actionService;


    @RequestMapping(value = "")
    public String recommend(Model model) {

        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);

        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);

        return "html";
    }

    @RequestMapping(value = "/rec")
    @ResponseBody
    public List<Product> getRecommendProductList(@PathParam(value = "userId") String userId) {
        List<Recommend> recommendList = recommendService.getRecommendList(userId);
        List<Product> recommendProductList = new ArrayList<>();
        recommendList.forEach(
            (e) -> recommendProductList.add(productService.getById(e.getProductId()))
        );
        return recommendProductList;
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public Object test() {
        return productService.test();
    }

    @RequestMapping(value = "/click")
    @ResponseBody
    public void clicked(@PathParam(value = "id") String id, @PathParam(value = "userId") String userId) {
        System.out.println(id + " has be clicked by " + userId);
        actionService.insert(userId, id, "1", System.currentTimeMillis());
    }

    @RequestMapping(value = "/search")
    @ResponseBody
    public void searched(@PathParam(value = "id") String id, @PathParam(value = "userId") String userId) {
        System.out.println(id + " has be searched by " + userId);
        actionService.insert(userId, id, "2", System.currentTimeMillis());

    }

    @RequestMapping(value = "/favorite")
    @ResponseBody
    public void favorited(@PathParam(value = "id") String id, @PathParam(value = "userId") String userId) {
        System.out.println(id + " has be favorited by " + userId);
        actionService.insert(userId, id, "3", System.currentTimeMillis());
    }

    @RequestMapping(value = "/pay")
    @ResponseBody
    public void payed(@PathParam(value = "id") String id, @PathParam(value = "userId") String userId) {
        System.out.println(id + " has be payed by " + userId);
        actionService.insert(userId, id, "4", System.currentTimeMillis());

    }

}
