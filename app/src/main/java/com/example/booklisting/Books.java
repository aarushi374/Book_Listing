package com.example.booklisting;

public class Books {
    private String title, info_link, rating, date, category, img_link, authors, price, subTitle, publisher, description, ratingsCount;

    public Books(String title, String authors, String img_link, String info_link, String rating, String date, String category, String price, String subTitle, String publisher, String description, String ratingsCount) {
        this.authors = authors;
        this.category = category;
        this.date = date;
        this.img_link = img_link;
        this.info_link = info_link;
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.ratingsCount = ratingsCount;
        this.price = price;
        this.subTitle = subTitle;
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public String getAuthors() {
        return authors;
    }

    public String getCategory() {
        return category;
    }

    public String getImg_link() {
        return img_link;
    }

    public String getInfo_link() {
        return info_link;
    }

    public String getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getRatingsCount() {
        return ratingsCount;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
