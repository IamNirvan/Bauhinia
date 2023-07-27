package com.nirvan.bauhinia.imagedata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.item.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="item_images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ImageData {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String type;
    private String filePath;

    @JsonIgnore
    @ManyToOne
    private Item imageItem;

    public ImageData(String name, String type, String filePath) {
        this.name = name;
        this.type = type;
        this.filePath = filePath;
    }

    public ImageData(String name, String type, String filePath, Item imageItem) {
        this.name = name;
        this.type = type;
        this.filePath = filePath;
        this.imageItem = imageItem;
    }
}
