package com.springboot.blog.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class CommentDTO {
    private Long Id;
    @NotEmpty(groups = UpdateGroups.ValidateAll.class)
    @Email(groups = {UpdateGroups.ValidateAll.class,UpdateGroups.ValidatePartial.class})
    private String emailId;
    @NotEmpty(groups = UpdateGroups.ValidateAll.class)
    private String name;
    @NotEmpty(groups = UpdateGroups.ValidateAll.class)
    @Size(
            min = 10,
            message = "Comment body should have at least 10 characters",
            groups = {UpdateGroups.ValidateAll.class,UpdateGroups.ValidatePartial.class}
    )
    private String body;
}
