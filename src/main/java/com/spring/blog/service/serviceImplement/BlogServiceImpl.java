package com.spring.blog.service.serviceImplement;

import com.spring.blog.model.Post;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

//Mostrar ao spring que essa classe será um BEAN gerenciado por ele
@Service
public class BlogServiceImpl implements BlogService {

    //Ponto de injeção do meu repository para conseguir utilizar as instancias desse repository
    @Autowired
    BlogRepository blogRepository;

    @Override
    public List<Post> findAll() {
        return blogRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Post findById(long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Post save(Post post) {
        return blogRepository.save(post);
    }

    @Override
    public void delete(long id) {
        blogRepository.deleteById(id);
    }
}
