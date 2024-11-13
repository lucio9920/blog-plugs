package com.luciogarcia.BlogApi.controller;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.luciogarcia.BlogApi.model.Post;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final List<Post> posts = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        post.setId(idCounter.incrementAndGet());
        posts.add(post);
        return post;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return posts;
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        Post post = getPost(id);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        return post;
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
}