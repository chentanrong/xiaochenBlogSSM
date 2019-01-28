package com.ylt.entity;

public class Posts {
    private String postsId;

    private String userId;

    private String postTitle;

    private String postContent;

    private String postDate;

    private String postStatus;

    private String postCommentCount;

    private String postImage;

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId == null ? null : postsId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle == null ? null : postTitle.trim();
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent == null ? null : postContent.trim();
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate == null ? null : postDate.trim();
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus == null ? null : postStatus.trim();
    }

    public String getPostCommentCount() {
        return postCommentCount;
    }

    public void setPostCommentCount(String postCommentCount) {
        this.postCommentCount = postCommentCount == null ? null : postCommentCount.trim();
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage == null ? null : postImage.trim();
    }
}