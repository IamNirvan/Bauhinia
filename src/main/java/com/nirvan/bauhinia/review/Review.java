package com.nirvan.bauhinia.review;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Review")
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne
    private Item item;

    public Review(String title, String content, Item item) {
        this.title = title;
        this.content = content;
        this.item = item;
    }

    public Review(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
