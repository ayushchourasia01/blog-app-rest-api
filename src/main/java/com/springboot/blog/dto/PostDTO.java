package com.springboot.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {
    private long id;
    @NotEmpty(groups = UpdateGroups.ValidateAll.class)
    @Size(min = 2, message = "Post title should have at least 2 characters", groups = {UpdateGroups.ValidateAll.class,UpdateGroups.ValidatePartial.class})
    private String title;
    @NotEmpty(groups = UpdateGroups.ValidateAll.class)
    @Size(min = 10, message = "Post Description should have at least 10 characters", groups = {UpdateGroups.ValidateAll.class,UpdateGroups.ValidatePartial.class})
    private String description;
    @NotEmpty(groups = UpdateGroups.ValidateAll.class)
    @Size(min = 10, message = "Post Content should have at least 10 characters", groups = {UpdateGroups.ValidateAll.class,UpdateGroups.ValidatePartial.class})
    private String content;
    private Set<CommentDTO> comments;
}
