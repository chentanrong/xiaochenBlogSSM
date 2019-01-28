package com.ylt.entity;

public class Articles {
    private String articleId;

    private String userId;

    private String articleTitle;

    private String articleContent;

    private String articleViews;

    private String articleCommentCount;

    private String articleDate;

    private String articleLikeCount;

    private String articleImage;

    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId == null ? null : articleId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle == null ? null : articleTitle.trim();
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent == null ? null : articleContent.trim();
    }

    public String getArticleViews() {
        return articleViews;
    }

    public void setArticleViews(String articleViews) {
        this.articleViews = articleViews == null ? null : articleViews.trim();
    }

    public String getArticleCommentCount() {
        return articleCommentCount;
    }

    public void setArticleCommentCount(String articleCommentCount) {
        this.articleCommentCount = articleCommentCount == null ? null : articleCommentCount.trim();
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate == null ? null : articleDate.trim();
    }

    public String getArticleLikeCount() {
        return articleLikeCount;
    }

    public void setArticleLikeCount(String articleLikeCount) {
        this.articleLikeCount = articleLikeCount == null ? null : articleLikeCount.trim();
    }

    public String getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(String articleImage) {
        this.articleImage = articleImage == null ? null : articleImage.trim();
    }
}