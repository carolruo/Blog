package com.spring.blog.controller;

import com.spring.blog.model.Post;
import com.spring.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    BlogService blogService; //usando interface para qnd quiser mudar a impl dela, o código nao vai quebrar

    //MVC; Model = Post; RETORNAR MODEL E A VIEW (o que o html vai renderizar)
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView getPosts() {
        ModelAndView mv = new ModelAndView("posts"); //posts.html
        List<Post> posts = blogService.findAll();//vai retornar a lista de posts salvos no banco de dados
        mv.addObject("posts", posts); //os objs java vao ser acessados atraves da palavra "posts"
        return mv;
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ModelAndView getPostDetails(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("postDetails");
        Post post = blogService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.GET)
    public String getPostForm() {
        return "postForm";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.POST)
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique se todos os campos foram preenchidos");
            return "redirect:/newpost";
        }
        post.setDate(LocalDate.now());
        blogService.save(post);
        return "redirect:/posts";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deletePost(@RequestParam Long id) {
        blogService.delete(id);
        return "redirect:/posts";
    }
}
