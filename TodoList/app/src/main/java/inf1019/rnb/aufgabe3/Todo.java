package inf1019.rnb.aufgabe3;

/**
 * Created by rnb on 10.04.2015.
 */

/*
Represents an item in the Todo-List.
Consists a
    + Title
    + Content
    + Category
 */
public class Todo extends Object {
    private Category category = Category.OTHER;
    private String content = "";
    private String title ="";
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    private boolean expanded;

    public Todo() {

    }

    public Todo(String content) {
        this.content = content;
    }
    public Todo(String content, Category category) {
        this(content);
        this.category = category;
    }
    public Todo(String content, String title, Category category)
    {
        this(content, category);
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public final Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public final String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return category+"\t"+title+"\t"+content;
    }

    public final boolean getChecked() {
        return checked;
    }

    public void setChecked(final boolean checked) {
        this.checked = checked;
    }

}